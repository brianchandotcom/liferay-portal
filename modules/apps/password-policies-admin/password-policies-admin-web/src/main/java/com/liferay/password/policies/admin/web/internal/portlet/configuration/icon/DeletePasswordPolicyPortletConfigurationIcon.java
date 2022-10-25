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

package com.liferay.password.policies.admin.web.internal.portlet.configuration.icon;

import com.liferay.password.policies.admin.constants.PasswordPoliciesAdminPortletKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.permission.PasswordPolicyPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PasswordPoliciesAdminPortletKeys.PASSWORD_POLICIES_ADMIN,
		"path=/edit_password_policy.jsp",
		"path=/edit_password_policy_assignments.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DeletePasswordPolicyPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/configuration/icon/delete_password_policy.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "delete");
	}

	@Override
	public double getWeight() {
		return 101;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long passwordPolicyId = _getPasswordPolicyId(portletRequest);

		PasswordPolicy passwordPolicy =
			_passwordPolicyLocalService.fetchPasswordPolicy(passwordPolicyId);

		if ((passwordPolicy != null) && !passwordPolicy.isDefaultPolicy() &&
			_passwordPolicyPermission.contains(
				themeDisplay.getPermissionChecker(), passwordPolicyId,
				ActionKeys.DELETE)) {

			return true;
		}

		return false;
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	private long _getPasswordPolicyId(PortletRequest portletRequest) {
		return ParamUtil.getLong(
			_portal.getHttpServletRequest(portletRequest), "passwordPolicyId");
	}

	@Reference
	private Language _language;

	@Reference
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Reference
	private PasswordPolicyPermission _passwordPolicyPermission;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.password.policies.admin.web)"
	)
	private ServletContext _servletContext;

}