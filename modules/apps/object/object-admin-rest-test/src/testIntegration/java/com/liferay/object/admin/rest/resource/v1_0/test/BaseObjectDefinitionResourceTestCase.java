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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectDefinitionSerDes;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseObjectDefinitionResourceTestCase {

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

		_objectDefinitionResource.setContextCompany(testCompany);

		ObjectDefinitionResource.Builder builder =
			ObjectDefinitionResource.builder();

		objectDefinitionResource = builder.authentication(
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

		ObjectDefinition objectDefinition1 = randomObjectDefinition();

		String json = objectMapper.writeValueAsString(objectDefinition1);

		ObjectDefinition objectDefinition2 = ObjectDefinitionSerDes.toDTO(json);

		Assert.assertTrue(equals(objectDefinition1, objectDefinition2));
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

		ObjectDefinition objectDefinition = randomObjectDefinition();

		String json1 = objectMapper.writeValueAsString(objectDefinition);
		String json2 = ObjectDefinitionSerDes.toJSON(objectDefinition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ObjectDefinition objectDefinition = randomObjectDefinition();

		objectDefinition.setName(regex);
		objectDefinition.setPanelAppOrder(regex);
		objectDefinition.setPanelCategoryKey(regex);
		objectDefinition.setScope(regex);

		String json = ObjectDefinitionSerDes.toJSON(objectDefinition);

		Assert.assertFalse(json.contains(regex));

		objectDefinition = ObjectDefinitionSerDes.toDTO(json);

		Assert.assertEquals(regex, objectDefinition.getName());
		Assert.assertEquals(regex, objectDefinition.getPanelAppOrder());
		Assert.assertEquals(regex, objectDefinition.getPanelCategoryKey());
		Assert.assertEquals(regex, objectDefinition.getScope());
	}

	@Test
	public void testGetObjectDefinitionsPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetObjectDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"objectDefinitions",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		Assert.assertEquals(0, objectDefinitionsJSONObject.get("totalCount"));

		ObjectDefinition objectDefinition1 =
			testGraphQLObjectDefinition_addObjectDefinition();
		ObjectDefinition objectDefinition2 =
			testGraphQLObjectDefinition_addObjectDefinition();

		objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		Assert.assertEquals(2, objectDefinitionsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(objectDefinition1, objectDefinition2),
			Arrays.asList(
				ObjectDefinitionSerDes.toDTOs(
					objectDefinitionsJSONObject.getString("items"))));
	}

	@Test
	public void testPostObjectDefinition() throws Exception {
		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		ObjectDefinition postObjectDefinition =
			testPostObjectDefinition_addObjectDefinition(
				randomObjectDefinition);

		assertEquals(randomObjectDefinition, postObjectDefinition);
		assertValid(postObjectDefinition);
	}

	protected ObjectDefinition testPostObjectDefinition_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteObjectDefinition() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectDefinition objectDefinition =
			testDeleteObjectDefinition_addObjectDefinition();

		assertHttpResponseStatusCode(
			204,
			objectDefinitionResource.deleteObjectDefinitionHttpResponse(
				objectDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			objectDefinitionResource.getObjectDefinitionHttpResponse(
				objectDefinition.getId()));

		assertHttpResponseStatusCode(
			404, objectDefinitionResource.getObjectDefinitionHttpResponse(0L));
	}

	protected ObjectDefinition testDeleteObjectDefinition_addObjectDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			testGraphQLObjectDefinition_addObjectDefinition();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteObjectDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"objectDefinitionId",
									objectDefinition.getId());
							}
						})),
				"JSONObject/data", "Object/deleteObjectDefinition"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"objectDefinition",
					new HashMap<String, Object>() {
						{
							put("objectDefinitionId", objectDefinition.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetObjectDefinition() throws Exception {
		ObjectDefinition postObjectDefinition =
			testGetObjectDefinition_addObjectDefinition();

		ObjectDefinition getObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				postObjectDefinition.getId());

		assertEquals(postObjectDefinition, getObjectDefinition);
		assertValid(getObjectDefinition);
	}

	protected ObjectDefinition testGetObjectDefinition_addObjectDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition =
			testGraphQLObjectDefinition_addObjectDefinition();

		Assert.assertTrue(
			equals(
				objectDefinition,
				ObjectDefinitionSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"objectDefinition",
								new HashMap<String, Object>() {
									{
										put(
											"objectDefinitionId",
											objectDefinition.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/objectDefinition"))));
	}

	@Test
	public void testGraphQLGetObjectDefinitionNotFound() throws Exception {
		Long irrelevantObjectDefinitionId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"objectDefinition",
						new HashMap<String, Object>() {
							{
								put(
									"objectDefinitionId",
									irrelevantObjectDefinitionId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchObjectDefinition() throws Exception {
		ObjectDefinition postObjectDefinition =
			testPatchObjectDefinition_addObjectDefinition();

		ObjectDefinition randomPatchObjectDefinition =
			randomPatchObjectDefinition();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectDefinition patchObjectDefinition =
			objectDefinitionResource.patchObjectDefinition(
				postObjectDefinition.getId(), randomPatchObjectDefinition);

		ObjectDefinition expectedPatchObjectDefinition =
			postObjectDefinition.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchObjectDefinition, randomPatchObjectDefinition);

		ObjectDefinition getObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				patchObjectDefinition.getId());

		assertEquals(expectedPatchObjectDefinition, getObjectDefinition);
		assertValid(getObjectDefinition);
	}

	protected ObjectDefinition testPatchObjectDefinition_addObjectDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutObjectDefinition() throws Exception {
		ObjectDefinition postObjectDefinition =
			testPutObjectDefinition_addObjectDefinition();

		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		ObjectDefinition putObjectDefinition =
			objectDefinitionResource.putObjectDefinition(
				postObjectDefinition.getId(), randomObjectDefinition);

		assertEquals(randomObjectDefinition, putObjectDefinition);
		assertValid(putObjectDefinition);

		ObjectDefinition getObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				putObjectDefinition.getId());

		assertEquals(randomObjectDefinition, getObjectDefinition);
		assertValid(getObjectDefinition);
	}

	protected ObjectDefinition testPutObjectDefinition_addObjectDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostObjectDefinitionPublish() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectDefinition objectDefinition =
			testPostObjectDefinitionPublish_addObjectDefinition();

		assertHttpResponseStatusCode(
			204,
			objectDefinitionResource.postObjectDefinitionPublishHttpResponse(
				objectDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			objectDefinitionResource.postObjectDefinitionPublishHttpResponse(
				0L));
	}

	protected ObjectDefinition
			testPostObjectDefinitionPublish_addObjectDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ObjectDefinition testGraphQLObjectDefinition_addObjectDefinition()
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
		ObjectDefinition objectDefinition1,
		ObjectDefinition objectDefinition2) {

		Assert.assertTrue(
			objectDefinition1 + " does not equal " + objectDefinition2,
			equals(objectDefinition1, objectDefinition2));
	}

	protected void assertEquals(
		List<ObjectDefinition> objectDefinitions1,
		List<ObjectDefinition> objectDefinitions2) {

		Assert.assertEquals(
			objectDefinitions1.size(), objectDefinitions2.size());

		for (int i = 0; i < objectDefinitions1.size(); i++) {
			ObjectDefinition objectDefinition1 = objectDefinitions1.get(i);
			ObjectDefinition objectDefinition2 = objectDefinitions2.get(i);

			assertEquals(objectDefinition1, objectDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ObjectDefinition> objectDefinitions1,
		List<ObjectDefinition> objectDefinitions2) {

		Assert.assertEquals(
			objectDefinitions1.size(), objectDefinitions2.size());

		for (ObjectDefinition objectDefinition1 : objectDefinitions1) {
			boolean contains = false;

			for (ObjectDefinition objectDefinition2 : objectDefinitions2) {
				if (equals(objectDefinition1, objectDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				objectDefinitions2 + " does not contain " + objectDefinition1,
				contains);
		}
	}

	protected void assertValid(ObjectDefinition objectDefinition)
		throws Exception {

		boolean valid = true;

		if (objectDefinition.getDateCreated() == null) {
			valid = false;
		}

		if (objectDefinition.getDateModified() == null) {
			valid = false;
		}

		if (objectDefinition.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (objectDefinition.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("label", additionalAssertFieldName)) {
				if (objectDefinition.getLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (objectDefinition.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("objectFields", additionalAssertFieldName)) {
				if (objectDefinition.getObjectFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("panelAppOrder", additionalAssertFieldName)) {
				if (objectDefinition.getPanelAppOrder() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("panelCategoryKey", additionalAssertFieldName)) {
				if (objectDefinition.getPanelCategoryKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pluralLabel", additionalAssertFieldName)) {
				if (objectDefinition.getPluralLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("scope", additionalAssertFieldName)) {
				if (objectDefinition.getScope() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (objectDefinition.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("system", additionalAssertFieldName)) {
				if (objectDefinition.getSystem() == null) {
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

	protected void assertValid(Page<ObjectDefinition> page) {
		boolean valid = false;

		java.util.Collection<ObjectDefinition> objectDefinitions =
			page.getItems();

		int size = objectDefinitions.size();

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
					com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition.
						class)) {

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
		ObjectDefinition objectDefinition1,
		ObjectDefinition objectDefinition2) {

		if (objectDefinition1 == objectDefinition2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectDefinition1.getActions(),
						(Map)objectDefinition2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getDateCreated(),
						objectDefinition2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getDateModified(),
						objectDefinition2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getId(), objectDefinition2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("label", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectDefinition1.getLabel(),
						(Map)objectDefinition2.getLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getName(),
						objectDefinition2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("objectFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getObjectFields(),
						objectDefinition2.getObjectFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("panelAppOrder", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getPanelAppOrder(),
						objectDefinition2.getPanelAppOrder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("panelCategoryKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getPanelCategoryKey(),
						objectDefinition2.getPanelCategoryKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pluralLabel", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectDefinition1.getPluralLabel(),
						(Map)objectDefinition2.getPluralLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("scope", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getScope(),
						objectDefinition2.getScope())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getStatus(),
						objectDefinition2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("system", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectDefinition1.getSystem(),
						objectDefinition2.getSystem())) {

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

		if (!(_objectDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_objectDefinitionResource;

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
		ObjectDefinition objectDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
							objectDefinition.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							objectDefinition.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(objectDefinition.getDateCreated()));
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
							objectDefinition.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							objectDefinition.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(objectDefinition.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("label")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(objectDefinition.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("objectFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("panelAppOrder")) {
			sb.append("'");
			sb.append(String.valueOf(objectDefinition.getPanelAppOrder()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("panelCategoryKey")) {
			sb.append("'");
			sb.append(String.valueOf(objectDefinition.getPanelCategoryKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("pluralLabel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("scope")) {
			sb.append("'");
			sb.append(String.valueOf(objectDefinition.getScope()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("system")) {
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

	protected ObjectDefinition randomObjectDefinition() throws Exception {
		return new ObjectDefinition() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				panelAppOrder = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				panelCategoryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				scope = StringUtil.toLowerCase(RandomTestUtil.randomString());
				system = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected ObjectDefinition randomIrrelevantObjectDefinition()
		throws Exception {

		ObjectDefinition randomIrrelevantObjectDefinition =
			randomObjectDefinition();

		return randomIrrelevantObjectDefinition;
	}

	protected ObjectDefinition randomPatchObjectDefinition() throws Exception {
		return randomObjectDefinition();
	}

	protected ObjectDefinitionResource objectDefinitionResource;
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
		LogFactoryUtil.getLog(BaseObjectDefinitionResourceTestCase.class);

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
	private com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource
		_objectDefinitionResource;

}