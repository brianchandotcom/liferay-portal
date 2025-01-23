/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSBulkActionList;
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
public class SystemFDSBulkActionListSerializerImplTest {

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

		_bulkActionListServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, FDSBulkActionList.class,
				"frontend.data.set.name",
				ServiceTrackerCustomizerFactory.
					<FDSBulkActionList>serviceWrapper(_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsBulkActionListRegistryImpl, "_serviceTrackerMap",
			_bulkActionListServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSBulkActionListSerializerImpl,
			"_fdsBulkActionListRegistry", _fdsBulkActionListRegistryImpl);
	}

	@After
	public void tearDown() {
		_bulkActionListServiceTrackerMap.close();
		_systemFDSEntryserviceTrackerMap.close();
	}

	@Test
	public void testFDSBulkActionListSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

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
				"fdsName", _httpServletRequest));

		bulkActionListServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSBulkActionListSerializationNoBulkActionList()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSBulkActionListSerializationSeparateBulkActionLists()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

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
				"fdsName1", _httpServletRequest),
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		Assert.assertEquals(
			dropDownItemList1,
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			dropDownItemList2,
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		bulkActionListServiceRegistration1.unregister();

		bulkActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSBulkActionListSerializationSharingBulkActionList()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

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
				"fdsName1", _httpServletRequest),
			_systemFDSBulkActionListSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		bulkActionListServiceRegistration1.unregister();

		bulkActionListServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSBulkActionList> _registerBulkActionList(
		String fdsName, List<FDSActionDropdownItem> bulkActions) {

		return _bundleContext.registerService(
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

	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSBulkActionList>>
			_bulkActionListServiceTrackerMap;
	private static BundleContext _bundleContext;
	private static final FDSBulkActionListRegistryImpl
		_fdsBulkActionListRegistryImpl = new FDSBulkActionListRegistryImpl();
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static final SystemFDSBulkActionListSerializerImpl
		_systemFDSBulkActionListSerializerImpl =
			new SystemFDSBulkActionListSerializerImpl();
	private static final SystemFDSEntryRegistryImpl
		_systemFDSEntryRegistryImpl = new SystemFDSEntryRegistryImpl();
	private static ServiceTrackerMap<String, SystemFDSEntry>
		_systemFDSEntryserviceTrackerMap;

}