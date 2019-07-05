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

package com.liferay.asset.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.exception.AssetListEntryTitleException;
import com.liferay.asset.list.exception.DuplicateAssetListEntryTitleException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.list.util.comparator.AssetListEntryCreateDateComparator;
import com.liferay.asset.list.util.comparator.AssetListEntryTitleComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetListEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddAssetListEntry() throws PortalException {
		AssetListEntry assetListEntry = _addAssetListEntry("Asset List Title");

		Assert.assertNotNull(
			_assetListEntryService.fetchAssetListEntry(
				assetListEntry.getAssetListEntryId()));

		Assert.assertEquals("Asset List Title", assetListEntry.getTitle());
	}

	@Test(expected = AssetListEntryTitleException.class)
	public void testAddAssetListEntryWithEmptyTitle() throws PortalException {
		_addAssetListEntry(StringPool.BLANK);
	}

	@Test(expected = AssetListEntryTitleException.class)
	public void testAddAssetListEntryWithInvalidLength()
		throws PortalException {

		_addAssetListEntry(RandomTestUtil.randomString(76));
	}

	@Test(expected = AssetListEntryTitleException.class)
	public void testAddAssetListEntryWithNullTitle() throws PortalException {
		_addAssetListEntry(null);
	}

	@Test(expected = DuplicateAssetListEntryTitleException.class)
	public void testAddDuplicateAssetListEntry() throws PortalException {
		_addAssetListEntry("Asset List Title");

		_addAssetListEntry("Asset List Title");
	}

	@Test
	public void testAssetListEntryKey() throws PortalException {
		AssetListEntry assetListEntry = _addAssetListEntry("Asset List Title");

		Assert.assertEquals(
			"asset-list-title", assetListEntry.getAssetListEntryKey());
	}

	@Test
	public void testDeleteAssetListEntries() throws PortalException {
		AssetListEntry assetListEntry1 = _addAssetListEntry(
			"Asset List Title 1");
		AssetListEntry assetListEntry2 = _addAssetListEntry(
			"Asset List Title 2");

		long[] assetListEntries = {
			assetListEntry1.getAssetListEntryId(),
			assetListEntry2.getAssetListEntryId()
		};

		_assetListEntryService.deleteAssetListEntries(assetListEntries);

		Assert.assertNull(
			_assetListEntryService.fetchAssetListEntry(
				assetListEntry1.getAssetListEntryId()));

		Assert.assertNull(
			_assetListEntryService.fetchAssetListEntry(
				assetListEntry2.getAssetListEntryId()));
	}

	@Test
	public void testDeleteAssetListEntry() throws PortalException {
		AssetListEntry assetListEntry = _addAssetListEntry("Asset List Title");

		_assetListEntryService.deleteAssetListEntry(
			assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			_assetListEntryService.fetchAssetListEntry(
				assetListEntry.getAssetListEntryId()));
	}

	@Test
	public void testGetAssetListEntriesByGroup() throws PortalException {
		List<AssetListEntry> originalAssetListEntries =
			_assetListEntryService.getAssetListEntries(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		AssetListEntry assetListEntry1 = _addAssetListEntry(
			"Asset List Title 1");

		AssetListEntry assetListEntry2 = _addAssetListEntry(
			"Asset List Title 2");

		List<AssetListEntry> actualAssetListEntries =
			_assetListEntryService.getAssetListEntries(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertEquals(
			actualAssetListEntries.toString(), actualAssetListEntries.size(),
			originalAssetListEntries.size() + 2);

		Assert.assertTrue(actualAssetListEntries.contains(assetListEntry1));

		Assert.assertTrue(actualAssetListEntries.contains(assetListEntry2));
	}

	@Test
	public void testGetAssetListEntriesByOrderByDateComparator()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetListEntry assetListEntry =
			_assetListEntryService.addAssetListEntry(
				_group.getGroupId(), "Test Name", 0, serviceContext);

		_assetListEntryService.addAssetListEntry(
			_group.getGroupId(), "A Test Name", 0, serviceContext);

		_assetListEntryService.addAssetListEntry(
			_group.getGroupId(), "B Test name", 0, serviceContext);

		OrderByComparator orderByComparator =
			new AssetListEntryCreateDateComparator(true);

		List<AssetListEntry> assetListEntries =
			_assetListEntryService.getAssetListEntries(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				orderByComparator);

		AssetListEntry firstAssetListEntry = assetListEntries.get(0);

		Assert.assertEquals(assetListEntry, firstAssetListEntry);

		orderByComparator = new AssetListEntryCreateDateComparator(false);

		assetListEntries = _assetListEntryService.getAssetListEntries(
			_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);

		AssetListEntry lastAssetListEntry = assetListEntries.get(
			assetListEntries.size() - 1);

		Assert.assertEquals(lastAssetListEntry, assetListEntry);
	}

	@Test
	public void testGetAssetListEntriesByOrderByTitleComparator()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetListEntry assetListEntry =
			_assetListEntryService.addAssetListEntry(
				_group.getGroupId(), "AA Asset List Entry", 0, serviceContext);

		_assetListEntryService.addAssetListEntry(
			_group.getGroupId(), "AB Asset List Entry", 0, serviceContext);

		_assetListEntryService.addAssetListEntry(
			_group.getGroupId(), "AC Asset List Entry", 0, serviceContext);

		OrderByComparator orderByComparator = new AssetListEntryTitleComparator(
			true);

		List<AssetListEntry> assetListEntries =
			_assetListEntryService.getAssetListEntries(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				orderByComparator);

		AssetListEntry firstAssetListEntry = assetListEntries.get(0);

		Assert.assertEquals(assetListEntry, firstAssetListEntry);

		orderByComparator = new AssetListEntryTitleComparator(false);

		assetListEntries = _assetListEntryService.getAssetListEntries(
			_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);

		AssetListEntry lastAssetListEntry = assetListEntries.get(
			assetListEntries.size() - 1);

		Assert.assertEquals(lastAssetListEntry, assetListEntry);
	}

	@Test
	public void testGetAssetListEntriesCountByGroup() throws PortalException {
		int originalAssetListEntriesCount =
			_assetListEntryService.getAssetListEntriesCount(
				_group.getGroupId());

		_addAssetListEntry("Asset List Title 1");

		int actualAssetListEntriesCount =
			_assetListEntryService.getAssetListEntriesCount(
				_group.getGroupId());

		Assert.assertEquals(
			actualAssetListEntriesCount, originalAssetListEntriesCount + 1);
	}

	@Test
	public void testUpdateAssetListEntry() throws PortalException {
		AssetListEntry assetListEntry = _addAssetListEntry("Asset List Title");

		assetListEntry = _assetListEntryService.updateAssetListEntry(
			assetListEntry.getAssetListEntryId(), "New Asset List Title");

		Assert.assertEquals("New Asset List Title", assetListEntry.getTitle());
	}

	private AssetListEntry _addAssetListEntry(String title)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _assetListEntryService.addAssetListEntry(
			_group.getGroupId(), title, 0, serviceContext);
	}

	@Inject
	private AssetListEntryService _assetListEntryService;

	@DeleteAfterTestRun
	private Group _group;

}