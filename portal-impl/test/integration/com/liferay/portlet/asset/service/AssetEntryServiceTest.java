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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Alberto Chaparro
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetEntryServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetEntriesCountNoFilters() throws Exception {
		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetTestUtil.addAssetEntry(_group.getGroupId());

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

			int count = AssetEntryLocalServiceUtil.getEntriesCount(
				assetEntryQuery);

			Assert.assertEquals(count, 1);
		}
		finally {
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);
		}
	}

	@Test
	public void testGetEntriesNoFilters() throws Exception {
		AssetEntry assetEntry = null;

		try {
			assetEntry = AssetTestUtil.addAssetEntry(_group.getGroupId());

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

			List<AssetEntry> assetEntries =
				AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

			Assert.assertEquals(assetEntries.size(), 1);

			Assert.assertEquals(assetEntries.get(0), assetEntry);
		}
		finally {
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);
		}
	}

	@Test
	public void testGetEntriesOrderByPublishDateAndRatings() throws Exception {
		AssetEntry assetEntry1 = null;
		AssetEntry assetEntry2 = null;
		AssetEntry assetEntry3 = null;

		RatingsStats ratingsStats1 = null;
		RatingsStats ratingsStats2 = null;
		RatingsStats ratingsStats3 = null;

		Date yesterday = DateUtil.newDate(
			DateUtil.newDate().getTime() - DAY_IN_MILISECONDS);

		Date dayBeforeYesterday = DateUtil.newDate(
			DateUtil.newDate().getTime() - (2 * DAY_IN_MILISECONDS));

		try {
			assetEntry1 = AssetTestUtil.addAssetEntry(
				_group.getGroupId(), dayBeforeYesterday);

			ratingsStats1 = RatingsTestUtil.addStats(
				assetEntry1.getClassName(), assetEntry1.getClassPK(), 2000);

			assetEntry2 = AssetTestUtil.addAssetEntry(
				_group.getGroupId(), dayBeforeYesterday);

			ratingsStats2 = RatingsTestUtil.addStats(
				assetEntry2.getClassName(), assetEntry2.getClassPK(), 1000);

			assetEntry3 = AssetTestUtil.addAssetEntry(
				_group.getGroupId(), yesterday);

			ratingsStats3 = RatingsTestUtil.addStats(
				assetEntry3.getClassName(), assetEntry3.getClassPK(), 3000);

			List<AssetEntry> orderedAssetEntries = new ArrayList<AssetEntry>(3);

			orderedAssetEntries.add(assetEntry3);
			orderedAssetEntries.add(assetEntry1);
			orderedAssetEntries.add(assetEntry2);

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

			assetEntryQuery.setOrderByCol1("publishDate");
			assetEntryQuery.setOrderByType1("DESC");

			assetEntryQuery.setOrderByCol2("ratings");
			assetEntryQuery.setOrderByType2("DESC");

			List<AssetEntry> assetEntries =
				AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

			Assert.assertEquals(assetEntries.size(), 3);

			for (int pos = 0; pos < 3; pos++) {
				Assert.assertEquals(
					assetEntries.get(pos), orderedAssetEntries.get(pos));
			}
		}
		finally {
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry1);
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry2);
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry3);

			RatingsStatsLocalServiceUtil.deleteRatingsStats(ratingsStats1);
			RatingsStatsLocalServiceUtil.deleteRatingsStats(ratingsStats2);
			RatingsStatsLocalServiceUtil.deleteRatingsStats(ratingsStats3);
		}
	}

	@Test
	public void testGetEntriesOrderByRatings() throws Exception {
		AssetEntry assetEntry1 = null;
		AssetEntry assetEntry2 = null;
		AssetEntry assetEntry3 = null;

		RatingsStats ratingsStats1 = null;
		RatingsStats ratingsStats2 = null;
		RatingsStats ratingsStats3 = null;

		try {
			assetEntry1 = AssetTestUtil.addAssetEntry(_group.getGroupId());

			ratingsStats1 = RatingsTestUtil.addStats(
				assetEntry1.getClassName(), assetEntry1.getClassPK(), 2000);

			assetEntry2 = AssetTestUtil.addAssetEntry(_group.getGroupId());

			ratingsStats2 = RatingsTestUtil.addStats(
				assetEntry2.getClassName(), assetEntry2.getClassPK(), 1000);

			assetEntry3 = AssetTestUtil.addAssetEntry(_group.getGroupId());

			ratingsStats3 = RatingsTestUtil.addStats(
				assetEntry3.getClassName(), assetEntry3.getClassPK(), 3000);

			List<AssetEntry> orderedAssetEntries = new ArrayList<AssetEntry>(3);

			orderedAssetEntries.add(assetEntry3);
			orderedAssetEntries.add(assetEntry1);
			orderedAssetEntries.add(assetEntry2);

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

			assetEntryQuery.setOrderByCol1("ratings");
			assetEntryQuery.setOrderByType1("DESC");

			List<AssetEntry> assetEntries =
				AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

			Assert.assertEquals(assetEntries.size(), 3);

			for (int pos = 0; pos < 3; pos++) {
				Assert.assertEquals(
					assetEntries.get(pos), orderedAssetEntries.get(pos));
			}
		}
		finally {
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry1);
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry2);
			AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry3);

			RatingsStatsLocalServiceUtil.deleteRatingsStats(ratingsStats1);
			RatingsStatsLocalServiceUtil.deleteRatingsStats(ratingsStats2);
			RatingsStatsLocalServiceUtil.deleteRatingsStats(ratingsStats3);
		}
	}

	private static final int DAY_IN_MILISECONDS = 86400000;

	@DeleteAfterTestRun
	private Group _group;

}