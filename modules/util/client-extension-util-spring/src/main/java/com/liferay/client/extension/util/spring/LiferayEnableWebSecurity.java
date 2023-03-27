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

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@Configuration
@EnableWebSecurity
public class LiferayEnableWebSecurity {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
			new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedHeaders(
			Arrays.asList("Authorization", "Content-Type"));
		corsConfiguration.setAllowedMethods(
			Arrays.asList(
				"DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
		corsConfiguration.setAllowedOrigins(_getAllowedOrigins());

		urlBasedCorsConfigurationSource.registerCorsConfiguration(
			"/**", corsConfiguration);

		return urlBasedCorsConfigurationSource;
	}

	@Bean
	public JwtDecoder jwtDecoder() throws Exception {
		DefaultJWTProcessor<SecurityContext> defaultJWTProcessor =
			new DefaultJWTProcessor<>();

		defaultJWTProcessor.setJWSKeySelector(
			JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(
				new URL(
					_serverProtocol + "://" + _mainDomain + "/o/oauth2/jwks")));
		defaultJWTProcessor.setJWSTypeVerifier(
			new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("at+jwt")));

		NimbusJwtDecoder nimbusJwtDecoder = new NimbusJwtDecoder(
			defaultJWTProcessor);

		String liferayOauthApplicationExternalReferenceCode =
			_environment.getProperty(
				"liferay.oauth.application.external.reference.code");

		String[] oauthProfiles =
			liferayOauthApplicationExternalReferenceCode.split(",");

		Set<String> validClientIds = Arrays.stream(
			oauthProfiles
		).map(
			this::_getLiferayOAuthClientId
		).collect(
			Collectors.toSet()
		);

		if (_log.isInfoEnabled()) {
			validClientIds.forEach(id -> _log.info("Using client ID " + id));
		}

		nimbusJwtDecoder.setJwtValidator(
			new DelegatingOAuth2TokenValidator<>(
				new ClientIdOAuth2TokenValidator(validClientIds)));

		return nimbusJwtDecoder;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
		throws Exception {

		return httpSecurity.cors(
		).and(
		).csrf(
		).disable(
		).sessionManagement(
		).sessionCreationPolicy(
			SessionCreationPolicy.STATELESS
		).and(
		).authorizeHttpRequests(
			customizer -> customizer.antMatchers(
				"/"
			).permitAll(
			).anyRequest(
			).authenticated()
		).oauth2ResourceServer(
			OAuth2ResourceServerConfigurer::jwt
		).build();
	}

	private List<String> _getAllowedOrigins() {
		List<String> allowedOrigins = new ArrayList<>();

		for (String dxpDomain : _dxpDomains.split("\\s*[,\n]\\s*")) {
			allowedOrigins.add("http://" + dxpDomain);
			allowedOrigins.add("https://" + dxpDomain);
		}

		return allowedOrigins;
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
		LiferayEnableWebSecurity.class);

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

	private class ClientIdOAuth2TokenValidator
		implements OAuth2TokenValidator<Jwt> {

		@Override
		public OAuth2TokenValidatorResult validate(Jwt jwt) {
			String clientId = jwt.getClaimAsString("client_id");

			if (_validClientIds.contains(clientId)) {
				return OAuth2TokenValidatorResult.success();
			}

			return OAuth2TokenValidatorResult.failure(_oAuth2Error);
		}

		private ClientIdOAuth2TokenValidator(Set<String> validClientIds) {
			_validClientIds = validClientIds;
		}

		private final OAuth2Error _oAuth2Error = new OAuth2Error(
			"invalid_token", "The client_id does not match", null);
		private final Set<String> _validClientIds;

	}

}