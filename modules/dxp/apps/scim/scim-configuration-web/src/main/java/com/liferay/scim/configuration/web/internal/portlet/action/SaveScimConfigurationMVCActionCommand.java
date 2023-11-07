/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.scim.configuration.web.internal.constants.ScimConstants;
import com.liferay.scim.configuration.web.internal.constants.ScimWebKeys;
import com.liferay.scim.rest.util.ScimClientUtil;

import java.util.Dictionary;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/scim_configuration/save_scim_configuration"
	},
	service = MVCActionCommand.class
)
public class SaveScimConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String clientId = ScimClientUtil.generateScimClientId(
			ParamUtil.getString(
				actionRequest, ScimConstants.PARAM_APPLICATION_NAME));

		if (ScimWebKeys.SCIM_GENERATE.equals(cmd)) {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.getOAuth2Application(
					themeDisplay.getCompanyId(), clientId);

			String tokens = _localOAuthClient.requestTokens(
				oAuth2Application, oAuth2Application.getUserId());

			JSONObject jsonObject = _jsonFactory.createJSONObject(tokens);

			String accessToken = jsonObject.getString("access_token");

			actionRequest.setAttribute(ScimConstants.PARAM_TOKEN, accessToken);
		}
		else if (ScimWebKeys.SCIM_REVOKE.equals(cmd)) {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.getOAuth2Application(
					themeDisplay.getCompanyId(), clientId);

			_oAuth2AuthorizationService.revokeAllOAuth2Authorizations(
				oAuth2Application.getOAuth2ApplicationId());
		}
		else {
			String filterString = StringBundler.concat(
				"(&(service.factoryPid=", ScimConstants.CONFIGURATION_PID, ")(",
				ScimConstants.PARAM_COMPANY_ID, "=",
				themeDisplay.getCompanyId(), "))");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				Configuration configuration = configurations[0];

				Dictionary<String, Object> properties =
					configuration.getProperties();

				properties.put(
					ScimConstants.PARAM_APPLICATION_NAME,
					ParamUtil.getString(
						actionRequest, ScimConstants.PARAM_APPLICATION_NAME));

				properties.put(
					ScimConstants.PARAM_MATCHER_FIELD,
					ParamUtil.getString(
						actionRequest, ScimConstants.PARAM_MATCHER_FIELD));

				configuration.update(properties);
			}
			else {
				Configuration configuration =
					_configurationAdmin.createFactoryConfiguration(
						ScimConstants.CONFIGURATION_PID, StringPool.QUESTION);

				configuration.update(
					HashMapDictionaryBuilder.<String, Object>put(
						ScimConstants.PARAM_APPLICATION_NAME,
						ParamUtil.getString(
							actionRequest, ScimConstants.PARAM_APPLICATION_NAME)
					).put(
						ScimConstants.PARAM_MATCHER_FIELD,
						ParamUtil.getString(
							actionRequest, ScimConstants.PARAM_MATCHER_FIELD)
					).put(
						"companyId", themeDisplay.getCompanyId()
					).build());
			}
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LocalOAuthClient _localOAuthClient;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference
	private OAuth2AuthorizationService _oAuth2AuthorizationService;

}