/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
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
			_stagingGroup.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_stagingGroup.getGroupId(), assetListEntry,
				segmentsEntry.getSegmentsEntryId());

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).put(
			"groupIds", String.valueOf(_liveGroup.getGroupId())
		).build();

		assetListEntrySegmentsEntryRel.setTypeSettings(
			unicodeProperties.toString());

		_assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRel(
				assetListEntrySegmentsEntryRel);

		AssetListStagingTestUtil.publishLayouts(_stagingGroup, _liveGroup);

		AssetListEntry liveAssetListEntry =
			_assetListEntryLocalService.getAssetListEntryByUuidAndGroupId(
				assetListEntry.getUuid(), _liveGroup.getGroupId());

		SegmentsEntry liveSegmentsEntry =
			_segmentsEntryLocalService.getSegmentsEntryByUuidAndGroupId(
				segmentsEntry.getUuid(), _liveGroup.getGroupId());

		AssetListEntrySegmentsEntryRel liveAssetListEntrySegmentsEntryRel =
			_assetListEntrySegmentsEntryRelLocalService.
				fetchAssetListEntrySegmentsEntryRel(
					liveAssetListEntry.getAssetListEntryId(),
					liveSegmentsEntry.getSegmentsEntryId());

		Assert.assertNotNull(liveAssetListEntrySegmentsEntryRel);

		UnicodeProperties liveUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				liveAssetListEntry.getTypeSettings(
					liveSegmentsEntry.getSegmentsEntryId())
			).build();

		long groupId = GetterUtil.getLong(
			liveUnicodeProperties.get("groupIds"));

		Assert.assertEquals(_liveGroup.getGroupId(), groupId);
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

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	private Group _stagingGroup;

}