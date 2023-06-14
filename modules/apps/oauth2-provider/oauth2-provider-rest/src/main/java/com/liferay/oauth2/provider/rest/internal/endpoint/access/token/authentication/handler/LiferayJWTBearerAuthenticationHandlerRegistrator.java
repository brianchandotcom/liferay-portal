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

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token.authentication.handler;

import com.liferay.oauth2.provider.rest.internal.configuration.admin.service.OAuth2InAssertionManagedServiceFactory;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import javax.ws.rs.container.ContainerRequestFilter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(service = {})
public class LiferayJWTBearerAuthenticationHandlerRegistrator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		LiferayJWTBearerAuthenticationHandler
			liferayJWTBearerAuthenticationHandler =
				new LiferayJWTBearerAuthenticationHandler();

		liferayJWTBearerAuthenticationHandler.setClientRegistrationProvider(
			_liferayOAuthDataProvider);
		liferayJWTBearerAuthenticationHandler.
			setOAuth2InAssertionManagedServiceFactory(
				_oAuth2InAssertionManagedServiceFactory);

		_serviceRegistration = bundleContext.registerService(
			ContainerRequestFilter.class, liferayJWTBearerAuthenticationHandler,
			HashMapDictionaryBuilder.<String, Object>put(
				"osgi.jaxrs.application.select",
				"(osgi.jaxrs.name=Liferay.OAuth2.Application)"
			).put(
				"osgi.jaxrs.extension", true
			).put(
				"osgi.jaxrs.name", "Liferay.JWT.Bearer.Authentication.Handler"
			).build());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_serviceRegistration = null;
		}
	}

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	@Reference
	private OAuth2InAssertionManagedServiceFactory
		_oAuth2InAssertionManagedServiceFactory;

	private volatile ServiceRegistration<ContainerRequestFilter>
		_serviceRegistration;

}