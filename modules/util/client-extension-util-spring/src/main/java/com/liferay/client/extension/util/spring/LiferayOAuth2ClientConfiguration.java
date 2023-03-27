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

package com.liferay.client.extension.util.spring;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
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

		OAuth2AuthorizedClientProvider oAuth2AuthorizedClientProvider =
			OAuth2AuthorizedClientProviderBuilder.builder(
			).clientCredentials(
			).build();

		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager =
				new AuthorizedClientServiceOAuth2AuthorizedClientManager(
					clientRegistrationRepository,
					oAuth2AuthorizedClientService);

		authorizedClientServiceOAuth2AuthorizedClientManager.
			setAuthorizedClientProvider(oAuth2AuthorizedClientProvider);

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

		ServerOAuth2AuthorizedClientExchangeFilterFunction
			serverOAuth2AuthorizedClientExchangeFilterFunction =
				new ServerOAuth2AuthorizedClientExchangeFilterFunction(
					new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
						reactiveClientRegistrationRepository,
						new InMemoryReactiveOAuth2AuthorizedClientService(
							reactiveClientRegistrationRepository)));

		return WebClient.builder(
		).filter(
			serverOAuth2AuthorizedClientExchangeFilterFunction
		).build();
	}

	private ClientRegistration[] _getClientRegistrations() {
		String liferayOauthApplicationExternalReferenceCode =
			_environment.getProperty(
				"liferay.oauth.application.external.reference.code");

		String[] oauthProfiles =
			liferayOauthApplicationExternalReferenceCode.split(",");

		return Arrays.stream(
			oauthProfiles
		).map(
			oauthProfile -> {
				String clientSecret = _environment.getProperty(
					oauthProfile + ".oauth2.headless.server.client.secret");

				if (clientSecret == null) {
					return null;
				}

				String tokenUri = _environment.getProperty(
					oauthProfile + ".oauth2.token.uri", "/o/oauth2/token");

				return ClientRegistration.withRegistrationId(
					oauthProfile
				).tokenUri(
					_serverProtocol + "://" + _mainDomain + tokenUri
				).clientId(
					_getLiferayOAuthClientId(oauthProfile)
				).clientSecret(
					clientSecret
				).authorizationGrantType(
					AuthorizationGrantType.CLIENT_CREDENTIALS
				).clientAuthenticationMethod(
					ClientAuthenticationMethod.CLIENT_SECRET_POST
				).build();
			}
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		).toArray(
			new ClientRegistration[0]
		);
	}

	private String _getLiferayOAuthClientId(String externalReferenceCode) {
		while (true) {
			try {
				return WebClient.create(
					_serverProtocol + "://" + _mainDomain +
						"/o/oauth2/application"
				).get(
				).uri(
					uriBuilder -> uriBuilder.queryParam(
						"externalReferenceCode", externalReferenceCode
					).build()
				).retrieve(
				).bodyToMono(
					ApplicationInfo.class
				).block(
				).client_id;
			}
			catch (Throwable throwable) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get client ID: " + throwable.getMessage());
				}

				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException interruptedException) {
					_log.error(
						"Thread.sleep interupted: " +
							interruptedException.getMessage());
				}
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		LiferayOAuth2ClientConfiguration.class);

	@Value("${com.liferay.lxc.dxp.domains}")
	private String _dxpDomains;

	@Autowired
	private Environment _environment;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _mainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _serverProtocol;

	private static class ApplicationInfo {

		public String client_id;

	}

}