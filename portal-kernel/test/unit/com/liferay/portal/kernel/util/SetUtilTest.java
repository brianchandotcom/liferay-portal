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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SetUtilTest {

	@Test
	public void testConstructor() {
		new SetUtil();
	}

	@Test
	public void testIntersectEmptyShortcut() {
		Assert.assertSame(
			Collections.emptySet(),
			SetUtil.intersect(new ArrayList<String>(), Arrays.asList("a")));
		Assert.assertSame(
			Collections.emptySet(),
			SetUtil.intersect(Arrays.asList("a"), new ArrayList<String>()));
	}

	@Test
	public void testIntersectWithoutWrapping() {
		Set<String> set1 = new HashSet<>(Arrays.asList("a", "b", "c"));
		Set<String> set2 = new HashSet<>(Arrays.asList("c", "d"));

		Assert.assertSame(set2, SetUtil.intersect(set1, set2));
		Assert.assertEquals(set2, new HashSet<String>(Arrays.asList("c")));

		Set<String> set3 = new HashSet<>(Arrays.asList("c", "d", "e"));

		Assert.assertSame(set1, SetUtil.intersect(set1, set3));

		Assert.assertEquals(set1, new HashSet<String>(Arrays.asList("c")));
	}

	@Test
	public void testIntersectWithWrapping() {
		List<String> list1 = Arrays.asList("a", "b", "c");
		List<String> list2 = Arrays.asList("c", "d");

		Set<String> set = SetUtil.intersect(list1, list2);

		Assert.assertEquals(set, new HashSet<String>(Arrays.asList("c")));

		List<String> list3 = Arrays.asList("c", "d", "e");

		set = SetUtil.intersect(list1, list3);

		Assert.assertEquals(set, new HashSet<String>(Arrays.asList("c")));
	}

}