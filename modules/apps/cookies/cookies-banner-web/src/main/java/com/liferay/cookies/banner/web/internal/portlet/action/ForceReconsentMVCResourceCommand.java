/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"jakarta.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"jakarta.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/cookies_banner/force_reconsent"
	},
	service = MVCResourceCommand.class
)
public class ForceReconsentMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		String scopeName = ParamUtil.getString(
			resourceRequest, "scope",
			ExtendedObjectClassDefinition.Scope.SYSTEM.getValue());

		ExtendedObjectClassDefinition.Scope scope =
			ExtendedObjectClassDefinition.Scope.SYSTEM;
		long scopePK = 0L;

		if (scopeName.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue()) &&
			permissionChecker.isCompanyAdmin()) {

			scope = ExtendedObjectClassDefinition.Scope.COMPANY;
			scopePK = themeDisplay.getCompanyId();
		}
		else if (scopeName.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue()) &&
				 permissionChecker.isGroupAdmin(
					 themeDisplay.getScopeGroupId())) {

			scope = ExtendedObjectClassDefinition.Scope.GROUP;
			scopePK = themeDisplay.getScopeGroupId();
		}
		else if (!permissionChecker.isOmniadmin()) {
			resourceResponse.setProperty(
				ResourceResponse.HTTP_STATUS_CODE,
				String.valueOf(HttpServletResponse.SC_FORBIDDEN));

			return;
		}

		_cookiesConfigurationProvider.forceCookiesPreferenceHandlingReconsent(
			scope, scopePK);
	}

	@Reference
	private CookiesConfigurationProvider _cookiesConfigurationProvider;

}