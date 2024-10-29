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
		String html = _rewriteInlineEventHandlers(
			"<div onclick=\"alert(1);\" onchange=\"alert(2);\">hey</div>",
			false);

		Assert.assertTrue(
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.getElementById\\('[^']+'\\)\\.onchange = " +
					"function\\(event\\) \\{alert\\(2\\);}.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.getElementById\\('[^']+'\\)\\.onclick = " +
					"function\\(event\\) \\{alert\\(1\\);}.*"));
		Assert.assertTrue(_matches(html, "<div id=\"[^\"]+\">.*</div>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersRecursive() {
		String html =
			"<body onload=\"alert(1);\"><div onclick=\"alert(2);\">hey</div>" +
				"</body>";

		Assert.assertFalse(
			_matches(
				_rewriteInlineEventHandlers(html, false),
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(2\\);.*" +
					"</script>.*"));
		Assert.assertTrue(
			_matches(
				_rewriteInlineEventHandlers(html, true),
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(2\\);.*" +
					"</script>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersWithBody() {
		String html = _rewriteInlineEventHandlers(
			"<BODY onclick=\"alert(1);\">hey</BODY>", false);

		Assert.assertTrue(_matches(html, ".*</body>"));
		Assert.assertTrue(
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.body\\.onclick = function\\(event\\) " +
					"\\{alert\\(1\\);}.*"));
		Assert.assertTrue(_matches(html, "<body>.*"));

		html = _rewriteInlineEventHandlers(
			"<body onclick=\"alert(1);\" onchange=\"alert(2);\">hey</body>",
			false);

		Assert.assertTrue(_matches(html, ".*</body>"));
		Assert.assertTrue(
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.body\\.onchange = function\\(event\\) " +
					"\\{alert\\(2\\);}.*"));
		Assert.assertTrue(
			_matches(
				html,
				".*document\\.body\\.onclick = function\\(event\\) " +
					"\\{alert\\(1\\);}.*"));
		Assert.assertTrue(_matches(html, "<body>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersWithId() {
		String html = _rewriteInlineEventHandlers(
			"<div id=\"TEST_ID\" onclick=\"alert(1);\">hey</div>", false);

		Assert.assertTrue(
			_matches(html, ".*document\\.getElementById\\('TEST_ID'\\).*"));
		Assert.assertTrue(_matches(html, "<div id=\"TEST_ID\">.*</div>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersWithMultipleTopNodes() {
		String html = _rewriteInlineEventHandlers(
			"<div onclick=\"alert(1);\">hey</div><div onclick=\"alert(2);\">" +
				"hey</div>",
			false);

		Assert.assertTrue(
			_matches(
				html,
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(1\\);.*" +
					"onclick.*alert\\(2\\);.*</script>.*"));
	}

	private boolean _matches(String html, String regexp) {
		Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(html);

		return matcher.matches();
	}

	private String _rewriteInlineEventHandlers(String html, boolean recursive) {
		return _contentSecurityPolicyHTMLRewriterImpl.
			rewriteInlineEventHandlers(html, "TEST_NONCE", recursive);
	}

	private final ContentSecurityPolicyHTMLRewriterImpl
		_contentSecurityPolicyHTMLRewriterImpl =
			new ContentSecurityPolicyHTMLRewriterImpl();

}