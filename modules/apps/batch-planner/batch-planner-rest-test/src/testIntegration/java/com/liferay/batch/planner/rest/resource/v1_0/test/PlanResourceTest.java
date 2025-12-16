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
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

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
					).build()),
				false);

		HttpInvoker.HttpResponse httpResponse1 =
			planResource.getPlanTemplateHttpResponse(
				"com.liferay.object.rest.dto.v1_0.ObjectEntry" +
					URLCodec.encodeURL("#") + objectDefinition.getName());

		Assert.assertEquals(200, httpResponse1.getStatusCode());

		String[] lines = StringUtil.split(
			httpResponse1.getContent(), System.lineSeparator());

		Assert.assertTrue(StringUtil.contains(lines[0], fieldName));

		HttpInvoker.HttpResponse httpResponse2 =
			planResource.getPlanTemplateHttpResponse(
				"com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition");

		Assert.assertEquals(200, httpResponse2.getStatusCode());

		lines = StringUtil.split(
			httpResponse2.getContent(), System.lineSeparator());

		lines = _sortCSVHeaderAndRows(lines);

		String planTemplateString = StreamUtil.toString(_getInputStream());

		String[] expectedLines = StringUtil.split(
			planTemplateString, System.lineSeparator());

		Assert.assertEquals(
			Arrays.toString(lines), expectedLines.length, lines.length);
		Assert.assertEquals(expectedLines[0], lines[0]);
		Assert.assertEquals(expectedLines[1], lines[1]);
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

	private InputStream _getInputStream() {
		return PlanResourceTest.class.getClassLoader(
		).getResourceAsStream(
			"com/liferay/batch/planner/rest/resource/v1_0/test/dependencies" +
				"/object_definition_template.csv"
		);
	}

	private String[] _sortCSVHeaderAndRows(String[] csvLines) {
		if (csvLines.length < 2) {
			return csvLines;
		}

		String[] headers = StringUtil.split(csvLines[0], ',');
		String[] dataRow = StringUtil.split(csvLines[1], ',');

		if (headers.length != dataRow.length) {
			return csvLines;
		}

		Map<String, String> map = new TreeMap<>();

		for (int i = 0; i < headers.length; i++) {
			map.put(headers[i], dataRow[i]);
		}

		return new String[] {
			StringUtil.merge(map.keySet(), ","),
			StringUtil.merge(map.values(), ",")
		};
	}

	private Plan _plan;

}