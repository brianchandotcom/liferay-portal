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

package com.liferay.portal.sso.openid.internal;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.sso.openid.OpenIdProvider;
import com.liferay.portal.sso.openid.OpenIdProviderRegistry;

import java.net.URL;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"open.id.ax.schema[default]=email,firstname,lastname",
		"open.id.ax.schema[yahoo]=email,fullname",
		"open.id.ax.type.email[default]=http://schema.openid.net/contact/email",
		"open.id.ax.type.email[yahoo]=http://axschema.org/contact/email",
		"open.id.ax.type.firstname[default]=http://schema.openid.net/namePerson/first",
		"open.id.ax.type.fullname[yahoo]=http://axschema.org/namePerson",
		"open.id.ax.type.lastname[default]=http://schema.openid.net/namePerson/last",
		"open.id.url[yahoo]=open.login.yahooapis.com"
	},
	service = OpenIdProviderRegistry.class
)
public class OpenIdProviderRegistryImpl implements OpenIdProviderRegistry {

	@Override
	public OpenIdProvider getOpenIdProvider(String name) {
		return _openIdProviders.get(name);
	}

	public OpenIdProvider getOpenIdProvider(URL url) {
		String hostName = url.getHost();

		for (OpenIdProvider openIdProvider : _openIdProviders.values()) {
			String openIdURLString = openIdProvider.getUrl();

			if (hostName.equals(openIdURLString)) {
				return openIdProvider;
			}
		}

		return _openIdProviders.get(DEFAULT_OPEN_ID_PROVIDER_NAME);
	}

	@Override
	public Collection<String> getOpenIdProviderNames() {
		return Collections.unmodifiableCollection(_openIdProviders.keySet());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		OpenIdProvider defaultOpenIdProvider = new OpenIdProvider();

		defaultOpenIdProvider.setAxSchema(
			GetterUtil.getString(properties, "open.id.ax.schema[default]"));
		defaultOpenIdProvider.setAxTypeEmail(
			GetterUtil.getString(properties, "open.id.ax.type.email[default]"));
		defaultOpenIdProvider.setAxTypeFirstName(
			GetterUtil.getString(
				properties, "open.id.ax.type.firstname[default]"));
		defaultOpenIdProvider.setAxTypeLastName(
			GetterUtil.getString(
				properties, "open.id.ax.type.lastname[default]"));
		defaultOpenIdProvider.setName(DEFAULT_OPEN_ID_PROVIDER_NAME);

		setOpenIdProvider(defaultOpenIdProvider);

		OpenIdProvider yahooOpenIdProvider = new OpenIdProvider();
		yahooOpenIdProvider.setAxSchema(
			GetterUtil.getString(properties, "open.id.ax.schema[yahoo]"));
		yahooOpenIdProvider.setAxTypeEmail(
			GetterUtil.getString(properties, "open.id.ax.type.email[yahoo]"));
		yahooOpenIdProvider.setAxTypeFullName(
			GetterUtil.getString(
				properties, "open.id.ax.type.fullname[yahoo]"));
		yahooOpenIdProvider.setName("yahoo");
		yahooOpenIdProvider.setUrl(
			GetterUtil.getString(properties, "open.id.url[yahoo]"));

		setOpenIdProvider(yahooOpenIdProvider);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setOpenIdProvider(OpenIdProvider openIdProvider) {
		_openIdProviders.put(openIdProvider.getName(), openIdProvider);
	}

	protected void unsetOpenIdProvider(OpenIdProvider openIdProvider) {
		_openIdProviders.remove(openIdProvider.getName());
	}

	private final Map<String, OpenIdProvider> _openIdProviders =
		new HashMap<>();

}