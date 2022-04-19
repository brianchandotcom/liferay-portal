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
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Arquillian.class)
@Sync
public class CommerceInventoryWarehouseFinderTest {

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

		_addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), 50, 40, 60);
		_addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), 20, 10);
		_addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), 0, 0, 100);
		_addCommerceInventoryWarehouse(
			RandomTestUtil.randomLocaleStringMap(), 100, 10);
	}

	private CommerceInventoryWarehouse _addCommerceInventoryWarehouse(
			Map<Locale, String> nameMap, int... quantities)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(nameMap);

		for (int i = 0; i < quantities.length; i++) {
			int quantity = quantities[i];

			if (quantity <= 0) {
				continue;
			}

			CPInstance cpInstance = _cpInstances[i];

			CommerceInventoryTestUtil.addCommerceInventoryWarehouseItem(
				commerceInventoryWarehouse.getUserId(),
				commerceInventoryWarehouse, cpInstance.getSku(), quantity);
		}

		return commerceInventoryWarehouse;
	}

	private CPInstance[] _cpInstances;

	@DeleteAfterTestRun
	private Group _group;

}