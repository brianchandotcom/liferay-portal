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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.BlogPostingSerDes;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
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
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

		assertResponseCode(
			204, invokeDeleteBlogPostingResponse(blogPosting.getId()));

		assertResponseCode(
			404, invokeGetBlogPostingResponse(blogPosting.getId()));
	}

	protected BlogPosting testDeleteBlogPosting_addBlogPosting()
		throws Exception {

		return invokePostSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	protected void invokeDeleteBlogPosting(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteBlogPostingResponse(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testGetBlogPosting_addBlogPosting();

		BlogPosting getBlogPosting = invokeGetBlogPosting(
			postBlogPosting.getId());

		assertEquals(postBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testGetBlogPosting_addBlogPosting() throws Exception {
		return invokePostSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	protected BlogPosting invokeGetBlogPosting(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return BlogPostingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetBlogPostingResponse(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testPatchBlogPosting_addBlogPosting();

		BlogPosting randomPatchBlogPosting = randomPatchBlogPosting();

		BlogPosting patchBlogPosting = invokePatchBlogPosting(
			postBlogPosting.getId(), randomPatchBlogPosting);

		BlogPosting expectedPatchBlogPosting = (BlogPosting)BeanUtils.cloneBean(
			postBlogPosting);

		_beanUtilsBean.copyProperties(
			expectedPatchBlogPosting, randomPatchBlogPosting);

		BlogPosting getBlogPosting = invokeGetBlogPosting(
			patchBlogPosting.getId());

		assertEquals(expectedPatchBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testPatchBlogPosting_addBlogPosting()
		throws Exception {

		return invokePostSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	protected BlogPosting invokePatchBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			BlogPostingSerDes.toJSON(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return BlogPostingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePatchBlogPostingResponse(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			BlogPostingSerDes.toJSON(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutBlogPosting() throws Exception {
		BlogPosting postBlogPosting = testPutBlogPosting_addBlogPosting();

		BlogPosting randomBlogPosting = randomBlogPosting();

		BlogPosting putBlogPosting = invokePutBlogPosting(
			postBlogPosting.getId(), randomBlogPosting);

		assertEquals(randomBlogPosting, putBlogPosting);
		assertValid(putBlogPosting);

		BlogPosting getBlogPosting = invokeGetBlogPosting(
			putBlogPosting.getId());

		assertEquals(randomBlogPosting, getBlogPosting);
		assertValid(getBlogPosting);
	}

	protected BlogPosting testPutBlogPosting_addBlogPosting() throws Exception {
		return invokePostSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	protected BlogPosting invokePutBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			BlogPostingSerDes.toJSON(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return BlogPostingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutBlogPostingResponse(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			BlogPostingSerDes.toJSON(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath("/blog-postings/{blogPostingId}", blogPostingId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteBlogPostingMyRating() throws Exception {
		BlogPosting blogPosting =
			testDeleteBlogPostingMyRating_addBlogPosting();

		assertResponseCode(
			204, invokeDeleteBlogPostingMyRatingResponse(blogPosting.getId()));

		assertResponseCode(
			404, invokeGetBlogPostingMyRatingResponse(blogPosting.getId()));
	}

	protected BlogPosting testDeleteBlogPostingMyRating_addBlogPosting()
		throws Exception {

		return invokePostSiteBlogPosting(
			testGroup.getGroupId(), randomBlogPosting());
	}

	protected void invokeDeleteBlogPostingMyRating(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteBlogPostingMyRatingResponse(
			Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokeGetBlogPostingMyRating(Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokeGetBlogPostingMyRatingResponse(
			Long blogPostingId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePostBlogPostingMyRating(
			Long blogPostingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostBlogPostingMyRatingResponse(
			Long blogPostingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutBlogPostingMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePutBlogPostingMyRating(
			Long blogPostingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutBlogPostingMyRatingResponse(
			Long blogPostingId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-postings/{blogPostingId}/my-rating", blogPostingId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

			Page<BlogPosting> page = invokeGetSiteBlogPostingsPage(
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

		Page<BlogPosting> page = invokeGetSiteBlogPostingsPage(
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
			Page<BlogPosting> page = invokeGetSiteBlogPostingsPage(
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
			Page<BlogPosting> page = invokeGetSiteBlogPostingsPage(
				siteId, null, getFilterString(entityField, "eq", blogPosting1),
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

		Page<BlogPosting> page1 = invokeGetSiteBlogPostingsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		List<BlogPosting> blogPostings1 = (List<BlogPosting>)page1.getItems();

		Assert.assertEquals(blogPostings1.toString(), 2, blogPostings1.size());

		Page<BlogPosting> page2 = invokeGetSiteBlogPostingsPage(
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
			Page<BlogPosting> ascPage = invokeGetSiteBlogPostingsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPosting1, blogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage = invokeGetSiteBlogPostingsPage(
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
			Page<BlogPosting> ascPage = invokeGetSiteBlogPostingsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPosting1, blogPosting2),
				(List<BlogPosting>)ascPage.getItems());

			Page<BlogPosting> descPage = invokeGetSiteBlogPostingsPage(
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

		return invokePostSiteBlogPosting(siteId, blogPosting);
	}

	protected Long testGetSiteBlogPostingsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteBlogPostingsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<BlogPosting> invokeGetSiteBlogPostingsPage(
			Long siteId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/blog-postings", siteId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return Page.of(string, BlogPostingSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteBlogPostingsPageResponse(
			Long siteId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/blog-postings", siteId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

		return invokePostSiteBlogPosting(
			testGetSiteBlogPostingsPage_getSiteId(), blogPosting);
	}

	protected BlogPosting invokePostSiteBlogPosting(
			Long siteId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			BlogPostingSerDes.toJSON(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/blog-postings", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return BlogPostingSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteBlogPostingResponse(
			Long siteId, BlogPosting blogPosting)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			BlogPostingSerDes.toJSON(blogPosting),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/blog-postings", siteId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

	protected BlogPosting randomBlogPosting() {
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

	protected BlogPosting randomIrrelevantBlogPosting() {
		BlogPosting randomIrrelevantBlogPosting = randomBlogPosting();

		randomIrrelevantBlogPosting.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantBlogPosting;
	}

	protected BlogPosting randomPatchBlogPosting() {
		return randomBlogPosting();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

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

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
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
	private BlogPostingResource _blogPostingResource;

	private URL _resourceURL;

}