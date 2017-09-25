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

package com.liferay.petra.json.web.service.client.internal;

import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.serversimulator.HTTPServerSimulator;
import com.liferay.petra.json.web.service.client.serversimulator.SimulatorConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientImplGetTest
	extends JSONWebServiceClientBaseTest {

	@Before
	public void setUp() throws Exception {
		HTTPServerSimulator.start();
	}

	@After
	public void tearDown() {
		HTTPServerSimulator.stop();
	}

	@Test(expected = JSONWebServiceInvocationException.class)
	public void testBadRequestOnGet() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		jsonWebServiceClientImpl.doGet(
			"/", Collections.<String, String>emptyMap());
	}

	@Test
	public void testResponse200OnGet() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "200");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		String json = jsonWebServiceClientImpl.doGet("/testGet/", params);

		Assert.assertTrue(
			json,
			json.contains(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));
	}

	@Test
	public void testResponse202OnGet() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "202");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		String json = jsonWebServiceClientImpl.doGet("/testGet/", params);

		Assert.assertEquals(SimulatorConstants.RESPONSE_SUCCESS_IN_JSON, json);
	}

	@Test(expected = JSONWebServiceInvocationException.class)
	public void testResponse204OnGet() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "Accept=application/json;headerKey2=headerValue2");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "204");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		jsonWebServiceClientImpl.doGet("/testGet/", params);
	}

}