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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;
import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.datumedge.hamcrest.json.SameJSONAs;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFolderResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Test
	public void testDeleteFolder() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocumentsRepository() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocumentsRepositoryFoldersPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFolder() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFolderFoldersPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentsRepositoryFolder() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentsRepositoryFolderBatchCreate()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFolderFolderBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutFolder() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokeDeleteFolder(Long folderId) throws Exception {
		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.when(
		).delete(
			_resourceURL + "/folders/{folder-id}", folderId
		);
	}

	protected void invokeGetDocumentsRepository(Long documentsRepositoryId)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.when(
		).get(
			_resourceURL + "/documents-repositories/{documents-repository-id}",
			documentsRepositoryId
		);
	}

	protected void invokeGetDocumentsRepositoryFoldersPage(
			Long documentsRepositoryId, Pagination pagination)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.when(
		).get(
			_resourceURL + "/documents-repositories/{documents-repository-id}" +
			"/folders",
			documentsRepositoryId
		);
	}

	protected Folder invokeGetFolder(Long folderId) throws Exception {
			Response response = invokeGetFolderResponse(folderId);

		return response.as(Folder.class);
	}

	protected Response invokeGetFolderResponse(Long folderId) {
		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/folders/{folder-id}", folderId
		);
	}

	protected void invokeGetFolderFoldersPage(
			Long folderId, Pagination pagination)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.when(
		).get(
			_resourceURL + "/folders/{folder-id}/folders", folderId
		);
	}

	protected Folder invokePostDocumentsRepositoryFolder(
			Long documentsRepositoryId, Folder folder)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		Response response = requestSpecification.body(
			folder
		).when(
		).post(
			_resourceURL + "/documents-repositories/{documents-repository-id}" +
			"/folders",
			documentsRepositoryId
		);

		return response.as(Folder.class);
	}

	protected void invokePostDocumentsRepositoryFolderBatchCreate(
			Long documentsRepositoryId, Folder folder)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.body(
			folder
		).when(
		).post(
			_resourceURL + "/documents-repositories/" +
			"{documents-repository-id}/folders/batch-create",
			documentsRepositoryId
		);
	}

	protected Folder invokePostFolderFolder(Long folderId, Folder folder)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		Response response = requestSpecification.body(
			folder
		).when(
		).post(
			_resourceURL + "/folders/{folder-id}/folders", folderId
		);

		return response.as(Folder.class);
	}

	protected void invokePostFolderFolderBatchCreate(
			Long folderId, Folder folder)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.body(
			folder
		).when(
		).post(
			_resourceURL + "/folders/{folder-id}/folders/batch-create", folderId
		);
	}

	protected Folder invokePutFolder(Long folderId, Folder folder)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		Response response = requestSpecification.body(
			folder
		).when(
		).put(
			_resourceURL + "/folders/{folder-id}", folderId
		);

		return response.as(Folder.class);
	}

	protected static SameJSONAs<? super String> sameJSONAs(Folder folder)
		throws JsonProcessingException {

		return SameJSONAs.sameJSONAs(
			toJSON(folder)
		).allowingExtraUnexpectedFields();
	}

	protected static String toJSON(Folder folder)
		throws JsonProcessingException {

		return _outputObjectMapper.writeValueAsString(folder);
	}

	private abstract class IgnoreFieldsMixin {

		@JsonIgnore
		public abstract Date getDateCreated();

		@JsonIgnore
		public abstract Date getDateModified();

		@JsonIgnore
		public abstract Long getId();

	}

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

	protected Group testGroup;

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};

	private static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			addMixIn(Folder.class, IgnoreFieldsMixin.class);
		}
	};

	private URL _resourceURL;

}