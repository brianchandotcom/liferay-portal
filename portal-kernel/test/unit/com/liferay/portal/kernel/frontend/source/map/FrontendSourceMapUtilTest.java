/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Assert.assertEquals(
			null, FrontendSourceMapUtil.stripJSSourceMapping(null));
	}

	@Test
	public void testTransferCSS() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			"Hello World!/*# sourceMappingURL=main.css.map */".getBytes(
				StandardCharsets.UTF_8));

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		FrontendSourceMapUtil.transferCSS(
			byteArrayInputStream, byteArrayOutputStream);

		Assert.assertEquals(
			"Hello World!", byteArrayOutputStream.toString(StringPool.UTF8));
	}

	@Test
	public void testTransferJS() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			"Hello World!//# sourceMappingURL=main.js.map".getBytes(
				StandardCharsets.UTF_8));

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		FrontendSourceMapUtil.transferJS(
			byteArrayInputStream, byteArrayOutputStream);

		Assert.assertEquals(
			"Hello World!", byteArrayOutputStream.toString(StringPool.UTF8));
	}

}