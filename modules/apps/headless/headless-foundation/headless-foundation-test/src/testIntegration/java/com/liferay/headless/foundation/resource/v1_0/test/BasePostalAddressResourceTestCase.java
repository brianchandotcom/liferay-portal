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

import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.internal.dto.v1_0.PostalAddressImpl;
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
public abstract class BasePostalAddressResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL =
			new URL("http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetAddress() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetGenericParentPostalAddressesPage() throws Exception {
		Assert.assertTrue(true);
	}

	protected Response invokeGetAddress(Long addressId) throws Exception {
		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/addresses/{address-id}",
			addressId
		);
	}

	protected Response invokeGetGenericParentPostalAddressesPage(
			Object genericParentId, Pagination pagination)
		throws Exception {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return requestSpecification.when(
		).get(
			_resourceURL + "/addresses",
			genericParentId 
		);
	}

	protected PostalAddress randomPostalAddress() {
		PostalAddress postalAddress = new PostalAddressImpl();

		addressCountry = RandomTestUtil.randomString();
		addressLocality = RandomTestUtil.randomString();
		addressRegion = RandomTestUtil.randomString();
		addressType = RandomTestUtil.randomString();
		id = RandomTestUtil.randomLong();
		postalCode = RandomTestUtil.randomString();
		streetAddressLine1 = RandomTestUtil.randomString();
		streetAddressLine2 = RandomTestUtil.randomString();
		streetAddressLine3 = RandomTestUtil.randomString();

		return postalAddress;
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