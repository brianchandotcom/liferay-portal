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

package com.liferay.asset.list.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.asset.list.util.AssetListStagingTestUtil;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class AssetListEntryStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();

		_liveGroup = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testAssetListCopiedWhenLocalStagingActivated()
		throws PortalException {

		AssetListTestUtil.addAssetListEntry(_liveGroup.getGroupId());

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		List<AssetListEntry> liveAssetLists =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_liveGroup.getGroupId());

		List<AssetListEntry> stagingAssetLists =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveAssetLists.toString(), 1, liveAssetLists.size());

		Assert.assertEquals(
			stagingAssetLists.toString(), 1, stagingAssetLists.size());
	}

	@Test
	public void testPublishCreateAssetList() throws PortalException {
		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListTestUtil.addAssetListEntry(_stagingGroup.getGroupId());

		List<AssetListEntry> liveAssetLists =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_liveGroup.getGroupId());

		List<AssetListEntry> stagingAssetLists =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveAssetLists.toString(), 0, liveAssetLists.size());

		Assert.assertEquals(
			stagingAssetLists.toString(), 1, stagingAssetLists.size());

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveAssetLists = AssetListEntryLocalServiceUtil.getAssetListEntries(
			_liveGroup.getGroupId());

		stagingAssetLists = AssetListEntryLocalServiceUtil.getAssetListEntries(
			_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveAssetLists.toString(), 1, liveAssetLists.size());

		Assert.assertEquals(
			stagingAssetLists.toString(), 1, stagingAssetLists.size());
	}

	@Test
	public void testPublishDeleteAssetList() throws PortalException {
		AssetListTestUtil.addAssetListEntry(_liveGroup.getGroupId());

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListEntry assetList =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_stagingGroup.getGroupId()).get(0);

		AssetListEntryLocalServiceUtil.deleteAssetListEntry(assetList);

		List<AssetListEntry> liveAssetLists =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_liveGroup.getGroupId());

		List<AssetListEntry> stagingAssetLists =
			AssetListEntryLocalServiceUtil.getAssetListEntries(
				_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveAssetLists.toString(), 1, liveAssetLists.size());

		Assert.assertEquals(
			stagingAssetLists.toString(), 0, stagingAssetLists.size());

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveAssetLists = AssetListEntryLocalServiceUtil.getAssetListEntries(
			_liveGroup.getGroupId());

		stagingAssetLists = AssetListEntryLocalServiceUtil.getAssetListEntries(
			_stagingGroup.getGroupId());

		Assert.assertEquals(
			liveAssetLists.toString(), 0, liveAssetLists.size());

		Assert.assertEquals(
			stagingAssetLists.toString(), 0, stagingAssetLists.size());
	}

	@Test
	public void testPublishUpdateAssetList() throws PortalException {
		AssetListEntry liveAsset = AssetListTestUtil.addAssetListEntry(
			_liveGroup.getGroupId(), "Test Title Original");

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListEntry stagingAsset =
			AssetListEntryLocalServiceUtil.fetchAssetListEntryByUuidAndGroupId(
				liveAsset.getUuid(), _stagingGroup.getGroupId());

		Assert.assertEquals(stagingAsset.getTitle(), liveAsset.getTitle());

		stagingAsset = AssetListEntryServiceUtil.updateAssetListEntry(
			stagingAsset.getAssetListEntryId(), "Test Title Edit");

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveAsset =
			AssetListEntryLocalServiceUtil.fetchAssetListEntryByUuidAndGroupId(
				stagingAsset.getUuid(), _liveGroup.getGroupId());

		Assert.assertEquals(stagingAsset.getTitle(), liveAsset.getTitle());
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
	}

	@DeleteAfterTestRun
	private Group _liveGroup;

	private PermissionChecker _originalPermissionChecker;
	private Group _stagingGroup;

}