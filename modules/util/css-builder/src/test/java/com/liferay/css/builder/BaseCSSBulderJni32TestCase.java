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

package com.liferay.css.builder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eduardo García
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public abstract class BaseCSSBulderJni32TestCase
	extends BaseCSSBuilderTestCase {

	@Test
	public void testCSSBuilderWithJni32() throws Exception {
		String output = testCSSBuilder(importDirPath, "jni32");

		Assert.assertTrue(
			output, output.contains("Using native 32-bit Sass compiler"));
	}

	@Test
	public void testCSSBuilderWithJni32AndPortalCommonJar() throws Exception {
		String output = testCSSBuilder(importJarPath, "jni32");

		Assert.assertTrue(
			output, output.contains("Using native 32-bit Sass compiler"));
	}

}