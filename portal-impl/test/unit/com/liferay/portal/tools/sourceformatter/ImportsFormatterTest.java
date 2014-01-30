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
public class ImportsFormatterTest {

	@Test
	public void testIfDuplicateImportsAreRemoved() throws Exception {
		String original =
			"import org.junit.Test;" + "\n" +
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Assert;";

		String expected =
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Test;" + "\n";

		assertFormat(original, expected);
	}

	@Test
	public void testIfNewlineIsAppendedAfterImport() throws Exception {
		String original = "import org.junit.Test;";

		String expected = original + "\n";

		assertFormat(original, expected);
	}

	@Test
	public void testIfNewlineIsAppendedBetweenDifferentPackages()
		throws Exception {

		String original =
			"import org.mockito.Mockito;" + "\n" +
			"import org.junit.Assert;";

		String expected =
			"import org.junit.Assert;" + "\n" +
			"\n" +
			"import org.mockito.Mockito;" + "\n";

		assertFormat(original, expected);
	}

	@Test
	public void testMultipleStaticImports() throws Exception {
		String original =
			"import static org.junit.Assert.*;" + "\n\n" +
			"import static org.mockito.Mockito.*;";

		String expected = original + "\n";

		assertFormat(original, expected);
	}

	@Test
	public void testNoImports() throws Exception {
		assertFormat("", "");
	}

	@Test
	public void testSorting() throws Exception {
		String original =
			"import org.junit.Test;" + "\n" +
			"import org.junit.Assert;";

		String expected =
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Test;" + "\n";

		assertFormat(original, expected);
	}

	@Test
	public void testSortingOfStaticImports() throws Exception {
		String original =
			"import static org.mockito.Mockito.*;" + "\n\n" +
			"import static org.junit.Assert.*;";

		String expected =
			"import static org.junit.Assert.*;" + "\n\n" +
			"import static org.mockito.Mockito.*;" + "\n";

		assertFormat(original, expected);
	}

	@Test
	public void testSortingWithInnerClass() throws Exception {
		String original =
			"import javax.servlet.FilterRegistration.Dynamic;" + "\n" +
			"import javax.servlet.FilterRegistration;";

		String expected =
			"import javax.servlet.FilterRegistration;" + "\n" +
			"import javax.servlet.FilterRegistration.Dynamic;" + '\n';

		assertFormat(original, expected);
	}

	protected void assertFormat(
			String original, int classStartPos, String expected)
		throws Exception {

		String formatted = ImportsFormatter.format(original, classStartPos);

		Assert.assertEquals(expected, formatted);
	}

	protected void assertFormat(String original, String expected)
		throws Exception {

		assertFormat(original, 0, expected);
	}

}