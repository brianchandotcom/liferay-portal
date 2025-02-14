/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSBulkActions;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.action.FDSItemsActions;
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
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
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

		_systemFDSSerializer.systemFDSEntryRegistry =
			new SystemFDSEntryRegistryImpl(_serviceTrackerMap);
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

		_systemFDSSerializer.fdsAPIURLResolverRegistry =
			new FDSAPIURLResolverRegistryImpl(serviceTrackerMap);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		// Different resolvers

		_registerServices(
			_registerFDSAPIURLResolver(
				"/app1", "schema", new String[] {"{foo}"},
				new String[] {"bar"}),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app1", "/endpoint/{foo}", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app2", "/endpoint/{foo}", "schema"));

		Assert.assertEquals(
			"/o/app1/endpoint/bar",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app2/endpoint/{foo}",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

		// No resolver, URL

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			"/o/app/endpoint",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", _httpServletRequest));

		_unregisterServices();

		// No resolver, URL with parameters

		_registerServices(
			_registerSystemFDSEntry(
				"param=3", "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			"/o/app/endpoint?param=3",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", _httpServletRequest));

		_unregisterServices();

		// Resolver with interpolation

		_registerServices(
			_registerFDSAPIURLResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"}),
			_registerSystemFDSEntry(
				"{foo}=3", "fdsName", "/app", "/endpoint/{foo}", "schema"));

		Assert.assertEquals(
			"/o/app/endpoint/bar?bar=3",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", _httpServletRequest));

		_unregisterServices();

		// Shared resolver

		_registerServices(
			_registerFDSAPIURLResolver(
				"/app", "schema", new String[] {"{foo}"}, new String[] {"bar"}),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint/{foo}", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint/{foo}", "schema"));

		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName1", _httpServletRequest));
		Assert.assertEquals(
			"/o/app/endpoint/bar",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

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

		_systemFDSSerializer.fdsBulkActionsRegistry =
			new FDSBulkActionsRegistryImpl(serviceTrackerMap);

		// Different bulk actions

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "trash", "delete", "delete", "delete", "delete",
					"headless"));

		_registerServices(
			_registerFDSBulkActions(fdsActionDropdownItems1, "fdsName1"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			fdsActionDropdownItems1,
			_systemFDSSerializer.serializeBulkActions(
				"fdsName1", _httpServletRequest));

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "cog", "permissions", "permissions", "get",
					"permissions", "modal-permissions"));

		_registerServices(
			_registerFDSBulkActions(fdsActionDropdownItems2, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			fdsActionDropdownItems2,
			_systemFDSSerializer.serializeBulkActions(
				"fdsName2", _httpServletRequest));

		Assert.assertNotEquals(
			_systemFDSSerializer.serializeBulkActions(
				"fdsName1", _httpServletRequest),
			_systemFDSSerializer.serializeBulkActions(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

		// No bulk actions

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertTrue(
			_systemFDSSerializer.serializeBulkActions(
				"fdsName", _httpServletRequest
			).isEmpty());

		_unregisterServices();

		// Shared bulk actions

		fdsActionDropdownItems1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		_registerServices(
			_registerFDSBulkActions(fdsActionDropdownItems1, "fdsName1"),
			_registerFDSBulkActions(fdsActionDropdownItems1, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			_systemFDSSerializer.serializeBulkActions(
				"fdsName1", _httpServletRequest),
			_systemFDSSerializer.serializeBulkActions(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

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

		_systemFDSSerializer.fdsCreationMenuRegistry =
			new FDSCreationMenuRegistryImpl(serviceTrackerMap);

		// Different creation menu

		CreationMenu creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		_registerServices(
			_registerFDSCreationMenu(creationMenu1, "fdsName1"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			creationMenu1,
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName1", _httpServletRequest));

		CreationMenu creationMenu2 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"cog"
			).build()
		).build();

		_registerServices(
			_registerFDSCreationMenu(creationMenu2, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			creationMenu2,
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName2", _httpServletRequest));

		Assert.assertNotEquals(
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName1", _httpServletRequest),
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

		// No creation menu

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertTrue(
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName", _httpServletRequest
			).isEmpty());

		_unregisterServices();

		// Shared creation menu

		creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		_registerServices(
			_registerFDSCreationMenu(creationMenu1, "fdsName1"),
			_registerFDSCreationMenu(creationMenu1, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName1", _httpServletRequest),
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeFilters() throws Exception {
		ServiceTrackerMap
			<String,
			 List
				 <ServiceTrackerCustomizerFactory.ServiceWrapper
					 <FDSFilterContextContributor>>> serviceTrackerMap1 =
						ServiceTrackerMapFactory.openMultiValueMap(
							_bundleContext, FDSFilterContextContributor.class,
							"frontend.data.set.filter.type",
							ServiceTrackerCustomizerFactory.
								<FDSFilterContextContributor>serviceWrapper(
									_bundleContext));

		_systemFDSSerializer.fdsFilterContextContributorRegistry =
			new FDSFilterContextContributorRegistryImpl(serviceTrackerMap1);

		ServiceTrackerMap
			<String,
			 List<ServiceTrackerCustomizerFactory.ServiceWrapper<FDSFilter>>>
				serviceTrackerMap2 = ServiceTrackerMapFactory.openMultiValueMap(
					_bundleContext, FDSFilter.class, "frontend.data.set.name",
					ServiceTrackerCustomizerFactory.<FDSFilter>serviceWrapper(
						_bundleContext));

		_systemFDSSerializer.fdsFilterRegistry = new FDSFilterRegistryImpl(
			serviceTrackerMap2);

		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(LocaleUtil.US, null)
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			language.get(Mockito.eq(LocaleUtil.US), Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1, String.class)
		);

		Mockito.when(
			language.get(
				Mockito.eq(ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE),
				Mockito.anyString())
		).thenAnswer(
			invocation -> invocation.getArgument(1, String.class)
		);

		languageUtil.setLanguage(language);

		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getLocale(_httpServletRequest)
		).thenReturn(
			LocaleUtil.US
		);

		portalUtil.setPortal(portal);

		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(
				Mockito.nullable(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		// Client extension filter

		_registerServices(
			_registerFDSFilter(
				"fdsName",
				new BaseClientExtensionFDSFilter() {

					@Override
					public String getId() {
						return "fooField";
					}

					@Override
					public String getLabel() {
						return "Foo label";
					}

					@Override
					public String getModuleURL() {
						return "/o/foo-filter/bar.js";
					}

					@Override
					public Map<String, Object> getPreloadedData() {
						return new HashMapBuilder<>().<String, Object>put(
							"fooParam1", "bar1"
						).put(
							"fooParam2", "bar2"
						).build();
					}

				}),
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				new ClientExtensionFDSFilterContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "clientExtension")),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

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
			_systemFDSSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Date range filter

		_registerServices(
			_registerFDSFilter(
				"fdsName",
				_createFDSDateRangeFilter(
					"createDate", "By Creation Date", FDSEntityFieldTypes.DATE,
					new HashMapBuilder<>().<String, Object>put(
						"from", new DateFDSFilterItem(30, 11, 1985)
					).put(
						"to", new DateFDSFilterItem(27, 5, 1995)
					).build(),
					new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(16, 3, 1977))),
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				new DateRangeFDSFilterContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "dateRange")),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

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
			_systemFDSSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Different filters

		_registerServices(
			_registerFDSFilter(
				"fdsName1",
				_createFDSDateRangeFilter(
					"createDate", "By Creation Date", FDSEntityFieldTypes.DATE,
					null, new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(1, 1, 1980))),
			_registerFDSFilter(
				"fdsName2",
				_createFDSDateRangeFilter(
					"modifiedDate", "By Modification Date",
					FDSEntityFieldTypes.DATE, null,
					new DateFDSFilterItem(0, 0, 0),
					new DateFDSFilterItem(1, 1, 1980))),
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				new DateRangeFDSFilterContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "dateRange")),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		String dateRangeFDSFilterSerialized1 =
			_systemFDSSerializer.serializeFilters(
				"fdsName1", _httpServletRequest
			).toString();

		String dateRangeFDSFilterSerialized2 =
			_systemFDSSerializer.serializeFilters(
				"fdsName2", _httpServletRequest
			).toString();

		JSONAssert.assertNotEquals(
			dateRangeFDSFilterSerialized1, dateRangeFDSFilterSerialized2,
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
			dateRangeFDSFilterSerialized1, JSONCompareMode.STRICT);

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
			dateRangeFDSFilterSerialized2, JSONCompareMode.STRICT);

		_unregisterServices();

		// Disabled filter

		_registerServices(
			_registerFDSFilter(
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

				}),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			"[]",
			_systemFDSSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// No filter

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			"[]",
			_systemFDSSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Selection filter, with API URL

		_registerServices(
			_registerFDSFilter(
				"fdsName",
				new BaseSelectionFDSFilter() {

					@Override
					public String getAPIURL() {
						return "/o/headless-admin-taxonomy/v1.0/taxonomy-" +
							"categories/0/taxonomy-categories?sort=name:asc";
					}

					@Override
					public String getEntityFieldType() {
						return FDSEntityFieldTypes.COLLECTION;
					}

					@Override
					public String getId() {
						return "categoryIds";
					}

					@Override
					public String getItemKey() {
						return "id";
					}

					@Override
					public String getItemLabel() {
						return "label";
					}

					@Override
					public String getLabel() {
						return "By Category";
					}

					@Override
					public Map<String, Object> getPreloadedData() {
						return new HashMapBuilder<>().<String, Object>put(
							"exclude", false
						).build();
					}

					@Override
					public boolean isAutocompleteEnabled() {
						return true;
					}

					@Override
					public boolean isMultiple() {
						return false;
					}

				}),
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				new SelectionFDSFilterContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "selection")),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

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
			_systemFDSSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Selection filter, with items

		_registerServices(
			_registerFDSFilter(
				"fdsName",
				new BaseSelectionFDSFilter() {

					@Override
					public String getEntityFieldType() {
						return FDSEntityFieldTypes.COLLECTION;
					}

					@Override
					public String getId() {
						return "categoryIds";
					}

					@Override
					public String getItemKey() {
						return "id";
					}

					@Override
					public String getItemLabel() {
						return "label";
					}

					@Override
					public String getLabel() {
						return "By Category";
					}

					@Override
					public Map<String, Object> getPreloadedData() {
						return new HashMapBuilder<>().<String, Object>put(
							"exclude", true
						).build();
					}

					@Override
					public List<SelectionFDSFilterItem>
						getSelectionFDSFilterItems(Locale locale) {

						return ListUtil.fromArray(
							new SelectionFDSFilterItem("animal", 1),
							new SelectionFDSFilterItem("vegetable", 2));
					}

					@Override
					public boolean isAutocompleteEnabled() {
						return false;
					}

					@Override
					public boolean isMultiple() {
						return true;
					}

				}),
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				new SelectionFDSFilterContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "selection")),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

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
			_systemFDSSerializer.serializeFilters(
				"fdsName", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Shared filters

		FDSFilter dateRangeFDSFilter = _createFDSDateRangeFilter(
			"createDate", "By Creation Date", FDSEntityFieldTypes.DATE, null,
			new DateFDSFilterItem(0, 0, 0), new DateFDSFilterItem(1, 1, 1980));

		_registerServices(
			_registerFDSFilter("fdsName1", dateRangeFDSFilter),
			_registerFDSFilter("fdsName2", dateRangeFDSFilter),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			_systemFDSSerializer.serializeFilters(
				"fdsName1", _httpServletRequest
			).toString(),
			_systemFDSSerializer.serializeFilters(
				"fdsName2", _httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		serviceTrackerMap1.close();
		serviceTrackerMap2.close();
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

		_systemFDSSerializer.fdsItemsActionsRegistry =
			new FDSItemsActionsRegistryImpl(serviceTrackerMap);

		// Different items actions

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "trash", "delete", "delete", "delete", "delete",
					"headless"));

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "cog", "permissions", "permissions", "get",
					"permissions", "modal-permissions"));

		_registerServices(
			_registerFDSItemsActions(fdsActionDropdownItems1, "fdsName1"),
			_registerFDSItemsActions(fdsActionDropdownItems2, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			fdsActionDropdownItems1,
			_systemFDSSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			fdsActionDropdownItems2,
			_systemFDSSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest));

		Assert.assertNotEquals(
			_systemFDSSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest),
			_systemFDSSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

		// No items actions

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertTrue(
			_systemFDSSerializer.serializeItemsActions(
				"fdsName", _httpServletRequest
			).isEmpty());

		_unregisterServices();

		// Shared items actions

		fdsActionDropdownItems1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		_registerServices(
			_registerFDSItemsActions(fdsActionDropdownItems1, "fdsName1"),
			_registerFDSItemsActions(fdsActionDropdownItems1, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			_systemFDSSerializer.serializeItemsActions(
				"fdsName1", _httpServletRequest),
			_systemFDSSerializer.serializeItemsActions(
				"fdsName2", _httpServletRequest));

		_unregisterServices();

		serviceTrackerMap.close();
	}

	private FDSFilter _createFDSDateRangeFilter(
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

	private ServiceRegistration<FDSFilter> _registerFDSFilter(
		String fdsName, FDSFilter fdsFilter) {

		return _bundleContext.registerService(
			FDSFilter.class, fdsFilter,
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

	private void _registerServices(
		ServiceRegistration<?>... serviceRegistrations) {

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			_serviceRegistrations.add(serviceRegistration);
		}
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

	private void _unregisterServices() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	private BundleContext _bundleContext = SystemBundleUtil.getBundleContext();
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private ServiceTrackerMap<String, SystemFDSEntry> _serviceTrackerMap;
	private final SystemFDSSerializer _systemFDSSerializer =
		new SystemFDSSerializer();

}