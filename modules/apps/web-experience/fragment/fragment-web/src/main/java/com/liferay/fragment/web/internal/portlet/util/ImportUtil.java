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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipReader;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ImportUtil.class)
public class ImportUtil {

	public void importFragmentCollections(
			ActionRequest actionRequest, ZipReader zipReader, boolean overwrite)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (String entry : zipReader.getEntries()) {
			if (!_isFragmentCollection(entry)) {
				continue;
			}

			String collectionPath = entry.substring(
				0, entry.lastIndexOf(CharPool.SLASH));

			String fragmentCollectionKey = collectionPath.substring(
				collectionPath.lastIndexOf(CharPool.SLASH) + 1);

			String fragmentCollectionName = fragmentCollectionKey;

			String fragmentCollectionDescription = StringPool.BLANK;

			String collectionJSON = zipReader.getEntryAsString(entry);

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

			FragmentCollection fragmentCollection =
				_fragmentCollectionLocalService.fetchFragmentCollection(
					themeDisplay.getScopeGroupId(), fragmentCollectionKey);

			if (fragmentCollection == null) {
				fragmentCollection =
					_fragmentCollectionService.addFragmentCollection(
						themeDisplay.getScopeGroupId(), fragmentCollectionKey,
						fragmentCollectionName, fragmentCollectionDescription,
						serviceContext);
			}
			else if (overwrite && (fragmentCollection != null)) {
				_fragmentCollectionService.updateFragmentCollection(
					fragmentCollection.getFragmentCollectionId(),
					fragmentCollectionName, fragmentCollectionDescription);
			}
			else {
				throw new DuplicateFragmentCollectionKeyException(
					fragmentCollectionKey);
			}

			importFragmentEntries(
				actionRequest, zipReader,
				fragmentCollection.getFragmentCollectionId(), collectionPath,
				overwrite);
		}
	}

	public void importFragmentEntries(
			ActionRequest actionRequest, ZipReader zipReader,
			long fragmentCollectionId, String path, boolean overwrite)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (String entry : zipReader.getEntries()) {
			if (!entry.startsWith(path) || !_isFragmentEntry(entry)) {
				continue;
			}

			String fragmentEntryPath = entry.substring(
				0, entry.lastIndexOf(CharPool.SLASH));

			String fragmentEntryKey = fragmentEntryPath.substring(
				fragmentEntryPath.lastIndexOf(CharPool.SLASH) + 1);

			String fragmentEntryName = fragmentEntryKey;

			String fragmentCssPath = fragmentEntryPath + "/src/index.css";
			String fragmentHtmlPath = fragmentEntryPath + "/src/index.html";
			String fragmentJsPath = fragmentEntryPath + "/src/index.js";

			String fragmentJSON = zipReader.getEntryAsString(entry);

			if (Validator.isNotNull(fragmentJSON)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					fragmentJSON);

				fragmentEntryName = jsonObject.getString("name");
				fragmentCssPath = jsonObject.getString("cssPath");
				fragmentHtmlPath = jsonObject.getString("htmlPath");
				fragmentJsPath = jsonObject.getString("jsPath");
			}

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.fetchFragmentEntry(
					themeDisplay.getScopeGroupId(), fragmentEntryKey);

			if (fragmentEntry == null) {
				_fragmentEntryService.addFragmentEntry(
					themeDisplay.getScopeGroupId(), fragmentCollectionId,
					fragmentEntryKey, fragmentEntryName,
					zipReader.getEntryAsString(fragmentCssPath),
					zipReader.getEntryAsString(fragmentHtmlPath),
					zipReader.getEntryAsString(fragmentJsPath),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}
			else if (overwrite && (fragmentEntry != null)) {
				_fragmentEntryService.updateFragmentEntry(
					fragmentEntry.getFragmentEntryId(), fragmentEntryName,
					zipReader.getEntryAsString(fragmentCssPath),
					zipReader.getEntryAsString(fragmentHtmlPath),
					zipReader.getEntryAsString(fragmentJsPath),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}
			else {
				throw new DuplicateFragmentEntryKeyException(fragmentEntryKey);
			}
		}
	}

	private boolean _isFragmentCollection(String entry) {
		if (entry.endsWith("collection.json")) {
			return true;
		}

		return false;
	}

	private boolean _isFragmentEntry(String entry) {
		if (entry.endsWith("fragment.json")) {
			return true;
		}

		return false;
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