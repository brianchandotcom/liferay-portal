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

import com.liferay.headless.admin.workflow.client.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.client.http.HttpInvoker;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.TransitionResource;
import com.liferay.headless.admin.workflow.client.serdes.v1_0.TransitionSerDes;
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
public abstract class BaseTransitionResourceTestCase {

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

		_transitionResource.setContextCompany(testCompany);

		TransitionResource.Builder builder = TransitionResource.builder();

		transitionResource = builder.locale(
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

		Transition transition1 = randomTransition();

		String json = objectMapper.writeValueAsString(transition1);

		Transition transition2 = TransitionSerDes.toDTO(json);

		Assert.assertTrue(equals(transition1, transition2));
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

		Transition transition = randomTransition();

		String json1 = objectMapper.writeValueAsString(transition);
		String json2 = TransitionSerDes.toJSON(transition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Transition transition = randomTransition();

		transition.setLabel(regex);
		transition.setName(regex);

		String json = TransitionSerDes.toJSON(transition);

		Assert.assertFalse(json.contains(regex));

		transition = TransitionSerDes.toDTO(json);

		Assert.assertEquals(regex, transition.getLabel());
		Assert.assertEquals(regex, transition.getName());
	}

	@Test
	public void testGetWorkflowInstanceNextTransitionsPage() throws Exception {
		Page<Transition> page =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowInstanceId =
			testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId();
		Long irrelevantWorkflowInstanceId =
			testGetWorkflowInstanceNextTransitionsPage_getIrrelevantWorkflowInstanceId();

		if ((irrelevantWorkflowInstanceId != null)) {
			Transition irrelevantTransition =
				testGetWorkflowInstanceNextTransitionsPage_addTransition(
					irrelevantWorkflowInstanceId, randomIrrelevantTransition());

			page = transitionResource.getWorkflowInstanceNextTransitionsPage(
				irrelevantWorkflowInstanceId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTransition),
				(List<Transition>)page.getItems());
			assertValid(page);
		}

		Transition transition1 =
			testGetWorkflowInstanceNextTransitionsPage_addTransition(
				workflowInstanceId, randomTransition());

		Transition transition2 =
			testGetWorkflowInstanceNextTransitionsPage_addTransition(
				workflowInstanceId, randomTransition());

		page = transitionResource.getWorkflowInstanceNextTransitionsPage(
			workflowInstanceId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(transition1, transition2),
			(List<Transition>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowInstanceNextTransitionsPageWithPagination()
		throws Exception {

		Long workflowInstanceId =
			testGetWorkflowInstanceNextTransitionsPage_getWorkflowInstanceId();

		Transition transition1 =
			testGetWorkflowInstanceNextTransitionsPage_addTransition(
				workflowInstanceId, randomTransition());

		Transition transition2 =
			testGetWorkflowInstanceNextTransitionsPage_addTransition(
				workflowInstanceId, randomTransition());

		Transition transition3 =
			testGetWorkflowInstanceNextTransitionsPage_addTransition(
				workflowInstanceId, randomTransition());

		Page<Transition> page1 =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				workflowInstanceId, Pagination.of(1, 2));

		List<Transition> transitions1 = (List<Transition>)page1.getItems();

		Assert.assertEquals(transitions1.toString(), 2, transitions1.size());

		Page<Transition> page2 =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				workflowInstanceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Transition> transitions2 = (List<Transition>)page2.getItems();

		Assert.assertEquals(transitions2.toString(), 1, transitions2.size());

		Page<Transition> page3 =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				workflowInstanceId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(transition1, transition2, transition3),
			(List<Transition>)page3.getItems());
	}

	protected Transition
			testGetWorkflowInstanceNextTransitionsPage_addTransition(
				Long workflowInstanceId, Transition transition)
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
		Page<Transition> page =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long workflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId();
		Long irrelevantWorkflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getIrrelevantWorkflowTaskId();

		if ((irrelevantWorkflowTaskId != null)) {
			Transition irrelevantTransition =
				testGetWorkflowTaskNextTransitionsPage_addTransition(
					irrelevantWorkflowTaskId, randomIrrelevantTransition());

			page = transitionResource.getWorkflowTaskNextTransitionsPage(
				irrelevantWorkflowTaskId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTransition),
				(List<Transition>)page.getItems());
			assertValid(page);
		}

