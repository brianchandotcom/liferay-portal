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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUsers;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowTaskAssignableUsersResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskAssignableUsersSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
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
public abstract class BaseWorkflowTaskAssignableUsersResourceTestCase {

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

		_workflowTaskAssignableUsersResource.setContextCompany(testCompany);

		WorkflowTaskAssignableUsersResource.Builder builder =
			WorkflowTaskAssignableUsersResource.builder();

		workflowTaskAssignableUsersResource = builder.authentication(
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

		WorkflowTaskAssignableUsers workflowTaskAssignableUsers1 =
			randomWorkflowTaskAssignableUsers();

		String json = objectMapper.writeValueAsString(
			workflowTaskAssignableUsers1);

		WorkflowTaskAssignableUsers workflowTaskAssignableUsers2 =
			WorkflowTaskAssignableUsersSerDes.toDTO(json);

		Assert.assertTrue(
			equals(workflowTaskAssignableUsers1, workflowTaskAssignableUsers2));
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

		WorkflowTaskAssignableUsers workflowTaskAssignableUsers =
			randomWorkflowTaskAssignableUsers();

		String json1 = objectMapper.writeValueAsString(
			workflowTaskAssignableUsers);
		String json2 = WorkflowTaskAssignableUsersSerDes.toJSON(
			workflowTaskAssignableUsers);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WorkflowTaskAssignableUsers workflowTaskAssignableUsers =
			randomWorkflowTaskAssignableUsers();

		String json = WorkflowTaskAssignableUsersSerDes.toJSON(
			workflowTaskAssignableUsers);

		Assert.assertFalse(json.contains(regex));

		workflowTaskAssignableUsers = WorkflowTaskAssignableUsersSerDes.toDTO(
			json);
	}

	@Test
	public void testPostWorkflowTaskAssignableUser() throws Exception {
		WorkflowTaskAssignableUsers randomWorkflowTaskAssignableUsers =
			randomWorkflowTaskAssignableUsers();

		WorkflowTaskAssignableUsers postWorkflowTaskAssignableUsers =
			testPostWorkflowTaskAssignableUser_addWorkflowTaskAssignableUsers(
				randomWorkflowTaskAssignableUsers);

		assertEquals(
			randomWorkflowTaskAssignableUsers, postWorkflowTaskAssignableUsers);
		assertValid(postWorkflowTaskAssignableUsers);
	}

	protected WorkflowTaskAssignableUsers
			testPostWorkflowTaskAssignableUser_addWorkflowTaskAssignableUsers(
				WorkflowTaskAssignableUsers workflowTaskAssignableUsers)
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
		WorkflowTaskAssignableUsers workflowTaskAssignableUsers1,
		WorkflowTaskAssignableUsers workflowTaskAssignableUsers2) {

		Assert.assertTrue(
			workflowTaskAssignableUsers1 + " does not equal " +
				workflowTaskAssignableUsers2,
			equals(workflowTaskAssignableUsers1, workflowTaskAssignableUsers2));
	}

	protected void assertEquals(
		List<WorkflowTaskAssignableUsers> workflowTaskAssignableUserses1,
		List<WorkflowTaskAssignableUsers> workflowTaskAssignableUserses2) {

		Assert.assertEquals(
			workflowTaskAssignableUserses1.size(),
			workflowTaskAssignableUserses2.size());

		for (int i = 0; i < workflowTaskAssignableUserses1.size(); i++) {
			WorkflowTaskAssignableUsers workflowTaskAssignableUsers1 =
				workflowTaskAssignableUserses1.get(i);
			WorkflowTaskAssignableUsers workflowTaskAssignableUsers2 =
				workflowTaskAssignableUserses2.get(i);

			assertEquals(
				workflowTaskAssignableUsers1, workflowTaskAssignableUsers2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowTaskAssignableUsers> workflowTaskAssignableUserses1,
		List<WorkflowTaskAssignableUsers> workflowTaskAssignableUserses2) {

		Assert.assertEquals(
			workflowTaskAssignableUserses1.size(),
			workflowTaskAssignableUserses2.size());

		for (WorkflowTaskAssignableUsers workflowTaskAssignableUsers1 :
				workflowTaskAssignableUserses1) {

			boolean contains = false;

			for (WorkflowTaskAssignableUsers workflowTaskAssignableUsers2 :
					workflowTaskAssignableUserses2) {

				if (equals(
						workflowTaskAssignableUsers1,
						workflowTaskAssignableUsers2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowTaskAssignableUserses2 + " does not contain " +
					workflowTaskAssignableUsers1,
				contains);
		}
	}

	protected void assertValid(
			WorkflowTaskAssignableUsers workflowTaskAssignableUsers)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"workflowTaskAssignableUsers", additionalAssertFieldName)) {

				if (workflowTaskAssignableUsers.
						getWorkflowTaskAssignableUsers() == null) {

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

	protected void assertValid(Page<WorkflowTaskAssignableUsers> page) {
		boolean valid = false;

		java.util.Collection<WorkflowTaskAssignableUsers>
			workflowTaskAssignableUserses = page.getItems();

		int size = workflowTaskAssignableUserses.size();

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
						WorkflowTaskAssignableUsers.class)) {

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
		WorkflowTaskAssignableUsers workflowTaskAssignableUsers1,
		WorkflowTaskAssignableUsers workflowTaskAssignableUsers2) {

		if (workflowTaskAssignableUsers1 == workflowTaskAssignableUsers2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"workflowTaskAssignableUsers", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						workflowTaskAssignableUsers1.
							getWorkflowTaskAssignableUsers(),
						workflowTaskAssignableUsers2.
							getWorkflowTaskAssignableUsers())) {

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

		if (!(_workflowTaskAssignableUsersResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowTaskAssignableUsersResource;

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
		WorkflowTaskAssignableUsers workflowTaskAssignableUsers) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("workflowTaskAssignableUsers")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected WorkflowTaskAssignableUsers randomWorkflowTaskAssignableUsers()
		throws Exception {

		return new WorkflowTaskAssignableUsers() {
			{
			}
		};
	}

	protected WorkflowTaskAssignableUsers
			randomIrrelevantWorkflowTaskAssignableUsers()
		throws Exception {

		WorkflowTaskAssignableUsers
			randomIrrelevantWorkflowTaskAssignableUsers =
				randomWorkflowTaskAssignableUsers();

		return randomIrrelevantWorkflowTaskAssignableUsers;
	}

	protected WorkflowTaskAssignableUsers
			randomPatchWorkflowTaskAssignableUsers()
		throws Exception {

		return randomWorkflowTaskAssignableUsers();
	}

	protected WorkflowTaskAssignableUsersResource
		workflowTaskAssignableUsersResource;
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
		BaseWorkflowTaskAssignableUsersResourceTestCase.class);

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
	private com.liferay.headless.admin.workflow.resource.v1_0.
		WorkflowTaskAssignableUsersResource
			_workflowTaskAssignableUsersResource;

}