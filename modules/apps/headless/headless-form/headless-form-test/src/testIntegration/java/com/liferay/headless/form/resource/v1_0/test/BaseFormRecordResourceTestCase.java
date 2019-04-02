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

package com.liferay.headless.form.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormRecordForm;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFormRecordResourceTestCase {

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

		_resourceURL = new URL("http://localhost:8080/o/headless-form/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetFormRecord() throws Exception {
		FormRecord postFormRecord = testGetFormRecord_addFormRecord();

		FormRecord getFormRecord = invokeGetFormRecord(postFormRecord.getId());

		assertEquals(postFormRecord, getFormRecord);
		assertValid(getFormRecord);
	}

	protected FormRecord testGetFormRecord_addFormRecord() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected FormRecord invokeGetFormRecord(Long formRecordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/form-records/{form-record-id}", formRecordId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, FormRecord.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetFormRecordResponse(Long formRecordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/form-records/{form-record-id}", formRecordId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetFormFetchLatestDraft() throws Exception {
		FormRecord postFormRecord = testGetFormFetchLatestDraft_addFormRecord();

		FormRecord getFormRecord = invokeGetFormFetchLatestDraft(
			postFormRecord.getFormId());

		assertEquals(postFormRecord, getFormRecord);
		assertValid(getFormRecord);
	}

	protected FormRecord testGetFormFetchLatestDraft_addFormRecord()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected FormRecord invokeGetFormFetchLatestDraft(Long formId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/forms/{form-id}/fetch-latest-draft", formId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, FormRecord.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetFormFetchLatestDraftResponse(Long formId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/forms/{form-id}/fetch-latest-draft", formId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetFormFormRecordsPage() throws Exception {
		Long formId = testGetFormFormRecordsPage_getFormId();
		Long irrelevantFormId =
			testGetFormFormRecordsPage_getIrrelevantFormId();

		if ((irrelevantFormId != null)) {
			FormRecord irrelevantFormRecord =
				testGetFormFormRecordsPage_addFormRecord(
					irrelevantFormId, randomIrrelevantFormRecord());

			Page<FormRecord> page = invokeGetFormFormRecordsPage(
				irrelevantFormId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFormRecord),
				(List<FormRecord>)page.getItems());
			assertValid(page);
		}

		FormRecord formRecord1 = testGetFormFormRecordsPage_addFormRecord(
			formId, randomFormRecord());

		FormRecord formRecord2 = testGetFormFormRecordsPage_addFormRecord(
			formId, randomFormRecord());

		Page<FormRecord> page = invokeGetFormFormRecordsPage(
			formId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(formRecord1, formRecord2),
			(List<FormRecord>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFormFormRecordsPageWithPagination() throws Exception {
		Long formId = testGetFormFormRecordsPage_getFormId();

		FormRecord formRecord1 = testGetFormFormRecordsPage_addFormRecord(
			formId, randomFormRecord());

		FormRecord formRecord2 = testGetFormFormRecordsPage_addFormRecord(
			formId, randomFormRecord());

		FormRecord formRecord3 = testGetFormFormRecordsPage_addFormRecord(
			formId, randomFormRecord());

		Page<FormRecord> page1 = invokeGetFormFormRecordsPage(
			formId, Pagination.of(1, 2));

		List<FormRecord> formRecords1 = (List<FormRecord>)page1.getItems();

		Assert.assertEquals(formRecords1.toString(), 2, formRecords1.size());

		Page<FormRecord> page2 = invokeGetFormFormRecordsPage(
			formId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<FormRecord> formRecords2 = (List<FormRecord>)page2.getItems();

		Assert.assertEquals(formRecords2.toString(), 1, formRecords2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(formRecord1, formRecord2, formRecord3),
			new ArrayList<FormRecord>() {
				{
					addAll(formRecords1);
					addAll(formRecords2);
				}
			});
	}

	protected FormRecord testGetFormFormRecordsPage_addFormRecord(
			Long formId, FormRecord formRecord)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetFormFormRecordsPage_getFormId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetFormFormRecordsPage_getIrrelevantFormId()
		throws Exception {

		return null;
	}

	protected Page<FormRecord> invokeGetFormFormRecordsPage(
			Long formId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/forms/{form-id}/form-records", formId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<FormRecord>>() {
			});
	}

	protected Http.Response invokeGetFormFormRecordsPageResponse(
			Long formId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/forms/{form-id}/form-records", formId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostFormFormRecord() throws Exception {
		FormRecord randomFormRecord = randomFormRecord();

		FormRecord postFormRecord = testPostFormFormRecord_addFormRecord(
			randomFormRecord);

		assertEquals(randomFormRecord, postFormRecord);
		assertValid(postFormRecord);
	}

	protected FormRecord testPostFormFormRecord_addFormRecord(
			FormRecord formRecord)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected FormRecord invokePostFormFormRecord(
			Long formId, FormRecordForm formRecordForm)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/forms/{form-id}/form-records", formId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, FormRecord.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostFormFormRecordResponse(
			Long formId, FormRecordForm formRecordForm)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/forms/{form-id}/form-records", formId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		FormRecord formRecord1, FormRecord formRecord2) {

		Assert.assertTrue(
			formRecord1 + " does not equal " + formRecord2,
			equals(formRecord1, formRecord2));
	}

	protected void assertEquals(
		List<FormRecord> formRecords1, List<FormRecord> formRecords2) {

		Assert.assertEquals(formRecords1.size(), formRecords2.size());

		for (int i = 0; i < formRecords1.size(); i++) {
			FormRecord formRecord1 = formRecords1.get(i);
			FormRecord formRecord2 = formRecords2.get(i);

			assertEquals(formRecord1, formRecord2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FormRecord> formRecords1, List<FormRecord> formRecords2) {

		Assert.assertEquals(formRecords1.size(), formRecords2.size());

		for (FormRecord formRecord1 : formRecords1) {
			boolean contains = false;

			for (FormRecord formRecord2 : formRecords2) {
				if (equals(formRecord1, formRecord2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				formRecords2 + " does not contain " + formRecord1, contains);
		}
	}

	protected void assertValid(FormRecord formRecord) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<FormRecord> page) {
		boolean valid = false;

		Collection<FormRecord> formRecords = page.getItems();

		int size = formRecords.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(FormRecord formRecord1, FormRecord formRecord2) {
		if (formRecord1 == formRecord2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_formRecordResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_formRecordResource;

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
		EntityField entityField, String operator, FormRecord formRecord) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(formRecord.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(formRecord.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			sb.append(_dateFormat.format(formRecord.getDatePublished()));

			return sb.toString();
		}

		if (entityFieldName.equals("draft")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("fieldValues")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("form")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("formId")) {
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

	protected FormRecord randomFormRecord() {
		return new FormRecord() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				draft = RandomTestUtil.randomBoolean();
				formId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected FormRecord randomIrrelevantFormRecord() {
		return randomFormRecord();
	}

	protected FormRecord randomPatchFormRecord() {
		return randomFormRecord();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
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
		BaseFormRecordResourceTestCase.class);

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
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	@Inject
	private FormRecordResource _formRecordResource;

	private URL _resourceURL;

}