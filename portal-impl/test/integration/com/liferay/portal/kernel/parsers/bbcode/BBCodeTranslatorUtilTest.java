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

package com.liferay.portal.kernel.parsers.bbcode;

import com.liferay.portal.kernel.parsers.bbcode.bundle.bbcodetranslatorutil.TestBBCodeTranslator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class BBCodeTranslatorUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.bbcodetranslatorutil"));

	@Test
	public void testEmoticonDescriptions() {
		Assert.assertEquals(
			3, BBCodeTranslatorUtil.getEmoticonDescriptions().length);
	}

	@Test
	public void testEmoticonFiles() {
		Assert.assertEquals(2, BBCodeTranslatorUtil.getEmoticonFiles().length);
	}

	@Test
	public void testEmoticonSymbols() {
		Assert.assertEquals(
			4, BBCodeTranslatorUtil.getEmoticonSymbols().length);
	}

	@Test
	public void testGetBBCodeTranslator() {
		Assert.assertEquals(
			TestBBCodeTranslator.class.getName(),
			BBCodeTranslatorUtil.getBBCodeTranslator().getClass().getName());
	}

	@Test
	public void testHTML() {
		String htmlTag =
			TestBBCodeTranslator.START_TAG + "1" + TestBBCodeTranslator.END_TAG;
		Assert.assertEquals(htmlTag, BBCodeTranslatorUtil.getHTML("1"));
	}

	@Test
	public void testParse() {
		Assert.assertEquals(
			TestBBCodeTranslator.END_TAG,
			BBCodeTranslatorUtil.parse(TestBBCodeTranslator.START_TAG));
	}

}