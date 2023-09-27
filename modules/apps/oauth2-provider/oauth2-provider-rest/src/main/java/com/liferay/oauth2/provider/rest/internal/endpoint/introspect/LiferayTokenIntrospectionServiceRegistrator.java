/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.introspect;

import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"oauth2.allow.token.introspection.endpoint=true",
		"oauth2.allow.token.introspection.endpoint.public.clients=true"
	},
	service = {}
)
public class LiferayTokenIntrospectionServiceRegistrator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		boolean enabled = MapUtil.getBoolean(
			properties, "oauth2.allow.token.introspection.endpoint", true);

		if (!enabled) {
			return;
		}

		boolean canSupportPublicClients = MapUtil.getBoolean(
			properties,
			"oauth2.allow.token.introspection.endpoint.public.clients", true);

		LiferayTokenIntrospectionService liferayTokenIntrospectionService =
			new LiferayTokenIntrospectionService(
				_liferayOAuthDataProvider, canSupportPublicClients);

		Dictionary<String, Object> liferayTokenIntrospectionProperties =
			new Hashtable<>();

		liferayTokenIntrospectionProperties.put(
			"osgi.jaxrs.application.select",
			"(osgi.jaxrs.name=Liferay.OAuth2.Application)");
		liferayTokenIntrospectionProperties.put(
			"osgi.jaxrs.name", "Liferay.Token.Introspection.Service");
		liferayTokenIntrospectionProperties.put("osgi.jaxrs.resource", true);

		_serviceRegistration = bundleContext.registerService(
			Object.class, liferayTokenIntrospectionService,
			liferayTokenIntrospectionProperties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private ServiceRegistration<Object> _serviceRegistration;

}