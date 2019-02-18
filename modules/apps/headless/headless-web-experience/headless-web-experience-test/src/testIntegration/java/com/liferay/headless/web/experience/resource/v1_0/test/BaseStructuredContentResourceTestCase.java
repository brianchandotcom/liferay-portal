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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.internal.dto.v1_0.StructuredContentImpl;
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
public abstract class BaseStructuredContentResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-web-experience/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceContentStructureStructuredContentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetContentSpaceStructuredContentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPatchStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutStructuredContent() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetContentSpaceContentStructureStructuredContentsPage( Long contentSpaceId , Long contentStructureId , Filter filter , Pagination pagination , Sort[] sorts ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-structures/{content-structure-id}/structured-contents",
				contentSpaceId , contentStructureId , filter  , sorts
			);

	}
	protected Response invokeGetContentSpaceStructuredContentsPage( Long contentSpaceId , Filter filter , Pagination pagination , Sort[] sorts ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-spaces/{content-space-id}/structured-contents",
				contentSpaceId , filter  , sorts
			);

	}
	protected Response invokePostContentSpaceStructuredContent( Long contentSpaceId , StructuredContent structuredContent ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				structuredContent
			).when(
			).post(
				_resourceURL + "/content-spaces/{content-space-id}/structured-contents",
				contentSpaceId 
			);

	}
	protected Response invokeDeleteStructuredContent( Long structuredContentId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/structured-contents/{structured-content-id}",
				structuredContentId
			);

	}
	protected Response invokeGetStructuredContent( Long structuredContentId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/structured-contents/{structured-content-id}",
				structuredContentId
			);

	}
	protected Response invokePatchStructuredContent( Long structuredContentId , StructuredContent structuredContent ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).patch(
				_resourceURL + "/structured-contents/{structured-content-id}",
				structuredContentId , structuredContent
			);

	}
	protected Response invokePutStructuredContent( Long structuredContentId , StructuredContent structuredContent ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				structuredContent
			).when(
			).put(
				_resourceURL + "/structured-contents/{structured-content-id}",
				structuredContentId 
			);

	}

	protected StructuredContent randomStructuredContent() {
		StructuredContent structuredContent = new StructuredContentImpl();

structuredContent.setContentSpace(RandomTestUtil.randomLong());
structuredContent.setContentStructureId(RandomTestUtil.randomLong());
structuredContent.setDateCreated(RandomTestUtil.nextDate());
structuredContent.setDateModified(RandomTestUtil.nextDate());
structuredContent.setDatePublished(RandomTestUtil.nextDate());
structuredContent.setDescription(RandomTestUtil.randomString());
structuredContent.setId(RandomTestUtil.randomLong());
structuredContent.setLastReviewed(RandomTestUtil.nextDate());
structuredContent.setTitle(RandomTestUtil.randomString());
		return structuredContent;
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