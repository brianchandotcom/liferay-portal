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

import com.liferay.headless.delivery.client.dto.v1_0.SitePage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.SitePageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.SitePageSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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

import java.lang.reflect.InvocationTargetException;

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
public abstract class BaseSitePageResourceTestCase {

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

		_sitePageResource.setContextCompany(testCompany);

		SitePageResource.Builder builder = SitePageResource.builder();

		sitePageResource = builder.authentication(
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

		SitePage sitePage1 = randomSitePage();

		String json = objectMapper.writeValueAsString(sitePage1);

		SitePage sitePage2 = SitePageSerDes.toDTO(json);

		Assert.assertTrue(equals(sitePage1, sitePage2));
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

		SitePage sitePage = randomSitePage();

		String json1 = objectMapper.writeValueAsString(sitePage);
		String json2 = SitePageSerDes.toJSON(sitePage);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		SitePage sitePage = randomSitePage();

		sitePage.setFriendlyUrlPath(regex);
		sitePage.setPageType(regex);
		sitePage.setTitle(regex);
		sitePage.setUuid(regex);

		String json = SitePageSerDes.toJSON(sitePage);

		Assert.assertFalse(json.contains(regex));

		sitePage = SitePageSerDes.toDTO(json);

		Assert.assertEquals(regex, sitePage.getFriendlyUrlPath());
		Assert.assertEquals(regex, sitePage.getPageType());
		Assert.assertEquals(regex, sitePage.getTitle());
		Assert.assertEquals(regex, sitePage.getUuid());
	}

