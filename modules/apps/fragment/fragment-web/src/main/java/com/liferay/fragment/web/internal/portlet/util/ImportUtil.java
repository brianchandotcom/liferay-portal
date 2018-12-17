/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.web.internal.portlet.util;

import com.liferay.fragment.constants.FragmentEntryTypeConstants;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.fragment.exception.DuplicateFragmentEntryKeyException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.exception.InvalidFileException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ImportUtil.class)
public class ImportUtil {

	public void importFile(
			ActionRequest actionRequest, File file, long fragmentCollectionId,
			boolean overwrite)
		throws Exception {

		_invalidFragmentEntriesNames = new ArrayList<>();

		ZipFile zipFile = new ZipFile(file);

		_isValidFile(zipFile);

		Map<String, String> orphanFragmentEntries = new HashMap<>();

		Map<String, FragmentCollectionFolder> fragmentCollectionFolderMap =
			_getFragmentCollectionFolderMap(zipFile, orphanFragmentEntries);

		for (Map.Entry<String, FragmentCollectionFolder> entry :
				fragmentCollectionFolderMap.entrySet()) {

			FragmentCollectionFolder fragmentCollectionFolder =
				entry.getValue();

			String name = entry.getKey();
			String description = StringPool.BLANK;

			String collectionJSON = _getContent(
				zipFile, fragmentCollectionFolder.getFileName());

			if (Validator.isNotNull(collectionJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					collectionJSON);

				name = jsonObject.getString("name");
				description = jsonObject.getString("description");
			}

			if (Validator.isNull(name)) {
				throw new FragmentCollectionNameException();
			}

			FragmentCollection fragmentCollection = _addFragmentCollection(
				actionRequest, entry.getKey(), name, description, overwrite);

			_importFragmentEntries(
				actionRequest, zipFile,
				fragmentCollection.getFragmentCollectionId(),
				fragmentCollectionFolder.getFragmentEntries(), overwrite);
		}

		if (MapUtil.isNotEmpty(orphanFragmentEntries)) {
			if (fragmentCollectionId <= 0) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				FragmentCollection fragmentCollection =
					_fragmentCollectionLocalService.fetchFragmentCollection(
						themeDisplay.getScopeGroupId(),
						_DEFAULT_FRAGMENT_COLLECTION_KEY);

				if (fragmentCollection == null) {
					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(actionRequest);

					fragmentCollection =
						_fragmentCollectionService.addFragmentCollection(
							themeDisplay.getScopeGroupId(),
							_DEFAULT_FRAGMENT_COLLECTION_KEY,
							LanguageUtil.get(
								_portal.getHttpServletRequest(actionRequest),
								_DEFAULT_FRAGMENT_COLLECTION_KEY),
							StringPool.BLANK, serviceContext);
				}

				fragmentCollectionId =
					fragmentCollection.getFragmentCollectionId();
			}

			_importFragmentEntries(
				actionRequest, zipFile, fragmentCollectionId,
				orphanFragmentEntries, overwrite);
		}

