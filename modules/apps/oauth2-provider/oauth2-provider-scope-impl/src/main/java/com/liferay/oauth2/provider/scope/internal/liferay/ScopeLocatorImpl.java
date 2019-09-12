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

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.oauth2.provider.scope.internal.configuration.ScopeLocatorConfiguration;
import com.liferay.oauth2.provider.scope.internal.constants.OAuth2ProviderScopeConstants;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopedServiceTrackerMap;
import com.liferay.oauth2.provider.scope.liferay.ScopedServiceTrackerMapFactory;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandler;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandlerFactory;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcher;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;
import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = ScopeLocator.class)
public class ScopeLocatorImpl implements ScopeLocator {

	@Override
	public LiferayOAuth2Scope getLiferayOAuth2Scope(
		long companyId, String applicationName, String scope) {

		ServiceReferenceServiceTuple<?, ScopeFinder>
			serviceReferenceServiceTuple =
				_scopeFinderByNameServiceTrackerMap.getService(applicationName);

		if (serviceReferenceServiceTuple == null) {
			return null;
		}

		Bundle bundle = getBundle(
			serviceReferenceServiceTuple.getServiceReference());

		return new LiferayOAuth2ScopeImpl(applicationName, bundle, scope);
	}

	@Override
	public Collection<LiferayOAuth2Scope> getLiferayOAuth2Scopes(
		long companyId) {

		Collection<LiferayOAuth2Scope> liferayOAuth2Scopes = new ArrayList<>();

		for (String key : _scopeFinderByNameServiceTrackerMap.keySet()) {
			ScopeFinder scopeFinder =
				_scopeFindersScopedServiceTrackerMap.getService(companyId, key);

			ServiceReferenceServiceTuple<?, ScopeFinder>
				serviceReferenceServiceTuple =
					_scopeFinderByNameServiceTrackerMap.getService(key);

			if (scopeFinder == null) {
				scopeFinder = serviceReferenceServiceTuple.getService();
			}

			Bundle bundle = getBundle(
				serviceReferenceServiceTuple.getServiceReference());

			for (String scope : scopeFinder.findScopes()) {
				liferayOAuth2Scopes.add(
					new LiferayOAuth2ScopeImpl(key, bundle, scope));
			}
		}

		return liferayOAuth2Scopes;
	}

	@Override
	public Collection<LiferayOAuth2Scope> getLiferayOAuth2Scopes(
		long companyId, String scopesAlias) {

		Set<String> names = _scopeFinderByNameServiceTrackerMap.keySet();

		Collection<LiferayOAuth2Scope> liferayOAuth2Scopes = new ArrayList<>();

		for (String name : names) {
			liferayOAuth2Scopes.addAll(
				getLiferayOAuth2Scopes(companyId, scopesAlias, name));
		}

		return liferayOAuth2Scopes;
	}

