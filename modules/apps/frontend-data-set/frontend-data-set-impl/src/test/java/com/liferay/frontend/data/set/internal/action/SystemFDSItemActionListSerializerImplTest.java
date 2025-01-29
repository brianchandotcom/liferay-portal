/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSItemActionList;
import com.liferay.frontend.data.set.internal.BaseSystemFDSSerializerTestCase;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
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

import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public class SystemFDSItemActionListSerializerImplTest
	extends BaseSystemFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_itemActionListServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FDSItemActionList.class,
				"frontend.data.set.name",
				ServiceTrackerCustomizerFactory.
					<FDSItemActionList>serviceWrapper(bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsItemActionListRegistryImpl, "_serviceTrackerMap",
			_itemActionListServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSItemActionListSerializerImpl,
			"_fdsItemActionListRegistry", _fdsItemActionListRegistryImpl);
	}

	@After
	public void tearDown() {
		super.tearDown();

		_itemActionListServiceTrackerMap.close();
	}

	@Test
	public void testFDSItemActionListSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

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
				"fdsName", httpServletRequest));

		itemActionListServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSItemActionListSerializationNoItemActionList()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSItemActionListSerializationSeparateItemActionLists()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

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
				"fdsName1", httpServletRequest),
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName2", httpServletRequest));

		Assert.assertEquals(
			dropDownItemList1,
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName1", httpServletRequest));

		Assert.assertEquals(
			dropDownItemList2,
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName2", httpServletRequest));

		itemActionListServiceRegistration1.unregister();

		itemActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSItemActionListSerializationSharingItemActionList()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

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
				"fdsName1", httpServletRequest),
			_systemFDSItemActionListSerializerImpl.serialize(
				"fdsName2", httpServletRequest));

		itemActionListServiceRegistration1.unregister();

		itemActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSItemActionList> _registerItemActionList(
		String fdsName, List<FDSActionDropdownItem> itemActions) {

		return bundleContext.registerService(
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

	private static final FDSItemActionListRegistryImpl
		_fdsItemActionListRegistryImpl = new FDSItemActionListRegistryImpl();
	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSItemActionList>>
			_itemActionListServiceTrackerMap;
	private static final SystemFDSItemActionListSerializerImpl
		_systemFDSItemActionListSerializerImpl =
			new SystemFDSItemActionListSerializerImpl();

}