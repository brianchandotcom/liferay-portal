/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.analytics.settings.rest.internal.manager.test;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class AnalyticsSettingsManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_analyticsChannelId1 = RandomTestUtil.randomString(8);
		_analyticsChannelId2 = RandomTestUtil.randomString(8);
		_commerceChannelGroup1 = _addCommerceChannelGroup();
		_commerceChannelGroup2 = _addCommerceChannelGroup();
		_siteGroup1 = GroupTestUtil.addGroup();
		_siteGroup2 = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_analyticsSettingsManager.deleteCompanyConfiguration(
			TestPropsValues.getCompanyId());

		_groupLocalService.deleteGroup(_commerceChannelGroup1);
		_groupLocalService.deleteGroup(_commerceChannelGroup2);
		_groupLocalService.deleteGroup(_siteGroup1);
		_groupLocalService.deleteGroup(_siteGroup2);
	}

	@Test
	public void testGetCommerceChannelIds() throws Exception {
		Long[] emptyCommerceChannelIds =
			_analyticsSettingsManager.getCommerceChannelIds(
				_analyticsChannelId1, TestPropsValues.getCompanyId());

		Assert.assertEquals(
			Arrays.toString(emptyCommerceChannelIds), 0,
			emptyCommerceChannelIds.length);

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId(),
					new Long[] {_commerceChannelGroup1.getClassPK()})
			).build());

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelId2, TestPropsValues.getCompanyId(),
					new Long[] {_commerceChannelGroup2.getClassPK()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] commerceChannelIds =
					_analyticsSettingsManager.getCommerceChannelIds(
						_analyticsChannelId1, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(commerceChannelIds), 1,
					commerceChannelIds.length);

				return null;
			});

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId(),
					new Long[] {
						_commerceChannelGroup1.getClassPK(),
						_commerceChannelGroup2.getClassPK()
					})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] commerceChannelIds =
					_analyticsSettingsManager.getCommerceChannelIds(
						_analyticsChannelId1, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(commerceChannelIds), 2,
					commerceChannelIds.length);

				return null;
			});

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId(),
					new Long[] {_commerceChannelGroup1.getClassPK()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] commerceChannelIds =
					_analyticsSettingsManager.getCommerceChannelIds(
						_analyticsChannelId1, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(commerceChannelIds), 1,
					commerceChannelIds.length);
				Assert.assertEquals(
					_commerceChannelGroup1.getClassPK(),
					(long)commerceChannelIds[0]);

				return null;
			});
	}

	@Test
	public void testGetSiteIds() throws Exception {
		Long[] emptySiteIds = _analyticsSettingsManager.getSiteIds(
			_analyticsChannelId1, TestPropsValues.getCompanyId());

		Assert.assertEquals(
			Arrays.toString(emptySiteIds), 0, emptySiteIds.length);

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId(),
					new Long[] {_siteGroup1.getGroupId()})
			).build());

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelId2, TestPropsValues.getCompanyId(),
					new Long[] {_siteGroup2.getGroupId()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] siteIds = _analyticsSettingsManager.getSiteIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(siteIds), 1, siteIds.length);

				return null;
			});

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId(),
					new Long[] {
						_siteGroup1.getGroupId(), _siteGroup2.getGroupId()
					})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] siteIds = _analyticsSettingsManager.getSiteIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(siteIds), 2, siteIds.length);

				return null;
			});

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId(),
					new Long[] {_siteGroup1.getGroupId()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] siteIds = _analyticsSettingsManager.getSiteIds(
					_analyticsChannelId1, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(siteIds), 1, siteIds.length);
				Assert.assertEquals(_siteGroup1.getGroupId(), (long)siteIds[0]);

				return null;
			});
	}

	@Test
	public void testIsSiteIdSynced() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId",
							RandomTestUtil.nextLong()
						).put(
							"liferayAnalyticsEnableAllGroupIds", true
						).put(
							"liferayAnalyticsFaroBackendSecuritySignature",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsFaroBackendURL",
							RandomTestUtil.randomString()
						).build(),
						SettingsFactoryUtil.getSettingsFactory())) {

			Assert.assertTrue(
				_analyticsSettingsManager.isSiteIdSynced(
					TestPropsValues.getCompanyId(), _siteGroup1.getGroupId()));
		}
	}

	@Test
	public void testIsSiteIdSyncedWithNullLiferayAnalyticsDataSourceId()
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary();

		dictionary.put("liferayAnalyticsDataSourceId", null);

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(), dictionary,
						SettingsFactoryUtil.getSettingsFactory())) {

			Assert.assertFalse(
				_analyticsSettingsManager.isSiteIdSynced(
					TestPropsValues.getCompanyId(), _siteGroup1.getGroupId()));
		}
	}

	@Test
	public void testIsSiteIdSyncedWithSyncedGroupId() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId",
							RandomTestUtil.nextLong()
						).put(
							"liferayAnalyticsFaroBackendSecuritySignature",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsFaroBackendURL",
							RandomTestUtil.randomString()
						).put(
							"syncedGroupIds",
							new String[] {
								String.valueOf(_siteGroup1.getGroupId())
							}
						).build(),
						SettingsFactoryUtil.getSettingsFactory())) {

			Assert.assertTrue(
				_analyticsSettingsManager.isSiteIdSynced(
					TestPropsValues.getCompanyId(), _siteGroup1.getGroupId()));
		}
	}

	@Test
	public void testIsSiteIdSyncedWithUnsyncedGroupId() throws Exception {
		_analyticsSettingsManager.isSiteIdSynced(
			TestPropsValues.getCompanyId(), _siteGroup1.getGroupId());
	}

	@Test
	public void testUpdateCompanyConfiguration() throws Exception {
		AnalyticsConfiguration analyticsConfiguration1 =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				TestPropsValues.getCompanyId());

		Assert.assertEquals(StringPool.BLANK, analyticsConfiguration1.token());

		String token = RandomTestUtil.randomString();

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"token", token
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				AnalyticsConfiguration analyticsConfiguration2 =
					_analyticsSettingsManager.getAnalyticsConfiguration(
						TestPropsValues.getCompanyId());

				Assert.assertEquals(token, analyticsConfiguration2.token());

				return null;
			});
	}

	private Group _addCommerceChannelGroup() throws Exception {
		return _groupLocalService.addGroup(
			TestPropsValues.getUserId(), 0,
			"com.liferay.commerce.product.model.CommerceChannel",
			RandomTestUtil.randomLong(), 0,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			GroupConstants.TYPE_SITE_OPEN, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			"/" + RandomTestUtil.randomString(6), false, false, true,
			ServiceContextTestUtil.getServiceContext());
	}

	private String _analyticsChannelId1;
	private String _analyticsChannelId2;

	@Inject
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private Group _commerceChannelGroup1;
	private Group _commerceChannelGroup2;

	@Inject
	private GroupLocalService _groupLocalService;

	private Group _siteGroup1;
	private Group _siteGroup2;

}