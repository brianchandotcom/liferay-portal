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
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

		ScopeFinder scopeFinder = _scopedScopeFinders.getService(
			companyId, applicationName);

		Collection<String> scopes = scopeFinder.findScopes();

		if (scopes.isEmpty()) {
			return Collections.emptyList();
		}

		ServiceReferenceServiceTuple<?, ScopeFinder>
			serviceReferenceServiceTuple =
				_scopeFinderByNameServiceTrackerMap.getService(applicationName);

		PrefixHandlerFactory prefixHandlerFactory =
			_scopedPrefixHandlerFactories.getService(
				companyId, applicationName);
		ServiceReference<?> serviceReference =
			serviceReferenceServiceTuple.getServiceReference();

		Bundle bundle = getBundle(serviceReference);

		Collection<LiferayOAuth2Scope> locatedScopes = new HashSet<>(
			scopes.size());
		Map<String, Boolean> matchCache = new HashMap<>();
		PrefixHandler prefixHandler = prefixHandlerFactory.create(
			serviceReference::getProperty);
		ScopeMapper scopeMapper = _scopedScopeMapper.getService(
			companyId, applicationName);
		ScopeMatcherFactory scopeMatcherFactory = getScopeMatcherFactory(
			companyId);

		for (String scope : scopes) {
			for (String mappedScope : scopeMapper.map(scope)) {
				boolean matched = matchCache.computeIfAbsent(
					mappedScope,
					input -> scopeMatchesScopesAlias(
						input, scopeMatcherFactory, prefixHandler,
						scopesAlias));

				if (matched) {
					locatedScopes.add(
						new LiferayOAuth2ScopeImpl(
							applicationName, bundle, scope));
				}
			}
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

		PrefixHandlerFactory prefixHandlerFactory =
			_scopedPrefixHandlerFactories.getService(
				companyId, applicationName);
		ServiceReference<?> serviceReference =
			serviceReferenceServiceTuple.getServiceReference();

		PrefixHandler prefixHandler = prefixHandlerFactory.create(
			serviceReference::getProperty);

		ScopeFinder scopeFinder = _scopedScopeFinders.getService(
			companyId, applicationName);
		ScopeMapper scopeMapper = _scopedScopeMapper.getService(
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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		setScopedPrefixHandlerFactories(
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
						PrefixHandler.PASSTHROUGH_PREFIXHANDLER;
				}));
		setScopedScopeFinders(
			_scopedServiceTrackerMapFactory.create(
				bundleContext, ScopeFinder.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME, () -> null));
		setScopedScopeMapper(
			_scopedServiceTrackerMapFactory.create(
				bundleContext, ScopeMapper.class,
				OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
				() -> {
					ScopeMapper scopeMapper = _defaultScopeMapper;

					if (scopeMapper != null) {
						return scopeMapper;
					}

					return ScopeMapper.PASSTHROUGH_SCOPEMAPPER;
				}));
		setScopedScopeMatcherFactories(
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
		_scopedPrefixHandlerFactories.close();
		_scopedScopeFinders.close();
		_scopedScopeMapper.close();
		_scopedScopeMatcherFactories.close();
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
			_scopedScopeMatcherFactories.getService(String.valueOf(companyId));

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

	protected void setScopedPrefixHandlerFactories(
		ScopedServiceTrackerMap<PrefixHandlerFactory>
			scopedPrefixHandlerFactories) {

		_scopedPrefixHandlerFactories = scopedPrefixHandlerFactories;
	}

	protected void setScopedScopeFinders(
		ScopedServiceTrackerMap<ScopeFinder> scopedScopeFinders) {

		_scopedScopeFinders = scopedScopeFinders;
	}

	protected void setScopedScopeMapper(
		ScopedServiceTrackerMap<ScopeMapper> scopedScopeMapper) {

		_scopedScopeMapper = scopedScopeMapper;
	}

	protected void setScopedScopeMatcherFactories(
		ServiceTrackerMap<String, ScopeMatcherFactory>
			scopedScopeMatcherFactories) {

		_scopedScopeMatcherFactories = scopedScopeMatcherFactories;
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

	private BundleContext _bundleContext;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(osgi.jaxrs.name=Default)"
	)
	private volatile PrefixHandlerFactory _defaultPrefixHandlerFactory;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(osgi.jaxrs.name=Default)"
	)
	private volatile ScopeMapper _defaultScopeMapper;

	private ScopeMatcherFactory _defaultScopeMatcherFactory;
	private ScopedServiceTrackerMap<PrefixHandlerFactory>
		_scopedPrefixHandlerFactories;
	private ScopedServiceTrackerMap<ScopeFinder> _scopedScopeFinders;
	private ScopedServiceTrackerMap<ScopeMapper> _scopedScopeMapper;
	private ServiceTrackerMap<String, ScopeMatcherFactory>
		_scopedScopeMatcherFactories;
	private ScopedServiceTrackerMapFactory _scopedServiceTrackerMapFactory;
	private ServiceTrackerMap
		<String, ServiceReferenceServiceTuple<?, ScopeFinder>>
			_scopeFinderByNameServiceTrackerMap;

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