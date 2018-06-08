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

	public void importFragmentCollections(
			ActionRequest actionRequest, File file, boolean overwrite)
		throws Exception {

		ZipFile zipFile = new ZipFile(file);

		_isValidFile(zipFile);

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			String fileName = zipEntry.getName();

			if (!_isFragmentCollection(fileName)) {
				continue;
			}

			String collectionPath = fileName.substring(
				0, fileName.lastIndexOf(CharPool.SLASH));

			String fragmentCollectionName = _getKey(fileName);
			String fragmentCollectionDescription = StringPool.BLANK;

			String collectionJSON = _getContent(zipFile, fileName);

			if (Validator.isNotNull(collectionJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					collectionJSON);

				fragmentCollectionName = jsonObject.getString("name");
				fragmentCollectionDescription = jsonObject.getString(
					"description");
			}

			if (Validator.isNull(fragmentCollectionName)) {
				throw new FragmentCollectionNameException();
			}

			FragmentCollection fragmentCollection = _addFragmentCollection(
				actionRequest, _getKey(fileName), fragmentCollectionName,
				fragmentCollectionDescription, overwrite);

			importFragmentEntries(
				actionRequest, zipFile,
				fragmentCollection.getFragmentCollectionId(), collectionPath,
				overwrite);
		}
	}

	public void importFragmentEntries(
			ActionRequest actionRequest, ZipFile zipFile,
			long fragmentCollectionId, String path, boolean overwrite)
		throws Exception {

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			String fileName = zipEntry.getName();

			if (!fileName.startsWith(path) || !_isFragmentEntry(fileName)) {
				continue;
			}

			String fragmentEntryPath = fileName.substring(
				0, fileName.lastIndexOf(CharPool.SLASH));

			String fragmentEntryName = _getKey(fileName);

			String fragmentCssPath = fragmentEntryPath + "/src/index.css";
			String fragmentHtmlPath = fragmentEntryPath + "/src/index.html";
			String fragmentJsPath = fragmentEntryPath + "/src/index.js";

			String fragmentJSON = _getContent(zipFile, fileName);

			if (Validator.isNotNull(fragmentJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				fragmentEntryName = jsonObject.getString("name");
				fragmentCssPath = jsonObject.getString("cssPath");
				fragmentHtmlPath = jsonObject.getString("htmlPath");
				fragmentJsPath = jsonObject.getString("jsPath");
			}

			_addFragmentEntry(
				actionRequest, fragmentCollectionId, _getKey(fileName),
				fragmentEntryName, _getContent(zipFile, fragmentCssPath),
				_getContent(zipFile, fragmentHtmlPath),
				_getContent(zipFile, fragmentJsPath), overwrite);
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

}