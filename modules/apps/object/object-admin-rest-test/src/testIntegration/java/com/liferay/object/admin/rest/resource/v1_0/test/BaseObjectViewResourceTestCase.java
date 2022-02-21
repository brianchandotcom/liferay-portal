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

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectViewResource;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectViewSerDes;
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
public abstract class BaseObjectViewResourceTestCase {

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

		_objectViewResource.setContextCompany(testCompany);

		ObjectViewResource.Builder builder = ObjectViewResource.builder();

		objectViewResource = builder.authentication(
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

		ObjectView objectView1 = randomObjectView();

		String json = objectMapper.writeValueAsString(objectView1);

		ObjectView objectView2 = ObjectViewSerDes.toDTO(json);

		Assert.assertTrue(equals(objectView1, objectView2));
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

		ObjectView objectView = randomObjectView();

		String json1 = objectMapper.writeValueAsString(objectView);
		String json2 = ObjectViewSerDes.toJSON(objectView);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ObjectView objectView = randomObjectView();

		String json = ObjectViewSerDes.toJSON(objectView);

		Assert.assertFalse(json.contains(regex));

		objectView = ObjectViewSerDes.toDTO(json);
	}

	@Test
	public void testGetObjectDefinitionObjectViewsPage() throws Exception {
		Long objectDefinitionId =
			testGetObjectDefinitionObjectViewsPage_getObjectDefinitionId();
		Long irrelevantObjectDefinitionId =
			testGetObjectDefinitionObjectViewsPage_getIrrelevantObjectDefinitionId();

		Page<ObjectView> page =
			objectViewResource.getObjectDefinitionObjectViewsPage(
				objectDefinitionId, null, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantObjectDefinitionId != null) {
			ObjectView irrelevantObjectView =
				testGetObjectDefinitionObjectViewsPage_addObjectView(
					irrelevantObjectDefinitionId, randomIrrelevantObjectView());

			page = objectViewResource.getObjectDefinitionObjectViewsPage(
				irrelevantObjectDefinitionId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantObjectView),
				(List<ObjectView>)page.getItems());
			assertValid(page);
		}

		ObjectView objectView1 =
			testGetObjectDefinitionObjectViewsPage_addObjectView(
				objectDefinitionId, randomObjectView());

		ObjectView objectView2 =
			testGetObjectDefinitionObjectViewsPage_addObjectView(
				objectDefinitionId, randomObjectView());

		page = objectViewResource.getObjectDefinitionObjectViewsPage(
			objectDefinitionId, null, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(objectView1, objectView2),
			(List<ObjectView>)page.getItems());
		assertValid(page);

		objectViewResource.deleteObjectView(objectView1.getId());

