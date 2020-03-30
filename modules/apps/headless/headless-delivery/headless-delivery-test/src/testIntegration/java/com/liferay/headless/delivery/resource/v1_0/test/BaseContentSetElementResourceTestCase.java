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

import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.ContentSetElementResource;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentSetElementSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
public abstract class BaseContentSetElementResourceTestCase {

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

		_contentSetElementResource.setContextCompany(testCompany);

		ContentSetElementResource.Builder builder =
			ContentSetElementResource.builder();

		contentSetElementResource = builder.locale(
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

		ContentSetElement contentSetElement1 = randomContentSetElement();

		String json = objectMapper.writeValueAsString(contentSetElement1);

		ContentSetElement contentSetElement2 = ContentSetElementSerDes.toDTO(
			json);

		Assert.assertTrue(equals(contentSetElement1, contentSetElement2));
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

		ContentSetElement contentSetElement = randomContentSetElement();

		String json1 = objectMapper.writeValueAsString(contentSetElement);
		String json2 = ContentSetElementSerDes.toJSON(contentSetElement);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ContentSetElement contentSetElement = randomContentSetElement();

		contentSetElement.setContentType(regex);
		contentSetElement.setTitle(regex);

		String json = ContentSetElementSerDes.toJSON(contentSetElement);

		Assert.assertFalse(json.contains(regex));

		contentSetElement = ContentSetElementSerDes.toDTO(json);

		Assert.assertEquals(regex, contentSetElement.getContentType());
		Assert.assertEquals(regex, contentSetElement.getTitle());
	}

