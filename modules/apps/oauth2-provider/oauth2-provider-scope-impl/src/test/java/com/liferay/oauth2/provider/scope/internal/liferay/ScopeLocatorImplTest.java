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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import com.liferay.oauth2.provider.scope.internal.configuration.ScopeLocatorConfiguration;
import com.liferay.oauth2.provider.scope.internal.liferay.ScopeLocatorImpl.ScopeLocatorConfigurationProvider;
import com.liferay.oauth2.provider.scope.internal.spi.scope.matcher.StrictScopeMatcherFactory;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopedServiceTrackerMap;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandler;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandlerFactory;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;
import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.osgi.framework.ServiceReference;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Stian Sigvartsen
 */
@RunWith(PowerMockRunner.class)
public class ScopeLocatorImplTest extends PowerMockito {

	@Test
	public void testPrefixHandlerFactoryByNameAndCompany() throws Exception {
		String applicationName2 = "com.liferay.test2";

		PrefixHandler defaultPrefixHandler = target -> "default/" + target;

		ScopeFinder scopeFinder = () -> scopesSet1;

		Builder builder = new Builder();

		ScopeLocatorImpl scopeLocatorImpl = builder.withPrefixHandlerFactories(
			propertyAccessor -> defaultPrefixHandler,
			registrator -> {
			}
		).withScopeFinders(
			registrator -> {
				registrator.register(
					_COMPANY_ID, _APPLICATION_NAME, scopeFinder);
				registrator.register(
					_COMPANY_ID, applicationName2, scopeFinder);
			}
		).build();

		Collection<String> application1ScopeAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, _APPLICATION_NAME);

