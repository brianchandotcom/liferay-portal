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

package com.liferay.portal.tools.sourceformatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class JSPImportsFormatterTest {

	@Test
	public void testAppendNewLineAfterImports() throws Exception {
		String original =
			"<%@ page import=\"com.liferay.portal.kernel.util.WebKeys\" %>";

		String expected = original + "\n";

		_test(original, expected);
	}

	@Test
	public void testAppendNewLineBetweenDifferentPackages() throws Exception {
		String original =
			"<%@ page import=\"java.text.DateFormat\" %>" + "\n" +
			"<%@ page import=\"java.io.Serializable\" %>";
		String expected =
			"<%@ page import=\"java.io.Serializable\" %>" + "\n" + "\n" +
			"<%@ page import=\"java.text.DateFormat\" %>" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortImports() throws Exception {
		String original =
			"<%@ page import=\"java.util.Arrays\" %>" + "\n" +
			"<%@ page import=\"java.util.ArrayList\" %>";
		String expected =
			"<%@ page import=\"java.util.ArrayList\" %>" + "\n" +
			"<%@ page import=\"java.util.Arrays\" %>" + "\n";

		_test(original, expected);
	}

	private void _test(String original, String expected) throws Exception {
		String formatted = _importsFormatter.format(original);

		Assert.assertEquals(expected, formatted);
	}

	private final ImportsFormatter _importsFormatter =
		new JSPImportsFormatter();

}