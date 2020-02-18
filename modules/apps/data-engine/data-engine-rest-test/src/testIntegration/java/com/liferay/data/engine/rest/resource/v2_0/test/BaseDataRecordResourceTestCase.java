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

import com.liferay.data.engine.rest.client.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.resource.v2_0.DataRecordResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataRecordSerDes;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataRecordResourceTestCase {

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

		_dataRecordResource.setContextCompany(testCompany);

		DataRecordResource.Builder builder = DataRecordResource.builder();

		dataRecordResource = builder.locale(
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

		DataRecord dataRecord1 = randomDataRecord();

		String json = objectMapper.writeValueAsString(dataRecord1);

		DataRecord dataRecord2 = DataRecordSerDes.toDTO(json);

		Assert.assertTrue(equals(dataRecord1, dataRecord2));
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

		DataRecord dataRecord = randomDataRecord();

		String json1 = objectMapper.writeValueAsString(dataRecord);
		String json2 = DataRecordSerDes.toJSON(dataRecord);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataRecord dataRecord = randomDataRecord();

		String json = DataRecordSerDes.toJSON(dataRecord);

		Assert.assertFalse(json.contains(regex));

		dataRecord = DataRecordSerDes.toDTO(json);
	}

	@Test
	public void testGetDataDefinitionDataRecordsPage() throws Exception {
		Page<DataRecord> page =
			dataRecordResource.getDataDefinitionDataRecordsPage(
				testGetDataDefinitionDataRecordsPage_getDataDefinitionId(),
				null, RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataRecordsPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			DataRecord irrelevantDataRecord =
				testGetDataDefinitionDataRecordsPage_addDataRecord(
					irrelevantDataDefinitionId, randomIrrelevantDataRecord());

			page = dataRecordResource.getDataDefinitionDataRecordsPage(
				irrelevantDataDefinitionId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataRecord),
				(List<DataRecord>)page.getItems());
			assertValid(page);
		}

		DataRecord dataRecord1 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		DataRecord dataRecord2 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		page = dataRecordResource.getDataDefinitionDataRecordsPage(
			dataDefinitionId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2),
			(List<DataRecord>)page.getItems());
		assertValid(page);

		dataRecordResource.deleteDataRecord(dataRecord1.getId());

		dataRecordResource.deleteDataRecord(dataRecord2.getId());
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithPagination()
		throws Exception {

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordsPage_getDataDefinitionId();

		DataRecord dataRecord1 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		DataRecord dataRecord2 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		DataRecord dataRecord3 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		Page<DataRecord> page1 =
			dataRecordResource.getDataDefinitionDataRecordsPage(
				dataDefinitionId, null, null, Pagination.of(1, 2), null);

		List<DataRecord> dataRecords1 = (List<DataRecord>)page1.getItems();

		Assert.assertEquals(dataRecords1.toString(), 2, dataRecords1.size());

		Page<DataRecord> page2 =
			dataRecordResource.getDataDefinitionDataRecordsPage(
				dataDefinitionId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataRecord> dataRecords2 = (List<DataRecord>)page2.getItems();

		Assert.assertEquals(dataRecords2.toString(), 1, dataRecords2.size());

		Page<DataRecord> page3 =
			dataRecordResource.getDataDefinitionDataRecordsPage(
				dataDefinitionId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2, dataRecord3),
			(List<DataRecord>)page3.getItems());
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithSortDateTime()
		throws Exception {

		testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanUtils.setProperty(
					dataRecord1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithSortInteger()
		throws Exception {

		testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanUtils.setProperty(dataRecord1, entityField.getName(), 0);
				BeanUtils.setProperty(dataRecord2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithSortString()
		throws Exception {

		testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, dataRecord1, dataRecord2) -> {
				Class<?> clazz = dataRecord1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						dataRecord1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						dataRecord2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						dataRecord1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						dataRecord2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, DataRecord, DataRecord, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long dataDefinitionId =
			testGetDataDefinitionDataRecordsPage_getDataDefinitionId();

		DataRecord dataRecord1 = randomDataRecord();
		DataRecord dataRecord2 = randomDataRecord();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, dataRecord1, dataRecord2);
		}

		dataRecord1 = testGetDataDefinitionDataRecordsPage_addDataRecord(
			dataDefinitionId, dataRecord1);

		dataRecord2 = testGetDataDefinitionDataRecordsPage_addDataRecord(
			dataDefinitionId, dataRecord2);

		for (EntityField entityField : entityFields) {
			Page<DataRecord> ascPage =
				dataRecordResource.getDataDefinitionDataRecordsPage(
					dataDefinitionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataRecord1, dataRecord2),
				(List<DataRecord>)ascPage.getItems());

			Page<DataRecord> descPage =
				dataRecordResource.getDataDefinitionDataRecordsPage(
					dataDefinitionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataRecord2, dataRecord1),
				(List<DataRecord>)descPage.getItems());
		}
	}

	protected DataRecord testGetDataDefinitionDataRecordsPage_addDataRecord(
			Long dataDefinitionId, DataRecord dataRecord)
		throws Exception {

		return dataRecordResource.postDataDefinitionDataRecord(
			dataDefinitionId, dataRecord);
	}

	protected Long testGetDataDefinitionDataRecordsPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataDefinitionDataRecordsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionDataRecord() throws Exception {
		DataRecord randomDataRecord = randomDataRecord();

		DataRecord postDataRecord =
			testPostDataDefinitionDataRecord_addDataRecord(randomDataRecord);

		assertEquals(randomDataRecord, postDataRecord);
		assertValid(postDataRecord);
	}

	protected DataRecord testPostDataDefinitionDataRecord_addDataRecord(
			DataRecord dataRecord)
		throws Exception {

		return dataRecordResource.postDataDefinitionDataRecord(
			testGetDataDefinitionDataRecordsPage_getDataDefinitionId(),
			dataRecord);
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPage() throws Exception {
		Page<DataRecord> page =
			dataRecordResource.getDataRecordCollectionDataRecordsPage(
				testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId(),
				null, RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId();
		Long irrelevantDataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getIrrelevantDataRecordCollectionId();

		if ((irrelevantDataRecordCollectionId != null)) {
			DataRecord irrelevantDataRecord =
				testGetDataRecordCollectionDataRecordsPage_addDataRecord(
					irrelevantDataRecordCollectionId,
					randomIrrelevantDataRecord());

			page = dataRecordResource.getDataRecordCollectionDataRecordsPage(
				irrelevantDataRecordCollectionId, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataRecord),
				(List<DataRecord>)page.getItems());
			assertValid(page);
		}

		DataRecord dataRecord1 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		DataRecord dataRecord2 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		page = dataRecordResource.getDataRecordCollectionDataRecordsPage(
			dataRecordCollectionId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2),
			(List<DataRecord>)page.getItems());
		assertValid(page);

		dataRecordResource.deleteDataRecord(dataRecord1.getId());

		dataRecordResource.deleteDataRecord(dataRecord2.getId());
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithPagination()
		throws Exception {

		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId();

		DataRecord dataRecord1 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		DataRecord dataRecord2 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		DataRecord dataRecord3 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		Page<DataRecord> page1 =
			dataRecordResource.getDataRecordCollectionDataRecordsPage(
				dataRecordCollectionId, null, null, Pagination.of(1, 2), null);

		List<DataRecord> dataRecords1 = (List<DataRecord>)page1.getItems();

		Assert.assertEquals(dataRecords1.toString(), 2, dataRecords1.size());

		Page<DataRecord> page2 =
			dataRecordResource.getDataRecordCollectionDataRecordsPage(
				dataRecordCollectionId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataRecord> dataRecords2 = (List<DataRecord>)page2.getItems();

		Assert.assertEquals(dataRecords2.toString(), 1, dataRecords2.size());

		Page<DataRecord> page3 =
			dataRecordResource.getDataRecordCollectionDataRecordsPage(
				dataRecordCollectionId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2, dataRecord3),
			(List<DataRecord>)page3.getItems());
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithSortDateTime()
		throws Exception {

		testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanUtils.setProperty(
					dataRecord1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithSortInteger()
		throws Exception {

		testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanUtils.setProperty(dataRecord1, entityField.getName(), 0);
				BeanUtils.setProperty(dataRecord2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithSortString()
		throws Exception {

		testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, dataRecord1, dataRecord2) -> {
				Class<?> clazz = dataRecord1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						dataRecord1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						dataRecord2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						dataRecord1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						dataRecord2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, DataRecord, DataRecord, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId();

		DataRecord dataRecord1 = randomDataRecord();
		DataRecord dataRecord2 = randomDataRecord();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, dataRecord1, dataRecord2);
		}

		dataRecord1 = testGetDataRecordCollectionDataRecordsPage_addDataRecord(
			dataRecordCollectionId, dataRecord1);

		dataRecord2 = testGetDataRecordCollectionDataRecordsPage_addDataRecord(
			dataRecordCollectionId, dataRecord2);

		for (EntityField entityField : entityFields) {
			Page<DataRecord> ascPage =
				dataRecordResource.getDataRecordCollectionDataRecordsPage(
					dataRecordCollectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataRecord1, dataRecord2),
				(List<DataRecord>)ascPage.getItems());

			Page<DataRecord> descPage =
				dataRecordResource.getDataRecordCollectionDataRecordsPage(
					dataRecordCollectionId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataRecord2, dataRecord1),
				(List<DataRecord>)descPage.getItems());
		}
	}

	protected DataRecord
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		return dataRecordResource.postDataRecordCollectionDataRecord(
			dataRecordCollectionId, dataRecord);
	}

	protected Long
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDataRecordCollectionDataRecordsPage_getIrrelevantDataRecordCollectionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataRecordCollectionDataRecord() throws Exception {
		DataRecord randomDataRecord = randomDataRecord();

		DataRecord postDataRecord =
			testPostDataRecordCollectionDataRecord_addDataRecord(
				randomDataRecord);

		assertEquals(randomDataRecord, postDataRecord);
		assertValid(postDataRecord);
	}

	protected DataRecord testPostDataRecordCollectionDataRecord_addDataRecord(
			DataRecord dataRecord)
		throws Exception {

		return dataRecordResource.postDataRecordCollectionDataRecord(
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId(),
			dataRecord);
	}

	@Test
	public void testGetDataRecordCollectionDataRecordExport() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteDataRecord() throws Exception {
		DataRecord dataRecord = testDeleteDataRecord_addDataRecord();

		assertHttpResponseStatusCode(
			204,
			dataRecordResource.deleteDataRecordHttpResponse(
				dataRecord.getId()));

		assertHttpResponseStatusCode(
			404,
			dataRecordResource.getDataRecordHttpResponse(dataRecord.getId()));

		assertHttpResponseStatusCode(
			404, dataRecordResource.getDataRecordHttpResponse(0L));
	}

	protected DataRecord testDeleteDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDataRecord() throws Exception {
		DataRecord dataRecord = testGraphQLDataRecord_addDataRecord();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteDataRecord",
				new HashMap<String, Object>() {
					{
						put("dataRecordId", dataRecord.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteDataRecord"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"dataRecord",
					new HashMap<String, Object>() {
						{
							put("dataRecordId", dataRecord.getId());
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
	public void testGetDataRecord() throws Exception {
		DataRecord postDataRecord = testGetDataRecord_addDataRecord();

		DataRecord getDataRecord = dataRecordResource.getDataRecord(
			postDataRecord.getId());

		assertEquals(postDataRecord, getDataRecord);
		assertValid(getDataRecord);
	}

	protected DataRecord testGetDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDataRecord() throws Exception {
		DataRecord dataRecord = testGraphQLDataRecord_addDataRecord();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataRecord",
				new HashMap<String, Object>() {
					{
						put("dataRecordId", dataRecord.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataRecord, dataJSONObject.getJSONObject("dataRecord")));
	}

	@Test
	public void testPutDataRecord() throws Exception {
		DataRecord postDataRecord = testPutDataRecord_addDataRecord();

		DataRecord randomDataRecord = randomDataRecord();

		DataRecord putDataRecord = dataRecordResource.putDataRecord(
			postDataRecord.getId(), randomDataRecord);

		assertEquals(randomDataRecord, putDataRecord);
		assertValid(putDataRecord);

		DataRecord getDataRecord = dataRecordResource.getDataRecord(
			putDataRecord.getId());

		assertEquals(randomDataRecord, getDataRecord);
		assertValid(getDataRecord);
	}

	protected DataRecord testPutDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataRecord testGraphQLDataRecord_addDataRecord()
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
		DataRecord dataRecord1, DataRecord dataRecord2) {

		Assert.assertTrue(
			dataRecord1 + " does not equal " + dataRecord2,
			equals(dataRecord1, dataRecord2));
	}

	protected void assertEquals(
		List<DataRecord> dataRecords1, List<DataRecord> dataRecords2) {

		Assert.assertEquals(dataRecords1.size(), dataRecords2.size());

		for (int i = 0; i < dataRecords1.size(); i++) {
			DataRecord dataRecord1 = dataRecords1.get(i);
			DataRecord dataRecord2 = dataRecords2.get(i);

			assertEquals(dataRecord1, dataRecord2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataRecord> dataRecords1, List<DataRecord> dataRecords2) {

		Assert.assertEquals(dataRecords1.size(), dataRecords2.size());

		for (DataRecord dataRecord1 : dataRecords1) {
			boolean contains = false;

			for (DataRecord dataRecord2 : dataRecords2) {
				if (equals(dataRecord1, dataRecord2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataRecords2 + " does not contain " + dataRecord1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<DataRecord> dataRecords, JSONArray jsonArray) {

		for (DataRecord dataRecord : dataRecords) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(dataRecord, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + dataRecord, contains);
		}
	}

	protected void assertValid(DataRecord dataRecord) {
		boolean valid = true;

		if (dataRecord.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dataRecordCollectionId", additionalAssertFieldName)) {

				if (dataRecord.getDataRecordCollectionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataRecordValues", additionalAssertFieldName)) {
				if (dataRecord.getDataRecordValues() == null) {
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

	protected void assertValid(Page<DataRecord> page) {
		boolean valid = false;

		java.util.Collection<DataRecord> dataRecords = page.getItems();

		int size = dataRecords.size();

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

	protected boolean equals(DataRecord dataRecord1, DataRecord dataRecord2) {
		if (dataRecord1 == dataRecord2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dataRecordCollectionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataRecord1.getDataRecordCollectionId(),
						dataRecord2.getDataRecordCollectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataRecordValues", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecord1.getDataRecordValues(),
						dataRecord2.getDataRecordValues())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecord1.getId(), dataRecord2.getId())) {

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
		DataRecord dataRecord, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("dataRecordCollectionId", fieldName)) {
				if (!Objects.deepEquals(
						dataRecord.getDataRecordCollectionId(),
						jsonObject.getLong("dataRecordCollectionId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						dataRecord.getId(), jsonObject.getLong("id"))) {

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

		if (!(_dataRecordResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataRecordResource;

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
		EntityField entityField, String operator, DataRecord dataRecord) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dataRecordCollectionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataRecordValues")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
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

	protected DataRecord randomDataRecord() throws Exception {
		return new DataRecord() {
			{
				dataRecordCollectionId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataRecord randomIrrelevantDataRecord() throws Exception {
		DataRecord randomIrrelevantDataRecord = randomDataRecord();

		return randomIrrelevantDataRecord;
	}

	protected DataRecord randomPatchDataRecord() throws Exception {
		return randomDataRecord();
	}

	protected DataRecordResource dataRecordResource;
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
		BaseDataRecordResourceTestCase.class);

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
	private com.liferay.data.engine.rest.resource.v2_0.DataRecordResource
		_dataRecordResource;

}