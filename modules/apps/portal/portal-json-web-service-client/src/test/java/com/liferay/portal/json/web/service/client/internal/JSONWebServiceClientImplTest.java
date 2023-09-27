/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.internal;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientImplTest
	extends BaseJSONWebServiceClientTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
			GetterUtil.getInteger(
				String.valueOf(properties.get("proxyHostPort"))),
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