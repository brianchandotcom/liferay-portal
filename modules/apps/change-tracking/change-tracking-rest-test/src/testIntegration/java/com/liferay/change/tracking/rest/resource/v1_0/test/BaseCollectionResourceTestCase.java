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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.CollectionResource;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CollectionSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
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
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public abstract class BaseCollectionResourceTestCase {

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

		_collectionResource.setContextCompany(testCompany);

		CollectionResource.Builder builder = CollectionResource.builder();

		collectionResource = builder.locale(
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

		Collection collection1 = randomCollection();

		String json = objectMapper.writeValueAsString(collection1);

		Collection collection2 = CollectionSerDes.toDTO(json);

		Assert.assertTrue(equals(collection1, collection2));
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

		Collection collection = randomCollection();

		String json1 = objectMapper.writeValueAsString(collection);
		String json2 = CollectionSerDes.toJSON(collection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Collection collection = randomCollection();

		collection.setDescription(regex);
		collection.setName(regex);
		collection.setStatusByUserName(regex);

		String json = CollectionSerDes.toJSON(collection);

		Assert.assertFalse(json.contains(regex));

		collection = CollectionSerDes.toDTO(json);

		Assert.assertEquals(regex, collection.getDescription());
		Assert.assertEquals(regex, collection.getName());
		Assert.assertEquals(regex, collection.getStatusByUserName());
	}

	@Test
	public void testGetCollectionsPage() throws Exception {
		Page<Collection> page = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Collection collection1 = testGetCollectionsPage_addCollection(
			randomCollection());

		Collection collection2 = testGetCollectionsPage_addCollection(
			randomCollection());

		page = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(collection1, collection2),
			(List<Collection>)page.getItems());
		assertValid(page);

		collectionResource.deleteCollection(collection1.getId(), null);

		collectionResource.deleteCollection(collection2.getId(), null);
	}

	@Test
	public void testGetCollectionsPageWithPagination() throws Exception {
		Collection collection1 = testGetCollectionsPage_addCollection(
			randomCollection());

		Collection collection2 = testGetCollectionsPage_addCollection(
			randomCollection());

		Collection collection3 = testGetCollectionsPage_addCollection(
			randomCollection());

		Page<Collection> page1 = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 2), null);

		List<Collection> collections1 = (List<Collection>)page1.getItems();

		Assert.assertEquals(collections1.toString(), 2, collections1.size());

		Page<Collection> page2 = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Collection> collections2 = (List<Collection>)page2.getItems();

		Assert.assertEquals(collections2.toString(), 1, collections2.size());

		Page<Collection> page3 = collectionResource.getCollectionsPage(
			null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(collection1, collection2, collection3),
			(List<Collection>)page3.getItems());
	}

	@Test
	public void testGetCollectionsPageWithSortDateTime() throws Exception {
		testGetCollectionsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, collection1, collection2) -> {
				BeanUtils.setProperty(
					collection1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetCollectionsPageWithSortInteger() throws Exception {
		testGetCollectionsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, collection1, collection2) -> {
				BeanUtils.setProperty(collection1, entityField.getName(), 0);
				BeanUtils.setProperty(collection2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetCollectionsPageWithSortString() throws Exception {
		testGetCollectionsPageWithSort(
			EntityField.Type.STRING,
			(entityField, collection1, collection2) -> {
				Class<?> clazz = collection1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						collection1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						collection2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						collection1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						collection2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetCollectionsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Collection, Collection, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Collection collection1 = randomCollection();
		Collection collection2 = randomCollection();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, collection1, collection2);
		}

		collection1 = testGetCollectionsPage_addCollection(collection1);

		collection2 = testGetCollectionsPage_addCollection(collection2);

		for (EntityField entityField : entityFields) {
			Page<Collection> ascPage = collectionResource.getCollectionsPage(
				null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(collection1, collection2),
				(List<Collection>)ascPage.getItems());

			Page<Collection> descPage = collectionResource.getCollectionsPage(
				null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(collection2, collection1),
				(List<Collection>)descPage.getItems());
		}
	}

	protected Collection testGetCollectionsPage_addCollection(
			Collection collection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCollectionsPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"collections",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject collectionsJSONObject = dataJSONObject.getJSONObject(
			"collections");

		Assert.assertEquals(0, collectionsJSONObject.get("totalCount"));

		Collection collection1 = testGraphQLCollection_addCollection();
		Collection collection2 = testGraphQLCollection_addCollection();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		collectionsJSONObject = dataJSONObject.getJSONObject("collections");

		Assert.assertEquals(2, collectionsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(collection1, collection2),
			collectionsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostCollection() throws Exception {
		Collection randomCollection = randomCollection();

		Collection postCollection = testPostCollection_addCollection(
			randomCollection);

		assertEquals(randomCollection, postCollection);
		assertValid(postCollection);
	}

	protected Collection testPostCollection_addCollection(Collection collection)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteCollection() throws Exception {
		Collection collection = testDeleteCollection_addCollection();

		assertHttpResponseStatusCode(
			204,
			collectionResource.deleteCollectionHttpResponse(
				collection.getId(), null));

		assertHttpResponseStatusCode(
			404,
			collectionResource.getCollectionHttpResponse(
				collection.getId(), null));
	}

	protected Collection testDeleteCollection_addCollection() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteCollection() throws Exception {
		Collection collection = testGraphQLCollection_addCollection();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteCollection",
				new HashMap<String, Object>() {
					{
						put("collectionId", collection.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteCollection"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"collection",
					new HashMap<String, Object>() {
						{
							put("collectionId", collection.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetCollection() throws Exception {
		Collection postCollection = testGetCollection_addCollection();

		Collection getCollection = collectionResource.getCollection(
			postCollection.getId(), null);

		assertEquals(postCollection, getCollection);
		assertValid(getCollection);
	}

	protected Collection testGetCollection_addCollection() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCollection() throws Exception {
		Collection collection = testGraphQLCollection_addCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"collection",
				new HashMap<String, Object>() {
					{
						put("collectionId", collection.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				collection, dataJSONObject.getJSONObject("collection")));
	}

	@Test
	public void testPostCollectionCheckout() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostCollectionPublish() throws Exception {
		Assert.assertTrue(false);
	}

	protected Collection testGraphQLCollection_addCollection()
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
		Collection collection1, Collection collection2) {

		Assert.assertTrue(
			collection1 + " does not equal " + collection2,
			equals(collection1, collection2));
	}

	protected void assertEquals(
		List<Collection> collections1, List<Collection> collections2) {

		Assert.assertEquals(collections1.size(), collections2.size());

		for (int i = 0; i < collections1.size(); i++) {
			Collection collection1 = collections1.get(i);
			Collection collection2 = collections2.get(i);

			assertEquals(collection1, collection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Collection> collections1, List<Collection> collections2) {

		Assert.assertEquals(collections1.size(), collections2.size());

		for (Collection collection1 : collections1) {
			boolean contains = false;

			for (Collection collection2 : collections2) {
				if (equals(collection1, collection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				collections2 + " does not contain " + collection1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Collection> collections, JSONArray jsonArray) {

		for (Collection collection : collections) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(collection, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + collection, contains);
		}
	}

	protected void assertValid(Collection collection) {
		boolean valid = true;

		if (collection.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionCount", additionalAssertFieldName)) {
				if (collection.getAdditionCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (collection.getCompanyId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateStatus", additionalAssertFieldName)) {
				if (collection.getDateStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("deletionCount", additionalAssertFieldName)) {
				if (collection.getDeletionCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (collection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"modificationCount", additionalAssertFieldName)) {

				if (collection.getModificationCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (collection.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("statusByUserName", additionalAssertFieldName)) {
				if (collection.getStatusByUserName() == null) {
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

	protected void assertValid(Page<Collection> page) {
		boolean valid = false;

		java.util.Collection<Collection> collections = page.getItems();

		int size = collections.size();

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

		graphQLFields.add(new GraphQLField("id"));

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Collection collection1, Collection collection2) {
		if (collection1 == collection2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getAdditionCount(),
						collection2.getAdditionCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("companyId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getCompanyId(),
						collection2.getCompanyId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getDateStatus(),
						collection2.getDateStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("deletionCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getDeletionCount(),
						collection2.getDeletionCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getDescription(),
						collection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getId(), collection2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"modificationCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						collection1.getModificationCount(),
						collection2.getModificationCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getName(), collection2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("statusByUserName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						collection1.getStatusByUserName(),
						collection2.getStatusByUserName())) {

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
		Collection collection, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("additionCount", fieldName)) {
				if (!Objects.equals(
						collection.getAdditionCount(),
						(Long)jsonObject.getLong("additionCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("companyId", fieldName)) {
				if (!Objects.equals(
						collection.getCompanyId(),
						(Long)jsonObject.getLong("companyId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("deletionCount", fieldName)) {
				if (!Objects.equals(
						collection.getDeletionCount(),
						(Long)jsonObject.getLong("deletionCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.equals(
						collection.getDescription(),
						(String)jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.equals(
						collection.getId(), (Long)jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modificationCount", fieldName)) {
				if (!Objects.equals(
						collection.getModificationCount(),
						(Long)jsonObject.getLong("modificationCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.equals(
						collection.getName(),
						(String)jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("statusByUserName", fieldName)) {
				if (!Objects.equals(
						collection.getStatusByUserName(),
						(String)jsonObject.getString("statusByUserName"))) {

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

		if (!(_collectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_collectionResource;

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
		EntityField entityField, String operator, Collection collection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("additionCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("companyId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateStatus")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(collection.getDateStatus(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(collection.getDateStatus(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(collection.getDateStatus()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("deletionCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(collection.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modificationCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(collection.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("statusByUserName")) {
			sb.append("'");
			sb.append(String.valueOf(collection.getStatusByUserName()));
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

	protected Collection randomCollection() throws Exception {
		return new Collection() {
			{
				additionCount = RandomTestUtil.randomLong();
				companyId = RandomTestUtil.randomLong();
				dateStatus = RandomTestUtil.nextDate();
				deletionCount = RandomTestUtil.randomLong();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				modificationCount = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				statusByUserName = RandomTestUtil.randomString();
			}
		};
	}

	protected Collection randomIrrelevantCollection() throws Exception {
		Collection randomIrrelevantCollection = randomCollection();

		return randomIrrelevantCollection;
	}

	protected Collection randomPatchCollection() throws Exception {
		return randomCollection();
	}

	protected CollectionResource collectionResource;
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

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCollectionResourceTestCase.class);

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
	private com.liferay.change.tracking.rest.resource.v1_0.CollectionResource
		_collectionResource;

}