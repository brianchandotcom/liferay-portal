/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.action.FDSBulkActions;
import com.liferay.frontend.data.set.action.FDSBulkActionsRegistry;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.action.FDSCreationMenuRegistry;
import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.action.FDSItemsActionsRegistry;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseClientExtensionFDSFilter;
import com.liferay.frontend.data.set.filter.BaseDateRangeFDSFilter;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.DateFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.frontend.data.set.internal.SystemFDSEntryRegistryImpl;
import com.liferay.frontend.data.set.internal.action.FDSBulkActionsRegistryImpl;
import com.liferay.frontend.data.set.internal.action.FDSCreationMenuRegistryImpl;
import com.liferay.frontend.data.set.internal.action.FDSItemsActionsRegistryImpl;
import com.liferay.frontend.data.set.internal.filter.ClientExtensionFDSFilterContextContributor;
import com.liferay.frontend.data.set.internal.filter.DateRangeFDSFilterContextContributor;
import com.liferay.frontend.data.set.internal.filter.FDSFilterContextContributorRegistryImpl;
import com.liferay.frontend.data.set.internal.filter.FDSFilterRegistryImpl;
import com.liferay.frontend.data.set.internal.filter.SelectionFDSFilterContextContributor;
import com.liferay.frontend.data.set.internal.url.FDSAPIURLResolverRegistryImpl;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.url.FDSAPIURLResolverRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Daniel Sanz
 */
