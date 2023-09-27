/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.test.util.URLConnectionUtil;

import java.net.URLConnection;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matija Petanjek
 */
@RunWith(Arquillian.class)
public class ReverseProxyTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testActionsHrefValues() throws Exception {
		URLConnection urlConnection = URLConnectionUtil.createURLConnection(
			String.format(
				"http://localhost:8080/o/headless-delivery/v1.0/sites/%s" +
					"/blog-postings",
				TestPropsValues.getGroupId()));

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"WEB_SERVER_FORWARDED_HOST_ENABLED", true);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"WEB_SERVER_FORWARDED_PORT_ENABLED", true);
			SafeCloseable safeCloseable3 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"WEB_SERVER_FORWARDED_PROTOCOL_ENABLED", true)) {

			urlConnection.addRequestProperty("X-Forwarded-Host", "myHost");
			urlConnection.addRequestProperty("X-Forwarded-Port", "12345");
			urlConnection.addRequestProperty("X-Forwarded-Proto", "https");

			String href = _getHref(urlConnection);

			Assert.assertTrue(href, href.startsWith("https://myHost:12345"));
		}
	}

	private String _getHref(URLConnection urlConnection) throws Exception {
		urlConnection.connect();

		JsonNode jsonNode = _objectMapper.readTree(
			urlConnection.getInputStream());

		jsonNode = jsonNode.at("/actions/create/href");

		return jsonNode.asText();
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

}