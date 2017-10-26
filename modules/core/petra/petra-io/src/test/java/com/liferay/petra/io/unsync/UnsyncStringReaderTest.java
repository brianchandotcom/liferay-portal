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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.Reader;

import java.lang.reflect.Field;

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
	public void testClose() throws Exception {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		unsyncStringReader.close();

		Assert.assertNull(_stringField.get(unsyncStringReader));

		testClose(unsyncStringReader, "String is null");
	}

	@Test
	public void testConstructor() throws Exception {
		new BoundaryCheckerUtil();

		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		Assert.assertEquals("abc", _stringField.get(unsyncStringReader));
		Assert.assertEquals(3, _stringLengthField.getInt(unsyncStringReader));

		unsyncStringReader = new UnsyncStringReader("defg");

		Assert.assertEquals("defg", _stringField.get(unsyncStringReader));
		Assert.assertEquals(4, _stringLengthField.getInt(unsyncStringReader));
	}

	@Override
	protected Reader getReader(String s) {
		return new UnsyncStringReader(s);
	}

	private static final Field _stringField = ReflectionTestUtil.getField(
		UnsyncStringReader.class, "_string");
	private static final Field _stringLengthField = ReflectionTestUtil.getField(
		UnsyncStringReader.class, "_stringLength");

}