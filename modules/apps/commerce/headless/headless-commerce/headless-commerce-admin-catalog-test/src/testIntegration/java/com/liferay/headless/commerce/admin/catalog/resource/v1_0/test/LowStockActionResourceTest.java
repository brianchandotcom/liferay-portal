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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.LowStockAction;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Danny Situ
 */
@RunWith(Arquillian.class)
public class LowStockActionResourceTest
	extends BaseLowStockActionResourceTestCase {

	@Override
	@Test
	public void testGetLowStockActionsPage() throws Exception {
		Page<LowStockAction> page =
			lowStockActionResource.getLowStockActionsPage();

		List<CommerceLowStockActivity> commerceLowStockActivities =
			_commerceLowStockActivityRegistry.getCommerceLowStockActivities();

		Assert.assertEquals(
			commerceLowStockActivities.toString(), page.getTotalCount(),
			commerceLowStockActivities.size());

		assertValid(page);
	}

	@Override
	@Test
	public void testGraphQLGetLowStockActionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"lowStockActions", new GraphQLField("page"),
			new GraphQLField("totalCount"));

		JSONObject lowStockActionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/lowStockActions");

		List<CommerceLowStockActivity> commerceLowStockActivities =
			_commerceLowStockActivityRegistry.getCommerceLowStockActivities();

		Assert.assertEquals(
			commerceLowStockActivities.toString(),
			lowStockActionsJSONObject.get("totalCount"),
			commerceLowStockActivities.size());
	}

	@Inject
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

}