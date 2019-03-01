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

import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.Options;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public abstract class BaseFormStructureResourceTestCase {

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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-form/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceFormStructuresPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceFormStructuresPage_getContentSpaceId();

		FormStructure formStructure1 =
			testGetContentSpaceFormStructuresPage_addFormStructure(
				contentSpaceId, randomFormStructure());
		FormStructure formStructure2 =
			testGetContentSpaceFormStructuresPage_addFormStructure(
				contentSpaceId, randomFormStructure());

		Page<FormStructure> page = invokeGetContentSpaceFormStructuresPage(
			contentSpaceId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(formStructure1, formStructure2),
			(List<FormStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceFormStructuresPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceFormStructuresPage_getContentSpaceId();

		FormStructure formStructure1 =
			testGetContentSpaceFormStructuresPage_addFormStructure(
				contentSpaceId, randomFormStructure());
		FormStructure formStructure2 =
			testGetContentSpaceFormStructuresPage_addFormStructure(
				contentSpaceId, randomFormStructure());
		FormStructure formStructure3 =
			testGetContentSpaceFormStructuresPage_addFormStructure(
				contentSpaceId, randomFormStructure());

		Page<FormStructure> page1 = invokeGetContentSpaceFormStructuresPage(
			contentSpaceId, Pagination.of(2, 1));

		List<FormStructure> formStructures1 =
			(List<FormStructure>)page1.getItems();

		Assert.assertEquals(
			formStructures1.toString(), 2, formStructures1.size());

		Page<FormStructure> page2 = invokeGetContentSpaceFormStructuresPage(
			contentSpaceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<FormStructure> formStructures2 =
			(List<FormStructure>)page2.getItems();

		Assert.assertEquals(
			formStructures2.toString(), 1, formStructures2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(formStructure1, formStructure2, formStructure3),
			new ArrayList<FormStructure>() {
				{
					addAll(formStructures1);
					addAll(formStructures2);
				}
			});
	}

	@Test
	public void testGetFormStructure() throws Exception {
		FormStructure postFormStructure =
			testGetFormStructure_addFormStructure();

		FormStructure getFormStructure = invokeGetFormStructure(
			postFormStructure.getId());

		assertEquals(postFormStructure, getFormStructure);
		assertValid(getFormStructure);
	}

	protected void assertEquals(
		FormStructure formStructure1, FormStructure formStructure2) {

		Assert.assertTrue(
			formStructure1 + " does not equal " + formStructure2,
			equals(formStructure1, formStructure2));
	}

	protected void assertEquals(
		List<FormStructure> formStructures1,
		List<FormStructure> formStructures2) {

		Assert.assertEquals(formStructures1.size(), formStructures2.size());

		for (int i = 0; i < formStructures1.size(); i++) {
			FormStructure formStructure1 = formStructures1.get(i);
			FormStructure formStructure2 = formStructures2.get(i);

			assertEquals(formStructure1, formStructure2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<FormStructure> formStructures1,
		List<FormStructure> formStructures2) {

		Assert.assertEquals(formStructures1.size(), formStructures2.size());

		for (FormStructure formStructure1 : formStructures1) {
			boolean contains = false;

			for (FormStructure formStructure2 : formStructures2) {
				if (equals(formStructure1, formStructure2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				formStructures2 + " does not contain " + formStructure1,
				contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(FormStructure formStructure) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<FormStructure> page) {
		boolean valid = false;

		Collection<FormStructure> formStructures = page.getItems();

		int size = formStructures.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		FormStructure formStructure1, FormStructure formStructure2) {

		if (formStructure1 == formStructure2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_formStructureResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_formStructureResource;

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
		EntityField entityField, String operator, FormStructure formStructure) {

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

		if (entityFieldName.equals("contentSpace")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(formStructure.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(formStructure.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(formStructure.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("formPages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(formStructure.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("successPage")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Page<FormStructure> invokeGetContentSpaceFormStructuresPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/form-structures",
					contentSpaceId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<FormStructure>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceFormStructuresPageResponse(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/form-structures",
					contentSpaceId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected FormStructure invokeGetFormStructure(Long formStructureId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/form-structures/{form-structure-id}", formStructureId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), FormStructure.class);
	}

	protected Http.Response invokeGetFormStructureResponse(Long formStructureId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/form-structures/{form-structure-id}", formStructureId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected FormStructure randomFormStructure() {
		return new FormStructure() {
			{
				contentSpace = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected FormStructure
			testGetContentSpaceFormStructuresPage_addFormStructure(
				Long contentSpaceId, FormStructure formStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceFormStructuresPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected FormStructure testGetFormStructure_addFormStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

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

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	@Inject
	private FormStructureResource _formStructureResource;

	private URL _resourceURL;

}