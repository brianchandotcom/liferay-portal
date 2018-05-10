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

import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.RequestScopeCheckerFilter;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.internal.constants.OAuth2ProviderScopeConstants;
import com.liferay.oauth2.provider.scope.internal.jaxrs.filter.AbstractContextContainerRequestFilter;
import com.liferay.oauth2.provider.scope.internal.jaxrs.filter.ScopeCheckerContainerRequestFilter;
import com.liferay.oauth2.provider.scope.liferay.ScopeContext;
import com.liferay.oauth2.provider.scope.liferay.ScopedServiceTrackerMapFactory;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServiceFactoryBean;
import org.apache.cxf.jaxrs.model.ApplicationInfo;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.FactoryBeanListenerManager;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, property = "liferay.extension=OAuth2")
@Provider
public class LiferayOAuth2OSGiFeature implements Feature {

	@Override
	public boolean configure(FeatureContext featureContext) {
		featureContext.register(
			new AbstractContextContainerRequestFilter() {

				@Override
				public void filter(ContainerRequestContext requestContext) {
					_scopeContext.setApplicationName(getApplicationName());
					_scopeContext.setBundle(getBundle());
					_scopeContext.setCompanyId(getCompanyId());
				}

			},
			Priorities.AUTHORIZATION - 10);

		featureContext.register(
			new ScopeCheckerContainerRequestFilter(
				_scopedServiceTrackerMapFactory.create(
					_bundleContext, RequestScopeCheckerFilter.class,
					OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
					() -> _defaultRequestScopeChecker),
				_scopeChecker),
			Priorities.AUTHORIZATION - 9);

		featureContext.register(
			(ContainerResponseFilter)(a, b) -> _scopeContext.clear(),
			Priorities.AUTHORIZATION - 9);

		_initializedThreadLocal.set(Boolean.TRUE);

		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, unbind = "restoreBus"
	)
	protected void modifyBus(Bus bus) {
		FactoryBeanListenerManager factoryBeanListenerManager =
			bus.getExtension(FactoryBeanListenerManager.class);

		if (factoryBeanListenerManager == null) {
			return;
		}

		factoryBeanListenerManager.addListener(_factoryBeanListener);
	}

	protected void restoreBus(Bus bus) {
		FactoryBeanListenerManager factoryBeanListenerManager =
			bus.getExtension(FactoryBeanListenerManager.class);

		if (factoryBeanListenerManager == null) {
			return;
		}

		factoryBeanListenerManager.removeListener(_factoryBeanListener);
	}

