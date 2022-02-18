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

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectFieldSettingResource;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectFieldSettingSerDes;
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
public abstract class BaseObjectFieldSettingResourceTestCase {

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

		_objectFieldSettingResource.setContextCompany(testCompany);

		ObjectFieldSettingResource.Builder builder =
			ObjectFieldSettingResource.builder();

		objectFieldSettingResource = builder.authentication(
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

		ObjectFieldSetting objectFieldSetting1 = randomObjectFieldSetting();

		String json = objectMapper.writeValueAsString(objectFieldSetting1);

		ObjectFieldSetting objectFieldSetting2 = ObjectFieldSettingSerDes.toDTO(
			json);

		Assert.assertTrue(equals(objectFieldSetting1, objectFieldSetting2));
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

		ObjectFieldSetting objectFieldSetting = randomObjectFieldSetting();

		String json1 = objectMapper.writeValueAsString(objectFieldSetting);
		String json2 = ObjectFieldSettingSerDes.toJSON(objectFieldSetting);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ObjectFieldSetting objectFieldSetting = randomObjectFieldSetting();

		objectFieldSetting.setName(regex);
		objectFieldSetting.setValue(regex);

		String json = ObjectFieldSettingSerDes.toJSON(objectFieldSetting);

		Assert.assertFalse(json.contains(regex));

		objectFieldSetting = ObjectFieldSettingSerDes.toDTO(json);

		Assert.assertEquals(regex, objectFieldSetting.getName());
		Assert.assertEquals(regex, objectFieldSetting.getValue());
	}

	@Test
	public void testDeleteObjectFieldSetting() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectFieldSetting objectFieldSetting =
			testDeleteObjectFieldSetting_addObjectFieldSetting();

		assertHttpResponseStatusCode(
			204,
			objectFieldSettingResource.deleteObjectFieldSettingHttpResponse(
				objectFieldSetting.getId()));

		assertHttpResponseStatusCode(
			404,
			objectFieldSettingResource.getObjectFieldSettingHttpResponse(
				objectFieldSetting.getId()));

