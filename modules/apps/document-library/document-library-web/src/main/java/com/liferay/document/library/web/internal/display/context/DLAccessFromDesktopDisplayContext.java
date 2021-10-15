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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.portlet.action.ActionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.search.ResultRow;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLAccessFromDesktopDisplayContext {

	public DLAccessFromDesktopDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_dlRequestHelper = new DLRequestHelper(httpServletRequest);
	}

	public Folder getFolder() throws PortalException {
		ResultRow row = (ResultRow)_httpServletRequest.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		if ((row != null) && (row.getObject() instanceof Folder)) {
			return (Folder)row.getObject();
		}

		Folder folder = (Folder)_httpServletRequest.getAttribute(
			"info_panel.jsp-folder");

		if (folder == null) {
			return ActionUtil.getFolder(_httpServletRequest);
		}

		return folder;
	}

	public String getRandomNamespace() {
		if (_randomNamespace != null) {
			return _randomNamespace;
		}

		String randomKey = PortalUtil.generateRandomKey(
			_httpServletRequest, _getRandomNamespaceKey());

		_randomNamespace = randomKey + StringPool.UNDERLINE;

		return _randomNamespace;
	}

	public String getWebDAVHelpMessage() {
		return LanguageUtil.get(_httpServletRequest, "webdav-help");
	}

	public String getWebDAVURL() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DLURLHelperUtil.getWebDavURL(themeDisplay, getFolder(), null);
	}

	private String _getRandomNamespaceKey() {
		String resourcePortletName = _dlRequestHelper.getResourcePortletName();

		if (resourcePortletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) ||
			resourcePortletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {

			return "portlet_document_library_folder_action";
		}

		return "portlet_image_gallery_display_folder_action";
	}

	private final DLRequestHelper _dlRequestHelper;
	private final HttpServletRequest _httpServletRequest;
	private String _randomNamespace;

}