/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.marketplace.settings.web.internal.configuration.MarketplaceConfiguration;
import com.liferay.marketplace.settings.web.internal.configuration.MarketplaceConfigurationUtil;
import com.liferay.marketplace.settings.web.internal.http.MarketplaceHttp;
import com.liferay.marketplace.settings.web.internal.model.Authorization;
import com.liferay.marketplace.settings.web.internal.model.Payload;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Keven Leone
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/marketplace_settings/get_authorization"
	},
	service = MVCResourceCommand.class
)
public class GetAuthorizationMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		MarketplaceConfiguration marketplaceConfiguration =
			MarketplaceConfigurationUtil.getMarketplaceConfiguration(
				themeDisplay.getCompanyId());

		if (Validator.isNull(marketplaceConfiguration.accessToken()) ||
			Validator.isNull(marketplaceConfiguration.url())) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONObject(
				).put(
					"authorized", false
				));

			return;
		}

		Authorization authorization = new Authorization(
			marketplaceConfiguration.accessToken(),
			marketplaceConfiguration.serviceURL(),
			marketplaceConfiguration.settings(),
			marketplaceConfiguration.url());

		if (System.currentTimeMillis() > marketplaceConfiguration.expiresIn()) {
			Payload payload = new Payload(
				marketplaceConfiguration.clientId(),
				marketplaceConfiguration.code(), null,
				marketplaceConfiguration.redirect(),
				marketplaceConfiguration.serviceURL(),
				marketplaceConfiguration.settings(),
				marketplaceConfiguration.url());

			authorization = _marketplaceHttp.exchangeToken(
				themeDisplay.getCompanyId(), payload,
				marketplaceConfiguration.refreshToken());
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_jsonFactory.createJSONObject(
			).put(
				"authorized", true
			).put(
				"data", authorization.toJSONObject()
			));
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MarketplaceHttp _marketplaceHttp;

}