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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowInstanceResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowInstanceSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
import org.apache.log4j.Level;

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
public abstract class BaseWorkflowInstanceResourceTestCase {

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

		_workflowInstanceResource.setContextCompany(testCompany);

		WorkflowInstanceResource.Builder builder =
			WorkflowInstanceResource.builder();

		workflowInstanceResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		WorkflowInstance workflowInstance1 = randomWorkflowInstance();

		String json = objectMapper.writeValueAsString(workflowInstance1);

		WorkflowInstance workflowInstance2 = WorkflowInstanceSerDes.toDTO(json);

		Assert.assertTrue(equals(workflowInstance1, workflowInstance2));
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

		WorkflowInstance workflowInstance = randomWorkflowInstance();

		String json1 = objectMapper.writeValueAsString(workflowInstance);
		String json2 = WorkflowInstanceSerDes.toJSON(workflowInstance);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WorkflowInstance workflowInstance = randomWorkflowInstance();

		workflowInstance.setWorkflowDefinitionName(regex);
		workflowInstance.setWorkflowDefinitionVersion(regex);

		String json = WorkflowInstanceSerDes.toJSON(workflowInstance);

		Assert.assertFalse(json.contains(regex));

		workflowInstance = WorkflowInstanceSerDes.toDTO(json);

