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

import com.liferay.headless.delivery.client.dto.v1_0.ContentPage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.ContentPageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.ContentPageSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
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
public abstract class BaseContentPageResourceTestCase {

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

		_contentPageResource.setContextCompany(testCompany);

		ContentPageResource.Builder builder = ContentPageResource.builder();

		contentPageResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		ContentPage contentPage1 = randomContentPage();

		String json = objectMapper.writeValueAsString(contentPage1);

		ContentPage contentPage2 = ContentPageSerDes.toDTO(json);

		Assert.assertTrue(equals(contentPage1, contentPage2));
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

		ContentPage contentPage = randomContentPage();

		String json1 = objectMapper.writeValueAsString(contentPage);
		String json2 = ContentPageSerDes.toJSON(contentPage);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ContentPage contentPage = randomContentPage();

		contentPage.setFriendlyUrlPath(regex);
		contentPage.setTitle(regex);
		contentPage.setUuid(regex);

		String json = ContentPageSerDes.toJSON(contentPage);

		Assert.assertFalse(json.contains(regex));

		contentPage = ContentPageSerDes.toDTO(json);

		Assert.assertEquals(regex, contentPage.getFriendlyUrlPath());
		Assert.assertEquals(regex, contentPage.getTitle());
		Assert.assertEquals(regex, contentPage.getUuid());
	}

	@Test
	public void testGetSiteContentPagesPage() throws Exception {
		Page<ContentPage> page = contentPageResource.getSiteContentPagesPage(
			testGetSiteContentPagesPage_getSiteId(),
			RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteContentPagesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteContentPagesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			ContentPage irrelevantContentPage =
				testGetSiteContentPagesPage_addContentPage(
					irrelevantSiteId, randomIrrelevantContentPage());

			page = contentPageResource.getSiteContentPagesPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantContentPage),
				(List<ContentPage>)page.getItems());
			assertValid(page);
		}

		ContentPage contentPage1 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		ContentPage contentPage2 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		page = contentPageResource.getSiteContentPagesPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(contentPage1, contentPage2),
			(List<ContentPage>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteContentPagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentPagesPage_getSiteId();

		ContentPage contentPage1 = randomContentPage();

		contentPage1 = testGetSiteContentPagesPage_addContentPage(
			siteId, contentPage1);

		for (EntityField entityField : entityFields) {
			Page<ContentPage> page =
				contentPageResource.getSiteContentPagesPage(
					siteId, null, null,
					getFilterString(entityField, "between", contentPage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentPage1),
				(List<ContentPage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentPagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentPagesPage_getSiteId();

		ContentPage contentPage1 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ContentPage contentPage2 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		for (EntityField entityField : entityFields) {
			Page<ContentPage> page =
				contentPageResource.getSiteContentPagesPage(
					siteId, null, null,
					getFilterString(entityField, "eq", contentPage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(contentPage1),
				(List<ContentPage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteContentPagesPageWithPagination() throws Exception {
		Long siteId = testGetSiteContentPagesPage_getSiteId();

		ContentPage contentPage1 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		ContentPage contentPage2 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		ContentPage contentPage3 = testGetSiteContentPagesPage_addContentPage(
			siteId, randomContentPage());

		Page<ContentPage> page1 = contentPageResource.getSiteContentPagesPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<ContentPage> contentPages1 = (List<ContentPage>)page1.getItems();

		Assert.assertEquals(contentPages1.toString(), 2, contentPages1.size());

		Page<ContentPage> page2 = contentPageResource.getSiteContentPagesPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<ContentPage> contentPages2 = (List<ContentPage>)page2.getItems();

		Assert.assertEquals(contentPages2.toString(), 1, contentPages2.size());

		Page<ContentPage> page3 = contentPageResource.getSiteContentPagesPage(
			siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(contentPage1, contentPage2, contentPage3),
			(List<ContentPage>)page3.getItems());
	}

	@Test
	public void testGetSiteContentPagesPageWithSortDateTime() throws Exception {
		testGetSiteContentPagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, contentPage1, contentPage2) -> {
				BeanUtils.setProperty(
					contentPage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteContentPagesPageWithSortInteger() throws Exception {
		testGetSiteContentPagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, contentPage1, contentPage2) -> {
				BeanUtils.setProperty(contentPage1, entityField.getName(), 0);
				BeanUtils.setProperty(contentPage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteContentPagesPageWithSortString() throws Exception {
		testGetSiteContentPagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, contentPage1, contentPage2) -> {
				Class<?> clazz = contentPage1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						contentPage1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						contentPage2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						contentPage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						contentPage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						contentPage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						contentPage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteContentPagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, ContentPage, ContentPage, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteContentPagesPage_getSiteId();

		ContentPage contentPage1 = randomContentPage();
		ContentPage contentPage2 = randomContentPage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, contentPage1, contentPage2);
		}

		contentPage1 = testGetSiteContentPagesPage_addContentPage(
			siteId, contentPage1);

		contentPage2 = testGetSiteContentPagesPage_addContentPage(
			siteId, contentPage2);

		for (EntityField entityField : entityFields) {
			Page<ContentPage> ascPage =
				contentPageResource.getSiteContentPagesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(contentPage1, contentPage2),
				(List<ContentPage>)ascPage.getItems());

			Page<ContentPage> descPage =
				contentPageResource.getSiteContentPagesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(contentPage2, contentPage1),
				(List<ContentPage>)descPage.getItems());
		}
	}

	protected ContentPage testGetSiteContentPagesPage_addContentPage(
			Long siteId, ContentPage contentPage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteContentPagesPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteContentPagesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteContentPagesPage() throws Exception {
		Long siteId = testGetSiteContentPagesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"contentPages",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject contentPagesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contentPages");

		Assert.assertEquals(0, contentPagesJSONObject.get("totalCount"));

		ContentPage contentPage1 = testGraphQLContentPage_addContentPage();
		ContentPage contentPage2 = testGraphQLContentPage_addContentPage();

		contentPagesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/contentPages");

		Assert.assertEquals(2, contentPagesJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(contentPage1, contentPage2),
			Arrays.asList(
				ContentPageSerDes.toDTOs(
					contentPagesJSONObject.getString("items"))));
	}

	@Test
	public void testGetSiteContentPagePrivateFriendlyUrlPath()
		throws Exception {

		ContentPage postContentPage =
			testGetSiteContentPagePrivateFriendlyUrlPath_addContentPage();

		ContentPage getContentPage =
			contentPageResource.getSiteContentPagePrivateFriendlyUrlPath(
				postContentPage.getSiteId(),
				postContentPage.getFriendlyUrlPath());

		assertEquals(postContentPage, getContentPage);
		assertValid(getContentPage);
	}

	protected ContentPage
			testGetSiteContentPagePrivateFriendlyUrlPath_addContentPage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteContentPagePrivateFriendlyUrlPath()
		throws Exception {

		ContentPage contentPage = testGraphQLContentPage_addContentPage();

		Assert.assertTrue(
			equals(
				contentPage,
				ContentPageSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"contentPagePrivateFriendlyUrlPath",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" + contentPage.getSiteId() +
												"\"");
										put(
											"friendlyUrlPath",
											"\"" +
												contentPage.
													getFriendlyUrlPath() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/contentPagePrivateFriendlyUrlPath"))));
	}

	@Test
	public void testGraphQLGetSiteContentPagePrivateFriendlyUrlPathNotFound()
		throws Exception {

		String irrelevantFriendlyUrlPath =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"contentPagePrivateFriendlyUrlPath",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put(
									"friendlyUrlPath",
									irrelevantFriendlyUrlPath);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetSiteContentPagePrivateFriendlyUrlPathRenderedPage()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetSiteContentPagePublicFriendlyUrlPath() throws Exception {
		ContentPage postContentPage =
			testGetSiteContentPagePublicFriendlyUrlPath_addContentPage();

		ContentPage getContentPage =
			contentPageResource.getSiteContentPagePublicFriendlyUrlPath(
				postContentPage.getSiteId(),
				postContentPage.getFriendlyUrlPath());

		assertEquals(postContentPage, getContentPage);
		assertValid(getContentPage);
	}

	protected ContentPage
			testGetSiteContentPagePublicFriendlyUrlPath_addContentPage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteContentPagePublicFriendlyUrlPath()
		throws Exception {

		ContentPage contentPage = testGraphQLContentPage_addContentPage();

		Assert.assertTrue(
			equals(
				contentPage,
				ContentPageSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"contentPagePublicFriendlyUrlPath",
								new HashMap<String, Object>() {
									{
										put(
											"siteKey",
											"\"" + contentPage.getSiteId() +
												"\"");
										put(
											"friendlyUrlPath",
											"\"" +
												contentPage.
													getFriendlyUrlPath() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/contentPagePublicFriendlyUrlPath"))));
	}

	@Test
	public void testGraphQLGetSiteContentPagePublicFriendlyUrlPathNotFound()
		throws Exception {

		String irrelevantFriendlyUrlPath =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"contentPagePublicFriendlyUrlPath",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + irrelevantGroup.getGroupId() + "\"");
								put(
									"friendlyUrlPath",
									irrelevantFriendlyUrlPath);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testGetSiteContentPagePublicFriendlyUrlPathRenderedPage()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected ContentPage testGraphQLContentPage_addContentPage()
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
		ContentPage contentPage1, ContentPage contentPage2) {

		Assert.assertTrue(
			contentPage1 + " does not equal " + contentPage2,
			equals(contentPage1, contentPage2));
	}

	protected void assertEquals(
		List<ContentPage> contentPages1, List<ContentPage> contentPages2) {

		Assert.assertEquals(contentPages1.size(), contentPages2.size());

		for (int i = 0; i < contentPages1.size(); i++) {
			ContentPage contentPage1 = contentPages1.get(i);
			ContentPage contentPage2 = contentPages2.get(i);

			assertEquals(contentPage1, contentPage2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ContentPage> contentPages1, List<ContentPage> contentPages2) {

		Assert.assertEquals(contentPages1.size(), contentPages2.size());

		for (ContentPage contentPage1 : contentPages1) {
			boolean contains = false;

			for (ContentPage contentPage2 : contentPages2) {
				if (equals(contentPage1, contentPage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				contentPages2 + " does not contain " + contentPage1, contains);
		}
	}

	protected void assertValid(ContentPage contentPage) throws Exception {
		boolean valid = true;

		if (contentPage.getDateCreated() == null) {
			valid = false;
		}

		if (contentPage.getDateModified() == null) {
			valid = false;
		}

		if (contentPage.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(contentPage.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (contentPage.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (contentPage.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (contentPage.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (contentPage.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (contentPage.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (contentPage.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultRenderedPage", additionalAssertFieldName)) {

				if (contentPage.getDefaultRenderedPage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (contentPage.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (contentPage.getFriendlyUrlPath_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (contentPage.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageDefinition", additionalAssertFieldName)) {
				if (contentPage.getPageDefinition() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageSettings", additionalAssertFieldName)) {
				if (contentPage.getPageSettings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("privatePage", additionalAssertFieldName)) {
				if (contentPage.getPrivatePage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("renderedPages", additionalAssertFieldName)) {
				if (contentPage.getRenderedPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (contentPage.getTaxonomyCategoryBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (contentPage.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (contentPage.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (contentPage.getTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (contentPage.getUuid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (contentPage.getViewableBy() == null) {
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

	protected void assertValid(Page<ContentPage> page) {
		boolean valid = false;

		java.util.Collection<ContentPage> contentPages = page.getItems();

		int size = contentPages.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.ContentPage.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		ContentPage contentPage1, ContentPage contentPage2) {

		if (contentPage1 == contentPage2) {
			return true;
		}

		if (!Objects.equals(
				contentPage1.getSiteId(), contentPage2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentPage1.getActions(),
						(Map)contentPage2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getAggregateRating(),
						contentPage2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentPage1.getAvailableLanguages(),
						contentPage2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getCreator(), contentPage2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getCustomFields(),
						contentPage2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getDateCreated(),
						contentPage2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getDateModified(),
						contentPage2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getDatePublished(),
						contentPage2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultRenderedPage", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentPage1.getDefaultRenderedPage(),
						contentPage2.getDefaultRenderedPage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getFriendlyUrlPath(),
						contentPage2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (!equals(
						(Map)contentPage1.getFriendlyUrlPath_i18n(),
						(Map)contentPage2.getFriendlyUrlPath_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getId(), contentPage2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getKeywords(),
						contentPage2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageDefinition", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getPageDefinition(),
						contentPage2.getPageDefinition())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageSettings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getPageSettings(),
						contentPage2.getPageSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("privatePage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getPrivatePage(),
						contentPage2.getPrivatePage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("renderedPages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getRenderedPages(),
						contentPage2.getRenderedPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentPage1.getTaxonomyCategoryBriefs(),
						contentPage2.getTaxonomyCategoryBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						contentPage1.getTaxonomyCategoryIds(),
						contentPage2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getTitle(), contentPage2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)contentPage1.getTitle_i18n(),
						(Map)contentPage2.getTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getUuid(), contentPage2.getUuid())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						contentPage1.getViewableBy(),
						contentPage2.getViewableBy())) {

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
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_contentPageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_contentPageResource;

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
		EntityField entityField, String operator, ContentPage contentPage) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("availableLanguages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
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
							contentPage.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(contentPage.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(contentPage.getDateCreated()));
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
							contentPage.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentPage.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(contentPage.getDateModified()));
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
							contentPage.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							contentPage.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(contentPage.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultRenderedPage")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(contentPage.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("pageDefinition")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("pageSettings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("privatePage")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("renderedPages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(contentPage.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("uuid")) {
			sb.append("'");
			sb.append(String.valueOf(contentPage.getUuid()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
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

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected ContentPage randomContentPage() throws Exception {
		return new ContentPage() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				friendlyUrlPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				privatePage = RandomTestUtil.randomBoolean();
				siteId = testGroup.getGroupId();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected ContentPage randomIrrelevantContentPage() throws Exception {
		ContentPage randomIrrelevantContentPage = randomContentPage();

		randomIrrelevantContentPage.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantContentPage;
	}

	protected ContentPage randomPatchContentPage() throws Exception {
		return randomContentPage();
	}

	protected ContentPageResource contentPageResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

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

			if (!_graphQLFields.isEmpty()) {
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

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseContentPageResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.ContentPageResource
		_contentPageResource;

}