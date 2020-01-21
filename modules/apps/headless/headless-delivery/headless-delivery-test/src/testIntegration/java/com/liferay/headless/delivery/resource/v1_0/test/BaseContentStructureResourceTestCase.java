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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.ContentStructureResource;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentStructureSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
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
public abstract class BaseContentStructureResourceTestCase {

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

		_contentStructureResource.setContextCompany(testCompany);

		ContentStructureResource.Builder builder =
			ContentStructureResource.builder();

		contentStructureResource = builder.locale(
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

		ContentStructure contentStructure1 = randomContentStructure();

		String json = objectMapper.writeValueAsString(contentStructure1);

		ContentStructure contentStructure2 = ContentStructureSerDes.toDTO(json);

		Assert.assertTrue(equals(contentStructure1, contentStructure2));
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

		ContentStructure contentStructure = randomContentStructure();

		String json1 = objectMapper.writeValueAsString(contentStructure);
		String json2 = ContentStructureSerDes.toJSON(contentStructure);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ContentStructure contentStructure = randomContentStructure();

		contentStructure.setDescription(regex);
		contentStructure.setName(regex);

		String json = ContentStructureSerDes.toJSON(contentStructure);

		Assert.assertFalse(json.contains(regex));

		contentStructure = ContentStructureSerDes.toDTO(json);

		Assert.assertEquals(regex, contentStructure.getDescription());
		Assert.assertEquals(regex, contentStructure.getName());
	}

	@Test
	public void testGetContentStructure() throws Exception {
		ContentStructure postContentStructure =
			testGetContentStructure_addContentStructure();

		ContentStructure getContentStructure =
			contentStructureResource.getContentStructure(
				postContentStructure.getId());

		assertEquals(postContentStructure, getContentStructure);
		assertValid(getContentStructure);
	}

	protected ContentStructure testGetContentStructure_addContentStructure()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetContentStructure() throws Exception {
		ContentStructure contentStructure =
			testGraphQLContentStructure_addContentStructure();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"contentStructure",
				new HashMap<String, Object>() {
					{
						put("contentStructureId", contentStructure.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				contentStructure,
				dataJSONObject.getJSONObject("contentStructure")));
	}

	@Test
	public void testGetSiteContentStructuresPage() throws Exception {
		Page<ContentStructure> page =
			contentStructureResource.getSiteContentStructuresPage(
				testGetSiteContentStructuresPage_getSiteId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteContentStructuresPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentStructuresPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			ContentStructure irrelevantContentStructure =
				testGetSiteContentStructuresPage_addContentStructure(
					irrelevantSiteId, randomIrrelevantContentStructure());

			page = contentStructureResource.getSiteContentStructuresPage(
				irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentStructure),
				(List<ContentStructure>)page.getItems());
			assertValid(page);
		}

		ContentStructure contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		page = contentStructureResource.getSiteContentStructuresPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentStructure1, contentStructure2),
			(List<ContentStructure>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentStructuresPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 = randomContentStructure();

		contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, contentStructure1);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null,
					getFilterString(entityField, "between", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentStructuresPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentStructure contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> page =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null,
					getFilterString(entityField, "eq", contentStructure1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentStructure1),
				(List<ContentStructure>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentStructuresPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		ContentStructure contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		ContentStructure contentStructure3 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, randomContentStructure());

		Page<ContentStructure> page1 =
			contentStructureResource.getSiteContentStructuresPage(
				siteId, null, null, Pagination.of(1, 2), null);

		List<ContentStructure> contentStructures1 =
			(List<ContentStructure>)page1.getItems();

		Assert.assertEquals(
			contentStructures1.toString(), 2, contentStructures1.size());

		Page<ContentStructure> page2 =
			contentStructureResource.getSiteContentStructuresPage(
				siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentStructure> contentStructures2 =
			(List<ContentStructure>)page2.getItems();

		Assert.assertEquals(
			contentStructures2.toString(), 1, contentStructures2.size());

		Page<ContentStructure> page3 =
			contentStructureResource.getSiteContentStructuresPage(
				siteId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentStructure1, contentStructure2, contentStructure3),
			(List<ContentStructure>)page3.getItems());
	}

