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
import com.liferay.headless.foundation.internal.dto.v1_0.CategoryImpl;
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
public abstract class BaseCategoryResourceTestCase {

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
	public void testDeleteCategory() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetCategory() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutCategory() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetCategoryCategoriesPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostCategoryCategory() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetVocabularyCategoriesPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostVocabularyCategory() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeDeleteCategory( Long categoryId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/categories/{category-id}",
				categoryId
			);

	}
	protected Response invokeGetCategory( Long categoryId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/categories/{category-id}",
				categoryId
			);

	}
	protected Response invokePutCategory( Long categoryId , Category category ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				category
			).when(
			).put(
				_resourceURL + "/categories/{category-id}",
				categoryId 
			);

	}
	protected Response invokeGetCategoryCategoriesPage( Long categoryId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/categories/{category-id}/categories",
				categoryId 
			);

	}
	protected Response invokePostCategoryCategory( Long categoryId , Category category ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				category
			).when(
			).post(
				_resourceURL + "/categories/{category-id}/categories",
				categoryId 
			);

	}
	protected Response invokeGetVocabularyCategoriesPage( Long vocabularyId , Pagination pagination ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/vocabularies/{vocabulary-id}/categories",
				vocabularyId 
			);

	}
	protected Response invokePostVocabularyCategory( Long vocabularyId , Category category ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				category
			).when(
			).post(
				_resourceURL + "/vocabularies/{vocabulary-id}/categories",
				vocabularyId 
			);

	}

	protected Category randomCategory() {
		Category category = new CategoryImpl();

category.setCreatorId(RandomTestUtil.randomLong());
category.setDateCreated(RandomTestUtil.nextDate());
category.setDateModified(RandomTestUtil.nextDate());
category.setDescription(RandomTestUtil.randomString());
category.setHasCategories(RandomTestUtil.randomBoolean());
category.setId(RandomTestUtil.randomLong());
category.setName(RandomTestUtil.randomString());
category.setParentVocabularyId(RandomTestUtil.randomLong());
		return category;
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