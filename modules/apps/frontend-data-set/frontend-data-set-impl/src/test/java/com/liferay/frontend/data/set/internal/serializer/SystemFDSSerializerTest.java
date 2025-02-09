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
import com.liferay.frontend.data.set.internal.SystemFDSEntryRegistryImpl;
import com.liferay.frontend.data.set.internal.action.FDSBulkActionsRegistryImpl;
import com.liferay.frontend.data.set.internal.action.FDSCreationMenuRegistryImpl;
import com.liferay.frontend.data.set.internal.action.FDSItemsActionsRegistryImpl;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

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

	private BundleContext _bundleContext = SystemBundleUtil.getBundleContext();
	private final FDSSerializer _fdsSerializer = new SystemFDSSerializer();
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private ServiceTrackerMap<String, SystemFDSEntry> _serviceTrackerMap;
	private final SystemFDSEntryRegistry _systemFDSEntryRegistry =
		new SystemFDSEntryRegistryImpl();

}