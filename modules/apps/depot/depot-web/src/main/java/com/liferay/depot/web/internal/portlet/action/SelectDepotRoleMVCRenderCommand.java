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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.display.context.DepotAdminSelectRoleDisplayContext;
import com.liferay.depot.web.internal.display.context.DepotAdminSelectRoleManagementToolbarDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"mvc.command.name=/depot/select_role"
	},
	service = MVCRenderCommand.class
)
public class SelectDepotRoleMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			DepotAdminSelectRoleDisplayContext
				depotAdminSelectRoleDisplayContext =
					new DepotAdminSelectRoleDisplayContext(
						renderRequest, renderResponse);

			renderRequest.setAttribute(
				DepotAdminWebKeys.DEPOT_ADMIN_SELECT_ROLE_DISPLAY_CONTEXT,
				depotAdminSelectRoleDisplayContext);

			DepotAdminSelectRoleDisplayContext.Step step =
				depotAdminSelectRoleDisplayContext.getStep();

			renderRequest.setAttribute(
				DepotAdminWebKeys.
					DEPOT_ADMIN_SELECT_ROLE_MANAGEMENT_TOOLBAL_DISPLAY_CONTEXT,
				new DepotAdminSelectRoleManagementToolbarDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					step.getSearchContainer()));

			return "/select_depot_role.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private Portal _portal;

}