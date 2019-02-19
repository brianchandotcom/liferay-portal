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

import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.internal.dto.v1_0.DocumentImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public abstract class BaseDocumentResourceTestCase {

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
	public void testGetContentSpaceDocumentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceDocument() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteDocument() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetDocument() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetFolderDocumentsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostFolderDocument() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetContentSpaceDocumentsPage( Long contentSpaceId , Filter filter , Pagination pagination , Sort[] sorts ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-spaces/{content-space-id}/documents",
				contentSpaceId , filter  , sorts
			);

	}
	protected Response invokePostContentSpaceDocument( Long contentSpaceId , MultipartBody multipartBody ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).post(
				_resourceURL + "/content-spaces/{content-space-id}/documents",
				contentSpaceId , multipartBody
			);

	}
	protected Response invokeDeleteDocument( Long documentId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/documents/{document-id}",
				documentId
			);

	}
	protected Response invokeGetDocument( Long documentId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/documents/{document-id}",
				documentId
			);

	}
	protected Response invokeGetFolderDocumentsPage( Long folderId , Filter filter , Pagination pagination , Sort[] sorts ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/folders/{folder-id}/documents",
				folderId , filter  , sorts
			);

	}
	protected Response invokePostFolderDocument( Long folderId , MultipartBody multipartBody ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).post(
				_resourceURL + "/folders/{folder-id}/documents",
				folderId , multipartBody
			);

	}

	protected Document randomDocument() {
		return new DocumentImpl() {
			{
contentUrl = RandomTestUtil.randomString();
dateCreated = RandomTestUtil.nextDate();
dateModified = RandomTestUtil.nextDate();
description = RandomTestUtil.randomString();
encodingFormat = RandomTestUtil.randomString();
fileExtension = RandomTestUtil.randomString();
folderId = RandomTestUtil.randomLong();
id = RandomTestUtil.randomLong();
title = RandomTestUtil.randomString();			}
		};
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