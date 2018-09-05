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
import com.liferay.oauth2.provider.scope.RequiresNoScope;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
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
		"osgi.jaxrs.application.select=(oauth2.scopechecker.type=annotations)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.OAuth2)",
		"osgi.jaxrs.name=Liferay.OAuth2.annotations.feature"
	},
	scope = ServiceScope.PROTOTYPE
)
@Provider
public class AnnotationFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		Configuration configuration = context.getConfiguration();

		HashSet<String> scopes = new HashSet<>();

		context.register(
			(DynamicFeature)(resourceInfo, a) -> scopes.addAll(
				RequiresScopeAnnotationFinder.find(
					resourceInfo.getResourceClass())));

		context.register(
			new AnnotationContainerScopeCheckerContainerRequestFilter(),
			Priorities.AUTHORIZATION - 8);

		Map<String, Object> applicationProperties =
			(Map<String, Object>)configuration.getProperty(
				"osgi.jaxrs.application.serviceProperties");

		_serviceRegistration = _bundleContext.registerService(
			ScopeFinder.class, new CollectionScopeFinder(scopes),
			new Hashtable<>(applicationProperties));

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

	private BundleContext _bundleContext;

	@Reference
	private ScopeChecker _scopeChecker;

	private ServiceRegistration<ScopeFinder> _serviceRegistration;

	private class AnnotationContainerScopeCheckerContainerRequestFilter
		extends BaseScopeCheckerContainerRequestFilter {

		public boolean isContainerRequestContextAllowed(
			ContainerRequestContext containerRequestContext) {

			Method resourceMethod = _resourceInfo.getResourceMethod();

			RequiresNoScope requiresNoScope = resourceMethod.getAnnotation(
				RequiresNoScope.class);

			RequiresScope requiresScope = resourceMethod.getAnnotation(
				RequiresScope.class);

			if ((requiresNoScope != null) && (requiresScope != null)) {
				StringBundler sb = new StringBundler(6);

				Class<?> declaringClass = resourceMethod.getDeclaringClass();

				sb.append("Method ");
				sb.append(declaringClass.getName());
				sb.append(StringPool.POUND);
				sb.append(resourceMethod.getName());
				sb.append("has both @RequiresNoScope and @RequiresScope ");
				sb.append("annotations defined");

				throw new RuntimeException(sb.toString());
			}

			if (requiresNoScope != null) {
				return true;
			}

			if (checkRequiresScope(requiresScope)) {
				return true;
			}

			Class<?> resourceClass = _resourceInfo.getResourceClass();

			requiresNoScope = resourceClass.getAnnotation(
				RequiresNoScope.class);

			requiresScope = resourceClass.getAnnotation(RequiresScope.class);

			if ((requiresNoScope != null) && (requiresScope != null)) {
				StringBundler sb = new StringBundler(4);

				sb.append("Class ");
				sb.append(resourceClass.getName());
				sb.append("has both @RequiresNoScope and @RequiresScope ");
				sb.append("annotations defined");

				throw new RuntimeException(sb.toString());
			}

			if (requiresNoScope != null) {
				return true;
			}

			if (checkRequiresScope(requiresScope)) {
				return true;
			}

			requiresNoScope = AnnotationLocator.locate(
				resourceClass, RequiresNoScope.class);

			requiresScope = AnnotationLocator.locate(
				resourceClass, RequiresScope.class);

			if ((requiresNoScope != null) && (requiresScope != null)) {
				StringBundler sb = new StringBundler(3);

				sb.append("Class ");
				sb.append(resourceClass.getName());
				sb.append("inherits both @RequiresNoScope and @RequiresScope");

				throw new RuntimeException(sb.toString());
			}

			if (requiresNoScope != null) {
				return true;
			}

			if (checkRequiresScope(requiresScope)) {
				return true;
			}

			return false;
		}

		protected boolean checkRequiresScope(RequiresScope requiresScope) {
			if (requiresScope != null) {
				if (requiresScope.allNeeded()) {
					return _scopeChecker.checkAllScopes(requiresScope.value());
				}

				return _scopeChecker.checkAnyScope(requiresScope.value());
			}

			return false;
		}

		@Context
		private ResourceInfo _resourceInfo;

	}

}