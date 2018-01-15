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
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

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
public class CommerceOrderFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_addCommerceOrders(10, CommerceOrderConstants.ORDER_STATUS_CANCELLED);
		_addCommerceOrders(15, CommerceOrderConstants.ORDER_STATUS_COMPLETED);
		_addCommerceOrders(20, CommerceOrderConstants.ORDER_STATUS_PROCESSING);
	}

	@Test
	public void testCountByG_S() {
		Map<Integer, Long> orderStatusCounts =
			CommerceOrderLocalServiceUtil.getCommerceOrdersCount(
				_group.getGroupId());

		Assert.assertEquals(
			orderStatusCounts.toString(), 3, orderStatusCounts.size());
		Assert.assertEquals(
			(Long)orderStatusCounts.get(
				CommerceOrderConstants.ORDER_STATUS_CANCELLED),
			Long.valueOf(10));
		Assert.assertEquals(
			(Long)orderStatusCounts.get(
				CommerceOrderConstants.ORDER_STATUS_COMPLETED),
			Long.valueOf(15));
		Assert.assertEquals(
			(Long)orderStatusCounts.get(
				CommerceOrderConstants.ORDER_STATUS_PROCESSING),
			Long.valueOf(20));
	}

	private void _addCommerceOrders(int count, int orderStatus)
		throws Exception {

		for (int i = 0; i < count; i++) {
			CommerceTestUtil.addCommerceOrder(_group.getGroupId(), orderStatus);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}