/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSBulkActions;
import com.liferay.frontend.data.set.internal.BaseSystemFDSSerializerTestCase;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
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
public class SystemFDSBulkActionsSerializerImplTest
	extends BaseSystemFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_bulkActionsServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FDSBulkActions.class, "frontend.data.set.name",
				ServiceTrackerCustomizerFactory.<FDSBulkActions>serviceWrapper(
					bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsBulkActionsRegistryImpl, "_serviceTrackerMap",
			_bulkActionsServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsBulkActionsRegistry",
			_fdsBulkActionsRegistryImpl);
	}

	@After
	public void tearDown() {
		super.tearDown();

		_bulkActionsServiceTrackerMap.close();
	}

	@Test
	public void testSerialize() throws Exception {

		// Different bulk actions

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "trash", "delete", "delete", "delete", "delete",
					"headless"));

		ServiceRegistration<FDSBulkActions> bulkActionsServiceRegistration1 =
			_registerFDSBulkActions(fdsActionDropdownItems1, "fdsName1");

		Assert.assertEquals(
			fdsActionDropdownItems1,
			_fdsSerializer.serialize("fdsName1", httpServletRequest));

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems2 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "cog", "permissions", "permissions", "get",
					"permissions", "modal-permissions"));

		ServiceRegistration<FDSBulkActions> bulkActionsServiceRegistration2 =
			_registerFDSBulkActions(fdsActionDropdownItems2, "fdsName2");

		Assert.assertEquals(
			fdsActionDropdownItems2,
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		Assert.assertNotEquals(
			_fdsSerializer.serialize("fdsName1", httpServletRequest),
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		bulkActionsServiceRegistration1.unregister();
		bulkActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No bulk actions

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_fdsSerializer.serialize(
				"fdsName", httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration1.unregister();

		// Shared bulk actions

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName1", "/app", "/endpoint", "schema");
		systemFDSEntryServiceRegistration2 = registerSystemFDSEntry(
			"fdsName2", "/app", "/endpoint", "schema");

		fdsActionDropdownItems1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		bulkActionsServiceRegistration1 = _registerFDSBulkActions(
			fdsActionDropdownItems1, "fdsName1");
		bulkActionsServiceRegistration2 = _registerFDSBulkActions(
			fdsActionDropdownItems1, "fdsName2");

		Assert.assertEquals(
			_fdsSerializer.serialize("fdsName1", httpServletRequest),
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		bulkActionsServiceRegistration1.unregister();
		bulkActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSBulkActions> _registerFDSBulkActions(
		List<FDSActionDropdownItem> fdsActionDropdownItems, String fdsName) {

		return bundleContext.registerService(
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

	private static ServiceTrackerMap
		<String, ServiceTrackerCustomizerFactory.ServiceWrapper<FDSBulkActions>>
			_bulkActionsServiceTrackerMap;
	private static final FDSBulkActionsRegistryImpl
		_fdsBulkActionsRegistryImpl = new FDSBulkActionsRegistryImpl();
	private static final FDSSerializer<List<FDSActionDropdownItem>>
		_fdsSerializer = new SystemFDSBulkActionsSerializerImpl();

}