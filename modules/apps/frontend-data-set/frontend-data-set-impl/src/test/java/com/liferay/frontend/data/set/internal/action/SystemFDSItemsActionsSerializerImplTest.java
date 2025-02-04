/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSItemsActions;
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
public class SystemFDSItemsActionsSerializerImplTest
	extends BaseSystemFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_itemsActionsServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, FDSItemsActions.class, "frontend.data.set.name",
				ServiceTrackerCustomizerFactory.<FDSItemsActions>serviceWrapper(
					bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsItemsActionsRegistryImpl, "_serviceTrackerMap",
			_itemsActionsServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsItemsActionsRegistry",
			_fdsItemsActionsRegistryImpl);
	}

	@After
	public void tearDown() {
		super.tearDown();

		_itemsActionsServiceTrackerMap.close();
	}

	@Test
	public void testSerialize() throws Exception {

		// Different items actions

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		List<FDSActionDropdownItem> fdsActionDropdownItems1 =
			ListUtil.fromArray(
				new FDSActionDropdownItem(
					null, "trash", "delete", "delete", "delete", "delete",
					"headless"));

		ServiceRegistration<FDSItemsActions> itemsActionsServiceRegistration1 =
			_registerFDSItemsActions("fdsName1", fdsActionDropdownItems1);

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

		ServiceRegistration<FDSItemsActions> itemsActionsServiceRegistration2 =
			_registerFDSItemsActions("fdsName2", fdsActionDropdownItems2);

		Assert.assertEquals(
			fdsActionDropdownItems2,
			_fdsSerializer.serialize("fdsName2", httpServletRequest));
		Assert.assertNotEquals(
			_fdsSerializer.serialize("fdsName1", httpServletRequest),
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		itemsActionsServiceRegistration1.unregister();
		itemsActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No items actions

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_fdsSerializer.serialize(
				"fdsName", httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration1.unregister();

		// Shared items actions

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName1", "/app", "/endpoint", "schema");
		systemFDSEntryServiceRegistration2 = registerSystemFDSEntry(
			"fdsName2", "/app", "/endpoint", "schema");

		fdsActionDropdownItems1 = ListUtil.fromArray(
			new FDSActionDropdownItem(
				null, "trash", "delete", "delete", "delete", "delete",
				"headless"));

		itemsActionsServiceRegistration1 = _registerFDSItemsActions(
			"fdsName1", fdsActionDropdownItems1);
		itemsActionsServiceRegistration2 = _registerFDSItemsActions(
			"fdsName2", fdsActionDropdownItems1);

		Assert.assertEquals(
			_fdsSerializer.serialize("fdsName1", httpServletRequest),
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		itemsActionsServiceRegistration1.unregister();
		itemsActionsServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSItemsActions> _registerFDSItemsActions(
		String fdsName, List<FDSActionDropdownItem> fdsActionDropdownItems) {

		return bundleContext.registerService(
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

	private static final FDSItemsActionsRegistryImpl
		_fdsItemsActionsRegistryImpl = new FDSItemsActionsRegistryImpl();
	private static final FDSSerializer<List<FDSActionDropdownItem>>
		_fdsSerializer = new SystemFDSItemsActionsSerializerImpl();
	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSItemsActions>>
			_itemsActionsServiceTrackerMap;

}