		Transition transition1 =
			testGetWorkflowTaskNextTransitionsPage_addTransition(
				workflowTaskId, randomTransition());

		Transition transition2 =
			testGetWorkflowTaskNextTransitionsPage_addTransition(
				workflowTaskId, randomTransition());

		page = transitionResource.getWorkflowTaskNextTransitionsPage(
			workflowTaskId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(transition1, transition2),
			(List<Transition>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWorkflowTaskNextTransitionsPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId();

		Transition transition1 =
			testGetWorkflowTaskNextTransitionsPage_addTransition(
				workflowTaskId, randomTransition());

		Transition transition2 =
			testGetWorkflowTaskNextTransitionsPage_addTransition(
				workflowTaskId, randomTransition());

		Transition transition3 =
			testGetWorkflowTaskNextTransitionsPage_addTransition(
				workflowTaskId, randomTransition());

		Page<Transition> page1 =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(1, 2));

		List<Transition> transitions1 = (List<Transition>)page1.getItems();

		Assert.assertEquals(transitions1.toString(), 2, transitions1.size());

		Page<Transition> page2 =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Transition> transitions2 = (List<Transition>)page2.getItems();

		Assert.assertEquals(transitions2.toString(), 1, transitions2.size());

		Page<Transition> page3 =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(transition1, transition2, transition3),
			(List<Transition>)page3.getItems());
	}

	protected Transition testGetWorkflowTaskNextTransitionsPage_addTransition(
			Long workflowTaskId, Transition transition)
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
		Transition transition1, Transition transition2) {

		Assert.assertTrue(
			transition1 + " does not equal " + transition2,
			equals(transition1, transition2));
	}

	protected void assertEquals(
		List<Transition> transitions1, List<Transition> transitions2) {

		Assert.assertEquals(transitions1.size(), transitions2.size());

		for (int i = 0; i < transitions1.size(); i++) {
			Transition transition1 = transitions1.get(i);
			Transition transition2 = transitions2.get(i);

			assertEquals(transition1, transition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Transition> transitions1, List<Transition> transitions2) {

		Assert.assertEquals(transitions1.size(), transitions2.size());

		for (Transition transition1 : transitions1) {
			boolean contains = false;

			for (Transition transition2 : transitions2) {
				if (equals(transition1, transition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				transitions2 + " does not contain " + transition1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Transition> transitions, JSONArray jsonArray) {

		for (Transition transition : transitions) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(transition, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + transition, contains);
		}
	}

	protected void assertValid(Transition transition) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("label", additionalAssertFieldName)) {
				if (transition.getLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (transition.getName() == null) {
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

	protected void assertValid(Page<Transition> page) {
		boolean valid = false;

		java.util.Collection<Transition> transitions = page.getItems();

		int size = transitions.size();

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

	protected boolean equals(Transition transition1, Transition transition2) {
		if (transition1 == transition2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("label", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						transition1.getLabel(), transition2.getLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						transition1.getName(), transition2.getName())) {

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
		Transition transition, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("label", fieldName)) {
				if (!Objects.deepEquals(
						transition.getLabel(), jsonObject.getString("label"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						transition.getName(), jsonObject.getString("name"))) {

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

		if (!(_transitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_transitionResource;

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
		EntityField entityField, String operator, Transition transition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("label")) {
			sb.append("'");
			sb.append(String.valueOf(transition.getLabel()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(transition.getName()));
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

	protected Transition randomTransition() throws Exception {
		return new Transition() {
			{
				label = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Transition randomIrrelevantTransition() throws Exception {
		Transition randomIrrelevantTransition = randomTransition();

		return randomIrrelevantTransition;
	}

	protected Transition randomPatchTransition() throws Exception {
		return randomTransition();
	}

	protected TransitionResource transitionResource;
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
		BaseTransitionResourceTestCase.class);

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
	private com.liferay.headless.admin.workflow.resource.v1_0.TransitionResource
		_transitionResource;

}