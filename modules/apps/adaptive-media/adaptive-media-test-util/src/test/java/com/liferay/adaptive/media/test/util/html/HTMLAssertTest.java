/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.test.util.html;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class HTMLAssertTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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