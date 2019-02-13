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

package com.liferay.headless.collaboration.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.collaboration.dto.v1_0.ImageObject;
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
public abstract class BaseImageObjectResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		_resourceURL = new URL(
			_url.toExternalForm() + "/o/headless-collaboration/v1.0");
	}

	@Test
	public void testDeleteImageObject() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetImageObject() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetImageObjectRepositoryImageObjectsPage()
		throws Exception {

			Assert.assertTrue(true);
	}

	@Test
	public void testPostImageObjectRepositoryImageObject() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostImageObjectRepositoryImageObjectBatchCreate()
		throws Exception {

			Assert.assertTrue(true);
	}

	protected void invokeDeleteImageObject(Long imageObjectId)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post("/image-objects/{image-object-id}");
	}

	protected void invokeGetImageObject(Long imageObjectId) throws Exception {
		RequestSender requestSender = _createRequestSender();

			requestSender.post("/image-objects/{image-object-id}");
	}

	protected void invokeGetImageObjectRepositoryImageObjectsPage(
			Long imageObjectRepositoryId, Pagination pagination)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/image-object-repositories/{image-object-repository-id}/image-objects");
	}

	protected void invokePostImageObjectRepositoryImageObject(
			Long imageObjectRepositoryId, ImageObject imageObject)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/image-object-repositories/{image-object-repository-id}/image-objects");
	}

	protected void invokePostImageObjectRepositoryImageObjectBatchCreate(
			Long imageObjectRepositoryId, ImageObject imageObject)
		throws Exception {

			RequestSender requestSender = _createRequestSender();

			requestSender.post(
				"/image-object-repositories/{image-object-repository-id}/image-objects/batch-create");
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