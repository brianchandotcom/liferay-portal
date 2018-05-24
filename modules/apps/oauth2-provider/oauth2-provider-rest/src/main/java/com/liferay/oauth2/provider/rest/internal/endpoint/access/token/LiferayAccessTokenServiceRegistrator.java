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

import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Dictionary;
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
import org.osgi.service.component.annotations.ReferencePolicy;
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

		_blockUnsecureRequests = MapUtil.getBoolean(
			properties, "block.unsecure.requests", true);
		_canSupportPublicClients = MapUtil.getBoolean(
			properties, "allow.public.clients", true);
		_enabled = MapUtil.getBoolean(properties, "enabled", true);

		_bundleContext = bundleContext;

		_updateLiferayAccessTokenService(bundleContext);

		_activated = true;
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addAccessTokenGrantHandler(
		AccessTokenGrantHandler accessTokenGrantHandler) {

		_accessTokenGrantHandlers.add(accessTokenGrantHandler);

		_updateLiferayAccessTokenService(_bundleContext);
	}

	@Deactivate
	protected void deactivate() {
		if (!_activated) {
			return;
		}

		_activated = false;

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	protected void removeAccessTokenGrantHandler(
		AccessTokenGrantHandler accessTokenGrantHandler) {

		_accessTokenGrantHandlers.remove(accessTokenGrantHandler);

		_updateLiferayAccessTokenService(_bundleContext);
	}

	private void _updateLiferayAccessTokenService(BundleContext bundleContext) {
		if (!_enabled || (bundleContext == null)) {
			return;
		}

		deactivate();

		LiferayAccessTokenService liferayAccessTokenService =
			new LiferayAccessTokenService();

		liferayAccessTokenService.setBlockUnsecureRequests(
			_blockUnsecureRequests);
		liferayAccessTokenService.setCanSupportPublicClients(
			_canSupportPublicClients);
		liferayAccessTokenService.setDataProvider(_liferayOAuthDataProvider);
		liferayAccessTokenService.setGrantHandlers(_accessTokenGrantHandlers);

		Dictionary<String, Object> liferayAccessTokenServiceProperties =
			new HashMapDictionary<>();

		liferayAccessTokenServiceProperties.put(
			"osgi.jaxrs.application.select",
			"(osgi.jaxrs.name=Liferay.OAuth2.Application)");
		liferayAccessTokenServiceProperties.put("osgi.jaxrs.resource", true);

		_serviceRegistration = bundleContext.registerService(
			Object.class, liferayAccessTokenService,
			liferayAccessTokenServiceProperties);
	}

	private final List<AccessTokenGrantHandler> _accessTokenGrantHandlers =
		new ArrayList<>();
	private volatile boolean _activated;
	private boolean _blockUnsecureRequests;
	private BundleContext _bundleContext;
	private boolean _canSupportPublicClients;
	private boolean _enabled;

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private ServiceRegistration<Object> _serviceRegistration;

}