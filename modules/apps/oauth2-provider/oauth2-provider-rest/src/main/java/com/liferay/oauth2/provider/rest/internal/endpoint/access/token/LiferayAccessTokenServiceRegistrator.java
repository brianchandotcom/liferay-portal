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

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token;

import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRestEndpointConstants;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.cxf.rs.security.oauth2.provider.AccessTokenGrantHandler;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		"block.unsecure.requests=true", "can.support.public.clients=true",
		"enabled=true"
	}
)
public class LiferayAccessTokenServiceRegistrator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		if (!MapUtil.getBoolean(properties, "enabled", true)) {
			return;
		}

		LiferayAccessTokenService liferayAccessTokenService =
			new LiferayAccessTokenService();

		liferayAccessTokenService.setBlockUnsecureRequests(
			MapUtil.getBoolean(properties, "block.unsecure.requests", true));
		liferayAccessTokenService.setCanSupportPublicClients(
			MapUtil.getBoolean(properties, "allow.public.clients", true));
		liferayAccessTokenService.setDataProvider(_liferayOAuthDataProvider);
		liferayAccessTokenService.setGrantHandlers(_accessTokenGrantHandlers);

		Dictionary<String, Object> liferayAccessTokenServiceProperties =
			new Hashtable<>();

		liferayAccessTokenServiceProperties.put(
			OAuth2ProviderRestEndpointConstants.
				PROPERTY_KEY_OAUTH2_ENDPOINT_JAXRS_RESOURCE,
			true);

		_serviceRegistration = bundleContext.registerService(
			Object.class, liferayAccessTokenService,
			liferayAccessTokenServiceProperties);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeAccessTokenGrantHandler"
	)
	protected void addAccessTokenGrantHandler(
		AccessTokenGrantHandler accessTokenGrantHandler) {

		_accessTokenGrantHandlers.add(accessTokenGrantHandler);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	protected void removeAccessTokenGrantHandler(
		AccessTokenGrantHandler accessTokenGrantHandler) {

		_accessTokenGrantHandlers.remove(accessTokenGrantHandler);
	}

	private final List<AccessTokenGrantHandler> _accessTokenGrantHandlers =
		new ArrayList<>();

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private ServiceRegistration<Object> _serviceRegistration;

}