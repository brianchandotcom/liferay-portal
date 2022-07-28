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

package com.liferay.portal.kernel.frontend.source.map;

import com.liferay.petra.string.StringPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Chi Le
 */
public class FrontendSourceMapUtilTest {

	@Test
	public void testStripCSSSourceMapping() {
		Assert.assertEquals(
			"Hello World!",
			FrontendSourceMapUtil.stripCSSSourceMapping("Hello World!"));

		Assert.assertEquals(
			"Hello World!",
			FrontendSourceMapUtil.stripCSSSourceMapping(
				"Hello World!/*# sourceMappingURL=main.css.map */"));

		Assert.assertEquals(
			null, FrontendSourceMapUtil.stripCSSSourceMapping(null));
	}

	@Test
	public void testStripJSSourceMapping() {
		Assert.assertEquals(
			"Hello World!",
			FrontendSourceMapUtil.stripJSSourceMapping("Hello World!"));

		Assert.assertEquals(
			"Hello World!",
			FrontendSourceMapUtil.stripJSSourceMapping(
				"Hello World!//# sourceMappingURL=main.js.map"));
	}

	@Test
	public void testTransferCSS() throws IOException {
		String input = "Hello World!/*# sourceMappingURL=main.css.map */";

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			input.getBytes(StandardCharsets.UTF_8));

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		FrontendSourceMapUtil.transferCSS(
			byteArrayInputStream, byteArrayOutputStream);

		Assert.assertEquals(
			"Hello World!", byteArrayOutputStream.toString(StringPool.UTF8));
	}

	@Test
	public void testTransferJS() throws IOException {
		String input = "Hello World!//# sourceMappingURL=main.js.map";

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			input.getBytes(StandardCharsets.UTF_8));

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		FrontendSourceMapUtil.transferJS(
			byteArrayInputStream, byteArrayOutputStream);

		Assert.assertEquals(
			"Hello World!", byteArrayOutputStream.toString(StringPool.UTF8));
	}

}