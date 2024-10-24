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
			html, "TEST_NONCE");

		Assert.assertTrue(
			"Rewritten HTML does not start with [<div id=\"...]:\n" + html,
			_matches(html, "<div id=\"[^\"]+\">.*</div>.*"));

		Assert.assertTrue(
			"Rewritten HTML does not end with [<script nonce=\"...>]:\n" + html,
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>"));

		Assert.assertTrue(
			"Rewritten HTML does not contain onclick handler:\n" + html,
			_matches(
				html,
				".*document\\.getElementById\\('[^']+'\\)\\.onclick=" +
					"function\\(event\\)\\{alert\\(1\\);}.*"));

		Assert.assertTrue(
			"Rewritten HTML does not contain onchange handler:\n" + html,
			_matches(
				html,
				".*document\\.getElementById\\('[^']+'\\)\\.onchange=" +
					"function\\(event\\)\\{alert\\(2\\);}.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersMantainsIdWhenPresent() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html = "<div id=\"TEST_ID\" onclick=\"alert(1);\">hey</div>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE");

		Assert.assertTrue(
			"Rewritten HTML maintains the original id in HTML:\n" + html,
			_matches(html, "<div id=\"TEST_ID\">.*</div>.*"));

		Assert.assertTrue(
			"Rewritten HTML maintains the original id in script:\n" + html,
			_matches(html, ".*document\\.getElementById\\('TEST_ID'\\).*"));
	}

	@Test
	public void testRewriteInlineEventHandlersPassingBodyTag() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html =
			"<body onclick=\"alert(1);\" onchange=\"alert(2);\">hey</body>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE");

		Assert.assertTrue(
			"Rewritten HTML does not start with [<body>]:\n" + html,
			_matches(html, "<body>.*"));

		Assert.assertTrue(
			"Rewritten HTML does not end with [</body>]:\n" + html,
			_matches(html, ".*</body>"));

		Assert.assertTrue(
			"Rewritten HTML does not contain [<script nonce=\"...>]:\n" + html,
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>.*"));

		Assert.assertTrue(
			"Rewritten HTML does not contain onclick handler:\n" + html,
			_matches(
				html,
				".*document\\.body\\.onclick=" +
					"function\\(event\\)\\{alert\\(1\\);}.*"));

		Assert.assertTrue(
			"Rewritten HTML does not contain onchange handler:\n" + html,
			_matches(
				html,
				".*document\\.body\\.onchange=" +
					"function\\(event\\)\\{alert\\(2\\);}.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersPassingUpperCaseBodyTag() {
		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		String html = "<BODY onclick=\"alert(1);\">hey</BODY>";

		html = contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, "TEST_NONCE");

		Assert.assertTrue(
			"Rewritten HTML does not start with [<body>]:\n" + html,
			_matches(html, "<body>.*"));

		Assert.assertTrue(
			"Rewritten HTML does not end with [</body>]:\n" + html,
			_matches(html, ".*</body>"));

		Assert.assertTrue(
			"Rewritten HTML does not contain [<script nonce=\"...>]:\n" + html,
			_matches(html, ".*<script nonce=\"TEST_NONCE\">.*</script>.*"));

		Assert.assertTrue(
			"Rewritten HTML does not contain onclick handler:\n" + html,
			_matches(
				html,
				".*document\\.body\\.onclick=" +
					"function\\(event\\)\\{alert\\(1\\);}.*"));
	}

	private boolean _matches(String html, String regexp) {
		Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(html);

		return matcher.matches();
	}

}