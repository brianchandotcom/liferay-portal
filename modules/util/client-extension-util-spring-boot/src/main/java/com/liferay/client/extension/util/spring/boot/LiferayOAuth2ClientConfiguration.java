/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Gregory Amerson
 */
@Configuration
public class LiferayOAuth2ClientConfiguration {

	@Bean
	public AuthorizedClientServiceOAuth2AuthorizedClientManager
		authorizedClientServiceOAuth2AuthorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {

		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager =
				new AuthorizedClientServiceOAuth2AuthorizedClientManager(
					clientRegistrationRepository,
					oAuth2AuthorizedClientService);

		authorizedClientServiceOAuth2AuthorizedClientManager.
			setAuthorizedClientProvider(
				OAuth2AuthorizedClientProviderBuilder.builder(
				).clientCredentials(
				).build());

		return authorizedClientServiceOAuth2AuthorizedClientManager;
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		ClientRegistration[] clientRegistrations = _getClientRegistrations();

		if (clientRegistrations.length > 0) {
			return new InMemoryClientRegistrationRepository(
				clientRegistrations);
		}

		return new InMemoryClientRegistrationRepository(Collections.emptyMap());
	}

	@Bean
	public ReactiveClientRegistrationRepository clientRegistrations() {
		ClientRegistration[] clientRegistrations = _getClientRegistrations();

		if (clientRegistrations.length > 0) {
			return new InMemoryReactiveClientRegistrationRepository(
				clientRegistrations);
		}

		return new ReactiveClientRegistrationRepository() {

			@Override
			public Mono<ClientRegistration> findByRegistrationId(
				String registrationId) {

				return Mono.empty();
			}

		};
	}

	@Bean
	public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(
		ClientRegistrationRepository clientRegistrationRepository) {

		return new InMemoryOAuth2AuthorizedClientService(
			clientRegistrationRepository);
	}

	@Bean
	public WebClient webClient(
		ReactiveClientRegistrationRepository
			reactiveClientRegistrationRepository) {

		return WebClient.builder(
		).filter(
			new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
					reactiveClientRegistrationRepository,
					new InMemoryReactiveOAuth2AuthorizedClientService(
						reactiveClientRegistrationRepository)))
		).build();
	}

	private ClientRegistration[] _getClientRegistrations() {
		String liferayOauthApplicationExternalReferenceCodes =
			_environment.getProperty(
				"liferay.oauth.application.external.reference.codes");

		if (liferayOauthApplicationExternalReferenceCodes == null) {
			throw new IllegalArgumentException(
				"Property " +
					"\"liferay.oauth.application.external.reference.codes\" " +
						"is not defined");
		}

		List<ClientRegistration> clientRegistrations = new ArrayList<>();

		for (String externalReferenceCode :
				liferayOauthApplicationExternalReferenceCodes.split(",")) {

			String clientId = _environment.getProperty(
				externalReferenceCode + ".oauth2.headless.server.client.id");

			if (clientId == null) {
				clientId = LiferayOAuth2Util.getClientId(
					externalReferenceCode, _lxcDXPMainDomain,
					_lxcDXPServerProtocol);
			}

			if (clientId == null) {
				continue;
			}

			String clientSecret = _environment.getProperty(
				externalReferenceCode +
					".oauth2.headless.server.client.secret");

			if (clientSecret == null) {
				continue;
			}

			String tokenURI = _environment.getProperty(
				externalReferenceCode + ".oauth2.token.uri", "/o/oauth2/token");

			ClientRegistration clientRegistration =
				ClientRegistration.withRegistrationId(
					externalReferenceCode
				).tokenUri(
					_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain + tokenURI
				).clientId(
					clientId
				).clientSecret(
					clientSecret
				).authorizationGrantType(
					AuthorizationGrantType.CLIENT_CREDENTIALS
				).clientAuthenticationMethod(
					ClientAuthenticationMethod.CLIENT_SECRET_POST
				).build();

			clientRegistrations.add(clientRegistration);
		}

		return clientRegistrations.toArray(new ClientRegistration[0]);
	}

	@Autowired
	private Environment _environment;

	@Value("${com.liferay.lxc.dxp.domains}")
	private String _lxcDXPDomains;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}