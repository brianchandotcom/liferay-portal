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
import com.liferay.osgi.util.StringPlus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
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
		"ignore.missing.scopes=HEAD", "ignore.missing.scopes=OPTIONS",
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
		Configuration configuration = context.getConfiguration();

		Map<String, Object> applicationProperties =
			(Map<String, Object>)configuration.getProperty(
				"osgi.jaxrs.application.serviceProperties");

		Object ignoreMissingScopesObject = applicationProperties.get(
			"ignore.missing.scopes");

		if (ignoreMissingScopesObject != null) {
			_ignoreMissingScopes = new HashSet<>(
				StringPlus.asList(ignoreMissingScopesObject));
		}

		context.register((DynamicFeature)this::_collectHttpMethods);
		context.register(
			new HttpScopeCheckerContainerRequestFilter(),
			Collections.singletonMap(
				ContainerRequestFilter.class, Priorities.AUTHORIZATION - 8));

		_serviceRegistration = _bundleContext.registerService(
			ScopeFinder.class, new CollectionScopeFinder(_scopes),
			new Hashtable<>(applicationProperties));

		return true;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_ignoreMissingScopes = new HashSet<>(
			StringPlus.asList(properties.get("ignore.missing.scopes")));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private void _collectHttpMethods(
		ResourceInfo resourceInfo, FeatureContext featureContext) {

		Method method = resourceInfo.getResourceMethod();

		while (method != null) {
			for (Annotation annotation : method.getAnnotations()) {
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

			method = _getSuperMethod(method);
		}
	}

	private Method _getSuperMethod(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		clazz = clazz.getSuperclass();

		if (clazz == Object.class) {
			return null;
		}

		try {
			return clazz.getDeclaredMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (NoSuchMethodException nsme) {
			return null;
		}
	}

	private BundleContext _bundleContext;
	private Set<String> _ignoreMissingScopes;

	@Reference
	private ScopeChecker _scopeChecker;

	private final Set<String> _scopes = new HashSet<>();
	private ServiceRegistration<ScopeFinder> _serviceRegistration;

	private class HttpScopeCheckerContainerRequestFilter
		extends BaseScopeCheckerContainerRequestFilter {

		public boolean isContainerRequestContextAllowed(
			ContainerRequestContext containerRequestContext) {

			Request request = containerRequestContext.getRequest();

			String requestMethod = request.getMethod();

			if (!_scopes.contains(requestMethod) &&
				_ignoreMissingScopes.contains(requestMethod)) {

				return true;
			}

			if (_scopeChecker.checkScope(requestMethod)) {
				return true;
			}

			return false;
		}

	}

}