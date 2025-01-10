/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.marketplace.settings.web.internal.http.MarketplaceHttp;
import com.liferay.marketplace.settings.web.internal.model.Authorization;
import com.liferay.marketplace.settings.web.internal.model.Payload;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Keven Leone
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/marketplace_settings/connect"
	},
	service = MVCResourceCommand.class
)
public class ConnectMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeCompanyAdmin(permissionChecker);
		}

		Authorization authorization = MarketplaceHttp.exchangeToken(
			themeDisplay.getCompanyId(),
			new Payload(
				ParamUtil.getString(resourceRequest, "clientId"),
				ParamUtil.getString(resourceRequest, "code"),
				ParamUtil.getString(resourceRequest, "codeVerifier"),
				ParamUtil.getString(resourceRequest, "redirect"),
				ParamUtil.getString(resourceRequest, "serviceURL"),
				ParamUtil.getString(resourceRequest, "settings"),
				ParamUtil.getString(resourceRequest, "url")),
			null);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, authorization.toJSONObject());
	}

}