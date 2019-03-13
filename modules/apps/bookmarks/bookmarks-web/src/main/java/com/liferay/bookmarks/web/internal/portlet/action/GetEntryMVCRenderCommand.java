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

package com.liferay.bookmarks.web.internal.portlet.action;

import com.liferay.bookmarks.constants.BookmarksWebKeys;
import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Iván Zaera
 */
public abstract class GetEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			BookmarksEntry entry = ActionUtil.getEntry(renderRequest);

			if (entry != null) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)renderRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				checkPermissions(themeDisplay.getPermissionChecker(), entry);
			}

			renderRequest.setAttribute(BookmarksWebKeys.BOOKMARKS_ENTRY, entry);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/bookmarks/error.jsp";
			}

			throw new PortletException(e);
		}

		return getPath();
	}

	protected void checkPermissions(
			PermissionChecker permissionChecker, BookmarksEntry entry)
		throws PortalException {
	}

	protected abstract String getPath();

}