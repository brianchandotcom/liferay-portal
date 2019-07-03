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

package com.liferay.headless.admin.workflow.graphql.v1_0.test;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.query.GraphQLField;
import com.liferay.portal.vulcan.util.GraphQLQueryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseWorkflowLogGraphQLTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetWorkflowLog() throws Exception {
		WorkflowLog postWorkflowLog = testGetWorkflowLog_addWorkflowLog();

		Map<String, Object> parameterMap = new HashMap<String, Object>() {
			{
				put("workflowLogId", postWorkflowLog.getId());
			}
		};

		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(GraphQLQueryUtil.field("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(
				GraphQLQueryUtil.field(additionalAssertFieldName));
		}

		GraphQLField graphQLField = GraphQLQueryUtil.field(
			"query",
			GraphQLQueryUtil.field(
				"getWorkflowLog", parameterMap,
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject responseJSONObject = new JSONObjectImpl(
			_invoke(graphQLField.toString()));

		JSONObject dataJSONObject = responseJSONObject.getJSONObject("data");

		JSONObject jsonObject = dataJSONObject.getJSONObject("getWorkflowLog");

		Assert.assertTrue(equals(postWorkflowLog, jsonObject));
	}

	protected WorkflowLog testGetWorkflowLog_addWorkflowLog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected boolean equals(WorkflowLog workflowLog, JSONObject jsonObject) {
		List<String> fields = new ArrayList(
			Arrays.asList(getAdditionalAssertFieldNames()));

		fields.add("id");

		for (String field : fields) {
			if (Objects.equals("commentLog", field)) {
				if (!Objects.equals(
						workflowLog.getCommentLog(),
						(String)jsonObject.getString("commentLog"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", field)) {
				if (!Objects.equals(
						workflowLog.getId(), (Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("previousState", field)) {
				if (!Objects.equals(
						workflowLog.getPreviousState(),
						(String)jsonObject.getString("previousState"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("state", field)) {
				if (!Objects.equals(
						workflowLog.getState(),
						(String)jsonObject.getString("state"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskId", field)) {
				if (!Objects.equals(
						workflowLog.getTaskId(),
						(Long)jsonObject.getLong("taskId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", field)) {
				if (!Objects.equals(
						workflowLog.getType(),
						(String)jsonObject.getString("type"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " + field);
		}

		return true;
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected WorkflowLog randomWorkflowLog() throws Exception {
		return new WorkflowLog() {
			{
				commentLog = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				previousState = RandomTestUtil.randomString();
				state = RandomTestUtil.randomString();
				taskId = RandomTestUtil.randomLong();
				type = RandomTestUtil.randomString();
			}
		};
	}

	private String _invoke(String query) throws IOException {
		JSONObject jsonObject = JSONUtil.put("query", query);

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();
		httpInvoker.body(jsonObject.toString(), "application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected Company testCompany;
	protected Group testGroup;

}