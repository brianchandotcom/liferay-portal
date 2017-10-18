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

package com.liferay.petra.io.unsync;

import com.liferay.petra.io.internal.BoundaryCheckerUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.Reader;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringReaderTest extends BaseReaderTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BoundaryCheckerUtil.class);
			}

		};

	@Test
	public void testClose() throws IOException {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		unsyncStringReader.close();

		Assert.assertTrue(unsyncStringReader.string == null);

		testClose(unsyncStringReader, "String is null");
	}

	@Test
	public void testConstructor() {
		new BoundaryCheckerUtil();

		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertEquals("abc", unsyncStringReader.string);
		Assert.assertEquals(3, unsyncStringReader.stringLength);

		unsyncStringReader = new UnsyncStringReader("defg");

		Assert.assertEquals("defg", unsyncStringReader.string);
		Assert.assertEquals(4, unsyncStringReader.stringLength);
	}

	@Override
	protected Reader getReader(String s) {
		return new UnsyncStringReader(s);
	}

}