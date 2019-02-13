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

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSender;

import java.net.URL;

import javax.annotation.Generated;

import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFolderResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		_resourceURL = new URL(
			_url.toExternalForm() + "/o/headless-document-library/v1.0");
	}

	@Test
	public void testDeleteFolders() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocumentsRepositories() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocumentsRepositoriesFoldersPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFolders() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFoldersFoldersPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentsRepositoriesFolders() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentsRepositoriesFoldersBatchCreate()
		throws Exception {

		Assert.assertTrue(true);
	}

	@Test
	public void testPostFoldersFolders() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFoldersFoldersBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutFolders() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokeDeleteFolders(Long folderId) throws Exception {
		RequestSender requestSender = _createRequestSender();

		requestSender.post("/folders/{folder-id}");
	}

	protected void invokeGetDocumentsRepositories(Long documentsRepositoryId)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post(
			"/documents-repositories/{documents-repository-id}");
	}

	protected void invokeGetDocumentsRepositoriesFoldersPage(
			Long documentsRepositoryId, Pagination pagination)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post(
			"/documents-repositories/{documents-repository-id}/folders");
	}

	protected void invokeGetFolders(Long folderId) throws Exception {
		RequestSender requestSender = _createRequestSender();

		requestSender.post("/folders/{folder-id}");
	}

	protected void invokeGetFoldersFoldersPage(
			Long folderId, Pagination pagination)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post("/folders/{folder-id}/folders");
	}

	protected void invokePostDocumentsRepositoriesFolders(
			Long documentsRepositoryId, Folder folder)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post(
			"/documents-repositories/{documents-repository-id}/folders");
	}

	protected void invokePostDocumentsRepositoriesFoldersBatchCreate(
			Long documentsRepositoryId, Folder folder)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post(
			"/documents-repositories/{documents-repository-id}/folders/" +
				"batch-create");
	}

	protected void invokePostFoldersFolders(Long folderId, Folder folder)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post("/folders/{folder-id}/folders");
	}

	protected void invokePostFoldersFoldersBatchCreate(
			Long folderId, Folder folder)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post("/folders/{folder-id}/folders/batch-create");
	}

	protected void invokePutFolders(Long folderId, Folder folder)
		throws Exception {

		RequestSender requestSender = _createRequestSender();

		requestSender.post("/folders/{folder-id}");
	}

	private RequestSender _createRequestSender() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).when();
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};

	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

	@ArquillianResource
	private URL _url;

}