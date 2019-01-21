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

package com.liferay.change.tracking.client.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isOneOf;

import com.liferay.change.tracking.client.test.internal.activator.ChangeTrackingClientTestBundleActivator;
import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.kernel.exception.PortalException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ChangeTrackingRestTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			ChangeTrackingClientTestBundleActivator.class);
	}

	@Before
	public void setUp() {
		RestAssured.port = 8080;
		RestAssured.basePath = "/o/change-tracking";
		RestAssured.baseURI = _url.toExternalForm();
		RestAssured.authentication = RestAssured.preemptive(
		).basic(
			"test@liferay.com", "test"
		);

		_companyId = RestAssured.get(
			"configurations/"
		).then(
		).extract(
		).jsonPath(
		).getLong(
			"[0].companyId"
		);
	}

	@Test
	public void testGetCTConfigurations() throws PortalException {
		RestAssured.get(
			"configurations/"
		).then(
		).statusCode(
			HttpStatus.SC_OK
		).body(
			"[0].changeTrackingEnabled", isOneOf(true, false)
		);
	}

	@Test
	public void testGetCTInvalidCompanyId() {
		RestAssured.get(
			"configurations/{companyId}", "companyId"
		).then(
		).statusCode(
			HttpStatus.SC_NOT_FOUND
		);
	}

	@Ignore
	@Test
	public void testPutCTNumericRequestBody() {
		Map<String, Integer> jsonBody = new HashMap<>();

		jsonBody.put("changeTrackingEnabled", 22);

		RestAssured.given(
		).body(
			jsonBody
		).when(
		).contentType(
			ContentType.JSON
		).put(
			"configurations/{companyId}", _companyId
		).then(
		).statusCode(
			HttpStatus.SC_BAD_REQUEST
		);
	}

	@Test
	public void testPutCTStringRequestBody() {
		Map<String, String> jsonBody = new HashMap<>();

		jsonBody.put("changeTrackingEnabled", "22");

		RestAssured.given(
		).body(
			jsonBody
		).when(
		).contentType(
			ContentType.JSON
		).put(
			"configurations/{companyId}", _companyId
		).then(
		).statusCode(
			HttpStatus.SC_BAD_REQUEST
		);
	}

	@Test
	public void testSwitchOnAndOffChangeTracking() throws PortalException {
		RestAssured.get(
			"configurations/{companyId}", _companyId
		).then(
		).statusCode(
			HttpStatus.SC_OK
		).body(
			"changeTrackingEnabled", equalTo(false)
		);

		Map<String, String> jsonBody = new HashMap<>();

		jsonBody.put("changeTrackingEnabled", "true");

		RestAssured.given(
		).body(
			jsonBody
		).when(
		).contentType(
			ContentType.JSON
		).put(
			"configurations/{companyId}", _companyId
		).then(
		).statusCode(
			HttpStatus.SC_OK
		).body(
			"changeTrackingEnabled", equalTo(true)
		);

		jsonBody.clear();

		jsonBody.put("changeTrackingEnabled", "false");

		RestAssured.given(
		).body(
			jsonBody
		).when(
		).contentType(
			ContentType.JSON
		).put(
			"configurations/{companyId}", _companyId
		).then(
		).statusCode(
			HttpStatus.SC_OK
		).body(
			"changeTrackingEnabled", equalTo(false)
		);
	}

	@Test
	public void testUnauthenticatedRequest() {
		RestAssured.authentication = RestAssured.preemptive(
		).basic(
			"", ""
		);

		RestAssured.get(
			"configurations/{companyId}", "companyId"
		).then(
		).statusCode(
			HttpStatus.SC_FORBIDDEN
		);
	}

	private long _companyId;

	@ArquillianResource
	private URL _url;

}