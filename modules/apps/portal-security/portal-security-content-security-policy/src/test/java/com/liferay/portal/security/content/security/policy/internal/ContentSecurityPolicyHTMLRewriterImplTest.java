/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class ContentSecurityPolicyHTMLRewriterImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRewriteInlineEventHandlers() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html =
			"<div onclick=\"alert(1);\" onchange=\"alert(2);\">hey</div>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", false);

		Assert.assertTrue(
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.getElementById\\('[^']+'\\)\\.onchange=" +
					"function\\(event\\)\\{alert\\(2\\);}.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.getElementById\\('[^']+'\\)\\.onclick=" +
					"function\\(event\\)\\{alert\\(1\\);}.*"));
		Assert.assertTrue(_matches(html, "<div id=\"[^\"]+\">.*</div>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersMantainsIdWhenPresent() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html = "<div id=\"TEST_ID\" onclick=\"alert(1);\">hey</div>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", false);

		Assert.assertTrue(
			_matches(html, ".*document\\.getElementById\\('TEST_ID'\\).*"));
		Assert.assertTrue(_matches(html, "<div id=\"TEST_ID\">.*</div>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersNonrecursive() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html =
			"<body onload=\"alert(1);\"><div onclick=\"alert(2);\">hey</div>" +
				"</body>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", false);

		Assert.assertFalse(
			_matches(
				html,
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(2\\);.*" +
					"</script>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersPassingBodyTag() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html =
			"<body onclick=\"alert(1);\" onchange=\"alert(2);\">hey</body>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", false);

		Assert.assertTrue(_matches(html, ".*</body>"));
		Assert.assertTrue(
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.body\\.onchange=" +
					"function\\(event\\)\\{alert\\(2\\);}.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.body\\.onclick=" +
					"function\\(event\\)\\{alert\\(1\\);}.*"));
		Assert.assertTrue(_matches(html, "<body>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersPassingUpperCaseBodyTag() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html = "<BODY onclick=\"alert(1);\">hey</BODY>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", false);

		Assert.assertTrue(_matches(html, ".*</body>"));
		Assert.assertTrue(
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.body\\.onclick=" +
					"function\\(event\\)\\{alert\\(1\\);}.*"));
		Assert.assertTrue(_matches(html, "<body>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersProcessesMultipleTopNodes() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html =
			"<div onclick=\"alert(1);\">hey</div><div onclick=\"alert(2);\">" +
				"hey</div>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", false);

		Assert.assertTrue(
			_matches(
				html,
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(1\\);.*" +
					"onclick.*alert\\(2\\);.*</script>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersRecursive() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html =
			"<body onload=\"alert(1);\"><div onclick=\"alert(2);\">hey</div>" +
				"</body>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE", true);

		Assert.assertTrue(
			_matches(
				html,
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(2\\);.*" +
					"</script>.*"));
	}

	private boolean _matches(String html, String regexp) {
		Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(html);

		return matcher.matches();
	}

}