/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration;
import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
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

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mikel Lorza
 */
@FeatureFlags("LPD-11147")
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
		_originalCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(_companyId);
	}

	@After
	public void tearDown() throws Exception {
		CompanyThreadLocal.setCompanyId(_originalCompanyId);
	}

	@Test
	public void testGetEmptyFriendlyURLSeparatorsJSON() throws Exception {
		String friendlyURLSeparatorsJSON =
			_friendlyURLSeparatorConfigurationManager.
				getFriendlyURLSeparatorsJSON(_companyId);

		Assert.assertNotNull(friendlyURLSeparatorsJSON);
		Assert.assertEquals(
			_jsonFactory.createJSONObject(
			).toString(),
			friendlyURLSeparatorsJSON);
	}

	@Test
	public void testGetFriendlyURLSeparatorsJSON() throws Exception {
		JSONObject friendlyURLSeparatorsJSONObject = JSONUtil.put(
			JournalArticle.class.getName(), "/test1/");

		ConfigurationTestUtil.updateConfiguration(
			FriendlyURLSeparatorCompanyConfiguration.class.getName(),
			() -> {
				_configurationProvider.saveCompanyConfiguration(
					FriendlyURLSeparatorCompanyConfiguration.class, _companyId,
					HashMapDictionaryBuilder.<String, Object>put(
						"friendlyURLSeparatorsJSON",
						friendlyURLSeparatorsJSONObject.toString()
					).build());

				Configuration configuration =
					_configurationAdmin.getConfiguration(
						FriendlyURLSeparatorCompanyConfiguration.class.
							getName(),
						StringPool.QUESTION);

				configuration.update();
			});

		String friendlyURLSeparatorsJSON =
			_friendlyURLSeparatorConfigurationManager.
				getFriendlyURLSeparatorsJSON(_companyId);

		Assert.assertNotNull(friendlyURLSeparatorsJSON);
		Assert.assertEquals(
			friendlyURLSeparatorsJSONObject.toString(),
			friendlyURLSeparatorsJSON);
	}

	@Test
	public void testUpdateFriendlyURLSeparatorCompanyConfiguration()
		throws Exception {

		JSONObject friendlyURLSeparatorsJSONObject = JSONUtil.put(
			JournalArticle.class.getName(), "/test1/");

		ConfigurationTestUtil.updateConfiguration(
			FriendlyURLSeparatorCompanyConfiguration.class.getName(),
			() -> {
				_friendlyURLSeparatorConfigurationManager.
					updateFriendlyURLSeparatorCompanyConfiguration(
						_companyId, friendlyURLSeparatorsJSONObject.toString());

				Configuration configuration =
					_configurationAdmin.getConfiguration(
						FriendlyURLSeparatorCompanyConfiguration.class.
							getName(),
						StringPool.QUESTION);

				configuration.update();
			});

		FriendlyURLSeparatorCompanyConfiguration
			friendlyURLSeparatorCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FriendlyURLSeparatorCompanyConfiguration.class, _companyId);

		Assert.assertNotNull(friendlyURLSeparatorCompanyConfiguration);
		Assert.assertEquals(
			friendlyURLSeparatorsJSONObject.toString(),
			friendlyURLSeparatorCompanyConfiguration.
				friendlyURLSeparatorsJSON());
	}

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private long _companyId;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private FriendlyURLSeparatorConfigurationManager
		_friendlyURLSeparatorConfigurationManager;

	@Inject
	private JSONFactory _jsonFactory;

	private long _originalCompanyId;

}