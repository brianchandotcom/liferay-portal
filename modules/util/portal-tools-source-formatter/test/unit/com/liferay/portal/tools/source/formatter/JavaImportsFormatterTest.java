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

import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class JavaImportsFormatterTest {

	@Test
	public void testAppendNewLineAfterImports() throws Exception {
		String original = "import org.junit.Test;";

		String expected = original + "\n";

		_test(original, expected);
	}

	@Test
	public void testAppendNewLineAfterMultipleStaticImports() throws Exception {
		String original =
			"import static org.junit.Assert.assertEquals;" + "\n\n" +
			"import static org.mockito.Mockito.when;";

		String expected = original + "\n";

		_test(original, expected);
	}

	@Test
	public void testAppendNewLineBetweenDifferentPackages() throws Exception {
		String original =
			"import org.mockito.Mockito;" + "\n" +
			"import org.junit.Assert;";
		String expected =
			"import org.junit.Assert;" + "\n" + "\n" +
			"import org.mockito.Mockito;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testDuplicateImports() throws Exception {
		String original =
			"import org.junit.Test;" + "\n" +
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Assert;";
		String expected =
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Test;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testNoImports() throws Exception {
		_test(StringPool.BLANK, StringPool.BLANK);
	}

	@Test
	public void testSortImports1() throws Exception {
		String original =
			"import org.junit.Assert;" + "\n" +
			"import static org.mockito.Mockito.when;" + "\n" +
			"import tw.xyz.FooUtils;";
		String expected =
			"import static org.mockito.Mockito.when;" + "\n" + "\n" +
			"import org.junit.Assert;" + "\n" + "\n" +
			"import tw.xyz.FooUtils;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortImports2() throws Exception {
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
			"import static org.junit.Assert.fail;" + "\n" + "\n" +
			"import static org.mockito.Mockito.doThrow;" + "\n" +
			"import static org.mockito.Mockito.when;" + "\n" + "\n" +
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n" + "\n" +
			"import org.mockito.Mock;" + "\n" +
			"import org.mockito.MockitoAnnotations;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortImports3() throws Exception {
		String original =
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n" +
			"import static org.junit.Assert.assertThat;" + "\n" +
			"import static org.junit.Assert.fail;";
		String expected =
			"import static org.junit.Assert.assertThat;" + "\n" +
			"import static org.junit.Assert.fail;" + "\n" + "\n" +
			"import org.junit.Before;" + "\n" +
			"import org.junit.Test;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortImports4() throws Exception {
		String original =
			"import java.awt.Graphics2D;" + "\n" +
			"import java.awt.Graphics;";
		String expected =
			"import java.awt.Graphics;" + "\n" +
			"import java.awt.Graphics2D;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortImportsWithInnerClass() throws Exception {
		String original =
			"import javax.servlet.FilterRegistration.Dynamic;" + "\n" +
			"import javax.servlet.FilterRegistration;" + "\n" +
			"import java.util.Map;" + "\n" +
			"import java.util.Map.Entry;";
		String expected =
			"import java.util.Map;" + "\n" +
			"import java.util.Map.Entry;" + "\n" + "\n" +
			"import javax.servlet.FilterRegistration;" + "\n" +
			"import javax.servlet.FilterRegistration.Dynamic;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortNonStaticImports() throws Exception {
		String original =
			"import org.junit.Test;" + "\n" +
			"import org.junit.Assert;";
		String expected =
			"import org.junit.Assert;" + "\n" +
			"import org.junit.Test;" + "\n";

		_test(original, expected);
	}

	@Test
	public void testSortStaticImports() throws Exception {
		String original =
			"import static org.mockito.Mockito.when;" + "\n" + "\n" +
			"import static org.junit.Assert.assertEquals;";
		String expected =
			"import static org.junit.Assert.assertEquals;" + "\n" + "\n" +
			"import static org.mockito.Mockito.when;" + "\n";

		_test(original, expected);
	}

	private void _test(String original, String expected) throws Exception {
		String formatted = _importsFormatter.format(original);

		Assert.assertEquals(expected, formatted);
	}

	private final ImportsFormatter _importsFormatter =
		new JavaImportsFormatter();

}