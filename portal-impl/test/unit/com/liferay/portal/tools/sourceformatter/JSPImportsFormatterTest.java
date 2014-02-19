/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
	public void testIfNewlineIsAppendedAfterImport() throws Exception {
		String original =
			"<%@ page import=\"com.liferay.portal.kernel.util.WebKeys\" %>";

		String expected = original + "\n";

		_assertFormat("single import, newline is appended", original, expected);
	}

	@Test
	public void testIfNewlineIsAppendedBetweenDifferentPackages()
		throws Exception {

		String original =
			"<%@ page import=\"java.text.DateFormat\" %>" + '\n' +
			"<%@ page import=\"java.io.Serializable\" %>";

		String expected =
			"<%@ page import=\"java.io.Serializable\" %>" + '\n' +
			'\n' +
			"<%@ page import=\"java.text.DateFormat\" %>" + '\n';

		_assertFormat(
			"different package groups are sorted and separated", original,
			expected);
	}

	@Test
	public void testSorting() throws Exception {

		String original =
			"<%@ page import=\"java.util.Arrays\" %>" + '\n' +
			"<%@ page import=\"java.util.ArrayList\" %>";

		String expected =
			"<%@ page import=\"java.util.ArrayList\" %>" + '\n' +
			"<%@ page import=\"java.util.Arrays\" %>" + '\n';

		_assertFormat("multiple imports are sorted", original, expected);
	}

	private void _assertFormat(String message, String original, String expected)
		throws Exception {

		String formatted = JSPImportsFormatter.format(original);

		Assert.assertEquals(message, expected, formatted);
	}

}