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

package com.liferay.portal.kernel.io.unsync;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class UnsyncStringWriterTest {

	@Test
	public void testAppendChar() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));
		Assert.assertEquals("b", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testAppendCharSequence() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append(new StringBuilder("ab"));

		Assert.assertEquals(2, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		unsyncStringWriter.append(new StringBuilder("cd"));

		Assert.assertEquals(4, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));
		Assert.assertEquals('c', unsyncStringWriter.stringBuilder.charAt(2));
		Assert.assertEquals('d', unsyncStringWriter.stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append(new StringBuilder("ab"));

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.append(new StringBuilder("cd"));

		Assert.assertEquals(2, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));
		Assert.assertEquals("cd", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testConstructor() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertEquals(16, unsyncStringWriter.stringBuilder.capacity());
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter = new UnsyncStringWriter(false, 32);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertEquals(32, unsyncStringWriter.stringBuilder.capacity());
		Assert.assertNull(unsyncStringWriter.stringBundler);

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);
		Assert.assertEquals(16, unsyncStringWriter.stringBundler.capacity());

		unsyncStringWriter = new UnsyncStringWriter(true, 32);

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);
		Assert.assertEquals(32, unsyncStringWriter.stringBundler.capacity());
	}

	@Test
	public void testReset() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		unsyncStringWriter.write("test1");

		Assert.assertEquals(5, unsyncStringWriter.stringBuilder.length());

		unsyncStringWriter.reset();

		Assert.assertEquals(0, unsyncStringWriter.stringBuilder.length());

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		unsyncStringWriter.write("test1");

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());

		unsyncStringWriter.reset();

		Assert.assertEquals(0, unsyncStringWriter.stringBundler.index());
	}

	@Test
	public void testToString() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals("a", unsyncStringWriter.toString());

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals("ab", unsyncStringWriter.toString());

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.append('a');

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("a", unsyncStringWriter.toString());

		unsyncStringWriter.append('b');

		Assert.assertEquals(2, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.toString());
	}

	@Test
	public void testWriteChar() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write('a');

		Assert.assertEquals(1, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));

		unsyncStringWriter.write('b');

		Assert.assertEquals(2, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write('a');

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.write('b');

		Assert.assertEquals(2, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("a", unsyncStringWriter.stringBundler.stringAt(0));
		Assert.assertEquals("b", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testWriteCharArray() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab".toCharArray());

		Assert.assertEquals(2, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		unsyncStringWriter.write("cd".toCharArray());

		Assert.assertEquals(4, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));
		Assert.assertEquals('c', unsyncStringWriter.stringBuilder.charAt(2));
		Assert.assertEquals('d', unsyncStringWriter.stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab".toCharArray());

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.write("cd".toCharArray());

		Assert.assertEquals(2, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));
		Assert.assertEquals("cd", unsyncStringWriter.stringBundler.stringAt(1));
	}

	@Test
	public void testWriteString() {

		// StringBuilder

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(false);

		Assert.assertNotNull(unsyncStringWriter.stringBuilder);
		Assert.assertNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab");

		Assert.assertEquals(2, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));

		unsyncStringWriter.write("cd");

		Assert.assertEquals(4, unsyncStringWriter.stringBuilder.length());
		Assert.assertEquals('a', unsyncStringWriter.stringBuilder.charAt(0));
		Assert.assertEquals('b', unsyncStringWriter.stringBuilder.charAt(1));
		Assert.assertEquals('c', unsyncStringWriter.stringBuilder.charAt(2));
		Assert.assertEquals('d', unsyncStringWriter.stringBuilder.charAt(3));

		// StringBundler

		unsyncStringWriter = new UnsyncStringWriter();

		Assert.assertNull(unsyncStringWriter.stringBuilder);
		Assert.assertNotNull(unsyncStringWriter.stringBundler);

		unsyncStringWriter.write("ab");

		Assert.assertEquals(1, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));

		unsyncStringWriter.write("cd");

		Assert.assertEquals(2, unsyncStringWriter.stringBundler.index());
		Assert.assertEquals("ab", unsyncStringWriter.stringBundler.stringAt(0));
		Assert.assertEquals("cd", unsyncStringWriter.stringBundler.stringAt(1));
	}

}