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

package com.liferay.oauth2.provider.rest.internal.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.io.IOException;

import java.util.Dictionary;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class OAuth2AuthorizationServerConfigurationGenerator {

	@Activate
	protected void activate() throws IOException {
		Configuration configuration = _configurationAdmin.getConfiguration(
			OAuth2AuthorizationServerConfiguration.class.getName(),
			StringPool.QUESTION);

		Dictionary<String, Object> dictionary = configuration.getProperties();

		if (dictionary == null) {
			configuration.update(
				HashMapDictionaryBuilder.<String, Object>put(
					"oauth2.authorization.server.issue.jwt.access.token", true
				).put(
					"oauth2.authorization.server.jwt.access.token.signing." +
						"json.web.key",
					() -> {
						RsaJsonWebKey rsaJsonWebKey =
							RsaJwkGenerator.generateJwk(2048);

						rsaJsonWebKey.setAlgorithm("RS256");
						rsaJsonWebKey.setKeyId("authServer");

						return rsaJsonWebKey.toJson(
							JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
					}
				).build());
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}