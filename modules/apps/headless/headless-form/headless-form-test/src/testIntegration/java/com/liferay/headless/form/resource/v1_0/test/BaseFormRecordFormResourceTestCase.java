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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.dto.v1_0.FormRecordForm;
import com.liferay.headless.form.resource.v1_0.FormRecordFormResource;
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
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
public abstract class BaseFormRecordFormResourceTestCase {

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
	public void testPutFormRecord() throws Exception {
		Assert.assertTrue(true);
	}

	protected FormRecord invokePutFormRecord(
			Long formRecordId, FormRecordForm formRecordForm)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(formRecordForm),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/form-records/{formRecordId}", formRecordId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, FormRecord.class);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutFormRecordResponse(
			Long formRecordId, FormRecordForm formRecordForm)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(formRecordForm),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/form-records/{formRecordId}", formRecordId);

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
		FormRecordForm formRecordForm1, FormRecordForm formRecordForm2) {

		Assert.assertTrue(
			formRecordForm1 + " does not equal " + formRecordForm2,
			equals(formRecordForm1, formRecordForm2));
	}

	protected void assertEquals(
		List<FormRecordForm> formRecordForms1,
		List<FormRecordForm> formRecordForms2) {

		Assert.assertEquals(formRecordForms1.size(), formRecordForms2.size());

		for (int i = 0; i < formRecordForms1.size(); i++) {
			FormRecordForm formRecordForm1 = formRecordForms1.get(i);
			FormRecordForm formRecordForm2 = formRecordForms2.get(i);

			assertEquals(formRecordForm1, formRecordForm2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FormRecordForm> formRecordForms1,
		List<FormRecordForm> formRecordForms2) {

		Assert.assertEquals(formRecordForms1.size(), formRecordForms2.size());

		for (FormRecordForm formRecordForm1 : formRecordForms1) {
			boolean contains = false;

			for (FormRecordForm formRecordForm2 : formRecordForms2) {
				if (equals(formRecordForm1, formRecordForm2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				formRecordForms2 + " does not contain " + formRecordForm1,
				contains);
		}
	}

	protected void assertValid(FormRecordForm formRecordForm) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("draft", additionalAssertFieldName)) {
				if (formRecordForm.getDraft() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fieldValues", additionalAssertFieldName)) {
				if (formRecordForm.getFieldValues() == null) {
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

	protected void assertValid(Page<FormRecordForm> page) {
		boolean valid = false;

		Collection<FormRecordForm> formRecordForms = page.getItems();

		int size = formRecordForms.size();

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

	protected boolean equals(
		FormRecordForm formRecordForm1, FormRecordForm formRecordForm2) {

		if (formRecordForm1 == formRecordForm2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("draft", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecordForm1.getDraft(),
						formRecordForm2.getDraft())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fieldValues", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formRecordForm1.getFieldValues(),
						formRecordForm2.getFieldValues())) {

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
		if (!(_formRecordFormResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_formRecordFormResource;

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
		EntityField entityField, String operator,
		FormRecordForm formRecordForm) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("draft")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("fieldValues")) {
			sb.append("'");
			sb.append(String.valueOf(formRecordForm.getFieldValues()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected FormRecordForm randomFormRecordForm() {
		return new FormRecordForm() {
			{
				draft = RandomTestUtil.randomBoolean();
				fieldValues = RandomTestUtil.randomString();
			}
		};
	}

	protected FormRecordForm randomIrrelevantFormRecordForm() {
		FormRecordForm randomIrrelevantFormRecordForm = randomFormRecordForm();

		return randomIrrelevantFormRecordForm;
	}

	protected FormRecordForm randomPatchFormRecordForm() {
		return randomFormRecordForm();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
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
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
			{
				addMixIn(FormRecordForm.class, FormRecordFormMixin.class);
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

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	protected static class FormRecordFormMixin {

		@JsonProperty
		Boolean draft;
		@JsonProperty
		String fieldValues;

	}

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
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

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
		BaseFormRecordFormResourceTestCase.class);

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
	private FormRecordFormResource _formRecordFormResource;

	private URL _resourceURL;

}