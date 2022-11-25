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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
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
		_analyticsChannelIdA = RandomTestUtil.randomString(8);

		_analyticsChannelIdB = RandomTestUtil.randomString(8);

		_siteAGroup = _addSiteGroup();

		_siteBGroup = _addSiteGroup();

		_commerceChannelAGroup = _addCommerceChannelGroup();

		_commerceChannelBGroup = _addCommerceChannelGroup();
	}

	@After
	public void tearDown() throws Exception {
		_analyticsSettingsManager.deleteCompanyConfiguration(
			TestPropsValues.getCompanyId());

		_groupLocalService.deleteGroup(_siteAGroup);

		_groupLocalService.deleteGroup(_siteBGroup);

		_groupLocalService.deleteGroup(_commerceChannelAGroup);

		_groupLocalService.deleteGroup(_commerceChannelBGroup);
	}

	@Test
	public void testGetCommerceChannelIds() throws Exception {
		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId(),
					new Long[] {_commerceChannelAGroup.getClassPK()})
			).build());

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelIdB, TestPropsValues.getCompanyId(),
					new Long[] {_commerceChannelBGroup.getClassPK()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] commerceChannelIds =
					_analyticsSettingsManager.getCommerceChannelIds(
						_analyticsChannelIdA, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(commerceChannelIds), 1,
					commerceChannelIds.length);

				return null;
			});
	}

	@Test
	public void testGetCommerceChannelIdsEmpty() throws Exception {
		Long[] commerceChannelIds =
			_analyticsSettingsManager.getCommerceChannelIds(
				_analyticsChannelIdA, TestPropsValues.getCompanyId());

		Assert.assertEquals(
			Arrays.toString(commerceChannelIds), 0, commerceChannelIds.length);
	}

	@Test
	public void testGetCommerceChannelIdsRemoval() throws Exception {
		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedCommerceChannelIds",
				_analyticsSettingsManager.updateCommerceChannelIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId(),
					new Long[] {
						_commerceChannelAGroup.getClassPK(),
						_commerceChannelBGroup.getClassPK()
					})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] commerceChannelIds =
					_analyticsSettingsManager.getCommerceChannelIds(
						_analyticsChannelIdA, TestPropsValues.getCompanyId());

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
					_analyticsChannelIdA, TestPropsValues.getCompanyId(),
					new Long[] {_commerceChannelAGroup.getClassPK()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] commerceChannelIds =
					_analyticsSettingsManager.getCommerceChannelIds(
						_analyticsChannelIdA, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(commerceChannelIds), 1,
					commerceChannelIds.length);

				Assert.assertEquals(
					_commerceChannelAGroup.getClassPK(),
					(long)commerceChannelIds[0]);

				return null;
			});
	}

	@Test
	public void testGetSiteIds() throws Exception {
		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId(),
					new Long[] {_siteAGroup.getGroupId()})
			).build());

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelIdB, TestPropsValues.getCompanyId(),
					new Long[] {_siteBGroup.getGroupId()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] siteIds = _analyticsSettingsManager.getSiteIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(siteIds), 1, siteIds.length);

				return null;
			});
	}

	@Test
	public void testGetSiteIdsEmpty() throws Exception {
		Long[] siteIds = _analyticsSettingsManager.getSiteIds(
			_analyticsChannelIdA, TestPropsValues.getCompanyId());

		Assert.assertEquals(Arrays.toString(siteIds), 0, siteIds.length);
	}

	@Test
	public void testGetSiteIdsRemoval() throws Exception {
		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId(),
					new Long[] {
						_siteAGroup.getGroupId(), _siteBGroup.getGroupId()
					})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] siteIds = _analyticsSettingsManager.getSiteIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(siteIds), 2, siteIds.length);

				return null;
			});

		_analyticsSettingsManager.updateCompanyConfiguration(
			TestPropsValues.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncedGroupIds",
				_analyticsSettingsManager.updateSiteIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId(),
					new Long[] {_siteAGroup.getGroupId()})
			).build());

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				Long[] siteIds = _analyticsSettingsManager.getSiteIds(
					_analyticsChannelIdA, TestPropsValues.getCompanyId());

				Assert.assertEquals(
					Arrays.toString(siteIds), 1, siteIds.length);

				Assert.assertEquals(_siteAGroup.getGroupId(), (long)siteIds[0]);

				return null;
			});
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

	private Group _addSiteGroup() throws Exception {
		return GroupTestUtil.addGroup();
	}

	private String _analyticsChannelIdA;
	private String _analyticsChannelIdB;

	@Inject
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private Group _commerceChannelAGroup;
	private Group _commerceChannelBGroup;

	@Inject
	private GroupLocalService _groupLocalService;

	private Group _siteAGroup;
	private Group _siteBGroup;

}