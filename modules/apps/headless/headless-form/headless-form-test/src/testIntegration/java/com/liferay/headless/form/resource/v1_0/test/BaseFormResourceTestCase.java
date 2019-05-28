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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.form.client.dto.v1_0.Form;
import com.liferay.headless.form.client.http.HttpInvoker;
import com.liferay.headless.form.client.pagination.Page;
import com.liferay.headless.form.client.pagination.Pagination;
import com.liferay.headless.form.client.resource.v1_0.FormResource;
import com.liferay.headless.form.client.serdes.v1_0.FormSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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
public abstract class BaseFormResourceTestCase {

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

		Form form1 = randomForm();

		String json = objectMapper.writeValueAsString(form1);

		Form form2 = FormSerDes.toDTO(json);

		Assert.assertTrue(equals(form1, form2));
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

		Form form = randomForm();

		String json1 = objectMapper.writeValueAsString(form);
		String json2 = FormSerDes.toJSON(form);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetForm() throws Exception {
		Form postForm = testGetForm_addForm();

		Form getForm = FormResource.getForm(postForm.getId());

		assertEquals(postForm, getForm);
		assertValid(getForm);
	}

	protected Form testGetForm_addForm() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostFormEvaluateContext() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFormFormDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetSiteFormsPage() throws Exception {
		Long siteId = testGetSiteFormsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteFormsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Form irrelevantForm = testGetSiteFormsPage_addForm(
				irrelevantSiteId, randomIrrelevantForm());

			Page<Form> page = FormResource.getSiteFormsPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantForm), (List<Form>)page.getItems());
			assertValid(page);
		}

		Form form1 = testGetSiteFormsPage_addForm(siteId, randomForm());

		Form form2 = testGetSiteFormsPage_addForm(siteId, randomForm());

		Page<Form> page = FormResource.getSiteFormsPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(form1, form2), (List<Form>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteFormsPageWithPagination() throws Exception {
		Long siteId = testGetSiteFormsPage_getSiteId();

		Form form1 = testGetSiteFormsPage_addForm(siteId, randomForm());

		Form form2 = testGetSiteFormsPage_addForm(siteId, randomForm());

		Form form3 = testGetSiteFormsPage_addForm(siteId, randomForm());

		Page<Form> page1 = FormResource.getSiteFormsPage(
			siteId, Pagination.of(1, 2));

		List<Form> forms1 = (List<Form>)page1.getItems();

		Assert.assertEquals(forms1.toString(), 2, forms1.size());

		Page<Form> page2 = FormResource.getSiteFormsPage(
			siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Form> forms2 = (List<Form>)page2.getItems();

		Assert.assertEquals(forms2.toString(), 1, forms2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(form1, form2, form3),
			new ArrayList<Form>() {
				{
					addAll(forms1);
					addAll(forms2);
				}
			});
	}

	protected Form testGetSiteFormsPage_addForm(Long siteId, Form form)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteFormsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteFormsPage_getIrrelevantSiteId() throws Exception {
		return irrelevantGroup.getGroupId();
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Form form1, Form form2) {
		Assert.assertTrue(
			form1 + " does not equal " + form2, equals(form1, form2));
	}

	protected void assertEquals(List<Form> forms1, List<Form> forms2) {
		Assert.assertEquals(forms1.size(), forms2.size());

		for (int i = 0; i < forms1.size(); i++) {
			Form form1 = forms1.get(i);
			Form form2 = forms2.get(i);

			assertEquals(form1, form2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Form> forms1, List<Form> forms2) {

		Assert.assertEquals(forms1.size(), forms2.size());

		for (Form form1 : forms1) {
			boolean contains = false;

			for (Form form2 : forms2) {
				if (equals(form1, form2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(forms2 + " does not contain " + form1, contains);
		}
	}

	protected void assertValid(Form form) {
		boolean valid = true;

		if (form.getDateCreated() == null) {
			valid = false;
		}

		if (form.getDateModified() == null) {
			valid = false;
		}

		if (form.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(form.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (form.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (form.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (form.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("defaultLanguage", additionalAssertFieldName)) {
				if (form.getDefaultLanguage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (form.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formRecords", additionalAssertFieldName)) {
				if (form.getFormRecords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formRecordsIds", additionalAssertFieldName)) {
				if (form.getFormRecordsIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (form.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("structure", additionalAssertFieldName)) {
				if (form.getStructure() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("structureId", additionalAssertFieldName)) {
				if (form.getStructureId() == null) {
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

	protected void assertValid(Page<Form> page) {
		boolean valid = false;

		Collection<Form> forms = page.getItems();

		int size = forms.size();

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

	protected boolean equals(Form form1, Form form2) {
		if (form1 == form2) {
			return true;
		}

		if (!Objects.equals(form1.getSiteId(), form2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						form1.getAvailableLanguages(),
						form2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getCreator(), form2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getDateCreated(), form2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getDateModified(), form2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getDatePublished(), form2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultLanguage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getDefaultLanguage(),
						form2.getDefaultLanguage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getDescription(), form2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formRecords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getFormRecords(), form2.getFormRecords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formRecordsIds", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getFormRecordsIds(), form2.getFormRecordsIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(form1.getId(), form2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(form1.getName(), form2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("structure", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getStructure(), form2.getStructure())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("structureId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getStructureId(), form2.getStructureId())) {

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
		if (!(_formResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_formResource;

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
		EntityField entityField, String operator, Form form) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

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
						DateUtils.addSeconds(form.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(form.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(form.getDateCreated()));
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
						DateUtils.addSeconds(form.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(form.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(form.getDateModified()));
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
						DateUtils.addSeconds(form.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(form.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(form.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultLanguage")) {
			sb.append("'");
			sb.append(String.valueOf(form.getDefaultLanguage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(form.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("formRecords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("formRecordsIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(form.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("structure")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("structureId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Form randomForm() throws Exception {
		return new Form() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				defaultLanguage = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
				structureId = RandomTestUtil.randomLong();
			}
		};
	}

	protected Form randomIrrelevantForm() throws Exception {
		Form randomIrrelevantForm = randomForm();

		randomIrrelevantForm.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantForm;
	}

	protected Form randomPatchForm() throws Exception {
		return randomForm();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFormResourceTestCase.class);

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
	private com.liferay.headless.form.resource.v1_0.FormResource _formResource;

}