		assertHttpResponseStatusCode(
			404,
			objectFieldSettingResource.getObjectFieldSettingHttpResponse(0L));
	}

	protected ObjectFieldSetting
			testDeleteObjectFieldSetting_addObjectFieldSetting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteObjectFieldSetting() throws Exception {
		ObjectFieldSetting objectFieldSetting =
			testGraphQLObjectFieldSetting_addObjectFieldSetting();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteObjectFieldSetting",
						new HashMap<String, Object>() {
							{
								put(
									"objectFieldSettingId",
									objectFieldSetting.getId());
							}
						})),
				"JSONObject/data", "Object/deleteObjectFieldSetting"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"objectFieldSetting",
					new HashMap<String, Object>() {
						{
							put(
								"objectFieldSettingId",
								objectFieldSetting.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetObjectFieldSetting() throws Exception {
		ObjectFieldSetting postObjectFieldSetting =
			testGetObjectFieldSetting_addObjectFieldSetting();

		ObjectFieldSetting getObjectFieldSetting =
			objectFieldSettingResource.getObjectFieldSetting(
				postObjectFieldSetting.getId());

		assertEquals(postObjectFieldSetting, getObjectFieldSetting);
		assertValid(getObjectFieldSetting);
	}

	protected ObjectFieldSetting
			testGetObjectFieldSetting_addObjectFieldSetting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetObjectFieldSetting() throws Exception {
		ObjectFieldSetting objectFieldSetting =
			testGraphQLObjectFieldSetting_addObjectFieldSetting();

		Assert.assertTrue(
			equals(
				objectFieldSetting,
				ObjectFieldSettingSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"objectFieldSetting",
								new HashMap<String, Object>() {
									{
										put(
											"objectFieldSettingId",
											objectFieldSetting.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/objectFieldSetting"))));
	}

	@Test
	public void testGraphQLGetObjectFieldSettingNotFound() throws Exception {
		Long irrelevantObjectFieldSettingId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"objectFieldSetting",
						new HashMap<String, Object>() {
							{
								put(
									"objectFieldSettingId",
									irrelevantObjectFieldSettingId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutObjectFieldSetting() throws Exception {
		ObjectFieldSetting postObjectFieldSetting =
			testPutObjectFieldSetting_addObjectFieldSetting();

		ObjectFieldSetting randomObjectFieldSetting =
			randomObjectFieldSetting();

		ObjectFieldSetting putObjectFieldSetting =
			objectFieldSettingResource.putObjectFieldSetting(
				postObjectFieldSetting.getId(), randomObjectFieldSetting);

		assertEquals(randomObjectFieldSetting, putObjectFieldSetting);
		assertValid(putObjectFieldSetting);

		ObjectFieldSetting getObjectFieldSetting =
			objectFieldSettingResource.getObjectFieldSetting(
				putObjectFieldSetting.getId());

		assertEquals(randomObjectFieldSetting, getObjectFieldSetting);
		assertValid(getObjectFieldSetting);
	}

	protected ObjectFieldSetting
			testPutObjectFieldSetting_addObjectFieldSetting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetObjectFieldObjectFieldSettingsPage() throws Exception {
		Long objectFieldId =
			testGetObjectFieldObjectFieldSettingsPage_getObjectFieldId();
		Long irrelevantObjectFieldId =
			testGetObjectFieldObjectFieldSettingsPage_getIrrelevantObjectFieldId();

		Page<ObjectFieldSetting> page =
			objectFieldSettingResource.getObjectFieldObjectFieldSettingsPage(
				objectFieldId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantObjectFieldId != null) {
			ObjectFieldSetting irrelevantObjectFieldSetting =
				testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
					irrelevantObjectFieldId,
					randomIrrelevantObjectFieldSetting());

			page =
				objectFieldSettingResource.
					getObjectFieldObjectFieldSettingsPage(
						irrelevantObjectFieldId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantObjectFieldSetting),
				(List<ObjectFieldSetting>)page.getItems());
			assertValid(page);
		}

		ObjectFieldSetting objectFieldSetting1 =
			testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
				objectFieldId, randomObjectFieldSetting());

		ObjectFieldSetting objectFieldSetting2 =
			testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
				objectFieldId, randomObjectFieldSetting());

		page = objectFieldSettingResource.getObjectFieldObjectFieldSettingsPage(
			objectFieldId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(objectFieldSetting1, objectFieldSetting2),
			(List<ObjectFieldSetting>)page.getItems());
		assertValid(page);

		objectFieldSettingResource.deleteObjectFieldSetting(
			objectFieldSetting1.getId());

		objectFieldSettingResource.deleteObjectFieldSetting(
			objectFieldSetting2.getId());
	}

	@Test
	public void testGetObjectFieldObjectFieldSettingsPageWithPagination()
		throws Exception {

		Long objectFieldId =
			testGetObjectFieldObjectFieldSettingsPage_getObjectFieldId();

		ObjectFieldSetting objectFieldSetting1 =
			testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
				objectFieldId, randomObjectFieldSetting());

		ObjectFieldSetting objectFieldSetting2 =
			testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
				objectFieldId, randomObjectFieldSetting());

		ObjectFieldSetting objectFieldSetting3 =
			testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
				objectFieldId, randomObjectFieldSetting());

		Page<ObjectFieldSetting> page1 =
			objectFieldSettingResource.getObjectFieldObjectFieldSettingsPage(
				objectFieldId, Pagination.of(1, 2));

		List<ObjectFieldSetting> objectFieldSettings1 =
			(List<ObjectFieldSetting>)page1.getItems();

		Assert.assertEquals(
			objectFieldSettings1.toString(), 2, objectFieldSettings1.size());

		Page<ObjectFieldSetting> page2 =
			objectFieldSettingResource.getObjectFieldObjectFieldSettingsPage(
				objectFieldId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ObjectFieldSetting> objectFieldSettings2 =
			(List<ObjectFieldSetting>)page2.getItems();

		Assert.assertEquals(
			objectFieldSettings2.toString(), 1, objectFieldSettings2.size());

		Page<ObjectFieldSetting> page3 =
			objectFieldSettingResource.getObjectFieldObjectFieldSettingsPage(
				objectFieldId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				objectFieldSetting1, objectFieldSetting2, objectFieldSetting3),
			(List<ObjectFieldSetting>)page3.getItems());
	}

	protected ObjectFieldSetting
			testGetObjectFieldObjectFieldSettingsPage_addObjectFieldSetting(
				Long objectFieldId, ObjectFieldSetting objectFieldSetting)
		throws Exception {

		return objectFieldSettingResource.postObjectFieldObjectFieldSetting(
			objectFieldId, objectFieldSetting);
	}

	protected Long testGetObjectFieldObjectFieldSettingsPage_getObjectFieldId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetObjectFieldObjectFieldSettingsPage_getIrrelevantObjectFieldId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostObjectFieldObjectFieldSetting() throws Exception {
		ObjectFieldSetting randomObjectFieldSetting =
			randomObjectFieldSetting();

		ObjectFieldSetting postObjectFieldSetting =
			testPostObjectFieldObjectFieldSetting_addObjectFieldSetting(
				randomObjectFieldSetting);

		assertEquals(randomObjectFieldSetting, postObjectFieldSetting);
		assertValid(postObjectFieldSetting);
	}

	protected ObjectFieldSetting
			testPostObjectFieldObjectFieldSetting_addObjectFieldSetting(
				ObjectFieldSetting objectFieldSetting)
		throws Exception {

		return objectFieldSettingResource.postObjectFieldObjectFieldSetting(
			testGetObjectFieldObjectFieldSettingsPage_getObjectFieldId(),
			objectFieldSetting);
	}

	protected ObjectFieldSetting
			testGraphQLObjectFieldSetting_addObjectFieldSetting()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ObjectFieldSetting objectFieldSetting,
		List<ObjectFieldSetting> objectFieldSettings) {

		boolean contains = false;

		for (ObjectFieldSetting item : objectFieldSettings) {
			if (equals(objectFieldSetting, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			objectFieldSettings + " does not contain " + objectFieldSetting,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ObjectFieldSetting objectFieldSetting1,
		ObjectFieldSetting objectFieldSetting2) {

		Assert.assertTrue(
			objectFieldSetting1 + " does not equal " + objectFieldSetting2,
			equals(objectFieldSetting1, objectFieldSetting2));
	}

	protected void assertEquals(
		List<ObjectFieldSetting> objectFieldSettings1,
		List<ObjectFieldSetting> objectFieldSettings2) {

		Assert.assertEquals(
			objectFieldSettings1.size(), objectFieldSettings2.size());

		for (int i = 0; i < objectFieldSettings1.size(); i++) {
			ObjectFieldSetting objectFieldSetting1 = objectFieldSettings1.get(
				i);
			ObjectFieldSetting objectFieldSetting2 = objectFieldSettings2.get(
				i);

			assertEquals(objectFieldSetting1, objectFieldSetting2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ObjectFieldSetting> objectFieldSettings1,
		List<ObjectFieldSetting> objectFieldSettings2) {

		Assert.assertEquals(
			objectFieldSettings1.size(), objectFieldSettings2.size());

		for (ObjectFieldSetting objectFieldSetting1 : objectFieldSettings1) {
			boolean contains = false;

			for (ObjectFieldSetting objectFieldSetting2 :
					objectFieldSettings2) {

				if (equals(objectFieldSetting1, objectFieldSetting2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				objectFieldSettings2 + " does not contain " +
					objectFieldSetting1,
				contains);
		}
	}

	protected void assertValid(ObjectFieldSetting objectFieldSetting)
		throws Exception {

		boolean valid = true;

		if (objectFieldSetting.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (objectFieldSetting.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("objectFieldId", additionalAssertFieldName)) {
				if (objectFieldSetting.getObjectFieldId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("required", additionalAssertFieldName)) {
				if (objectFieldSetting.getRequired() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("value", additionalAssertFieldName)) {
				if (objectFieldSetting.getValue() == null) {
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

	protected void assertValid(Page<ObjectFieldSetting> page) {
		boolean valid = false;

		java.util.Collection<ObjectFieldSetting> objectFieldSettings =
			page.getItems();

		int size = objectFieldSettings.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
		ObjectFieldSetting objectFieldSetting1,
		ObjectFieldSetting objectFieldSetting2) {

		if (objectFieldSetting1 == objectFieldSetting2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectFieldSetting1.getId(),
						objectFieldSetting2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectFieldSetting1.getName(),
						objectFieldSetting2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("objectFieldId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectFieldSetting1.getObjectFieldId(),
						objectFieldSetting2.getObjectFieldId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("required", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectFieldSetting1.getRequired(),
						objectFieldSetting2.getRequired())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("value", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectFieldSetting1.getValue(),
						objectFieldSetting2.getValue())) {

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

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_objectFieldSettingResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_objectFieldSettingResource;

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
		ObjectFieldSetting objectFieldSetting) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(objectFieldSetting.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("objectFieldId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("required")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("value")) {
			sb.append("'");
			sb.append(String.valueOf(objectFieldSetting.getValue()));
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

	protected ObjectFieldSetting randomObjectFieldSetting() throws Exception {
		return new ObjectFieldSetting() {
			{
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				objectFieldId = RandomTestUtil.randomLong();
				required = RandomTestUtil.randomBoolean();
				value = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected ObjectFieldSetting randomIrrelevantObjectFieldSetting()
		throws Exception {

		ObjectFieldSetting randomIrrelevantObjectFieldSetting =
			randomObjectFieldSetting();

		return randomIrrelevantObjectFieldSetting;
	}

	protected ObjectFieldSetting randomPatchObjectFieldSetting()
		throws Exception {

		return randomObjectFieldSetting();
	}

	protected ObjectFieldSettingResource objectFieldSettingResource;
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
		LogFactoryUtil.getLog(BaseObjectFieldSettingResourceTestCase.class);

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
		com.liferay.object.admin.rest.resource.v1_0.ObjectFieldSettingResource
			_objectFieldSettingResource;

}