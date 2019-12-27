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

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskAssignableUsersBag;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowTaskAssignableUsersBagResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.WorkflowTaskAssignableUsersBagSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
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
public abstract class BaseWorkflowTaskAssignableUsersBagResourceTestCase {

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

		_workflowTaskAssignableUsersBagResource.setContextCompany(testCompany);

		WorkflowTaskAssignableUsersBagResource.Builder builder =
			WorkflowTaskAssignableUsersBagResource.builder();

		workflowTaskAssignableUsersBagResource = builder.locale(
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

		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag1 =
			randomWorkflowTaskAssignableUsersBag();

		String json = objectMapper.writeValueAsString(
			workflowTaskAssignableUsersBag1);

		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag2 =
			WorkflowTaskAssignableUsersBagSerDes.toDTO(json);

		Assert.assertTrue(
			equals(
				workflowTaskAssignableUsersBag1,
				workflowTaskAssignableUsersBag2));
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

		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag =
			randomWorkflowTaskAssignableUsersBag();

		String json1 = objectMapper.writeValueAsString(
			workflowTaskAssignableUsersBag);
		String json2 = WorkflowTaskAssignableUsersBagSerDes.toJSON(
			workflowTaskAssignableUsersBag);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag =
			randomWorkflowTaskAssignableUsersBag();

		String json = WorkflowTaskAssignableUsersBagSerDes.toJSON(
			workflowTaskAssignableUsersBag);

		Assert.assertFalse(json.contains(regex));

		workflowTaskAssignableUsersBag =
			WorkflowTaskAssignableUsersBagSerDes.toDTO(json);
	}

	@Test
	public void testGetWorkflowTaskAssignableUsersBag() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetWorkflowTaskAssignableUsersBag()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag1,
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag2) {

		Assert.assertTrue(
			workflowTaskAssignableUsersBag1 + " does not equal " +
				workflowTaskAssignableUsersBag2,
			equals(
				workflowTaskAssignableUsersBag1,
				workflowTaskAssignableUsersBag2));
	}

	protected void assertEquals(
		List<WorkflowTaskAssignableUsersBag> workflowTaskAssignableUsersBags1,
		List<WorkflowTaskAssignableUsersBag> workflowTaskAssignableUsersBags2) {

		Assert.assertEquals(
			workflowTaskAssignableUsersBags1.size(),
			workflowTaskAssignableUsersBags2.size());

		for (int i = 0; i < workflowTaskAssignableUsersBags1.size(); i++) {
			WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag1 =
				workflowTaskAssignableUsersBags1.get(i);
			WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag2 =
				workflowTaskAssignableUsersBags2.get(i);

			assertEquals(
				workflowTaskAssignableUsersBag1,
				workflowTaskAssignableUsersBag2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WorkflowTaskAssignableUsersBag> workflowTaskAssignableUsersBags1,
		List<WorkflowTaskAssignableUsersBag> workflowTaskAssignableUsersBags2) {

		Assert.assertEquals(
			workflowTaskAssignableUsersBags1.size(),
			workflowTaskAssignableUsersBags2.size());

		for (WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag1 :
				workflowTaskAssignableUsersBags1) {

			boolean contains = false;

			for (WorkflowTaskAssignableUsersBag
					workflowTaskAssignableUsersBag2 :
						workflowTaskAssignableUsersBags2) {

				if (equals(
						workflowTaskAssignableUsersBag1,
						workflowTaskAssignableUsersBag2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				workflowTaskAssignableUsersBags2 + " does not contain " +
					workflowTaskAssignableUsersBag1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<WorkflowTaskAssignableUsersBag> workflowTaskAssignableUsersBags,
		JSONArray jsonArray) {

		for (WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag :
				workflowTaskAssignableUsersBags) {

			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(
						workflowTaskAssignableUsersBag, (JSONObject)object)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " +
					workflowTaskAssignableUsersBag,
				contains);
		}
	}

	protected void assertValid(
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag) {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"workflowTaskAssignableUsers", additionalAssertFieldName)) {

				if (workflowTaskAssignableUsersBag.
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

	protected void assertValid(Page<WorkflowTaskAssignableUsersBag> page) {
		boolean valid = false;

		java.util.Collection<WorkflowTaskAssignableUsersBag>
			workflowTaskAssignableUsersBags = page.getItems();

		int size = workflowTaskAssignableUsersBags.size();

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
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag1,
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag2) {

		if (workflowTaskAssignableUsersBag1 ==
				workflowTaskAssignableUsersBag2) {

			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"workflowTaskAssignableUsers", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						workflowTaskAssignableUsersBag1.
							getWorkflowTaskAssignableUsers(),
						workflowTaskAssignableUsersBag2.
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

	protected boolean equalsJSONObject(
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag,
		JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_workflowTaskAssignableUsersBagResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_workflowTaskAssignableUsersBagResource;

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
		WorkflowTaskAssignableUsersBag workflowTaskAssignableUsersBag) {

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

	protected WorkflowTaskAssignableUsersBag
			randomWorkflowTaskAssignableUsersBag()
		throws Exception {

		return new WorkflowTaskAssignableUsersBag() {
			{
			}
		};
	}

	protected WorkflowTaskAssignableUsersBag
			randomIrrelevantWorkflowTaskAssignableUsersBag()
		throws Exception {

		WorkflowTaskAssignableUsersBag
			randomIrrelevantWorkflowTaskAssignableUsersBag =
				randomWorkflowTaskAssignableUsersBag();

		return randomIrrelevantWorkflowTaskAssignableUsersBag;
	}

	protected WorkflowTaskAssignableUsersBag
			randomPatchWorkflowTaskAssignableUsersBag()
		throws Exception {

		return randomWorkflowTaskAssignableUsersBag();
	}

	protected WorkflowTaskAssignableUsersBagResource
		workflowTaskAssignableUsersBagResource;
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
		BaseWorkflowTaskAssignableUsersBagResourceTestCase.class);

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
		WorkflowTaskAssignableUsersBagResource
			_workflowTaskAssignableUsersBagResource;

}