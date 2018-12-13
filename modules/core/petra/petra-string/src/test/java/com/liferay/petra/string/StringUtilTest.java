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

package com.liferay.petra.string;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 * @author Hugo Huijser
 * @author Preston Crary
 */
public class StringUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(CharPool.class);
				assertClasses.add(StringPool.class);
			}

		};

	@Test
	public void testConstructors() {
		new CharPool();
		new StringPool();
		new StringUtil();
	}

	@Test
	public void testMerge() {
		Assert.assertNull(StringUtil.merge((boolean[])null, null));
		Assert.assertNull(StringUtil.merge((byte[])null, null));
		Assert.assertNull(StringUtil.merge((char[])null, null));
		Assert.assertNull(StringUtil.merge((double[])null, null));
		Assert.assertNull(StringUtil.merge((float[])null, null));
		Assert.assertNull(StringUtil.merge((int[])null, null));
		Assert.assertNull(StringUtil.merge((long[])null, null));
		Assert.assertNull(StringUtil.merge((short[])null, null));
		Assert.assertNull(StringUtil.merge((String[])null, null));
		Assert.assertNull(StringUtil.merge((Object[])null, null));
		Assert.assertNull(StringUtil.merge((Object[])null, null, null));
		Assert.assertNull(StringUtil.merge((ArrayList<Object>)null, null));
		Assert.assertNull(
			StringUtil.merge((ArrayList<Object>)null, null, null));
		Assert.assertNull(StringUtil.merge((Collection<Object>)null, null));
		Assert.assertNull(
			StringUtil.merge((Collection<Object>)null, null, null));

		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new boolean[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new byte[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new char[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new double[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new float[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new int[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new long[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new short[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new String[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new Object[0], null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new Object[0], null, null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new ArrayList<>(), null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new ArrayList<>(), null, null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new LinkedList<>(), null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new LinkedList<>(), null, null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(Arrays.asList(), null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(Arrays.asList(), null, null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new HashSet<>(), null));
		Assert.assertEquals(
			StringPool.BLANK, StringUtil.merge(new HashSet<>(), null, null));

		Assert.assertEquals(
			StringPool.TRUE, StringUtil.merge(new boolean[] {true}, null));
		Assert.assertEquals("1", StringUtil.merge(new byte[] {1}, null));
		Assert.assertEquals("a", StringUtil.merge(new char[] {'a'}, null));
		Assert.assertEquals("1.2", StringUtil.merge(new double[] {1.2D}, null));
		Assert.assertEquals("1.0", StringUtil.merge(new float[] {1.0F}, null));
		Assert.assertEquals("2", StringUtil.merge(new int[] {2}, null));
		Assert.assertEquals("3", StringUtil.merge(new long[] {3L}, null));
		Assert.assertEquals("4", StringUtil.merge(new short[] {4}, null));
		Assert.assertEquals(
			"abc", StringUtil.merge(new String[] {"abc"}, null));
		Assert.assertEquals("4", StringUtil.merge(new Object[] {4}, null));
		Assert.assertEquals(
			"5x",
			StringUtil.merge(
				new Object[] {5}, obj -> obj.toString() + "x", null));
		Assert.assertEquals(
			"a", StringUtil.merge(new ArrayList<>(Arrays.asList("a")), null));
		Assert.assertEquals(
			"ax",
			StringUtil.merge(
				new ArrayList<>(Arrays.asList("a")), s -> s + "x", null));
		Assert.assertEquals("a", StringUtil.merge(Arrays.asList("a"), null));
		Assert.assertEquals(
			"ax", StringUtil.merge(Arrays.asList("a"), s -> s + "x", null));
		Assert.assertEquals(
			"a", StringUtil.merge(new HashSet<>(Arrays.asList("a")), null));
		Assert.assertEquals(
			"ax",
			StringUtil.merge(
				new HashSet<>(Arrays.asList("a")), s -> s + "x", null));

		Assert.assertEquals(
			"true,false",
			StringUtil.merge(new boolean[] {true, false}, StringPool.COMMA));
		Assert.assertEquals(
			"1,2", StringUtil.merge(new byte[] {1, 2}, StringPool.COMMA));
		Assert.assertEquals(
			"a,b", StringUtil.merge(new char[] {'a', 'b'}, StringPool.COMMA));
		Assert.assertEquals(
			"1.2,3.4",
			StringUtil.merge(new double[] {1.2D, 3.4D}, StringPool.COMMA));
		Assert.assertEquals(
			"1.0,2.0",
			StringUtil.merge(new float[] {1.0F, 2.0F}, StringPool.COMMA));
		Assert.assertEquals(
			"2,3", StringUtil.merge(new int[] {2, 3}, StringPool.COMMA));
		Assert.assertEquals(
			"3,4", StringUtil.merge(new long[] {3L, 4L}, StringPool.COMMA));
		Assert.assertEquals(
			"4,5", StringUtil.merge(new short[] {4, 5}, StringPool.COMMA));
		Assert.assertEquals(
			"abc,def",
			StringUtil.merge(new String[] {"abc", "def"}, StringPool.COMMA));
		Assert.assertEquals(
			"4,x", StringUtil.merge(new Object[] {4, "x"}, StringPool.COMMA));
		Assert.assertEquals(
			"5x,ax",
			StringUtil.merge(
				new Object[] {5, 'a'}, obj -> obj.toString() + "x",
				StringPool.COMMA));
		Assert.assertEquals(
			"a,b",
			StringUtil.merge(
				new ArrayList<>(Arrays.asList("a", "b")), StringPool.COMMA));
		Assert.assertEquals(
			"ax,bx",
			StringUtil.merge(
				new ArrayList<>(Arrays.asList("a", "b")), s -> s + "x",
				StringPool.COMMA));
		Assert.assertEquals(
			"a,b", StringUtil.merge(Arrays.asList("a", "b"), StringPool.COMMA));
		Assert.assertEquals(
			"ax,bx",
			StringUtil.merge(
				Arrays.asList("a", "b"), s -> s + "x", StringPool.COMMA));
		Assert.assertEquals(
			"a,b",
			StringUtil.merge(
				new HashSet<>(Arrays.asList("a", "b")), StringPool.COMMA));
		Assert.assertEquals(
			"ax,bx",
			StringUtil.merge(
				new HashSet<>(Arrays.asList("a", "b")), s -> s + "x",
				StringPool.COMMA));
	}

	@Test
	public void testSplit() {
		Assert.assertSame(Collections.emptyList(), StringUtil.split(null));
		Assert.assertSame(
			Collections.emptyList(), StringUtil.split(StringPool.BLANK));
		Assert.assertSame(
			Collections.emptyList(), StringUtil.split(StringPool.SPACE));

		Assert.assertEquals(
			Collections.<String>emptyList(),
			StringUtil.split(StringPool.COMMA));
		Assert.assertEquals(
			Collections.<String>emptyList(), StringUtil.split(",,,"));

		Assert.assertEquals(
			Collections.singletonList("test"), StringUtil.split("test"));
		Assert.assertEquals(
			Collections.singletonList("test"), StringUtil.split("test,"));
		Assert.assertEquals(
			Collections.singletonList("test"), StringUtil.split(",test"));

		Assert.assertEquals(
			Arrays.asList("test1", "test2"),
			StringUtil.split("test1-test2", CharPool.DASH));
	}

}