		objectViewResource.deleteObjectView(objectView2.getId());
	}

	@Test
	public void testGetObjectDefinitionObjectViewsPageWithPagination()
		throws Exception {

		Long objectDefinitionId =
			testGetObjectDefinitionObjectViewsPage_getObjectDefinitionId();

		ObjectView objectView1 =
			testGetObjectDefinitionObjectViewsPage_addObjectView(
				objectDefinitionId, randomObjectView());

		ObjectView objectView2 =
			testGetObjectDefinitionObjectViewsPage_addObjectView(
				objectDefinitionId, randomObjectView());

		ObjectView objectView3 =
			testGetObjectDefinitionObjectViewsPage_addObjectView(
				objectDefinitionId, randomObjectView());

		Page<ObjectView> page1 =
			objectViewResource.getObjectDefinitionObjectViewsPage(
				objectDefinitionId, null, Pagination.of(1, 2));

		List<ObjectView> objectViews1 = (List<ObjectView>)page1.getItems();

		Assert.assertEquals(objectViews1.toString(), 2, objectViews1.size());

		Page<ObjectView> page2 =
			objectViewResource.getObjectDefinitionObjectViewsPage(
				objectDefinitionId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ObjectView> objectViews2 = (List<ObjectView>)page2.getItems();

		Assert.assertEquals(objectViews2.toString(), 1, objectViews2.size());

		Page<ObjectView> page3 =
			objectViewResource.getObjectDefinitionObjectViewsPage(
				objectDefinitionId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(objectView1, objectView2, objectView3),
			(List<ObjectView>)page3.getItems());
	}

	protected ObjectView testGetObjectDefinitionObjectViewsPage_addObjectView(
			Long objectDefinitionId, ObjectView objectView)
		throws Exception {

		return objectViewResource.postObjectDefinitionObjectView(
			objectDefinitionId, objectView);
	}

	protected Long
			testGetObjectDefinitionObjectViewsPage_getObjectDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetObjectDefinitionObjectViewsPage_getIrrelevantObjectDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostObjectDefinitionObjectView() throws Exception {
		ObjectView randomObjectView = randomObjectView();

		ObjectView postObjectView =
			testPostObjectDefinitionObjectView_addObjectView(randomObjectView);

		assertEquals(randomObjectView, postObjectView);
		assertValid(postObjectView);
	}

	protected ObjectView testPostObjectDefinitionObjectView_addObjectView(
			ObjectView objectView)
		throws Exception {

		return objectViewResource.postObjectDefinitionObjectView(
			testGetObjectDefinitionObjectViewsPage_getObjectDefinitionId(),
			objectView);
	}

	@Test
	public void testDeleteObjectView() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ObjectView objectView = testDeleteObjectView_addObjectView();

		assertHttpResponseStatusCode(
			204,
			objectViewResource.deleteObjectViewHttpResponse(
				objectView.getId()));

		assertHttpResponseStatusCode(
			404,
			objectViewResource.getObjectViewHttpResponse(objectView.getId()));

		assertHttpResponseStatusCode(
			404, objectViewResource.getObjectViewHttpResponse(0L));
	}

	protected ObjectView testDeleteObjectView_addObjectView() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteObjectView() throws Exception {
		ObjectView objectView = testGraphQLObjectView_addObjectView();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteObjectView",
						new HashMap<String, Object>() {
							{
								put("objectViewId", objectView.getId());
							}
						})),
				"JSONObject/data", "Object/deleteObjectView"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"objectView",
					new HashMap<String, Object>() {
						{
							put("objectViewId", objectView.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetObjectView() throws Exception {
		ObjectView postObjectView = testGetObjectView_addObjectView();

		ObjectView getObjectView = objectViewResource.getObjectView(
			postObjectView.getId());

		assertEquals(postObjectView, getObjectView);
		assertValid(getObjectView);
	}

	protected ObjectView testGetObjectView_addObjectView() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetObjectView() throws Exception {
		ObjectView objectView = testGraphQLObjectView_addObjectView();

		Assert.assertTrue(
			equals(
				objectView,
				ObjectViewSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"objectView",
								new HashMap<String, Object>() {
									{
										put("objectViewId", objectView.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/objectView"))));
	}

	@Test
	public void testGraphQLGetObjectViewNotFound() throws Exception {
		Long irrelevantObjectViewId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"objectView",
						new HashMap<String, Object>() {
							{
								put("objectViewId", irrelevantObjectViewId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutObjectView() throws Exception {
		ObjectView postObjectView = testPutObjectView_addObjectView();

		ObjectView randomObjectView = randomObjectView();

		ObjectView putObjectView = objectViewResource.putObjectView(
			postObjectView.getId(), randomObjectView);

		assertEquals(randomObjectView, putObjectView);
		assertValid(putObjectView);

		ObjectView getObjectView = objectViewResource.getObjectView(
			putObjectView.getId());

		assertEquals(randomObjectView, getObjectView);
		assertValid(getObjectView);
	}

	protected ObjectView testPutObjectView_addObjectView() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ObjectView testGraphQLObjectView_addObjectView()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ObjectView objectView, List<ObjectView> objectViews) {

		boolean contains = false;

		for (ObjectView item : objectViews) {
			if (equals(objectView, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			objectViews + " does not contain " + objectView, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ObjectView objectView1, ObjectView objectView2) {

		Assert.assertTrue(
			objectView1 + " does not equal " + objectView2,
			equals(objectView1, objectView2));
	}

	protected void assertEquals(
		List<ObjectView> objectViews1, List<ObjectView> objectViews2) {

		Assert.assertEquals(objectViews1.size(), objectViews2.size());

		for (int i = 0; i < objectViews1.size(); i++) {
			ObjectView objectView1 = objectViews1.get(i);
			ObjectView objectView2 = objectViews2.get(i);

			assertEquals(objectView1, objectView2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ObjectView> objectViews1, List<ObjectView> objectViews2) {

		Assert.assertEquals(objectViews1.size(), objectViews2.size());

		for (ObjectView objectView1 : objectViews1) {
			boolean contains = false;

			for (ObjectView objectView2 : objectViews2) {
				if (equals(objectView1, objectView2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				objectViews2 + " does not contain " + objectView1, contains);
		}
	}

	protected void assertValid(ObjectView objectView) throws Exception {
		boolean valid = true;

		if (objectView.getDateCreated() == null) {
			valid = false;
		}

		if (objectView.getDateModified() == null) {
			valid = false;
		}

		if (objectView.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (objectView.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultObjectView", additionalAssertFieldName)) {

				if (objectView.getDefaultObjectView() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (objectView.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionId", additionalAssertFieldName)) {

				if (objectView.getObjectDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectViewColumns", additionalAssertFieldName)) {

				if (objectView.getObjectViewColumns() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"objectViewSortColumns", additionalAssertFieldName)) {

				if (objectView.getObjectViewSortColumns() == null) {
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

	protected void assertValid(Page<ObjectView> page) {
		boolean valid = false;

		java.util.Collection<ObjectView> objectViews = page.getItems();

		int size = objectViews.size();

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
					com.liferay.object.admin.rest.dto.v1_0.ObjectView.class)) {

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

	protected boolean equals(ObjectView objectView1, ObjectView objectView2) {
		if (objectView1 == objectView2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectView1.getActions(),
						(Map)objectView2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectView1.getDateCreated(),
						objectView2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectView1.getDateModified(),
						objectView2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultObjectView", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectView1.getDefaultObjectView(),
						objectView2.getDefaultObjectView())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						objectView1.getId(), objectView2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)objectView1.getName(),
						(Map)objectView2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectDefinitionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectView1.getObjectDefinitionId(),
						objectView2.getObjectDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectViewColumns", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectView1.getObjectViewColumns(),
						objectView2.getObjectViewColumns())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"objectViewSortColumns", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						objectView1.getObjectViewSortColumns(),
						objectView2.getObjectViewSortColumns())) {

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

		if (!(_objectViewResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_objectViewResource;

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
		EntityField entityField, String operator, ObjectView objectView) {

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
						DateUtils.addSeconds(objectView.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(objectView.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(objectView.getDateCreated()));
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
							objectView.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(objectView.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(objectView.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultObjectView")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectViewColumns")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("objectViewSortColumns")) {
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

	protected ObjectView randomObjectView() throws Exception {
		return new ObjectView() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultObjectView = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				objectDefinitionId = RandomTestUtil.randomLong();
			}
		};
	}

	protected ObjectView randomIrrelevantObjectView() throws Exception {
		ObjectView randomIrrelevantObjectView = randomObjectView();

		return randomIrrelevantObjectView;
	}

	protected ObjectView randomPatchObjectView() throws Exception {
		return randomObjectView();
	}

	protected ObjectViewResource objectViewResource;
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
		LogFactoryUtil.getLog(BaseObjectViewResourceTestCase.class);

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
	private com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource
		_objectViewResource;

}