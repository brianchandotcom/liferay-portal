/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet.filter;

import com.liferay.frontend.js.web.internal.frontend.resource.FrontendResource;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class FrontendResourceFilterTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContentAndType() throws Exception {
		FrontendResourceFilter frontendResourceFilter =
			new FrontendResourceFilter();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		frontendResourceFilter.send(
			_mockFrontendResource("CAFEBABE", 1234L, true, true),
			new MockHttpServletRequest(), mockHttpServletResponse);

		String contentType = mockHttpServletResponse.getHeader("Content-Type");

		Assert.assertTrue(contentType.contains("text/javascript"));

		Assert.assertEquals(
			"import React from 'react';",
			mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testETag() throws Exception {
		FrontendResourceFilter frontendResourceFilter =
			new FrontendResourceFilter();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		frontendResourceFilter.send(
			_mockFrontendResource("CAFEBABE", 1234L, true, true),
			new MockHttpServletRequest(), mockHttpServletResponse);

		Assert.assertEquals(
			"CAFEBABE", mockHttpServletResponse.getHeader("ETag"));
	}

	@Test
	public void testImmutable() throws Exception {
		FrontendResourceFilter frontendResourceFilter =
			new FrontendResourceFilter();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		frontendResourceFilter.send(
			_mockFrontendResource("CAFEBABE", 1234L, true, true),
			new MockHttpServletRequest(), mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			"Cache-Control");

		Assert.assertTrue(cacheControl.contains("immutable"));
		Assert.assertTrue(cacheControl.contains("max-age=31536000"));
		Assert.assertTrue(cacheControl.contains("public"));
	}

	@Test
	public void testMaxAge() throws Exception {
		FrontendResourceFilter frontendResourceFilter =
			new FrontendResourceFilter();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		frontendResourceFilter.send(
			_mockFrontendResource("CAFEBABE", 1357L, false, true),
			new MockHttpServletRequest(), mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			"Cache-Control");

		Assert.assertTrue(cacheControl.contains("max-age=1357"));
	}

	@Test
	public void testSendNoCache() throws Exception {
		FrontendResourceFilter frontendResourceFilter =
			new FrontendResourceFilter();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		frontendResourceFilter.send(
			_mockFrontendResource("CAFEBABE", 1357L, false, false),
			new MockHttpServletRequest(), mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			"Cache-Control");

		Assert.assertTrue(cacheControl.contains("must-revalidate"));

		mockHttpServletResponse = new MockHttpServletResponse();

		frontendResourceFilter.send(
			_mockFrontendResource("CAFEBABE", 1357L, false, true),
			new MockHttpServletRequest(), mockHttpServletResponse);

		cacheControl = mockHttpServletResponse.getHeader("Cache-Control");

		Assert.assertTrue(cacheControl.contains("no-cache"));
	}

	private FrontendResource _mockFrontendResource(
		String eTag, long maxAge, boolean immutable, boolean sendNoCache) {

		return new FrontendResource() {

			@Override
			public String getContentType() {
				return _CONTENT_TYPE;
			}

			@Override
			public String getETag() {
				return eTag;
			}

			@Override
			public InputStream getInputStream() {
				return new ByteArrayInputStream(
					_CONTENT.getBytes(StandardCharsets.UTF_8));
			}

			@Override
			public long getMaxAge() {
				return maxAge;
			}

			@Override
			public boolean isImmutable() {
				return immutable;
			}

			@Override
			public boolean isSendNoCache() {
				return sendNoCache;
			}

		};
	}

	private static final String _CONTENT = "import React from 'react';";

	private static final String _CONTENT_TYPE = "text/javascript";

}