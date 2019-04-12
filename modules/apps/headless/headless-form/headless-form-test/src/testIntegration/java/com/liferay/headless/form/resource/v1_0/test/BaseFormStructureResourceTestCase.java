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

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.FormPage;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.SuccessPage;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
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
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
	public void testGetFormStructure() throws Exception {
		FormStructure postFormStructure =
			testGetFormStructure_addFormStructure();

		FormStructure getFormStructure = invokeGetFormStructure(
			postFormStructure.getId());

		assertEquals(postFormStructure, getFormStructure);
		assertValid(getFormStructure);
	}

	protected FormStructure testGetFormStructure_addFormStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected FormStructure invokeGetFormStructure(Long formStructureId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/form-structures/{formStructureId}", formStructureId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, FormStructure.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetFormStructureResponse(Long formStructureId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/form-structures/{formStructureId}", formStructureId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSiteFormStructuresPage() throws Exception {
		Long siteId = testGetSiteFormStructuresPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteFormStructuresPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			FormStructure irrelevantFormStructure =
				testGetSiteFormStructuresPage_addFormStructure(
					irrelevantSiteId, randomIrrelevantFormStructure());

			Page<FormStructure> page = invokeGetSiteFormStructuresPage(
				irrelevantSiteId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFormStructure),
				(List<FormStructure>)page.getItems());
			assertValid(page);
		}

		FormStructure formStructure1 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		FormStructure formStructure2 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		Page<FormStructure> page = invokeGetSiteFormStructuresPage(
			siteId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(formStructure1, formStructure2),
			(List<FormStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteFormStructuresPageWithPagination() throws Exception {
		Long siteId = testGetSiteFormStructuresPage_getSiteId();

		FormStructure formStructure1 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		FormStructure formStructure2 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		FormStructure formStructure3 =
			testGetSiteFormStructuresPage_addFormStructure(
				siteId, randomFormStructure());

		Page<FormStructure> page1 = invokeGetSiteFormStructuresPage(
			siteId, Pagination.of(1, 2));

		List<FormStructure> formStructures1 =
			(List<FormStructure>)page1.getItems();

		Assert.assertEquals(
			formStructures1.toString(), 2, formStructures1.size());

		Page<FormStructure> page2 = invokeGetSiteFormStructuresPage(
			siteId, Pagination.of(2, 2));

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

	protected FormStructure testGetSiteFormStructuresPage_addFormStructure(
			Long siteId, FormStructure formStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteFormStructuresPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteFormStructuresPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<FormStructure> invokeGetSiteFormStructuresPage(
			Long siteId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/form-structures", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<FormStructure>>() {
			});
	}

	protected Http.Response invokeGetSiteFormStructuresPageResponse(
			Long siteId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/form-structures", siteId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

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

	protected void assertValid(FormStructure formStructure) {
		boolean valid = true;

		if (formStructure.getDateCreated() == null) {
			valid = false;
		}

		if (formStructure.getDateModified() == null) {
			valid = false;
		}

		if (formStructure.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				formStructure.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (formStructure.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (formStructure.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (formStructure.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("formPages", additionalAssertFieldName)) {
				if (formStructure.getFormPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (formStructure.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("successPage", additionalAssertFieldName)) {
				if (formStructure.getSuccessPage() == null) {
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

	protected void assertValid(Page<FormStructure> page) {
		boolean valid = false;

		Collection<FormStructure> formStructures = page.getItems();

		int size = formStructures.size();

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
		FormStructure formStructure1, FormStructure formStructure2) {

		if (formStructure1 == formStructure2) {
			return true;
		}

		if (!Objects.equals(
				formStructure1.getSiteId(), formStructure2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						formStructure1.getAvailableLanguages(),
						formStructure2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getCreator(),
						formStructure2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getDateCreated(),
						formStructure2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getDateModified(),
						formStructure2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getDescription(),
						formStructure2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("formPages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getFormPages(),
						formStructure2.getFormPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getId(), formStructure2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getName(), formStructure2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("successPage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						formStructure1.getSuccessPage(),
						formStructure2.getSuccessPage())) {

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

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("successPage")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected FormStructure randomFormStructure() {
		return new FormStructure() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected FormStructure randomIrrelevantFormStructure() {
		FormStructure randomIrrelevantFormStructure = randomFormStructure();

		randomIrrelevantFormStructure.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantFormStructure;
	}

	protected FormStructure randomPatchFormStructure() {
		return randomFormStructure();
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
				addMixIn(FormStructure.class, FormStructureMixin.class);
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

	protected static class FormStructureMixin {

		@JsonProperty
		String[] availableLanguages;

		@JsonProperty
		Creator creator;

		@JsonProperty
		Date dateCreated;

		@JsonProperty
		Date dateModified;

		@JsonProperty
		String description;

		@JsonProperty
		FormPage[] formPages;

		@JsonProperty
		Long id;

		@JsonProperty
		String name;

		@JsonProperty
		Long siteId;

		@JsonProperty
		SuccessPage successPage;

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
		BaseFormStructureResourceTestCase.class);

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
	private FormStructureResource _formStructureResource;

	private URL _resourceURL;

}