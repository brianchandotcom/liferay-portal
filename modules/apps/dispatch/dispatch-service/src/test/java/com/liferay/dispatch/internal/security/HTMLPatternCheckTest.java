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

package com.liferay.dispatch.internal.security;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class HTMLPatternCheckTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testHasHtml() {
		Assert.assertTrue(
			HTMLPatternCheck.hasHtml("This is <p>html injected</p>"));
		Assert.assertTrue(
			HTMLPatternCheck.hasHtml("This is <liferay-ui:input/>"));
		Assert.assertTrue(
			HTMLPatternCheck.hasHtml("<liferay-ui:input>I</liferay-ui:input>"));
		Assert.assertTrue(HTMLPatternCheck.hasHtml("This is <liferay-ui/>"));
		Assert.assertTrue(HTMLPatternCheck.hasHtml("This is <br />"));
		Assert.assertTrue(
			HTMLPatternCheck.hasHtml("\"<script>script code</script>"));
		Assert.assertFalse(HTMLPatternCheck.hasHtml("\"No html code here"));
		Assert.assertFalse(HTMLPatternCheck.hasHtml("No html code here -"));
		Assert.assertFalse(HTMLPatternCheck.hasHtml("A-456-artifact-0.6.zip"));
	}

}