		Assert.assertEquals(
			regex, workflowInstance.getWorkflowDefinitionName());
		Assert.assertEquals(
			regex, workflowInstance.getWorkflowDefinitionVersion());
	}

	@Test
	public void testGetWorkflowInstancesPage() throws Exception {
		Page<WorkflowInstance> page =
			workflowInstanceResource.getWorkflowInstancesPage(
				null, null, null, Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WorkflowInstance workflowInstance1 =
			testGetWorkflowInstancesPage_addWorkflowInstance(
				randomWorkflowInstance());

		WorkflowInstance workflowInstance2 =
			testGetWorkflowInstancesPage_addWorkflowInstance(
				randomWorkflowInstance());

		page = workflowInstanceResource.getWorkflowInstancesPage(
			null, null, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowInstance1, workflowInstance2),
			(List<WorkflowInstance>)page.getItems());
		assertValid(page);

		workflowInstanceResource.deleteWorkflowInstance(
			workflowInstance1.getId());

		workflowInstanceResource.deleteWorkflowInstance(
			workflowInstance2.getId());
	}

	@Test
	public void testGetWorkflowInstancesPageWithPagination() throws Exception {
		WorkflowInstance workflowInstance1 =
			testGetWorkflowInstancesPage_addWorkflowInstance(
				randomWorkflowInstance());

		WorkflowInstance workflowInstance2 =
			testGetWorkflowInstancesPage_addWorkflowInstance(
				randomWorkflowInstance());

		WorkflowInstance workflowInstance3 =
			testGetWorkflowInstancesPage_addWorkflowInstance(
				randomWorkflowInstance());

		Page<WorkflowInstance> page1 =
			workflowInstanceResource.getWorkflowInstancesPage(
				null, null, null, Pagination.of(1, 2));

		List<WorkflowInstance> workflowInstances1 =
			(List<WorkflowInstance>)page1.getItems();

		Assert.assertEquals(
			workflowInstances1.toString(), 2, workflowInstances1.size());

		Page<WorkflowInstance> page2 =
			workflowInstanceResource.getWorkflowInstancesPage(
				null, null, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WorkflowInstance> workflowInstances2 =
			(List<WorkflowInstance>)page2.getItems();

		Assert.assertEquals(
			workflowInstances2.toString(), 1, workflowInstances2.size());

		Page<WorkflowInstance> page3 =
			workflowInstanceResource.getWorkflowInstancesPage(
				null, null, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				workflowInstance1, workflowInstance2, workflowInstance3),
			(List<WorkflowInstance>)page3.getItems());
	}

	protected WorkflowInstance testGetWorkflowInstancesPage_addWorkflowInstance(
			WorkflowInstance workflowInstance)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowInstancesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"workflowInstances",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject workflowInstancesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/workflowInstances");

		Assert.assertEquals(0, workflowInstancesJSONObject.get("totalCount"));

		WorkflowInstance workflowInstance1 =
			testGraphQLWorkflowInstance_addWorkflowInstance();
		WorkflowInstance workflowInstance2 =
			testGraphQLWorkflowInstance_addWorkflowInstance();

		workflowInstancesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/workflowInstances");

		Assert.assertEquals(2, workflowInstancesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(workflowInstance1, workflowInstance2),
			Arrays.asList(
				WorkflowInstanceSerDes.toDTOs(
					workflowInstancesJSONObject.getString("items"))));
	}

	@Test
	public void testPostWorkflowInstanceSubmit() throws Exception {
		WorkflowInstance randomWorkflowInstance = randomWorkflowInstance();

		WorkflowInstance postWorkflowInstance =
			testPostWorkflowInstanceSubmit_addWorkflowInstance(
				randomWorkflowInstance);

		assertEquals(randomWorkflowInstance, postWorkflowInstance);
		assertValid(postWorkflowInstance);
	}

	protected WorkflowInstance
			testPostWorkflowInstanceSubmit_addWorkflowInstance(
				WorkflowInstance workflowInstance)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteWorkflowInstance() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WorkflowInstance workflowInstance =
			testDeleteWorkflowInstance_addWorkflowInstance();

		assertHttpResponseStatusCode(
			204,
			workflowInstanceResource.deleteWorkflowInstanceHttpResponse(
				workflowInstance.getId()));

		assertHttpResponseStatusCode(
			404,
			workflowInstanceResource.getWorkflowInstanceHttpResponse(
				workflowInstance.getId()));

		assertHttpResponseStatusCode(
			404, workflowInstanceResource.getWorkflowInstanceHttpResponse(0L));
	}

	protected WorkflowInstance testDeleteWorkflowInstance_addWorkflowInstance()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteWorkflowInstance() throws Exception {
		WorkflowInstance workflowInstance =
			testGraphQLWorkflowInstance_addWorkflowInstance();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteWorkflowInstance",
						new HashMap<String, Object>() {
							{
								put(
									"workflowInstanceId",
									workflowInstance.getId());
							}
						})),
				"JSONObject/data", "Object/deleteWorkflowInstance"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"workflowInstance",
						new HashMap<String, Object>() {
							{
								put(
									"workflowInstanceId",
									workflowInstance.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetWorkflowInstance() throws Exception {
		WorkflowInstance postWorkflowInstance =
			testGetWorkflowInstance_addWorkflowInstance();

		WorkflowInstance getWorkflowInstance =
			workflowInstanceResource.getWorkflowInstance(
				postWorkflowInstance.getId());

		assertEquals(postWorkflowInstance, getWorkflowInstance);
		assertValid(getWorkflowInstance);
	}

	protected WorkflowInstance testGetWorkflowInstance_addWorkflowInstance()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWorkflowInstance() throws Exception {
		WorkflowInstance workflowInstance =
			testGraphQLWorkflowInstance_addWorkflowInstance();

		Assert.assertTrue(
			equals(
				workflowInstance,
				WorkflowInstanceSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"workflowInstance",
								new HashMap<String, Object>() {
									{
										put(
											"workflowInstanceId",
											workflowInstance.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/workflowInstance"))));
	}

	@Test
	public void testGraphQLGetWorkflowInstanceNotFound() throws Exception {
		Long irrelevantWorkflowInstanceId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"workflowInstance",
						new HashMap<String, Object>() {
							{
								put(
									"workflowInstanceId",
									irrelevantWorkflowInstanceId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPostWorkflowInstanceChangeTransition() throws Exception {
		WorkflowInstance randomWorkflowInstance = randomWorkflowInstance();

		WorkflowInstance postWorkflowInstance =
			testPostWorkflowInstanceChangeTransition_addWorkflowInstance(
				randomWorkflowInstance);

		assertEquals(randomWorkflowInstance, postWorkflowInstance);
		assertValid(postWorkflowInstance);
	}

	protected WorkflowInstance
			testPostWorkflowInstanceChangeTransition_addWorkflowInstance(
				WorkflowInstance workflowInstance)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WorkflowInstance testGraphQLWorkflowInstance_addWorkflowInstance()
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
		WorkflowInstance workflowInstance1,
		WorkflowInstance workflowInstance2) {

		Assert.assertTrue(
			workflowInstance1 + " does not equal " + workflowInstance2,
			equals(workflowInstance1, workflowInstance2));
	}

	protected void assertEquals(
		List<WorkflowInstance> workflowInstances1,
		List<WorkflowInstance> workflowInstances2) {

		Assert.assertEquals(
			workflowInstances1.size(), workflowInstances2.size());

		for (int i = 0; i < workflowInstances1.size(); i++) {
			WorkflowInstance workflowInstance1 = workflowInstances1.get(i);
			WorkflowInstance workflowInstance2 = workflowInstances2.get(i);

			assertEquals(workflowInstance1, workflowInstance2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowInstance> workflowInstances1,
		List<WorkflowInstance> workflowInstances2) {

		Assert.assertEquals(
			workflowInstances1.size(), workflowInstances2.size());

		for (WorkflowInstance workflowInstance1 : workflowInstances1) {
			boolean contains = false;

			for (WorkflowInstance workflowInstance2 : workflowInstances2) {
				if (equals(workflowInstance1, workflowInstance2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowInstances2 + " does not contain " + workflowInstance1,
				contains);
		}
	}

	protected void assertValid(WorkflowInstance workflowInstance)
		throws Exception {

		boolean valid = true;

		if (workflowInstance.getDateCreated() == null) {
			valid = false;
		}

		if (workflowInstance.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (workflowInstance.getCompleted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (workflowInstance.getDateCompletion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("objectReviewed", additionalAssertFieldName)) {
				if (workflowInstance.getObjectReviewed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowDefinitionName", additionalAssertFieldName)) {

				if (workflowInstance.getWorkflowDefinitionName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowDefinitionVersion", additionalAssertFieldName)) {

				if (workflowInstance.getWorkflowDefinitionVersion() == null) {
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

	protected void assertValid(Page<WorkflowInstance> page) {
		boolean valid = false;

		java.util.Collection<WorkflowInstance> workflowInstances =
			page.getItems();

		int size = workflowInstances.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.admin.workflow.dto.v1_0.
						WorkflowInstance.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		WorkflowInstance workflowInstance1,
		WorkflowInstance workflowInstance2) {

		if (workflowInstance1 == workflowInstance2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("completed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowInstance1.getCompleted(),
						workflowInstance2.getCompleted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCompletion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowInstance1.getDateCompletion(),
						workflowInstance2.getDateCompletion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowInstance1.getDateCreated(),
						workflowInstance2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowInstance1.getId(), workflowInstance2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("objectReviewed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						workflowInstance1.getObjectReviewed(),
						workflowInstance2.getObjectReviewed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowDefinitionName", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						workflowInstance1.getWorkflowDefinitionName(),
						workflowInstance2.getWorkflowDefinitionName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowDefinitionVersion", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						workflowInstance1.getWorkflowDefinitionVersion(),
						workflowInstance2.getWorkflowDefinitionVersion())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_workflowInstanceResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowInstanceResource;

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
		EntityField entityField, String operator,
		WorkflowInstance workflowInstance) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("completed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCompletion")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowInstance.getDateCompletion(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowInstance.getDateCompletion(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(workflowInstance.getDateCompletion()));
			}

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
							workflowInstance.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							workflowInstance.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(workflowInstance.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectReviewed")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("workflowDefinitionName")) {
			sb.append("'");
			sb.append(
				String.valueOf(workflowInstance.getWorkflowDefinitionName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("workflowDefinitionVersion")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					workflowInstance.getWorkflowDefinitionVersion()));
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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected WorkflowInstance randomWorkflowInstance() throws Exception {
		return new WorkflowInstance() {
			{
				completed = RandomTestUtil.randomBoolean();
				dateCompletion = RandomTestUtil.nextDate();
				dateCreated = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				workflowDefinitionName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				workflowDefinitionVersion = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected WorkflowInstance randomIrrelevantWorkflowInstance()
		throws Exception {

		WorkflowInstance randomIrrelevantWorkflowInstance =
			randomWorkflowInstance();

		return randomIrrelevantWorkflowInstance;
	}

	protected WorkflowInstance randomPatchWorkflowInstance() throws Exception {
		return randomWorkflowInstance();
	}

	protected WorkflowInstanceResource workflowInstanceResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowInstanceResourceTestCase.class);

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
		com.liferay.headless.admin.workflow.resource.v1_0.
			WorkflowInstanceResource _workflowInstanceResource;

}