		if (ListUtil.isNotEmpty(_invalidFragmentEntriesNames)) {
			SessionMessages.add(
				actionRequest, "invalidFragmentEntriesNames",
				_invalidFragmentEntriesNames);
		}
	}

	private FragmentCollection _addFragmentCollection(
			ActionRequest actionRequest, String fragmentCollectionKey,
			String name, String description, boolean overwrite)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				themeDisplay.getScopeGroupId(), fragmentCollectionKey);

		if (fragmentCollection == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			fragmentCollection =
				_fragmentCollectionService.addFragmentCollection(
					themeDisplay.getScopeGroupId(), fragmentCollectionKey, name,
					description, serviceContext);
		}
		else if (overwrite) {
			fragmentCollection =
				_fragmentCollectionService.updateFragmentCollection(
					fragmentCollection.getFragmentCollectionId(), name,
					description);
		}
		else {
			throw new DuplicateFragmentCollectionKeyException(
				fragmentCollectionKey);
		}

		return fragmentCollection;
	}

	private FragmentEntry _addFragmentEntry(
			ActionRequest actionRequest, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, String typeLabel, boolean overwrite)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				themeDisplay.getScopeGroupId(), fragmentEntryKey);

		if ((fragmentEntry != null) && !overwrite) {
			throw new DuplicateFragmentEntryKeyException(fragmentEntryKey);
		}

		int status = WorkflowConstants.STATUS_APPROVED;

		try {
			_fragmentEntryProcessorRegistry.validateFragmentEntryHTML(html);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			status = WorkflowConstants.STATUS_DRAFT;

			_invalidFragmentEntriesNames.add(name);
		}

		int type = FragmentEntryTypeConstants.getTypeFromLabel(
			StringUtil.toLowerCase(StringUtil.trim(typeLabel)));

		if (fragmentEntry == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			return _fragmentEntryService.addFragmentEntry(
				themeDisplay.getScopeGroupId(), fragmentCollectionId,
				fragmentEntryKey, name, css, html, js, type, status,
				serviceContext);
		}

		return _fragmentEntryService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), name, css, html, js, status);
	}

	private String _getContent(ZipFile zipFile, String fileName)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(fileName);

		if (zipEntry == null) {
			return StringPool.BLANK;
		}

		return StringUtil.read(zipFile.getInputStream(zipEntry));
	}

	private String _getFileName(String path) {
		int pos = path.lastIndexOf(CharPool.SLASH);

		if (pos > 0) {
			return path.substring(pos + 1);
		}

		return StringPool.BLANK;
	}

	private Map<String, FragmentCollectionFolder>
		_getFragmentCollectionFolderMap(
			ZipFile zipFile, Map<String, String> orphanFragmentEntries) {

		Map<String, FragmentCollectionFolder> fragmentCollectionFolderMap =
			new HashMap<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			if (!_isFragmentCollection(fileName)) {
				continue;
			}

			fragmentCollectionFolderMap.put(
				_getKey(fileName), new FragmentCollectionFolder(fileName));
		}

		enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			if (!_isFragmentEntry(fileName)) {
				continue;
			}

			String[] paths = fileName.split(StringPool.SLASH);

			if (paths.length < 3) {
				orphanFragmentEntries.put(_getKey(fileName), fileName);

				continue;
			}

			String fragmentCollectionKey = paths[paths.length - 3];

			FragmentCollectionFolder fragmentCollectionFolder =
				fragmentCollectionFolderMap.get(fragmentCollectionKey);

			if (fragmentCollectionFolder == null) {
				orphanFragmentEntries.put(_getKey(fileName), fileName);

				continue;
			}

			fragmentCollectionFolder.addFragmentEntry(
				_getKey(fileName), fileName);
		}

		return fragmentCollectionFolderMap;
	}

	private String _getFragmentEntryContent(
			ZipFile zipFile, String fileName, String contentPath)
		throws Exception {

		InputStream inputStream = _getFragmentEntryInputStream(
			zipFile, fileName, contentPath);

		if (inputStream == null) {
			return StringPool.BLANK;
		}

		return StringUtil.read(inputStream);
	}

	private InputStream _getFragmentEntryInputStream(
			ZipFile zipFile, String fileName, String contentPath)
		throws Exception {

		if (contentPath.startsWith(StringPool.SLASH)) {
			return _getInputStream(zipFile, contentPath.substring(1));
		}

		if (contentPath.startsWith("./")) {
			contentPath = contentPath.substring(2);
		}

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.SLASH));

		return _getInputStream(zipFile, path + StringPool.SLASH + contentPath);
	}

	private InputStream _getInputStream(ZipFile zipFile, String fileName)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(fileName);

		if (zipEntry == null) {
			return null;
		}

		return zipFile.getInputStream(zipEntry);
	}

	private String _getKey(String fileName) {
		String path = fileName.substring(
			0, fileName.lastIndexOf(CharPool.SLASH));

		return path.substring(path.lastIndexOf(CharPool.SLASH) + 1);
	}

	private long _getPreviewFileEntryId(
			ActionRequest actionRequest, ZipFile zipFile, long fragmentEntryId,
			String fileName, String contentPath)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		InputStream inputStream = _getFragmentEntryInputStream(
			zipFile, fileName, contentPath);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				themeDisplay.getScopeGroupId(), FragmentPortletKeys.FRAGMENT);

		if (repository == null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				themeDisplay.getScopeGroupId(), FragmentPortletKeys.FRAGMENT,
				serviceContext);
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			FragmentEntry.class.getName(), fragmentEntryId,
			FragmentPortletKeys.FRAGMENT, repository.getDlFolderId(),
			inputStream,
			fragmentEntryId + "_preview." + FileUtil.getExtension(contentPath),
			MimeTypesUtil.getContentType(contentPath), false);

		return fileEntry.getFileEntryId();
	}

	private void _importFragmentEntries(
			ActionRequest actionRequest, ZipFile zipFile,
			long fragmentCollectionId, Map<String, String> fragmentEntries,
			boolean overwrite)
		throws Exception {

		for (Map.Entry<String, String> entry : fragmentEntries.entrySet()) {
			String name = entry.getKey();
			String css = StringPool.BLANK;
			String html = StringPool.BLANK;
			String js = StringPool.BLANK;
			String typeLabel = StringPool.BLANK;

			String fragmentJSON = _getContent(zipFile, entry.getValue());

			if (Validator.isNotNull(fragmentJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				name = jsonObject.getString("name");
				css = _getFragmentEntryContent(
					zipFile, entry.getValue(), jsonObject.getString("cssPath"));
				html = _getFragmentEntryContent(
					zipFile, entry.getValue(),
					jsonObject.getString("htmlPath"));
				js = _getFragmentEntryContent(
					zipFile, entry.getValue(), jsonObject.getString("jsPath"));
				typeLabel = jsonObject.getString("type");
			}

			FragmentEntry fragmentEntry = _addFragmentEntry(
				actionRequest, fragmentCollectionId, entry.getKey(), name, css,
				html, js, typeLabel, overwrite);

			if (Validator.isNotNull(fragmentJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				String thumbnailPath = jsonObject.getString("thumbnailPath");

				if (Validator.isNotNull(thumbnailPath)) {
					long previewFileEntryId = _getPreviewFileEntryId(
						actionRequest, zipFile,
						fragmentEntry.getFragmentEntryId(), entry.getValue(),
						thumbnailPath);

					_fragmentEntryLocalService.updateFragmentEntry(
						fragmentEntry.getFragmentEntryId(), previewFileEntryId);
				}
			}
		}
	}

	private boolean _isFragmentCollection(String fileName) {
		if (Objects.equals(
				_getFileName(fileName),
				FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG)) {

			return true;
		}

		return false;
	}

	private boolean _isFragmentEntry(String fileName) {
		if (Objects.equals(
				_getFileName(fileName),
				FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG)) {

			return true;
		}

		return false;
	}

	private void _isValidFile(ZipFile zipFile) throws PortalException {
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (_isFragmentCollection(zipEntry.getName()) ||
				_isFragmentEntry(zipEntry.getName())) {

				return;
			}
		}

		throw new InvalidFileException();
	}

	private static final String _DEFAULT_FRAGMENT_COLLECTION_KEY = "imported";

	private static final Log _log = LogFactoryUtil.getLog(ImportUtil.class);

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	private List<String> _invalidFragmentEntriesNames;

	@Reference
	private Portal _portal;

	private class FragmentCollectionFolder {

		public FragmentCollectionFolder(String fileName) {
			_fileName = fileName;

			_fragmentEntries = new HashMap<>();
		}

		public void addFragmentEntry(String key, String fileName) {
			_fragmentEntries.put(key, fileName);
		}

		public String getFileName() {
			return _fileName;
		}

		public Map<String, String> getFragmentEntries() {
			return _fragmentEntries;
		}

		private final String _fileName;
		private final Map<String, String> _fragmentEntries;

	}

}