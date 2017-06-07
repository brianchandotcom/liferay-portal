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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectMetadataFactory;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration;

import java.net.URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration",
	service = {ManagedServiceFactory.class, OpenIdConnectProviderRegistry.class}
)
public class OpenIdConnectProviderRegistryImpl
	implements OpenIdConnectProviderRegistry, ManagedServiceFactory {

	@Override
	public void deleted(String factoryPid) {
		removeOpenConnectIdProvider(factoryPid);
	}

	@Override
	public OpenIdConnectProvider findOpenIdConnectProvider(String name)
		throws OpenIdConnectServiceException.ProviderException {

		OpenIdConnectProvider openIdConnectProvider = getOpenIdConnectProvider(
			name);

		if (openIdConnectProvider == null) {
			throw new OpenIdConnectServiceException.ProviderException(
				"Unable to get OpenId Connect provider with name " + name);
		}

		return openIdConnectProvider;
	}

	@Override
	public String getName() {
		return "OpenId Connect Provider Factory";
	}

	@Override
	public OpenIdConnectProvider getOpenIdConnectProvider(String name) {
		return _openIdConnectProvidersPerName.get(name);
	}

	@Override
	public Collection<String> getOpenIdConnectProviderNames() {
		if (_openIdConnectProvidersPerName.isEmpty()) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableCollection(
			_openIdConnectProvidersPerName.keySet());
	}

	@Override
	public void updated(String factoryPid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		OpenIdConnectProviderConfiguration openIdConnectProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenIdConnectProviderConfiguration.class, properties);

		synchronized (_openIdConnectProvidersPerFactory) {
			OpenIdConnectProvider openIdConnectProvider =
				createOpenIdConnectProvider(openIdConnectProviderConfiguration);

			removeOpenConnectIdProvider(factoryPid);

			addOpenConnectIdConnectProvider(factoryPid, openIdConnectProvider);
		}
	}

	protected void addOpenConnectIdConnectProvider(
		String factoryPid, OpenIdConnectProvider openIdConnectProvider) {

		synchronized (_openIdConnectProvidersPerFactory) {
			_openIdConnectProvidersPerFactory.put(
				factoryPid, openIdConnectProvider);

			_openIdConnectProvidersPerName.put(
				openIdConnectProvider.getName(), openIdConnectProvider);
		}
	}

	protected OpenIdConnectProvider createOpenIdConnectProvider(
			OpenIdConnectProviderConfiguration
				openIdConnectProviderConfiguration)
		throws ConfigurationException {

		OpenIdConnectMetadataFactory openIdConnectMetadataFactory = null;

		try {
			if (Validator.isNotNull(
					openIdConnectProviderConfiguration.discoveryEndPoint())) {

				openIdConnectMetadataFactory =
					new OpenIdConnectMetadataFactoryImpl(
						openIdConnectProviderConfiguration.providerName(),
						new URL(
							openIdConnectProviderConfiguration.
								discoveryEndPoint()),
						openIdConnectProviderConfiguration.
							discoveryEndPointCacheInMillis());
			}
			else {
				openIdConnectMetadataFactory =
					new OpenIdConnectMetadataFactoryImpl(
						openIdConnectProviderConfiguration.providerName(),
						openIdConnectProviderConfiguration.issuerURL(),
						openIdConnectProviderConfiguration.subjectTypes(),
						openIdConnectProviderConfiguration.jwksURI(),
						openIdConnectProviderConfiguration.
							authorizationEndPoint(),
						openIdConnectProviderConfiguration.tokenEndPoint(),
						openIdConnectProviderConfiguration.userInfoEndPoint());
			}
		}
		catch (Exception e) {
			throw new ConfigurationException(
				null,
				"Unable to instantiate provider metadata factory for " +
					openIdConnectProviderConfiguration.providerName(),
				e);
		}

		OpenIdConnectProvider openIdConnectProvider = new OpenIdConnectProvider(
			openIdConnectProviderConfiguration.providerName(),
			openIdConnectProviderConfiguration.openIdConnectClientId(),
			openIdConnectProviderConfiguration.openIdConnectClientSecret(),
			openIdConnectProviderConfiguration.scopes(),
			openIdConnectMetadataFactory);

		return openIdConnectProvider;
	}

	protected void removeOpenConnectIdProvider(String factoryPid) {
		synchronized (_openIdConnectProvidersPerFactory) {
			OpenIdConnectProvider openIdConnectProvider =
				_openIdConnectProvidersPerFactory.remove(factoryPid);

			if (openIdConnectProvider != null) {
				_openIdConnectProvidersPerName.remove(
					openIdConnectProvider.getName());
			}
		}
	}

	private final Map<String, OpenIdConnectProvider>
		_openIdConnectProvidersPerFactory = new ConcurrentHashMap<>();
	private final Map<String, OpenIdConnectProvider>
		_openIdConnectProvidersPerName = new ConcurrentHashMap<>();

}