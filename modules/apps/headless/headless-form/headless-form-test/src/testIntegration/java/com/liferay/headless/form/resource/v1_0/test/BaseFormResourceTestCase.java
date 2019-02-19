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

import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.internal.dto.v1_0.FormImpl;
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
public abstract class BaseFormResourceTestCase {

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
	public void testGetContentSpaceFormsPage() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetForm() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetFormFetchLatestDraft() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFormEvaluateContext() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFormUploadFile() throws Exception {
		Assert.assertTrue(true);
	}

	protected Response invokeGetContentSpaceFormsPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/content-spaces/{content-space-id}/form",
			contentSpaceId 
		);
	}

	protected Response invokeGetForm(Long formId) throws Exception {
		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/forms/{form-id}",
			formId
		);
	}

	protected Response invokeGetFormFetchLatestDraft(Long formId)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/forms/{form-id}/fetch-latest-draft",
			formId
		);
	}

	protected Response invokePostFormEvaluateContext(Long formId, Form form)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.body(
			form
		).when(
		).post(
			_resourceURL + "/forms/{form-id}/evaluate-context",
			formId 
		);
	}

	protected Response invokePostFormUploadFile(Long formId, Form form)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.body(
			form
		).when(
		).post(
			_resourceURL + "/forms/{form-id}/upload-file",
			formId 
		);
	}

	protected Form randomForm() {
		Form form = new FormImpl();

		contentSpace = RandomTestUtil.randomLong();
		dateCreated = RandomTestUtil.nextDate();
		dateModified = RandomTestUtil.nextDate();
		datePublished = RandomTestUtil.nextDate();
		defaultLanguage = RandomTestUtil.randomString();
		description = RandomTestUtil.randomString();
		id = RandomTestUtil.randomLong();
		name = RandomTestUtil.randomString();
		structureId = RandomTestUtil.randomLong();

		return form;
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