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

package com.liferay.portal.html.parser.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Olaf Kock
 * @author Neil Zhao Jin
 */
public class HtmlParserImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExtraction() {
		Assert.assertEquals(
			"whitespace removal",
			_htmlParserImpl.extractText("   whitespace \n   <br/> removal   "));
		Assert.assertEquals(
			"script removal",
			_htmlParserImpl.extractText(
				"script <script>   test   </script> removal"));
		Assert.assertEquals(
			"HTML attribute removal",
			_htmlParserImpl.extractText(
				"<h1>HTML</h1> <i>attribute</i> <strong>removal</strong>"));
		Assert.assertEquals(
			"onclick removal",
			_htmlParserImpl.extractText(
				"<div onclick=\"honk()\">onclick removal</div>"));
	}

	private final HtmlParserImpl _htmlParserImpl = new HtmlParserImpl();

}