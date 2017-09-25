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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientImplTest extends JSONWebServiceClientBaseTest {

	@Test
	public void testActivateForBasicProxy() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put("proxyHostName", "proxyhost.net");
		properties.put("proxyHostPort", "443");
		properties.put("proxyLogin", "proxylogin");
		properties.put("proxyPassword", "proxypass");

		jsonWebServiceClientImpl.activate(properties);

		Assert.assertEquals(
			properties.get("hostName"), jsonWebServiceClientImpl.getHostName());
		Assert.assertEquals(
			properties.get("protocol"), jsonWebServiceClientImpl.getProtocol());

		Assert.assertNull(jsonWebServiceClientImpl.getProxyAuthType());

		Assert.assertEquals(
			properties.get("proxyHostName"),
			jsonWebServiceClientImpl.getProxyHostName());
		Assert.assertEquals(
			Integer.parseInt(String.valueOf(properties.get("proxyHostPort"))),
			jsonWebServiceClientImpl.getProxyHostPort());
		Assert.assertEquals(
			properties.get("proxyLogin"),
			jsonWebServiceClientImpl.getProxyLogin());
		Assert.assertEquals(
			properties.get("proxyPassword"),
			jsonWebServiceClientImpl.getProxyPassword());
	}

	@Test
	public void testActivateForNTLMProxy() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put("proxyAuthType", "ntlm");
		properties.put("proxyDomain", "liferay.com");
		properties.put("proxyWorkstation", "lrdcom2003");

		jsonWebServiceClientImpl.activate(properties);

		Assert.assertEquals(
			properties.get("proxyAuthType"),
			jsonWebServiceClientImpl.getProxyAuthType());
		Assert.assertEquals(
			properties.get("proxyDomain"),
			jsonWebServiceClientImpl.getProxyDomain());
		Assert.assertEquals(
			properties.get("proxyWorkstation"),
			jsonWebServiceClientImpl.getProxyWorkstation());
	}

	@Test
	public void testActivateWithHeaders() throws Exception {
		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		Map<String, Object> properties = getBaseProperties();

		properties.put(
			"headers", "headerKey1=headerValue1;headerKey2=headerValue2");

		jsonWebServiceClientImpl.activate(properties);

		Map<String, String> headers = jsonWebServiceClientImpl.getHeaders();

		Assert.assertTrue(headers.containsKey("headerKey1"));
	}

}