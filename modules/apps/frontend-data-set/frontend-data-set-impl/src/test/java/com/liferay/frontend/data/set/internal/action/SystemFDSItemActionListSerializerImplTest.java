/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSItemActionList;
import com.liferay.frontend.data.set.internal.SystemFDSEntryRegistryImpl;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
public class SystemFDSItemActionListSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bundleContext = SystemBundleUtil.getBundleContext();

		_systemFDSEntryserviceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, SystemFDSEntry.class, "frontend.data.set.name");

		ReflectionTestUtil.setFieldValue(
			_systemFDSEntryRegistryImpl, "_serviceTrackerMap",
			_systemFDSEntryserviceTrackerMap);

		_itemActionListServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, FDSItemActionList.class,
				"frontend.data.set.name",
				ServiceTrackerCustomizerFactory.
					<FDSItemActionList>serviceWrapper(_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsItemActionListRegistryImpl, "_serviceTrackerMap",
			_itemActionListServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSItemActionListSerializerImpl,
			"_fdsItemActionListRegistry", _fdsItemActionListRegistryImpl);
	}

	@After
	public void tearDown() {
		_itemActionListServiceTrackerMap.close();
		_systemFDSEntryserviceTrackerMap.close();
	}

	@Test
	public void testFDSCreationMenuSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> dropDownItemList = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		ServiceRegistration<FDSItemActionList>
			itemActionListServiceRegistration = _registerItemActionList(
				"fdsName", dropDownItemList);

		Assert.assertEquals(
			dropDownItemList,
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName", _httpServletRequest));

		itemActionListServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSCreationMenuSerializationNoCreationMenu()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSCreationMenuSerializationSeparateCreationMenus()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> dropDownItemList1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		ServiceRegistration<FDSItemActionList>
			itemActionListServiceRegistration1 = _registerItemActionList(
				"fdsName1", dropDownItemList1);

		List<FDSActionDropdownItem> dropDownItemList2 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "cog", "permissions", "permissions", "get", "permissions",
				"modal-permissions"));

		ServiceRegistration<FDSItemActionList>
			itemActionListServiceRegistration2 = _registerItemActionList(
				"fdsName2", dropDownItemList2);

		Assert.assertNotEquals(
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName1", _httpServletRequest),
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		Assert.assertEquals(
			dropDownItemList1,
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			dropDownItemList2,
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		itemActionListServiceRegistration1.unregister();

		itemActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSCreationMenuSerializationSharingCreationMenu()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> dropDownItemList = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		ServiceRegistration<FDSItemActionList>
			itemActionListServiceRegistration1 = _registerItemActionList(
				"fdsName1", dropDownItemList);

		ServiceRegistration<FDSItemActionList>
			itemActionListServiceRegistration2 = _registerItemActionList(
				"fdsName2", dropDownItemList);

		Assert.assertEquals(
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName1", _httpServletRequest),
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		itemActionListServiceRegistration1.unregister();

		itemActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSItemActionList> _registerItemActionList(
		String fdsName, List<FDSActionDropdownItem> itemActions) {

		return _bundleContext.registerService(
			FDSItemActionList.class,
			new FDSItemActionList() {

				@Override
				public List<FDSActionDropdownItem> getFDSActionDropdownItems(
					HttpServletRequest httpServletRequest) {

					return itemActions;
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		return _registerSystemFDSEntry(
			fdsName, restApplication, restEndpoint, restSchema, null);
	}

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema, String additionalURLParameters) {

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

	private static BundleContext _bundleContext;
	private static final FDSItemActionListRegistryImpl
		_fdsItemActionListRegistryImpl = new FDSItemActionListRegistryImpl();
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSItemActionList>>
			_itemActionListServiceTrackerMap;
	private static final SystemFDSEntryRegistryImpl
		_systemFDSEntryRegistryImpl = new SystemFDSEntryRegistryImpl();
	private static ServiceTrackerMap<String, SystemFDSEntry>
		_systemFDSEntryserviceTrackerMap;
	private static final SystemFDSItemActionListSerializerImpl
		_systemFDSItemActionListSerializerImpl =
			new SystemFDSItemActionListSerializerImpl();

}