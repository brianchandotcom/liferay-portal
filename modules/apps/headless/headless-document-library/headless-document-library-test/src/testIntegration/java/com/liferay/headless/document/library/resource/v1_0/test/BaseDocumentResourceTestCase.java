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
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public abstract class BaseDocumentResourceTestCase {

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
	public void testDeleteDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocumentCategoriesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetDocumentsRepositoryDocumentsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFolderDocumentsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentCategories() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentCategoriesBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentsRepositoryDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentsRepositoryDocumentBatchCreate()
		throws Exception {

			Assert.assertTrue(true);
	}

	@Test
	public void testPostFolderDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFolderDocumentBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokeDeleteDocument(Long documentId) throws Exception {
		RequestSender requestSender = _createRequestSender();

			requestSender.post("/documents/{document-id}");
	}

	protected void invokeGetDocument(Long documentId) throws Exception {
		RequestSender requestSender = _createRequestSender();

			requestSender.post("/documents/{document-id}");
	}

	protected void invokeGetDocumentCategoriesPage(
			Long documentId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/documents/{document-id}/categories");
	}

	protected void invokeGetDocumentsRepositoryDocumentsPage(
			Long documentsRepositoryId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/documents-repositories/{documents-repository-id}/documents");
	}

	protected void invokeGetFolderDocumentsPage(
			Long folderId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/folders/{folder-id}/documents");
	}

	protected void invokePostDocumentCategories(
			Long documentId, Long referenceId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/documents/{document-id}/categories");
	}

	protected void invokePostDocumentCategoriesBatchCreate(
			Long documentId, Long referenceId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/documents/{document-id}/categories/batch-create");
	}

	protected void invokePostDocumentsRepositoryDocument(
			Long documentsRepositoryId, Document document)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/documents-repositories/{documents-repository-id}/documents");
	}

	protected void invokePostDocumentsRepositoryDocumentBatchCreate(
			Long documentsRepositoryId, Document document)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/documents-repositories/{documents-repository-id}/documents/batch-create");
	}

	protected void invokePostFolderDocument(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/folders/{folder-id}/documents");
	}

	protected void invokePostFolderDocumentBatchCreate(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/folders/{folder-id}/documents/batch-create");
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