	@Test
	public void testGetContentSetContentSetElementsPage() throws Exception {
		Page<ContentSetElement> page =
			contentSetElementResource.getContentSetContentSetElementsPage(
				testGetContentSetContentSetElementsPage_getContentSetId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long contentSetId =
			testGetContentSetContentSetElementsPage_getContentSetId();
		Long irrelevantContentSetId =
			testGetContentSetContentSetElementsPage_getIrrelevantContentSetId();

		if ((irrelevantContentSetId != null)) {
			ContentSetElement irrelevantContentSetElement =
				testGetContentSetContentSetElementsPage_addContentSetElement(
					irrelevantContentSetId,
					randomIrrelevantContentSetElement());

			page =
				contentSetElementResource.getContentSetContentSetElementsPage(
					irrelevantContentSetId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentSetElement),
				(List<ContentSetElement>)page.getItems());
			assertValid(page);
		}

		ContentSetElement contentSetElement1 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		page = contentSetElementResource.getContentSetContentSetElementsPage(
			contentSetId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentSetElement1, contentSetElement2),
			(List<ContentSetElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSetContentSetElementsPageWithPagination()
		throws Exception {

		Long contentSetId =
			testGetContentSetContentSetElementsPage_getContentSetId();

		ContentSetElement contentSetElement1 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		ContentSetElement contentSetElement3 =
			testGetContentSetContentSetElementsPage_addContentSetElement(
				contentSetId, randomContentSetElement());

		Page<ContentSetElement> page1 =
			contentSetElementResource.getContentSetContentSetElementsPage(
				contentSetId, Pagination.of(1, 2));

		List<ContentSetElement> contentSetElements1 =
			(List<ContentSetElement>)page1.getItems();

		Assert.assertEquals(
			contentSetElements1.toString(), 2, contentSetElements1.size());

		Page<ContentSetElement> page2 =
			contentSetElementResource.getContentSetContentSetElementsPage(
				contentSetId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentSetElement> contentSetElements2 =
			(List<ContentSetElement>)page2.getItems();

		Assert.assertEquals(
			contentSetElements2.toString(), 1, contentSetElements2.size());

		Page<ContentSetElement> page3 =
			contentSetElementResource.getContentSetContentSetElementsPage(
				contentSetId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentSetElement1, contentSetElement2, contentSetElement3),
			(List<ContentSetElement>)page3.getItems());
	}

	protected ContentSetElement
			testGetContentSetContentSetElementsPage_addContentSetElement(
				Long contentSetId, ContentSetElement contentSetElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSetContentSetElementsPage_getContentSetId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetContentSetContentSetElementsPage_getIrrelevantContentSetId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetSiteContentSetByKeyContentSetElementsPage()
		throws Exception {

		Page<ContentSetElement> page =
			contentSetElementResource.
				getSiteContentSetByKeyContentSetElementsPage(
					testGetSiteContentSetByKeyContentSetElementsPage_getSiteId(),
					testGetSiteContentSetByKeyContentSetElementsPage_getKey(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId =
			testGetSiteContentSetByKeyContentSetElementsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantSiteId();
		String key = testGetSiteContentSetByKeyContentSetElementsPage_getKey();
		String irrelevantKey =
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantKey();

		if ((irrelevantSiteId != null) && (irrelevantKey != null)) {
			ContentSetElement irrelevantContentSetElement =
				testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
					irrelevantSiteId, irrelevantKey,
					randomIrrelevantContentSetElement());

			page =
				contentSetElementResource.
					getSiteContentSetByKeyContentSetElementsPage(
						irrelevantSiteId, irrelevantKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentSetElement),
				(List<ContentSetElement>)page.getItems());
			assertValid(page);
		}

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		page =
			contentSetElementResource.
				getSiteContentSetByKeyContentSetElementsPage(
					siteId, key, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentSetElement1, contentSetElement2),
			(List<ContentSetElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentSetByKeyContentSetElementsPageWithPagination()
		throws Exception {

		Long siteId =
			testGetSiteContentSetByKeyContentSetElementsPage_getSiteId();
		String key = testGetSiteContentSetByKeyContentSetElementsPage_getKey();

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		ContentSetElement contentSetElement3 =
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				siteId, key, randomContentSetElement());

		Page<ContentSetElement> page1 =
			contentSetElementResource.
				getSiteContentSetByKeyContentSetElementsPage(
					siteId, key, Pagination.of(1, 2));

		List<ContentSetElement> contentSetElements1 =
			(List<ContentSetElement>)page1.getItems();

		Assert.assertEquals(
			contentSetElements1.toString(), 2, contentSetElements1.size());

		Page<ContentSetElement> page2 =
			contentSetElementResource.
				getSiteContentSetByKeyContentSetElementsPage(
					siteId, key, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentSetElement> contentSetElements2 =
			(List<ContentSetElement>)page2.getItems();

		Assert.assertEquals(
			contentSetElements2.toString(), 1, contentSetElements2.size());

		Page<ContentSetElement> page3 =
			contentSetElementResource.
				getSiteContentSetByKeyContentSetElementsPage(
					siteId, key, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentSetElement1, contentSetElement2, contentSetElement3),
			(List<ContentSetElement>)page3.getItems());
	}

	protected ContentSetElement
			testGetSiteContentSetByKeyContentSetElementsPage_addContentSetElement(
				Long siteId, String key, ContentSetElement contentSetElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentSetByKeyContentSetElementsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String testGetSiteContentSetByKeyContentSetElementsPage_getKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteContentSetByKeyContentSetElementsPage_getIrrelevantKey()
		throws Exception {

		return null;
	}

	@Test
	public void testGetSiteContentSetByUuidContentSetElementsPage()
		throws Exception {

		Page<ContentSetElement> page =
			contentSetElementResource.
				getSiteContentSetByUuidContentSetElementsPage(
					testGetSiteContentSetByUuidContentSetElementsPage_getSiteId(),
					testGetSiteContentSetByUuidContentSetElementsPage_getUuid(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId =
			testGetSiteContentSetByUuidContentSetElementsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantSiteId();
		String uuid =
			testGetSiteContentSetByUuidContentSetElementsPage_getUuid();
		String irrelevantUuid =
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantUuid();

		if ((irrelevantSiteId != null) && (irrelevantUuid != null)) {
			ContentSetElement irrelevantContentSetElement =
				testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
					irrelevantSiteId, irrelevantUuid,
					randomIrrelevantContentSetElement());

			page =
				contentSetElementResource.
					getSiteContentSetByUuidContentSetElementsPage(
						irrelevantSiteId, irrelevantUuid, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentSetElement),
				(List<ContentSetElement>)page.getItems());
			assertValid(page);
		}

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		page =
			contentSetElementResource.
				getSiteContentSetByUuidContentSetElementsPage(
					siteId, uuid, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentSetElement1, contentSetElement2),
			(List<ContentSetElement>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentSetByUuidContentSetElementsPageWithPagination()
		throws Exception {

		Long siteId =
			testGetSiteContentSetByUuidContentSetElementsPage_getSiteId();
		String uuid =
			testGetSiteContentSetByUuidContentSetElementsPage_getUuid();

		ContentSetElement contentSetElement1 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		ContentSetElement contentSetElement2 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		ContentSetElement contentSetElement3 =
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				siteId, uuid, randomContentSetElement());

		Page<ContentSetElement> page1 =
			contentSetElementResource.
				getSiteContentSetByUuidContentSetElementsPage(
					siteId, uuid, Pagination.of(1, 2));

		List<ContentSetElement> contentSetElements1 =
			(List<ContentSetElement>)page1.getItems();

		Assert.assertEquals(
			contentSetElements1.toString(), 2, contentSetElements1.size());

		Page<ContentSetElement> page2 =
			contentSetElementResource.
				getSiteContentSetByUuidContentSetElementsPage(
					siteId, uuid, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentSetElement> contentSetElements2 =
			(List<ContentSetElement>)page2.getItems();

		Assert.assertEquals(
			contentSetElements2.toString(), 1, contentSetElements2.size());

		Page<ContentSetElement> page3 =
			contentSetElementResource.
				getSiteContentSetByUuidContentSetElementsPage(
					siteId, uuid, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				contentSetElement1, contentSetElement2, contentSetElement3),
			(List<ContentSetElement>)page3.getItems());
	}

	protected ContentSetElement
			testGetSiteContentSetByUuidContentSetElementsPage_addContentSetElement(
				Long siteId, String uuid, ContentSetElement contentSetElement)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentSetByUuidContentSetElementsPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String testGetSiteContentSetByUuidContentSetElementsPage_getUuid()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteContentSetByUuidContentSetElementsPage_getIrrelevantUuid()
		throws Exception {

		return null;
	}

	protected ContentSetElement
			testGraphQLContentSetElement_addContentSetElement()
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
		ContentSetElement contentSetElement1,
		ContentSetElement contentSetElement2) {

		Assert.assertTrue(
			contentSetElement1 + " does not equal " + contentSetElement2,
			equals(contentSetElement1, contentSetElement2));
	}

	protected void assertEquals(
		List<ContentSetElement> contentSetElements1,
		List<ContentSetElement> contentSetElements2) {

		Assert.assertEquals(
			contentSetElements1.size(), contentSetElements2.size());

		for (int i = 0; i < contentSetElements1.size(); i++) {
			ContentSetElement contentSetElement1 = contentSetElements1.get(i);
			ContentSetElement contentSetElement2 = contentSetElements2.get(i);

			assertEquals(contentSetElement1, contentSetElement2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentSetElement> contentSetElements1,
		List<ContentSetElement> contentSetElements2) {

		Assert.assertEquals(
			contentSetElements1.size(), contentSetElements2.size());

		for (ContentSetElement contentSetElement1 : contentSetElements1) {
			boolean contains = false;

			for (ContentSetElement contentSetElement2 : contentSetElements2) {
				if (equals(contentSetElement1, contentSetElement2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentSetElements2 + " does not contain " + contentSetElement1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<ContentSetElement> contentSetElements, JSONArray jsonArray) {

		for (ContentSetElement contentSetElement : contentSetElements) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(contentSetElement, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + contentSetElement, contains);
		}
	}

	protected void assertValid(ContentSetElement contentSetElement) {
		boolean valid = true;

		if (contentSetElement.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (contentSetElement.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (contentSetElement.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (contentSetElement.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (contentSetElement.getTitle_i18n() == null) {
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

	protected void assertValid(Page<ContentSetElement> page) {
		boolean valid = false;

		java.util.Collection<ContentSetElement> contentSetElements =
			page.getItems();

		int size = contentSetElements.size();

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
		ContentSetElement contentSetElement1,
		ContentSetElement contentSetElement2) {

		if (contentSetElement1 == contentSetElement2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getContent(),
						contentSetElement2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getContentType(),
						contentSetElement2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getId(),
						contentSetElement2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getTitle(),
						contentSetElement2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentSetElement1.getTitle_i18n(),
						contentSetElement2.getTitle_i18n())) {

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
		ContentSetElement contentSetElement, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentType", fieldName)) {
				if (!Objects.deepEquals(
						contentSetElement.getContentType(),
						jsonObject.getString("contentType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						contentSetElement.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", fieldName)) {
				if (!Objects.deepEquals(
						contentSetElement.getTitle(),
						jsonObject.getString("title"))) {

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

		if (!(_contentSetElementResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentSetElementResource;

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
		ContentSetElement contentSetElement) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("content")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(contentSetElement.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(contentSetElement.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
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

	protected ContentSetElement randomContentSetElement() throws Exception {
		return new ContentSetElement() {
			{
				contentType = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected ContentSetElement randomIrrelevantContentSetElement()
		throws Exception {

		ContentSetElement randomIrrelevantContentSetElement =
			randomContentSetElement();

		return randomIrrelevantContentSetElement;
	}

	protected ContentSetElement randomPatchContentSetElement()
		throws Exception {

		return randomContentSetElement();
	}

	protected ContentSetElementResource contentSetElementResource;
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
		BaseContentSetElementResourceTestCase.class);

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
	private
		com.liferay.headless.delivery.resource.v1_0.ContentSetElementResource
			_contentSetElementResource;

}