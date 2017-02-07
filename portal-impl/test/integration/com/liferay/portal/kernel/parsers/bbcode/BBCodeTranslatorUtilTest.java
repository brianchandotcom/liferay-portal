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
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.Arrays;

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
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.bbcodetranslatorutil"));

	@Test
	public void testEmoticonDescriptions() {
		String[] emoticonDescriptions =
			BBCodeTranslatorUtil.getEmoticonDescriptions();

		Assert.assertEquals(
			Arrays.toString(emoticonDescriptions), 3,
			emoticonDescriptions.length);
	}

	@Test
	public void testEmoticonFiles() {
		String[] emoticonFiles = BBCodeTranslatorUtil.getEmoticonFiles();

		Assert.assertEquals(
			Arrays.toString(emoticonFiles), 2, emoticonFiles.length);
	}

	@Test
	public void testEmoticonSymbols() {
		String[] emoticonSymbols = BBCodeTranslatorUtil.getEmoticonSymbols();

		Assert.assertEquals(
			Arrays.toString(emoticonSymbols), 4, emoticonSymbols.length);
	}

	@Test
	public void testGetBBCodeTranslator() {
		BBCodeTranslator bbCodeTranslator =
			BBCodeTranslatorUtil.getBBCodeTranslator();

		Class<?> clazz = bbCodeTranslator.getClass();

		Assert.assertEquals(
			TestBBCodeTranslator.class.getName(), clazz.getName());
	}

	@Test
	public void testHTML() {
		Assert.assertEquals(
			TestBBCodeTranslator.START_TAG + "1" + TestBBCodeTranslator.END_TAG,
			BBCodeTranslatorUtil.getHTML("1"));
	}

	@Test
	public void testParse() {
		Assert.assertEquals(
			TestBBCodeTranslator.END_TAG,
			BBCodeTranslatorUtil.parse(TestBBCodeTranslator.START_TAG));
	}

}