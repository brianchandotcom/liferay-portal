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

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token.grant.handler;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.grants.jwt.Constants;
import org.apache.cxf.rs.security.oauth2.grants.jwt.JwtBearerGrantHandler;
import org.apache.cxf.rs.security.oauth2.provider.AccessTokenGrantHandler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	service = {AccessTokenGrantHandler.class}
)
public class LiferayJWTBearerGrantHandler extends BaseAccessTokenGrantHandler {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_jwtBearerGrantHandler = new JwtBearerGrantHandler();

		_jwtBearerGrantHandler.setDataProvider(_liferayOAuthDataProvider);

		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);
	}

	@Override
	protected AccessTokenGrantHandler getAccessTokenGrantHandler() {
		return _jwtBearerGrantHandler;
	}

	@Override
	protected boolean hasPermission(
		Client client, MultivaluedMap<String, String> params) {

		if (params.getFirst(Constants.CLIENT_GRANT_ASSERTION_PARAM) != null) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isGrantHandlerEnabled() {
		return _oAuth2ProviderConfiguration.allowJWTBearerGrant();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJWTBearerGrantHandler.class);

	private JwtBearerGrantHandler _jwtBearerGrantHandler;

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

}