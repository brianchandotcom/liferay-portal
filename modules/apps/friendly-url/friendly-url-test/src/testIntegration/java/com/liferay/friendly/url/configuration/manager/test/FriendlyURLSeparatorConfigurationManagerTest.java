/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.configuration.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration;
import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@FeatureFlags("LPS-203351")
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
		_companyId = RandomTestUtil.randomInt();
	}

	@Test
	public void testGetEmptyFriendlyURLSeparators() throws Exception {
		String friendlyURLSeparators =
			_friendlyURLSeparatorConfigurationManager.getFriendlyURLSeparators(
				_companyId);

		Assert.assertNotNull(friendlyURLSeparators);

		Assert.assertEquals(
			_jsonFactory.createJSONObject(
			).toString(),
			friendlyURLSeparators);
	}

	@Test
	public void testGetFriendlyURLSeparators() throws Exception {
		JSONObject friendlyURLSeparatorsJSONObject = JSONUtil.put(
			JournalArticle.class.getName(), "/test1/");

		_configurationProvider.saveCompanyConfiguration(
			FriendlyURLSeparatorCompanyConfiguration.class, _companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"friendlyURLSeparators",
				friendlyURLSeparatorsJSONObject.toString()
			).build());

		String friendlyURLSeparators =
			_friendlyURLSeparatorConfigurationManager.getFriendlyURLSeparators(
				_companyId);

		Assert.assertNotNull(friendlyURLSeparators);

		Assert.assertEquals(
			friendlyURLSeparatorsJSONObject.toString(), friendlyURLSeparators);
	}

	@Test
	public void testUpdateFriendlyURLSeparatorCompanyConfiguration()
		throws Exception {

		JSONObject friendlyURLSeparatorsJSONObject = JSONUtil.put(
			JournalArticle.class.getName(), "/test1/");

		_friendlyURLSeparatorConfigurationManager.
			updateFriendlyURLSeparatorCompanyConfiguration(
				_companyId, friendlyURLSeparatorsJSONObject.toString());

		FriendlyURLSeparatorCompanyConfiguration
			friendlyURLSeparatorCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FriendlyURLSeparatorCompanyConfiguration.class, _companyId);

		Assert.assertNotNull(friendlyURLSeparatorCompanyConfiguration);

		Assert.assertEquals(
			friendlyURLSeparatorsJSONObject.toString(),
			friendlyURLSeparatorCompanyConfiguration.friendlyURLSeparators());
	}

	private int _companyId;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private FriendlyURLSeparatorConfigurationManager
		_friendlyURLSeparatorConfigurationManager;

	@Inject
	private JSONFactory _jsonFactory;

}