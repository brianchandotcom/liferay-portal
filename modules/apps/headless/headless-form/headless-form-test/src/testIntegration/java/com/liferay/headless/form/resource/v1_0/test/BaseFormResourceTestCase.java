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
import com.liferay.headless.form.client.dto.v1_0.FormContext;
import com.liferay.headless.form.client.dto.v1_0.FormDocument;
import com.liferay.headless.form.client.http.HttpInvoker;
import com.liferay.headless.form.client.pagination.Page;
import com.liferay.headless.form.client.pagination.Pagination;
import com.liferay.headless.form.client.resource.v1_0.FormResource;
import com.liferay.headless.form.client.serdes.v1_0.FormSerDes;
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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_formResource.setContextCompany(testCompany);

		FormResource.Builder builder = FormResource.builder();

		formResource = builder.locale(
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
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Form form = randomForm();

		form.setDefaultLanguage(regex);
		form.setDescription(regex);
		form.setName(regex);

		String json = FormSerDes.toJSON(form);

		Assert.assertFalse(json.contains(regex));

		form = FormSerDes.toDTO(json);

		Assert.assertEquals(regex, form.getDefaultLanguage());
		Assert.assertEquals(regex, form.getDescription());
		Assert.assertEquals(regex, form.getName());
	}

	@Test
	public void testGetForm() throws Exception {
		Form postForm = testGetForm_addForm();

		Form getForm = formResource.getForm(postForm.getId());

		assertEquals(postForm, getForm);
		assertValid(getForm);
	}

	protected Form testGetForm_addForm() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetForm() throws Exception {
		Form form = testGraphQLForm_addForm();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"form",
				new HashMap<String, Object>() {
					{
						put("formId", form.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(form, dataJSONObject.getJSONObject("form")));
	}

	@Test
	public void testGetSiteFormsPage() throws Exception {
		Page<Form> page = formResource.getSiteFormsPage(
			testGetSiteFormsPage_getSiteId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteFormsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteFormsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Form irrelevantForm = testGetSiteFormsPage_addForm(
				irrelevantSiteId, randomIrrelevantForm());

			page = formResource.getSiteFormsPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantForm), (List<Form>)page.getItems());
			assertValid(page);
		}

		Form form1 = testGetSiteFormsPage_addForm(siteId, randomForm());

		Form form2 = testGetSiteFormsPage_addForm(siteId, randomForm());

		page = formResource.getSiteFormsPage(siteId, Pagination.of(1, 2));

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

		Page<Form> page1 = formResource.getSiteFormsPage(
			siteId, Pagination.of(1, 2));

		List<Form> forms1 = (List<Form>)page1.getItems();

		Assert.assertEquals(forms1.toString(), 2, forms1.size());

		Page<Form> page2 = formResource.getSiteFormsPage(
			siteId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Form> forms2 = (List<Form>)page2.getItems();

		Assert.assertEquals(forms2.toString(), 1, forms2.size());

		Page<Form> page3 = formResource.getSiteFormsPage(
			siteId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(form1, form2, form3), (List<Form>)page3.getItems());
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

	@Test
	public void testGraphQLGetSiteFormsPage() throws Exception {
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
				"forms",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject formsJSONObject = dataJSONObject.getJSONObject("forms");

		Assert.assertEquals(0, formsJSONObject.get("totalCount"));

		Form form1 = testGraphQLForm_addForm();
		Form form2 = testGraphQLForm_addForm();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		formsJSONObject = dataJSONObject.getJSONObject("forms");

		Assert.assertEquals(2, formsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(form1, form2), formsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostFormEvaluateContext() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFormFormDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Form testGraphQLForm_addForm() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected void assertEquals(
		FormContext formContext1, FormContext formContext2) {

		Assert.assertTrue(
			formContext1 + " does not equal " + formContext2,
			equals(formContext1, formContext2));
	}

	protected void assertEquals(
		FormDocument formDocument1, FormDocument formDocument2) {

		Assert.assertTrue(
			formDocument1 + " does not equal " + formDocument2,
			equals(formDocument1, formDocument2));
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

	protected void assertEqualsJSONArray(
		List<Form> forms, JSONArray jsonArray) {

		for (Form form : forms) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(form, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + form, contains);
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

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (form.getDescription_i18n() == null) {
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

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (form.getName_i18n() == null) {
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

		java.util.Collection<Form> forms = page.getItems();

		int size = forms.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(FormContext formContext) {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalFormContextAssertFieldNames()) {

			if (Objects.equals("formFieldValues", additionalAssertFieldName)) {
				if (formContext.getFormFieldValues() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formPageContexts", additionalAssertFieldName)) {
				if (formContext.getFormPageContexts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("readOnly", additionalAssertFieldName)) {
				if (formContext.getReadOnly() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"showRequiredFieldsWarning", additionalAssertFieldName)) {

				if (formContext.getShowRequiredFieldsWarning() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("showSubmitButton", additionalAssertFieldName)) {
				if (formContext.getShowSubmitButton() == null) {
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

	protected void assertValid(FormDocument formDocument) {
		boolean valid = true;

		if (formDocument.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(formDocument.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalFormDocumentAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (formDocument.getContentUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (formDocument.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (formDocument.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (formDocument.getFileExtension() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("folderId", additionalAssertFieldName)) {
				if (formDocument.getFolderId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (formDocument.getSizeInBytes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (formDocument.getTitle() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalFormContextAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalFormDocumentAssertFieldNames() {
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

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getDescription_i18n(),
						form2.getDescription_i18n())) {

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

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						form1.getName_i18n(), form2.getName_i18n())) {

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

	protected boolean equals(
		FormContext formContext1, FormContext formContext2) {

		if (formContext1 == formContext2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalFormContextAssertFieldNames()) {

			if (Objects.equals("formFieldValues", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formContext1.getFormFieldValues(),
						formContext2.getFormFieldValues())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formPageContexts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formContext1.getFormPageContexts(),
						formContext2.getFormPageContexts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("readOnly", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formContext1.getReadOnly(),
						formContext2.getReadOnly())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"showRequiredFieldsWarning", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						formContext1.getShowRequiredFieldsWarning(),
						formContext2.getShowRequiredFieldsWarning())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("showSubmitButton", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formContext1.getShowSubmitButton(),
						formContext2.getShowSubmitButton())) {

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
		FormDocument formDocument1, FormDocument formDocument2) {

		if (formDocument1 == formDocument2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalFormDocumentAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getContentUrl(),
						formDocument2.getContentUrl())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getDescription(),
						formDocument2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getEncodingFormat(),
						formDocument2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getFileExtension(),
						formDocument2.getFileExtension())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("folderId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getFolderId(),
						formDocument2.getFolderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getId(), formDocument2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getSiteId(), formDocument2.getSiteId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getSizeInBytes(),
						formDocument2.getSizeInBytes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formDocument1.getTitle(), formDocument2.getTitle())) {

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

	protected boolean equalsJSONObject(Form form, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("defaultLanguage", fieldName)) {
				if (!Objects.deepEquals(
						form.getDefaultLanguage(),
						jsonObject.getString("defaultLanguage"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						form.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						form.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						form.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("structureId", fieldName)) {
				if (!Objects.deepEquals(
						form.getStructureId(),
						jsonObject.getLong("structureId"))) {

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

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

		if (entityFieldName.equals("name_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected FormContext randomFormContext() throws Exception {
		return new FormContext() {
			{
				readOnly = RandomTestUtil.randomBoolean();
				showRequiredFieldsWarning = RandomTestUtil.randomBoolean();
				showSubmitButton = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected FormDocument randomFormDocument() throws Exception {
		return new FormDocument() {
			{
				contentUrl = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				folderId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				siteId = RandomTestUtil.randomLong();
				sizeInBytes = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected FormResource formResource;
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