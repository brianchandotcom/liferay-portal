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

		String scopeName = ParamUtil.getString(
			resourceRequest, "scope", "SYSTEM");

		ExtendedObjectClassDefinition.Scope scope;
		long scopePK;

		if (scopeName.equals("COMPANY")) {
			scope = ExtendedObjectClassDefinition.Scope.COMPANY;
			scopePK = themeDisplay.getCompanyId();
		}
		else if (scopeName.equals("GROUP")) {
			scope = ExtendedObjectClassDefinition.Scope.GROUP;
			scopePK = themeDisplay.getScopeGroupId();
		}
		else {
			scope = ExtendedObjectClassDefinition.Scope.SYSTEM;
			scopePK = 0L;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (((scope == ExtendedObjectClassDefinition.Scope.SYSTEM) &&
			 !permissionChecker.isOmniadmin()) ||
			((scope == ExtendedObjectClassDefinition.Scope.COMPANY) &&
			 !permissionChecker.isCompanyAdmin()) ||
			((scope == ExtendedObjectClassDefinition.Scope.GROUP) &&
			 !permissionChecker.isGroupAdmin(scopePK))) {

			resourceResponse.setProperty(
				ResourceResponse.HTTP_STATUS_CODE, "403");

			return;
		}

		_cookiesConfigurationProvider.forceCookiesPreferenceHandlingReconsent(
			scope, scopePK);
	}

	@Reference
	private CookiesConfigurationProvider _cookiesConfigurationProvider;

}