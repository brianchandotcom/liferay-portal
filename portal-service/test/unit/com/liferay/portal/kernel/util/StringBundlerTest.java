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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.TestCase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Manuel de la Peña
 */
public class StringBundlerTest extends TestCase {

	@Test
	public void testAppendNullCharArray() {
		StringBundler sb = new StringBundler();

		sb.append((char[])null);

		assertEquals(1, sb.index());
		assertEquals("null", sb.stringAt(0));
	}

	@Test
	public void testAppendNullObject() {
		StringBundler sb = new StringBundler();

		sb.append((Object)null);

		assertEquals(1, sb.index());
		assertEquals("null", sb.stringAt(0));
	}

	@Test
	public void testAppendNullString() {
		StringBundler sb = new StringBundler();

		sb.append((String)null);

		assertEquals(1, sb.index());
		assertEquals(StringPool.NULL, sb.stringAt(0));
	}

	@Test
	public void testAppendNullStringArray() {
		StringBundler sb = new StringBundler();

		sb.append((String[])null);

		assertEquals(0, sb.index());
	}

	@Test
	public void testAppendNullStringBundler() {
		StringBundler sb = new StringBundler();

		sb.append((StringBundler)null);

		assertEquals(0, sb.index());
	}

	@Test
	public void testAppendStringArrayWithGrowth() {
		StringBundler sb = new StringBundler(2);

		sb.append(new String[] {"test1", "test2", "test3"});

		assertEquals(3, sb.index());
		assertEquals(10, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals("test3", sb.stringAt(2));

		sb = new StringBundler(2);

		sb.append(new String[] {"test1", "", "test3"});

		assertEquals(2, sb.index());
		assertEquals(10, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test3", sb.stringAt(1));

		sb = new StringBundler(2);

		sb.append(new String[] {"test1", "test2", null});

		assertEquals(2, sb.index());
		assertEquals(10, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
	}

	@Test
	public void testAppendStringArrayWithoutGrowth() {
		StringBundler sb = new StringBundler();

		sb.append(new String[] {"test1", "test2", "test3"});

		assertEquals(3, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals("test3", sb.stringAt(2));

		sb = new StringBundler();

		sb.append(new String[] {"test1", "", "test3"});

		assertEquals(2, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test3", sb.stringAt(1));

		sb = new StringBundler();

		sb.append(new String[] {"test1", "test2", null});

		assertEquals(2, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
	}

	@Test
	public void testAppendStringBundlerWithGrowth() {
		StringBundler sb = new StringBundler(2);

		StringBundler testSB = new StringBundler();

		testSB.append("test1");
		testSB.append("test2");
		testSB.append("test3");

		sb.append(testSB);

		assertEquals(3, sb.index());
		assertEquals(10, sb.capacity());
		assertEquals("test3", sb.stringAt(2));
	}

	@Test
	public void testAppendStringBundlerWithoutGrowth() {
		StringBundler sb = new StringBundler();

		StringBundler testSB = new StringBundler();

		testSB.append("test1");
		testSB.append("test2");
		testSB.append("test3");

		sb.append(testSB);

		assertEquals(3, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test3", sb.stringAt(2));
	}

	@Test
	public void testAppendStringWithGrowth() {
		StringBundler sb = new StringBundler(2);

		sb.append("test1");

		assertEquals(1, sb.index());
		assertEquals(2, sb.capacity());
		assertEquals("test1", sb.stringAt(0));

		sb.append("test2");

		assertEquals(2, sb.index());
		assertEquals(2, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		sb.append("test3");

		assertEquals(3, sb.index());
		assertEquals(4, sb.capacity());

		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals("test3", sb.stringAt(2));
	}

	@Test
	public void testAppendStringWithoutGrowing() {
		StringBundler sb = new StringBundler();

		sb.append("test1");

		assertEquals(1, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));

		sb.append("test2");

		assertEquals(2, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		sb.append("test3");

		assertEquals(3, sb.index());
		assertEquals(16, sb.capacity());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals("test3", sb.stringAt(2));
	}

	@Test
	public void testConstructor() {
		StringBundler sb = new StringBundler();

		assertEquals(0, sb.index());
		assertEquals(16, sb.capacity());
	}

	@Test
	public void testConstructorWithCapacity() {
		StringBundler sb = new StringBundler(32);

		assertEquals(0, sb.index());
		assertEquals(32, sb.capacity());
	}

	@Test
	public void testConstructorWithString() {
		StringBundler sb = new StringBundler("test");

		assertEquals(1, sb.index());
		assertEquals("test", sb.stringAt(0));
		assertEquals(16, sb.capacity());
	}

	@Test
	public void testConstructorWithStringArray() {
		StringBundler sb = new StringBundler(new String[] {"aa", "bb"});

		assertEquals(2, sb.index());
		assertEquals("aa", sb.stringAt(0));
		assertEquals("bb", sb.stringAt(1));
		assertEquals(2, sb.capacity());
	}

	@Test
	public void testConstructorWithStringArrayEmpty() {
		StringBundler sb = new StringBundler(new String[] {"", "bb"});

		assertEquals(1, sb.index());
		assertEquals("bb", sb.stringAt(0));
		assertEquals(2, sb.capacity());
	}

	@Test
	public void testConstructorWithStringArrayExtraSpace() {
		StringBundler sb = new StringBundler(new String[] {"aa", "bb"}, 3);

		assertEquals(2, sb.index());
		assertEquals("aa", sb.stringAt(0));
		assertEquals("bb", sb.stringAt(1));
		assertEquals(5, sb.capacity());
	}

	@Test
	public void testConstructorWithStringArrayExtraSpaceEmpty() {
		StringBundler sb = new StringBundler(new String[] {"", "bb"}, 3);

		assertEquals(1, sb.index());
		assertEquals("bb", sb.stringAt(0));
		assertEquals(5, sb.capacity());
	}

	@Test
	public void testConstructorWithStringArrayExtraSpaceNull() {
		StringBundler sb = new StringBundler(new String[] {"aa", null}, 3);

		assertEquals(1, sb.index());
		assertEquals("aa", sb.stringAt(0));
		assertEquals(5, sb.capacity());
	}

	@Test
	public void testConstructorWithStringArrayNull() {
		StringBundler sb = new StringBundler(new String[] {"aa", null});

		assertEquals(1, sb.index());
		assertEquals("aa", sb.stringAt(0));
		assertEquals(2, sb.capacity());
	}

	@Test
	public void testSerialization() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("test1");
		sb.append("test2");
		sb.append("test3");

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		objectOutputStream.writeObject(sb);

		objectOutputStream.close();

		byte[] bytes = unsyncByteArrayOutputStream.toByteArray();

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			unsyncByteArrayInputStream);

		StringBundler cloneSB = (StringBundler)objectInputStream.readObject();

		objectInputStream.close();

		assertEquals(sb.capacity(), cloneSB.capacity());
		assertEquals(sb.index(), cloneSB.index());

		for (int i = 0; i < sb.index(); i++) {
			assertEquals(sb.stringAt(i), cloneSB.stringAt(i));
		}
	}

	@Test
	public void testSetIndex() {

		// Negative index

		StringBundler sb = new StringBundler();

		try {
			sb.setIndex(-1);

			fail();
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
		}

		// New index equals current index

		sb = new StringBundler(4);

		sb.append("test1");
		sb.append("test2");

		assertEquals(2, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		sb.setIndex(2);

		assertEquals(2, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));

		// New index is larger than the current index but smaller than the array
		// size

		assertEquals(4, sb.capacity());

		sb.setIndex(4);

		assertEquals(4, sb.capacity());
		assertEquals(4, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals(StringPool.BLANK, sb.stringAt(2));
		assertEquals(StringPool.BLANK, sb.stringAt(3));

		// New index is larger than the current index and array size

		sb.setIndex(6);

		assertEquals(6, sb.capacity());
		assertEquals(6, sb.index());
		assertEquals("test1", sb.stringAt(0));
		assertEquals("test2", sb.stringAt(1));
		assertEquals(StringPool.BLANK, sb.stringAt(2));
		assertEquals(StringPool.BLANK, sb.stringAt(3));
		assertEquals(StringPool.BLANK, sb.stringAt(4));
		assertEquals(StringPool.BLANK, sb.stringAt(5));

		// New index is smaller than current index

		sb.setIndex(1);

		assertEquals(6, sb.capacity());
		assertEquals(1, sb.index());
		assertEquals("test1", sb.stringAt(0));

		try {
			assertEquals(null, sb.stringAt(1));

			fail();
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
		}
	}

	@Test
	public void testToString() {
		StringBundler sb = new StringBundler();

		sb.append("test1");
		sb.append("test2");
		sb.append("test3");

		assertEquals("test1test2test3", sb.toString());

		// StringBuilder

		sb.append("test4");

		assertEquals("test1test2test3test4", sb.toString());
	}

	@Test
	public void testToStringEmpty() {
		StringBundler sb = new StringBundler();

		assertEquals(StringPool.BLANK, sb.toString());
	}

	@Test
	public void testWriteTo() throws IOException {
		StringBundler sb = new StringBundler();

		sb.append("test1");
		sb.append("test2");
		sb.append("test3");
		sb.append("test4");
		sb.append("test5");

		StringWriter stringWriter = new StringWriter();

		sb.writeTo(stringWriter);

		assertEquals("test1test2test3test4test5", stringWriter.toString());
	}

}