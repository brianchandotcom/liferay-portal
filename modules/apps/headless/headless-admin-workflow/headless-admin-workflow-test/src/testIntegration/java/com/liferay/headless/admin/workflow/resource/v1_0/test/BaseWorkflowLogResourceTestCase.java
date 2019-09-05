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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowLogResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowLogSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseWorkflowLogResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_workflowLogResource.setContextCompany(testCompany);

		WorkflowLogResource.Builder builder = WorkflowLogResource.builder();

		workflowLogResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		WorkflowLog workflowLog1 = randomWorkflowLog();

		String json = objectMapper.writeValueAsString(workflowLog1);

		WorkflowLog workflowLog2 = WorkflowLogSerDes.toDTO(json);

		Assert.assertTrue(equals(workflowLog1, workflowLog2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		WorkflowLog workflowLog = randomWorkflowLog();

		String json1 = objectMapper.writeValueAsString(workflowLog);
		String json2 = WorkflowLogSerDes.toJSON(workflowLog);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WorkflowLog workflowLog = randomWorkflowLog();

		workflowLog.setCommentLog(regex);
		workflowLog.setPreviousState(regex);
		workflowLog.setState(regex);
		workflowLog.setType(regex);

		String json = WorkflowLogSerDes.toJSON(workflowLog);

		Assert.assertFalse(json.contains(regex));

		workflowLog = WorkflowLogSerDes.toDTO(json);

		Assert.assertEquals(regex, workflowLog.getCommentLog());
		Assert.assertEquals(regex, workflowLog.getPreviousState());
		Assert.assertEquals(regex, workflowLog.getState());
		Assert.assertEquals(regex, workflowLog.getType());
	}

	@Test
	public void testGetWorkflowLog() throws Exception {
		WorkflowLog postWorkflowLog = testGetWorkflowLog_addWorkflowLog();

		WorkflowLog getWorkflowLog = workflowLogResource.getWorkflowLog(
			postWorkflowLog.getId());

		assertEquals(postWorkflowLog, getWorkflowLog);
		assertValid(getWorkflowLog);
	}

	protected WorkflowLog testGetWorkflowLog_addWorkflowLog() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowLog() throws Exception {
		WorkflowLog workflowLog = testGraphQLWorkflowLog_addWorkflowLog();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"workflowLog",
				new HashMap<String, Object>() {
					{
						put("workflowLogId", workflowLog.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				workflowLog, dataJSONObject.getJSONObject("workflowLog")));
	}

	@Test
	public void testGetWorkflowTaskWorkflowLogsPage() throws Exception {
		Page<WorkflowLog> page =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowTaskId =
			testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId();
		Long irrelevantWorkflowTaskId =
			testGetWorkflowTaskWorkflowLogsPage_getIrrelevantWorkflowTaskId();

		if ((irrelevantWorkflowTaskId != null)) {
			WorkflowLog irrelevantWorkflowLog =
				testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
					irrelevantWorkflowTaskId, randomIrrelevantWorkflowLog());

			page = workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				irrelevantWorkflowTaskId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWorkflowLog),
				(List<WorkflowLog>)page.getItems());
			assertValid(page);
		}

		WorkflowLog workflowLog1 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		WorkflowLog workflowLog2 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		page = workflowLogResource.getWorkflowTaskWorkflowLogsPage(
			workflowTaskId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowLog1, workflowLog2),
			(List<WorkflowLog>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTaskWorkflowLogsPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId();

		WorkflowLog workflowLog1 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		WorkflowLog workflowLog2 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		WorkflowLog workflowLog3 =
			testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
				workflowTaskId, randomWorkflowLog());

		Page<WorkflowLog> page1 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				workflowTaskId, Pagination.of(1, 2));

		List<WorkflowLog> workflowLogs1 = (List<WorkflowLog>)page1.getItems();

		Assert.assertEquals(workflowLogs1.toString(), 2, workflowLogs1.size());

		Page<WorkflowLog> page2 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowLog> workflowLogs2 = (List<WorkflowLog>)page2.getItems();

		Assert.assertEquals(workflowLogs2.toString(), 1, workflowLogs2.size());

		Page<WorkflowLog> page3 =
			workflowLogResource.getWorkflowTaskWorkflowLogsPage(
				workflowTaskId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowLog1, workflowLog2, workflowLog3),
			(List<WorkflowLog>)page3.getItems());
	}

	protected WorkflowLog testGetWorkflowTaskWorkflowLogsPage_addWorkflowLog(
			Long workflowTaskId, WorkflowLog workflowLog)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkflowTaskWorkflowLogsPage_getWorkflowTaskId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowTaskWorkflowLogsPage_getIrrelevantWorkflowTaskId()
		throws Exception {

		return null;
	}

	protected WorkflowLog testGraphQLWorkflowLog_addWorkflowLog()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		WorkflowLog workflowLog1, WorkflowLog workflowLog2) {

		Assert.assertTrue(
			workflowLog1 + " does not equal " + workflowLog2,
			equals(workflowLog1, workflowLog2));
	}

	protected void assertEquals(
		List<WorkflowLog> workflowLogs1, List<WorkflowLog> workflowLogs2) {

		Assert.assertEquals(workflowLogs1.size(), workflowLogs2.size());

		for (int i = 0; i < workflowLogs1.size(); i++) {
			WorkflowLog workflowLog1 = workflowLogs1.get(i);
			WorkflowLog workflowLog2 = workflowLogs2.get(i);

			assertEquals(workflowLog1, workflowLog2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowLog> workflowLogs1, List<WorkflowLog> workflowLogs2) {

		Assert.assertEquals(workflowLogs1.size(), workflowLogs2.size());

		for (WorkflowLog workflowLog1 : workflowLogs1) {
			boolean contains = false;

			for (WorkflowLog workflowLog2 : workflowLogs2) {
				if (equals(workflowLog1, workflowLog2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowLogs2 + " does not contain " + workflowLog1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<WorkflowLog> workflowLogs, JSONArray jsonArray) {

		for (WorkflowLog workflowLog : workflowLogs) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(workflowLog, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + workflowLog, contains);
		}
	}

	protected void assertValid(WorkflowLog workflowLog) {
		boolean valid = true;

		if (workflowLog.getDateCreated() == null) {
			valid = false;
		}

		if (workflowLog.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("auditPerson", additionalAssertFieldName)) {
				if (workflowLog.getAuditPerson() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("commentLog", additionalAssertFieldName)) {
				if (workflowLog.getCommentLog() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("person", additionalAssertFieldName)) {
				if (workflowLog.getPerson() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("previousPerson", additionalAssertFieldName)) {
				if (workflowLog.getPreviousPerson() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("previousState", additionalAssertFieldName)) {
				if (workflowLog.getPreviousState() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("state", additionalAssertFieldName)) {
				if (workflowLog.getState() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taskId", additionalAssertFieldName)) {
				if (workflowLog.getTaskId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (workflowLog.getType() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<WorkflowLog> page) {
		boolean valid = false;

		java.util.Collection<WorkflowLog> workflowLogs = page.getItems();

		int size = workflowLogs.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		WorkflowLog workflowLog1, WorkflowLog workflowLog2) {

		if (workflowLog1 == workflowLog2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("auditPerson", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getAuditPerson(),
						workflowLog2.getAuditPerson())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("commentLog", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getCommentLog(),
						workflowLog2.getCommentLog())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getDateCreated(),
						workflowLog2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getId(), workflowLog2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("person", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getPerson(), workflowLog2.getPerson())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("previousPerson", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getPreviousPerson(),
						workflowLog2.getPreviousPerson())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("previousState", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getPreviousState(),
						workflowLog2.getPreviousState())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("state", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getState(), workflowLog2.getState())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getTaskId(), workflowLog2.getTaskId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowLog1.getType(), workflowLog2.getType())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equalsJSONObject(
		WorkflowLog workflowLog, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("commentLog", fieldName)) {
				if (!Objects.deepEquals(
						workflowLog.getCommentLog(),
						jsonObject.getString("commentLog"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						workflowLog.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("previousState", fieldName)) {
				if (!Objects.deepEquals(
						workflowLog.getPreviousState(),
						jsonObject.getString("previousState"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("state", fieldName)) {
				if (!Objects.deepEquals(
						workflowLog.getState(),
						jsonObject.getString("state"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taskId", fieldName)) {
				if (!Objects.deepEquals(
						workflowLog.getTaskId(),
						jsonObject.getLong("taskId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", fieldName)) {
				if (!Objects.deepEquals(
						workflowLog.getType(), jsonObject.getString("type"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_workflowLogResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowLogResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, WorkflowLog workflowLog) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("auditPerson")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("commentLog")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getCommentLog()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowLog.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(workflowLog.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(workflowLog.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("person")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("previousPerson")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("previousState")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getPreviousState()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("state")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getState()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("taskId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(workflowLog.getType()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
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

	protected WorkflowLog randomIrrelevantWorkflowLog() throws Exception {
		WorkflowLog randomIrrelevantWorkflowLog = randomWorkflowLog();

		return randomIrrelevantWorkflowLog;
	}

	protected WorkflowLog randomPatchWorkflowLog() throws Exception {
		return randomWorkflowLog();
	}

	protected WorkflowLogResource workflowLogResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowLogResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.headless.admin.workflow.resource.v1_0.WorkflowLogResource
			_workflowLogResource;

}