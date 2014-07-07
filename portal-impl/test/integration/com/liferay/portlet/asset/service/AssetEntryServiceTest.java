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
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		AssetTestUtil.addAssetEntry(_group.getGroupId());

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		int count = AssetEntryLocalServiceUtil.getEntriesCount(assetEntryQuery);

		Assert.assertEquals(1, count);
	}

	@Test
	public void testGetEntriesNoFilters() throws Exception {
		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		List<AssetEntry> entries = AssetEntryLocalServiceUtil.getEntries(
			assetEntryQuery);

		Assert.assertEquals(1, entries.size());

		AssetEntry assetEntry1 = entries.get(0);

		Assert.assertEquals(assetEntry.getEntryId(), assetEntry1.getEntryId());
	}

	@Test
	public void testGetEntriesOrderByPublishDateAndRatings() throws Exception {
		List<AssetEntry> assetEntries = createAssetEntries();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setOrderByCol1("publishDate");
		assetEntryQuery.setOrderByType1("DESC");

		assetEntryQuery.setOrderByCol2("ratings");
		assetEntryQuery.setOrderByType2("DESC");

		List<AssetEntry> entries = AssetEntryLocalServiceUtil.getEntries(
			assetEntryQuery);

		validateAssetEntries(assetEntries, entries);
	}

	@Test
	public void testGetEntriesOrderByRatings() throws Exception {
		List<AssetEntry> assetEntries = createAssetEntries();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setOrderByCol1("ratings");
		assetEntryQuery.setOrderByType1("DESC");

		List<AssetEntry> entries = AssetEntryLocalServiceUtil.getEntries(
			assetEntryQuery);

		validateAssetEntries(assetEntries, entries);
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

		List<AssetEntry> entries = new ArrayList<AssetEntry>(3);

		entries.add(assetEntry3);
		entries.add(assetEntry1);
		entries.add(assetEntry2);

		return entries;
	}

	protected void validateAssetEntries(
		List<AssetEntry> expectedEntries, List<AssetEntry> actualEntries) {

		Assert.assertEquals(expectedEntries.size(), actualEntries.size());

		for (int pos = 0; pos < expectedEntries.size(); pos++) {
			AssetEntry expectedEntry = expectedEntries.get(pos);
			AssetEntry actualEntry = actualEntries.get(pos);

			Assert.assertEquals(
				expectedEntry.getEntryId(), actualEntry.getEntryId());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}