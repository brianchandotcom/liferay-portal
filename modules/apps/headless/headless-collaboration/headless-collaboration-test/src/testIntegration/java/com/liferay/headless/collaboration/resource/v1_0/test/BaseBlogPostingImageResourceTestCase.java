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

package com.liferay.headless.collaboration.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingImageResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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

import org.apache.commons.beanutils.BeanUtils;
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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-collaboration/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteBlogPostingImage() throws Exception {
		BlogPostingImage blogPostingImage =
			testDeleteBlogPostingImage_addBlogPostingImage();

		assertResponseCode(
			200,
			invokeDeleteBlogPostingImageResponse(blogPostingImage.getId()));

		assertResponseCode(
			404, invokeGetBlogPostingImageResponse(blogPostingImage.getId()));
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

	@Test
	public void testGetContentSpaceBlogPostingImagesPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceBlogPostingImagesPage_getContentSpaceId();

		BlogPostingImage blogPostingImage1 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());
		BlogPostingImage blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		Page<BlogPostingImage> page =
			invokeGetContentSpaceBlogPostingImagesPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				(String)null);

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
					contentSpaceId,
					getFilterString(entityField, "eq", blogPostingImage1),
					Pagination.of(2, 1), (String)null);

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
		BlogPostingImage blogPostingImage2 =
			testGetContentSpaceBlogPostingImagesPage_addBlogPostingImage(
				contentSpaceId, randomBlogPostingImage());

		for (EntityField entityField : entityFields) {
			Page<BlogPostingImage> page =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId,
					getFilterString(entityField, "eq", blogPostingImage1),
					Pagination.of(2, 1), (String)null);

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
				contentSpaceId, (String)null, Pagination.of(2, 1),
				(String)null);

		List<BlogPostingImage> blogPostingImages1 =
			(List<BlogPostingImage>)page1.getItems();

		Assert.assertEquals(
			blogPostingImages1.toString(), 2, blogPostingImages1.size());

		Page<BlogPostingImage> page2 =
			invokeGetContentSpaceBlogPostingImagesPage(
				contentSpaceId, (String)null, Pagination.of(2, 2),
				(String)null);

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
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPostingImage1, blogPostingImage2),
				(List<BlogPostingImage>)ascPage.getItems());

			Page<BlogPostingImage> descPage =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, (String)null, Pagination.of(2, 1),
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
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(blogPostingImage1, blogPostingImage2),
				(List<BlogPostingImage>)ascPage.getItems());

			Page<BlogPostingImage> descPage =
				invokeGetContentSpaceBlogPostingImagesPage(
					contentSpaceId, (String)null, Pagination.of(2, 1),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(blogPostingImage2, blogPostingImage1),
				(List<BlogPostingImage>)descPage.getItems());
		}
	}

	@Test
	public void testPatchBlogPostingImage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceBlogPostingImage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutBlogPostingImage() throws Exception {
		Assert.assertTrue(true);
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

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(BlogPostingImage blogPostingImage) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<BlogPostingImage> page) {
		boolean valid = false;

		Collection<BlogPostingImage> blogPostingImages = page.getItems();

		int size = blogPostingImages.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
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
			sb.append("'");
			sb.append(String.valueOf(blogPostingImage.getViewableBy()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected boolean invokeDeleteBlogPostingImage(Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteBlogPostingImageResponse(
			Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPostingImage invokeGetBlogPostingImage(
			Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPostingImage.class);
	}

	protected Http.Response invokeGetBlogPostingImageResponse(
			Long blogPostingImageId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<BlogPostingImage> invokeGetContentSpaceBlogPostingImagesPage(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceBlogPostingImagesLocation(
				contentSpaceId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<BlogPostingImage>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceBlogPostingImagesPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceBlogPostingImagesLocation(
				contentSpaceId, filterString, pagination, sortString));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPostingImage invokePatchBlogPostingImage(
			Long blogPostingImageId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPostingImage.class);
	}

	protected Http.Response invokePatchBlogPostingImageResponse(
			Long blogPostingImageId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPostingImage invokePostContentSpaceBlogPostingImage(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/blog-posting-images",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPostingImage.class);
	}

	protected Http.Response invokePostContentSpaceBlogPostingImageResponse(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/blog-posting-images",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPostingImage invokePutBlogPostingImage(
			Long blogPostingImageId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPostingImage.class);
	}

	protected Http.Response invokePutBlogPostingImageResponse(
			Long blogPostingImageId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{blog-posting-image-id}",
					blogPostingImageId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected BlogPostingImage randomBlogPostingImage() {
		return new BlogPostingImage() {
			{
				contentUrl = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
				viewableBy = RandomTestUtil.randomString();
			}
		};
	}

	protected BlogPostingImage testDeleteBlogPostingImage_addBlogPostingImage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BlogPostingImage testGetBlogPostingImage_addBlogPostingImage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected BlogPostingImage
			testPostContentSpaceBlogPostingImage_addBlogPostingImage(
				BlogPostingImage blogPostingImage)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected BlogPostingImage testPutBlogPostingImage_addBlogPostingImage()
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

	private String _getContentSpaceBlogPostingImagesLocation(
		Long contentSpaceId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/blog-posting-images",
					contentSpaceId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
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
	private BlogPostingImageResource _blogPostingImageResource;

	private URL _resourceURL;

}