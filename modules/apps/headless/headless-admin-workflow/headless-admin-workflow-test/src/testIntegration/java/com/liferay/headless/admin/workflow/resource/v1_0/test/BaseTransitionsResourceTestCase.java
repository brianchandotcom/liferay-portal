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

import com.liferay.headless.admin.workflow.client.dto.v1_0.Transitions;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.TransitionsResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.TransitionsSerDes;
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
public abstract class BaseTransitionsResourceTestCase {

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

		_transitionsResource.setContextCompany(testCompany);

		TransitionsResource.Builder builder = TransitionsResource.builder();

		transitionsResource = builder.locale(
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

		Transitions transitions1 = randomTransitions();

		String json = objectMapper.writeValueAsString(transitions1);

		Transitions transitions2 = TransitionsSerDes.toDTO(json);

		Assert.assertTrue(equals(transitions1, transitions2));
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

		Transitions transitions = randomTransitions();

		String json1 = objectMapper.writeValueAsString(transitions);
		String json2 = TransitionsSerDes.toJSON(transitions);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Transitions transitions = randomTransitions();

		transitions.setName(regex);

		String json = TransitionsSerDes.toJSON(transitions);

		Assert.assertFalse(json.contains(regex));

		transitions = TransitionsSerDes.toDTO(json);

		Assert.assertEquals(regex, transitions.getName());
	}

