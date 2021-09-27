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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProvider;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thuong Dinh
 * @author Edward C. Han
 */
@Component(
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration",
	service = {ManagedServiceFactory.class, OpenIdConnectProviderRegistry.class}
)
public class OpenIdConnectProviderRegistryImpl
	implements ManagedServiceFactory,
			   OpenIdConnectProviderRegistry
				   <OIDCClientMetadata, OIDCProviderMetadata> {

	@Override
	public void deleted(String pid) {
		Dictionary<String, ?> properties = _configurationPidsProperties.remove(
			pid);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == CompanyConstants.SYSTEM) {
			_rebuild();
		}
		else {
			_rebuild(companyId);
		}
	}

	@Override
	public OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			findOpenIdConnectProvider(long companyId, String name)
		throws OpenIdConnectServiceException.ProviderException {

		OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			openIdConnectProvider = getOpenIdConnectProvider(companyId, name);

		if (openIdConnectProvider == null) {
			throw new OpenIdConnectServiceException.ProviderException(
				"Unable to find an OpenId Connect provider with name \"" +
					name + "\"");
		}

		return openIdConnectProvider;
	}

	@Override
	public String getName() {
		return "OpenId Connect Provider Factory";
	}

	@Override
	public OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
		getOpenIdConnectProvider(long companyId, String name) {

		Map
			<String,
			 OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>>
				openIdConnectProviderMap =
					_companyIdProviderNameOpenIdConnectProviders.get(companyId);

		if (openIdConnectProviderMap == null) {
			openIdConnectProviderMap =
				_companyIdProviderNameOpenIdConnectProviders.get(
					CompanyConstants.SYSTEM);
		}

		return openIdConnectProviderMap.get(name);
	}

	@Override
	public Collection<String> getOpenIdConnectProviderNames(long companyId) {
		Map
			<String,
			 OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>>
				openIdConnectProviderMap =
					_companyIdProviderNameOpenIdConnectProviders.get(companyId);

		if (openIdConnectProviderMap == null) {
			openIdConnectProviderMap =
				_companyIdProviderNameOpenIdConnectProviders.get(
					CompanyConstants.SYSTEM);
		}

		return Collections.unmodifiableSet(openIdConnectProviderMap.keySet());
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) {
		Dictionary<String, ?> oldProperties = _configurationPidsProperties.put(
			pid, properties);

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId == CompanyConstants.SYSTEM) {
			_rebuild();

			return;
		}

		if (oldProperties != null) {
			long oldCompanyId = GetterUtil.getLong(
				oldProperties.get("companyId"));

			if (oldCompanyId == CompanyConstants.SYSTEM) {
				_rebuild();

				return;
			}

			if (oldCompanyId != companyId) {
				_rebuild(oldCompanyId);
			}
		}

		_rebuild(companyId);
	}

	@Activate
	protected void activate() {
		_companyIdProviderNameOpenIdConnectProviders.putIfAbsent(
			CompanyConstants.SYSTEM, Collections.emptyMap());
	}

	protected OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
			createOpenIdConnectProvider(
				OpenIdConnectProviderConfiguration
					openIdConnectProviderConfiguration)
		throws ConfigurationException {

		try {
			return new OpenIdConnectProviderImpl(
				openIdConnectProviderConfiguration.providerName(),
				openIdConnectProviderConfiguration.openIdConnectClientId(),
				openIdConnectProviderConfiguration.openIdConnectClientSecret(),
				openIdConnectProviderConfiguration.scopes(),
				getOpenIdConnectMetadataFactory(
					openIdConnectProviderConfiguration),
				openIdConnectProviderConfiguration.tokenConnectionTimeout());
		}
		catch (Exception exception) {
			throw new ConfigurationException(
				null,
				StringBundler.concat(
					"Unable to instantiate provider metadata factory for \"",
					openIdConnectProviderConfiguration.providerName(), "\": ",
					exception.getMessage()),
				exception);
		}
	}

	protected OpenIdConnectMetadataFactory getOpenIdConnectMetadataFactory(
			OpenIdConnectProviderConfiguration
				openIdConnectProviderConfiguration)
		throws MalformedURLException,
			   OpenIdConnectServiceException.ProviderException {

		if (Validator.isNotNull(
				openIdConnectProviderConfiguration.discoveryEndPoint())) {

			return new OpenIdConnectMetadataFactoryImpl(
				openIdConnectProviderConfiguration.providerName(),
				new URL(openIdConnectProviderConfiguration.discoveryEndPoint()),
				openIdConnectProviderConfiguration.
					discoveryEndPointCacheInMillis(),
				openIdConnectProviderConfiguration.
					registeredIdTokenSigningAlg());
		}

		return new OpenIdConnectMetadataFactoryImpl(
			openIdConnectProviderConfiguration.providerName(),
			openIdConnectProviderConfiguration.registeredIdTokenSigningAlg(),
			openIdConnectProviderConfiguration.idTokenSigningAlgValues(),
			openIdConnectProviderConfiguration.issuerURL(),
			openIdConnectProviderConfiguration.subjectTypes(),
			openIdConnectProviderConfiguration.jwksURI(),
			openIdConnectProviderConfiguration.authorizationEndPoint(),
			openIdConnectProviderConfiguration.tokenEndPoint(),
			openIdConnectProviderConfiguration.userInfoEndPoint());
	}

	private <U, V> void _addDefaults(Map<U, V> map, Map<U, V> defaultsMap) {
		if (defaultsMap != null) {
			defaultsMap.forEach(map::putIfAbsent);
		}
	}

	private void _rebuild() {
		_rebuild(CompanyConstants.SYSTEM);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (companyId != CompanyConstants.SYSTEM) {
					_rebuild(companyId);
				}
			},
			ArrayUtil.toLongArray(
				_companyIdProviderNameOpenIdConnectProviders.keySet()));
	}

	private void _rebuild(long companyId) {
		Map
			<String,
			 OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>>
				openIdConnectProviderMap = new TreeMap<>();

		for (Dictionary<String, ?> properties :
				_configurationPidsProperties.values()) {

			if (companyId != GetterUtil.getLong(properties.get("companyId"))) {
				continue;
			}

			try {
				OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>
					openIdConnectProvider = createOpenIdConnectProvider(
						ConfigurableUtil.createConfigurable(
							OpenIdConnectProviderConfiguration.class,
							properties));

				if (openIdConnectProviderMap.containsKey(
						openIdConnectProvider.getName())) {

					_log.error(
						"Duplicate OpenId Connect provider name \"" +
							openIdConnectProvider.getName() + "\"");

					continue;
				}

				openIdConnectProviderMap.put(
					openIdConnectProvider.getName(), openIdConnectProvider);
			}
			catch (ConfigurationException configurationException) {
				_log.error(configurationException, configurationException);
			}
		}

		if (companyId != CompanyConstants.SYSTEM) {
			_addDefaults(
				openIdConnectProviderMap,
				_companyIdProviderNameOpenIdConnectProviders.get(
					CompanyConstants.SYSTEM));
		}

		_companyIdProviderNameOpenIdConnectProviders.put(
			companyId, openIdConnectProviderMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectProviderRegistryImpl.class);

	private final Map
		<Long,
		 Map
			 <String,
			  OpenIdConnectProvider<OIDCClientMetadata, OIDCProviderMetadata>>>
				_companyIdProviderNameOpenIdConnectProviders =
					new ConcurrentHashMap<>();

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Map<String, Dictionary<String, ?>>
		_configurationPidsProperties = new ConcurrentHashMap<>();

}