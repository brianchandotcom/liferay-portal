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
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class JavaImportsFormatterTest {

	@Ignore("Needs LPS-43888 fix")
	@Test
	public void testAlphabeticalOrderVersusImportStatic() throws Exception {
		String original =
			"import org.junit.Assert;" + "\n" +
			"import static org.mockito.Mockito.when;" + "\n" +
			"import tw.xyz.FooUtils;";

		String expected =
			"import static org.mockito.Mockito.when;" + "\n" +
			"\n" +
			"import org.junit.Assert;" + "\n" +
			"\n" +
			"import tw.xyz.FooUtils;" + "\n";

		_assertFormat(
			"regardless of alphabetical order ('org' -> 'static' -> 'tw'), " +
			"mixed regular and static imports are sorted and grouped properly",
			original, expected);
	}

	@Test
	public void testIfDuplicateImportsAreRemoved() throws Exception {
		String original =
			"import org.junit.Test;" + "\n" +
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Assert;";

		String expected =
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Test;" + "\n";

		_assertFormat("duplicates are removed", original, expected);
	}

	@Test
	public void testIfNewlineIsAppendedAfterImport() throws Exception {
		String original = "import org.junit.Test;";

		String expected = original + "\n";

		_assertFormat("single import, newline is appended", original, expected);
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

		_assertFormat(
			"different package groups are sorted and separated", original,
			expected);
	}

	@Ignore("Needs LPS-43888 fix")
	@Test
	public void testMixedIntentsMultiplePackages() throws Exception {
		String original =
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n" +
			"import static org.junit.Assert.assertThat;" + "\n" +
			"import static org.junit.Assert.fail;" + "\n" +
			"import org.mockito.Mock;" + "\n" +
			"import org.mockito.MockitoAnnotations;" + "\n" +
			"import static org.mockito.Mockito.doThrow;" + "\n" +
			"import static org.mockito.Mockito.when;";

		String expected =
			"import static org.junit.Assert.assertThat;" + "\n" +
			"import static org.junit.Assert.fail;" + "\n" +
			"\n" +
			"import static org.mockito.Mockito.doThrow;" + "\n" +
			"import static org.mockito.Mockito.when;" + "\n" +
			"\n" +
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n" +
			"\n" +
			"import org.mockito.Mock;" + "\n" +
			"import org.mockito.MockitoAnnotations;" + "\n";

		_assertFormat(
			"mixed regular and static imports are sorted and grouped properly",
			original, expected);
	}

	@Ignore("Needs LPS-43888 fix")
	@Test
	public void testMixedIntentsSinglePackage() throws Exception {
		String original =
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n" +
			"import static org.junit.Assert.assertThat;" + "\n" +
			"import static org.junit.Assert.fail;";

		String expected =
			"import static org.junit.Assert.assertThat;" + "\n" +
			"import static org.junit.Assert.fail;" + "\n" +
			"\n" +
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n";

		_assertFormat(
			"mixed regular and static imports " +
			"are sorted and grouped properly " +
			"even when everything is in the same package", original, expected);
	}

	@Ignore("Needs LPS-43888 fix")
	@Test
	public void testMultipleStaticImports() throws Exception {
		String original =
			"import static org.junit.Assert.assertEquals;" + "\n\n" +
			"import static org.mockito.Mockito.when;";

		String expected = original + "\n";

		_assertFormat(
			"multiple static imports should be preserved", original, expected);
	}

	@Test
	public void testNoImports() throws Exception {
		_assertFormat("no imports, nothing happens", "", "");
	}

	@Test
	public void testSorting() throws Exception {
		String original =
			"import org.junit.Test;" + "\n" +
			"import org.junit.Assert;";

		String expected =
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Test;" + "\n";

		_assertFormat("multiple imports are sorted", original, expected);
	}

	@Ignore("Needs LPS-43888 fix")
	@Test
	public void testSortingOfStaticImports() throws Exception {
		String original =
			"import static org.mockito.Mockito.when;" + "\n" +
			"\n" +
			"import static org.junit.Assert.assertEquals;";

		String expected =
			"import static org.junit.Assert.assertEquals;" + "\n" +
			"\n" +
			"import static org.mockito.Mockito.when;" + "\n";

		_assertFormat(
			"multiple static imports must be sorted, " +
			"however separate package groups must remain separate", original,
			expected);
	}

	@Test
	public void testSortingWithInnerClass() throws Exception {
		String original =
			"import javax.servlet.FilterRegistration.Dynamic;" + "\n" +
			"import javax.servlet.FilterRegistration;";

		String expected =
			"import javax.servlet.FilterRegistration;" + "\n" +
			"import javax.servlet.FilterRegistration.Dynamic;" + '\n';

		_assertFormat(
			"when a class and an inner class of itself are both imported, " +
			"the inner class must be second in order", original, expected);
	}

	private void _assertFormat(String message, String original, String expected)
		throws Exception {

		String formatted = JavaImportsFormatter.format(original);

		Assert.assertEquals(message, expected, formatted);
	}

}