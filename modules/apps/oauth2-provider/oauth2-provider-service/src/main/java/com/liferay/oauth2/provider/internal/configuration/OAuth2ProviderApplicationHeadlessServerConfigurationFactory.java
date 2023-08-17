/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.internal.configuration;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "_portalK8sConfigMapModifier.cardinality.minimum=1", service = {}
)
public class OAuth2ProviderApplicationHeadlessServerConfigurationFactory
	extends BaseConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Activate " + properties);
		}

		ConfigurationFactoryUtil.executeAsCompany(
			companyLocalService, properties,
			companyId -> {
				String externalReferenceCode =
					ConfigurationFactoryUtil.getExternalReferenceCode(
						properties);

				OAuth2ProviderApplicationHeadlessServerConfiguration
					oAuth2ProviderApplicationHeadlessServerConfiguration =
						ConfigurableUtil.createConfigurable(
							OAuth2ProviderApplicationHeadlessServerConfiguration.class,
							properties);

				List<String> scopeAliasesList = ListUtil.fromArray(
					oAuth2ProviderApplicationHeadlessServerConfiguration.
						scopes());

				oAuth2Application = _addOrUpdateOAuth2Application(
					companyId, externalReferenceCode,
					oAuth2ProviderApplicationHeadlessServerConfiguration,
					scopeAliasesList);

				if (_log.isDebugEnabled()) {
					_log.debug("OAuth 2 application " + oAuth2Application);
				}

				modifyConfigMap(
					companyLocalService.getCompanyById(companyId),
					HashMapBuilder.put(
						externalReferenceCode +
							".oauth2.headless.server.audience",
						oAuth2Application.getHomePageURL()
					).put(
						externalReferenceCode +
							".oauth2.headless.server.client.id",
						oAuth2Application.getClientId()
					).put(
						externalReferenceCode +
							".oauth2.headless.server.client.secret",
						oAuth2Application.getClientSecret()
					).put(
						externalReferenceCode +
							".oauth2.headless.server.scopes",
						StringUtil.merge(scopeAliasesList, StringPool.NEW_LINE)
					).put(
						externalReferenceCode + ".oauth2.authorization.uri",
						"/o/oauth2/authorize"
					).put(
						externalReferenceCode + ".oauth2.introspection.uri",
						"/o/oauth2/introspect"
					).put(
						externalReferenceCode + ".oauth2.jwks.uri",
						"/o/oauth2/jwks"
					).put(
						externalReferenceCode + ".oauth2.redirect.uris",
						"/o/oauth2/redirect"
					).put(
						externalReferenceCode + ".oauth2.token.uri",
						"/o/oauth2/token"
					).build(),
					properties);
			});
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private OAuth2Application _addOrUpdateOAuth2Application(
			long companyId, String externalReferenceCode,
			OAuth2ProviderApplicationHeadlessServerConfiguration
				oAuth2ProviderApplicationHeadlessServerConfiguration,
			List<String> scopeAliasesList)
		throws Exception {

		User user = userLocalService.getGuestUser(companyId);

		User serviceUser = _getServiceUser(
			companyId, oAuth2ProviderApplicationHeadlessServerConfiguration);

		String clientId = OAuth2SecureRandomGenerator.generateClientId();
		String clientSecret =
			OAuth2SecureRandomGenerator.generateClientSecret();

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (oAuth2Application != null) {
			clientId = oAuth2Application.getClientId();
			clientSecret = oAuth2Application.getClientSecret();
		}

		String homePageURL = getHomePageURL(
			oAuth2ProviderApplicationHeadlessServerConfiguration.homePageURL(),
			oAuth2ProviderApplicationHeadlessServerConfiguration.baseURL());

		oAuth2Application =
			oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
				externalReferenceCode, user.getUserId(), user.getScreenName(),
				ListUtil.fromArray(
					GrantType.CLIENT_CREDENTIALS, GrantType.JWT_BEARER),
				"client_secret_post", serviceUser.getUserId(), clientId,
				ClientProfile.HEADLESS_SERVER.id(), clientSecret,
				oAuth2ProviderApplicationHeadlessServerConfiguration.
					description(),
				Arrays.asList("token.introspection"), homePageURL, 0, null,
				getName(
					oAuth2ProviderApplicationHeadlessServerConfiguration.name(),
					externalReferenceCode),
				oAuth2ProviderApplicationHeadlessServerConfiguration.
					privacyPolicyURL(),
				Collections.emptyList(), false, true, null,
				new ServiceContext());

		oAuth2Application = oAuth2ApplicationLocalService.updateScopeAliases(
			oAuth2Application.getUserId(), oAuth2Application.getUserName(),
			oAuth2Application.getOAuth2ApplicationId(), scopeAliasesList);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"OAuth 2 application with external reference code ",
					oAuth2Application.getExternalReferenceCode(),
					" and company ID ", oAuth2Application.getCompanyId(),
					" has client ID ", oAuth2Application.getClientId()));
		}

		return oAuth2Application;
	}

	private User _getServiceUser(
			long companyId,
			OAuth2ProviderApplicationHeadlessServerConfiguration
				oAuth2ProviderApplicationHeadlessServerConfiguration)
		throws Exception {

		String userAccountEmailAddress =
			oAuth2ProviderApplicationHeadlessServerConfiguration.
				userAccountEmailAddress();
		String userAccountScreenName =
			oAuth2ProviderApplicationHeadlessServerConfiguration.
				userAccountScreenName();

		if (!Objects.equals(
				_COMPANY_DEFAULT_USER_TOKEN, userAccountEmailAddress) &&
			Objects.equals(
				_COMPANY_DEFAULT_USER_TOKEN, userAccountScreenName)) {

			if (!Validator.isEmailAddress(userAccountEmailAddress)) {
				throw new IllegalArgumentException(
					"User account email address is not an email address");
			}

			return userLocalService.getUserByEmailAddress(
				companyId, userAccountEmailAddress);
		}

		if (Validator.isNull(userAccountScreenName)) {
			throw new IllegalArgumentException(
				"User account screen name is null");
		}

		if (Objects.equals(
				_COMPANY_DEFAULT_USER_TOKEN, userAccountScreenName)) {

			return userLocalService.getUserByScreenName(
				companyId, PropsValues.DEFAULT_ADMIN_SCREEN_NAME);
		}

		return userLocalService.getUserByScreenName(
			companyId, userAccountScreenName);
	}

	private static final String _COMPANY_DEFAULT_USER_TOKEN =
		"<company.default.user>";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ProviderApplicationHeadlessServerConfigurationFactory.class);

}