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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.NoSuchFileShortcutException;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.DLEditFileShortcutDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_shortcut"
	},
	service = MVCRenderCommand.class
)
public class EditFileShortcutMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_EDIT_FILE_SHORTCUT_DISPLAY_CONTEXT,
				new DLEditFileShortcutDisplayContext(
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse)));

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			FileShortcut fileShortcut = ActionUtil.getFileShortcut(
				renderRequest);

			if (fileShortcut != null) {
				_fileShortcutModelResourcePermission.check(
					themeDisplay.getPermissionChecker(), fileShortcut,
					ActionKeys.UPDATE);
			}

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT, fileShortcut);

			return "/document_library/edit_file_shortcut.jsp";
		}
		catch (NoSuchFileShortcutException | PrincipalException e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/document_library/error.jsp";
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileShortcut)"
	)
	private volatile ModelResourcePermission<FileShortcut>
		_fileShortcutModelResourcePermission;

	@Reference
	private Portal _portal;

}