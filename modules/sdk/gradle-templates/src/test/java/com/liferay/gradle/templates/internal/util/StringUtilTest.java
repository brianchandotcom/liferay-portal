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

package com.liferay.gradle.templates.internal.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtilTest {

	@Test
	public void testCapitalize() {
		Assert.assertEquals("Foo", StringUtil.capitalize("foo", '-'));
		Assert.assertEquals("Foo.bar", StringUtil.capitalize("foo.bar", '-'));
		Assert.assertEquals("Foo-BAR", StringUtil.capitalize("foo-BAR", '-'));
		Assert.assertEquals(
			"Foo-Bar-Baz-Qux", StringUtil.capitalize("foo-bar-baz-qux", '-'));
	}

	@Test
	public void testRemoveChar() {
		Assert.assertEquals("foo", StringUtil.removeChar("foo", '-'));
		Assert.assertEquals(
			"foo.bar.baz.qux", StringUtil.removeChar("foo.bar.baz.qux", '-'));
		Assert.assertEquals("foobar", StringUtil.removeChar("-foo-bar-", '-'));
		Assert.assertEquals(
			"foobarbazqux", StringUtil.removeChar("foo-bar-baz-qux", '-'));
	}

}