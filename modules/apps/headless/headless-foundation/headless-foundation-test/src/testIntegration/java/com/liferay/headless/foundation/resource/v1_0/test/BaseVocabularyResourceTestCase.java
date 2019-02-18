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

import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.internal.dto.v1_0.VocabularyImpl;
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
public abstract class BaseVocabularyResourceTestCase {

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
	public void testGetContentSpaceVocabulariesPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceVocabulary() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteVocabulary() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetVocabulary() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutVocabulary() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetContentSpaceVocabulariesPage( Long contentSpaceId , Filter filter , Pagination pagination , Sort[] sorts ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/content-spaces/{content-space-id}/vocabularies",
				contentSpaceId , filter  , sorts
			);

	}
	protected Response invokePostContentSpaceVocabulary( Long contentSpaceId , Vocabulary vocabulary ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				vocabulary
			).when(
			).post(
				_resourceURL + "/content-spaces/{content-space-id}/vocabularies",
				contentSpaceId 
			);

	}
	protected Response invokeDeleteVocabulary( Long vocabularyId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).delete(
				_resourceURL + "/vocabularies/{vocabulary-id}",
				vocabularyId
			);

	}
	protected Response invokeGetVocabulary( Long vocabularyId ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.when(
			).get(
				_resourceURL + "/vocabularies/{vocabulary-id}",
				vocabularyId
			);

	}
	protected Response invokePutVocabulary( Long vocabularyId , Vocabulary vocabulary ) throws Exception {
		RequestSpecification requestSpecification = _createRequestSpecification();

			return requestSpecification.body(
				vocabulary
			).when(
			).put(
				_resourceURL + "/vocabularies/{vocabulary-id}",
				vocabularyId 
			);

	}

	protected Vocabulary randomVocabulary() {
		Vocabulary vocabulary = new VocabularyImpl();

vocabulary.setContentSpace(RandomTestUtil.randomLong());
vocabulary.setDateCreated(RandomTestUtil.nextDate());
vocabulary.setDateModified(RandomTestUtil.nextDate());
vocabulary.setDescription(RandomTestUtil.randomString());
vocabulary.setHasCategories(RandomTestUtil.randomBoolean());
vocabulary.setId(RandomTestUtil.randomLong());
vocabulary.setName(RandomTestUtil.randomString());
		return vocabulary;
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