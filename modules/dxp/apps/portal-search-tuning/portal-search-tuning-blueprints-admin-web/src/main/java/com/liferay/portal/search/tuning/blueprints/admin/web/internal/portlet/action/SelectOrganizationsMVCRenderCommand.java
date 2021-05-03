/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context.SelectOrganizationsDisplayContext;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context.SelectOrganizationsManagementToolbarDisplayContext;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;

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
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.SELECT_ORGANIZATIONS
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
				BlueprintsAdminWebKeys.SELECT_ORGANIZATIONS_DISPLAY_CONTEXT,
				selectOrganizationsDisplayContext);
			renderRequest.setAttribute(
				BlueprintsAdminWebKeys.
					SELECT_ORGANIZATIONS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
				new SelectOrganizationsManagementToolbarDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					selectOrganizationsDisplayContext));

			return "/field/select_organizations.jsp";
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