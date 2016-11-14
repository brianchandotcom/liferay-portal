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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration;

import java.io.IOException;

import java.net.URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Thuong Dinh
 */
@Component(
	immediate = true,
	service = {ConfigurationListener.class, OpenIdConnectProviderRegistry.class}
)
public class OpenIdConnectProviderRegistryImpl
	implements OpenIdConnectProviderRegistry, ConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		String factoryPid = configurationEvent.getFactoryPid();

		if (!OpenIdConnectProviderConfiguration.class.getName().equals(
				factoryPid)) {

			return;
		}

		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				configurationEvent.getPid(), StringPool.QUESTION);

			if (configuration.getProperties() == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No configuration properties for: " +
							configurationEvent.getPid());
				}

				return;
			}

			OpenIdConnectProviderConfiguration
				openIdConnectProviderConfiguration =
					ConfigurableUtil.createConfigurable(
						OpenIdConnectProviderConfiguration.class,
						configuration.getProperties());

			if (configurationEvent.getType() == ConfigurationEvent.CM_DELETED) {
				_openIdConnectProviders.remove(
					openIdConnectProviderConfiguration.providerName());
			}
			else {
				OpenIdConnectProvider openIdConnectProvider =
					createOpenIdConnectProvider(
						openIdConnectProviderConfiguration);

				addOpenConnectIdConnectProvider(openIdConnectProvider);
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Error retrieving configuration: " +
						configurationEvent.getPid(),
					ioe);
			}
		}
	}

	@Override
	public OpenIdConnectProvider getOpenIdConnectProvider(String name) {
		return _openIdConnectProviders.get(name);
	}

	@Override
	public OpenIdConnectProvider getOpenIdConnectProvider(URL issuerUrl) {
		for (OpenIdConnectProvider openIdConnectProvider :
				_openIdConnectProviders.values()) {

			if (Objects.equals(
					openIdConnectProvider.getIssuerUrl(),
					issuerUrl.getHost())) {

				return openIdConnectProvider;
			}
		}

		return _openIdConnectProviders.get(
			OPEN_ID_CONNECT_PROVIDER_NAME_DEFAULT);
	}

	@Override
	public Collection<String> getOpenIdConnectProviderNames() {
		return Collections.unmodifiableCollection(
			_openIdConnectProviders.keySet());
	}

	@Activate
	protected void activate() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=" +
				OpenIdConnectProviderConfiguration.class.getName() + ")");

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				OpenIdConnectProviderConfiguration
					openIdConnectProviderConfiguration =
						ConfigurableUtil.createConfigurable(
							OpenIdConnectProviderConfiguration.class,
							configuration.getProperties());

				OpenIdConnectProvider openIdConnectProvider =
					createOpenIdConnectProvider(
						openIdConnectProviderConfiguration);

				addOpenConnectIdConnectProvider(openIdConnectProvider);
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeOpenConnectIdProvider"
	)
	protected void addOpenConnectIdConnectProvider(
		OpenIdConnectProvider openIdConnectProvider) {

		_openIdConnectProviders.put(
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

		_openIdConnectProviders.remove(openIdConnectProvider.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectProviderRegistryImpl.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private final Map<String, OpenIdConnectProvider> _openIdConnectProviders =
		new ConcurrentHashMap<>();

}