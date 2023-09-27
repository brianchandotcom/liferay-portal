/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Reader;

import java.lang.reflect.Field;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringReaderTest extends BaseReaderTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(BoundaryCheckerUtil.class);
				}

			},
			LiferayUnitTestRule.INSTANCE);

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