	@Test
	public void testGetSiteSitePagesPage() throws Exception {
		Long siteId = testGetSiteSitePagesPage_getSiteId();
		Long irrelevantSiteId = testGetSiteSitePagesPage_getIrrelevantSiteId();

		Page<SitePage> page = sitePageResource.getSiteSitePagesPage(
			siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSiteId != null) {
			SitePage irrelevantSitePage = testGetSiteSitePagesPage_addSitePage(
				irrelevantSiteId, randomIrrelevantSitePage());

			page = sitePageResource.getSiteSitePagesPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSitePage),
				(List<SitePage>)page.getItems());
			assertValid(page);
		}

		SitePage sitePage1 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		SitePage sitePage2 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		page = sitePageResource.getSiteSitePagesPage(
			siteId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(sitePage1, sitePage2),
			(List<SitePage>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteSitePagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteSitePagesPage_getSiteId();

		SitePage sitePage1 = randomSitePage();

		sitePage1 = testGetSiteSitePagesPage_addSitePage(siteId, sitePage1);

		for (EntityField entityField : entityFields) {
			Page<SitePage> page = sitePageResource.getSiteSitePagesPage(
				siteId, null, null,
				getFilterString(entityField, "between", sitePage1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sitePage1),
				(List<SitePage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteSitePagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteSitePagesPage_getSiteId();

		SitePage sitePage1 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		SitePage sitePage2 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		for (EntityField entityField : entityFields) {
			Page<SitePage> page = sitePageResource.getSiteSitePagesPage(
				siteId, null, null,
				getFilterString(entityField, "eq", sitePage1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(sitePage1),
				(List<SitePage>)page.getItems());
		}
	}

	@Test
	public void testGetSiteSitePagesPageWithPagination() throws Exception {
		Long siteId = testGetSiteSitePagesPage_getSiteId();

		SitePage sitePage1 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		SitePage sitePage2 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		SitePage sitePage3 = testGetSiteSitePagesPage_addSitePage(
			siteId, randomSitePage());

		Page<SitePage> page1 = sitePageResource.getSiteSitePagesPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<SitePage> sitePages1 = (List<SitePage>)page1.getItems();

		Assert.assertEquals(sitePages1.toString(), 2, sitePages1.size());

		Page<SitePage> page2 = sitePageResource.getSiteSitePagesPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<SitePage> sitePages2 = (List<SitePage>)page2.getItems();

		Assert.assertEquals(sitePages2.toString(), 1, sitePages2.size());

		Page<SitePage> page3 = sitePageResource.getSiteSitePagesPage(
			siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(sitePage1, sitePage2, sitePage3),
			(List<SitePage>)page3.getItems());
	}

	@Test
	public void testGetSiteSitePagesPageWithSortDateTime() throws Exception {
		testGetSiteSitePagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, sitePage1, sitePage2) -> {
				BeanUtils.setProperty(
					sitePage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteSitePagesPageWithSortInteger() throws Exception {
		testGetSiteSitePagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, sitePage1, sitePage2) -> {
				BeanUtils.setProperty(sitePage1, entityField.getName(), 0);
				BeanUtils.setProperty(sitePage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteSitePagesPageWithSortString() throws Exception {
		testGetSiteSitePagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, sitePage1, sitePage2) -> {
				Class<?> clazz = sitePage1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						sitePage1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						sitePage2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						sitePage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						sitePage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						sitePage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						sitePage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteSitePagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, SitePage, SitePage, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteSitePagesPage_getSiteId();

		SitePage sitePage1 = randomSitePage();
		SitePage sitePage2 = randomSitePage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, sitePage1, sitePage2);
		}

		sitePage1 = testGetSiteSitePagesPage_addSitePage(siteId, sitePage1);

		sitePage2 = testGetSiteSitePagesPage_addSitePage(siteId, sitePage2);

		for (EntityField entityField : entityFields) {
			Page<SitePage> ascPage = sitePageResource.getSiteSitePagesPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(sitePage1, sitePage2),
				(List<SitePage>)ascPage.getItems());

			Page<SitePage> descPage = sitePageResource.getSiteSitePagesPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(sitePage2, sitePage1),
				(List<SitePage>)descPage.getItems());
		}
	}

	protected SitePage testGetSiteSitePagesPage_addSitePage(
			Long siteId, SitePage sitePage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteSitePagesPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteSitePagesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteSitePagesPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetSiteSitePage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetSiteSitePage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetSiteSitePageNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetSiteSitePagesExperiencesPage() throws Exception {
		Long siteId = testGetSiteSitePagesExperiencesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteSitePagesExperiencesPage_getIrrelevantSiteId();
		String friendlyUrlPath =
			testGetSiteSitePagesExperiencesPage_getFriendlyUrlPath();
		String irrelevantFriendlyUrlPath =
			testGetSiteSitePagesExperiencesPage_getIrrelevantFriendlyUrlPath();

		Page<SitePage> page = sitePageResource.getSiteSitePagesExperiencesPage(
			siteId, friendlyUrlPath);

		Assert.assertEquals(0, page.getTotalCount());

		if ((irrelevantSiteId != null) && (irrelevantFriendlyUrlPath != null)) {
			SitePage irrelevantSitePage =
				testGetSiteSitePagesExperiencesPage_addSitePage(
					irrelevantSiteId, irrelevantFriendlyUrlPath,
					randomIrrelevantSitePage());

			page = sitePageResource.getSiteSitePagesExperiencesPage(
				irrelevantSiteId, irrelevantFriendlyUrlPath);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSitePage),
				(List<SitePage>)page.getItems());
			assertValid(page);
		}

		SitePage sitePage1 = testGetSiteSitePagesExperiencesPage_addSitePage(
			siteId, friendlyUrlPath, randomSitePage());

		SitePage sitePage2 = testGetSiteSitePagesExperiencesPage_addSitePage(
			siteId, friendlyUrlPath, randomSitePage());

		page = sitePageResource.getSiteSitePagesExperiencesPage(
			siteId, friendlyUrlPath);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(sitePage1, sitePage2),
			(List<SitePage>)page.getItems());
		assertValid(page);
	}

	protected SitePage testGetSiteSitePagesExperiencesPage_addSitePage(
			Long siteId, String friendlyUrlPath, SitePage sitePage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteSitePagesExperiencesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteSitePagesExperiencesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String testGetSiteSitePagesExperiencesPage_getFriendlyUrlPath()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteSitePagesExperiencesPage_getIrrelevantFriendlyUrlPath()
		throws Exception {

		return null;
	}

	@Test
	public void testGetSiteSitePageExperienceExperienceKey() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetSiteSitePageExperienceExperienceKey()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetSiteSitePageExperienceExperienceKeyNotFound()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testGetSiteSitePageExperienceExperienceKeyRenderedPage()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetSiteSitePageRenderedPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(SitePage sitePage, List<SitePage> sitePages) {
		boolean contains = false;

		for (SitePage item : sitePages) {
			if (equals(sitePage, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			sitePages + " does not contain " + sitePage, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(SitePage sitePage1, SitePage sitePage2) {
		Assert.assertTrue(
			sitePage1 + " does not equal " + sitePage2,
			equals(sitePage1, sitePage2));
	}

	protected void assertEquals(
		List<SitePage> sitePages1, List<SitePage> sitePages2) {

		Assert.assertEquals(sitePages1.size(), sitePages2.size());

		for (int i = 0; i < sitePages1.size(); i++) {
			SitePage sitePage1 = sitePages1.get(i);
			SitePage sitePage2 = sitePages2.get(i);

			assertEquals(sitePage1, sitePage2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<SitePage> sitePages1, List<SitePage> sitePages2) {

		Assert.assertEquals(sitePages1.size(), sitePages2.size());

		for (SitePage sitePage1 : sitePages1) {
			boolean contains = false;

			for (SitePage sitePage2 : sitePages2) {
				if (equals(sitePage1, sitePage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				sitePages2 + " does not contain " + sitePage1, contains);
		}
	}

	protected void assertValid(SitePage sitePage) throws Exception {
		boolean valid = true;

		if (sitePage.getDateCreated() == null) {
			valid = false;
		}

		if (sitePage.getDateModified() == null) {
			valid = false;
		}

		if (!Objects.equals(sitePage.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (sitePage.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (sitePage.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (sitePage.getAvailableLanguages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (sitePage.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (sitePage.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (sitePage.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("experience", additionalAssertFieldName)) {
				if (sitePage.getExperience() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (sitePage.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (sitePage.getFriendlyUrlPath_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (sitePage.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageDefinition", additionalAssertFieldName)) {
				if (sitePage.getPageDefinition() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageSettings", additionalAssertFieldName)) {
				if (sitePage.getPageSettings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pageType", additionalAssertFieldName)) {
				if (sitePage.getPageType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("renderedPage", additionalAssertFieldName)) {
				if (sitePage.getRenderedPage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (sitePage.getTaxonomyCategoryBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (sitePage.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (sitePage.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (sitePage.getTitle_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (sitePage.getUuid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (sitePage.getViewableBy() == null) {
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

	protected void assertValid(Page<SitePage> page) {
		boolean valid = false;

		java.util.Collection<SitePage> sitePages = page.getItems();

		int size = sitePages.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.SitePage.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(SitePage sitePage1, SitePage sitePage2) {
		if (sitePage1 == sitePage2) {
			return true;
		}

		if (!Objects.equals(sitePage1.getSiteId(), sitePage2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)sitePage1.getActions(),
						(Map)sitePage2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getAggregateRating(),
						sitePage2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"availableLanguages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						sitePage1.getAvailableLanguages(),
						sitePage2.getAvailableLanguages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getCreator(), sitePage2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getCustomFields(),
						sitePage2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getDateCreated(),
						sitePage2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getDateModified(),
						sitePage2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getDatePublished(),
						sitePage2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("experience", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getExperience(), sitePage2.getExperience())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getFriendlyUrlPath(),
						sitePage2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"friendlyUrlPath_i18n", additionalAssertFieldName)) {

				if (!equals(
						(Map)sitePage1.getFriendlyUrlPath_i18n(),
						(Map)sitePage2.getFriendlyUrlPath_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getKeywords(), sitePage2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageDefinition", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getPageDefinition(),
						sitePage2.getPageDefinition())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageSettings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getPageSettings(),
						sitePage2.getPageSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("pageType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getPageType(), sitePage2.getPageType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("renderedPage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getRenderedPage(),
						sitePage2.getRenderedPage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						sitePage1.getTaxonomyCategoryBriefs(),
						sitePage2.getTaxonomyCategoryBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						sitePage1.getTaxonomyCategoryIds(),
						sitePage2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getTitle(), sitePage2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)sitePage1.getTitle_i18n(),
						(Map)sitePage2.getTitle_i18n())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("uuid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getUuid(), sitePage2.getUuid())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sitePage1.getViewableBy(), sitePage2.getViewableBy())) {

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

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_sitePageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_sitePageResource;

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
		EntityField entityField, String operator, SitePage sitePage) {

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
						DateUtils.addSeconds(sitePage.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sitePage.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sitePage.getDateCreated()));
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
						DateUtils.addSeconds(sitePage.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sitePage.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sitePage.getDateModified()));
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
						DateUtils.addSeconds(sitePage.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(sitePage.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(sitePage.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("experience")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(sitePage.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath_i18n")) {
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

		if (entityFieldName.equals("pageType")) {
			sb.append("'");
			sb.append(String.valueOf(sitePage.getPageType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("renderedPage")) {
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
			sb.append(String.valueOf(sitePage.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title_i18n")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("uuid")) {
			sb.append("'");
			sb.append(String.valueOf(sitePage.getUuid()));
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

	protected SitePage randomSitePage() throws Exception {
		return new SitePage() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				friendlyUrlPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				pageType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected SitePage randomIrrelevantSitePage() throws Exception {
		SitePage randomIrrelevantSitePage = randomSitePage();

		randomIrrelevantSitePage.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantSitePage;
	}

	protected SitePage randomPatchSitePage() throws Exception {
		return randomSitePage();
	}

	protected SitePageResource sitePageResource;
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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseSitePageResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.SitePageResource
		_sitePageResource;

}