		Collection<String> application2ScopeAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, applicationName2);

		for (String scope : scopesSet1) {
			Assert.assertThat(
				application1ScopeAliases,
				hasItem(defaultPrefixHandler.addPrefix(scope)));

			Assert.assertThat(
				application2ScopeAliases,
				hasItem(defaultPrefixHandler.addPrefix(scope)));
		}

		PrefixHandler appPrefixHandler = target -> "app/" + target;
		PrefixHandler companyPrefixHandler = target -> "company/" + target;

		builder = new Builder();

		scopeLocatorImpl = builder.withPrefixHandlerFactories(
			propertyAccessor -> defaultPrefixHandler,
			registrator -> {
				registrator.register(
					null, _APPLICATION_NAME,
					propertyAccessor -> appPrefixHandler);
				registrator.register(
					_COMPANY_ID, null,
					propertyAccessor -> companyPrefixHandler);
			}
		).withScopeFinders(
			registrator -> {
				registrator.register(
					_COMPANY_ID, _APPLICATION_NAME, scopeFinder);
				registrator.register(
					_COMPANY_ID, applicationName2, scopeFinder);
			}
		).build();

		application1ScopeAliases = scopeLocatorImpl.getScopeAliases(
			_COMPANY_ID, _APPLICATION_NAME);

		application2ScopeAliases = scopeLocatorImpl.getScopeAliases(
			_COMPANY_ID, applicationName2);

		for (String scope : scopesSet1) {
			Assert.assertThat(
				application1ScopeAliases,
				hasItem(appPrefixHandler.addPrefix(scope)));

			Assert.assertThat(
				application2ScopeAliases,
				hasItem(companyPrefixHandler.addPrefix(scope)));
		}
	}

	@Test
	public void testScopeFinderByName() throws Exception {
		String applicationName2 = "com.liferay.test2";
		ScopeFinder application1ScopeFinder = () -> scopesSet1;
		ScopeFinder application2ScopeFinder = () -> scopedSet2;

		Builder builder = new Builder();

		ScopeLocatorImpl scopeLocatorImpl = builder.withScopeFinders(
			registrator -> {
				registrator.register(
					_COMPANY_ID, _APPLICATION_NAME, application1ScopeFinder);
				registrator.register(
					_COMPANY_ID, applicationName2, application2ScopeFinder);
			}
		).build();

		Collection<String> application1ScopeAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, _APPLICATION_NAME);

		Collection<String> application2ScopesAliasesDefault =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, applicationName2);

		for (String scope : scopesSet1) {
			Assert.assertThat(application1ScopeAliases, hasItem(scope));
		}

		for (String scope : scopedSet2) {
			Assert.assertThat(application2ScopesAliasesDefault, hasItem(scope));
		}

		Assert.assertNotEquals(
			application1ScopeAliases, application2ScopesAliasesDefault);
	}

	@Test
	public void testScopeMapperByNameAndCompany() throws Exception {
		String applicationName2 = "com.liferay.test2";

		ScopeMapper defaultScopeMapper = ScopeMapper.PASS_THROUGH_SCOPE_MAPPER;

		ScopeFinder scopeFinder = () -> scopesSet1;

		Builder builder = new Builder();

		ScopeLocatorImpl scopeLocatorImpl = builder.withScopeMappers(
			defaultScopeMapper,
			registrator -> {
			}
		).withScopeFinders(
			registrator -> {
				registrator.register(
					_COMPANY_ID, _APPLICATION_NAME, scopeFinder);
				registrator.register(
					_COMPANY_ID, applicationName2, scopeFinder);
			}
		).build();

		Collection<String> application1ScopeAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, _APPLICATION_NAME);

		Collection<String> application2ScopeAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, applicationName2);

		for (String scope : scopesSet1) {
			Assert.assertThat(application1ScopeAliases, hasItem(scope));

			Assert.assertThat(application2ScopeAliases, hasItem(scope));
		}

		ScopeMapper appScopeMapper = scope -> Collections.singleton(
			"app/" + scope);
		ScopeMapper companyScopeMapper = scope -> Collections.singleton(
			"company/" + scope);

		builder = new Builder();

		scopeLocatorImpl = builder.withScopeMappers(
			defaultScopeMapper,
			registrator -> {
				registrator.register(null, _APPLICATION_NAME, appScopeMapper);
				registrator.register(_COMPANY_ID, null, companyScopeMapper);
			}
		).withScopeFinders(
			registrator -> {
				registrator.register(
					_COMPANY_ID, _APPLICATION_NAME, scopeFinder);
				registrator.register(
					_COMPANY_ID, applicationName2, scopeFinder);
			}
		).build();

		Collection<String> application1ScopesAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, _APPLICATION_NAME);

		Collection<String> application2ScopesAliases =
			scopeLocatorImpl.getScopeAliases(_COMPANY_ID, applicationName2);

		for (String scope : scopesSet1) {
			Assert.assertThat(
				application1ScopesAliases,
				hasItems(
					appScopeMapper.map(
						scope
					).toArray(
						new String[0]
					)));

			Assert.assertThat(
				application2ScopesAliases,
				hasItems(
					companyScopeMapper.map(
						scope
					).toArray(
						new String[0]
					)));
		}
	}

	@Test
	public void testScopeMatcherByCompany() throws Exception {
		String applicationName2 = "com.liferay.test2";

		ScopeFinder service = () -> scopesSet1;

		Set<String> matchScopes = Collections.singleton("everything.readonly");

		ScopeMatcherFactory explicitScopeMatcherFactory = scopeAlias ->
			scope -> scope.equals(scopeAlias) && matchScopes.contains(scope);

		Builder builder = new Builder();

		ScopeLocatorImpl scopeLocatorImpl = builder.withScopeFinders(
			registrator -> {
				registrator.register(_COMPANY_ID, _APPLICATION_NAME, service);
				registrator.register(_COMPANY_ID, applicationName2, service);
			}
		).withScopeMatcherFactories(
			scopeAlias -> scopeAlias::equals,
			registrator -> registrator.register(
				String.valueOf(_COMPANY_ID), explicitScopeMatcherFactory)
		).build();

		Collection<LiferayOAuth2Scope> matchedLiferayOAuth2Scopes =
			scopeLocatorImpl.getLiferayOAuth2Scopes(
				_COMPANY_ID, "everything", _APPLICATION_NAME);

		Set<String> matchedScopes = _getScopes(matchedLiferayOAuth2Scopes);

		Assert.assertFalse(matchedScopes.contains("everything"));

		matchedLiferayOAuth2Scopes = scopeLocatorImpl.getLiferayOAuth2Scopes(
			_COMPANY_ID, "everything.readonly", _APPLICATION_NAME);

		matchedScopes = _getScopes(matchedLiferayOAuth2Scopes);

		Assert.assertTrue(matchedScopes.contains("everything.readonly"));
	}

	@Test
	public void testScopeMatcherIsolatedFromPrefixHanderFactory()
		throws Exception {

		PrefixHandlerFactory testPrefixHandlerFactory =
			propertyAccessor -> target -> "test/" + target;

		final ScopeMatcherFactory scopeMatcherFactory = Mockito.spy(
			new StrictScopeMatcherFactory());

		Builder builder = new Builder();

		ScopeLocatorImpl scopeLocatorImpl = builder.withPrefixHandlerFactories(
			propertyAccessor -> PrefixHandler.PASS_THROUGH_PREFIX_HANDLER,
			registrator -> registrator.register(
				_COMPANY_ID, _APPLICATION_NAME, testPrefixHandlerFactory)
		).withScopeMatcherFactories(
			scopeAlias -> scopeAlias::equals,
			registrator -> registrator.register(
				String.valueOf(_COMPANY_ID), scopeMatcherFactory)
		).withScopeFinders(
			registrator -> registrator.register(
				_COMPANY_ID, _APPLICATION_NAME, () -> scopesSet1)
		).build();

		Collection<LiferayOAuth2Scope> matchedLiferayOAuth2Scopes =
			scopeLocatorImpl.getLiferayOAuth2Scopes(
				_COMPANY_ID, "test/everything", _APPLICATION_NAME);

		Mockito.verify(
			scopeMatcherFactory, Mockito.atLeast(1)
		).create(
			"everything"
		);

		Set<String> matchedScopes = _getScopes(matchedLiferayOAuth2Scopes);

		Assert.assertTrue(matchedScopes.contains("everything"));
	}

	protected final Set<String> scopedSet2 = new HashSet<>(
		Arrays.asList("GET", "POST"));
	protected final Set<String> scopesSet1 = new HashSet<>(
		Arrays.asList("everything", "everything.readonly"));

	private static void _set(Object object, String fieldName, Object value) {
		Class<?> clazz = object.getClass();

		try {
			Field field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			field.set(object, value);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private Set<String> _getScopes(
		Collection<LiferayOAuth2Scope> liferayOAuth2Scopes) {

		Stream<LiferayOAuth2Scope> stream = liferayOAuth2Scopes.stream();

		return stream.flatMap(
			liferayOAuth2Scope -> Collections.singleton(
				liferayOAuth2Scope.getScope()
			).stream()
		).collect(
			Collectors.toSet()
		);
	}

	private static final String _APPLICATION_NAME = "com.liferay.test1";

	private static final long _COMPANY_ID = 1;

	private class Builder {

		public ScopeLocatorImpl build() throws IllegalAccessException {
			if (!_scopeMatcherFactoriesInitialized) {
				withScopeMatcherFactories(
					scopeAlias -> scopeAlias::equals,
					registrator -> {
					});
			}

			if (!_prefixHandlerFactoriesInitialized) {
				withPrefixHandlerFactories(
					propertyAccessor ->
						PrefixHandler.PASS_THROUGH_PREFIX_HANDLER,
					registrator -> {
					});
			}

			if (!_scopeMappersInitialized) {
				withScopeMappers(
					ScopeMapper.PASS_THROUGH_SCOPE_MAPPER,
					registrator -> {
					});
			}

			if (!_scopeFindersInitialized) {
				withScopeFinders(
					registrator -> {
					});
			}

			if (!_scopeLocatorConfigurationProvidersInitialized) {
				withScopeLocatorConfigurationProviders(
					() -> new TestScopeLocatorConfiguration(),
					registrator -> {
					});
			}

			return _scopeLocatorImpl;
		}

		public Builder withPrefixHandlerFactories(
				PrefixHandlerFactory defaultPrefixHandlerFactory,
				CompanyAndKeyConfigurator<PrefixHandlerFactory> configurator)
			throws IllegalAccessException {

			ScopedServiceTrackerMap<PrefixHandlerFactory>
				prefixHandlerFactoriesScopedServiceTrackerMap =
					_prepareScopedServiceTrackerMapMock(
						defaultPrefixHandlerFactory, configurator);

			_set(
				_scopeLocatorImpl, "_defaultPrefixHandlerFactory",
				defaultPrefixHandlerFactory);

			_scopeLocatorImpl.setPrefixHandlerFactoriesScopedServiceTrackerMap(
				prefixHandlerFactoriesScopedServiceTrackerMap);

			_prefixHandlerFactoriesInitialized = true;

			return this;
		}

		public Builder withScopeFinders(
				CompanyAndKeyConfigurator<ScopeFinder> configurator)
			throws IllegalAccessException, IllegalArgumentException {

			ServiceTrackerMap
				<String, ServiceReferenceServiceTuple<?, ScopeFinder>>
					scopeFinderByNameServiceTrackerMap = Mockito.mock(
						ServiceTrackerMap.class);

			_scopeLocatorImpl.setScopeFinderByNameServiceTrackerMap(
				scopeFinderByNameServiceTrackerMap);

			ScopedServiceTrackerMap<ScopeFinder>
				scopeFindersScopedServiceTrackerMap = Mockito.mock(
					ScopedServiceTrackerMap.class);

			_scopeLocatorImpl.setScopeFindersScopedServiceTrackerMap(
				scopeFindersScopedServiceTrackerMap);

			configurator.configure(
				(companyId, applicationName, service) -> {
					ServiceReference<?> serviceReference = Mockito.mock(
						ServiceReference.class);

					when(
						scopeFinderByNameServiceTrackerMap.getService(
							applicationName)
					).thenReturn(
						new ServiceReferenceServiceTuple(
							serviceReference, service)
					);

					when(
						scopeFindersScopedServiceTrackerMap.getService(
							companyId, applicationName)
					).thenReturn(
						service
					);
				});

			_scopeFindersInitialized = true;

			return this;
		}

		public Builder withScopeLocatorConfigurationProviders(
				ScopeLocatorConfigurationProvider
					defaultScopeLocatorConfigurationProvider,
				CompanyAndKeyConfigurator<ScopeLocatorConfigurationProvider>
					configurator)
			throws IllegalAccessException {

			ScopedServiceTrackerMap<ScopeLocatorConfigurationProvider>
				scopeLocatorConfigurationProvidersScopeServiceTrackerMap =
					_prepareScopedServiceTrackerMapMock(
						defaultScopeLocatorConfigurationProvider, configurator);

			_set(
				_scopeLocatorImpl, "_defaultScopeLocatorConfigurationProvider",
				defaultScopeLocatorConfigurationProvider);

			_scopeLocatorImpl.
				setScopeLocatorConfigurationProvidersScopedServiceTrackerMap(
					scopeLocatorConfigurationProvidersScopeServiceTrackerMap);

			_scopeLocatorConfigurationProvidersInitialized = true;

			return this;
		}

		public Builder withScopeMappers(
				ScopeMapper defaultScopeMapper,
				CompanyAndKeyConfigurator<ScopeMapper> configurator)
			throws IllegalAccessException {

			ScopedServiceTrackerMap<ScopeMapper>
				scopeMappersScopedServiceTrackerMap =
					_prepareScopedServiceTrackerMapMock(
						defaultScopeMapper, configurator);

			_set(_scopeLocatorImpl, "_defaultScopeMapper", defaultScopeMapper);

			_scopeLocatorImpl.setScopeMappersScopedServiceTrackerMap(
				scopeMappersScopedServiceTrackerMap);

			_scopeMappersInitialized = true;

			return this;
		}

		public Builder withScopeMatcherFactories(
				ScopeMatcherFactory defaultScopeMatcherFactory,
				KeyConfigurator<ScopeMatcherFactory> configurator)
			throws IllegalAccessException, IllegalArgumentException {

			ServiceTrackerMap<String, ScopeMatcherFactory>
				scopeMatcherFactoriesServiceTrackerMap = Mockito.mock(
					ServiceTrackerMap.class);

			_scopeLocatorImpl.setDefaultScopeMatcherFactory(
				defaultScopeMatcherFactory);

			_scopeLocatorImpl.setScopeMatcherFactoriesServiceTrackerMap(
				scopeMatcherFactoriesServiceTrackerMap);

			configurator.configure(
				(companyId, service) -> when(
					scopeMatcherFactoriesServiceTrackerMap.getService(companyId)
				).thenReturn(
					service
				));

			_scopeMatcherFactoriesInitialized = true;

			return this;
		}

		private <T> ScopedServiceTrackerMap<T>
			_prepareScopedServiceTrackerMapMock(
				T defaultService, CompanyAndKeyConfigurator<T> configurator) {

			ScopedServiceTrackerMap<T> scopedServiceTrackerMap = Mockito.mock(
				ScopedServiceTrackerMap.class);

			TestScopedServiceTrackerMap<T> testScopedServiceTrackerMap =
				new TestScopedServiceTrackerMap<>(defaultService);

			Answer<T> answer = invocation -> {
				long companyId = invocation.getArgumentAt(0, Long.class);
				String key = invocation.getArgumentAt(1, String.class);

				return testScopedServiceTrackerMap.getService(companyId, key);
			};

			when(
				scopedServiceTrackerMap.getService(anyLong(), anyString())
			).thenAnswer(
				answer
			);

			configurator.configure(testScopedServiceTrackerMap::setService);

			return scopedServiceTrackerMap;
		}

		private boolean _prefixHandlerFactoriesInitialized;
		private boolean _scopeFindersInitialized;
		private boolean _scopeLocatorConfigurationProvidersInitialized;
		private final ScopeLocatorImpl _scopeLocatorImpl =
			new ScopeLocatorImpl();
		private boolean _scopeMappersInitialized;
		private boolean _scopeMatcherFactoriesInitialized;

	}

	private interface CompanyAndKeyConfigurator<T> {

		public void configure(CompanyAndKeyRegistrator<T> registrator);

	}

	private interface CompanyAndKeyRegistrator<T> {

		public void register(Long companyId, String key, T service);

	}

	private interface KeyConfigurator<T> {

		public void configure(KeyRegistrator<T> registrator);

	}

	private interface KeyRegistrator<T> {

		public void register(String key, T service);

	}

	private class TestScopeLocatorConfiguration
		implements ScopeLocatorConfiguration {

		@Override
		public boolean includeScopesImpliedBeforeScopeMapping() {
			return true;
		}

		@Override
		public String osgiJaxRsName() {
			return "Default";
		}

	}

}