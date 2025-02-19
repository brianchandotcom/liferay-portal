/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSBulkActions;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.constants.FDSConstants;
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
import com.liferay.frontend.data.set.internal.view.FDSViewContextContributorRegistryImpl;
import com.liferay.frontend.data.set.internal.view.FDSViewRegistryImpl;
import com.liferay.frontend.data.set.internal.view.cards.CardsFDSViewContextContributor;
import com.liferay.frontend.data.set.internal.view.list.ListFDSViewContextContributor;
import com.liferay.frontend.data.set.internal.view.table.FDSTableSchemaBuilderImpl;
import com.liferay.frontend.data.set.internal.view.table.TableFDSViewContextContributor;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.url.FDSAPIURLResolver;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.cards.BaseCardsFDSView;
import com.liferay.frontend.data.set.view.list.BaseListFDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
public class SystemFDSSerializerTest extends BaseFDSSerializerTestCase {

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

		// Nested fields: creator

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
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		_registerServices(
			_registerSystemFDSEntry(
				"nestedFields=creator", "fdsName", "/app", "/endpoint",
				"schema"));

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", httpServletRequest));

		_unregisterServices();

		// Nested fields: creator and status

		_registerServices(
			_registerSystemFDSEntry(
				"nestedFields=creator,status", "fdsName", "/app", "/endpoint",
				"schema"));

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator,status",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", httpServletRequest));

		_unregisterServices();

		// Nested fields depth

		_registerServices(
			_registerSystemFDSEntry(
				"nestedFields=creator,status,relation&nestedFieldsDepth=2",
				"fdsName", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			"/o/app/endpoint?nestedFields=creator,status,relation&" +
				"nestedFieldsDepth=2",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", httpServletRequest));

		_unregisterServices();

		// No parameters

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertEquals(
			"/o/app/endpoint",
			_systemFDSSerializer.serializeAPIURL(
				"fdsName", httpServletRequest));

		_unregisterServices();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeBulkActions() throws Exception {

		// Different bulk actions

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
				"fdsName1", httpServletRequest));

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
				"fdsName2", httpServletRequest));

		Assert.assertNotEquals(
			_systemFDSSerializer.serializeBulkActions(
				"fdsName1", httpServletRequest),
			_systemFDSSerializer.serializeBulkActions(
				"fdsName2", httpServletRequest));

		_unregisterServices();

		// No bulk actions

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertTrue(
			_systemFDSSerializer.serializeBulkActions(
				"fdsName", httpServletRequest
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
				"fdsName1", httpServletRequest),
			_systemFDSSerializer.serializeBulkActions(
				"fdsName2", httpServletRequest));

		_unregisterServices();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeCreationMenu() throws Exception {

		// Different creation menu

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
				"fdsName1", httpServletRequest));

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
				"fdsName2", httpServletRequest));

		Assert.assertNotEquals(
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName1", httpServletRequest),
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName2", httpServletRequest));

		_unregisterServices();

		// No creation menu

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertTrue(
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName", httpServletRequest
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
				"fdsName1", httpServletRequest),
			_systemFDSSerializer.serializeCreationMenu(
				"fdsName2", httpServletRequest));

		_unregisterServices();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeFilters() throws Exception {

		// Client extension filter

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

		mockLanguage();

		_registerServices(
			_registerFDSFilter(
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

				},
				"fdsName"),
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
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Date range filter

		_registerServices(
			_registerFDSFilter(
				_createFDSFilterDate(
					"createDate", "By Creation Date",
					new DateFDSFilterItem(16, 3, 1977),
					new DateFDSFilterItem(0, 0, 0),
					new HashMapBuilder<>().<String, Object>put(
						"from", new DateFDSFilterItem(30, 11, 1985)
					).put(
						"to", new DateFDSFilterItem(27, 5, 1995)
					).build()),
				"fdsName"),
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
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Different filters

		_registerServices(
			_registerFDSFilter(
				_createFDSFilterDate(
					"createDate", "By Creation Date",
					new DateFDSFilterItem(1, 1, 1980),
					new DateFDSFilterItem(0, 0, 0), null),
				"fdsName1"),
			_registerFDSFilter(
				_createFDSFilterDate(
					"modifiedDate", "By Modification Date",
					new DateFDSFilterItem(1, 1, 1980),
					new DateFDSFilterItem(0, 0, 0), null),
				"fdsName2"),
			_bundleContext.registerService(
				FDSFilterContextContributor.class,
				new DateRangeFDSFilterContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.filter.type", "dateRange")),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		String json1 = _systemFDSSerializer.serializeFilters(
			"fdsName1", httpServletRequest
		).toString();
		String json2 = _systemFDSSerializer.serializeFilters(
			"fdsName2", httpServletRequest
		).toString();

		JSONAssert.assertNotEquals(json1, json2, JSONCompareMode.STRICT);

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
			json1, JSONCompareMode.STRICT);
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
			json2, JSONCompareMode.STRICT);

		_unregisterServices();

		// Disabled filter

		_registerServices(
			_registerFDSFilter(
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

				},
				"fdsName"),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			"[]",
			_systemFDSSerializer.serializeFilters(
				"fdsName", httpServletRequest
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
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Selection filter

		_registerServices(
			_registerFDSFilter(
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
					public List<SelectionFDSFilterItem>
						getSelectionFDSFilterItems(Locale locale) {

						return ListUtil.fromArray(
							new SelectionFDSFilterItem("animal", 1),
							new SelectionFDSFilterItem("vegetable", 2));
					}

					@Override
					public boolean isAutocompleteEnabled() {
						return true;
					}

					@Override
					public boolean isMultiple() {
						return true;
					}

				},
				"fdsName"),
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
					"preloadedData", JSONUtil.put("exclude", false)
				).put(
					"type", "selection"
				)
			).toString(),
			_systemFDSSerializer.serializeFilters(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Shared filters

		FDSFilter dateRangeFDSFilter = _createFDSFilterDate(
			"createDate", "By Creation Date", new DateFDSFilterItem(1, 1, 1980),
			new DateFDSFilterItem(0, 0, 0), null);

		_registerServices(
			_registerFDSFilter(dateRangeFDSFilter, "fdsName1"),
			_registerFDSFilter(dateRangeFDSFilter, "fdsName2"),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			_systemFDSSerializer.serializeFilters(
				"fdsName1", httpServletRequest
			).toString(),
			_systemFDSSerializer.serializeFilters(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		serviceTrackerMap1.close();
		serviceTrackerMap2.close();
	}

	@Test
	public void testSerializeItemsActions() throws Exception {

		// Different items actions

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
				"fdsName1", httpServletRequest));
		Assert.assertEquals(
			fdsActionDropdownItems2,
			_systemFDSSerializer.serializeItemsActions(
				"fdsName2", httpServletRequest));
		Assert.assertNotEquals(
			_systemFDSSerializer.serializeItemsActions(
				"fdsName1", httpServletRequest),
			_systemFDSSerializer.serializeItemsActions(
				"fdsName2", httpServletRequest));

		_unregisterServices();

		// No items actions

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		Assert.assertTrue(
			_systemFDSSerializer.serializeItemsActions(
				"fdsName", httpServletRequest
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
				"fdsName1", httpServletRequest),
			_systemFDSSerializer.serializeItemsActions(
				"fdsName2", httpServletRequest));

		_unregisterServices();

		serviceTrackerMap.close();
	}

	@Test
	public void testSerializeViews() throws Exception {

		// Cards view

		ServiceTrackerMap
			<String,
			 List
				 <ServiceTrackerCustomizerFactory.ServiceWrapper
					 <FDSViewContextContributor>>> serviceTrackerMap1 =
						ServiceTrackerMapFactory.openMultiValueMap(
							_bundleContext, FDSViewContextContributor.class,
							"frontend.data.set.view.name",
							ServiceTrackerCustomizerFactory.
								<FDSViewContextContributor>serviceWrapper(
									_bundleContext));

		_systemFDSSerializer.fdsViewContextContributorRegistry =
			new FDSViewContextContributorRegistryImpl(serviceTrackerMap1);

		ServiceTrackerMap
			<String,
			 List<ServiceTrackerCustomizerFactory.ServiceWrapper<FDSView>>>
				serviceTrackerMap2 = ServiceTrackerMapFactory.openMultiValueMap(
					_bundleContext, FDSView.class, "frontend.data.set.name",
					ServiceTrackerCustomizerFactory.<FDSView>serviceWrapper(
						_bundleContext));

		_systemFDSSerializer.fdsViewRegistry = new FDSViewRegistryImpl(
			serviceTrackerMap2);

		mockLanguage();

		_registerServices(
			_bundleContext.registerService(
				FDSViewContextContributor.class,
				new CardsFDSViewContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.view.name", FDSConstants.CARDS)),
			_registerFDSView(
				"fdsName",
				_createFDSViewCards(
					"longDescription", "thumbnail", "detailURL", "sticker",
					"icon", "title")),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "cards"
				).put(
					"default", false
				).put(
					"label", "cards"
				).put(
					"name", "cards"
				).put(
					"schema",
					JSONUtil.put(
						"description", "longDescription"
					).put(
						"href", "detailURL"
					).put(
						"image", "thumbnail"
					).put(
						"sticker", "sticker"
					).put(
						"symbol", "icon"
					).put(
						"title", "title"
					)
				).put(
					"thumbnail", "cards2"
				)
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Different views

		_registerServices(
			_bundleContext.registerService(
				FDSViewContextContributor.class,
				new CardsFDSViewContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.view.name", FDSConstants.CARDS)),
			_bundleContext.registerService(
				FDSViewContextContributor.class,
				new ListFDSViewContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.view.name", FDSConstants.LIST)),
			_registerFDSView(
				"fdsName1",
				_createFDSViewCards(
					"longDescription", "thumbnail", "detailURL", "sticker",
					"icon", "title")),
			_registerFDSView(
				"fdsName2",
				_createFDSViewList(
					"longDescription", "thumbnail", "sticker", "icon",
					"title")),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "cards"
				).put(
					"default", false
				).put(
					"label", "cards"
				).put(
					"name", "cards"
				).put(
					"schema",
					JSONUtil.put(
						"description", "longDescription"
					).put(
						"href", "detailURL"
					).put(
						"image", "thumbnail"
					).put(
						"sticker", "sticker"
					).put(
						"symbol", "icon"
					).put(
						"title", "title"
					)
				).put(
					"thumbnail", "cards2"
				)
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName1", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "list"
				).put(
					"default", false
				).put(
					"label", "list"
				).put(
					"name", "list"
				).put(
					"schema",
					JSONUtil.put(
						"description", "longDescription"
					).put(
						"image", "thumbnail"
					).put(
						"sticker", "sticker"
					).put(
						"symbol", "icon"
					).put(
						"title", "title"
					)
				).put(
					"thumbnail", "list"
				)
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);
		JSONAssert.assertNotEquals(
			_systemFDSSerializer.serializeViews(
				"fdsName1", httpServletRequest
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Empty view

		_registerServices(
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			"[]",
			_systemFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// List view

		_registerServices(
			_bundleContext.registerService(
				FDSViewContextContributor.class,
				new ListFDSViewContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.view.name", FDSConstants.LIST)),
			_registerFDSView(
				"fdsName",
				_createFDSViewList(
					"longDescription", "thumbnail", "sticker", "icon",
					"title")),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "list"
				).put(
					"default", false
				).put(
					"label", "list"
				).put(
					"name", "list"
				).put(
					"schema",
					JSONUtil.put(
						"description", "longDescription"
					).put(
						"image", "thumbnail"
					).put(
						"sticker", "sticker"
					).put(
						"symbol", "icon"
					).put(
						"title", "title"
					)
				).put(
					"thumbnail", "list"
				)
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		// Shared view

		FDSView cardsView = _createFDSViewCards(
			"longDescription", "thumbnail", "detailURL", "sticker", "icon",
			"title");

		_registerServices(
			_bundleContext.registerService(
				FDSViewContextContributor.class,
				new CardsFDSViewContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.view.name", FDSConstants.CARDS)),
			_registerFDSView("fdsName1", cardsView),
			_registerFDSView("fdsName2", cardsView),
			_registerSystemFDSEntry(
				null, "fdsName1", "/app", "/endpoint", "schema"),
			_registerSystemFDSEntry(
				null, "fdsName2", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			_systemFDSSerializer.serializeViews(
				"fdsName1", httpServletRequest
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName2", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		// Table view

		_registerServices(
			_bundleContext.registerService(
				FDSViewContextContributor.class,
				new TableFDSViewContextContributor(),
				MapUtil.singletonDictionary(
					"frontend.data.set.view.name", FDSConstants.TABLE)),
			_registerFDSView(
				"fdsName",
				new BaseTableFDSView() {

					@Override
					public FDSTableSchema getFDSTableSchema(Locale locale) {
						FDSTableSchemaBuilder fdsTableSchemaBuilder =
							new FDSTableSchemaBuilderImpl();

						return fdsTableSchemaBuilder.add(
							"thumbnail", "picture",
							fdsTableSchemaField ->
								fdsTableSchemaField.setContentRenderer(
									"imageRenderer")
						).add(
							"name", "name",
							fdsTableSchemaField ->
								fdsTableSchemaField.setSortable(true)
						).add(
							"id", "id",
							fdsTableSchemaField ->
								fdsTableSchemaField.setContentRenderer(
									"idRenderer")
						).build();
					}

					@Override
					public boolean isQuickActionsEnabled() {
						return false;
					}

				}),
			_registerSystemFDSEntry(
				null, "fdsName", "/app", "/endpoint", "schema"));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"contentRenderer", "table"
				).put(
					"default", false
				).put(
					"label", "table"
				).put(
					"name", "table"
				).put(
					"quickActionsEnabled", false
				).put(
					"schema",
					JSONUtil.put(
						"fields",
						JSONUtil.putAll(
							JSONUtil.put(
								"contentRenderer", "imageRenderer"
							).put(
								"contentRendererClientExtension", false
							).put(
								"fieldName", "thumbnail"
							).put(
								"label", "picture"
							).put(
								"localizeLabel", true
							).put(
								"sortable", false
							),
							JSONUtil.put(
								"contentRendererClientExtension", false
							).put(
								"fieldName", "name"
							).put(
								"label", "name"
							).put(
								"localizeLabel", true
							).put(
								"sortable", true
							),
							JSONUtil.put(
								"contentRenderer", "idRenderer"
							).put(
								"contentRendererClientExtension", false
							).put(
								"fieldName", "id"
							).put(
								"label", "id"
							).put(
								"localizeLabel", true
							).put(
								"sortable", false
							)))
				).put(
					"thumbnail", "table"
				)
			).toString(),
			_systemFDSSerializer.serializeViews(
				"fdsName", httpServletRequest
			).toString(),
			JSONCompareMode.STRICT);

		_unregisterServices();

		serviceTrackerMap2.close();
	}

	private FDSFilter _createFDSFilterDate(
		String id, String label, DateFDSFilterItem maxDateFDSFilterItem,
		DateFDSFilterItem minDateFDSFilterItem,
		Map<String, Object> preloadedData) {

		return new BaseDateRangeFDSFilter() {

			@Override
			public String getEntityFieldType() {
				return FDSEntityFieldTypes.DATE;
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
				return maxDateFDSFilterItem;
			}

			@Override
			public DateFDSFilterItem getMinDateFDSFilterItem() {
				return minDateFDSFilterItem;
			}

			@Override
			public Map<String, Object> getPreloadedData() {
				return preloadedData;
			}

		};
	}

	private FDSView _createFDSViewCards(
		String description, String image, String link, String sticker,
		String symbol, String title) {

		return new BaseCardsFDSView() {

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public String getImage() {
				return image;
			}

			@Override
			public String getLink() {
				return link;
			}

			@Override
			public String getSticker() {
				return sticker;
			}

			@Override
			public String getSymbol() {
				return symbol;
			}

			@Override
			public String getTitle() {
				return title;
			}

		};
	}

	private FDSView _createFDSViewList(
		String description, String image, String sticker, String symbol,
		String title) {

		return new BaseListFDSView() {

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public String getImage() {
				return image;
			}

			@Override
			public String getSticker() {
				return sticker;
			}

			@Override
			public String getSymbol() {
				return symbol;
			}

			@Override
			public String getTitle() {
				return title;
			}

		};
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
		FDSFilter fdsFilter, String fdsName) {

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

	private ServiceRegistration<FDSView> _registerFDSView(
		String fdsName, FDSView fdsView) {

		return _bundleContext.registerService(
			FDSView.class, fdsView,
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
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();
	private ServiceTrackerMap<String, SystemFDSEntry> _serviceTrackerMap;
	private final SystemFDSSerializer _systemFDSSerializer =
		new SystemFDSSerializer();

}