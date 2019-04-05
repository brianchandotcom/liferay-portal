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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.Writer;

import java.lang.reflect.Field;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringWriterTest extends BaseWriterTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(BoundaryCheckerUtil.class);
			}

		};

	@Test
	public void testAppendChar() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("a", stringBundler.stringAt(0));

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("a", stringBundler.stringAt(0));
		Assert.assertEquals("b", stringBundler.stringAt(1));
	}

	@Test
	public void testAppendCharSequence() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter.append(new StringBuilder("ab"), 0, 2);

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));

		unsyncStringWriter.append(new StringBuilder("cd"));

		Assert.assertEquals(4, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));
		Assert.assertEquals('c', stringBuilder.charAt(2));
		Assert.assertEquals('d', stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);

		unsyncStringWriter.append(new StringBuilder("ab"));

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("ab", stringBundler.stringAt(0));

		unsyncStringWriter.append(new StringBuilder("cd"));

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("ab", stringBundler.stringAt(0));
		Assert.assertEquals("cd", stringBundler.stringAt(1));
	}

	@Test
	public void testAppendNull() throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		unsyncStringWriter.append(null);

		Assert.assertEquals(StringPool.NULL, stringBuilder.toString());

		unsyncStringWriter.reset();

		unsyncStringWriter.append(null, 0, 4);

		Assert.assertEquals(StringPool.NULL, stringBuilder.toString());
	}

	@Test
	public void testConstructor() throws Exception {
		new BoundaryCheckerUtil();

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);
		Assert.assertEquals(16, stringBuilder.capacity());

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter = new UnsyncStringWriter(false, 32);

		stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);
		Assert.assertEquals(32, stringBuilder.capacity());

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);
		Assert.assertEquals(16, stringBundler.capacity());

		unsyncStringWriter = new UnsyncStringWriter(32);

		stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);
		Assert.assertEquals(32, stringBundler.capacity());
	}

	@Test
	public void testFlushAndClose() {
		try (UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {
			unsyncStringWriter.flush();
		}
	}

	@Test
	public void testGetStringBuilder() throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertSame(
			_stringBuilderField.get(unsyncStringWriter),
			unsyncStringWriter.getStringBuilder());
	}

	@Test
	public void testGetStringBundler() throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertSame(stringBundler, unsyncStringWriter.getStringBundler());
	}

	@Test
	public void testReset() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		unsyncStringWriter.write("test1");

		Assert.assertEquals(5, stringBuilder.length());

		unsyncStringWriter.reset();

		Assert.assertEquals(0, stringBuilder.length());

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		unsyncStringWriter.write("test1");

		Assert.assertEquals(1, stringBundler.index());

		unsyncStringWriter.reset();

		Assert.assertEquals(0, stringBundler.index());
	}

	@Test
	public void testToString() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, stringBuilder.length());
		Assert.assertEquals("a", unsyncStringWriter.toString());

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals("ab", unsyncStringWriter.toString());

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("a", unsyncStringWriter.toString());

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.toString());
	}

	@Test
	public void testWriteChar() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter.write('a');

		Assert.assertEquals(1, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));

		unsyncStringWriter.write('b');

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);

		unsyncStringWriter.write('a');

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("a", stringBundler.stringAt(0));

		unsyncStringWriter.write('b');

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("a", stringBundler.stringAt(0));
		Assert.assertEquals("b", stringBundler.stringAt(1));

		unsyncStringWriter.reset();

		unsyncStringWriter.write('\u00a1');

		Assert.assertEquals(1, stringBundler.length());
		Assert.assertEquals("\u00a1", stringBundler.stringAt(0));
	}

	@Test
	public void testWriteCharArray() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter.write("ab".toCharArray());

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));

		unsyncStringWriter.write("cd".toCharArray());

		Assert.assertEquals(4, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));
		Assert.assertEquals('c', stringBuilder.charAt(2));
		Assert.assertEquals('d', stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);

		unsyncStringWriter.write("ab".toCharArray());

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("ab", stringBundler.stringAt(0));

		unsyncStringWriter.write("cd".toCharArray());

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("ab", stringBundler.stringAt(0));
		Assert.assertEquals("cd", stringBundler.stringAt(1));
	}

	@Override
	@Test
	public void testWriteNullString() throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		unsyncStringWriter.write((String)null, 0, 4);

		Assert.assertEquals(StringPool.NULL, stringBundler.toString());

		unsyncStringWriter.reset();

		unsyncStringWriter.write(StringPool.NULL, 0, 4);

		Assert.assertEquals(StringPool.NULL, stringBundler.toString());
	}

	@Test
	public void testWriteString() throws Exception {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		StringBuilder stringBuilder = (StringBuilder)_stringBuilderField.get(
			unsyncStringWriter);

		Assert.assertNotNull(stringBuilder);

		Assert.assertNull(_stringBundlerField.get(unsyncStringWriter));

		unsyncStringWriter.write("ab");

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));

		unsyncStringWriter.write("cd");

		Assert.assertEquals(4, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));
		Assert.assertEquals('c', stringBuilder.charAt(2));
		Assert.assertEquals('d', stringBuilder.charAt(3));

		unsyncStringWriter.reset();

		unsyncStringWriter.write("ab", 0, 1);

		Assert.assertEquals(1, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));

		unsyncStringWriter.write("ab", 1, 1);

		Assert.assertEquals(2, stringBuilder.length());
		Assert.assertEquals('a', stringBuilder.charAt(0));
		Assert.assertEquals('b', stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		StringBundler stringBundler = (StringBundler)_stringBundlerField.get(
			unsyncStringWriter);

		Assert.assertNull(_stringBuilderField.get(unsyncStringWriter));

		Assert.assertNotNull(stringBundler);

		unsyncStringWriter.write("ab");

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("ab", stringBundler.stringAt(0));

		unsyncStringWriter.write("cd");

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("ab", stringBundler.stringAt(0));
		Assert.assertEquals("cd", stringBundler.stringAt(1));

		unsyncStringWriter.reset();

		unsyncStringWriter.write("ab", 0, 1);

		Assert.assertEquals(1, stringBundler.index());
		Assert.assertEquals("a", stringBundler.stringAt(0));

		unsyncStringWriter.write("ab", 1, 1);

		Assert.assertEquals(2, stringBundler.index());
		Assert.assertEquals("a", stringBundler.stringAt(0));
		Assert.assertEquals("b", stringBundler.stringAt(1));
	}

	@Override
	protected Writer getWriter() {
		return new UnsyncStringWriter(false);
	}

	private static final Field _stringBuilderField =
		ReflectionTestUtil.getField(UnsyncStringWriter.class, "_stringBuilder");
	private static final Field _stringBundlerField =
		ReflectionTestUtil.getField(UnsyncStringWriter.class, "_stringBundler");

}