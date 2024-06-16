/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v2_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.test.util.AssetListTestUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class AssetListEntryAssetEntryRelUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testUpgrade() throws Exception {
		_addAssetListEntryAssetEntryRel(0);

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels();
	}

	@Test
	public void testUpgradeExistingAssetEntry() throws Exception {
		JournalArticle journalArticle = _addJournalArticle();

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(journalArticle);
	}

	@Test
	public void testUpgradePosition() throws Exception {
		JournalArticle journalArticle1 = _addJournalArticle();
		JournalArticle journalArticle2 = _addJournalArticle();

		_addAssetListEntryAssetEntryRel(2);

		JournalArticle journalArticle3 = _addJournalArticle();
		JournalArticle journalArticle4 = _addJournalArticle();

		_assertAssetListEntryAssetEntryRelsSize(5);

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(
			journalArticle1, journalArticle2, journalArticle3, journalArticle4);
	}

	@Test
	public void testUpgradePositionWithMultipleOrphanAssetEntryIdAndSegmentsEntryVariation()
		throws Exception {

		_addAssetListEntryAssetEntryRel(0);

		JournalArticle journalArticle1 = _addJournalArticle();
		JournalArticle journalArticle2 = _addJournalArticle();
		JournalArticle journalArticle3 = _addJournalArticle();
		JournalArticle journalArticle4 = _addJournalArticle();

		_addAssetListEntryAssetEntryRel(5);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), _assetListEntry,
			segmentsEntry.getSegmentsEntryId());

		_addAssetListEntryAssetEntryRel(
			segmentsEntry.getSegmentsEntryId(), journalArticle4);
		_addAssetListEntryAssetEntryRel(1, segmentsEntry.getSegmentsEntryId());
		_addAssetListEntryAssetEntryRel(
			segmentsEntry.getSegmentsEntryId(), journalArticle3);
		_addAssetListEntryAssetEntryRel(3, segmentsEntry.getSegmentsEntryId());
		_addAssetListEntryAssetEntryRel(
			segmentsEntry.getSegmentsEntryId(), journalArticle2);
		_addAssetListEntryAssetEntryRel(
			segmentsEntry.getSegmentsEntryId(), journalArticle1);
		_addAssetListEntryAssetEntryRel(6, segmentsEntry.getSegmentsEntryId());

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(
			journalArticle1, journalArticle2, journalArticle3, journalArticle4);
		_assertAssetListEntryAssetEntryRels(
			segmentsEntry.getSegmentsEntryId(), journalArticle4,
			journalArticle3, journalArticle2, journalArticle1);
	}

	@Test
	public void testUpgradePositionWithMultipleOrphanAssetEntryIds()
		throws Exception {

		JournalArticle journalArticle1 = _addJournalArticle();
		JournalArticle journalArticle2 = _addJournalArticle();

		_addAssetListEntryAssetEntryRel(2);

		JournalArticle journalArticle3 = _addJournalArticle();
		JournalArticle journalArticle4 = _addJournalArticle();

		_addAssetListEntryAssetEntryRel(5);

		JournalArticle journalArticle5 = _addJournalArticle();
		JournalArticle journalArticle6 = _addJournalArticle();

		_assertAssetListEntryAssetEntryRelsSize(8);

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(
			journalArticle1, journalArticle2, journalArticle3, journalArticle4,
			journalArticle5, journalArticle6);
	}

	@Test
	public void testUpgradeWithOrphanAssetEntryIdAtFirstPosition()
		throws Exception {

		_addAssetListEntryAssetEntryRel(0);

		JournalArticle journalArticle1 = _addJournalArticle();
		JournalArticle journalArticle2 = _addJournalArticle();

		_assertAssetListEntryAssetEntryRelsSize(3);

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(journalArticle1, journalArticle2);
	}

	@Test
	public void testUpgradeWithOrphanAssetEntryIdAtLastPosition()
		throws Exception {

		JournalArticle journalArticle1 = _addJournalArticle();
		JournalArticle journalArticle2 = _addJournalArticle();

		_addAssetListEntryAssetEntryRel(2);

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(journalArticle1, journalArticle2);
	}

	private void _addAssetListEntryAssetEntryRel(int expected)
		throws Exception {

		_addAssetListEntryAssetEntryRel(expected, 0L);
	}

	private void _addAssetListEntryAssetEntryRel(
			int expected, long segmentsEntryId)
		throws Exception {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			_assetListEntryAssetEntryRelLocalService.
				addAssetListEntryAssetEntryRel(
					_assetListEntry.getAssetListEntryId(),
					RandomTestUtil.randomLong(), segmentsEntryId,
					_serviceContext);

		Assert.assertEquals(
			expected, assetListEntryAssetEntryRel.getPosition());

		_assertAssetListEntryAssetEntryRelsSize(expected + 1, segmentsEntryId);
	}

	private void _addAssetListEntryAssetEntryRel(
			long segmentsEntryId, JournalArticle journalArticle)
		throws Exception {

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		_assetListEntryAssetEntryRelLocalService.addAssetListEntryAssetEntryRel(
			_assetListEntry.getAssetListEntryId(), assetEntry.getEntryId(),
			segmentsEntryId, _serviceContext);
	}

	private JournalArticle _addJournalArticle() throws Exception {
		return _addJournalArticle(0L);
	}

	private JournalArticle _addJournalArticle(long segmentsEntryId)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_addAssetListEntryAssetEntryRel(segmentsEntryId, journalArticle);

		return journalArticle;
	}

	private void _assertAssetListEntryAssetEntryRels(
			JournalArticle... journalArticles)
		throws Exception {

		_assertAssetListEntryAssetEntryRels(0L, journalArticles);
	}

	private void _assertAssetListEntryAssetEntryRels(
			long segmentsEntryId, JournalArticle... journalArticles)
		throws Exception {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), segmentsEntryId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryAssetEntryRels.toString(), journalArticles.length,
			assetListEntryAssetEntryRels.size());

		for (int i = 0; i < assetListEntryAssetEntryRels.size(); i++) {
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
				assetListEntryAssetEntryRels.get(i);

			Assert.assertEquals(i, assetListEntryAssetEntryRel.getPosition());

			AssetEntry assetEntry = _assetEntryLocalService.getAssetEntry(
				assetListEntryAssetEntryRel.getAssetEntryId());

			Assert.assertNotNull(assetEntry);
			Assert.assertEquals(
				JournalArticle.class.getName(), assetEntry.getClassName());

			JournalArticle journalArticle = journalArticles[i];

			Assert.assertEquals(
				journalArticle.getResourcePrimKey(), assetEntry.getClassPK());
		}
	}

	private void _assertAssetListEntryAssetEntryRelsSize(int expected) {
		_assertAssetListEntryAssetEntryRelsSize(expected, 0L);
	}

	private void _assertAssetListEntryAssetEntryRelsSize(
		int expected, long segmentsEntryId) {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), segmentsEntryId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryAssetEntryRels.toString(), expected,
			assetListEntryAssetEntryRels.size());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_entityCache.clearCache();
			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.asset.list.internal.upgrade.v2_1_1." +
			"AssetListEntryAssetEntryRelUpgradeProcess";

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private AssetListEntry _assetListEntry;

	@Inject
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	private ServiceContext _serviceContext;

	@Inject(
		filter = "(&(component.name=com.liferay.asset.list.internal.upgrade.registry.AssetListServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}