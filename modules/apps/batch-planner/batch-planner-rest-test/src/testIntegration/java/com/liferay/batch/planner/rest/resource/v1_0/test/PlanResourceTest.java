/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.rest.client.dto.v1_0.Plan;
import com.liferay.batch.planner.rest.client.http.HttpInvoker;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
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

		HttpInvoker.HttpResponse response =
			planResource.getPlanTemplateHttpResponse(
				"com.liferay.object.rest.dto.v1_0.ObjectEntry" +
					URLCodec.encodeURL("#") + objectDefinition.getName());

		Assert.assertEquals(200, response.getStatusCode());

		String[] lines = StringUtil.split(
			response.getContent(), System.lineSeparator());

		Assert.assertTrue(
			StringBundler.concat(
				"The first line '", lines[0], "' does not contain the field '",
				fieldName, "'"),
			StringUtil.contains(lines[0], fieldName));
	}

	@Override
	protected Plan randomPatchPlan() {
		Plan plan = randomPlan();

		plan.setTemplate(true);

		return plan;
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
				internalClassName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				template = RandomTestUtil.randomBoolean();
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
		Plan plan = randomPlan();

		plan.setTemplate(true);

		return planResource.postPlan(plan);
	}

	@Override
	protected Plan testPostPlan_addPlan(Plan plan) throws Exception {
		return _addPlan();
	}

	private Plan _addPlan() throws Exception {
		return planResource.postPlan(randomPlan());
	}

}