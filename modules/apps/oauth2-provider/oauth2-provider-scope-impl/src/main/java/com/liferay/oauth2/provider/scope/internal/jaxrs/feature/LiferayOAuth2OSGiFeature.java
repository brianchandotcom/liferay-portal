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

package com.liferay.oauth2.provider.scope.internal.jaxrs.feature;

import com.liferay.oauth2.provider.scope.internal.constants.OAuth2ProviderScopeConstants;
import com.liferay.oauth2.provider.scope.internal.jaxrs.filter.AbstractContextContainerRequestFilter;
import com.liferay.oauth2.provider.scope.liferay.ScopeContext;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"liferay.extension=OAuth2",
		"osgi.jaxrs.application.select=(!(liferay.oauth2=false))",
		"osgi.jaxrs.extension=true", "osgi.jaxrs.name=Liferay.OAuth2"
	},
	scope = ServiceScope.PROTOTYPE, service = Feature.class
)
@Provider
public class LiferayOAuth2OSGiFeature implements Feature {

	@Override
	public boolean configure(FeatureContext featureContext) {
		Configuration configuration = featureContext.getConfiguration();

		Map<String, Object> applicationProperties =
			(Map<String, Object>)configuration.getProperty(
				"osgi.jaxrs.application.serviceProperties");

		Class<? extends Application> applicationClass = _application.getClass();

		String osgiJAXRSName = MapUtil.getString(
			applicationProperties, "osgi.jaxrs.name",
			applicationClass.getName());

		featureContext.register(
			new AbstractContextContainerRequestFilter() {

				@Override
				public void filter(ContainerRequestContext requestContext) {
					_scopeContext.setApplicationName(osgiJAXRSName);
					_scopeContext.setBundle(_bundle);
					_scopeContext.setCompanyId(getCompanyId());
				}

			},
			Priorities.AUTHORIZATION - 10);

		featureContext.register(
			(ContainerResponseFilter)(a, b) -> _scopeContext.clear(),
			Priorities.AUTHORIZATION - 9);


		registerDescriptors(osgiJAXRSName);

		return true;
	}

	@Activate
	protected void activate(
		ComponentContext componentContext, Map<String, Object> properties) {

		_bundle = componentContext.getUsingBundle();

		_bundleContext = componentContext.getBundleContext();
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
			}
		}

		for (ServiceTracker<?, ?> serviceTracker : _serviceTrackers) {
			serviceTracker.close();
		}
	}

	protected void registerDescriptors(String osgiJAXRSName) {
		String bundleSymbolicName = _bundle.getSymbolicName();

		StringBundler sb = new StringBundler(5);

		sb.append("(&(bundle.symbolic.name=");
		sb.append(bundleSymbolicName);
		sb.append(")(objectClass=");
		sb.append(ResourceBundleLoader.class.getName());
		sb.append(")(resource.bundle.base.name=content.Language))");

		ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
			serviceTracker = ServiceTrackerFactory.open(
				_bundleContext, sb.toString());

		_serviceTrackers.add(serviceTracker);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME, osgiJAXRSName);

		_serviceRegistrations.add(
			_bundleContext.registerService(
				new String[] {
					ScopeDescriptor.class.getName(),
					ApplicationDescriptor.class.getName()
				},
				new ApplicationDescriptorsImpl(serviceTracker, osgiJAXRSName),
				properties));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayOAuth2OSGiFeature.class);

	@Context
	private Application _application;

	private Bundle _bundle;
	private BundleContext _bundleContext;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile ScopeDescriptor _defaultScopeDescriptor;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ScopeContext _scopeContext;

	private final Collection<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private final Collection<ServiceTracker<?, ?>> _serviceTrackers =
		new ArrayList<>();

	private class ApplicationDescriptorsImpl
		implements ScopeDescriptor, ApplicationDescriptor {

		public ApplicationDescriptorsImpl(
			ServiceTracker<?, ResourceBundleLoader> serviceTracker,
			String osgiJAXRSName) {

			_serviceTracker = serviceTracker;
			_osgiJAXRSName = osgiJAXRSName;
		}

		@Override
		public String describeApplication(Locale locale) {
			ResourceBundleLoader resourceBundleLoader =
				_serviceTracker.getService();

			if (resourceBundleLoader == null) {
				return _osgiJAXRSName;
			}

			String key = "oauth2.application.description." + _osgiJAXRSName;

			return GetterUtil.getString(
				ResourceBundleUtil.getString(
					resourceBundleLoader.loadResourceBundle(locale), key),
				key);
		}

		@Override
		public String describeScope(String scope, Locale locale) {
			ResourceBundleLoader resourceBundleLoader =
				_serviceTracker.getService();

			if (resourceBundleLoader == null) {
				return _defaultScopeDescriptor.describeScope(scope, locale);
			}

			String key = "oauth2.scope." + scope;

			return GetterUtil.getString(
				ResourceBundleUtil.getString(
					resourceBundleLoader.loadResourceBundle(locale), key),
				_defaultScopeDescriptor.describeScope(scope, locale));
		}

		private final String _osgiJAXRSName;
		private final ServiceTracker<?, ResourceBundleLoader> _serviceTracker;

	}

}