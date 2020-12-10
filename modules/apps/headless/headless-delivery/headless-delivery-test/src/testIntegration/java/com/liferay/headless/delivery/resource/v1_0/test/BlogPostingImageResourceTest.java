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
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.File;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class BlogPostingImageResourceTest
	extends BaseBlogPostingImageResourceTestCase {

	@Test
	public void testPostSiteBlogPostingImageRollback() throws Exception {
		Folder folder = BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId());

		Assert.assertNull(folder);

		BlogPostingImage blogPostingImage = randomBlogPostingImage();

		try {
			testPostSiteBlogPostingImage_addBlogPostingImage(
				blogPostingImage,
				HashMapBuilder.put(
					"file",
					() -> {
						File tempFile = FileUtil.createTempFile("*,?", "txt");

						FileUtil.write(
							tempFile, TestDataConstants.TEST_BYTE_ARRAY);

						return tempFile;
					}
				).build());

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertTrue(throwable instanceof Problem.ProblemException);
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
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"fileExtension", "sizeInBytes"};
	}

	@Override
	protected Map<String, File> getMultipartFiles() throws Exception {
		return HashMapBuilder.<String, File>put(
			"file",
			() -> FileUtil.createTempFile(TestDataConstants.TEST_BYTE_ARRAY)
		).build();
	}

	@Override
	protected BlogPostingImage testGraphQLBlogPostingImage_addBlogPostingImage()
		throws Exception {

		return testDeleteBlogPostingImage_addBlogPostingImage();
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