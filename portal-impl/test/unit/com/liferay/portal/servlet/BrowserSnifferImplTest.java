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

package com.liferay.portal.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Shuyang Zhou
 * @author Iliyan Peych
 */
public class BrowserSnifferImplTest {

	@Test
	public void testIsAndroid() throws IOException {
		assertBrowser(
			"Android",
			mockHttpServletRequest -> Assert.assertTrue(
				_browserSnifferImpl.isAndroid(mockHttpServletRequest)));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.USER_AGENT,
			"Safari 6, 6.0, 536.26, mozilla/5.0 (ipad; cpu os 6_0 like mac " +
				"os x) applewebkit/536.26 (khtml, like gecko) version/6.0 " +
					"mobile/10a5355d safari/8536.25");

		Assert.assertFalse(
			_browserSnifferImpl.isAndroid(mockHttpServletRequest));
	}

	@Test
	public void testIsEdge() throws IOException {
		assertBrowser(
			"## Edge",
			mockHttpServletRequest -> {
				Assert.assertFalse(
					_browserSnifferImpl.isChrome(mockHttpServletRequest));
				Assert.assertTrue(
					_browserSnifferImpl.isEdge(mockHttpServletRequest));
				Assert.assertFalse(
					_browserSnifferImpl.isGecko(mockHttpServletRequest));
				Assert.assertFalse(
					_browserSnifferImpl.isMozilla(mockHttpServletRequest));
				Assert.assertFalse(
					_browserSnifferImpl.isWebKit(mockHttpServletRequest));
			});

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.USER_AGENT,
			"opera/9.80 (windows nt 6.0) presto/2.12.388 version/12.14");

		Assert.assertFalse(_browserSnifferImpl.isEdge(mockHttpServletRequest));
	}

	@Test
	public void testIsIe() throws IOException {
		assertBrowser(
			"## IE",
			mockHttpServletRequest -> Assert.assertTrue(
				_browserSnifferImpl.isIe(mockHttpServletRequest)));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.USER_AGENT,
			"Opera 12 var1, 12.14, 9.80, opera/9.80 (windows nt 6.0) " +
				"presto/2.12.388 version/12.14");

		Assert.assertFalse(_browserSnifferImpl.isIe(mockHttpServletRequest));
	}

	@Test
	public void testIsMobile() throws IOException {
		assertBrowser(
			"Mobile",
			mockHttpServletRequest -> Assert.assertTrue(
				_browserSnifferImpl.isMobile(mockHttpServletRequest)));

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader(
			HttpHeaders.USER_AGENT,
			"IE 6 var4, , 6.0, mozilla/5.0 (compatible; msie 6.0; windows nt " +
				"5.1)");

		Assert.assertFalse(
			_browserSnifferImpl.isMobile(mockHttpServletRequest));
	}

	@Test
	public void testParseVersion() throws IOException {
		Class<?> clazz = getClass();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new InputStreamReader(
						clazz.getResourceAsStream(
							"dependencies/user_agents.csv")))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty() || (line.charAt(0) == CharPool.POUND)) {
					continue;
				}

				String[] parts = StringUtil.split(line, CharPool.COMMA);

				if (parts.length != 4) {
					continue;
				}

				String userAgent = parts[3].trim();

				Assert.assertEquals(
					parts[0].trim() + " version", parts[1].trim(),
					BrowserSnifferImpl.parseVersion(
						userAgent, BrowserSnifferImpl.versionLeadings,
						BrowserSnifferImpl.versionSeparators));

				Assert.assertEquals(
					parts[0].trim() + " revision", parts[2].trim(),
					BrowserSnifferImpl.parseVersion(
						userAgent, BrowserSnifferImpl.revisionLeadings,
						BrowserSnifferImpl.revisionSeparators));
			}
		}
	}

	protected void assertBrowser(
			String key, Consumer<MockHttpServletRequest> requestConsumer)
		throws IOException {

		UnsyncBufferedReader unsyncBufferedReader =
			getResourceAsUnsyncBufferedReader("dependencies/user_agents.csv");

		boolean matches = false;
		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = line.trim();

			if (line.isEmpty()) {
				continue;
			}

			if (line.contains(key)) {
				matches = true;

				continue;
			}

			if (matches && (line.charAt(0) == CharPool.POUND)) {
				break;
			}

			if (matches) {
				MockHttpServletRequest mockHttpServletRequest =
					new MockHttpServletRequest();

				mockHttpServletRequest.addHeader(HttpHeaders.USER_AGENT, line);

				requestConsumer.accept(mockHttpServletRequest);
			}
		}
	}

	protected UnsyncBufferedReader getResourceAsUnsyncBufferedReader(
		String name) {

		Class<?> clazz = getClass();

		return new UnsyncBufferedReader(
			new InputStreamReader(
				clazz.getResourceAsStream("dependencies/user_agents.csv")));
	}

	private final BrowserSnifferImpl _browserSnifferImpl =
		new BrowserSnifferImpl();

}