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
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration;

import java.net.URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;

/**
 * @author Thuong Dinh
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
		_openIdConnectProvidersPerFactory.computeIfPresent(
			factoryPid,
			(pid, openIdConnectProvider) -> {
				removeOpenConnectIdProvider(openIdConnectProvider);

				return null;
			});
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
	public OpenIdConnectProvider getOpenIdConnectProvider(URL issuerUrl) {
		for (OpenIdConnectProvider openIdConnectProvider :
				_openIdConnectProvidersPerName.values()) {

			if (Objects.equals(
					openIdConnectProvider.getIssuerUrl(),
					issuerUrl.getHost())) {

				return openIdConnectProvider;
			}
		}

		return _openIdConnectProvidersPerName.get(
			OPEN_ID_CONNECT_PROVIDER_NAME_DEFAULT);
	}

	@Override
	public Collection<String> getOpenIdConnectProviderNames() {
		return Collections.unmodifiableCollection(
			_openIdConnectProvidersPerName.keySet());
	}

	@Override
	public void updated(String factoryPid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		OpenIdConnectProviderConfiguration openIdConnectProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenIdConnectProviderConfiguration.class, properties);

		_openIdConnectProvidersPerFactory.computeIfPresent(
			factoryPid,
			(oldFactoryPid, oldOpenIdConnectIdProvider) -> {
				removeOpenConnectIdProvider(oldOpenIdConnectIdProvider);

				OpenIdConnectProvider openIdConnectProvider =
					createOpenIdConnectProvider(
						openIdConnectProviderConfiguration);

				addOpenConnectIdConnectProvider(openIdConnectProvider);

				return openIdConnectProvider;
			});

		_openIdConnectProvidersPerFactory.computeIfAbsent(
			factoryPid,
			newFactoryPid -> {
				OpenIdConnectProvider openIdConnectProvider =
					createOpenIdConnectProvider(
						openIdConnectProviderConfiguration);

				addOpenConnectIdConnectProvider(openIdConnectProvider);

				return openIdConnectProvider;
			});
	}

	protected void addOpenConnectIdConnectProvider(
		OpenIdConnectProvider openIdConnectProvider) {

		_openIdConnectProvidersPerName.put(
			openIdConnectProvider.getName(), openIdConnectProvider);
	}

	protected OpenIdConnectProvider createOpenIdConnectProvider(
		OpenIdConnectProviderConfiguration openIdConnectProviderConfiguration) {

		OpenIdConnectProvider openIdConnectProvider = new OpenIdConnectProvider(
			openIdConnectProviderConfiguration.providerName());

		openIdConnectProvider.setAuthorizationEndPoint(
			openIdConnectProviderConfiguration.authorizationEndPoint());
		openIdConnectProvider.setClientId(
			openIdConnectProviderConfiguration.openIdConnectClientId());
		openIdConnectProvider.setClientSecret(
			openIdConnectProviderConfiguration.openIdConnectClientSecret());
		openIdConnectProvider.setDiscoveryEndPoint(
			openIdConnectProviderConfiguration.discoveryEndPoint());
		openIdConnectProvider.setTokenEndPoint(
			openIdConnectProviderConfiguration.tokenEndPoint());
		openIdConnectProvider.setUserInfoEndPoint(
			openIdConnectProviderConfiguration.userInfoEndPoint());
		openIdConnectProvider.setIssuerUrl(
			openIdConnectProviderConfiguration.issuerUrl());
		openIdConnectProvider.setSubjectTypes(
			openIdConnectProviderConfiguration.subjectTypes());
		openIdConnectProvider.setJwksUri(
			openIdConnectProviderConfiguration.jwksUri());

		return openIdConnectProvider;
	}

	protected void removeOpenConnectIdProvider(
		OpenIdConnectProvider openIdConnectProvider) {

		_openIdConnectProvidersPerName.remove(openIdConnectProvider.getName());
	}

	private final Map<String, OpenIdConnectProvider>
		_openIdConnectProvidersPerFactory = new ConcurrentHashMap<>();
	private final Map<String, OpenIdConnectProvider>
		_openIdConnectProvidersPerName = new ConcurrentHashMap<>();

}