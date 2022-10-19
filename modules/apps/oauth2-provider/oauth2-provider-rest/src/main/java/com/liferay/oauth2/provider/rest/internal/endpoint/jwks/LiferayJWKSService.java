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

package com.liferay.oauth2.provider.rest.internal.endpoint.jwks;

import com.liferay.oauth2.provider.rest.internal.configuration.OAuth2AuthorizationServerConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.rs.security.jose.jwk.JsonWebKeys;
import org.apache.cxf.rs.security.jose.jwk.JwkUtils;
import org.apache.cxf.rs.security.oauth2.services.JwksService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Raymond Augé
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.rest.internal.configuration.OAuth2AuthorizationServerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.name=Liferay.Authorization.JWKS", "osgi.jaxrs.resource=true"
	},
	service = LiferayJWKSService.class
)
@Path("/jwks")
public class LiferayJWKSService extends JwksService {

	@GET
	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public JsonWebKeys getPublicVerificationKeys() {
		return _jsonWebKeys;
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		OAuth2AuthorizationServerConfiguration
			oAuth2AuthorizationServerConfiguration =
				ConfigurableUtil.createConfigurable(
					OAuth2AuthorizationServerConfiguration.class, properties);

		_jsonWebKeys = new JsonWebKeys(
			JwkUtils.stripPrivateParameters(
				Collections.singletonList(
					JwkUtils.readJwkKey(
						oAuth2AuthorizationServerConfiguration.
							jwtAccessTokenSigningJSONWebKey()))));
	}

	private JsonWebKeys _jsonWebKeys;

}