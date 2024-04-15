/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.portlet.action;

import com.liferay.portal.defaultpermissions.configuration.PortalDefaultPermissionsConfiguration;
import com.liferay.portal.defaultpermissions.web.internal.constants.PortalDefaultPermissionsWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.roles.admin.constants.RolesAdminWebKeys;
import com.liferay.roles.admin.role.type.contributor.provider.RoleTypeContributorProvider;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"javax.portlet.name=com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet",
		"mvc.command.name=/configuration/edit_portal_default_permissions_configuration"
	},
	service = MVCRenderCommand.class
)
public class EditPortalDefaultPermissionsConfigurationMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/configuration" +
					"/edit_portal_default_permissions_configuration.jsp");

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			renderRequest.setAttribute(
				PortalDefaultPermissionsWebKeys.
					PORTAL_DEFAULT_PERMISSIONS_CONFIGURATION,
				_portalDefaultPermissionsConfiguration);

			renderRequest.setAttribute(
				RolesAdminWebKeys.ROLE_TYPE_CONTRIBUTOR_PROVIDER,
				_roleTypeContributorProvider);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortalDefaultPermissionsConfiguration
		_portalDefaultPermissionsConfiguration;

	@Reference
	private RoleTypeContributorProvider _roleTypeContributorProvider;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.defaultpermissions.web)"
	)
	private ServletContext _servletContext;

}