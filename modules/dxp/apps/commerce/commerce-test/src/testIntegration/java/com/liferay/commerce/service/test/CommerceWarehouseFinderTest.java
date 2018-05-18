/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.internal.test.util.CommerceTestUtil;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.service.CommerceWarehouseLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceWarehouseFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		long groupId = _group.getGroupId();

		_cpInstances = new CPInstance[] {
			CPTestUtil.addCPInstance(groupId),
			CPTestUtil.addCPInstance(groupId), CPTestUtil.addCPInstance(groupId)
		};

		_addCommerceWarehouse("Commerce Warehouse 1", 50, 40, 60);
		_addCommerceWarehouse("Commerce Warehouse 2", 20, 10);
		_addCommerceWarehouse("Commerce Warehouse 3", 0, 0, 100);
		_addCommerceWarehouse("Commerce Warehouse 4", 100, 10);
	}

	@Test
	public void testFindByCPInstance() {
		_testFindByCPInstance(
			_cpInstances[0], "Commerce Warehouse 1", "Commerce Warehouse 2",
			"Commerce Warehouse 4");
	}

	private CommerceWarehouse _addCommerceWarehouse(
			String name, int... quantities)
		throws Exception {

		CommerceWarehouse commerceWarehouse =
			CommerceTestUtil.addCommerceWarehouse(_group.getGroupId(), name);

		for (int i = 0; i < quantities.length; i++) {
			int quantity = quantities[i];

			if (quantity <= 0) {
				continue;
			}

			CPInstance cpInstance = _cpInstances[i];

			CommerceTestUtil.addCommerceWarehouseItem(
				commerceWarehouse, cpInstance.getCPInstanceId(), quantity);
		}

		return commerceWarehouse;
	}

	private void _testFindByCPInstance(
		CPInstance cpInstance, String... expectedCommerceWarehouseNames) {

		List<CommerceWarehouse> commerceWarehouses =
			CommerceWarehouseLocalServiceUtil.getCommerceWarehouses(
				cpInstance.getCPInstanceId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			commerceWarehouses.toString(),
			expectedCommerceWarehouseNames.length, commerceWarehouses.size());

		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
			Assert.assertTrue(
				ArrayUtil.contains(
					expectedCommerceWarehouseNames,
					commerceWarehouse.getName()));
		}
	}

	private CPInstance[] _cpInstances;

	@DeleteAfterTestRun
	private Group _group;

}