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

package com.liferay.headless.form.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.internal.dto.v1_0.FormDocumentImpl;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

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
public abstract class BaseFormDocumentResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL("http://localhost:8080/o/headless-form/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteFormDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFormDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Response invokeDeleteFormDocument(Long formDocumentId)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).delete(
			_resourceURL + "/form-documents/{form-document-id}",
			formDocumentId
		);
	}

	protected Response invokeGetFormDocument(Long formDocumentId)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/form-documents/{form-document-id}",
			formDocumentId
		);
	}

	protected FormDocument randomFormDocument() {
		FormDocument formDocument = new FormDocumentImpl();

		contentUrl = RandomTestUtil.randomString();
		encodingFormat = RandomTestUtil.randomString();
		fileExtension = RandomTestUtil.randomString();
		id = RandomTestUtil.randomLong();
		title = RandomTestUtil.randomString();

		return formDocument;
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