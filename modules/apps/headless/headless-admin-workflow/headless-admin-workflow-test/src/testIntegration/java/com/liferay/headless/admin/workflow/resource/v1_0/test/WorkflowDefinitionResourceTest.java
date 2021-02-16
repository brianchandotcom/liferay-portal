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

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowDefinitionSerDes;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class WorkflowDefinitionResourceTest
	extends BaseWorkflowDefinitionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseWorkflowDefinitionResourceTestCase.setUpClass();

		_workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				TestPropsValues.getCompanyId(), "Single Approver");

		_undeployWorkflowDefinition(
			_workflowDefinition.getName(), _workflowDefinition.getVersion());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		String content = _workflowDefinition.getContent();

		_workflowDefinitionManager.deployWorkflowDefinition(
			_workflowDefinition.getCompanyId(), _workflowDefinition.getUserId(),
			_workflowDefinition.getTitle(), _workflowDefinition.getName(),
			content.getBytes());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		for (WorkflowDefinition workflowDefinition :
				_workflowDefinitionMap.values()) {

			_undeployWorkflowDefinition(
				workflowDefinition.getName(),
				GetterUtil.getInteger(workflowDefinition.getVersion()));
		}

		_workflowDefinitionMap.clear();
	}

	@Override
	@Test
	public void testDeleteWorkflowDefinitionUndeploy() throws Exception {
		WorkflowDefinition workflowDefinition =
			testPostWorkflowDefinitionSave_addWorkflowDefinition(
				randomWorkflowDefinition());

		assertHttpResponseStatusCode(
			204,
			workflowDefinitionResource.
				deleteWorkflowDefinitionUndeployHttpResponse(
					workflowDefinition.getName(),
					workflowDefinition.getVersion()));

		_workflowDefinitionMap.remove(workflowDefinition.getName());
	}

	@Override
	@Test
	public void testGetWorkflowDefinitionByName() throws Exception {
		WorkflowDefinition workflowDefinition =
			testPostWorkflowDefinitionDeploy_addWorkflowDefinition(
				randomWorkflowDefinition());

		assertHttpResponseStatusCode(
			200,
			workflowDefinitionResource.getWorkflowDefinitionByNameHttpResponse(
				workflowDefinition.getName()));
	}

	@Override
	@Test
	public void testGraphQLGetWorkflowDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"workflowDefinitions",
			HashMapBuilder.<String, Object>put(
				"page", 1
			).put(
				"pageSize", 2
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject workflowDefinitionsJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/workflowDefinitions");

		Assert.assertEquals(0, workflowDefinitionsJSONObject.get("totalCount"));

		WorkflowDefinition workflowDefinition1 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());
		WorkflowDefinition workflowDefinition2 =
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				randomWorkflowDefinition());

		workflowDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/workflowDefinitions");

		Assert.assertEquals(2, workflowDefinitionsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowDefinition1, workflowDefinition2),
			Arrays.asList(
				WorkflowDefinitionSerDes.toDTOs(
					workflowDefinitionsJSONObject.getString("items"))));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name", "title", "version"};
	}

	@Override
	protected WorkflowDefinition randomWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition =
			super.randomWorkflowDefinition();

		workflowDefinition.setActive(true);
		workflowDefinition.setContent(
			WorkflowDefinitionTestUtil.getContent(
				workflowDefinition.getDescription(),
				workflowDefinition.getName()));
		workflowDefinition.setVersion("1");

		return workflowDefinition;
	}

	@Override
	protected WorkflowDefinition
			testGetWorkflowDefinitionsPage_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		workflowDefinition =
			workflowDefinitionResource.postWorkflowDefinitionDeploy(
				workflowDefinition);

		_workflowDefinitionMap.put(
			workflowDefinition.getName(), workflowDefinition);

		return workflowDefinition;
	}

	@Override
	protected WorkflowDefinition
			testPostWorkflowDefinitionDeploy_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		return testGetWorkflowDefinitionsPage_addWorkflowDefinition(
			workflowDefinition);
	}

	@Override
	protected WorkflowDefinition
			testPostWorkflowDefinitionSave_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		workflowDefinition.setActive(false);

		workflowDefinition =
			workflowDefinitionResource.postWorkflowDefinitionSave(
				workflowDefinition);

		_workflowDefinitionMap.put(
			workflowDefinition.getName(), workflowDefinition);

		return workflowDefinition;
	}

	@Override
	protected WorkflowDefinition
			testPostWorkflowDefinitionUpdateActive_addWorkflowDefinition(
				WorkflowDefinition workflowDefinition)
		throws Exception {

		return testGetWorkflowDefinitionsPage_addWorkflowDefinition(
			workflowDefinition);
	}

	private static void _undeployWorkflowDefinition(
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws Exception {

		_workflowDefinitionManager.updateActive(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowDefinitionName, workflowDefinitionVersion, false);

		_workflowDefinitionManager.undeployWorkflowDefinition(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowDefinitionName, workflowDefinitionVersion);
	}

	private static com.liferay.portal.kernel.workflow.WorkflowDefinition
		_workflowDefinition;

	@Inject
	private static WorkflowDefinitionManager _workflowDefinitionManager;

	private final Map<String, WorkflowDefinition> _workflowDefinitionMap =
		new HashMap<>();

}