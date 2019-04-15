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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.form.client.dto.v1_0.FormRecord;
import com.liferay.headless.form.client.pagination.Page;
import com.liferay.headless.form.client.serdes.v1_0.FormRecordSerDes;
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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL("http://localhost:8080/o/headless-form/v1.0");
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

		FormRecord formRecord1 = randomFormRecord();

		String json = objectMapper.writeValueAsString(formRecord1);

		FormRecord formRecord2 = FormRecordSerDes.toDTO(json);

		Assert.assertTrue(equals(formRecord1, formRecord2));
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

		FormRecord formRecord = randomFormRecord();

		String json1 = objectMapper.writeValueAsString(formRecord);
		String json2 = FormRecordSerDes.toJSON(formRecord);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
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
				_toPath("/form-records/{formRecordId}", formRecordId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return FormRecordSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetFormRecordResponse(Long formRecordId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/form-records/{formRecordId}", formRecordId);

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
			_resourceURL + _toPath("/forms/{formId}/form-records", formId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, FormRecordSerDes::toDTO);
	}

	protected Http.Response invokeGetFormFormRecordsPageResponse(
			Long formId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/forms/{formId}/form-records", formId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetFormFormRecordByLatestDraft() throws Exception {
		FormRecord postFormRecord =
			testGetFormFormRecordByLatestDraft_addFormRecord();

		FormRecord getFormRecord = invokeGetFormFormRecordByLatestDraft(
			postFormRecord.getFormId());

		assertEquals(postFormRecord, getFormRecord);
		assertValid(getFormRecord);
	}

	protected FormRecord testGetFormFormRecordByLatestDraft_addFormRecord()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected FormRecord invokeGetFormFormRecordByLatestDraft(Long formId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/forms/{formId}/form-records/by-latest-draft", formId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return FormRecordSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetFormFormRecordByLatestDraftResponse(
			Long formId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/forms/{formId}/form-records/by-latest-draft", formId);

		options.setLocation(location);

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
		boolean valid = true;

		if (formRecord.getDateCreated() == null) {
			valid = false;
		}

		if (formRecord.getDateModified() == null) {
			valid = false;
		}

		if (formRecord.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (formRecord.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (formRecord.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("draft", additionalAssertFieldName)) {
				if (formRecord.getDraft() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fieldValues", additionalAssertFieldName)) {
				if (formRecord.getFieldValues() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("form", additionalAssertFieldName)) {
				if (formRecord.getForm() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formId", additionalAssertFieldName)) {
				if (formRecord.getFormId() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(FormRecord formRecord1, FormRecord formRecord2) {
		if (formRecord1 == formRecord2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getCreator(), formRecord2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getDateCreated(),
						formRecord2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getDateModified(),
						formRecord2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getDatePublished(),
						formRecord2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("draft", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getDraft(), formRecord2.getDraft())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fieldValues", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getFieldValues(),
						formRecord2.getFieldValues())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("form", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getForm(), formRecord2.getForm())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getFormId(), formRecord2.getFormId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecord1.getId(), formRecord2.getId())) {

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
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(formRecord.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(formRecord.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(formRecord.getDateCreated()));
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
							formRecord.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(formRecord.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(formRecord.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("datePublished")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							formRecord.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							formRecord.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(formRecord.getDatePublished()));
			}

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
		FormRecord randomIrrelevantFormRecord = randomFormRecord();

		return randomIrrelevantFormRecord;
	}

	protected FormRecord randomPatchFormRecord() {
		return randomFormRecord();
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

	@Inject
	private FormRecordResource _formRecordResource;

	private URL _resourceURL;

}