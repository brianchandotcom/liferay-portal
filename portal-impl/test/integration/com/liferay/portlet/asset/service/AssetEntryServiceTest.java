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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class AssetEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetEntriesCountNoFilters() throws Exception {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		int initialAssetEntriesCount =
			AssetEntryLocalServiceUtil.getEntriesCount(assetEntryQuery);

		AssetTestUtil.addAssetEntry(_group.getGroupId());

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		int actualAssetEntriesCount =
			AssetEntryLocalServiceUtil.getEntriesCount(assetEntryQuery);

		Assert.assertEquals(
			initialAssetEntriesCount + 1, actualAssetEntriesCount);
	}

	@Test
	public void testGetEntriesNoFilters() throws Exception {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		List<AssetEntry> initialAssetEntries =
			AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

		AssetTestUtil.addAssetEntry(_group.getGroupId());

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(
			assetEntryQuery);

		Assert.assertEquals(
			assetEntries.toString(), initialAssetEntries.size() + 1,
			assetEntries.size());

		AssetEntry assetEntry = assetEntries.get(0);

		Assert.assertTrue(assetEntries.contains(assetEntry));
	}

	@Test
	public void testGetEntriesOrderByPublishDateAndRatings() throws Exception {
		List<AssetEntry> expectedAssetEntries = createAssetEntries();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});
		assetEntryQuery.setOrderByCol1("publishDate");
		assetEntryQuery.setOrderByCol2("ratings");
		assetEntryQuery.setOrderByType1("DESC");
		assetEntryQuery.setOrderByType2("DESC");

		List<AssetEntry> actualAssetEntries =
			AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

		validateAssetEntries(expectedAssetEntries, actualAssetEntries);
	}

	@Test
	public void testGetEntriesOrderByRatings() throws Exception {
		List<AssetEntry> expectedAssetEntries = createAssetEntries();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});
		assetEntryQuery.setOrderByCol1("ratings");
		assetEntryQuery.setOrderByType1("DESC");

		List<AssetEntry> actualAssetEntries =
			AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

		validateAssetEntries(expectedAssetEntries, actualAssetEntries);
	}

	protected List<AssetEntry> createAssetEntries() throws Exception {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.DAY_OF_MONTH, -1);

		Date yesterday = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, -2);

		Date dayBeforeYesterday = calendar.getTime();

		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), dayBeforeYesterday);

		RatingsTestUtil.addStats(
			assetEntry1.getClassName(), assetEntry1.getClassPK(), 2000);

		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), dayBeforeYesterday);

		RatingsTestUtil.addStats(
			assetEntry2.getClassName(), assetEntry2.getClassPK(), 1000);

		AssetEntry assetEntry3 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), yesterday);

		RatingsTestUtil.addStats(
			assetEntry3.getClassName(), assetEntry3.getClassPK(), 3000);

		List<AssetEntry> assetEntries = new ArrayList<>(3);

		assetEntries.add(assetEntry3);
		assetEntries.add(assetEntry1);
		assetEntries.add(assetEntry2);

		return assetEntries;
	}

	protected void validateAssetEntries(
		List<AssetEntry> expectedAssetEntries,
		List<AssetEntry> actualAssetEntries) {

		Assert.assertEquals(
			actualAssetEntries.toString(), expectedAssetEntries.size(),
			actualAssetEntries.size());

		for (int i = 0; i < expectedAssetEntries.size(); i++) {
			AssetEntry expectedAssetEntry = expectedAssetEntries.get(i);
			AssetEntry actualAssetEntry = actualAssetEntries.get(i);

			Assert.assertEquals(
				expectedAssetEntry.getEntryId(), actualAssetEntry.getEntryId());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}