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

package com.liferay.headless.document.library.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Comment;
import com.liferay.headless.document.library.internal.dto.v1_0.CommentImpl;
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
public abstract class BaseCommentResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetComment() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetCommentCommentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetDocumentCommentsPage() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetComment( Long commentId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/comments/{comment-id}",
				commentId
			);

	}
	protected Response invokeGetCommentCommentsPage( Long commentId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/comments/{comment-id}/comments",
				commentId 
			);

	}
	protected Response invokeGetDocumentCommentsPage( Long documentId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/documents/{document-id}/comments",
				documentId 
			);

	}

	protected Comment randomComment() {
		Comment comment = new CommentImpl();

comment.setDateCreated(RandomTestUtil.nextDate());
comment.setDateModified(RandomTestUtil.nextDate());
comment.setHasComments(RandomTestUtil.randomBoolean());
comment.setId(RandomTestUtil.randomLong());
comment.setText(RandomTestUtil.randomString());
		return comment;
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