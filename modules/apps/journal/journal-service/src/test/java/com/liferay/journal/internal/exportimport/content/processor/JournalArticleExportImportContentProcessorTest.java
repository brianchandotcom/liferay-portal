/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class JournalArticleExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultipleLinesHTMLComment() {
		_testExcludeHTMLComments(
			"<p>Just a test</p>",
			"<p>Just <!-- with a\n HTML comment -->a test</p>");
	}

	@Test
	public void testNestedHTMLComment() {
		_testExcludeHTMLComments(
			"<p>Just a test</p>",
			"<p>Just <!-- with a <!-- inside --> HTML comment -->a test</p>");
	}

	@Test
	public void testNoHTMLComment() {
		_testExcludeHTMLComments("<p>Just a test</p>", "<p>Just a test</p>");
	}

	@Test
	public void testSingleLineHTMLComment() {
		_testExcludeHTMLComments(
			"<p>Just a test</p>",
			"<p>Just <!-- with a HTML comment -->a test</p>");
	}

	private void _testExcludeHTMLComments(
		String expectedContent, String content) {

		String excludedHtmlCommentContent = ReflectionTestUtil.invoke(
			new JournalArticleExportImportContentProcessor(),
			"_excludeHTMLComments", new Class<?>[] {String.class}, content);

		Assert.assertEquals(expectedContent, excludedHtmlCommentContent);
	}

}