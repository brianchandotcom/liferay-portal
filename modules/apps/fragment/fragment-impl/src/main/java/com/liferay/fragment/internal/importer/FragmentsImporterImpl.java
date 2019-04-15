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

package com.liferay.fragment.internal.importer;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.fragment.exception.DuplicateFragmentEntryKeyException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.exception.InvalidFileException;
import com.liferay.fragment.importer.FragmentsImporter;
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
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentsImporter.class)
public class FragmentsImporterImpl implements FragmentsImporter {

	@Override
	public List<String> importFile(
			long userId, long groupId, long fragmentCollectionId, File file,
			boolean overwrite)
		throws Exception {

		_invalidFragmentEntriesNames = new ArrayList<>();

		ZipFile zipFile = new ZipFile(file);

		_isValidFile(zipFile);

		Map<String, String> orphanFragmentEntries = new HashMap<>();

		Map<String, FragmentCollectionFolder> fragmentCollectionFolderMap =
			_getFragmentCollectionFolderMap(
				zipFile, groupId, orphanFragmentEntries);

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
				groupId, entry.getKey(), name, description, overwrite);

			_importResources(
				userId, groupId, fragmentCollection.getFragmentCollectionId(),
				fragmentCollection.getResourcesFolderId(), zipFile);

			_importFragmentEntries(
				userId, groupId, zipFile,
				fragmentCollection.getFragmentCollectionId(),
				fragmentCollectionFolder.getFragmentEntries(), overwrite);
		}

		if (MapUtil.isNotEmpty(orphanFragmentEntries)) {
			if (fragmentCollectionId <= 0) {
				FragmentCollection fragmentCollection =
					_fragmentCollectionLocalService.fetchFragmentCollection(
						groupId, _DEFAULT_FRAGMENT_COLLECTION_KEY);

				if (fragmentCollection == null) {
					Locale locale = _portal.getSiteDefaultLocale(groupId);

					ServiceContext serviceContext =
						ServiceContextThreadLocal.getServiceContext();

					fragmentCollection =
						_fragmentCollectionService.addFragmentCollection(
							groupId, _DEFAULT_FRAGMENT_COLLECTION_KEY,
							LanguageUtil.get(
								locale, _DEFAULT_FRAGMENT_COLLECTION_KEY),
							StringPool.BLANK, serviceContext);
				}

				fragmentCollectionId =
					fragmentCollection.getFragmentCollectionId();
			}

			_importFragmentEntries(
				userId, groupId, zipFile, fragmentCollectionId,
				orphanFragmentEntries, overwrite);
		}

		return _invalidFragmentEntriesNames;
	}

	private FragmentCollection _addFragmentCollection(
			long groupId, String fragmentCollectionKey, String name,
			String description, boolean overwrite)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				groupId, fragmentCollectionKey);

		if (fragmentCollection == null) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			fragmentCollection =
				_fragmentCollectionService.addFragmentCollection(
					groupId, fragmentCollectionKey, name, description,
					serviceContext);
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
			long fragmentCollectionId, String fragmentEntryKey, String name,
			String css, String html, String js, String typeLabel,
			boolean overwrite)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.getFragmentCollection(
				fragmentCollectionId);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentCollection.getGroupId(), fragmentEntryKey);

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

		int type = FragmentConstants.getTypeFromLabel(
			StringUtil.toLowerCase(StringUtil.trim(typeLabel)));

		if (fragmentEntry == null) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			return _fragmentEntryService.addFragmentEntry(
				fragmentCollection.getGroupId(), fragmentCollectionId,
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

		return path;
	}

	private Map<String, FragmentCollectionFolder>
			_getFragmentCollectionFolderMap(
				ZipFile zipFile, long groupId,
				Map<String, String> orphanFragmentEntries)
		throws Exception {

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
				_getKey(zipFile, groupId, fileName),
				new FragmentCollectionFolder(fileName));
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

			String fragmentCollectionPath = fileName;

			String fragmentCollectionKey = StringPool.BLANK;

			while (fragmentCollectionPath.length() > 0) {
				fragmentCollectionPath = fragmentCollectionPath.substring(
					0,
					fragmentCollectionPath.lastIndexOf(StringPool.SLASH) + 1);

				String fragmentCollectionFileName =
					fragmentCollectionPath +
						FragmentExportImportConstants.
							FILE_NAME_COLLECTION_CONFIG;

				ZipEntry fragmentCollectionZipEntry = zipFile.getEntry(
					fragmentCollectionFileName);

				if (fragmentCollectionZipEntry != null) {
					fragmentCollectionKey = _getKey(
						zipFile, groupId, fragmentCollectionFileName);

					break;
				}

				fragmentCollectionPath = fragmentCollectionPath.substring(
					0, fragmentCollectionPath.lastIndexOf(StringPool.SLASH));
			}

			if (Validator.isNull(fragmentCollectionKey)) {
				orphanFragmentEntries.put(
					_getKey(zipFile, groupId, fileName), fileName);

				continue;
			}

			FragmentCollectionFolder fragmentCollectionFolder =
				fragmentCollectionFolderMap.get(fragmentCollectionKey);

			if (fragmentCollectionFolder == null) {
				orphanFragmentEntries.put(
					_getKey(zipFile, groupId, fileName), fileName);

				continue;
			}

			fragmentCollectionFolder.addFragmentEntry(
				_getKey(zipFile, groupId, fileName), fileName);
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

	private String _getKey(ZipFile zipFile, long groupId, String fileName)
		throws Exception {

		String key = StringPool.BLANK;

		if (fileName.lastIndexOf(CharPool.SLASH) != -1) {
			String path = fileName.substring(
				0, fileName.lastIndexOf(CharPool.SLASH));

			key = path.substring(path.lastIndexOf(CharPool.SLASH) + 1);
		}
		else if (fileName.equals(
					FragmentExportImportConstants.
						FILE_NAME_COLLECTION_CONFIG)) {

			JSONObject collectionJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					zipFile.getInputStream(zipFile.getEntry(fileName))));

			key = _fragmentCollectionLocalService.generateFragmentCollectionKey(
				groupId, collectionJSONObject.getString("name"));
		}
		else if (fileName.equals(
					FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG)) {

			JSONObject fragmentJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					zipFile.getInputStream(zipFile.getEntry(fileName))));

			key = _fragmentEntryLocalService.generateFragmentEntryKey(
				groupId, fragmentJSONObject.getString("name"));
		}

		if (Validator.isNotNull(key)) {
			return key;
		}

		throw new IllegalArgumentException("Incorrect file name " + fileName);
	}

	private long _getPreviewFileEntryId(
			long userId, long groupId, ZipFile zipFile, long fragmentEntryId,
			String fileName, String contentPath)
		throws Exception {

		InputStream inputStream = _getFragmentEntryInputStream(
			zipFile, fileName, contentPath);

		if (inputStream == null) {
			return 0;
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT);

		if (repository == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT, serviceContext);
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, FragmentEntry.class.getName(), fragmentEntryId,
			FragmentPortletKeys.FRAGMENT, repository.getDlFolderId(),
			inputStream,
			fragmentEntryId + "_preview." + FileUtil.getExtension(contentPath),
			MimeTypesUtil.getContentType(contentPath), false);

		return fileEntry.getFileEntryId();
	}

	private void _importFragmentEntries(
			long userId, long groupId, ZipFile zipFile,
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
				fragmentCollectionId, entry.getKey(), name, css, html, js,
				typeLabel, overwrite);

			if (Validator.isNotNull(fragmentJSON)) {
				if (fragmentEntry.getPreviewFileEntryId() > 0) {
					PortletFileRepositoryUtil.deletePortletFileEntry(
						fragmentEntry.getPreviewFileEntryId());
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				String thumbnailPath = jsonObject.getString("thumbnailPath");

				if (Validator.isNotNull(thumbnailPath)) {
					long previewFileEntryId = _getPreviewFileEntryId(
						userId, groupId, zipFile,
						fragmentEntry.getFragmentEntryId(), entry.getValue(),
						thumbnailPath);

					_fragmentEntryLocalService.updateFragmentEntry(
						fragmentEntry.getFragmentEntryId(), previewFileEntryId);
				}
			}
		}
	}

	private void _importResources(
			long userId, long groupId, long fragmentCollectionId, long folderId,
			ZipFile zipFile)
		throws Exception {

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			String[] paths = StringUtil.split(
				zipEntry.getName(), StringPool.FORWARD_SLASH);

			if (!ArrayUtil.contains(paths, "resources")) {
				continue;
			}

			String fileName = _getFileName(zipEntry.getName());

			InputStream inputStream = _getInputStream(
				zipFile, zipEntry.getName());

			FileEntry fileEntry =
				PortletFileRepositoryUtil.fetchPortletFileEntry(
					groupId, folderId, fileName);

			if (fileEntry != null) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					fileEntry.getFileEntryId());
			}

			PortletFileRepositoryUtil.addPortletFileEntry(
				groupId, userId, FragmentCollection.class.getName(),
				fragmentCollectionId, FragmentPortletKeys.FRAGMENT, folderId,
				inputStream, fileName, MimeTypesUtil.getContentType(fileName),
				false);
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

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentsImporterImpl.class);

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