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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.File;

import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingImageResourceTest
	extends BaseBlogPostingImageResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteBlogPostingImage() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetBlogPostingImage() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteBlogPostingImagesPage() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLPostSiteBlogPostingImage() {
	}

	@Test
	public void testPostSiteBlogPostingImageRollback() throws Exception {
		Folder folder = BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId());

		Assert.assertNull(folder);

		BlogPostingImage blogPostingImage = randomBlogPostingImage();

		blogPostingImage.setTitle("*,?");

		try {
			testPostSiteBlogPostingImage_addBlogPostingImage(
				blogPostingImage, getMultipartFiles());

			Assert.fail();
		}
		catch (Throwable e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}

		folder = BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId());

		Assert.assertNull(folder);
	}

	@Override
	protected void assertValid(
			BlogPostingImage blogPostingImage, Map<String, File> multipartFiles)
		throws Exception {

		Assert.assertEquals(
			new String(FileUtil.getBytes(multipartFiles.get("file"))),
			_read("http://localhost:8080" + blogPostingImage.getContentUrl()));
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"fileExtension"};
	}

	@Override
	protected Map<String, File> getMultipartFiles() throws Exception {
		String randomString = RandomTestUtil.randomString();

		return HashMapBuilder.<String, File>put(
			"file", FileUtil.createTempFile(randomString.getBytes())
		).build();
	}

	private String _read(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

}