	private static final ThreadLocal<Boolean> _initializedThreadLocal =
		ThreadLocal.withInitial(() -> Boolean.FALSE);

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=annotation)"
	)
	private RequestScopeCheckerFilter _annotationRequestScopeChecker;

	private BundleContext _bundleContext;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private RequestScopeCheckerFilter _defaultRequestScopeChecker;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(default=true)"
	)
	private volatile ScopeDescriptor _defaultScopeDescriptor;

	private final FactoryBeanListener _factoryBeanListener =
		new ScopeFinderFactoryBeanListener();

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ScopeChecker _scopeChecker;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ScopeContext _scopeContext;

	@Reference
	private ScopedServiceTrackerMapFactory _scopedServiceTrackerMapFactory;

	private class ScopeFinderFactoryBeanListener
		implements FactoryBeanListener {

		@Override
		public void handleEvent(
			Event ev, AbstractServiceFactoryBean factory, Object... args) {

			if ((factory instanceof JAXRSServiceFactoryBean) &&
				ev.equals(Event.SERVER_CREATED)) {

				if (!_initializedThreadLocal.get()) {
					return;
				}

				_initializedThreadLocal.remove();

				Server server = (Server)args[0];

				Endpoint endpoint = server.getEndpoint();

				ApplicationInfo applicationInfo = (ApplicationInfo)endpoint.get(
					Application.class.getName());

				Application application = applicationInfo.getProvider();

				Class<? extends Application> applicationClass =
					application.getClass();

				Bundle bundle = FrameworkUtil.getBundle(applicationClass);

				if (bundle == null) {
					return;
				}

				BundleContext bundleContext = bundle.getBundleContext();

				ServiceReference<?> serviceReference = getServiceReference(
					bundleContext, application);

				Map<String, Object> properties = new HashMap<>();

				String oAuth2ScopeCheckerType = GetterUtil.getString(
					serviceReference.getProperty("oauth2.scopechecker.type"),
					"request.operation");

				properties.put(
					"oauth2.scopechecker.type", oAuth2ScopeCheckerType);

				applicationInfo.setOverridingProps(properties);

				Dictionary<String, Object> serviceProperties =
					new HashMapDictionary<>();

				for (String key : serviceReference.getPropertyKeys()) {
					if (key.startsWith("service.")) {
						continue;
					}

					serviceProperties.put(
						key, serviceReference.getProperty(key));
				}

				String osgiJAXRSName = GetterUtil.getString(
					serviceProperties.get(
						OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME),
					applicationClass.getName());

				serviceProperties.put(
					OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
					osgiJAXRSName);

				if (oAuth2ScopeCheckerType.equals("request.operation")) {
					processRequestOperation(
						bundleContext, endpoint, serviceProperties);
				}

				if (oAuth2ScopeCheckerType.equals("annotations")) {
					processAnnotation(
						bundleContext, (JAXRSServiceFactoryBean)factory,
						endpoint, serviceProperties);
				}

				registerDescriptors(endpoint, bundle, osgiJAXRSName);
			}
		}

		protected ServiceReference<?> getServiceReference(
			BundleContext bundleContext, Application application) {

			ServiceReference<?>[] serviceReferences;

			try {
				serviceReferences = bundleContext.getAllServiceReferences(
					Application.class.getName(), null);
			}
			catch (InvalidSyntaxException ise) {
				throw new RuntimeException(ise);
			}

			for (ServiceReference<?> serviceReference : serviceReferences) {
				try {
					Object service = bundleContext.getService(serviceReference);

					if (Objects.equals(service, application)) {
						return serviceReference;
					}
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			}

			return null;
		}

		protected void processAnnotation(
			BundleContext bundleContext, JAXRSServiceFactoryBean factory,
			Endpoint endpoint, Dictionary<String, Object> serviceProperties) {

			Collection<String> scopes = new HashSet<>();

			for (Class<?> resourceClass : factory.getResourceClasses()) {
				scopes.addAll(
					RequiresScopeAnnotationFinder.find(
						resourceClass, factory.getBus()));
			}

			ServiceRegistration<ScopeFinder> scopeFinderRegistration =
				bundleContext.registerService(
					ScopeFinder.class, new CollectionScopeFinder(scopes),
					serviceProperties);

			ServiceRegistration<RequestScopeCheckerFilter>
				requestScopeCheckerFilterRegistration =
					bundleContext.registerService(
						RequestScopeCheckerFilter.class,
						_annotationRequestScopeChecker, serviceProperties);

			endpoint.addCleanupHook(
				() -> {
					scopeFinderRegistration.unregister();
					requestScopeCheckerFilterRegistration.unregister();
				});
		}

		protected void processRequestOperation(
			BundleContext bundleContext, Endpoint endpoint,
			Dictionary<String, Object> serviceProperties) {

			ServiceRegistration<ScopeFinder> serviceRegistration =
				bundleContext.registerService(
					ScopeFinder.class,
					new CollectionScopeFinder(
						Arrays.asList(
							HttpMethod.DELETE, HttpMethod.GET, HttpMethod.HEAD,
							HttpMethod.OPTIONS, HttpMethod.POST,
							HttpMethod.PUT)),
					serviceProperties);

			endpoint.addCleanupHook(serviceRegistration::unregister);
		}

		protected void registerDescriptors(
			Endpoint endpoint, Bundle bundle, String osgiJAXRSName) {

			String bundleSymbolicName = bundle.getSymbolicName();

			StringBundler sb = new StringBundler(5);

			sb.append("(&(objectClass=");
			sb.append(ResourceBundleLoader.class.getName());
			sb.append(")(bundle.symbolic.name=");
			sb.append(bundleSymbolicName);
			sb.append(")(resource.bundle.base.name=content.Language))");

			ServiceTracker<ResourceBundleLoader, ResourceBundleLoader>
				serviceTracker = ServiceTrackerFactory.open(
					_bundleContext, sb.toString());

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put(
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME, osgiJAXRSName);

			ServiceRegistration<?> serviceRegistration =
				_bundleContext.registerService(
					new String[] {
						ScopeDescriptor.class.getName(),
						ApplicationDescriptor.class.getName()
					},
					new ApplicationDescriptorsImpl(
						serviceTracker, osgiJAXRSName),
					properties);

			endpoint.addCleanupHook(
				() -> {
					serviceRegistration.unregister();
					serviceTracker.close();
				});
		}

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

				String key = StringBundler.concat(
					"oauth2.application.description.", _osgiJAXRSName);

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

				String key = StringBundler.concat("oauth2.scope.", scope);

				return GetterUtil.getString(
					ResourceBundleUtil.getString(
						resourceBundleLoader.loadResourceBundle(locale), key),
					_defaultScopeDescriptor.describeScope(scope, locale));
			}

			private final String _osgiJAXRSName;
			private final ServiceTracker<?, ResourceBundleLoader>
				_serviceTracker;

		}

	}

}