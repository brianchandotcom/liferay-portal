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

package com.liferay.portal.kernel.io.unsync;

import com.liferay.portal.kernel.test.TestCase;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringWriterTest extends TestCase {

	@Test
	public void testAppendChar() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		assertEquals(1, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));

		unsyncStringWriter.append('b');

		assertEquals(2, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		assertEquals(1, unsyncStringWriter.stringBundler.index());
		assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.append('b');

		assertEquals(2, unsyncStringWriter.stringBundler.index());
		assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));
		assertEquals("b", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testAppendCharSequence() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append(new StringBuilder("ab"));

		assertEquals(2, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		unsyncStringWriter.append(new StringBuilder("cd"));

		assertEquals(4, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));
		assertEquals('c', unsyncStringWriter.stringBuilder.charAt(2));
		assertEquals('d', unsyncStringWriter.stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append(new StringBuilder("ab"));

		assertEquals(1, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.append(new StringBuilder("cd"));

		assertEquals(2, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));
		assertEquals("cd", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testConstructor() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertEquals(16, unsyncStringWriter.stringBuilder.capacity());
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter = new UnsyncStringWriter(false, 32);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertEquals(32, unsyncStringWriter.stringBuilder.capacity());
		assertNull(unsyncStringWriter.stringBundler);

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);
		assertEquals(16, unsyncStringWriter.stringBundler.capacity());

		unsyncStringWriter = new UnsyncStringWriter(true, 32);

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);
		assertEquals(32, unsyncStringWriter.stringBundler.capacity());
	}

	@Test
	public void testReset() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		unsyncStringWriter.write("test1");

		assertEquals(5, unsyncStringWriter.stringBuilder.length());

		unsyncStringWriter.reset();

		assertEquals(0, unsyncStringWriter.stringBuilder.length());

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		unsyncStringWriter.write("test1");

		assertEquals(1, unsyncStringWriter.stringBundler.index());

		unsyncStringWriter.reset();

		assertEquals(0, unsyncStringWriter.stringBundler.index());
	}

	@Test
	public void testToString() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		assertEquals(1, unsyncStringWriter.stringBuilder.length());
		assertEquals("a", unsyncStringWriter.toString());

		unsyncStringWriter.append('b');

		assertEquals(2, unsyncStringWriter.stringBuilder.length());
		assertEquals("ab", unsyncStringWriter.toString());

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		assertEquals(1, unsyncStringWriter.stringBundler.index());
		assertEquals("a", unsyncStringWriter.toString());

		unsyncStringWriter.append('b');

		assertEquals(2, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.toString());
	}

	@Test
	public void testWriteChar() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write('a');

		assertEquals(1, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));

		unsyncStringWriter.write('b');

		assertEquals(2, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write('a');

		assertEquals(1, unsyncStringWriter.stringBundler.index());
		assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.write('b');

		assertEquals(2, unsyncStringWriter.stringBundler.index());
		assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));
		assertEquals("b", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testWriteCharArray() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab".toCharArray());

		assertEquals(2, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		unsyncStringWriter.write("cd".toCharArray());

		assertEquals(4, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));
		assertEquals('c', unsyncStringWriter.stringBuilder.charAt(2));
		assertEquals('d', unsyncStringWriter.stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab".toCharArray());

		assertEquals(1, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.write("cd".toCharArray());

		assertEquals(2, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));
		assertEquals("cd", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testWriteString() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		assertNotNull(unsyncStringWriter.stringBuilder);
		assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab");

		assertEquals(2, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		unsyncStringWriter.write("cd");

		assertEquals(4, unsyncStringWriter.stringBuilder.length());
		assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));
		assertEquals('c', unsyncStringWriter.stringBuilder.charAt(2));
		assertEquals('d', unsyncStringWriter.stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		assertNull(unsyncStringWriter.stringBuilder);
		assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab");

		assertEquals(1, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.write("cd");

		assertEquals(2, unsyncStringWriter.stringBundler.index());
		assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));
		assertEquals("cd", unsyncStringWriter.stringBundler.stringAt(1));
	}

}