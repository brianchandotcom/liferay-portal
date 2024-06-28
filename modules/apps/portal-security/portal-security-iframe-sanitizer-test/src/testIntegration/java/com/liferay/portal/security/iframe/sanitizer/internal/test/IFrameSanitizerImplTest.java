/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.iframe.sanitizer.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Roberto Díaz
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class IFrameSanitizerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
	}

	@After
	public void tearDown() throws Exception {
		String filterString = StringBundler.concat(
			"(&(companyId=", _companyId, ")(service.factoryPid=",
			_CONFIGURATION_PID, ".scoped))");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (ArrayUtil.isNotEmpty(configurations)) {
			Configuration configuration = configurations[0];

			if (configuration != null) {
				configuration.delete();
			}
		}
	}

	@Test
	public void testSanitizeHTMLWithIFrame() throws Exception {
		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX_ADDED,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndConfigurationDisabled()
		throws Exception {

		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, false, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndRemoveIFrameTags()
		throws Exception {

		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, true, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndSandboxAttributeValues()
		throws Exception {

		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, "test",
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG_SANDBOX,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameAndSandboxAttributeValuesEmpty()
		throws Exception {

		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG_SANDBOX,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG_SANDBOX,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameBlacklistedClassName()
		throws Exception {

		String className = RandomTestUtil.randomString();

		_updateCompanyConfiguration(
			className, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX_ADDED,
			_sanitize(
				className, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameEnabledByDefault() throws Exception {
		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX_ADDED,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameJournalArticleClassNameWhitelistedByDefault()
		throws Exception {

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				"com.liferay.journal.model.JournalArticle", 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameScopedByCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		try {
			_updateCompanyConfiguration(
				StringPool.BLANK, company.getCompanyId(), false, false,
				StringPool.BLANK, StringPool.BLANK);

			Assert.assertEquals(
				_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX_ADDED,
				_sanitize(
					StringPool.BLANK, 0, _companyId,
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					ContentTypes.TEXT_HTML));

			Assert.assertEquals(
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				_sanitize(
					StringPool.BLANK, 0, company.getCompanyId(),
					_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
					ContentTypes.TEXT_HTML));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Test
	public void testSanitizeHTMLWithIFrameWhitelistedAll() throws Exception {
		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.STAR);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				RandomTestUtil.randomString(), 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameWhitelistedAllExceptClassNameBlacklisted()
		throws Exception {

		String className = RandomTestUtil.randomString();

		_updateCompanyConfiguration(
			className, _companyId, true, false, StringPool.BLANK,
			StringPool.STAR);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX_ADDED,
			_sanitize(
				className, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				RandomTestUtil.randomString(), 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithIFrameWhitelistedClassName()
		throws Exception {

		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		String className = RandomTestUtil.randomString();

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _EXPECTED_IFRAME_TAG_SANDBOX_ADDED,
			_sanitize(
				className, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));

		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			className);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				className, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithInvalidContentType() throws Exception {
		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_CONTENT,
			_sanitize(
				StringPool.BLANK, 0, _companyId, _BASIC_CONTENT,
				ContentTypes.TEXT_PLAIN));
		Assert.assertEquals(
			_BASIC_HTML_CONTENT,
			_sanitize(
				StringPool.BLANK, 0, _companyId, _BASIC_HTML_CONTENT,
				ContentTypes.TEXT_PLAIN));
		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG, "text/creole"));
	}

	@Test
	public void testSanitizeHTMLWithNullContent() throws Exception {
		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			StringPool.BLANK,
			_sanitize(
				StringPool.BLANK, 0, _companyId, StringPool.BLANK,
				ContentTypes.TEXT_HTML));
	}

	@Test
	public void testSanitizeHTMLWithNullContentType() throws Exception {
		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG,
			_sanitize(
				StringPool.BLANK, 0, _companyId,
				_BASIC_HTML_CONTENT + _INITIAL_IFRAME_TAG, StringPool.BLANK));
	}

	@Test
	public void testSanitizeHTMLWithoutIFrame() throws Exception {
		_updateCompanyConfiguration(
			StringPool.BLANK, _companyId, true, false, StringPool.BLANK,
			StringPool.BLANK);

		Assert.assertEquals(
			_BASIC_HTML_CONTENT,
			_sanitize(
				StringPool.BLANK, 0, _companyId, _BASIC_HTML_CONTENT,
				ContentTypes.TEXT_HTML));
	}

	private String _sanitize(
			String className, long classPK, long companyId, String content,
			String contentType)
		throws Exception {

		return _iFrameSanitizer.sanitize(
			companyId, 0, 0, className, classPK, contentType, new String[0],
			content, new HashMap<>());
	}

	private void _updateCompanyConfiguration(
			String blacklist, long companyId, boolean enabled,
			boolean removeIFrameTags, String sandboxAttributeValues,
			String whitelist)
		throws Exception {

		ConfigurationTestUtil.updateConfiguration(
			_CONFIGURATION_PID,
			() -> {
				_configurationProvider.saveCompanyConfiguration(
					companyId, _CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"blacklist", blacklist
					).put(
						"enabled", enabled
					).put(
						"removeIFrameTags", removeIFrameTags
					).put(
						"sandboxAttributeValues", sandboxAttributeValues
					).put(
						"whitelist", whitelist
					).build());

				Configuration configuration =
					_configurationAdmin.getConfiguration(
						_CONFIGURATION_PID, StringPool.QUESTION);

				configuration.update();
			});
	}

	private static final String _BASIC_CONTENT = "Content";

	private static final String _BASIC_HTML_CONTENT =
		"<h1><strong>Content</strong></h1>";

	private static final String _CONFIGURATION_PID =
		"com.liferay.portal.security.iframe.sanitizer.configuration." +
			"IFrameConfiguration";

	private static final String _EXPECTED_IFRAME_TAG_SANDBOX =
		"<iframe src=\"test\" sandbox=\"test\"></iframe>";

	private static final String _EXPECTED_IFRAME_TAG_SANDBOX_ADDED =
		"<iframe src=\"test\" sandbox=\"\"></iframe>";

	private static final String _INITIAL_IFRAME_TAG =
		"<iframe src=\"test\"></iframe>";

	private static final String _INITIAL_IFRAME_TAG_SANDBOX =
		"<iframe src=\"test\" sandbox=\"\"></iframe>";

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private long _companyId;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject(
		filter = "component.name=com.liferay.portal.security.iframe.sanitizer.internal.IFrameSanitizerImpl"
	)
	private Sanitizer _iFrameSanitizer;

}