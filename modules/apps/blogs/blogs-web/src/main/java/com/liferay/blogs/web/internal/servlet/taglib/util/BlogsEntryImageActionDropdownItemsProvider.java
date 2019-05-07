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

package com.liferay.blogs.web.internal.servlet.taglib.util;

import com.liferay.blogs.web.internal.security.permission.resource.BlogsPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class BlogsEntryImageActionDropdownItemsProvider {

	public BlogsEntryImageActionDropdownItemsProvider(
		FileEntry fileEntry, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_fileEntry = fileEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		if ((_fileEntry.getUserId() != _themeDisplay.getUserId()) &&
			!BlogsPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.UPDATE)) {

			return null;
		}

		PortletURL portletURL = (PortletURL)_httpServletRequest.getAttribute(
			"view_images.jsp-portletURL");

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createActionURL(),
							ActionRequest.ACTION_NAME, "/blogs/edit_image",
							Constants.CMD, Constants.DELETE, "redirect",
							portletURL.toString(), "fileEntryId",
							_fileEntry.getFileEntryId());
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
					});
			}
		};
	}

	private final FileEntry _fileEntry;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}