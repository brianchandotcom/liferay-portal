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

import com.liferay.headless.form.client.dto.v1_0.FormDocument;
import com.liferay.headless.form.client.http.HttpInvoker;
import com.liferay.headless.form.client.pagination.Page;
import com.liferay.headless.form.client.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.client.serdes.v1_0.FormDocumentSerDes;
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
public abstract class BaseFormDocumentResourceTestCase {

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

		FormDocument formDocument1 = randomFormDocument();

		String json = objectMapper.writeValueAsString(formDocument1);

		FormDocument formDocument2 = FormDocumentSerDes.toDTO(json);

		Assert.assertTrue(equals(formDocument1, formDocument2));
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

		FormDocument formDocument = randomFormDocument();

		String json1 = objectMapper.writeValueAsString(formDocument);
		String json2 = FormDocumentSerDes.toJSON(formDocument);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testDeleteFormDocument() throws Exception {
		FormDocument formDocument = testDeleteFormDocument_addFormDocument();

		assertHttpResponseStatusCode(
			204,
			FormDocumentResource.deleteFormDocumentHttpResponse(
				formDocument.getId()));

		assertHttpResponseStatusCode(
			404,
			FormDocumentResource.getFormDocumentHttpResponse(
				formDocument.getId()));

		assertHttpResponseStatusCode(
			404, FormDocumentResource.getFormDocumentHttpResponse(0L));
	}

	protected FormDocument testDeleteFormDocument_addFormDocument()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetFormDocument() throws Exception {
		FormDocument postFormDocument = testGetFormDocument_addFormDocument();

		FormDocument getFormDocument = FormDocumentResource.getFormDocument(
			postFormDocument.getId());

		assertEquals(postFormDocument, getFormDocument);
		assertValid(getFormDocument);
	}

	protected FormDocument testGetFormDocument_addFormDocument()
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
		FormDocument formDocument1, FormDocument formDocument2) {

		Assert.assertTrue(
			formDocument1 + " does not equal " + formDocument2,
			equals(formDocument1, formDocument2));
	}

	protected void assertEquals(
		List<FormDocument> formDocuments1, List<FormDocument> formDocuments2) {

		Assert.assertEquals(formDocuments1.size(), formDocuments2.size());

		for (int i = 0; i < formDocuments1.size(); i++) {
			FormDocument formDocument1 = formDocuments1.get(i);
			FormDocument formDocument2 = formDocuments2.get(i);

			assertEquals(formDocument1, formDocument2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FormDocument> formDocuments1, List<FormDocument> formDocuments2) {

		Assert.assertEquals(formDocuments1.size(), formDocuments2.size());

		for (FormDocument formDocument1 : formDocuments1) {
			boolean contains = false;

			for (FormDocument formDocument2 : formDocuments2) {
				if (equals(formDocument1, formDocument2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				formDocuments2 + " does not contain " + formDocument1,
				contains);
		}
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
				getAdditionalAssertFieldNames()) {

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

	protected void assertValid(Page<FormDocument> page) {
		boolean valid = false;

		Collection<FormDocument> formDocuments = page.getItems();

		int size = formDocuments.size();

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
		FormDocument formDocument1, FormDocument formDocument2) {

		if (formDocument1 == formDocument2) {
			return true;
		}

		if (!Objects.equals(
				formDocument1.getSiteId(), formDocument2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

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

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_formDocumentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_formDocumentResource;

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
		EntityField entityField, String operator, FormDocument formDocument) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(formDocument.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(formDocument.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(formDocument.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(String.valueOf(formDocument.getFileExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("folderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(formDocument.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
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
				siteId = testGroup.getGroupId();
				sizeInBytes = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected FormDocument randomIrrelevantFormDocument() throws Exception {
		FormDocument randomIrrelevantFormDocument = randomFormDocument();

		randomIrrelevantFormDocument.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantFormDocument;
	}

	protected FormDocument randomPatchFormDocument() throws Exception {
		return randomFormDocument();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFormDocumentResourceTestCase.class);

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
	private com.liferay.headless.form.resource.v1_0.FormDocumentResource
		_formDocumentResource;

}