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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
	public void testDeleteImageObject() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContentSpaceBlogPostingImagesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetImageObject() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceBlogPostingImage() throws Exception {
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

	protected boolean invokeDeleteImageObject(Long imageObjectId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{image-object-id}", imageObjectId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteImageObjectResponse(Long imageObjectId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{image-object-id}", imageObjectId));

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

	protected BlogPostingImage invokeGetImageObject(Long imageObjectId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{image-object-id}", imageObjectId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), BlogPostingImage.class);
	}

	protected Http.Response invokeGetImageObjectResponse(Long imageObjectId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/blog-posting-images/{image-object-id}", imageObjectId));

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

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public int getItemsPerPage() {
			return itemsPerPage;
		}

		public int getLastPageNumber() {
			return lastPageNumber;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected int itemsPerPage;

		@JsonProperty
		protected int lastPageNumber;

		@JsonProperty("page")
		protected int pageNumber;

		@JsonProperty
		protected int totalCount;

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

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}