public class SystemFDSSerializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, SystemFDSEntry.class, "frontend.data.set.name");

		ReflectionTestUtil.setFieldValue(
			_systemFDSEntryRegistry, "_serviceTrackerMap", _serviceTrackerMap);
	}

	@After
	public void tearDown() {
		_serviceTrackerMap.close();
	}

	@Test
	public void testSerializeAPIURL() throws Exception {
		ServiceTrackerMap
			<String,
			 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSAPIURLResolver>>
				serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, FDSAPIURLResolver.class,
					"fds.rest.application.key",
					ServiceTrackerCustomizerFactory.
						<FDSAPIURLResolver>serviceWrapper(_bundleContext));

		FDSAPIURLResolverRegistry fdsAPIURLResolverRegistry =
			new FDSAPIURLResolverRegistryImpl();

		ReflectionTestUtil.setFieldValue(
			fdsAPIURLResolverRegistry, "_serviceTrackerMap", serviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "fdsAPIURLResolverRegistry",
			fdsAPIURLResolverRegistry);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_systemFDSEntryRegistry", _systemFDSEntryRegistry);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		// Different resolvers

		ServiceRegistration<FDSAPIURLResolver> fdsAPIURLServiceRegistration =
			_registerFDSAPIURLResolver(
				"/app1", "schema", new String[] {"{foo}"},
				new String[] {"bar"});
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				null, "fdsName1", "/app1", "/endpoint/{foo}", "schema");
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				null, "fdsName2", "/app2", "/endpoint/{foo}", "schema");

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_fdsSerializer.serializeAPIURL("fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app2/endpoint/{foo}",
			_fdsSerializer.serializeAPIURL("fdsName2", _httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No resolver, URL

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		Assert.assertEquals(
			"/o/app/endpoint",
			_fdsSerializer.serializeAPIURL("fdsName", _httpServletRequest));

		systemFDSEntryServiceRegistration1.unregister();

		// No resolver, URL with parameters

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			"param=3", "fdsName", "/app", "/endpoint", "schema");

		Assert.assertEquals(
			"/o/app/endpoint?param=3",
			_fdsSerializer.serializeAPIURL("fdsName", _httpServletRequest));

		systemFDSEntryServiceRegistration1.unregister();

		// Resolver with interpolation

		fdsAPIURLServiceRegistration = _registerFDSAPIURLResolver(
			"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});
		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			"{foo}=3", "fdsName", "/app", "/endpoint/{foo}", "schema");

		Assert.assertEquals(
			"/o/app/endpoint/bar?bar=3",
			_fdsSerializer.serializeAPIURL("fdsName", _httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
		systemFDSEntryServiceRegistration1.unregister();

		// Shared resolver

		fdsAPIURLServiceRegistration = _registerFDSAPIURLResolver(
			"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"});
		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName1", "/app", "/endpoint/{foo}", "schema");
		systemFDSEntryServiceRegistration2 = _registerSystemFDSEntry(
			null, "fdsName2", "/app", "/endpoint/{foo}", "schema");

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serializeAPIURL("fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_fdsSerializer.serializeAPIURL("fdsName2", _httpServletRequest));

		fdsAPIURLServiceRegistration.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeBulkActions() throws Exception {
		ServiceTrackerMap
			<String,
			 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSBulkActions>>
				serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, FDSBulkActions.class,
					"frontend.data.set.name",
					ServiceTrackerCustomizerFactory.
						<FDSBulkActions>serviceWrapper(_bundleContext));

		FDSBulkActionsRegistry fdsBulkActionsRegistry =
			new FDSBulkActionsRegistryImpl();

		ReflectionTestUtil.setFieldValue(
			fdsBulkActionsRegistry, "_serviceTrackerMap", serviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsBulkActionsRegistry", fdsBulkActionsRegistry);

		// Different bulk actions

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "trash", "delete", "delete", "delete", "delete",
					"headless"));

		ServiceRegistration<FDSBulkActions> bulkActionsServiceRegistration1 =
			_registerFDSBulkActions(fdsActionDropdownItems1, "fdsName1");

		Assert.assertEquals(
			fdsActionDropdownItems1,
			_fdsSerializer.serializeBulkActions(
				"fdsName1", _httpServletRequest));

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "cog", "permissions", "permissions", "get",
					"permissions", "modal-permissions"));

		ServiceRegistration<FDSBulkActions> bulkActionsServiceRegistration2 =
			_registerFDSBulkActions(fdsActionDropdownItems2, "fdsName2");

		Assert.assertEquals(
			fdsActionDropdownItems2,
			_fdsSerializer.serializeBulkActions(
				"fdsName2", _httpServletRequest));

		Assert.assertNotEquals(
			_fdsSerializer.serializeBulkActions(
				"fdsName1", _httpServletRequest),
			_fdsSerializer.serializeBulkActions(
				"fdsName2", _httpServletRequest));

		bulkActionsServiceRegistration1.unregister();
		bulkActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No bulk actions

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_fdsSerializer.serializeBulkActions(
				"fdsName", _httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration1.unregister();

		// Shared bulk actions

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName1", "/app", "/endpoint", "schema");
		systemFDSEntryServiceRegistration2 = _registerSystemFDSEntry(
			null, "fdsName2", "/app", "/endpoint", "schema");

		fdsActionDropdownItems1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		bulkActionsServiceRegistration1 = _registerFDSBulkActions(
			fdsActionDropdownItems1, "fdsName1");
		bulkActionsServiceRegistration2 = _registerFDSBulkActions(
			fdsActionDropdownItems1, "fdsName2");

		Assert.assertEquals(
			_fdsSerializer.serializeBulkActions(
				"fdsName1", _httpServletRequest),
			_fdsSerializer.serializeBulkActions(
				"fdsName2", _httpServletRequest));

		bulkActionsServiceRegistration1.unregister();
		bulkActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeCreationMenu() throws Exception {
		ServiceTrackerMap
			<String,
			 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSCreationMenu>>
				serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, FDSCreationMenu.class,
					"frontend.data.set.name",
					ServiceTrackerCustomizerFactory.
						<FDSCreationMenu>serviceWrapper(_bundleContext));

		FDSCreationMenuRegistry fdsCreationMenuRegistry =
			new FDSCreationMenuRegistryImpl();

		ReflectionTestUtil.setFieldValue(
			fdsCreationMenuRegistry, "_serviceTrackerMap", serviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsCreationMenuRegistry",
			fdsCreationMenuRegistry);

		// Different creation menu

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema");

		CreationMenu creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration1 =
			_registerFDSCreationMenu(creationMenu1, "fdsName1");

		Assert.assertEquals(
			creationMenu1,
			_fdsSerializer.serializeCreationMenu(
				"fdsName1", _httpServletRequest));

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema");

		CreationMenu creationMenu2 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"cog"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration2 =
			_registerFDSCreationMenu(creationMenu2, "fdsName2");

		Assert.assertEquals(
			creationMenu2,
			_fdsSerializer.serializeCreationMenu(
				"fdsName2", _httpServletRequest));

		Assert.assertNotEquals(
			_fdsSerializer.serializeCreationMenu(
				"fdsName1", _httpServletRequest),
			_fdsSerializer.serializeCreationMenu(
				"fdsName2", _httpServletRequest));

		creationMenuServiceRegistration1.unregister();
		creationMenuServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No creation menu

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_fdsSerializer.serializeCreationMenu(
				"fdsName", _httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration1.unregister();

		// Shared creation menu

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName1", "/app", "/endpoint", "schema");
		systemFDSEntryServiceRegistration2 = _registerSystemFDSEntry(
			null, "fdsName2", "/app", "/endpoint", "schema");

		creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		creationMenuServiceRegistration1 = _registerFDSCreationMenu(
			creationMenu1, "fdsName1");
		creationMenuServiceRegistration2 = _registerFDSCreationMenu(
			creationMenu1, "fdsName2");

		Assert.assertEquals(
			_fdsSerializer.serializeCreationMenu(
				"fdsName1", _httpServletRequest),
			_fdsSerializer.serializeCreationMenu(
				"fdsName2", _httpServletRequest));

		creationMenuServiceRegistration1.unregister();
		creationMenuServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeFilters() throws Exception {
		_filterServiceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			_bundleContext, FDSFilter.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSFilter>serviceWrapper(
				_bundleContext));

		_filterContextContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				_bundleContext, FDSFilterContextContributor.class,
				"frontend.data.set.filter.type",
				ServiceTrackerCustomizerFactory.
					<FDSFilterContextContributor>serviceWrapper(
						_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsFilterRegistryImpl, "_serviceTrackerMap",
			_filterServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsFilterContextContributorRegistryImpl, "_serviceTrackerMap",
			_filterContextContributorServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsFilterRegistry", _fdsFilterRegistryImpl);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsFilterContextContributorRegistry",
			_fdsFilterContextContributorRegistryImpl);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_jsonFactory", _jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_language", _language);

		ReflectionTestUtil.setFieldValue(_fdsSerializer, "_portal", _portal);

		ReflectionTestUtil.setFieldValue(
			_dateRangeFDSFilterContextContributor, "_jsonFactory",
			_jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_selectionFDSFilterContextContributor, "_jsonFactory",
			_jsonFactory);

		ReflectionTestUtil.setFieldValue(
			_selectionFDSFilterContextContributor, "_language", _language);

		_clientExtensionFDSFilterContextContributorServiceRegistration =
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				_clientExtensionFDSFilterContextContributor,
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "clientExtension"));

		_dateRangeFDSFilterContextContributorServiceRegistration =
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				_dateRangeFDSFilterContextContributor,
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "dateRange"));

		_selectionFDSFilterContextContributorServiceRegistration =
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				_selectionFDSFilterContextContributor,
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "selection"));

		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(
				Mockito.nullable(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		Mockito.when(
			_portal.getLocale(_httpServletRequest)
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_language.get(LocaleUtil.US, null)
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			_language.get(Mockito.eq(LocaleUtil.US), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1, String.class)
		);

		Mockito.when(
			_language.get(
				Mockito.eq(ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE),
				Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1, String.class)
		);

		// Client extension filter

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter>
			clientExtensionFilterServiceRegistration = _registerFilter(
				"fdsName",
				_createClientExtensionFilter(
					"fooField", "Foo label", "/o/foo-filter/bar.js",
					new HashMapBuilder<>().<String, Object>put(
						"fooParam1", "bar1"
					).put(
						"fooParam2", "bar2"
					).build()));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"clientExtensionFilterURL", "/o/foo-filter/bar.js"
				).put(
					"id", "fooField"
				).put(
					"label", "Foo label"
				).put(
					"preloadedData",
					JSONUtil.put(
						"fooParam1", "bar1"
					).put(
						"fooParam2", "bar2"
					)
				).put(
					"type", "clientExtension"
				)
			).toString(),
			_fdsSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		clientExtensionFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		// Date range filter

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration1 =
			_registerFilter(
				"fdsName",
				_createDateRangeFilter(
					"createDate", "By Creation Date", FDSEntityFieldTypes.DATE,
					new HashMapBuilder<>().<String, Object>put(
						"from", new DateFDSFilterItem(30, 11, 1985)
					).put(
						"to", new DateFDSFilterItem(27, 5, 1995)
					).build(),
					new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(16, 3, 1977)));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"entityFieldType", "date"
				).put(
					"id", "createDate"
				).put(
					"label", "By Creation Date"
				).put(
					"max",
					JSONUtil.put(
						"day", 16
					).put(
						"month", 3
					).put(
						"year", 1977
					)
				).put(
					"min",
					JSONUtil.put(
						"day", 0
					).put(
						"month", 0
					).put(
						"year", 0
					)
				).put(
					"preloadedData",
					JSONUtil.put(
						"from",
						JSONUtil.put(
							"day", 30
						).put(
							"month", 11
						).put(
							"year", 1985
						)
					).put(
						"to",
						JSONUtil.put(
							"day", 27
						).put(
							"month", 5
						).put(
							"year", 1995
						)
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			_fdsSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		dateRangeFilterServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		// Different filters

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema");

		dateRangeFilterServiceRegistration1 = _registerFilter(
			"fdsName1",
			_createDateRangeFilter(
				"createDate", "By Creation Date", FDSEntityFieldTypes.DATE,
				null, new DateFDSFilterItem(0, 0, 0),
				new DateFDSFilterItem(1, 1, 1980)));

		ServiceRegistration<FDSFilter> dateRangeFilterServiceRegistration2 =
			_registerFilter(
				"fdsName2",
				_createDateRangeFilter(
					"modifiedDate", "By Modification Date",
					FDSEntityFieldTypes.DATE, null,
					new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(1, 1, 1980)));

		String dateRangeFilterSerialized1 = _fdsSerializer.serializeFilters(
			"fdsName1", _httpServletRequest
		).toString();

		String dateRangeFilterSerialized2 = _fdsSerializer.serializeFilters(
			"fdsName2", _httpServletRequest
		).toString();

		JSONAssert.assertNotEquals(
			dateRangeFilterSerialized1, dateRangeFilterSerialized2,
			JSONCompareMode.STRICT);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"entityFieldType", "date"
				).put(
					"id", "createDate"
				).put(
					"label", "By Creation Date"
				).put(
					"max",
					JSONUtil.put(
						"day", 1
					).put(
						"month", 1
					).put(
						"year", 1980
					)
				).put(
					"min",
					JSONUtil.put(
						"day", 0
					).put(
						"month", 0
					).put(
						"year", 0
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			dateRangeFilterSerialized1, JSONCompareMode.STRICT);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"entityFieldType", "date"
				).put(
					"id", "modifiedDate"
				).put(
					"label", "By Modification Date"
				).put(
					"max",
					JSONUtil.put(
						"day", 1
					).put(
						"month", 1
					).put(
						"year", 1980
					)
				).put(
					"min",
					JSONUtil.put(
						"day", 0
					).put(
						"month", 0
					).put(
						"year", 0
					)
				).put(
					"type", "dateRange"
				)
			).toString(),
			dateRangeFilterSerialized2, JSONCompareMode.STRICT);

		dateRangeFilterServiceRegistration1.unregister();

		dateRangeFilterServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();

		// Disabled filter

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> fdsFilterServiceRegistration =
			_registerFilter(
				"fdsName",
				new FDSFilter() {

					@Override
					public String getId() {
						return "id";
					}

					@Override
					public String getLabel() {
						return "label";
					}

					@Override
					public String getType() {
						return "type";
					}

					@Override
					public boolean isEnabled() {
						return false;
					}

				});

		JSONAssert.assertEquals(
			"[]",
			_fdsSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		systemFDSEntryServiceRegistration1.unregister();

		fdsFilterServiceRegistration.unregister();

		// No filter

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		JSONAssert.assertEquals(
			"[]",
			_fdsSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		systemFDSEntryServiceRegistration1.unregister();

		// Selection filter, with API URL

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		ServiceRegistration<FDSFilter> selectionFilterServiceRegistration =
			_registerFilter(
				"fdsName",
				_createSelectionFilter(
					"categoryIds", "By Category",
					FDSEntityFieldTypes.COLLECTION,
					new HashMapBuilder<>().<String, Object>put(
						"exclude", false
					).build(),
					"/o/headless-admin-taxonomy/v1.0/taxonomy-categories/0" +
						"/taxonomy-categories?sort=name:asc",
					"id", "label", false));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"apiURL",
					"/o/headless-admin-taxonomy/v1.0/taxonomy-" +
						"categories/0/taxonomy-categories?sort=name:asc"
				).put(
					"autocompleteEnabled", true
				).put(
					"entityFieldType", "collection"
				).put(
					"id", "categoryIds"
				).put(
					"inputPlaceholder", "search"
				).put(
					"itemKey", "id"
				).put(
					"itemLabel", "label"
				).put(
					"items", JSONUtil.putAll()
				).put(
					"label", "By Category"
				).put(
					"multiple", false
				).put(
					"preloadedData", JSONUtil.put("exclude", false)
				).put(
					"type", "selection"
				)
			).toString(),
			_fdsSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		selectionFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		// Selection filter, with items

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		selectionFilterServiceRegistration = _registerFilter(
			"fdsName",
			_createSelectionFilter(
				"categoryIds", "By Category", FDSEntityFieldTypes.COLLECTION,
				new HashMapBuilder<>().<String, Object>put(
					"exclude", true
				).build(),
				ListUtil.fromArray(
					new SelectionFDSFilterItem("animal", 1),
					new SelectionFDSFilterItem("vegetable", 2)),
				"id", "label", false, true));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"autocompleteEnabled", false
				).put(
					"entityFieldType", "collection"
				).put(
					"id", "categoryIds"
				).put(
					"items",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "animal"
						).put(
							"value", 1
						),
						JSONUtil.put(
							"label", "vegetable"
						).put(
							"value", 2
						))
				).put(
					"label", "By Category"
				).put(
					"multiple", true
				).put(
					"preloadedData", JSONUtil.put("exclude", true)
				).put(
					"type", "selection"
				)
			).toString(),
			_fdsSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		selectionFilterServiceRegistration.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		// Shared filters

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName1", "/app", "/endpoint", "schema");

		systemFDSEntryServiceRegistration2 = _registerSystemFDSEntry(
			null, "fdsName2", "/app", "/endpoint", "schema");

		FDSFilter dateRangeFilter = _createDateRangeFilter(
			"createDate", "By Creation Date", FDSEntityFieldTypes.DATE, null,
			new DateFDSFilterItem(0, 0, 0), new DateFDSFilterItem(1, 1, 1980));

		dateRangeFilterServiceRegistration1 = _registerFilter(
			"fdsName1", dateRangeFilter);

		dateRangeFilterServiceRegistration2 = _registerFilter(
			"fdsName2", dateRangeFilter);

		JSONAssert.assertEquals(
			_fdsSerializer.serializeFilters(
				"fdsName1", _httpServletRequest
			).toString(),
			_fdsSerializer.serializeFilters(
				"fdsName2", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		dateRangeFilterServiceRegistration1.unregister();

		dateRangeFilterServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();

		_clientExtensionFDSFilterContextContributorServiceRegistration.
			unregister();
		_dateRangeFDSFilterContextContributorServiceRegistration.unregister();
		_selectionFDSFilterContextContributorServiceRegistration.unregister();

		_filterServiceTrackerMap.close();
	}

	@Test
	public void testSerializeItemsActions() throws Exception {
		ServiceTrackerMap
			<String,
			 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSItemsActions>>
				serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
					_bundleContext, FDSItemsActions.class,
					"frontend.data.set.name",
					ServiceTrackerCustomizerFactory.
						<FDSItemsActions>serviceWrapper(_bundleContext));

		FDSItemsActionsRegistry fdsItemsActionsRegistry =
			new FDSItemsActionsRegistryImpl();

		ReflectionTestUtil.setFieldValue(
			fdsItemsActionsRegistry, "_serviceTrackerMap", serviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsItemsActionsRegistry",
			fdsItemsActionsRegistry);

		// Different items actions

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "trash", "delete", "delete", "delete", "delete",
					"headless"));

		ServiceRegistration<FDSItemsActions> itemsActionsServiceRegistration1 =
			_registerFDSItemsActions(fdsActionDropdownItems1, "fdsName1");

		Assert.assertEquals(
			fdsActionDropdownItems1,
			_fdsSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest));

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "cog", "permissions", "permissions", "get",
					"permissions", "modal-permissions"));

		ServiceRegistration<FDSItemsActions> itemsActionsServiceRegistration2 =
			_registerFDSItemsActions(fdsActionDropdownItems2, "fdsName2");

		Assert.assertEquals(
			fdsActionDropdownItems2,
			_fdsSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest));

		Assert.assertNotEquals(
			_fdsSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest),
			_fdsSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest));

		itemsActionsServiceRegistration1.unregister();
		itemsActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No items actions

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_fdsSerializer.serializeItemsActions(
				"fdsName", _httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration1.unregister();

		// Shared items actions

		systemFDSEntryServiceRegistration1 = _registerSystemFDSEntry(
			null, "fdsName1", "/app", "/endpoint", "schema");
		systemFDSEntryServiceRegistration2 = _registerSystemFDSEntry(
			null, "fdsName2", "/app", "/endpoint", "schema");

		fdsActionDropdownItems1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		itemsActionsServiceRegistration1 = _registerFDSItemsActions(
			fdsActionDropdownItems1, "fdsName1");
		itemsActionsServiceRegistration2 = _registerFDSItemsActions(
			fdsActionDropdownItems1, "fdsName2");

		Assert.assertEquals(
			_fdsSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest),
			_fdsSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest));

		itemsActionsServiceRegistration1.unregister();
		itemsActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		serviceTrackerMap.close();
	}

	private FDSFilter _createClientExtensionFilter(
		String id, String label, String moduleURL,
		Map<String, Object> preloadedData) {

		return new BaseClientExtensionFDSFilter() {

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public String getModuleURL() {
				return moduleURL;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

		};
	}

	private FDSFilter _createDateRangeFilter(
		String id, String label, String entityFieldType,
		Map<String, Object> preloadedData, DateFDSFilterItem min,
		DateFDSFilterItem max) {

		return new BaseDateRangeFDSFilter() {

			@Override
			public String getEntityFieldType() {
				return entityFieldType;
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public DateFDSFilterItem getMaxDateFDSFilterItem() {
				return max;
			}

			@Override
			public DateFDSFilterItem getMinDateFDSFilterItem() {
				return min;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

		};
	}

	private FDSFilter _createSelectionFilter(
		String id, String label, String entityFieldType,
		Map<String, Object> preloadedData,
		List<SelectionFDSFilterItem> selectionFDSFilterItems, String itemKey,
		String itemLabel, boolean autocompleteEnabled, boolean multiple) {

		return new BaseSelectionFDSFilter() {

			@Override
			public String getEntityFieldType() {
				return entityFieldType;
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getItemKey() {
				return itemKey;
			}

			@Override
			public String getItemLabel() {
				return itemLabel;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

			@Override
			public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
				Locale locale) {

				return selectionFDSFilterItems;
			}

			@Override
			public boolean isAutocompleteEnabled() {
				return autocompleteEnabled;
			}

			@Override
			public boolean isMultiple() {
				return multiple;
			}

		};
	}

	private FDSFilter _createSelectionFilter(
		String id, String label, String entityFieldType,
		Map<String, Object> preloadedData, String apiURL, String itemKey,
		String itemLabel, boolean multiple) {

		return new BaseSelectionFDSFilter() {

			@Override
			public String getAPIURL() {
				return apiURL;
			}

			@Override
			public String getEntityFieldType() {
				return entityFieldType;
			}

			@Override
			public String getId() {
				return id;
			}

			@Override
			public String getItemKey() {
				return itemKey;
			}

			@Override
			public String getItemLabel() {
				return itemLabel;
			}

			@Override
			public String getLabel() {
				return label;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

			@Override
			public boolean isAutocompleteEnabled() {
				return true;
			}

			@Override
			public boolean isMultiple() {
				return multiple;
			}

		};
	}

	private ServiceRegistration<FDSAPIURLResolver> _registerFDSAPIURLResolver(
		String restApplication, String restSchema, String[] tokens,
		String[] values) {

		return _bundleContext.registerService(
			FDSAPIURLResolver.class,
			new FDSAPIURLResolver() {

				@Override
				public String getSchema() {
					return restSchema;
				}

				@Override
				public String resolve(
						String baseURL, HttpServletRequest httpServletRequest)
					throws PortalException {

					return StringUtil.replace(baseURL, tokens, values);
				}

			},
			MapUtil.singletonDictionary(
				"fds.rest.application.key",
				restApplication + "/" + restSchema));
	}

	private ServiceRegistration<FDSBulkActions> _registerFDSBulkActions(
		List<FDSActionDropdownItem> fdsActionDropdownItems, String fdsName) {

		return _bundleContext.registerService(
			FDSBulkActions.class,
			new FDSBulkActions() {

				@Override
				public List<FDSActionDropdownItem> getFDSActionDropdownItems(
					HttpServletRequest httpServletRequest) {

					return fdsActionDropdownItems;
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private ServiceRegistration<FDSCreationMenu> _registerFDSCreationMenu(
		CreationMenu creationMenu, String fdsName) {

		return _bundleContext.registerService(
			FDSCreationMenu.class,
			new FDSCreationMenu() {

				@Override
				public CreationMenu getCreationMenu(
					HttpServletRequest httpServletRequest) {

					return creationMenu;
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private ServiceRegistration<FDSItemsActions> _registerFDSItemsActions(
		List<FDSActionDropdownItem> fdsActionDropdownItems, String fdsName) {

		return _bundleContext.registerService(
			FDSItemsActions.class,
			new FDSItemsActions() {

				@Override
				public List<FDSActionDropdownItem> getFDSActionDropdownItems(
					HttpServletRequest httpServletRequest) {

					return fdsActionDropdownItems;
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private ServiceRegistration<FDSFilter> _registerFilter(
		String fdsName, FDSFilter fdsFilter) {

		return _bundleContext.registerService(
			FDSFilter.class, fdsFilter,
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String additionalURLParameters, String fdsName, String restApplication,
		String restEndpoint, String restSchema) {

		return _bundleContext.registerService(
			SystemFDSEntry.class,
			new SystemFDSEntry() {

				@Override
				public String getAdditionalAPIURLParameters() {
					return additionalURLParameters;
				}

				@Override
				public String getDescription() {
					return "";
				}

				@Override
				public String getName() {
					return fdsName;
				}

				@Override
				public String getRESTApplication() {
					return restApplication;
				}

				@Override
				public String getRESTEndpoint() {
					return restEndpoint;
				}

				@Override
				public String getRESTSchema() {
					return restSchema;
				}

				@Override
				public String getTitle() {
					return "";
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private static final ClientExtensionFDSFilterContextContributor
		_clientExtensionFDSFilterContextContributor =
			new ClientExtensionFDSFilterContextContributor();
	private static final DateRangeFDSFilterContextContributor
		_dateRangeFDSFilterContextContributor =
			new DateRangeFDSFilterContextContributor();
	private static final FDSFilterRegistryImpl _fdsFilterRegistryImpl =
		new FDSFilterRegistryImpl();
	private static ServiceTrackerMap
		<String,
		 List<ServiceTrackerCustomizerFactory.ServiceWrapper<FDSFilter>>>
			_filterServiceTrackerMap;
	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private static final SelectionFDSFilterContextContributor
		_selectionFDSFilterContextContributor =
			new SelectionFDSFilterContextContributor();

	private BundleContext _bundleContext = SystemBundleUtil.getBundleContext();
	private ServiceRegistration<FDSFilterContextContributor>
		_clientExtensionFDSFilterContextContributorServiceRegistration;
	private ServiceRegistration<FDSFilterContextContributor>
		_dateRangeFDSFilterContextContributorServiceRegistration;
	private final FDSFilterContextContributorRegistryImpl
		_fdsFilterContextContributorRegistryImpl =
			new FDSFilterContextContributorRegistryImpl();
	private final FDSSerializer _fdsSerializer = new SystemFDSSerializer();
	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <FDSFilterContextContributor>>>
					_filterContextContributorServiceTrackerMap;
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final Language _language = Mockito.mock(Language.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private ServiceRegistration<FDSFilterContextContributor>
		_selectionFDSFilterContextContributorServiceRegistration;
	private ServiceTrackerMap<String, SystemFDSEntry> _serviceTrackerMap;
	private final SystemFDSEntryRegistry _systemFDSEntryRegistry =
		new SystemFDSEntryRegistryImpl();

}