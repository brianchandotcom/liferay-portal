/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.batch.engine.task.TaskItemUtil;
import com.liferay.batch.planner.rest.client.dto.v1_0.Plan;
import com.liferay.batch.planner.rest.client.http.HttpInvoker;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;

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
	public void testGetPlanTemplate() throws Exception {
		assertHttpResponseStatusCode(
			200,
			planResource.getPlanTemplateHttpResponse(
				"com.liferay.headless.admin.user.dto.v1_0.Account"));

		String fieldName = "a" + RandomTestUtil.randomString();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						fieldName
					).build()));

		HttpInvoker.HttpResponse httpResponse =
			planResource.getPlanTemplateHttpResponse(
				"com.liferay.object.rest.dto.v1_0.ObjectEntry" +
					URLCodec.encodeURL("#") + objectDefinition.getName());

		Assert.assertEquals(200, httpResponse.getStatusCode());

		String[] lines = StringUtil.split(
			httpResponse.getContent(), System.lineSeparator());

		Assert.assertTrue(StringUtil.contains(lines[0], fieldName));
	}

	@Override
	@Test
	public void testPostPlan() throws Exception {
		super.testPostPlan();

		Plan randomPlan = randomPlan();

		randomPlan.setTaskItemDelegateName(RandomTestUtil.randomString());

		Plan postPlan = testPostPlan_addPlan(randomPlan);

		assertEquals(randomPlan, postPlan);
		assertValid(postPlan);

		randomPlan = randomPlan();

		randomPlan.setTaskItemDelegateName("DEFAULT");

		postPlan = testPostPlan_addPlan(randomPlan);

		assertEquals(randomPlan, postPlan);
		assertValid(postPlan);
	}

	@Override
	protected void assertEquals(Plan plan1, Plan plan2) {
		super.assertEquals(plan1, plan2);

		Assert.assertEquals(
			TaskItemUtil.getInternalClassNameKey(
				plan1.getInternalClassName(), plan1.getTaskItemDelegateName()),
			plan2.getInternalClassNameKey());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "externalType", "externalURL", "internalClassName",
			"name", "template"
		};
	}

	@Override
	protected Plan randomPatchPlan() {
		Plan plan = randomPlan();

		plan.setActive(true);
		plan.setExternalURL(_plan.getExternalURL());
		plan.setTemplate(true);

		return plan;
	}

	@Override
	protected Plan randomPlan() {
		return new Plan() {
			{
				active = true;
				export = RandomTestUtil.randomBoolean();
				externalType = "JSON";
				externalURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				internalClassName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				template = RandomTestUtil.randomBoolean();
			}
		};
	}

	@Override
	protected Plan testDeletePlan_addPlan() throws Exception {
		return _addPlan(randomPlan());
	}

	@Override
	protected Plan testGetPlan_addPlan() throws Exception {
		return _addPlan(randomPlan());
	}

	@Override
	protected Plan testGetPlansPage_addPlan(Plan plan) throws Exception {
		return _addPlan(plan);
	}

	@Override
	protected Plan testPatchPlan_addPlan() throws Exception {
		_plan = randomPlan();

		_plan.setTemplate(true);

		return _addPlan(_plan);
	}

	@Override
	protected Plan testPostPlan_addPlan(Plan plan) throws Exception {
		return _addPlan(plan);
	}

	private Plan _addPlan(Plan plan) throws Exception {
		return planResource.postPlan(plan);
	}

	private Plan _plan;

}