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

import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingResource;
import com.liferay.headless.delivery.client.serdes.v1_0.BlogPostingSerDes;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public abstract class BaseBlogPostingResourceTestCase {

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

		BlogPosting blogPosting1 = randomBlogPosting();

		String json = objectMapper.writeValueAsString(blogPosting1);

		BlogPosting blogPosting2 = BlogPostingSerDes.toDTO(json);

		Assert.assertTrue(equals(blogPosting1, blogPosting2));
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

		BlogPosting blogPosting = randomBlogPosting();

		String json1 = objectMapper.writeValueAsString(blogPosting);
		String json2 = BlogPostingSerDes.toJSON(blogPosting);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testDeleteBlogPosting() throws Exception {
		BlogPosting blogPosting = testDeleteBlogPosting_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			BlogPostingResource.deleteBlogPostingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404,
			BlogPostingResource.getBlogPostingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404, BlogPostingResource.getBlogPostingHttpResponse(0L));
	}

	protected BlogPosting testDeleteBlogPosting_addBlogPosting()
		throws Exception {

		return BlogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testGetBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testGetBlogPosting_addBlogPosting();

		BlogPosting getBlogPosting = BlogPostingResource.getBlogPosting(
			postBlogPosting.getId());

		assertEquals(postBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testGetBlogPosting_addBlogPosting() throws Exception {
		return BlogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testPatchBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testPatchBlogPosting_addBlogPosting();

		BlogPosting randomPatchBlogPosting = randomPatchBlogPosting();

		BlogPosting patchBlogPosting = BlogPostingResource.patchBlogPosting(
			postBlogPosting.getId(), randomPatchBlogPosting);

		BlogPosting expectedPatchBlogPosting = (BlogPosting)BeanUtils.cloneBean(
			postBlogPosting);

		_beanUtilsBean.copyProperties(
			expectedPatchBlogPosting, randomPatchBlogPosting);

		BlogPosting getBlogPosting = BlogPostingResource.getBlogPosting(
			patchBlogPosting.getId());

		assertEquals(expectedPatchBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testPatchBlogPosting_addBlogPosting()
		throws Exception {

		return BlogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testPutBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testPutBlogPosting_addBlogPosting();

		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting putBlogPosting = BlogPostingResource.putBlogPosting(
			postBlogPosting.getId(), randomBlogPosting);

		assertEquals(randomBlogPosting, putBlogPosting);
		assertValid(putBlogPosting);

		BlogPosting getBlogPosting = BlogPostingResource.getBlogPosting(
			putBlogPosting.getId());

		assertEquals(randomBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testPutBlogPosting_addBlogPosting() throws Exception {
		return BlogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testDeleteBlogPostingMyRating() throws Exception {
		BlogPosting blogPosting =
			testDeleteBlogPostingMyRating_addBlogPosting();

		assertHttpResponseStatusCode(
			204,
			BlogPostingResource.deleteBlogPostingMyRatingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404,
			BlogPostingResource.getBlogPostingMyRatingHttpResponse(
				blogPosting.getId()));

		assertHttpResponseStatusCode(
			404, BlogPostingResource.getBlogPostingMyRatingHttpResponse(0L));
	}

	protected BlogPosting testDeleteBlogPostingMyRating_addBlogPosting()
		throws Exception {

		return BlogPostingResource.postSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	@Test
	public void testGetBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetSiteBlogPostingsPage() throws Exception {
		Long siteId = testGetSiteBlogPostingsPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteBlogPostingsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			BlogPosting irrelevantBlogPosting =
				testGetSiteBlogPostingsPage_addBlogPosting(
					irrelevantSiteId, randomIrrelevantBlogPosting());

			Page<BlogPosting> page =
				BlogPostingResource.getSiteBlogPostingsPage(
					irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantBlogPosting),
				(List<BlogPosting>)page.getItems());
			assertValid(page);
		}

		BlogPosting blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		BlogPosting blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		Page<BlogPosting> page = BlogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPosting1, blogPosting2),
			(List<BlogPosting>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteBlogPostingsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = randomBlogPosting();

		blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting1);

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> page =
				BlogPostingResource.getSiteBlogPostingsPage(
					siteId, null,
					getFilterString(entityField, "between", blogPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPosting1),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPosting blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> page =
				BlogPostingResource.getSiteBlogPostingsPage(
					siteId, null,
					getFilterString(entityField, "eq", blogPosting1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPosting1),
				(List<BlogPosting>)page.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingsPageWithPagination() throws Exception {
		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		BlogPosting blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		BlogPosting blogPosting3 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, randomBlogPosting());

		Page<BlogPosting> page1 = BlogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		List<BlogPosting> blogPostings1 = (List<BlogPosting>)page1.getItems();

		Assert.assertEquals(blogPostings1.toString(), 2, blogPostings1.size());

		Page<BlogPosting> page2 = BlogPostingResource.getSiteBlogPostingsPage(
			siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<BlogPosting> blogPostings2 = (List<BlogPosting>)page2.getItems();

		Assert.assertEquals(blogPostings2.toString(), 1, blogPostings2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPosting1, blogPosting2, blogPosting3),
			new ArrayList<BlogPosting>() {
				{
					addAll(blogPostings1);
					addAll(blogPostings2);
				}
			});
	}

	@Test
	public void testGetSiteBlogPostingsPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = randomBlogPosting();
		BlogPosting blogPosting2 = randomBlogPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				blogPosting1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting1);

		blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting2);

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> ascPage =
				BlogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPosting1, blogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage =
				BlogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPosting2, blogPosting1),
				(List<BlogPosting>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteBlogPostingsPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteBlogPostingsPage_getSiteId();

		BlogPosting blogPosting1 = randomBlogPosting();
		BlogPosting blogPosting2 = randomBlogPosting();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(blogPosting1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(blogPosting2, entityField.getName(), "Bbb");
		}

		blogPosting1 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting1);

		blogPosting2 = testGetSiteBlogPostingsPage_addBlogPosting(
			siteId, blogPosting2);

		for (EntityField entityField : entityFields) {
			Page<BlogPosting> ascPage =
				BlogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPosting1, blogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage =
				BlogPostingResource.getSiteBlogPostingsPage(
					siteId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPosting2, blogPosting1),
				(List<BlogPosting>)descPage.getItems());
		}
	}

	protected BlogPosting testGetSiteBlogPostingsPage_addBlogPosting(
			Long siteId, BlogPosting blogPosting)
		throws Exception {

		return BlogPostingResource.postSiteBlogPosting(siteId, blogPosting);
	}

	protected Long testGetSiteBlogPostingsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteBlogPostingsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testPostSiteBlogPosting() throws Exception {
		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting postBlogPosting = testPostSiteBlogPosting_addBlogPosting(
			randomBlogPosting);

		assertEquals(randomBlogPosting, postBlogPosting);
		assertValid(postBlogPosting);
	}

	protected BlogPosting testPostSiteBlogPosting_addBlogPosting(
			BlogPosting blogPosting)
		throws Exception {

		return BlogPostingResource.postSiteBlogPosting(
			testGetSiteBlogPostingsPage_getSiteId(), blogPosting);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		Assert.assertTrue(
			blogPosting1 + " does not equal " + blogPosting2,
			equals(blogPosting1, blogPosting2));
	}

	protected void assertEquals(
		List<BlogPosting> blogPostings1, List<BlogPosting> blogPostings2) {

		Assert.assertEquals(blogPostings1.size(), blogPostings2.size());

		for (int i = 0; i < blogPostings1.size(); i++) {
			BlogPosting blogPosting1 = blogPostings1.get(i);
			BlogPosting blogPosting2 = blogPostings2.get(i);

			assertEquals(blogPosting1, blogPosting2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BlogPosting> blogPostings1, List<BlogPosting> blogPostings2) {

		Assert.assertEquals(blogPostings1.size(), blogPostings2.size());

		for (BlogPosting blogPosting1 : blogPostings1) {
			boolean contains = false;

			for (BlogPosting blogPosting2 : blogPostings2) {
				if (equals(blogPosting1, blogPosting2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				blogPostings2 + " does not contain " + blogPosting1, contains);
		}
	}

	protected void assertValid(BlogPosting blogPosting) {
		boolean valid = true;

		if (blogPosting.getDateCreated() == null) {
			valid = false;
		}

		if (blogPosting.getDateModified() == null) {
			valid = false;
		}

		if (blogPosting.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(blogPosting.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (blogPosting.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"alternativeHeadline", additionalAssertFieldName)) {

				if (blogPosting.getAlternativeHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (blogPosting.getArticleBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (blogPosting.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (blogPosting.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (blogPosting.getDatePublished() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (blogPosting.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (blogPosting.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (blogPosting.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (blogPosting.getHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (blogPosting.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (blogPosting.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (blogPosting.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (blogPosting.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (blogPosting.getTaxonomyCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (blogPosting.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (blogPosting.getViewableBy() == null) {
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

	protected void assertValid(Page<BlogPosting> page) {
		boolean valid = false;

		Collection<BlogPosting> blogPostings = page.getItems();

		int size = blogPostings.size();

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
		BlogPosting blogPosting1, BlogPosting blogPosting2) {

		if (blogPosting1 == blogPosting2) {
			return true;
		}

		if (!Objects.equals(
				blogPosting1.getSiteId(), blogPosting2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getAggregateRating(),
						blogPosting2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"alternativeHeadline", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						blogPosting1.getAlternativeHeadline(),
						blogPosting2.getAlternativeHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getArticleBody(),
						blogPosting2.getArticleBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getCreator(), blogPosting2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getCustomFields(),
						blogPosting2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDateCreated(),
						blogPosting2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDateModified(),
						blogPosting2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("datePublished", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDatePublished(),
						blogPosting2.getDatePublished())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getDescription(),
						blogPosting2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getEncodingFormat(),
						blogPosting2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getFriendlyUrlPath(),
						blogPosting2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getHeadline(),
						blogPosting2.getHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getId(), blogPosting2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getImage(), blogPosting2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getKeywords(),
						blogPosting2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getNumberOfComments(),
						blogPosting2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getRelatedContents(),
						blogPosting2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						blogPosting1.getTaxonomyCategories(),
						blogPosting2.getTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						blogPosting1.getTaxonomyCategoryIds(),
						blogPosting2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						blogPosting1.getViewableBy(),
						blogPosting2.getViewableBy())) {

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
		if (!(_blogPostingResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_blogPostingResource;

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
		EntityField entityField, String operator, BlogPosting blogPosting) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("alternativeHeadline")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getAlternativeHeadline()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getArticleBody()));
			sb.append("'");

			return sb.toString();
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
							blogPosting.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(blogPosting.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(blogPosting.getDateCreated()));
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
							blogPosting.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							blogPosting.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(blogPosting.getDateModified()));
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
							blogPosting.getDatePublished(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							blogPosting.getDatePublished(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(blogPosting.getDatePublished()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getFriendlyUrlPath()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(blogPosting.getHeadline()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("relatedContents")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected BlogPosting randomBlogPosting() throws Exception {
		return new BlogPosting() {
			{
				alternativeHeadline = RandomTestUtil.randomString();
				articleBody = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				headline = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected BlogPosting randomIrrelevantBlogPosting() throws Exception {
		BlogPosting randomIrrelevantBlogPosting = randomBlogPosting();

		randomIrrelevantBlogPosting.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantBlogPosting;
	}

	protected BlogPosting randomPatchBlogPosting() throws Exception {
		return randomBlogPosting();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBlogPostingResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.BlogPostingResource
		_blogPostingResource;

}