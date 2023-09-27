/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 * @author Allen Ziegenfus
 */
@Configuration
@EnableWebSecurity
public class LiferayOAuth2ResourceServerEnableWebSecurity {

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
		String liferayOauthApplicationExternalReferenceCodes =
			_environment.getProperty(
				"liferay.oauth.application.external.reference.codes");

		if (liferayOauthApplicationExternalReferenceCodes == null) {
			throw new IllegalArgumentException(
				"Property " +
					"\"liferay.oauth.application.external.reference.codes\" " +
						"is not defined");
		}

		DefaultJWTProcessor<SecurityContext> defaultJWTProcessor =
			new DefaultJWTProcessor<>();

		URL jwkSetURL = new URL(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain +
				"/o/oauth2/jwks");

		if (_log.isDebugEnabled()) {
			_log.debug("Using " + jwkSetURL);
		}

		defaultJWTProcessor.setJWSKeySelector(
			JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(jwkSetURL));

		defaultJWTProcessor.setJWSTypeVerifier(
			new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("at+jwt")));

		NimbusJwtDecoder nimbusJwtDecoder = new NimbusJwtDecoder(
			defaultJWTProcessor);

		Set<String> clientIds = new HashSet<>();

		for (String externalReferenceCode :
				liferayOauthApplicationExternalReferenceCodes.split(",")) {

			String clientId = _environment.getProperty(
				externalReferenceCode + ".oauth2.user.agent.client.id");

			long timeout = TimeUnit.MINUTES.toMillis(5);

			while ((clientId == null) && (timeout > 0)) {
				clientId = LiferayOAuth2Util.getClientId(
					externalReferenceCode, _lxcDXPMainDomain,
					_lxcDXPServerProtocol);

				if (clientId == null) {
					long sleep = TimeUnit.SECONDS.toMillis(1);

					Thread.sleep(sleep);

					timeout -= sleep;
				}
			}

			clientIds.add(clientId);

			if (_log.isInfoEnabled()) {
				_log.info("Using client ID " + clientId);
			}
		}

		nimbusJwtDecoder.setJwtValidator(
			new DelegatingOAuth2TokenValidator<>(
				new ClientIdOAuth2TokenValidator(clientIds)));

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
				_liferayOauthURLsExcludes
			).permitAll(
			).anyRequest(
			).authenticated()
		).oauth2ResourceServer(
			OAuth2ResourceServerConfigurer::jwt
		).build();
	}

	private List<String> _getAllowedOrigins() {
		List<String> allowedOrigins = new ArrayList<>();

		for (String lxcDXPDomain : _lxcDXPDomains.split("\\s*[,\n]\\s*")) {
			allowedOrigins.add("http://" + lxcDXPDomain);
			allowedOrigins.add("https://" + lxcDXPDomain);
		}

		return allowedOrigins;
	}

	private static final Log _log = LogFactory.getLog(
		LiferayOAuth2ResourceServerEnableWebSecurity.class);

	@Autowired
	private Environment _environment;

	@Value("${liferay.oauth.urls.excludes:}")
	private String[] _liferayOauthURLsExcludes;

	@Value("${com.liferay.lxc.dxp.domains}")
	private String _lxcDXPDomains;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

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