	@Test
	public void testGetWorkflowInstanceNextTransitionsPage() throws Exception {
		Page<Transitions> page =
			transitionsResource.getWorkflowInstanceNextTransitionsPage(
				testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowInstanceId =
			testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId();
		Long irrelevantWorkflowInstanceId =
			testGetWorkflowInstanceNextTransitionsPage_getIrrelevantWorkflowInstanceId();

		if ((irrelevantWorkflowInstanceId != null)) {
			Transitions irrelevantTransitions =
				testGetWorkflowInstanceNextTransitionsPage_addTransitions(
					irrelevantWorkflowInstanceId,
					randomIrrelevantTransitions());

			page = transitionsResource.getWorkflowInstanceNextTransitionsPage(
				irrelevantWorkflowInstanceId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTransitions),
				(List<Transitions>)page.getItems());
			assertValid(page);
		}

		Transitions transitions1 =
			testGetWorkflowInstanceNextTransitionsPage_addTransitions(
				workflowInstanceId, randomTransitions());

		Transitions transitions2 =
			testGetWorkflowInstanceNextTransitionsPage_addTransitions(
				workflowInstanceId, randomTransitions());

		page = transitionsResource.getWorkflowInstanceNextTransitionsPage(
			workflowInstanceId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(transitions1, transitions2),
			(List<Transitions>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowInstanceNextTransitionsPageWithPagination()
		throws Exception {

		Long workflowInstanceId =
			testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId();

		Transitions transitions1 =
			testGetWorkflowInstanceNextTransitionsPage_addTransitions(
				workflowInstanceId, randomTransitions());

		Transitions transitions2 =
			testGetWorkflowInstanceNextTransitionsPage_addTransitions(
				workflowInstanceId, randomTransitions());

		Transitions transitions3 =
			testGetWorkflowInstanceNextTransitionsPage_addTransitions(
				workflowInstanceId, randomTransitions());

		Page<Transitions> page1 =
			transitionsResource.getWorkflowInstanceNextTransitionsPage(
				workflowInstanceId, Pagination.of(1, 2));

		List<Transitions> transitionses1 = (List<Transitions>)page1.getItems();

		Assert.assertEquals(
			transitionses1.toString(), 2, transitionses1.size());

		Page<Transitions> page2 =
			transitionsResource.getWorkflowInstanceNextTransitionsPage(
				workflowInstanceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Transitions> transitionses2 = (List<Transitions>)page2.getItems();

		Assert.assertEquals(
			transitionses2.toString(), 1, transitionses2.size());

		Page<Transitions> page3 =
			transitionsResource.getWorkflowInstanceNextTransitionsPage(
				workflowInstanceId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(transitions1, transitions2, transitions3),
			(List<Transitions>)page3.getItems());
	}

	protected Transitions
			testGetWorkflowInstanceNextTransitionsPage_addTransitions(
				Long workflowInstanceId, Transitions transitions)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowInstanceNextTransitionsPage_getIrrelevantWorkflowInstanceId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetWorkflowTaskNextTransitionsPage() throws Exception {
		Page<Transitions> page =
			transitionsResource.getWorkflowTaskNextTransitionsPage(
				testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId();
		Long irrelevantWorkflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getIrrelevantWorkflowTaskId();

		if ((irrelevantWorkflowTaskId != null)) {
			Transitions irrelevantTransitions =
				testGetWorkflowTaskNextTransitionsPage_addTransitions(
					irrelevantWorkflowTaskId, randomIrrelevantTransitions());

			page = transitionsResource.getWorkflowTaskNextTransitionsPage(
				irrelevantWorkflowTaskId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTransitions),
				(List<Transitions>)page.getItems());
			assertValid(page);
		}

		Transitions transitions1 =
			testGetWorkflowTaskNextTransitionsPage_addTransitions(
				workflowTaskId, randomTransitions());

		Transitions transitions2 =
			testGetWorkflowTaskNextTransitionsPage_addTransitions(
				workflowTaskId, randomTransitions());

		page = transitionsResource.getWorkflowTaskNextTransitionsPage(
			workflowTaskId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(transitions1, transitions2),
			(List<Transitions>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTaskNextTransitionsPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId();

		Transitions transitions1 =
			testGetWorkflowTaskNextTransitionsPage_addTransitions(
				workflowTaskId, randomTransitions());

		Transitions transitions2 =
			testGetWorkflowTaskNextTransitionsPage_addTransitions(
				workflowTaskId, randomTransitions());

		Transitions transitions3 =
			testGetWorkflowTaskNextTransitionsPage_addTransitions(
				workflowTaskId, randomTransitions());

		Page<Transitions> page1 =
			transitionsResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(1, 2));

		List<Transitions> transitionses1 = (List<Transitions>)page1.getItems();

		Assert.assertEquals(
			transitionses1.toString(), 2, transitionses1.size());

		Page<Transitions> page2 =
			transitionsResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Transitions> transitionses2 = (List<Transitions>)page2.getItems();

		Assert.assertEquals(
			transitionses2.toString(), 1, transitionses2.size());

		Page<Transitions> page3 =
			transitionsResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(transitions1, transitions2, transitions3),
			(List<Transitions>)page3.getItems());
	}

	protected Transitions testGetWorkflowTaskNextTransitionsPage_addTransitions(
			Long workflowTaskId, Transitions transitions)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWorkflowTaskNextTransitionsPage_getIrrelevantWorkflowTaskId()
		throws Exception {

		return null;
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		Transitions transitions1, Transitions transitions2) {

		Assert.assertTrue(
			transitions1 + " does not equal " + transitions2,
			equals(transitions1, transitions2));
	}

	protected void assertEquals(
		List<Transitions> transitionses1, List<Transitions> transitionses2) {

		Assert.assertEquals(transitionses1.size(), transitionses2.size());

		for (int i = 0; i < transitionses1.size(); i++) {
			Transitions transitions1 = transitionses1.get(i);
			Transitions transitions2 = transitionses2.get(i);

			assertEquals(transitions1, transitions2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Transitions> transitionses1, List<Transitions> transitionses2) {

		Assert.assertEquals(transitionses1.size(), transitionses2.size());

		for (Transitions transitions1 : transitionses1) {
			boolean contains = false;

			for (Transitions transitions2 : transitionses2) {
				if (equals(transitions1, transitions2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				transitionses2 + " does not contain " + transitions1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Transitions> transitionses, JSONArray jsonArray) {

		for (Transitions transitions : transitionses) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(transitions, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + transitions, contains);
		}
	}

	protected void assertValid(Transitions transitions) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (transitions.getName() == null) {
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

	protected void assertValid(Page<Transitions> page) {
		boolean valid = false;

		java.util.Collection<Transitions> transitionses = page.getItems();

		int size = transitionses.size();

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
		Transitions transitions1, Transitions transitions2) {

		if (transitions1 == transitions2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						transitions1.getName(), transitions2.getName())) {

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
		Transitions transitions, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						transitions.getName(), jsonObject.getString("name"))) {

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

		if (!(_transitionsResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_transitionsResource;

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
		EntityField entityField, String operator, Transitions transitions) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(transitions.getName()));
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

	protected Transitions randomTransitions() throws Exception {
		return new Transitions() {
			{
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Transitions randomIrrelevantTransitions() throws Exception {
		Transitions randomIrrelevantTransitions = randomTransitions();

		return randomIrrelevantTransitions;
	}

	protected Transitions randomPatchTransitions() throws Exception {
		return randomTransitions();
	}

	protected TransitionsResource transitionsResource;
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
		BaseTransitionsResourceTestCase.class);

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
		com.liferay.headless.admin.workflow.resource.v1_0.TransitionsResource
			_transitionsResource;

}