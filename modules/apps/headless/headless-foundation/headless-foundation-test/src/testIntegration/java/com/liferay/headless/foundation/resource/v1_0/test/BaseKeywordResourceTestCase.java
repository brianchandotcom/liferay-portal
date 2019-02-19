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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.internal.dto.v1_0.KeywordImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
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
public abstract class BaseKeywordResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceKeywordsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceKeyword() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteKeyword() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetKeyword() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutKeyword() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetContentSpaceKeywordsPage( Long contentSpaceId , Filter filter , Pagination pagination , Sort[] sorts ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-spaces/{content-space-id}/keywords",
				contentSpaceId , filter  , sorts
			);

	}
	protected Response invokePostContentSpaceKeyword( Long contentSpaceId , Keyword keyword ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				keyword
			).when(
			).post(
				_resourceURL + "/content-spaces/{content-space-id}/keywords",
				contentSpaceId 
			);

	}
	protected Response invokeDeleteKeyword( Long keywordId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/keywords/{keyword-id}",
				keywordId
			);

	}
	protected Response invokeGetKeyword( Long keywordId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/keywords/{keyword-id}",
				keywordId
			);

	}
	protected Response invokePutKeyword( Long keywordId , Keyword keyword ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				keyword
			).when(
			).put(
				_resourceURL + "/keywords/{keyword-id}",
				keywordId 
			);

	}

	protected Keyword randomKeyword() {
		Keyword keyword = new KeywordImpl();

contentSpace = RandomTestUtil.randomLong();
dateCreated = RandomTestUtil.nextDate();
dateModified = RandomTestUtil.nextDate();
id = RandomTestUtil.randomLong();
name = RandomTestUtil.randomString();
		return keyword;
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