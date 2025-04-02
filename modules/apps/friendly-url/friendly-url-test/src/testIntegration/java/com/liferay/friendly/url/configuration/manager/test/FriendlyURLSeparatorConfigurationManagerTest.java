/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration;
import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
@Sync
public class FriendlyURLSeparatorConfigurationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_companyId = RandomTestUtil.randomLong();

		_safeCloseable = CompanyThreadLocal.setCompanyIdWithSafeCloseable(
			_companyId);
	}

	@After
	public void tearDown() throws Exception {
		_safeCloseable.close();
	}

	@Test
	public void testGetEmptyFriendlyURLSeparatorsJSON() throws Exception {
		JSONObject friendlyURLSeparatorsJSONObject =
			_friendlyURLSeparatorConfigurationManager.
				getFriendlyURLSeparatorsJSONObject(_companyId);

		Assert.assertNotNull(friendlyURLSeparatorsJSONObject);
		Assert.assertEquals(
			_jsonFactory.createJSONObject(
			).toString(),
			friendlyURLSeparatorsJSONObject.toString());
	}

	@Test
	public void testGetFriendlyURLSeparatorsJSON() throws Exception {
		JSONObject originalFriendlyURLSeparatorsJSONObject = JSONUtil.put(
			JournalArticle.class.getName(), "/test1/");

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						_companyId,
						FriendlyURLSeparatorCompanyConfiguration.class.
							getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"friendlyURLSeparatorsJSON",
							originalFriendlyURLSeparatorsJSONObject.toString()
						).build())) {

			JSONObject friendlyURLSeparatorsJSONObject =
				_friendlyURLSeparatorConfigurationManager.
					getFriendlyURLSeparatorsJSONObject(_companyId);

			Assert.assertNotNull(friendlyURLSeparatorsJSONObject);
			Assert.assertEquals(
				originalFriendlyURLSeparatorsJSONObject.toString(),
				friendlyURLSeparatorsJSONObject.toString());
		}
	}

	@Test
	public void testUpdateFriendlyURLSeparatorCompanyConfiguration()
		throws Exception {

		JSONObject friendlyURLSeparatorsJSONObject = JSONUtil.put(
			JournalArticle.class.getName(), "/test1/");

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						_companyId,
						FriendlyURLSeparatorCompanyConfiguration.class.
							getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"friendlyURLSeparatorsJSON",
							friendlyURLSeparatorsJSONObject.toString()
						).build())) {

			FriendlyURLSeparatorCompanyConfiguration
				friendlyURLSeparatorCompanyConfiguration =
					_configurationProvider.getCompanyConfiguration(
						FriendlyURLSeparatorCompanyConfiguration.class,
						_companyId);

			Assert.assertNotNull(friendlyURLSeparatorCompanyConfiguration);
			Assert.assertEquals(
				friendlyURLSeparatorsJSONObject.toString(),
				friendlyURLSeparatorCompanyConfiguration.
					friendlyURLSeparatorsJSON());
		}
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static SafeCloseable _safeCloseable;

	private long _companyId;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private FriendlyURLSeparatorConfigurationManager
		_friendlyURLSeparatorConfigurationManager;

	@Inject
	private JSONFactory _jsonFactory;

}