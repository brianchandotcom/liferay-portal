/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSBulkActionList;
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
public class SystemFDSBulkActionListSerializerImplTest
	extends BaseSystemFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_bulkActionListServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FDSBulkActionList.class,
				"frontend.data.set.name",
				ServiceTrackerCustomizerFactory.
					<FDSBulkActionList>serviceWrapper(bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsBulkActionListRegistryImpl, "_serviceTrackerMap",
			_bulkActionListServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSBulkActionListSerializerImpl,
			"_fdsBulkActionListRegistry", _fdsBulkActionListRegistryImpl);
	}

	@After
	public void tearDown() {
		super.tearDown();

		_bulkActionListServiceTrackerMap.close();
	}

	@Test
	public void testFDSBulkActionListSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> dropDownItemList = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		ServiceRegistration<FDSBulkActionList>
			bulkActionListServiceRegistration = _registerBulkActionList(
				"fdsName", dropDownItemList);

		Assert.assertEquals(
			dropDownItemList,
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName", httpServletRequest));

		bulkActionListServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSBulkActionListSerializationNoBulkActionList()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName", httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSBulkActionListSerializationSeparateBulkActionLists()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> dropDownItemList1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		ServiceRegistration<FDSBulkActionList>
			bulkActionListServiceRegistration1 = _registerBulkActionList(
				"fdsName1", dropDownItemList1);

		List<FDSActionDropdownItem> dropDownItemList2 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "cog", "permissions", "permissions", "get", "permissions",
				"modal-permissions"));

		ServiceRegistration<FDSBulkActionList>
			bulkActionListServiceRegistration2 = _registerBulkActionList(
				"fdsName2", dropDownItemList2);

		Assert.assertNotEquals(
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName1", httpServletRequest),
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName2", httpServletRequest));

		Assert.assertEquals(
			dropDownItemList1,
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName1", httpServletRequest));

		Assert.assertEquals(
			dropDownItemList2,
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName2", httpServletRequest));

		bulkActionListServiceRegistration1.unregister();

		bulkActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSBulkActionListSerializationSharingBulkActionList()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> dropDownItemList = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		ServiceRegistration<FDSBulkActionList>
			bulkActionListServiceRegistration1 = _registerBulkActionList(
				"fdsName1", dropDownItemList);

		ServiceRegistration<FDSBulkActionList>
			bulkActionListServiceRegistration2 = _registerBulkActionList(
				"fdsName2", dropDownItemList);

		Assert.assertEquals(
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName1", httpServletRequest),
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName2", httpServletRequest));

		bulkActionListServiceRegistration1.unregister();

		bulkActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSBulkActionList> _registerBulkActionList(
		String fdsName, List<FDSActionDropdownItem> bulkActions) {

		return bundleContext.registerService(
			FDSBulkActionList.class,
			new FDSBulkActionList() {

				@Override
				public List<FDSActionDropdownItem> getFDSActionDropdownItems(
					HttpServletRequest httpServletRequest) {

					return bulkActions;
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSBulkActionList>>
			_bulkActionListServiceTrackerMap;
	private static final FDSBulkActionListRegistryImpl
		_fdsBulkActionListRegistryImpl = new FDSBulkActionListRegistryImpl();
	private static final SystemFDSBulkActionListSerializerImpl
		_systemFDSBulkActionListSerializerImpl =
			new SystemFDSBulkActionListSerializerImpl();

}