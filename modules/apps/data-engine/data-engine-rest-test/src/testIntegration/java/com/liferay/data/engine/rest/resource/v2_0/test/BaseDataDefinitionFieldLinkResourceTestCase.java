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

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionFieldLink;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionFieldLinkResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataDefinitionFieldLinkSerDes;
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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataDefinitionFieldLinkResourceTestCase {

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

		_dataDefinitionFieldLinkResource.setContextCompany(testCompany);

		DataDefinitionFieldLinkResource.Builder builder =
			DataDefinitionFieldLinkResource.builder();

		dataDefinitionFieldLinkResource = builder.authentication(
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

		DataDefinitionFieldLink dataDefinitionFieldLink1 =
			randomDataDefinitionFieldLink();

		String json = objectMapper.writeValueAsString(dataDefinitionFieldLink1);

		DataDefinitionFieldLink dataDefinitionFieldLink2 =
			DataDefinitionFieldLinkSerDes.toDTO(json);

		Assert.assertTrue(
			equals(dataDefinitionFieldLink1, dataDefinitionFieldLink2));
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

		DataDefinitionFieldLink dataDefinitionFieldLink =
			randomDataDefinitionFieldLink();

		String json1 = objectMapper.writeValueAsString(dataDefinitionFieldLink);
		String json2 = DataDefinitionFieldLinkSerDes.toJSON(
			dataDefinitionFieldLink);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataDefinitionFieldLink dataDefinitionFieldLink =
			randomDataDefinitionFieldLink();

		String json = DataDefinitionFieldLinkSerDes.toJSON(
			dataDefinitionFieldLink);

		Assert.assertFalse(json.contains(regex));

		dataDefinitionFieldLink = DataDefinitionFieldLinkSerDes.toDTO(json);
	}

	@Test
	public void testGetDataDefinitionDataDefinitionFieldLinkPage()
		throws Exception {

		Page<DataDefinitionFieldLink> page =
			dataDefinitionFieldLinkResource.
				getDataDefinitionDataDefinitionFieldLinkPage(
					testGetDataDefinitionDataDefinitionFieldLinkPage_getDataDefinitionId(),
					RandomTestUtil.randomString());

		Assert.assertEquals(0, page.getTotalCount());

		Long dataDefinitionId =
			testGetDataDefinitionDataDefinitionFieldLinkPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataDefinitionFieldLinkPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			DataDefinitionFieldLink irrelevantDataDefinitionFieldLink =
				testGetDataDefinitionDataDefinitionFieldLinkPage_addDataDefinitionFieldLink(
					irrelevantDataDefinitionId,
					randomIrrelevantDataDefinitionFieldLink());

			page =
				dataDefinitionFieldLinkResource.
					getDataDefinitionDataDefinitionFieldLinkPage(
						irrelevantDataDefinitionId, null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataDefinitionFieldLink),
				(List<DataDefinitionFieldLink>)page.getItems());
			assertValid(page);
		}

		DataDefinitionFieldLink dataDefinitionFieldLink1 =
			testGetDataDefinitionDataDefinitionFieldLinkPage_addDataDefinitionFieldLink(
				dataDefinitionId, randomDataDefinitionFieldLink());

		DataDefinitionFieldLink dataDefinitionFieldLink2 =
			testGetDataDefinitionDataDefinitionFieldLinkPage_addDataDefinitionFieldLink(
				dataDefinitionId, randomDataDefinitionFieldLink());

		page =
			dataDefinitionFieldLinkResource.
				getDataDefinitionDataDefinitionFieldLinkPage(
					dataDefinitionId, null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinitionFieldLink1, dataDefinitionFieldLink2),
			(List<DataDefinitionFieldLink>)page.getItems());
		assertValid(page);
	}

	protected DataDefinitionFieldLink
			testGetDataDefinitionDataDefinitionFieldLinkPage_addDataDefinitionFieldLink(
				Long dataDefinitionId,
				DataDefinitionFieldLink dataDefinitionFieldLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataDefinitionFieldLinkPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataDefinitionFieldLinkPage_getIrrelevantDataDefinitionId()
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
		DataDefinitionFieldLink dataDefinitionFieldLink1,
		DataDefinitionFieldLink dataDefinitionFieldLink2) {

		Assert.assertTrue(
			dataDefinitionFieldLink1 + " does not equal " +
				dataDefinitionFieldLink2,
			equals(dataDefinitionFieldLink1, dataDefinitionFieldLink2));
	}

	protected void assertEquals(
		List<DataDefinitionFieldLink> dataDefinitionFieldLinks1,
		List<DataDefinitionFieldLink> dataDefinitionFieldLinks2) {

		Assert.assertEquals(
			dataDefinitionFieldLinks1.size(), dataDefinitionFieldLinks2.size());

		for (int i = 0; i < dataDefinitionFieldLinks1.size(); i++) {
			DataDefinitionFieldLink dataDefinitionFieldLink1 =
				dataDefinitionFieldLinks1.get(i);
			DataDefinitionFieldLink dataDefinitionFieldLink2 =
				dataDefinitionFieldLinks2.get(i);

			assertEquals(dataDefinitionFieldLink1, dataDefinitionFieldLink2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataDefinitionFieldLink> dataDefinitionFieldLinks1,
		List<DataDefinitionFieldLink> dataDefinitionFieldLinks2) {

		Assert.assertEquals(
			dataDefinitionFieldLinks1.size(), dataDefinitionFieldLinks2.size());

		for (DataDefinitionFieldLink dataDefinitionFieldLink1 :
				dataDefinitionFieldLinks1) {

			boolean contains = false;

			for (DataDefinitionFieldLink dataDefinitionFieldLink2 :
					dataDefinitionFieldLinks2) {

				if (equals(
						dataDefinitionFieldLink1, dataDefinitionFieldLink2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataDefinitionFieldLinks2 + " does not contain " +
					dataDefinitionFieldLink1,
				contains);
		}
	}

	protected void assertValid(DataDefinitionFieldLink dataDefinitionFieldLink)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinition", additionalAssertFieldName)) {
				if (dataDefinitionFieldLink.getDataDefinition() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayouts", additionalAssertFieldName)) {
				if (dataDefinitionFieldLink.getDataLayouts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataListViews", additionalAssertFieldName)) {
				if (dataDefinitionFieldLink.getDataListViews() == null) {
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

	protected void assertValid(Page<DataDefinitionFieldLink> page) {
		boolean valid = false;

		java.util.Collection<DataDefinitionFieldLink> dataDefinitionFieldLinks =
			page.getItems();

		int size = dataDefinitionFieldLinks.size();

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
					com.liferay.data.engine.rest.dto.v2_0.
						DataDefinitionFieldLink.class)) {

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
		DataDefinitionFieldLink dataDefinitionFieldLink1,
		DataDefinitionFieldLink dataDefinitionFieldLink2) {

		if (dataDefinitionFieldLink1 == dataDefinitionFieldLink2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinition", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinitionFieldLink1.getDataDefinition(),
						dataDefinitionFieldLink2.getDataDefinition())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayouts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinitionFieldLink1.getDataLayouts(),
						dataDefinitionFieldLink2.getDataLayouts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataListViews", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinitionFieldLink1.getDataListViews(),
						dataDefinitionFieldLink2.getDataListViews())) {

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

		if (!(_dataDefinitionFieldLinkResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataDefinitionFieldLinkResource;

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
		DataDefinitionFieldLink dataDefinitionFieldLink) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataDefinition")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataLayouts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataListViews")) {
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

	protected DataDefinitionFieldLink randomDataDefinitionFieldLink()
		throws Exception {

		return new DataDefinitionFieldLink() {
			{
			}
		};
	}

	protected DataDefinitionFieldLink randomIrrelevantDataDefinitionFieldLink()
		throws Exception {

		DataDefinitionFieldLink randomIrrelevantDataDefinitionFieldLink =
			randomDataDefinitionFieldLink();

		return randomIrrelevantDataDefinitionFieldLink;
	}

	protected DataDefinitionFieldLink randomPatchDataDefinitionFieldLink()
		throws Exception {

		return randomDataDefinitionFieldLink();
	}

	protected DataDefinitionFieldLinkResource dataDefinitionFieldLinkResource;
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
		BaseDataDefinitionFieldLinkResourceTestCase.class);

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
		com.liferay.data.engine.rest.resource.v2_0.
			DataDefinitionFieldLinkResource _dataDefinitionFieldLinkResource;

}