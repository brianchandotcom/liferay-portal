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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataRecordSerDes;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL("http://localhost:8080/o/data-engine/v1.0");
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
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		DataRecord dataRecord = randomDataRecord();

		String json1 = objectMapper.writeValueAsString(dataRecord);
		String json2 = DataRecordSerDes.toJSON(dataRecord);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetDataRecordCollectionDataRecordsPage() throws Exception {
		Long dataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId();
		Long irrelevantDataRecordCollectionId =
			testGetDataRecordCollectionDataRecordsPage_getIrrelevantDataRecordCollectionId();

		if ((irrelevantDataRecordCollectionId != null)) {
			DataRecord irrelevantDataRecord =
				testGetDataRecordCollectionDataRecordsPage_addDataRecord(
					irrelevantDataRecordCollectionId,
					randomIrrelevantDataRecord());

			Page<DataRecord> page =
				invokeGetDataRecordCollectionDataRecordsPage(
					irrelevantDataRecordCollectionId, Pagination.of(1, 2));

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

		Page<DataRecord> page = invokeGetDataRecordCollectionDataRecordsPage(
			dataRecordCollectionId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2),
			(List<DataRecord>)page.getItems());
		assertValid(page);
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

		Page<DataRecord> page1 = invokeGetDataRecordCollectionDataRecordsPage(
			dataRecordCollectionId, Pagination.of(1, 2));

		List<DataRecord> dataRecords1 = (List<DataRecord>)page1.getItems();

		Assert.assertEquals(dataRecords1.toString(), 2, dataRecords1.size());

		Page<DataRecord> page2 = invokeGetDataRecordCollectionDataRecordsPage(
			dataRecordCollectionId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataRecord> dataRecords2 = (List<DataRecord>)page2.getItems();

		Assert.assertEquals(dataRecords2.toString(), 1, dataRecords2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataRecord1, dataRecord2, dataRecord3),
			new ArrayList<DataRecord>() {
				{
					addAll(dataRecords1);
					addAll(dataRecords2);
				}
			});
	}

	protected DataRecord
			testGetDataRecordCollectionDataRecordsPage_addDataRecord(
				Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		return invokePostDataRecordCollectionDataRecord(
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

	protected Page<DataRecord> invokeGetDataRecordCollectionDataRecordsPage(
			Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-records",
					dataRecordCollectionId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, DataRecordSerDes::toDTO);
	}

	protected Http.Response
			invokeGetDataRecordCollectionDataRecordsPageResponse(
				Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-records",
					dataRecordCollectionId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

		return invokePostDataRecordCollectionDataRecord(
			testGetDataRecordCollectionDataRecordsPage_getDataRecordCollectionId(),
			dataRecord);
	}

	protected DataRecord invokePostDataRecordCollectionDataRecord(
			Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordSerDes.toJSON(dataRecord), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-records",
					dataRecordCollectionId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataRecordSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostDataRecordCollectionDataRecordResponse(
			Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordSerDes.toJSON(dataRecord), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-records",
					dataRecordCollectionId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDataRecordCollectionDataRecordExport() throws Exception {
		Assert.assertTrue(true);
	}

	protected String invokeGetDataRecordCollectionDataRecordExport(
			Long dataRecordCollectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-records/export",
					dataRecordCollectionId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return string;
	}

	protected Http.Response
			invokeGetDataRecordCollectionDataRecordExportResponse(
				Long dataRecordCollectionId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/data-record-collections/{dataRecordCollectionId}/data-records/export",
					dataRecordCollectionId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDataRecord() throws Exception {
		DataRecord dataRecord = testDeleteDataRecord_addDataRecord();

		assertResponseCode(
			204, invokeDeleteDataRecordResponse(dataRecord.getId()));

		assertResponseCode(
			404, invokeGetDataRecordResponse(dataRecord.getId()));
	}

	protected DataRecord testDeleteDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDataRecord(Long dataRecordId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/data-records/{dataRecordId}", dataRecordId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDataRecordResponse(Long dataRecordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/data-records/{dataRecordId}", dataRecordId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDataRecord() throws Exception {
		DataRecord postDataRecord = testGetDataRecord_addDataRecord();

		DataRecord getDataRecord = invokeGetDataRecord(postDataRecord.getId());

		assertEquals(postDataRecord, getDataRecord);
		assertValid(getDataRecord);
	}

	protected DataRecord testGetDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataRecord invokeGetDataRecord(Long dataRecordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/data-records/{dataRecordId}", dataRecordId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataRecordSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetDataRecordResponse(Long dataRecordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/data-records/{dataRecordId}", dataRecordId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDataRecord() throws Exception {
		DataRecord postDataRecord = testPutDataRecord_addDataRecord();

		DataRecord randomDataRecord = randomDataRecord();

		DataRecord putDataRecord = invokePutDataRecord(
			postDataRecord.getId(), randomDataRecord);

		assertEquals(randomDataRecord, putDataRecord);
		assertValid(putDataRecord);

		DataRecord getDataRecord = invokeGetDataRecord(putDataRecord.getId());

		assertEquals(randomDataRecord, getDataRecord);
		assertValid(getDataRecord);
	}

	protected DataRecord testPutDataRecord_addDataRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DataRecord invokePutDataRecord(
			Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordSerDes.toJSON(dataRecord), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/data-records/{dataRecordId}", dataRecordId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DataRecordSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutDataRecordResponse(
			Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DataRecordSerDes.toJSON(dataRecord), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/data-records/{dataRecordId}", dataRecordId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

		Collection<DataRecord> dataRecords = page.getItems();

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

	protected Collection<EntityField> getEntityFields() throws Exception {
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

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
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

	protected DataRecord randomDataRecord() {
		return new DataRecord() {
			{
				dataRecordCollectionId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataRecord randomIrrelevantDataRecord() {
		DataRecord randomIrrelevantDataRecord = randomDataRecord();

		return randomIrrelevantDataRecord;
	}

	protected DataRecord randomPatchDataRecord() {
		return randomDataRecord();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
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
	private DataRecordResource _dataRecordResource;

	private URL _resourceURL;

}