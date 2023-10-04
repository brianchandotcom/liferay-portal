/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.client.internal.configuration;

import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.scim.client.configuration.SCIMClientOAuth2ApplicationConfiguration;
import com.liferay.scim.client.util.SCIMClientUtil;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	configurationPid = "com.liferay.scim.client.configuration.SCIMClientOAuth2ApplicationConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class SCIMClientOAuth2ApplicationConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-96845")) {
			return;
		}

		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, properties,
			companyId -> {
				SCIMClientOAuth2ApplicationConfiguration
					scimClientOAuth2ApplicationConfiguration =
						ConfigurableUtil.createConfigurable(
							SCIMClientOAuth2ApplicationConfiguration.class,
							properties);

				_oAuth2Application = _getOrAddOAuth2Application(
					companyId, scimClientOAuth2ApplicationConfiguration);

				JSONObject jsonObject = _jsonFactory.createJSONObject(
					_localOAuthClient.requestTokens(
						_oAuth2Application,
						userLocalService.getGuestUser(
							companyId
						).getUserId()));

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"New access token for SCIM OAuth 2 application ",
							_oAuth2Application.getName(), " generated: ",
							jsonObject.getString("access_token")));
				}
			});
	}

	@Deactivate
	protected void deactivate(Integer reason) throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-96845") ||
			(reason !=
				ComponentConstants.DEACTIVATION_REASON_CONFIGURATION_DELETED)) {

			return;
		}

		_oAuth2ApplicationLocalService.deleteOAuth2Application(
			_oAuth2Application);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Removed OAuth2 application: " + _oAuth2Application.getName());
		}
	}

	@Reference
	protected UserLocalService userLocalService;

	private OAuth2Application _getOrAddOAuth2Application(
			long companyId,
			SCIMClientOAuth2ApplicationConfiguration
				scimClientOAuth2ApplicationConfiguration)
		throws Exception {

		User user = userLocalService.getGuestUser(companyId);

		User clientCredentialUser = userLocalService.getUserByScreenName(
			companyId, PropsValues.DEFAULT_ADMIN_SCREEN_NAME);

		String clientId = SCIMClientUtil.generateSCIMClientId(
			scimClientOAuth2ApplicationConfiguration.applicationName());

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				companyId, clientId);

		if (oAuth2Application == null) {
			oAuth2Application =
				_oAuth2ApplicationLocalService.addOAuth2Application(
					companyId, user.getUserId(), user.getScreenName(),
					ListUtil.fromArray(GrantType.JWT_BEARER),
					"client_secret_post", clientCredentialUser.getUserId(),
					clientId, ClientProfile.HEADLESS_SERVER.id(),
					OAuth2SecureRandomGenerator.generateClientSecret(), null,
					Collections.emptyList(), null, 0, null,
					scimClientOAuth2ApplicationConfiguration.applicationName(),
					null, Collections.emptyList(), false, true, null,
					new ServiceContext());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Created OAuth2 application: " +
						oAuth2Application.getName());
			}
		}

		return _oAuth2ApplicationLocalService.updateScopeAliases(
			oAuth2Application.getUserId(), oAuth2Application.getUserName(),
			oAuth2Application.getOAuth2ApplicationId(),
			ListUtil.fromArray("Liferay.SCIM.Application.everything"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SCIMClientOAuth2ApplicationConfigurationFactory.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LocalOAuthClient _localOAuthClient;

	private volatile OAuth2Application _oAuth2Application;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}