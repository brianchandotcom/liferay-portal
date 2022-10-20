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

package com.liferay.oauth2.provider.internal.configuration;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationHeadlessServerConfiguration;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.osgi.util.factory.FactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

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
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "_portalK8sConfigMapModifier.cardinality.minimum=1", service = {}
)
public class OAuth2ProviderApplicationHeadlessServerConfigurationFactory
	extends BaseConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Activate " + properties);
		}

		long companyId = FactoryUtil.getCompanyId(
			properties, companyLocalService);

		String externalReferenceCode = FactoryUtil.getExternalReferenceCode(
			properties);

		OAuth2ProviderApplicationHeadlessServerConfiguration
			oAuth2ProviderApplicationHeadlessServerConfiguration =
				ConfigurableUtil.createConfigurable(
					OAuth2ProviderApplicationHeadlessServerConfiguration.class,
					properties);

		List<String> scopeAliasesList = ListUtil.fromArray(
			oAuth2ProviderApplicationHeadlessServerConfiguration.scopes());

		oAuth2Application = _addOrUpdateOAuth2Application(
			companyId, externalReferenceCode,
			oAuth2ProviderApplicationHeadlessServerConfiguration,
			scopeAliasesList);

		if (_log.isDebugEnabled()) {
			_log.debug("OAuth 2 application " + oAuth2Application);
		}

		Company company = companyLocalService.getCompanyById(companyId);

		String serviceAddress = getServiceAddress(company);

		modifyConfigMap(
			company,
			HashMapBuilder.put(
				externalReferenceCode + ".oauth2.authorization.uri",
				serviceAddress.concat("/o/oauth2/authorize")
			).put(
				externalReferenceCode + ".oauth2.headless.server.client.id",
				oAuth2Application.getClientId()
			).put(
				externalReferenceCode + ".oauth2.headless.server.client.secret",
				oAuth2Application.getClientSecret()
			).put(
				externalReferenceCode + ".oauth2.headless.server.scopes",
				StringUtil.merge(scopeAliasesList, StringPool.NEW_LINE)
			).put(
				externalReferenceCode + ".oauth2.introspection.uri",
				serviceAddress.concat("/o/oauth2/introspect")
			).put(
				externalReferenceCode + ".oauth2.token.uri",
				serviceAddress.concat("/o/oauth2/token")
			).build(),
			properties);
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

		String userAccountEmailAddress =
			oAuth2ProviderApplicationHeadlessServerConfiguration.
				userAccountEmailAddress();

		if (!Objects.equals(
				_COMPANY_DEFAULT_USER_TOKEN, userAccountEmailAddress) &&
			!Validator.isEmailAddress(userAccountEmailAddress)) {

			throw new IllegalArgumentException(
				"User account email address must be a valid email address or " +
					_COMPANY_DEFAULT_USER_TOKEN);
		}

		User user = userLocalService.getDefaultUser(companyId);

		User serviceUser = null;

		if (Objects.equals(
				_COMPANY_DEFAULT_USER_TOKEN, userAccountEmailAddress)) {

			serviceUser = user;
		}
		else {
			serviceUser = userLocalService.getUserByEmailAddress(
				companyId, userAccountEmailAddress);
		}

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
				externalReferenceCode, user.getUserId(), user.getScreenName(),
				ListUtil.fromArray(
					GrantType.CLIENT_CREDENTIALS, GrantType.JWT_BEARER),
				"client_secret_post", serviceUser.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.HEADLESS_SERVER.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(),
				oAuth2ProviderApplicationHeadlessServerConfiguration.
					description(),
				Arrays.asList("token.introspection"),
				oAuth2ProviderApplicationHeadlessServerConfiguration.
					homePageURL(),
				0, null, externalReferenceCode,
				oAuth2ProviderApplicationHeadlessServerConfiguration.
					privacyPolicyURL(),
				Collections.emptyList(), false, true, null,
				new ServiceContext());

		oAuth2Application = oAuth2ApplicationLocalService.updateScopeAliases(
			oAuth2Application.getUserId(), oAuth2Application.getUserName(),
			oAuth2Application.getOAuth2ApplicationId(), scopeAliasesList);

		Class<?> clazz = getClass();

		return oAuth2ApplicationLocalService.updateIcon(
			oAuth2Application.getOAuth2ApplicationId(),
			clazz.getResourceAsStream("dependencies/logo.png"));
	}

	private static final String _COMPANY_DEFAULT_USER_TOKEN =
		"<company.default.user>";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ProviderApplicationHeadlessServerConfigurationFactory.class);

}