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

package com.liferay.adaptive.media.test.util.html;

import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class HTMLAssertTest {

	@Test
	public void testAssertHTMLEqualsWithChildren() {
		HTMLAssert.assertHTMLEquals(
			"<div attr1=\"value1\"><img src=\"url\"/></div>",
			"<div attr1=\"value1\"><img src=\"url\"/></div>");
	}

	@Test(expected = AssertionError.class)
	public void testAssertHTMLEqualsWithDifferentAttributes() {
		HTMLAssert.assertHTMLEquals(
			"<div attr1=\"value1\"></div>",
			"<div attr1=\"value1\" attr2=\"value2\"></div>");
	}

	@Test(expected = AssertionError.class)
	public void testAssertHTMLEqualsWithDifferentChildren() {
		HTMLAssert.assertHTMLEquals(
			"<div attr1=\"value1\"><img src=\"url1\"/></div>",
			"<div attr1=\"value1\"><img src=\"url2\"/></div>");
	}

	@Test(expected = AssertionError.class)
	public void testAssertHTMLEqualsWithDifferentHTML() {
		HTMLAssert.assertHTMLEquals(
			"<div attr1=\"value1\" attr2=\"value2\"></div>",
			"<div attr1=\"value2\" attr2=\"value1\"></div>");
	}

	@Test
	public void testAssertHTMLEqualsWithEqualHTML() {
		HTMLAssert.assertHTMLEquals(
			"<div attr1=\"value1\" attr2=\"value2\"></div>",
			"<div attr2=\"value2\" attr1=\"value1\"></div>");
	}

	@Test
	public void testAssertHTMLEqualsWithEqualHTMLButDifferentAttributeOrder() {
		HTMLAssert.assertHTMLEquals(
			"<div attr1=\"value1\" attr2=\"value2\"></div>",
			"<div attr2=\"value2\" attr1=\"value1\"></div>");
	}

}