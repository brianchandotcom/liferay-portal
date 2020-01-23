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

import com.liferay.data.engine.rest.client.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.permission.Permission;
import com.liferay.data.engine.rest.client.resource.v2_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataRecordCollectionSerDes;
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
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
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
import org.apache.log4j.Level;

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
public abstract class BaseDataRecordCollectionResourceTestCase {

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

		_dataRecordCollectionResource.setContextCompany(testCompany);

		DataRecordCollectionResource.Builder builder =
			DataRecordCollectionResource.builder();

		dataRecordCollectionResource = builder.locale(
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

		DataRecordCollection dataRecordCollection1 =
			randomDataRecordCollection();

		String json = objectMapper.writeValueAsString(dataRecordCollection1);

		DataRecordCollection dataRecordCollection2 =
			DataRecordCollectionSerDes.toDTO(json);

		Assert.assertTrue(equals(dataRecordCollection1, dataRecordCollection2));
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

		DataRecordCollection dataRecordCollection =
			randomDataRecordCollection();

		String json1 = objectMapper.writeValueAsString(dataRecordCollection);
		String json2 = DataRecordCollectionSerDes.toJSON(dataRecordCollection);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataRecordCollection dataRecordCollection =
			randomDataRecordCollection();

		dataRecordCollection.setDataRecordCollectionKey(regex);

		String json = DataRecordCollectionSerDes.toJSON(dataRecordCollection);

		Assert.assertFalse(json.contains(regex));

		dataRecordCollection = DataRecordCollectionSerDes.toDTO(json);

		Assert.assertEquals(
			regex, dataRecordCollection.getDataRecordCollectionKey());
	}

	@Test
	public void testGetDataDefinitionDataRecordCollection() throws Exception {
		DataRecordCollection postDataRecordCollection =
			testGetDataDefinitionDataRecordCollection_addDataRecordCollection();

		DataRecordCollection getDataRecordCollection =
			dataRecordCollectionResource.getDataDefinitionDataRecordCollection(
				postDataRecordCollection.getDataDefinitionId());

		assertEquals(postDataRecordCollection, getDataRecordCollection);
		assertValid(getDataRecordCollection);
	}

	protected DataRecordCollection
			testGetDataDefinitionDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDataDefinitionDataRecordCollection()
		throws Exception {

		DataRecordCollection dataRecordCollection =
			testGraphQLDataRecordCollection_addDataRecordCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataDefinitionDataRecordCollection",
				new HashMap<String, Object>() {
					{
						put(
							"dataDefinitionId",
							dataRecordCollection.getDataDefinitionId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataRecordCollection,
				dataJSONObject.getJSONObject(
					"dataDefinitionDataRecordCollection")));
	}

	@Test
	public void testGetDataDefinitionDataRecordCollectionsPage()
		throws Exception {

		Page<DataRecordCollection> page =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId(),
					RandomTestUtil.randomString(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			DataRecordCollection irrelevantDataRecordCollection =
				testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
					irrelevantDataDefinitionId,
					randomIrrelevantDataRecordCollection());

			page =
				dataRecordCollectionResource.
					getDataDefinitionDataRecordCollectionsPage(
						irrelevantDataDefinitionId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataRecordCollection),
				(List<DataRecordCollection>)page.getItems());
			assertValid(page);
		}

