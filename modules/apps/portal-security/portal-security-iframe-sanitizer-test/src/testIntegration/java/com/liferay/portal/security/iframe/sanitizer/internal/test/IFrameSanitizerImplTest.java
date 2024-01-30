/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.iframe.sanitizer.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class IFrameSanitizerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSanitizeHTMLWithIFrame() throws Exception {
		_withConfiguration(
			true, false, "",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG,
				_sanitize(
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					ContentTypes.TEXT_HTML)));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndConfigurationDisabled()
		throws Exception {

		_withConfiguration(
			false, false, "",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				_sanitize(
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					ContentTypes.TEXT_HTML)));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndRemoveIFrameTags()
		throws Exception {

		_withConfiguration(
			true, true, "",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT,
				_sanitize(
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					ContentTypes.TEXT_HTML)));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndSandboxAttributeValues()
		throws Exception {

		_withConfiguration(
			true, false, "test",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX,
				_sanitize(
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					ContentTypes.TEXT_HTML)));
	}

	@Test
	public void testSanitizeHTMLWithInvalidContentType() throws Exception {
		_withConfiguration(
			true, false, "",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				_sanitize(
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG, "text/creole")));
	}

	@Test
	public void testSanitizeHTMLWithNullContent() throws Exception {
		_withConfiguration(
			true, false, "",
			() -> Assert.assertEquals(
				StringPool.BLANK,
				_sanitize(StringPool.BLANK, ContentTypes.TEXT_HTML)));
	}

	@Test
	public void testSanitizeHTMLWithNullContentType() throws Exception {
		_withConfiguration(
			true, false, "",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				_sanitize(
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					StringPool.BLANK)));
	}

	@Test
	public void testSanitizeHTMLWithoutIFrame() throws Exception {
		_withConfiguration(
			true, false, "",
			() -> Assert.assertEquals(
				_BASIC_HTML_CONTENT,
				_sanitize(_BASIC_HTML_CONTENT, ContentTypes.TEXT_HTML)));
	}

	@Test
	public void testSanitizeTextWithoutIFrame() throws Exception {
		_withConfiguration(
			true, false, "",
			() -> {
				Assert.assertEquals(
					_BASIC_CONTENT,
					_sanitize(_BASIC_CONTENT, ContentTypes.TEXT_PLAIN));

				Assert.assertEquals(
					_BASIC_CONTENT,
					_sanitize(_BASIC_HTML_CONTENT, ContentTypes.TEXT_PLAIN));
			});
	}

	private String _sanitize(String content, String contentType)
		throws Exception {

		return _iFrameSanitizer.sanitize(
			0, 0, 0, StringPool.BLANK, 0, contentType, new String[0], content,
			new HashMap<>());
	}

	private void _withConfiguration(
			boolean enabled, boolean removeIFrameTags,
			String sandboxAttributeValues,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.security.iframe.sanitizer." +
						"configuration.IFrameConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", enabled
					).put(
						"removeIFrameTags", removeIFrameTags
					).put(
						"sandboxAttributeValues", sandboxAttributeValues
					).build())) {

			unsafeRunnable.run();
		}
	}

	private static final String _BASIC_CONTENT = "Content";

	private static final String _BASIC_HTML_CONTENT =
		"<h1><strong>Content</strong></h1>";

	private static final String _EXPECTED_IFRAME_TAG =
		"<iframe src=\"test\" sandbox=\"\"></iframe>";

	private static final String _EXPECTED_IFRAME_TAG_SANDBOX =
		"<iframe src=\"test\" sandbox=\"test\"></iframe>";

	private static final String _INITIAL_IFRAME_TAG =
		"<iframe src=\"test\"></iframe>";

	@Inject(
		filter = "component.name=com.liferay.portal.security.iframe.sanitizer.internal.IFrameSanitizerImpl"
	)
	private Sanitizer _iFrameSanitizer;

}