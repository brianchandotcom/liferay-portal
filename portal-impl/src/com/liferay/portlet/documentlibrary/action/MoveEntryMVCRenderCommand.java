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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 * @author Manuel de la Peña
 * @author Levente Hudák
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY_DISPLAY,
		"javax.portlet.name=" + PortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/move_entry"
	},
	service = MVCRenderCommand.class
)
public class MoveEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ActionUtil.getFileEntries(renderRequest);
			ActionUtil.getFileShortcuts(renderRequest);
			ActionUtil.getFolders(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/html/portlet/document_library/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/html/portlet/document_library/move_entries.jsp";
	}

}