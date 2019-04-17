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

package com.liferay.oauth2.provider.rest.internal.jaxrs.feature;

import com.liferay.oauth2.provider.rest.spi.scope.checker.container.request.filter.BaseScopeCheckerContainerRequestFilter;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.petra.reflect.AnnotationLocator;

import java.lang.annotation.Annotation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Priority;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.ext.Provider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(|(&(!(oauth2.scope.checker.type=*))(!(oauth2.scopechecker.type=*)))(|(oauth2.scope.checker.type=http.method)(oauth2.scopechecker.type=http.method)))",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.OAuth2)",
		"osgi.jaxrs.name=Liferay.OAuth2.HTTP.method.request.checker"
	},
	scope = ServiceScope.PROTOTYPE, service = Feature.class
)
@Priority(Priorities.AUTHORIZATION - 8)
@Provider
public class HttpMethodFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		Map<Class<?>, Integer> contracts = new HashMap<>();

		contracts.put(
			ContainerRequestFilter.class, Priorities.AUTHORIZATION - 8);

		context.register((DynamicFeature)this::_collectHttpMethods);
		context.register(
			new HttpScopeCheckerContainerRequestFilter(), contracts);

		Configuration configuration = context.getConfiguration();

		_serviceRegistration = _bundleContext.registerService(
			ScopeFinder.class, new CollectionScopeFinder(_scopes),
			new Hashtable<>(
				(Map<String, Object>)configuration.getProperty(
					"osgi.jaxrs.application.serviceProperties")));

		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private void _collectHttpMethods(
		ResourceInfo resourceInfo, FeatureContext featureContext) {

		List<Annotation> annotations = AnnotationLocator.locate(
			resourceInfo.getResourceMethod(), null);

		for (Annotation annotation : annotations) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			HttpMethod[] annotationsByType =
				annotationType.getAnnotationsByType(HttpMethod.class);

			if (annotationsByType != null) {
				for (HttpMethod httpMethod : annotationsByType) {
					_scopes.add(httpMethod.value());
				}
			}
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private ScopeChecker _scopeChecker;

	private final Set<String> _scopes = new HashSet<>();
	private ServiceRegistration<ScopeFinder> _serviceRegistration;

	private class HttpScopeCheckerContainerRequestFilter
		extends BaseScopeCheckerContainerRequestFilter {

		public boolean isContainerRequestContextAllowed(
			ContainerRequestContext containerRequestContext) {

			Request request = containerRequestContext.getRequest();

			if (_scopeChecker.checkScope(request.getMethod())) {
				return true;
			}

			return false;
		}

	}

}