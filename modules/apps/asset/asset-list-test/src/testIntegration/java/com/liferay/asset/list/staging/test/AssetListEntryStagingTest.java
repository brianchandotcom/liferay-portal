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
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.test.util.SegmentsTestUtil;

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

		AssetListEntry liveAssetListEntry = AssetListTestUtil.addAssetListEntry(
			_liveGroup.getGroupId());

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListEntry stagingAssetListEntry =
			_assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
				liveAssetListEntry.getUuid(), _stagingGroup.getGroupId());

		Assert.assertNotNull(stagingAssetListEntry);
	}

	@Test
	public void testPublishCreateAssetList() throws PortalException {
		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		List<AssetListEntry> originalLiveAssetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				_liveGroup.getGroupId());

		AssetListTestUtil.addAssetListEntry(_stagingGroup.getGroupId());

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		List<AssetListEntry> actualLiveAssetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				_liveGroup.getGroupId());

		Assert.assertEquals(
			actualLiveAssetListEntries.toString(),
			originalLiveAssetListEntries.size() + 1,
			actualLiveAssetListEntries.size());
	}

	@Test
	public void testPublishCreateAssetListWithSegments()
		throws PortalException {

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_stagingGroup.getGroupId());

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_liveGroup.getGroupId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_stagingGroup.getGroupId(), assetListEntry,
			segmentsEntry.getSegmentsEntryId());

		int assetListEntrySegmentsEntryRelsCount =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRelsCount(
					assetListEntry.getAssetListEntryId());

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		AssetListEntry liveAssetListEntry =
			_assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
				assetListEntry.getUuid(), _liveGroup.getGroupId());

		int liveAssetListEntrySegmentsEntryRelsCount =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRelsCount(
					liveAssetListEntry.getAssetListEntryId());

		Assert.assertEquals(
			assetListEntrySegmentsEntryRelsCount,
			liveAssetListEntrySegmentsEntryRelsCount);
	}

	@Test
	public void testPublishDeleteAssetList() throws PortalException {
		AssetListEntry liveAssetListEntry = AssetListTestUtil.addAssetListEntry(
			_liveGroup.getGroupId());

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		List<AssetListEntry> originalLiveAssetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				_liveGroup.getGroupId());

		AssetListEntry stagingAssetListEntry =
			_assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
				liveAssetListEntry.getUuid(), _stagingGroup.getGroupId());

		_assetListEntryLocalService.deleteAssetListEntry(stagingAssetListEntry);

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		List<AssetListEntry> actualLiveAssetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				_liveGroup.getGroupId());

		Assert.assertEquals(
			actualLiveAssetListEntries.toString(),
			originalLiveAssetListEntries.size() - 1,
			actualLiveAssetListEntries.size());
	}

	@Test
	public void testPublishDeleteAssetListWithSegments()
		throws PortalException {

		AssetListEntry liveAssetListEntry = AssetListTestUtil.addAssetListEntry(
			_liveGroup.getGroupId());

		SegmentsEntry liveSegmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_liveGroup.getGroupId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_liveGroup.getGroupId(), liveAssetListEntry,
			liveSegmentsEntry.getSegmentsEntryId());

		int originalLiveAssetListEntrySegmentsEntryRelsCount =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRelsCount(
					liveAssetListEntry.getAssetListEntryId());

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListEntry stagingAssetListEntry =
			_assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
				liveAssetListEntry.getUuid(), _stagingGroup.getGroupId());

		SegmentsEntry stagingSegmentsEntry =
			SegmentsEntryLocalServiceUtil.fetchSegmentsEntryByUuidAndGroupId(
				liveSegmentsEntry.getUuid(), _stagingGroup.getGroupId());

		_assetListEntrySegmentsEntryRelLocalService.
			deleteAssetListEntrySegmentsEntryRel(
				stagingAssetListEntry.getAssetListEntryId(),
				stagingSegmentsEntry.getSegmentsEntryId());

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		int liveAssetListEntrySegmentsEntryRelsCount =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRelsCount(
					liveAssetListEntry.getAssetListEntryId());

		Assert.assertEquals(
			originalLiveAssetListEntrySegmentsEntryRelsCount - 1,
			liveAssetListEntrySegmentsEntryRelsCount);
	}

	@Test
	public void testPublishUpdateAssetList() throws PortalException {
		AssetListEntry liveAsset = AssetListTestUtil.addAssetListEntry(
			_liveGroup.getGroupId(), "Test Title Original");

		_stagingGroup = AssetListStagingTestUtil.enableLocalStaging(
			_liveGroup, true);

		AssetListEntry stagingAsset =
			_assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
				liveAsset.getUuid(), _stagingGroup.getGroupId());

		Assert.assertEquals(stagingAsset.getTitle(), liveAsset.getTitle());

		stagingAsset = _assetListEntryLocalService.updateAssetListEntry(
			stagingAsset.getAssetListEntryId(), "Test Title Edit");

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		liveAsset =
			_assetListEntryLocalService.fetchAssetListEntryByUuidAndGroupId(
				stagingAsset.getUuid(), _liveGroup.getGroupId());

		Assert.assertEquals(stagingAsset.getTitle(), liveAsset.getTitle());
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private PermissionChecker _originalPermissionChecker;
	private Group _stagingGroup;

}