/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
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
		"mvc.command.name=/marketplace_settings/get_configuration"
	},
	service = MVCResourceCommand.class
)
public class GetConfigurationMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean authorized = Validator.isNotNull(
			PrefsPropsUtil.getString(
				themeDisplay.getCompanyId(),
				PropsKeys.MARKETPLACE_ACCESS_TOKEN));

		JSONObject jsonObject = _jsonFactory.createJSONObject(
		).put(
			"authorized", authorized
		);

		if (authorized) {
			jsonObject.put(
				"data",
				_jsonFactory.createJSONObject(
				).put(
					"serviceURL",
					PrefsPropsUtil.getString(
						themeDisplay.getCompanyId(),
						PropsKeys.MARKETPLACE_SERVICE_URL)
				).put(
					"settings",
					_jsonFactory.createJSONObject(
						PrefsPropsUtil.getString(
							themeDisplay.getCompanyId(),
							PropsKeys.MARKETPLACE_SETTINGS))
				).put(
					"url", PrefsPropsUtil.getString(PropsKeys.MARKETPLACE_URL)
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private JSONFactory _jsonFactory;

}