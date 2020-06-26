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

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;
import com.liferay.segments.web.internal.display.context.SelectOrganizationsDisplayContext;
import com.liferay.segments.web.internal.display.context.SelectOrganizationsManagementToolbarDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=selectOrganizations"
	},
	service = MVCRenderCommand.class
)
public class SelectOrganizationsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			SelectOrganizationsDisplayContext
				selectOrganizationsDisplayContext =
					new SelectOrganizationsDisplayContext(
						_portal.getHttpServletRequest(renderRequest),
						renderRequest, renderResponse,
						_organizationLocalService);

			renderRequest.setAttribute(
				SegmentsWebKeys.SELECT_ORGANIZATIONS_DISPLAY_CONTEXT,
				selectOrganizationsDisplayContext);

			renderRequest.setAttribute(
				SegmentsWebKeys.
					SEGMENTS_SELECT_ROLE_MANAGEMENT_TOOLBAL_DISPLAY_CONTEXT,
				new SelectOrganizationsManagementToolbarDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					selectOrganizationsDisplayContext));

			return "/field/select_organizations.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

}