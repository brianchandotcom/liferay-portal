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

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.rest.client.dto.v1_0.Plan;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class PlanResourceTest extends BasePlanResourceTestCase {

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
	protected Plan testGetPlansPage_addPlan(Plan plan) throws Exception {
		return planResource.postPlan(plan);
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