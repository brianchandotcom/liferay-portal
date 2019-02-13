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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
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
public abstract class BaseStructuredContentResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		_resourceURL = new URL(
			_url.toExternalForm() + "/o/headless-web-experience/v1.0");
	}

	@Test
	public void testDeleteStructuredContent() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContentSpaceContentStructureStructuredContentsPage()
		throws Exception {

			Assert.assertTrue(true);
	}

	@Test
	public void testGetContentSpaceStructuredContentsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContentStructure() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetStructuredContent() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetStructuredContentCategoriesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPatchContentSpaceStructuredContents() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceStructuredContent() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceStructuredContentBatchCreate()
		throws Exception {

			Assert.assertTrue(true);
	}

	@Test
	public void testPostStructuredContentCategories() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostStructuredContentCategoriesBatchCreate()
		throws Exception {

			Assert.assertTrue(true);
	}

	@Test
	public void testPutStructuredContent() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokeDeleteStructuredContent(Long structuredContentId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/structured-contents/{structured-content-id}");
	}

	protected void invokeGetContentSpaceContentStructureStructuredContentsPage(
			Long contentSpaceId, Long contentStructureId, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/content-spaces/{content-space-id}/content-structures/{content-structure-id}/structured-contents");
	}

	protected void invokeGetContentSpaceStructuredContentsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/content-spaces/{content-space-id}/structured-contents");
	}

	protected void invokeGetContentStructure(Long contentStructureId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/content-structures/{content-structure-id}");
	}

	protected void invokeGetStructuredContent(Long structuredContentId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/structured-contents/{structured-content-id}");
	}

	protected void invokeGetStructuredContentCategoriesPage(
			Long structuredContentId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/structured-contents/{structured-content-id}/categories");
	}

	protected void invokePatchContentSpaceStructuredContents(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/content-spaces/{content-space-id}/structured-contents");
	}

	protected void invokePostContentSpaceStructuredContent(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/content-spaces/{content-space-id}/structured-contents");
	}

	protected void invokePostContentSpaceStructuredContentBatchCreate(
			Long contentSpaceId, StructuredContent structuredContent)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/content-spaces/{content-space-id}/structured-contents/batch-create");
	}

	protected void invokePostStructuredContentCategories(
			Long structuredContentId, Long referenceId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/structured-contents/{structured-content-id}/categories");
	}

	protected void invokePostStructuredContentCategoriesBatchCreate(
			Long structuredContentId, Long referenceId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/structured-contents/{structured-content-id}/categories/batch-create");
	}

	protected void invokePutStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/structured-contents/{structured-content-id}");
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