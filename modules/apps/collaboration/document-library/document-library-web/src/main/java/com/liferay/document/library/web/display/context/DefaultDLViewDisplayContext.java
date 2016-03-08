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

package com.liferay.document.library.web.display.context;

import com.liferay.document.library.display.context.DLViewDisplayContext;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.display.context.util.DLRequestHelper;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class DefaultDLViewDisplayContext implements DLViewDisplayContext {

	public DefaultDLViewDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		Folder folder) {

		_folder = folder;

		_dlRequestHelper = new DLRequestHelper(request);
	}

	@Override
	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(
			_dlRequestHelper.getRequest(), "displayStyle");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_dlRequestHelper.getLiferayPortletRequest());

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				DLPortletKeys.DOCUMENT_LIBRARY, "display-style",
				PropsValues.DL_DEFAULT_DISPLAY_VIEW);
		}

		return displayStyle;
	}

	@Override
	public PortletURL getDisplayStyleURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL displayStyleURL = liferayPortletResponse.createRenderURL();

		long folderId = getFolderId();

		String keywords = ParamUtil.getString(
			_dlRequestHelper.getRequest(), "keywords");

		String mvcRenderCommandName = "/document_library/search";

		if (Validator.isNull(keywords)) {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				mvcRenderCommandName = "/document_library/view";
			}
			else {
				mvcRenderCommandName = "/document_library/view_folder";
			}
		}

		displayStyleURL.setParameter(
			"mvcRenderCommandName", mvcRenderCommandName);

		displayStyleURL.setParameter(
			"navigation", HtmlUtil.escapeJS(getNavigation()));

		int curEntry = ParamUtil.getInteger(
			_dlRequestHelper.getRequest(), "curEntry");

		if (curEntry > 0) {
			displayStyleURL.setParameter("curEntry", String.valueOf(curEntry));
		}

		int deltaEntry = ParamUtil.getInteger(
			_dlRequestHelper.getRequest(), "deltaEntry");

		if (deltaEntry > 0) {
			displayStyleURL.setParameter(
				"deltaEntry", String.valueOf(deltaEntry));
		}

		displayStyleURL.setParameter("folderId", String.valueOf(folderId));

		long fileEntryTypeId = getFileEntryTypeId();

		if (fileEntryTypeId != -1) {
			displayStyleURL.setParameter(
				"fileEntryTypeId", String.valueOf(fileEntryTypeId));
		}

		return displayStyleURL;
	}

	@Override
	public Map<String, String> getOrderColumns() {
		Map<String, String> orderColumns = new HashMap();

		orderColumns.put("creationDate", "create-date");
		orderColumns.put("downloads", "downloads");
		orderColumns.put("modifiedDate", "modified-date");
		orderColumns.put("size", "size");
		orderColumns.put("title", "title");

		return orderColumns;
	}

	@Override
	public PortletURL getSortURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL sortURL = liferayPortletResponse.createRenderURL();

		long folderId = getFolderId();

		String mvcRenderCommandName = "/document_library/view";

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			mvcRenderCommandName = "/document_library/view_folder";
		}

		sortURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		sortURL.setParameter("navigation", getNavigation());
		sortURL.setParameter("folderId", String.valueOf(folderId));
		sortURL.setParameter(
			"fileEntryTypeId", String.valueOf(getFileEntryTypeId()));

		return sortURL;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	protected long getFileEntryTypeId() {
		return ParamUtil.getLong(
			_dlRequestHelper.getRequest(), "fileEntryTypeId", -1);
	}

	protected long getFolderId() {
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (_folder != null) {
			folderId = _folder.getFolderId();
		}

		return folderId;
	}

	protected String getNavigation() {
		return ParamUtil.getString(
			_dlRequestHelper.getRequest(), "navigation", "home");
	}

	private static final UUID _UUID = UUID.fromString(
		"9A2EC49C-4E47-4C8d-B49A-4C428D19E662");

	private final DLRequestHelper _dlRequestHelper;
	private final Folder _folder;

}