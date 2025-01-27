/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author Gregory Amerson
 */
@Configuration
public class LiferayOAuth2ClientConfigurationExtra {

	@Bean
	@Qualifier("extra")
	public InMemoryClientRegistrationRepository
		inMemoryClientRegistrationRepository() {

		return new InMemoryClientRegistrationRepository(
			_extraClientRegistration);
	}

	@Bean
	@Qualifier("extra")
	public InMemoryReactiveClientRegistrationRepository
		inMemoryReactiveClientRegistrationRepository() {

		return new InMemoryReactiveClientRegistrationRepository(
			_extraClientRegistration);
	}

	private final ClientRegistration _extraClientRegistration =
		ClientRegistration.withRegistrationId(
			"extra"
		).authorizationGrantType(
			AuthorizationGrantType.CLIENT_CREDENTIALS
		).clientId(
			"extra"
		).clientSecret(
			"extra"
		).scope(
			"extra"
		).tokenUri(
			"extra"
		).build();

}