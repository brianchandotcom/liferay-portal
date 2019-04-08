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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingImageResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public abstract class BaseBlogPostingImageResourceTestCase {

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

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testDeleteBlogPostingImage_addBlogPostingImage();

		assertResponseCode(
			204,
			invokeDeleteBlogPostingImageResponse(blogPostingImage.getId()));

		assertResponseCode(
			404, invokeGetBlogPostingImageResponse(blogPostingImage.getId()));
	}

	protected BlogPostingImage testDeleteBlogPostingImage_addBlogPostingImage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteBlogPostingImage(Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blogPostingImageId}",
					blogPostingImageId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteBlogPostingImageResponse(
			Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blogPostingImageId}",
					blogPostingImageId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetBlogPostingImage() throws Exception {
		BlogPostingImage postBlogPostingImage =
			testGetBlogPostingImage_addBlogPostingImage();

		BlogPostingImage getBlogPostingImage = invokeGetBlogPostingImage(
			postBlogPostingImage.getId());

		assertEquals(postBlogPostingImage, getBlogPostingImage);
		assertValid(getBlogPostingImage);
	}

	protected BlogPostingImage testGetBlogPostingImage_addBlogPostingImage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BlogPostingImage invokeGetBlogPostingImage(
			Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blogPostingImageId}",
					blogPostingImageId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, BlogPostingImage.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetBlogPostingImageResponse(
			Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blogPostingImageId}",
					blogPostingImageId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			BlogPostingImage irrelevantBlogPostingImage =
				testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
					irrelevantContentSpaceId,
					randomIrrelevantBlogPostingImage());

			Page<BlogPostingImage> page =
				invokeGetContentSpaceBlogPostingImagesPage(
					irrelevantContentSpaceId, null, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantBlogPostingImage),
				(List<BlogPostingImage>)page.getItems());
			assertValid(page);
		}

		BlogPostingImage blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		Page<BlogPostingImage> page =
			invokeGetContentSpaceBlogPostingImagesPage(
				contentSpaceId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(blogPostingImage1, blogPostingImage2),
			(List<BlogPostingImage>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();

		BlogPostingImage blogPostingImage1 = randomBlogPostingImage();
		BlogPostingImage blogPostingImage2 = randomBlogPostingImage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				blogPostingImage1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, blogPostingImage1);

		Thread.sleep(1000);

		blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, blogPostingImage2);

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> page =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, null,
					getFilterString(entityField, "eq", blogPostingImage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPostingImage1),
				(List<BlogPostingImage>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();

		BlogPostingImage blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		BlogPostingImage blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> page =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, null,
					getFilterString(entityField, "eq", blogPostingImage1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(blogPostingImage1),
				(List<BlogPostingImage>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();

		BlogPostingImage blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		BlogPostingImage blogPostingImage3 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		Page<BlogPostingImage> page1 =
			invokeGetContentSpaceBlogPostingImagesPage(
				contentSpaceId, null, null, Pagination.of(1, 2), null);

		List<BlogPostingImage> blogPostingImages1 =
			(List<BlogPostingImage>)page1.getItems();

		Assert.assertEquals(
			blogPostingImages1.toString(), 2, blogPostingImages1.size());

		Page<BlogPostingImage> page2 =
			invokeGetContentSpaceBlogPostingImagesPage(
				contentSpaceId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<BlogPostingImage> blogPostingImages2 =
			(List<BlogPostingImage>)page2.getItems();

		Assert.assertEquals(
			blogPostingImages2.toString(), 1, blogPostingImages2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				blogPostingImage1, blogPostingImage2, blogPostingImage3),
			new ArrayList<BlogPostingImage>() {
				{
					addAll(blogPostingImages1);
					addAll(blogPostingImages2);
				}
			});
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();

		BlogPostingImage blogPostingImage1 = randomBlogPostingImage();
		BlogPostingImage blogPostingImage2 = randomBlogPostingImage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				blogPostingImage1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, blogPostingImage1);

		Thread.sleep(1000);

		blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, blogPostingImage2);

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> ascPage =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPostingImage1, blogPostingImage2),
				(List<BlogPostingImage>)ascPage.getItems());

			Page<BlogPostingImage> descPage =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPostingImage2, blogPostingImage1),
				(List<BlogPostingImage>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();

		BlogPostingImage blogPostingImage1 = randomBlogPostingImage();
		BlogPostingImage blogPostingImage2 = randomBlogPostingImage();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				blogPostingImage1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				blogPostingImage2, entityField.getName(), "Bbb");
		}

		blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, blogPostingImage1);

		blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, blogPostingImage2);

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> ascPage =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPostingImage1, blogPostingImage2),
				(List<BlogPostingImage>)ascPage.getItems());

			Page<BlogPostingImage> descPage =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPostingImage2, blogPostingImage1),
				(List<BlogPostingImage>)descPage.getItems());
		}
	}

	protected BlogPostingImage
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				Long contentSpaceId, BlogPostingImage blogPostingImage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceBlogPostingImagesPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceBlogPostingImagesPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<BlogPostingImage> invokeGetContentSpaceBlogPostingImagesPage(
			Long contentSpaceId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/blog-posting-images",
					contentSpaceId);

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

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<BlogPostingImage>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceBlogPostingImagesPageResponse(
			Long contentSpaceId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/blog-posting-images",
					contentSpaceId);

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
	public void testPostContentSpaceBlogPostingImage() throws Exception {
		Assert.assertTrue(true);
	}

	protected BlogPostingImage
			testPostContentSpaceBlogPostingImage_addBlogPostingImage(
				BlogPostingImage blogPostingImage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BlogPostingImage invokePostContentSpaceBlogPostingImage(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.addPart(
			"blogPostingImage",
			inputObjectMapper.writeValueAsString(multipartBody.getValues()));

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		options.addFilePart(
			"file", binaryFile.getFileName(),
			FileUtil.getBytes(binaryFile.getInputStream()), contentType,
			"UTF-8");

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/blog-posting-images",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, BlogPostingImage.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceBlogPostingImageResponse(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/blog-posting-images",
					contentSpaceId);

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
		BlogPostingImage blogPostingImage1,
		BlogPostingImage blogPostingImage2) {

		Assert.assertTrue(
			blogPostingImage1 + " does not equal " + blogPostingImage2,
			equals(blogPostingImage1, blogPostingImage2));
	}

	protected void assertEquals(
		List<BlogPostingImage> blogPostingImages1,
		List<BlogPostingImage> blogPostingImages2) {

		Assert.assertEquals(
			blogPostingImages1.size(), blogPostingImages2.size());

		for (int i = 0; i < blogPostingImages1.size(); i++) {
			BlogPostingImage blogPostingImage1 = blogPostingImages1.get(i);
			BlogPostingImage blogPostingImage2 = blogPostingImages2.get(i);

			assertEquals(blogPostingImage1, blogPostingImage2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<BlogPostingImage> blogPostingImages1,
		List<BlogPostingImage> blogPostingImages2) {

		Assert.assertEquals(
			blogPostingImages1.size(), blogPostingImages2.size());

		for (BlogPostingImage blogPostingImage1 : blogPostingImages1) {
			boolean contains = false;

			for (BlogPostingImage blogPostingImage2 : blogPostingImages2) {
				if (equals(blogPostingImage1, blogPostingImage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				blogPostingImages2 + " does not contain " + blogPostingImage1,
				contains);
		}
	}

	protected void assertValid(BlogPostingImage blogPostingImage) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<BlogPostingImage> page) {
		boolean valid = false;

		Collection<BlogPostingImage> blogPostingImages = page.getItems();

		int size = blogPostingImages.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		BlogPostingImage blogPostingImage1,
		BlogPostingImage blogPostingImage2) {

		if (blogPostingImage1 == blogPostingImage2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_blogPostingImageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_blogPostingImageResource;

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
		EntityField entityField, String operator,
		BlogPostingImage blogPostingImage) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getFileExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getTitle()));
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

	protected BlogPostingImage randomBlogPostingImage() {
		return new BlogPostingImage() {
			{
				contentUrl = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected BlogPostingImage randomIrrelevantBlogPostingImage() {
		return randomBlogPostingImage();
	}

	protected BlogPostingImage randomPatchBlogPostingImage() {
		return randomBlogPostingImage();
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

	protected String contentType = "application/json";
	protected Group irrelevantGroup;
	protected Group testGroup;
	protected String userNameAndPassword = "test@liferay.com:test";

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

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", contentType);

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
		BaseBlogPostingImageResourceTestCase.class);

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
	private BlogPostingImageResource _blogPostingImageResource;

	private URL _resourceURL;

}