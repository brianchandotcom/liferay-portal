/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.content.security.policy.ContentSecurityPolicyNonceProvider;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

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
			"<div onclick=\"alert(1);\" onchange=\"alert(2);\">Yo!</div>",
			"TEST_NONCE", false);

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
			"<body onload=\"alert(1);\"><div onclick=\"alert(2);\">Yo!</div>" +
				"</body>";

		Assert.assertFalse(
			_matches(
				_rewriteInlineEventHandlers(html, "TEST_NONCE", false),
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(2\\);.*" +
					"</script>.*"));
		Assert.assertTrue(
			_matches(
				_rewriteInlineEventHandlers(html, "TEST_NONCE", true),
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(2\\);.*" +
					"</script>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersWithBody() {
		String html = _rewriteInlineEventHandlers(
			"<BODY onclick=\"alert(1);\">Yo!</BODY>", "TEST_NONCE", false);

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
			"<body onclick=\"alert(1);\" onchange=\"alert(2);\">Yo!</body>",
			"TEST_NONCE", false);

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
			"<div id=\"TEST_ID\" onclick=\"alert(1);\">Yo!</div>", "TEST_NONCE",
			false);

		Assert.assertTrue(
			_matches(html, ".*document\\.getElementById\\('TEST_ID'\\).*"));
		Assert.assertTrue(_matches(html, "<div id=\"TEST_ID\">.*</div>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersWithMultipleTopNodes() {
		String html = _rewriteInlineEventHandlers(
			"<div onclick=\"alert(1);\">Yo!</div><div onclick=\"alert(2);\">" +
				"Yo!</div>",
			"TEST_NONCE", false);

		Assert.assertTrue(
			_matches(
				html,
				".*<script nonce=\"TEST_NONCE\">.*onclick.*alert\\(1\\);.*" +
					"onclick.*alert\\(2\\);.*</script>.*"));
	}

	@Test
	public void testRewriteInlineEventHandlersWithoutNonce() {
		String html =
			"<div onclick=\"alert(1);\" onchange=\"alert(2);\">Yo!</div>";

		Assert.assertEquals(
			html, _rewriteInlineEventHandlers(html, StringPool.BLANK, false));
	}

	private boolean _matches(String html, String regexp) {
		Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(html);

		return matcher.matches();
	}

	private String _rewriteInlineEventHandlers(
		String html, String nonce, boolean recursive) {

		ContentSecurityPolicyHTMLRewriterImpl
			contentSecurityPolicyHTMLRewriterImpl =
				new ContentSecurityPolicyHTMLRewriterImpl();

		ContentSecurityPolicyNonceProvider contentSecurityPolicyNonceProvider =
			Mockito.mock(ContentSecurityPolicyNonceProvider.class);

		Mockito.when(
			contentSecurityPolicyNonceProvider.getNonce(Mockito.any())
		).thenReturn(
			nonce
		);

		ReflectionTestUtil.setFieldValue(
			contentSecurityPolicyHTMLRewriterImpl,
			"_contentSecurityPolicyNonceProvider",
			contentSecurityPolicyNonceProvider);

		return contentSecurityPolicyHTMLRewriterImpl.rewriteInlineEventHandlers(
			html, null, recursive);
	}

}