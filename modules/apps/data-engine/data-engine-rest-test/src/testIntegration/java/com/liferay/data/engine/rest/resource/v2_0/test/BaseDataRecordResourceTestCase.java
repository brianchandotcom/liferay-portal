/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.function.transform.TransformUtil;
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

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

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

		dataRecordResource = builder.authentication(
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
		Long dataDefinitionId =
			testGetDataDefinitionDataRecordsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionDataRecordsPage_getIrrelevantDataDefinitionId();

		Page<DataRecord> page =
			dataRecordResource.getDataDefinitionDataRecordsPage(
				dataDefinitionId, null, RandomTestUtil.randomString(),
				Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantDataDefinitionId != null) {
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
			assertValid(
				page,
				testGetDataDefinitionDataRecordsPage_getExpectedActions(
					irrelevantDataDefinitionId));
		}

		DataRecord dataRecord1 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		DataRecord dataRecord2 =
			testGetDataDefinitionDataRecordsPage_addDataRecord(
				dataDefinitionId, randomDataRecord());

		page = dataRecordResource.getDataDefinitionDataRecordsPage(
			dataDefinitionId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2),
			(List<DataRecord>)page.getItems());
		assertValid(
			page,
			testGetDataDefinitionDataRecordsPage_getExpectedActions(
				dataDefinitionId));

		dataRecordResource.deleteDataRecord(dataRecord1.getId());

		dataRecordResource.deleteDataRecord(dataRecord2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDataDefinitionDataRecordsPage_getExpectedActions(
				Long dataDefinitionId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/data-engine/v2.0/data-definitions/{dataDefinitionId}/data-records/batch".
				replace(
					"{dataDefinitionId}", String.valueOf(dataDefinitionId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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
				BeanTestUtil.setProperty(
					dataRecord1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithSortDouble()
		throws Exception {

		testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanTestUtil.setProperty(
					dataRecord1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					dataRecord2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithSortInteger()
		throws Exception {

		testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanTestUtil.setProperty(dataRecord1, entityField.getName(), 0);
				BeanTestUtil.setProperty(dataRecord2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataDefinitionDataRecordsPageWithSortString()
		throws Exception {

		testGetDataDefinitionDataRecordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, dataRecord1, dataRecord2) -> {
				Class<?> clazz = dataRecord1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						dataRecord1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						dataRecord2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						dataRecord1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						dataRecord2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						dataRecord1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						dataRecord2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
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
		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId();
		Long irrelevantDataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getIrrelevantDataRecordCollectionId();

		Page<DataRecord> page =
			dataRecordResource.getDataRecordCollectionDataRecordsPage(
				dataRecordCollectionId, null, RandomTestUtil.randomString(),
				Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantDataRecordCollectionId != null) {
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
			assertValid(
				page,
				testGetDataRecordCollectionDataRecordsPage_getExpectedActions(
					irrelevantDataRecordCollectionId));
		}

		DataRecord dataRecord1 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		DataRecord dataRecord2 =
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				dataRecordCollectionId, randomDataRecord());

		page = dataRecordResource.getDataRecordCollectionDataRecordsPage(
			dataRecordCollectionId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2),
			(List<DataRecord>)page.getItems());
		assertValid(
			page,
			testGetDataRecordCollectionDataRecordsPage_getExpectedActions(
				dataRecordCollectionId));

		dataRecordResource.deleteDataRecord(dataRecord1.getId());

		dataRecordResource.deleteDataRecord(dataRecord2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDataRecordCollectionDataRecordsPage_getExpectedActions(
				Long dataRecordCollectionId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		Map createBatchAction = new HashMap<>();
		createBatchAction.put("method", "POST");
		createBatchAction.put(
			"href",
			"http://localhost:8080/o/data-engine/v2.0/data-record-collections/{dataRecordCollectionId}/data-records/batch".
				replace(
					"{dataRecordCollectionId}",
					String.valueOf(dataRecordCollectionId)));

		expectedActions.put("createBatch", createBatchAction);

		return expectedActions;
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
				BeanTestUtil.setProperty(
					dataRecord1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithSortDouble()
		throws Exception {

		testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanTestUtil.setProperty(
					dataRecord1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					dataRecord2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithSortInteger()
		throws Exception {

		testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataRecord1, dataRecord2) -> {
				BeanTestUtil.setProperty(dataRecord1, entityField.getName(), 0);
				BeanTestUtil.setProperty(dataRecord2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPageWithSortString()
		throws Exception {

		testGetDataRecordCollectionDataRecordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, dataRecord1, dataRecord2) -> {
				Class<?> clazz = dataRecord1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						dataRecord1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						dataRecord2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						dataRecord1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						dataRecord2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						dataRecord1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						dataRecord2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
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
		@SuppressWarnings("PMD.UnusedLocalVariable")
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
		DataRecord dataRecord = testGraphQLDeleteDataRecord_addDataRecord();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDataRecord",
						new HashMap<String, Object>() {
							{
								put("dataRecordId", dataRecord.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDataRecord"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"dataRecord",
					new HashMap<String, Object>() {
						{
							put("dataRecordId", dataRecord.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected DataRecord testGraphQLDeleteDataRecord_addDataRecord()
		throws Exception {

		return testGraphQLDataRecord_addDataRecord();
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
		DataRecord dataRecord = testGraphQLGetDataRecord_addDataRecord();

		Assert.assertTrue(
			equals(
				dataRecord,
				DataRecordSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"dataRecord",
								new HashMap<String, Object>() {
									{
										put("dataRecordId", dataRecord.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/dataRecord"))));
	}

	@Test
	public void testGraphQLGetDataRecordNotFound() throws Exception {
		Long irrelevantDataRecordId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"dataRecord",
						new HashMap<String, Object>() {
							{
								put("dataRecordId", irrelevantDataRecordId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected DataRecord testGraphQLGetDataRecord_addDataRecord()
		throws Exception {

		return testGraphQLDataRecord_addDataRecord();
	}

	@Test
	public void testPatchDataRecord() throws Exception {
		DataRecord postDataRecord = testPatchDataRecord_addDataRecord();

		DataRecord randomPatchDataRecord = randomPatchDataRecord();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataRecord patchDataRecord = dataRecordResource.patchDataRecord(
			postDataRecord.getId(), randomPatchDataRecord);

		DataRecord expectedPatchDataRecord = postDataRecord.clone();

		BeanTestUtil.copyProperties(
			randomPatchDataRecord, expectedPatchDataRecord);

		DataRecord getDataRecord = dataRecordResource.getDataRecord(
			patchDataRecord.getId());

		assertEquals(expectedPatchDataRecord, getDataRecord);
		assertValid(getDataRecord);
	}

	protected DataRecord testPatchDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected void assertContains(
		DataRecord dataRecord, List<DataRecord> dataRecords) {

		boolean contains = false;

		for (DataRecord item : dataRecords) {
			if (equals(dataRecord, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			dataRecords + " does not contain " + dataRecord, contains);
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

	protected void assertValid(DataRecord dataRecord) throws Exception {
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

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (dataRecord.getStatus() == null) {
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
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<DataRecord> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<DataRecord> dataRecords = page.getItems();

		int size = dataRecords.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.data.engine.rest.dto.v2_0.DataRecord.class)) {

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
				if (!equals(
						(Map)dataRecord1.getDataRecordValues(),
						(Map)dataRecord2.getDataRecordValues())) {

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

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataRecord1.getStatus(), dataRecord2.getStatus())) {

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

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
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

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
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

		if (entityFieldName.equals("status")) {
			sb.append(String.valueOf(dataRecord.getStatus()));

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

	protected DataRecord randomDataRecord() throws Exception {
		return new DataRecord() {
			{
				dataRecordCollectionId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				status = RandomTestUtil.randomInt();
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

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

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
		LogFactoryUtil.getLog(BaseDataRecordResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.data.engine.rest.resource.v2_0.DataRecordResource
		_dataRecordResource;

}