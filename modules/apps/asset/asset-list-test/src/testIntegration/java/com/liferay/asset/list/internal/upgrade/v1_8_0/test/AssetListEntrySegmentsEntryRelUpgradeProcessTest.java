/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v1_8_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.test.util.AssetListTestUtil;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class AssetListEntrySegmentsEntryRelUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		_segmentsEntry = SegmentsTestUtil.addSegmentsEntry(_group.getGroupId());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _assetListEntrySegmentsEntryRelLocalService.
			addAssetListEntrySegmentsEntryRel(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_assetListEntry.getAssetListEntryId(),
				_segmentsEntry.getSegmentsEntryId(), StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Override
	protected CTService<?> getCTService() {
		return _assetListEntrySegmentsEntryRelLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			(AssetListEntrySegmentsEntryRel)ctModel;

		_assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		assetListEntrySegmentsEntryRel.setAssetListEntryId(
			_assetListEntry.getAssetListEntryId());

		return _assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRel(
				assetListEntrySegmentsEntryRel);
	}

	private static final String _CLASS_NAME =
		"com.liferay.asset.list.internal.upgrade.v1_8_0." +
			"AssetListEntrySegmentsEntryRelUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.asset.list.internal.upgrade.registry.AssetListServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private AssetListEntry _assetListEntry;

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

}