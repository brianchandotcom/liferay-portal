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

package com.liferay.oauth2.provider.rest.internal.endpoint;

import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRestEndpointConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.rs.security.oauth2.provider.OAuthJSONProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carlos Sierra Andrés
 */
@ApplicationPath("/")
@Component(immediate = true, service = Application.class)
public class OAuth2EndpointApplication extends Application {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(" + OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_OAUTH2_ENDPOINT_JAXRS_PROVIDER + "=true)",
		unbind = "removeJaxrsObject"
	)
	public void addJaxrsProvider(Object jaxrsProvider) {
		_jaxrsObjects.add(jaxrsProvider);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(" + OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_OAUTH2_ENDPOINT_JAXRS_RESOURCE + "=true)",
		unbind = "removeJaxrsProvider"
	)
	public void addJaxrsResource(Object jaxrsResource) {
		_jaxrsObjects.add(jaxrsResource);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return Collections.singleton(OAuthJSONProvider.class);
	}

	@Override
	public Set<Object> getSingletons() {
		return new HashSet<>(_jaxrsObjects);
	}

	public void removeJaxrsObject(Object object) {
		_jaxrsObjects.remove(object);
	}

	private final List<Object> _jaxrsObjects = new ArrayList<>();

}