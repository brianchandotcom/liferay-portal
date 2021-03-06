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
import com.liferay.petra.json.web.service.client.server.simulator.HTTPServerSimulator;
import com.liferay.petra.json.web.service.client.server.simulator.constants.SimulatorConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientImplDeleteTest
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
	public void testBadRequestOnDelete() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		jsonWebServiceClientImpl.doDelete("/", Collections.emptyList());
	}

	@Test
	public void testResponse200OnDelete() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		String json = jsonWebServiceClientImpl.doDelete(
			"/testDelete/", getParameters("200"));

		Assert.assertTrue(
			json,
			json.contains(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));
	}

	@Test
	public void testResponse202OnDelete() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		Assert.assertEquals(
			SimulatorConstants.RESPONSE_SUCCESS_IN_JSON,
			jsonWebServiceClientImpl.doDelete(
				"/testDelete/", getParameters("202")));
	}

	@Test
	public void testResponse204OnDelete() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "Accept=application/json;headerKey1=headerValue1");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		Assert.assertNull(
			jsonWebServiceClientImpl.doDelete(
				"/testDelete/", getParameters("204")));
	}

	@Test(expected = JSONWebServiceInvocationException.class)
	public void testResponse405OnDelete() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "Accept=application/json;headerKey1=headerValue1");
		properties.put("protocol", "http");

		jsonWebServiceClientImpl.activate(properties);

		Assert.assertNull(
			jsonWebServiceClientImpl.doDelete(
				"/testDelete/", getParameters("405")));
	}

}