		DataRecordCollection dataRecordCollection1 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection2 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		page =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					dataDefinitionId, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecordCollection1, dataRecordCollection2),
			(List<DataRecordCollection>)page.getItems());
		assertValid(page);

		dataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollection1.getId());

		dataRecordCollectionResource.deleteDataRecordCollection(
			dataRecordCollection2.getId());
	}

	@Test
	public void testGetDataDefinitionDataRecordCollectionsPageWithPagination()
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId();

		DataRecordCollection dataRecordCollection1 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection2 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		DataRecordCollection dataRecordCollection3 =
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				dataDefinitionId, randomDataRecordCollection());

		Page<DataRecordCollection> page1 =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					dataDefinitionId, null, Pagination.of(1, 2));

		List<DataRecordCollection> dataRecordCollections1 =
			(List<DataRecordCollection>)page1.getItems();

		Assert.assertEquals(
			dataRecordCollections1.toString(), 2,
			dataRecordCollections1.size());

		Page<DataRecordCollection> page2 =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					dataDefinitionId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataRecordCollection> dataRecordCollections2 =
			(List<DataRecordCollection>)page2.getItems();

		Assert.assertEquals(
			dataRecordCollections2.toString(), 1,
			dataRecordCollections2.size());

		Page<DataRecordCollection> page3 =
			dataRecordCollectionResource.
				getDataDefinitionDataRecordCollectionsPage(
					dataDefinitionId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				dataRecordCollection1, dataRecordCollection2,
				dataRecordCollection3),
			(List<DataRecordCollection>)page3.getItems());
	}

	protected DataRecordCollection
			testGetDataDefinitionDataRecordCollectionsPage_addDataRecordCollection(
				Long dataDefinitionId,
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				dataDefinitionId, dataRecordCollection);
	}

	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataRecordCollectionsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionDataRecordCollection() throws Exception {
		DataRecordCollection randomDataRecordCollection =
			randomDataRecordCollection();

		DataRecordCollection postDataRecordCollection =
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				randomDataRecordCollection);

		assertEquals(randomDataRecordCollection, postDataRecordCollection);
		assertValid(postDataRecordCollection);
	}

	protected DataRecordCollection
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				testGetDataDefinitionDataRecordCollectionsPage_getDataDefinitionId(),
				dataRecordCollection);
	}

	@Test
	public void testDeleteDataRecordCollection() throws Exception {
		DataRecordCollection dataRecordCollection =
			testDeleteDataRecordCollection_addDataRecordCollection();

		assertHttpResponseStatusCode(
			204,
			dataRecordCollectionResource.deleteDataRecordCollectionHttpResponse(
				dataRecordCollection.getId()));

		assertHttpResponseStatusCode(
			404,
			dataRecordCollectionResource.getDataRecordCollectionHttpResponse(
				dataRecordCollection.getId()));

		assertHttpResponseStatusCode(
			404,
			dataRecordCollectionResource.getDataRecordCollectionHttpResponse(
				0L));
	}

	protected DataRecordCollection
			testDeleteDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDataRecordCollection() throws Exception {
		DataRecordCollection dataRecordCollection =
			testGraphQLDataRecordCollection_addDataRecordCollection();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteDataRecordCollection",
				new HashMap<String, Object>() {
					{
						put(
							"dataRecordCollectionId",
							dataRecordCollection.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			dataJSONObject.getBoolean("deleteDataRecordCollection"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"dataRecordCollection",
					new HashMap<String, Object>() {
						{
							put(
								"dataRecordCollectionId",
								dataRecordCollection.getId());
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
	public void testGetDataRecordCollection() throws Exception {
		DataRecordCollection postDataRecordCollection =
			testGetDataRecordCollection_addDataRecordCollection();

		DataRecordCollection getDataRecordCollection =
			dataRecordCollectionResource.getDataRecordCollection(
				postDataRecordCollection.getId());

		assertEquals(postDataRecordCollection, getDataRecordCollection);
		assertValid(getDataRecordCollection);
	}

	protected DataRecordCollection
			testGetDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDataRecordCollection() throws Exception {
		DataRecordCollection dataRecordCollection =
			testGraphQLDataRecordCollection_addDataRecordCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataRecordCollection",
				new HashMap<String, Object>() {
					{
						put(
							"dataRecordCollectionId",
							dataRecordCollection.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataRecordCollection,
				dataJSONObject.getJSONObject("dataRecordCollection")));
	}

	@Test
	public void testPutDataRecordCollection() throws Exception {
		DataRecordCollection postDataRecordCollection =
			testPutDataRecordCollection_addDataRecordCollection();

		DataRecordCollection randomDataRecordCollection =
			randomDataRecordCollection();

		DataRecordCollection putDataRecordCollection =
			dataRecordCollectionResource.putDataRecordCollection(
				postDataRecordCollection.getId(), randomDataRecordCollection);

		assertEquals(randomDataRecordCollection, putDataRecordCollection);
		assertValid(putDataRecordCollection);

		DataRecordCollection getDataRecordCollection =
			dataRecordCollectionResource.getDataRecordCollection(
				putDataRecordCollection.getId());

		assertEquals(randomDataRecordCollection, getDataRecordCollection);
		assertValid(getDataRecordCollection);
	}

	protected DataRecordCollection
			testPutDataRecordCollection_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutDataRecordCollectionPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataRecordCollection dataRecordCollection =
			testPutDataRecordCollectionPermission_addDataRecordCollection();

		assertHttpResponseStatusCode(
			204,
			dataRecordCollectionResource.
				putDataRecordCollectionPermissionHttpResponse(
					dataRecordCollection.getId(),
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"VIEW"});
								setRoleName("Guest");
							}
						}
					}));

		assertHttpResponseStatusCode(
			404,
			dataRecordCollectionResource.
				putDataRecordCollectionPermissionHttpResponse(
					0L,
					new Permission[] {
						new Permission() {
							{
								setActionIds(new String[] {"-"});
								setRoleName("-");
							}
						}
					}));
	}

	protected DataRecordCollection
			testPutDataRecordCollectionPermission_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataRecordCollectionPermissionByCurrentUser()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetSiteDataRecordCollectionByDataRecordCollectionKey()
		throws Exception {

		DataRecordCollection postDataRecordCollection =
			testGetSiteDataRecordCollectionByDataRecordCollectionKey_addDataRecordCollection();

		DataRecordCollection getDataRecordCollection =
			dataRecordCollectionResource.
				getSiteDataRecordCollectionByDataRecordCollectionKey(
					postDataRecordCollection.getSiteId(),
					postDataRecordCollection.getDataRecordCollectionKey());

		assertEquals(postDataRecordCollection, getDataRecordCollection);
		assertValid(getDataRecordCollection);
	}

	protected DataRecordCollection
			testGetSiteDataRecordCollectionByDataRecordCollectionKey_addDataRecordCollection()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteDataRecordCollectionByDataRecordCollectionKey()
		throws Exception {

		DataRecordCollection dataRecordCollection =
			testGraphQLDataRecordCollection_addDataRecordCollection();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataRecordCollectionByDataRecordCollectionKey",
				new HashMap<String, Object>() {
					{
						put("siteId", dataRecordCollection.getSiteId());
						put(
							"dataRecordCollectionKey",
							dataRecordCollection.getDataRecordCollectionKey());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataRecordCollection,
				dataJSONObject.getJSONObject(
					"dataRecordCollectionByDataRecordCollectionKey")));
	}

	protected DataRecordCollection
			testGraphQLDataRecordCollection_addDataRecordCollection()
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
		DataRecordCollection dataRecordCollection1,
		DataRecordCollection dataRecordCollection2) {

		Assert.assertTrue(
			dataRecordCollection1 + " does not equal " + dataRecordCollection2,
			equals(dataRecordCollection1, dataRecordCollection2));
	}

	protected void assertEquals(
		List<DataRecordCollection> dataRecordCollections1,
		List<DataRecordCollection> dataRecordCollections2) {

		Assert.assertEquals(
			dataRecordCollections1.size(), dataRecordCollections2.size());

		for (int i = 0; i < dataRecordCollections1.size(); i++) {
			DataRecordCollection dataRecordCollection1 =
				dataRecordCollections1.get(i);
			DataRecordCollection dataRecordCollection2 =
				dataRecordCollections2.get(i);

			assertEquals(dataRecordCollection1, dataRecordCollection2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataRecordCollection> dataRecordCollections1,
		List<DataRecordCollection> dataRecordCollections2) {

		Assert.assertEquals(
			dataRecordCollections1.size(), dataRecordCollections2.size());

		for (DataRecordCollection dataRecordCollection1 :
				dataRecordCollections1) {

			boolean contains = false;

			for (DataRecordCollection dataRecordCollection2 :
					dataRecordCollections2) {

				if (equals(dataRecordCollection1, dataRecordCollection2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataRecordCollections2 + " does not contain " +
					dataRecordCollection1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<DataRecordCollection> dataRecordCollections, JSONArray jsonArray) {

		for (DataRecordCollection dataRecordCollection :
				dataRecordCollections) {

			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(
						dataRecordCollection, (JSONObject)object)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + dataRecordCollection,
				contains);
		}
	}

	protected void assertValid(DataRecordCollection dataRecordCollection) {
		boolean valid = true;

		if (dataRecordCollection.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				dataRecordCollection.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (dataRecordCollection.getDataDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataRecordCollectionKey", additionalAssertFieldName)) {

				if (dataRecordCollection.getDataRecordCollectionKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataRecordCollection.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataRecordCollection.getName() == null) {
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

	protected void assertValid(Page<DataRecordCollection> page) {
		boolean valid = false;

		java.util.Collection<DataRecordCollection> dataRecordCollections =
			page.getItems();

		int size = dataRecordCollections.size();

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
		DataRecordCollection dataRecordCollection1,
		DataRecordCollection dataRecordCollection2) {

		if (dataRecordCollection1 == dataRecordCollection2) {
			return true;
		}

		if (!Objects.equals(
				dataRecordCollection1.getSiteId(),
				dataRecordCollection2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getDataDefinitionId(),
						dataRecordCollection2.getDataDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataRecordCollectionKey", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataRecordCollection1.getDataRecordCollectionKey(),
						dataRecordCollection2.getDataRecordCollectionKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getDescription(),
						dataRecordCollection2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getId(),
						dataRecordCollection2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection1.getName(),
						dataRecordCollection2.getName())) {

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
		DataRecordCollection dataRecordCollection, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("dataDefinitionId", fieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection.getDataDefinitionId(),
						jsonObject.getLong("dataDefinitionId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataRecordCollectionKey", fieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection.getDataRecordCollectionKey(),
						jsonObject.getString("dataRecordCollectionKey"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						dataRecordCollection.getId(),
						jsonObject.getLong("id"))) {

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

		if (!(_dataRecordCollectionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataRecordCollectionResource;

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
		DataRecordCollection dataRecordCollection) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataRecordCollectionKey")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					dataRecordCollection.getDataRecordCollectionKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
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

		if (entityFieldName.equals("siteId")) {
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

	protected DataRecordCollection randomDataRecordCollection()
		throws Exception {

		return new DataRecordCollection() {
			{
				dataDefinitionId = RandomTestUtil.randomLong();
				dataRecordCollectionKey = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected DataRecordCollection randomIrrelevantDataRecordCollection()
		throws Exception {

		DataRecordCollection randomIrrelevantDataRecordCollection =
			randomDataRecordCollection();

		randomIrrelevantDataRecordCollection.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantDataRecordCollection;
	}

	protected DataRecordCollection randomPatchDataRecordCollection()
		throws Exception {

		return randomDataRecordCollection();
	}

	protected DataRecordCollectionResource dataRecordCollectionResource;
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
		BaseDataRecordCollectionResourceTestCase.class);

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
		com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource
			_dataRecordCollectionResource;

}