/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.servlet.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Stian Sigvartsen
 */
public class ContentSecurityPolicyFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testHTMLContent() {
		Assert.assertEquals(
			StringBundler.concat(
				_HTML_CONTEXT_START,
				StringUtil.merge(_HTML_CONTENT, " nonce=\"" + _NONCE + "\""),
				_HTML_CONTEXT_END),
			_contentSecurityPolicyFilter.updateContent(
				StringBundler.concat(
					_HTML_CONTEXT_START,
					StringUtil.merge(_HTML_CONTENT, StringPool.BLANK),
					_HTML_CONTEXT_END),
				_NONCE));
	}

	@Test
	public void testJSAdjoiningHTMLContent() {
		ContentSecurityPolicyFilter contentSecurityPolicyFilter =
			new ContentSecurityPolicyFilter();

		String jsObjectLiteralContextStart = StringBundler.concat(
			"<script>index.render('', {\\\"", StringUtil.randomString(),
			"\\\":\\\"", StringUtil.randomString(), "\\\", ",
			"\\\"content\\\":\\\"");
		String jsObjectLiteralContextEnd =
			"\\\"}, '" + StringUtil.randomString() + "');</script>";

		Assert.assertEquals(
			StringBundler.concat(
				_HTML_CONTEXT_START, jsObjectLiteralContextStart,
				jsObjectLiteralContextEnd,
				StringUtil.merge(_HTML_CONTENT, " nonce=\"" + _NONCE + "\""),
				jsObjectLiteralContextStart, jsObjectLiteralContextEnd,
				_HTML_CONTEXT_END),
			contentSecurityPolicyFilter.updateContent(
				StringBundler.concat(
					_HTML_CONTEXT_START, jsObjectLiteralContextStart,
					jsObjectLiteralContextEnd,
					StringUtil.merge(_HTML_CONTENT, StringPool.BLANK),
					jsObjectLiteralContextStart, jsObjectLiteralContextEnd,
					_HTML_CONTEXT_END),
				_NONCE));
	}

	@Test
	public void testJSProvidedHTMLContent() {
		ContentSecurityPolicyFilter contentSecurityPolicyFilter =
			new ContentSecurityPolicyFilter();

		String jsObjectLiteralContextStart = StringBundler.concat(
			"<script>index.render('', {\\\"", StringUtil.randomString(),
			"\\\":\\\"", StringUtil.randomString(), "\\\", ",
			"\\\"content\\\":\\\"");
		String jsObjectLiteralContextEnd =
			"\\\"}, '" + StringUtil.randomString() + "');</script>";

		Assert.assertEquals(
			StringBundler.concat(
				_HTML_CONTEXT_START, jsObjectLiteralContextStart,
				_escapeJS(
					StringUtil.merge(
						_HTML_CONTENT, " nonce=\"" + _NONCE + "\"")),
				jsObjectLiteralContextEnd, _HTML_CONTEXT_END),
			contentSecurityPolicyFilter.updateContent(
				StringBundler.concat(
					_HTML_CONTEXT_START, jsObjectLiteralContextStart,
					_escapeJS(
						StringUtil.merge(_HTML_CONTENT, StringPool.BLANK)),
					jsObjectLiteralContextEnd, _HTML_CONTEXT_END),
				_NONCE));
	}

	private String _escapeJS(String content) {
		return content.replaceAll("\"", "\\\\\"");
	}

	private static final String[] _HTML_CONTENT = {
		StringBundler.concat(
			"<div id=\"", StringUtil.randomString(), "\"><div class=\"\">",
			StringUtil.randomString(), "</div></div><style"),
		StringBundler.concat(
			">.", StringUtil.randomString(), "{", StringUtil.randomString(),
			":", StringUtil.randomString(), ";}</style>")
	};

	private static final String _HTML_CONTEXT_END = "</body></html>";

	private static final String _HTML_CONTEXT_START = "<html><body>";

	// Reserved RegEx characters. Should add '$', '\' also

	private static final String _NONCE = "NONCE_.+*?^()[]{}|";

	private final ContentSecurityPolicyFilter _contentSecurityPolicyFilter =
		new ContentSecurityPolicyFilter();

}