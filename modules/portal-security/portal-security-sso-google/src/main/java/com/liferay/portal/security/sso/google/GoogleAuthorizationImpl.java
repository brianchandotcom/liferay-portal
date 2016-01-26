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

package com.liferay.portal.security.sso.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.google.configuration.GoogleAuthorizationConfiguration;
import com.liferay.portal.security.sso.google.constants.GoogleConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Serves as the core implementation of the Google protocol.
 *
 * <p>
 * This class is utilized by many other classes via {@link
 * com.liferay.portal.util.GoogleUtil} which exposes all of its methods
 * statically.
 * </p>
 *
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.google.configuration.GoogleAuthorizationConfiguration",
	immediate = true, service = GoogleAuthorization.class
)
public class GoogleAuthorizationImpl implements GoogleAuthorization {

	public GoogleAuthorizationCodeFlow getGoogleAuthorizationCodeFlow(
			long companyId, List<String> scopes)
		throws Exception {

		GoogleAuthorizationConfiguration googleConfiguration =
			getGoogleConfiguration(companyId);

		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow.Builder builder =
			new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, googleConfiguration.clientId(),
				googleConfiguration.clientSecret(), scopes);

		String accessType = "online";

		builder.setAccessType(accessType);

		return builder.build();
	}

	@Override
	public boolean isEnabled(long companyId) {
		GoogleAuthorizationConfiguration googleConfiguration =
			getGoogleConfiguration(companyId);

		if (Validator.isNull(googleConfiguration.clientId()) ||
			Validator.isNull(googleConfiguration.clientSecret())) {

			return false;
		}

		return googleConfiguration.enabled();
	}

	protected GoogleAuthorizationConfiguration getGoogleConfiguration(
		long companyId) {

		try {
			GoogleAuthorizationConfiguration googleConfiguration =
				_configurationFactory.getConfiguration(
					GoogleAuthorizationConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, GoogleConstants.SERVICE_NAME));

			return googleConfiguration;
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get Google configuration", ce);
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GoogleAuthorizationImpl.class);

	private ConfigurationFactory _configurationFactory;

}