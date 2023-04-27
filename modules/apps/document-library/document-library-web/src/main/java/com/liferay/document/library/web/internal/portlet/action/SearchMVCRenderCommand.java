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
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContext;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContextProvider;
import com.liferay.document.library.web.internal.display.context.DLAdminManagementToolbarDisplayContext;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/search"
	},
	service = MVCRenderCommand.class
)
public class SearchMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		DLAdminDisplayContext dlAdminDisplayContext =
			_dlAdminDisplayContextProvider.getDLAdminDisplayContext(
				_portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse));

		renderRequest.setAttribute(
			DLAdminDisplayContext.class.getName(), dlAdminDisplayContext);
		renderRequest.setAttribute(
			DLAdminManagementToolbarDisplayContext.class.getName(),
			_dlAdminDisplayContextProvider.
				getDLAdminManagementToolbarDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getHttpServletResponse(renderResponse),
					dlAdminDisplayContext));

		renderRequest.setAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_PORTLET_TOOLBAR_CONTRIBUTOR,
			_defaultDLPortletToolbarContributor);
		renderRequest.setAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_TRASH_HELPER, _dlTrashHelper);

		return "/document_library/view.jsp";
	}

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")"
	)
	private PortletToolbarContributor _defaultDLPortletToolbarContributor;

	@Reference
	private DLAdminDisplayContextProvider _dlAdminDisplayContextProvider;

	@Reference
	private DLTrashHelper _dlTrashHelper;

	@Reference
	private Portal _portal;

}