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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Hai Yu
 */
public class ServletResponseUtilContentLengthTest {

	@Test
	public void testContentLengthEqualsToInputStreamLength() throws Exception {
		PropsUtil.setProps(ProxyFactory.newDummyInstance(Props.class));

		String content = StringUtil.randomString(10);

		int contentLength = content.length();

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		ServletResponseUtil.write(
			mockHttpServletResponse,
			new ByteArrayInputStream(content.getBytes()), contentLength);

		Assert.assertEquals(
			String.valueOf(contentLength),
			mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_LENGTH));
		Assert.assertEquals(
			content, mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testContentLengthGreaterThanInputStreamLength()
		throws Exception {

		PropsUtil.setProps(ProxyFactory.newDummyInstance(Props.class));

		String content = StringUtil.randomString(10);

		int contentLength = content.length() + 1;

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		ServletResponseUtil.write(
			mockHttpServletResponse,
			new ByteArrayInputStream(content.getBytes()), contentLength);

		Assert.assertEquals(
			String.valueOf(contentLength),
			mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_LENGTH));
		Assert.assertEquals(
			content, mockHttpServletResponse.getContentAsString());
	}

	@Test
	public void testContentLengthLowerThanInputStreamLength() throws Exception {
		PropsUtil.setProps(ProxyFactory.newDummyInstance(Props.class));

		String content = StringUtil.randomString(10);

		int contentLength = content.length() - 1;

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		ServletResponseUtil.write(
			mockHttpServletResponse,
			new ByteArrayInputStream(content.getBytes()), contentLength);

		Assert.assertEquals(
			String.valueOf(contentLength),
			mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_LENGTH));
		Assert.assertEquals(
			content.substring(0, contentLength),
			mockHttpServletResponse.getContentAsString());
	}

}