	@Override
	public Collection<LiferayOAuth2Scope> getLiferayOAuth2Scopes(
		long companyId, String scopesAlias, String applicationName) {

		ScopeFinder scopeFinder =
			_scopeFindersScopedServiceTrackerMap.getService(
				companyId, applicationName);

		Collection<String> scopes = scopeFinder.findScopes();

		if (scopes.isEmpty()) {
			return Collections.emptyList();
		}

		ServiceReferenceServiceTuple<?, ScopeFinder>
			serviceReferenceServiceTuple =
				_scopeFinderByNameServiceTrackerMap.getService(applicationName);

		PrefixHandlerFactory prefixHandlerFactory =
			_prefixHandlerFactoriesScopedServiceTrackerMap.getService(
				companyId, applicationName);

		ServiceReference<?> serviceReference =
			serviceReferenceServiceTuple.getServiceReference();

		Bundle bundle = getBundle(serviceReference);

		Set<LiferayOAuth2Scope> locatedScopes = new HashSet<>();

		Map<String, Set<String>> mappedScopeToUnmappedScopes = new HashMap<>();
		Map<String, Boolean> matchCache = new HashMap<>();
		PrefixHandler prefixHandler = prefixHandlerFactory.create(
			serviceReference::getProperty);
		Queue<String> queue = new LinkedList<>();
		ScopeMapper scopeMapper =
			_scopeMappersScopedServiceTrackerMap.getService(
				companyId, applicationName);
		ScopeMatcherFactory scopeMatcherFactory = getScopeMatcherFactory(
			companyId);

		for (String scope : scopes) {
			for (String mappedScope : scopeMapper.map(scope)) {
				boolean matched = matchCache.computeIfAbsent(
					mappedScope,
					key -> scopeMatchesScopesAlias(
						key, scopeMatcherFactory, prefixHandler, scopesAlias));

				if (matched) {
					queue.add(scope);
				}

				Set<String> unmappedScopes =
					mappedScopeToUnmappedScopes.computeIfAbsent(
						mappedScope, key -> new HashSet());

				unmappedScopes.add(scope);
			}
		}

		ScopeLocatorConfigurationProvider scopeLocatorConfigurationProvider =
			_scopeLocatorConfigurationProvidersScopedServiceTrackerMap.
				getService(companyId, applicationName);

		ScopeLocatorConfiguration scopeLocatorConfiguration =
			scopeLocatorConfigurationProvider.getScopeLocatorConfiguration();

		Set<String> processedScopes = new HashSet<>();

		for (String scope = queue.poll(); scope != null; scope = queue.poll()) {
			processedScopes.add(scope);

			locatedScopes.add(
				new LiferayOAuth2ScopeImpl(applicationName, bundle, scope));

			if (!scopeLocatorConfiguration.
					includeScopesImpliedBeforeScopeMapping()) {

				continue;
			}

			ScopeMatcher scopeMatcher = scopeMatcherFactory.create(scope);

			mappedScopeToUnmappedScopes.forEach(
				(mappedScope, unmappedScopes) -> {
					boolean matched = matchCache.compute(
						mappedScope,
						(key, matches) -> {
							if (!matches) {
								return scopeMatcher.match(mappedScope);
							}

							return matches;
						});

					if (!matched) {
						return;
					}

					for (String unmappedScope : unmappedScopes) {
						if (!processedScopes.contains(unmappedScope)) {
							queue.add(unmappedScope);
						}
					}
				});
		}

		return locatedScopes;
	}

	@Override
	public Collection<String> getScopeAliases(long companyId) {
		Collection<String> scopesAliases = new HashSet<>();

		Set<String> applicationNames =
			_scopeFinderByNameServiceTrackerMap.keySet();

		for (String applicationName : applicationNames) {
			scopesAliases.addAll(getScopeAliases(companyId, applicationName));
		}

		return scopesAliases;
	}

	@Override
	public Collection<String> getScopeAliases(
		long companyId, String applicationName) {

		ServiceReferenceServiceTuple<?, ScopeFinder>
			serviceReferenceServiceTuple =
				_scopeFinderByNameServiceTrackerMap.getService(applicationName);

		if (serviceReferenceServiceTuple == null) {
			return Collections.emptyList();
		}

		PrefixHandlerFactory prefixHandlerFactory =
			_prefixHandlerFactoriesScopedServiceTrackerMap.getService(
				companyId, applicationName);
		ServiceReference<?> serviceReference =
			serviceReferenceServiceTuple.getServiceReference();

		PrefixHandler prefixHandler = prefixHandlerFactory.create(
			serviceReference::getProperty);

		ScopeFinder scopeFinder =
			_scopeFindersScopedServiceTrackerMap.getService(
				companyId, applicationName);

		ScopeMapper scopeMapper =
			_scopeMappersScopedServiceTrackerMap.getService(
				companyId, applicationName);
		Collection<String> scopes = scopeFinder.findScopes();
		Collection<String> scopesAliases = new ArrayList<>();

		for (String scope : scopes) {
			Set<String> mappedScopes = scopeMapper.map(scope);

			for (String mappedScope : mappedScopes) {
				String externalAlias = prefixHandler.addPrefix(mappedScope);

				scopesAliases.add(externalAlias);
			}
		}

		return scopesAliases;
	}

	public interface ScopeLocatorConfigurationProvider {

