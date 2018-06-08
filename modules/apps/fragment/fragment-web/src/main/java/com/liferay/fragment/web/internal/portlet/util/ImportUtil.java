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

import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.fragment.exception.DuplicateFragmentEntryKeyException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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
			ActionRequest actionRequest, File file, boolean overwrite)
		throws Exception {

		ZipFile zipFile = new ZipFile(file);

		_isValidFile(zipFile);

		Map<String, FragmentCollectionFolder> fragmentCollectionFolderMap =
			_getFragmentCollectionFolderMap(zipFile);

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

			importFragmentEntries(
				actionRequest, zipFile,
				fragmentCollection.getFragmentCollectionId(),
				fragmentCollectionFolder.getFragmentEntries(), overwrite);
		}
	}

	public void importFragmentEntries(
			ActionRequest actionRequest, ZipFile zipFile,
			long fragmentCollectionId, Map<String, String> fragmentEntries,
			boolean overwrite)
		throws Exception {

		for (Map.Entry<String, String> entry : fragmentEntries.entrySet()) {
			String name = entry.getKey();
			String css = StringPool.BLANK;
			String html = StringPool.BLANK;
			String js = StringPool.BLANK;

			String fragmentJSON = _getContent(zipFile, entry.getValue());

			if (Validator.isNotNull(fragmentJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				name = jsonObject.getString("name");
				css = _getContent(zipFile, jsonObject.getString("cssPath"));
				html = _getContent(zipFile, jsonObject.getString("htmlPath"));
				js = _getContent(zipFile, jsonObject.getString("jsPath"));
			}

			_addFragmentEntry(
				actionRequest, fragmentCollectionId, entry.getKey(), name, css,
				html, js, overwrite);
		}
	}

	private FragmentCollection _addFragmentCollection(
			ActionRequest actionRequest, String fragmentCollectionKey,
			String name, String description, boolean overwrite)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				themeDisplay.getScopeGroupId(), fragmentCollectionKey);

		if (fragmentCollection == null) {
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

	private void _addFragmentEntry(
			ActionRequest actionRequest, long fragmentCollectionId,
			String fragmentEntryKey, String name, String css, String html,
			String js, boolean overwrite)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				themeDisplay.getScopeGroupId(), fragmentEntryKey);

		if (fragmentEntry == null) {
			_fragmentEntryService.addFragmentEntry(
				themeDisplay.getScopeGroupId(), fragmentCollectionId,
				fragmentEntryKey, name, css, html, js,
				WorkflowConstants.STATUS_DRAFT, serviceContext);
		}
		else if (overwrite) {
			_fragmentEntryService.updateFragmentEntry(
				fragmentEntry.getFragmentEntryId(), name, css, html, js,
				WorkflowConstants.STATUS_APPROVED);
		}
		else {
			throw new DuplicateFragmentEntryKeyException(fragmentEntryKey);
		}
	}

	private String _getContent(ZipFile zipFile, String fileName)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(fileName);

		if (zipEntry == null) {
			return StringPool.BLANK;
		}

		return StringUtil.read(zipFile.getInputStream(zipEntry));
	}

	private Map<String, FragmentCollectionFolder>
		_getFragmentCollectionFolderMap(ZipFile zipFile) {

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
				continue;
			}

			String fragmentCollectionKey = paths[paths.length - 3];

			FragmentCollectionFolder fragmentCollectionFolder =
				fragmentCollectionFolderMap.get(fragmentCollectionKey);

			if (fragmentCollectionFolder == null) {
				continue;
			}

			fragmentCollectionFolder.addFragmentEntry(
				_getKey(fileName), fileName);
		}

		return fragmentCollectionFolderMap;
	}

	private String _getKey(String fileName) {
		String path = fileName.substring(
			0, fileName.lastIndexOf(CharPool.SLASH));

		return path.substring(path.lastIndexOf(CharPool.SLASH) + 1);
	}

	private boolean _isFragmentCollection(String fileName) {
		if (fileName.endsWith(
				FragmentExportImportConstants.COLLECTION_CONFIG_FILE_NAME)) {

			return true;
		}

		return false;
	}

	private boolean _isFragmentEntry(String fileName) {
		if (fileName.endsWith(
				FragmentExportImportConstants.FRAGMENT_CONFIG_FILE_NAME)) {

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

		throw new PortalException();
	}

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

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