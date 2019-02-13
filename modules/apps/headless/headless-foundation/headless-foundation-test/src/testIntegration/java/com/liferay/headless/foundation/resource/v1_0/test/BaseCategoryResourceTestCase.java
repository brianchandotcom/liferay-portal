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

import com.liferay.headless.foundation.dto.v1_0.Category;
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
public abstract class BaseCategoryResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		_resourceURL = new URL(
			_url.toExternalForm() + "/o/headless-foundation/v1.0");
	}

	@Test
	public void testDeleteCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetCategoryCategoriesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetVocabularyCategoriesPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostCategoryCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostCategoryCategoryBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostVocabularyCategory() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostVocabularyCategoryBatchCreate() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutCategory() throws Exception {
		Assert.assertTrue(true);
	}

	protected void invokeDeleteCategory(Long categoryId) throws Exception {
		RequestSender requestSender = _createRequestSender();

			requestSender.post("/categories/{category-id}");
	}

	protected void invokeGetCategory(Long categoryId) throws Exception {
		RequestSender requestSender = _createRequestSender();

			requestSender.post("/categories/{category-id}");
	}

	protected void invokeGetCategoryCategoriesPage(
			Long categoryId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/categories/{category-id}/categories");
	}

	protected void invokeGetVocabularyCategoriesPage(
			Long vocabularyId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/vocabularies/{vocabulary-id}/categories");
	}

	protected void invokePostCategoryCategory(
			Long categoryId, Category category)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/categories/{category-id}/categories");
	}

	protected void invokePostCategoryCategoryBatchCreate(
			Long categoryId, Category category)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/categories/{category-id}/categories/batch-create");
	}

	protected void invokePostVocabularyCategory(
			Long vocabularyId, Category category)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/vocabularies/{vocabulary-id}/categories");
	}

	protected void invokePostVocabularyCategoryBatchCreate(
			Long vocabularyId, Category category)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/vocabularies/{vocabulary-id}/categories/batch-create");
	}

	protected void invokePutCategory(Long categoryId, Category category)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/categories/{category-id}");
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