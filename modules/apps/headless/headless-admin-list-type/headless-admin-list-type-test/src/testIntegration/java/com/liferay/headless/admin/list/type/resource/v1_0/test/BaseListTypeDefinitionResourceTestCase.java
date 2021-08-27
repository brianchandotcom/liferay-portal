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

package com.liferay.headless.admin.list.type.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.client.http.HttpInvoker;
import com.liferay.headless.admin.list.type.client.pagination.Page;
import com.liferay.headless.admin.list.type.client.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.admin.list.type.client.serdes.v1_0.ListTypeDefinitionSerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public abstract class BaseListTypeDefinitionResourceTestCase {

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

		_listTypeDefinitionResource.setContextCompany(testCompany);

		ListTypeDefinitionResource.Builder builder =
			ListTypeDefinitionResource.builder();

		listTypeDefinitionResource = builder.authentication(
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

		ListTypeDefinition listTypeDefinition1 = randomListTypeDefinition();

		String json = objectMapper.writeValueAsString(listTypeDefinition1);

		ListTypeDefinition listTypeDefinition2 = ListTypeDefinitionSerDes.toDTO(
			json);

		Assert.assertTrue(equals(listTypeDefinition1, listTypeDefinition2));
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

		ListTypeDefinition listTypeDefinition = randomListTypeDefinition();

		String json1 = objectMapper.writeValueAsString(listTypeDefinition);
		String json2 = ListTypeDefinitionSerDes.toJSON(listTypeDefinition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ListTypeDefinition listTypeDefinition = randomListTypeDefinition();

		listTypeDefinition.setName(regex);

		String json = ListTypeDefinitionSerDes.toJSON(listTypeDefinition);

		Assert.assertFalse(json.contains(regex));

		listTypeDefinition = ListTypeDefinitionSerDes.toDTO(json);

		Assert.assertEquals(regex, listTypeDefinition.getName());
	}

	@Test
	public void testGetListTypeDefinitionsPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetListTypeDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"listTypeDefinitions",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject listTypeDefinitionsJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(graphQLField), "JSONObject/data",
				"JSONObject/listTypeDefinitions");

		Assert.assertEquals(0, listTypeDefinitionsJSONObject.get("totalCount"));

		ListTypeDefinition listTypeDefinition1 =
			testGraphQLListTypeDefinition_addListTypeDefinition();
		ListTypeDefinition listTypeDefinition2 =
			testGraphQLListTypeDefinition_addListTypeDefinition();

		listTypeDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/listTypeDefinitions");

		Assert.assertEquals(2, listTypeDefinitionsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(listTypeDefinition1, listTypeDefinition2),
			Arrays.asList(
				ListTypeDefinitionSerDes.toDTOs(
					listTypeDefinitionsJSONObject.getString("items"))));
	}

	@Test
	public void testPostListTypeDefinition() throws Exception {
		ListTypeDefinition randomListTypeDefinition =
			randomListTypeDefinition();

		ListTypeDefinition postListTypeDefinition =
			testPostListTypeDefinition_addListTypeDefinition(
				randomListTypeDefinition);

		assertEquals(randomListTypeDefinition, postListTypeDefinition);
		assertValid(postListTypeDefinition);
	}

	protected ListTypeDefinition
			testPostListTypeDefinition_addListTypeDefinition(
				ListTypeDefinition listTypeDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteListTypeDefinition() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ListTypeDefinition listTypeDefinition =
			testDeleteListTypeDefinition_addListTypeDefinition();

		assertHttpResponseStatusCode(
			204,
			listTypeDefinitionResource.deleteListTypeDefinitionHttpResponse(
				listTypeDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			listTypeDefinitionResource.getListTypeDefinitionHttpResponse(
				listTypeDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			listTypeDefinitionResource.getListTypeDefinitionHttpResponse(0L));
	}

	protected ListTypeDefinition
			testDeleteListTypeDefinition_addListTypeDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteListTypeDefinition() throws Exception {
		ListTypeDefinition listTypeDefinition =
			testGraphQLListTypeDefinition_addListTypeDefinition();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteListTypeDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"listTypeDefinitionId",
									listTypeDefinition.getId());
							}
						})),
				"JSONObject/data", "Object/deleteListTypeDefinition"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"listTypeDefinition",
					new HashMap<String, Object>() {
						{
							put(
								"listTypeDefinitionId",
								listTypeDefinition.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetListTypeDefinition() throws Exception {
		ListTypeDefinition postListTypeDefinition =
			testGetListTypeDefinition_addListTypeDefinition();

		ListTypeDefinition getListTypeDefinition =
			listTypeDefinitionResource.getListTypeDefinition(
				postListTypeDefinition.getId());

		assertEquals(postListTypeDefinition, getListTypeDefinition);
		assertValid(getListTypeDefinition);
	}

	protected ListTypeDefinition
			testGetListTypeDefinition_addListTypeDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetListTypeDefinition() throws Exception {
		ListTypeDefinition listTypeDefinition =
			testGraphQLListTypeDefinition_addListTypeDefinition();

		Assert.assertTrue(
			equals(
				listTypeDefinition,
				ListTypeDefinitionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"listTypeDefinition",
								new HashMap<String, Object>() {
									{
										put(
											"listTypeDefinitionId",
											listTypeDefinition.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/listTypeDefinition"))));
	}

	@Test
	public void testGraphQLGetListTypeDefinitionNotFound() throws Exception {
		Long irrelevantListTypeDefinitionId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"listTypeDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"listTypeDefinitionId",
									irrelevantListTypeDefinitionId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutListTypeDefinition() throws Exception {
		ListTypeDefinition postListTypeDefinition =
			testPutListTypeDefinition_addListTypeDefinition();

		ListTypeDefinition randomListTypeDefinition =
			randomListTypeDefinition();

		ListTypeDefinition putListTypeDefinition =
			listTypeDefinitionResource.putListTypeDefinition(
				postListTypeDefinition.getId(), randomListTypeDefinition);

		assertEquals(randomListTypeDefinition, putListTypeDefinition);
		assertValid(putListTypeDefinition);

		ListTypeDefinition getListTypeDefinition =
			listTypeDefinitionResource.getListTypeDefinition(
				putListTypeDefinition.getId());

		assertEquals(randomListTypeDefinition, getListTypeDefinition);
		assertValid(getListTypeDefinition);
	}

	protected ListTypeDefinition
			testPutListTypeDefinition_addListTypeDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ListTypeDefinition
			testGraphQLListTypeDefinition_addListTypeDefinition()
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
		ListTypeDefinition listTypeDefinition1,
		ListTypeDefinition listTypeDefinition2) {

		Assert.assertTrue(
			listTypeDefinition1 + " does not equal " + listTypeDefinition2,
			equals(listTypeDefinition1, listTypeDefinition2));
	}

	protected void assertEquals(
		List<ListTypeDefinition> listTypeDefinitions1,
		List<ListTypeDefinition> listTypeDefinitions2) {

		Assert.assertEquals(
			listTypeDefinitions1.size(), listTypeDefinitions2.size());

		for (int i = 0; i < listTypeDefinitions1.size(); i++) {
			ListTypeDefinition listTypeDefinition1 = listTypeDefinitions1.get(
				i);
			ListTypeDefinition listTypeDefinition2 = listTypeDefinitions2.get(
				i);

			assertEquals(listTypeDefinition1, listTypeDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ListTypeDefinition> listTypeDefinitions1,
		List<ListTypeDefinition> listTypeDefinitions2) {

		Assert.assertEquals(
			listTypeDefinitions1.size(), listTypeDefinitions2.size());

		for (ListTypeDefinition listTypeDefinition1 : listTypeDefinitions1) {
			boolean contains = false;

			for (ListTypeDefinition listTypeDefinition2 :
					listTypeDefinitions2) {

				if (equals(listTypeDefinition1, listTypeDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				listTypeDefinitions2 + " does not contain " +
					listTypeDefinition1,
				contains);
		}
	}

	protected void assertValid(ListTypeDefinition listTypeDefinition)
		throws Exception {

		boolean valid = true;

		if (listTypeDefinition.getDateCreated() == null) {
			valid = false;
		}

		if (listTypeDefinition.getDateModified() == null) {
			valid = false;
		}

		if (listTypeDefinition.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("listTypeEntries", additionalAssertFieldName)) {
				if (listTypeDefinition.getListTypeEntries() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (listTypeDefinition.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (listTypeDefinition.getName_i18n() == null) {
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

	protected void assertValid(Page<ListTypeDefinition> page) {
		boolean valid = false;

		java.util.Collection<ListTypeDefinition> listTypeDefinitions =
			page.getItems();

		int size = listTypeDefinitions.size();

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
				getDeclaredFields(
					com.liferay.headless.admin.list.type.dto.v1_0.
						ListTypeDefinition.class)) {

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
					getDeclaredFields(clazz));

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
		ListTypeDefinition listTypeDefinition1,
		ListTypeDefinition listTypeDefinition2) {

		if (listTypeDefinition1 == listTypeDefinition2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeDefinition1.getDateCreated(),
						listTypeDefinition2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeDefinition1.getDateModified(),
						listTypeDefinition2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeDefinition1.getId(),
						listTypeDefinition2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("listTypeEntries", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeDefinition1.getListTypeEntries(),
						listTypeDefinition2.getListTypeEntries())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						listTypeDefinition1.getName(),
						listTypeDefinition2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)listTypeDefinition1.getName_i18n(),
						(Map)listTypeDefinition2.getName_i18n())) {

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

			return true;
		}

		return false;
	}

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_listTypeDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_listTypeDefinitionResource;

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
		ListTypeDefinition listTypeDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeDefinition.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeDefinition.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(listTypeDefinition.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeDefinition.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							listTypeDefinition.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(listTypeDefinition.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("listTypeEntries")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(listTypeDefinition.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name_i18n")) {
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

	protected ListTypeDefinition randomListTypeDefinition() throws Exception {
		return new ListTypeDefinition() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected ListTypeDefinition randomIrrelevantListTypeDefinition()
		throws Exception {

		ListTypeDefinition randomIrrelevantListTypeDefinition =
			randomListTypeDefinition();

		return randomIrrelevantListTypeDefinition;
	}

	protected ListTypeDefinition randomPatchListTypeDefinition()
		throws Exception {

		return randomListTypeDefinition();
	}

	protected ListTypeDefinitionResource listTypeDefinitionResource;
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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseListTypeDefinitionResourceTestCase.class);

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
	private com.liferay.headless.admin.list.type.resource.v1_0.
		ListTypeDefinitionResource _listTypeDefinitionResource;

}