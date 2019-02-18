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
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseBlogPostingResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-collaboration/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteBlogPosting() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetBlogPosting() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutBlogPosting() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetContentSpaceBlogPostingsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceBlogPosting() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeDeleteBlogPosting( Long blogPostingId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/blog-postings/{blog-posting-id}",
				blogPostingId
			);

	}
	protected Response invokeGetBlogPosting( Long blogPostingId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/blog-postings/{blog-posting-id}",
				blogPostingId
			);

	}
	protected Response invokePutBlogPosting( Long blogPostingId , BlogPosting blogPosting ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				blogPosting
			).when(
			).put(
				_resourceURL + "/blog-postings/{blog-posting-id}",
				blogPostingId 
			);

	}
	protected Response invokeGetContentSpaceBlogPostingsPage( Long contentSpaceId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-spaces/{content-space-id}/blog-postings",
				contentSpaceId 
			);

	}
	protected Response invokePostContentSpaceBlogPosting( Long contentSpaceId , BlogPosting blogPosting ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				blogPosting
			).when(
			).post(
				_resourceURL + "/content-spaces/{content-space-id}/blog-postings",
				contentSpaceId 
			);

	}

	protected BlogPosting randomBlogPosting() {
		BlogPosting blogPosting = new BlogPostingImpl();

blogPosting.setAlternativeHeadline(RandomTestUtil.randomString());
blogPosting.setArticleBody(RandomTestUtil.randomString());
blogPosting.setCaption(RandomTestUtil.randomString());
blogPosting.setContentSpace(RandomTestUtil.randomLong());
blogPosting.setDateCreated(RandomTestUtil.nextDate());
blogPosting.setDateModified(RandomTestUtil.nextDate());
blogPosting.setDatePublished(RandomTestUtil.nextDate());
blogPosting.setDescription(RandomTestUtil.randomString());
blogPosting.setEncodingFormat(RandomTestUtil.randomString());
blogPosting.setFriendlyUrlPath(RandomTestUtil.randomString());
blogPosting.setHeadline(RandomTestUtil.randomString());
blogPosting.setId(RandomTestUtil.randomLong());
blogPosting.setImageId(RandomTestUtil.randomLong());
		return blogPosting;
	}

	protected Group testGroup;

	private RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}