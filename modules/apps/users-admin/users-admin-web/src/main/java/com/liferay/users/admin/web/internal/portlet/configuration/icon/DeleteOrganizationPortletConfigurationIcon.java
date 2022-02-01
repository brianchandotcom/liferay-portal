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

package com.liferay.users.admin.web.internal.portlet.configuration.icon;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.web.internal.portlet.action.ActionUtil;
import com.liferay.users.admin.web.internal.util.UsersAdminPortletURLUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"path=/users_admin/view"
	},
	service = PortletConfigurationIcon.class
)
public class DeleteOrganizationPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "delete");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		StringBundler sb = new StringBundler(6);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			sb.append(portletDisplay.getNamespace());

			sb.append("deleteOrganization('");

			Organization organization = ActionUtil.getOrganization(
				portletRequest);

			sb.append(organization.getOrganizationId());

			sb.append("', '");

			String backURL = String.valueOf(
				portletRequest.getAttribute("view.jsp-backURL"));

			if (Validator.isNull(backURL)) {
				backURL =
					UsersAdminPortletURLUtil.
						createParentOrganizationViewTreeURL(
							organization.getOrganizationId(),
							(RenderResponse)portletResponse);
			}

			sb.append(HtmlUtil.escapeJS(backURL));

			sb.append("');");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return sb.toString();
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 102;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (_organizationPermission.contains(
					themeDisplay.getPermissionChecker(),
					ActionUtil.getOrganization(portletRequest),
					ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteOrganizationPortletConfigurationIcon.class);

	@Reference
	private OrganizationPermission _organizationPermission;

}