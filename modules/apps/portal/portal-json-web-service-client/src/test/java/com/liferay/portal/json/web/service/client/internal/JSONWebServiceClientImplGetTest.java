/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.internal;

import com.liferay.portal.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.portal.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.json.web.service.client.server.simulator.HTTPServerSimulator;
import com.liferay.portal.json.web.service.client.server.simulator.constants.SimulatorConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientImplGetTest
	extends BaseJSONWebServiceClientTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		jsonWebServiceClientImpl.doGet("/", Collections.emptyList());
	}

	@Test(
		expected = JSONWebServiceTransportException.CommunicationFailure.class
	)
	public void testCommunicationFailureOnGetWithRetryable() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put("hostPort", 5555);
		properties.put("maxAttempts", 5);

		jsonWebServiceClientImpl.activate(properties);

		jsonWebServiceClientImpl.doGet("/testGet/", Collections.emptyList());
	}

	@Test
	public void testResponse200OnGet() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		String json = jsonWebServiceClientImpl.doGet(
			"/testGet/", getParameters("200"));

		Assert.assertTrue(
			json,
			json.contains(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));
	}

	@Test
	public void testResponse200OnGetWithMultiNames() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		List<NameValuePair> params = getParameters("200");

		params.add(new BasicNameValuePair("multi", "first"));
		params.add(new BasicNameValuePair("multi", "second"));
		params.add(new BasicNameValuePair("multi", "third"));
		params.add(new BasicNameValuePair("multi", "fourth"));

		String json = jsonWebServiceClientImpl.doGet("/testGet/", params);

		Assert.assertTrue(json, json.contains("first"));
		Assert.assertTrue(json, json.contains("second"));
		Assert.assertTrue(json, json.contains("third"));
		Assert.assertTrue(json, json.contains("fourth"));
	}

	@Test
	public void testResponse200OnGetWithRetryable() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("maxAttempts", 5);

		jsonWebServiceClientImpl.activate(properties);

		String json = jsonWebServiceClientImpl.doGet(
			"/testGet/", getParameters("200"));

		Assert.assertTrue(
			json,
			json.contains(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));
	}

	@Test
	public void testResponse202OnGet() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		Assert.assertEquals(
			SimulatorConstants.RESPONSE_SUCCESS_IN_JSON,
			jsonWebServiceClientImpl.doGet("/testGet/", getParameters("202")));
	}

	@Test(expected = JSONWebServiceInvocationException.class)
	public void testResponse204OnGet() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "Accept=application/json;headerKey2=headerValue2");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		jsonWebServiceClientImpl.doGet("/testGet/", getParameters("204"));
	}

}