	@Test
	public void testGetSiteContentStructuresPageWithSortDateTime()
		throws Exception {

		testGetSiteContentStructuresPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contentStructure1, contentStructure2) -> {
				BeanUtils.setProperty(
					contentStructure1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteContentStructuresPageWithSortInteger()
		throws Exception {

		testGetSiteContentStructuresPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contentStructure1, contentStructure2) -> {
				BeanUtils.setProperty(
					contentStructure1, entityField.getName(), 0);
				BeanUtils.setProperty(
					contentStructure2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteContentStructuresPageWithSortString()
		throws Exception {

		testGetSiteContentStructuresPageWithSort(
			EntityField.Type.STRING,
			(entityField, contentStructure1, contentStructure2) -> {
				Class<?> clazz = contentStructure1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						contentStructure1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						contentStructure2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						contentStructure1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						contentStructure2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetSiteContentStructuresPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, ContentStructure, ContentStructure, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentStructuresPage_getSiteId();

		ContentStructure contentStructure1 = randomContentStructure();
		ContentStructure contentStructure2 = randomContentStructure();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, contentStructure1, contentStructure2);
		}

		contentStructure1 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, contentStructure1);

		contentStructure2 =
			testGetSiteContentStructuresPage_addContentStructure(
				siteId, contentStructure2);

		for (EntityField entityField : entityFields) {
			Page<ContentStructure> ascPage =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentStructure1, contentStructure2),
				(List<ContentStructure>)ascPage.getItems());

			Page<ContentStructure> descPage =
				contentStructureResource.getSiteContentStructuresPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentStructure2, contentStructure1),
				(List<ContentStructure>)descPage.getItems());
		}
	}

	protected ContentStructure
			testGetSiteContentStructuresPage_addContentStructure(
				Long siteId, ContentStructure contentStructure)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentStructuresPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteContentStructuresPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteContentStructuresPage() throws Exception {
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
				"contentStructures",
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

		JSONObject contentStructuresJSONObject = dataJSONObject.getJSONObject(
			"contentStructures");

		Assert.assertEquals(0, contentStructuresJSONObject.get("totalCount"));

		ContentStructure contentStructure1 =
			testGraphQLContentStructure_addContentStructure();
		ContentStructure contentStructure2 =
			testGraphQLContentStructure_addContentStructure();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		contentStructuresJSONObject = dataJSONObject.getJSONObject(
			"contentStructures");

		Assert.assertEquals(2, contentStructuresJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(contentStructure1, contentStructure2),
			contentStructuresJSONObject.getJSONArray("items"));
	}

	protected ContentStructure testGraphQLContentStructure_addContentStructure()
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
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		Assert.assertTrue(
			contentStructure1 + " does not equal " + contentStructure2,
			equals(contentStructure1, contentStructure2));
	}

	protected void assertEquals(
		List<ContentStructure> contentStructures1,
		List<ContentStructure> contentStructures2) {

		Assert.assertEquals(
			contentStructures1.size(), contentStructures2.size());

		for (int i = 0; i < contentStructures1.size(); i++) {
			ContentStructure contentStructure1 = contentStructures1.get(i);
			ContentStructure contentStructure2 = contentStructures2.get(i);

			assertEquals(contentStructure1, contentStructure2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentStructure> contentStructures1,
		List<ContentStructure> contentStructures2) {

		Assert.assertEquals(
			contentStructures1.size(), contentStructures2.size());

		for (ContentStructure contentStructure1 : contentStructures1) {
			boolean contains = false;

			for (ContentStructure contentStructure2 : contentStructures2) {
				if (equals(contentStructure1, contentStructure2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentStructures2 + " does not contain " + contentStructure1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<ContentStructure> contentStructures, JSONArray jsonArray) {

		for (ContentStructure contentStructure : contentStructures) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(contentStructure, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + contentStructure, contains);
		}
	}

	protected void assertValid(ContentStructure contentStructure) {
		boolean valid = true;

		if (contentStructure.getDateCreated() == null) {
			valid = false;
		}

		if (contentStructure.getDateModified() == null) {
			valid = false;
		}

		if (contentStructure.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				contentStructure.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (contentStructure.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureFields", additionalAssertFieldName)) {

				if (contentStructure.getContentStructureFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (contentStructure.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (contentStructure.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (contentStructure.getDescription_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (contentStructure.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (contentStructure.getName_i18n() == null) {
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

	protected void assertValid(Page<ContentStructure> page) {
		boolean valid = false;

		java.util.Collection<ContentStructure> contentStructures =
			page.getItems();

		int size = contentStructures.size();

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

	protected boolean equals(
		ContentStructure contentStructure1,
		ContentStructure contentStructure2) {

		if (contentStructure1 == contentStructure2) {
			return true;
		}

		if (!Objects.equals(
				contentStructure1.getSiteId(), contentStructure2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentStructure1.getAvailableLanguages(),
						contentStructure2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contentStructureFields", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentStructure1.getContentStructureFields(),
						contentStructure2.getContentStructureFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getCreator(),
						contentStructure2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDateCreated(),
						contentStructure2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDateModified(),
						contentStructure2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDescription(),
						contentStructure2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getDescription_i18n(),
						contentStructure2.getDescription_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getId(), contentStructure2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getName(),
						contentStructure2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentStructure1.getName_i18n(),
						contentStructure2.getName_i18n())) {

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

	protected boolean equalsJSONObject(
		ContentStructure contentStructure, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						contentStructure.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						contentStructure.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						contentStructure.getName(),
						jsonObject.getString("name"))) {

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

		if (!(_contentStructureResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentStructureResource;

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
		EntityField entityField, String operator,
		ContentStructure contentStructure) {

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

		if (entityFieldName.equals("contentStructureFields")) {
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
						DateUtils.addSeconds(
							contentStructure.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentStructure.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(contentStructure.getDateCreated()));
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
							contentStructure.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentStructure.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(contentStructure.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(contentStructure.getName()));
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

	protected ContentStructure randomContentStructure() throws Exception {
		return new ContentStructure() {
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

	protected ContentStructure randomIrrelevantContentStructure()
		throws Exception {

		ContentStructure randomIrrelevantContentStructure =
			randomContentStructure();

		randomIrrelevantContentStructure.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantContentStructure;
	}

	protected ContentStructure randomPatchContentStructure() throws Exception {
		return randomContentStructure();
	}

	protected ContentStructureResource contentStructureResource;
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
		BaseContentStructureResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.ContentStructureResource
		_contentStructureResource;

}