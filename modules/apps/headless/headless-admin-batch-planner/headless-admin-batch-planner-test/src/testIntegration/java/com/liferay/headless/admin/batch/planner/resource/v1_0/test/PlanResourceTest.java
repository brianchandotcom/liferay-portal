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

package com.liferay.headless.admin.batch.planner.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.batch.planner.client.dto.v1_0.Plan;
import com.liferay.headless.admin.batch.planner.client.pagination.Page;
import com.liferay.headless.admin.batch.planner.client.pagination.Pagination;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class PlanResourceTest extends BasePlanResourceTestCase {

	@Override
	@Test
	public void testGetPlansPage() throws Exception {
		Page<Plan> plansPage = planResource.getPlansPage(Pagination.of(1, 5));

		Assert.assertEquals(0, plansPage.getTotalCount());

		Collection<Plan> plans = plansPage.getItems();

		Assert.assertTrue(plans.isEmpty());

		List<Plan> randomPlans = new ArrayList<>();
		int randomPlansCount = RandomTestUtil.randomInt(5, 10);

		for (int i = 0; i < randomPlansCount; i++) {
			randomPlans.add(_addPlan());
		}

		plansPage = planResource.getPlansPage(Pagination.of(1, 5));

		Assert.assertEquals(randomPlansCount, plansPage.getTotalCount());

		plans = plansPage.getItems();

		Assert.assertEquals(plans.toString(), 5, plans.size());
		Assert.assertTrue(randomPlans.containsAll(plans));
	}

	@Override
	protected Plan randomPlan() {
		return new Plan() {
			{
				active = RandomTestUtil.randomBoolean();
				export = RandomTestUtil.randomBoolean();
				externalType = "JSON";
				externalURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				internalClassName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected Plan testDeletePlan_addPlan() throws Exception {
		return _addPlan();
	}

	@Override
	protected Plan testGetPlan_addPlan() throws Exception {
		return _addPlan();
	}

	@Override
	protected Plan testPatchPlan_addPlan() throws Exception {
		return _addPlan();
	}

	@Override
	protected Plan testPostPlan_addPlan(Plan plan) throws Exception {
		return _addPlan();
	}

	private Plan _addPlan() throws Exception {
		return planResource.postPlan(randomPlan());
	}

}