		public ScopeLocatorConfiguration getScopeLocatorConfiguration();

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		setPrefixHandlerFactoriesScopedServiceTrackerMap(
			_scopedServiceTrackerMapFactory.create(
				bundleContext, PrefixHandlerFactory.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
				() -> {
					PrefixHandlerFactory prefixHandlerFactory =
						_defaultPrefixHandlerFactory;

					if (prefixHandlerFactory != null) {
						return prefixHandlerFactory;
					}

					return propertyAccessor ->
						PrefixHandler.PASS_THROUGH_PREFIX_HANDLER;
				}));
		setScopeFindersScopedServiceTrackerMap(
			_scopedServiceTrackerMapFactory.create(
				bundleContext, ScopeFinder.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
				() -> Collections::emptySet));
		setScopeLocatorConfigurationProvidersScopedServiceTrackerMap(
			_scopedServiceTrackerMapFactory.create(
				bundleContext, ScopeLocatorConfigurationProvider.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
				() -> {
					if (_defaultScopeLocatorConfigurationProvider != null) {
						return _defaultScopeLocatorConfigurationProvider;
					}

					return () -> _defaultScopeLocatorConfiguration;
				}));
		setScopeMappersScopedServiceTrackerMap(
			_scopedServiceTrackerMapFactory.create(
				bundleContext, ScopeMapper.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
				() -> {
					ScopeMapper scopeMapper = _defaultScopeMapper;

					if (scopeMapper != null) {
						return scopeMapper;
					}

					return ScopeMapper.PASS_THROUGH_SCOPE_MAPPER;
				}));
		setScopeMatcherFactoriesServiceTrackerMap(
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScopeMatcherFactory.class, "company.id"));
		setScopeFinderByNameServiceTrackerMap(
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScopeFinder.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
				new ScopeFinderServiceTupleServiceTrackerCustomizer(
					bundleContext)));
	}

	@Deactivate
	protected void deactivate() {
		_scopeFinderByNameServiceTrackerMap.close();
		_prefixHandlerFactoriesScopedServiceTrackerMap.close();
		_scopeFindersScopedServiceTrackerMap.close();
		_scopeLocatorConfigurationProvidersScopedServiceTrackerMap.close();
		_scopeMappersScopedServiceTrackerMap.close();
		_scopeMatcherFactoriesServiceTrackerMap.close();
	}

	protected Bundle getBundle(ServiceReference<?> serviceReference) {
		Object property = serviceReference.getProperty(
			"original.service.bundleid");

		if (property == null) {
			return serviceReference.getBundle();
		}

		long bundleId = GetterUtil.getLong(property, -1L);

		if (bundleId == -1) {
			return serviceReference.getBundle();
		}

		Bundle bundle = _bundleContext.getBundle(bundleId);

		if (bundle == null) {
			return serviceReference.getBundle();
		}

		return bundle;
	}

	protected ScopeMatcherFactory getScopeMatcherFactory(long companyId) {
		ScopeMatcherFactory scopeMatcherFactory =
			_scopeMatcherFactoriesServiceTrackerMap.getService(
				String.valueOf(companyId));

		if (scopeMatcherFactory == null) {
			return _defaultScopeMatcherFactory;
		}

		return scopeMatcherFactory;
	}

	protected boolean scopeMatchesScopesAlias(
		String scope, ScopeMatcherFactory scopeMatcherFactory,
		PrefixHandler prefixHandler, String scopesAlias) {

		String prefixedScope = prefixHandler.addPrefix(scope);

		if (scope.length() > prefixedScope.length()) {
			return false;
		}

		String prefix = prefixedScope.substring(
			0, prefixedScope.length() - scope.length());

		if (!scopesAlias.startsWith(prefix)) {
			return false;
		}

		ScopeMatcher scopeMatcher = scopeMatcherFactory.create(
			scopesAlias.substring(prefix.length()));

		return scopeMatcher.match(scope);
	}

	@Reference(name = "default", unbind = "-")
	protected void setDefaultScopeMatcherFactory(
		ScopeMatcherFactory defaultScopeMatcherFactory) {

		_defaultScopeMatcherFactory = defaultScopeMatcherFactory;
	}

	protected void setPrefixHandlerFactoriesScopedServiceTrackerMap(
		ScopedServiceTrackerMap<PrefixHandlerFactory>
			prefixHandlerFactoriesScopedServiceTrackerMap) {

		_prefixHandlerFactoriesScopedServiceTrackerMap =
			prefixHandlerFactoriesScopedServiceTrackerMap;
	}

	@Reference(unbind = "-")
	protected void setScopedServiceTrackerMapFactory(
		ScopedServiceTrackerMapFactory scopedServiceTrackerMapFactory) {

		_scopedServiceTrackerMapFactory = scopedServiceTrackerMapFactory;
	}

	protected void setScopeFinderByNameServiceTrackerMap(
		ServiceTrackerMap<String, ServiceReferenceServiceTuple<?, ScopeFinder>>
			scopeFinderByNameServiceTrackerMap) {

		_scopeFinderByNameServiceTrackerMap =
			scopeFinderByNameServiceTrackerMap;
	}

	protected void setScopeFindersScopedServiceTrackerMap(
		ScopedServiceTrackerMap<ScopeFinder>
			scopeFindersScopedServiceTrackerMap) {

		_scopeFindersScopedServiceTrackerMap =
			scopeFindersScopedServiceTrackerMap;
	}

	protected void setScopeLocatorConfigurationProvidersScopedServiceTrackerMap(
		ScopedServiceTrackerMap<ScopeLocatorConfigurationProvider>
			scopeLocatorConfigurationProvidersScopedServiceTrackerMap) {

		_scopeLocatorConfigurationProvidersScopedServiceTrackerMap =
			scopeLocatorConfigurationProvidersScopedServiceTrackerMap;
	}

	protected void setScopeMappersScopedServiceTrackerMap(
		ScopedServiceTrackerMap<ScopeMapper>
			scopeMappersScopedServiceTrackerMap) {

		_scopeMappersScopedServiceTrackerMap =
			scopeMappersScopedServiceTrackerMap;
	}

	protected void setScopeMatcherFactoriesServiceTrackerMap(
		ServiceTrackerMap<String, ScopeMatcherFactory>
			scopeMatcherFactoriesServiceTrackerMap) {

		_scopeMatcherFactoriesServiceTrackerMap =
			scopeMatcherFactoriesServiceTrackerMap;
	}

	private BundleContext _bundleContext;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(osgi.jaxrs.name=Default)"
	)
	private volatile PrefixHandlerFactory _defaultPrefixHandlerFactory;

	private final ScopeLocatorConfiguration _defaultScopeLocatorConfiguration =
		ConfigurableUtil.createConfigurable(
			ScopeLocatorConfiguration.class, Collections.emptyMap());

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(osgi.jaxrs.name=Default)"
	)
	private volatile ScopeLocatorConfigurationProvider
		_defaultScopeLocatorConfigurationProvider;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(osgi.jaxrs.name=Default)"
	)
	private volatile ScopeMapper _defaultScopeMapper;

	private ScopeMatcherFactory _defaultScopeMatcherFactory;
	private ScopedServiceTrackerMap<PrefixHandlerFactory>
		_prefixHandlerFactoriesScopedServiceTrackerMap;
	private ScopedServiceTrackerMapFactory _scopedServiceTrackerMapFactory;
	private ServiceTrackerMap
		<String, ServiceReferenceServiceTuple<?, ScopeFinder>>
			_scopeFinderByNameServiceTrackerMap;
	private ScopedServiceTrackerMap<ScopeFinder>
		_scopeFindersScopedServiceTrackerMap;
	private ScopedServiceTrackerMap<ScopeLocatorConfigurationProvider>
		_scopeLocatorConfigurationProvidersScopedServiceTrackerMap;
	private ScopedServiceTrackerMap<ScopeMapper>
		_scopeMappersScopedServiceTrackerMap;
	private ServiceTrackerMap<String, ScopeMatcherFactory>
		_scopeMatcherFactoriesServiceTrackerMap;

	private static class ScopeFinderServiceTupleServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ScopeFinder, ServiceReferenceServiceTuple<?, ScopeFinder>> {

		public ScopeFinderServiceTupleServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceReferenceServiceTuple<?, ScopeFinder> addingService(
			ServiceReference<ScopeFinder> serviceReference) {

			ScopeFinder scopeFinder = _bundleContext.getService(
				serviceReference);

			return new ServiceReferenceServiceTuple<>(
				serviceReference, scopeFinder);
		}

		@Override
		public void modifiedService(
			ServiceReference<ScopeFinder> serviceReference,
			ServiceReferenceServiceTuple<?, ScopeFinder>
				serviceReferenceServiceTuple) {
		}

		@Override
		public void removedService(
			ServiceReference<ScopeFinder> serviceReference,
			ServiceReferenceServiceTuple<?, ScopeFinder>
				serviceReferenceServiceTuple) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}