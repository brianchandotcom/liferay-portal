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
		_assetListEntryAssetEntryRelLocalService.addAssetListEntryAssetEntryRel(
			_assetListEntry.getAssetListEntryId(), RandomTestUtil.randomLong(),
			0L, _serviceContext);

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryAssetEntryRels.toString(), 1,
			assetListEntryAssetEntryRels.size());

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels();
	}

	@Test
	public void testUpgradeExistingAssetEntry() throws Exception {
		JournalArticle journalArticle = _addJournalArticle();

		_runUpgrade();

		_assertAssetListEntryAssetEntryRels(journalArticle);
	}

	private JournalArticle _addJournalArticle() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		_assetListEntryAssetEntryRelLocalService.addAssetListEntryAssetEntryRel(
			_assetListEntry.getAssetListEntryId(), assetEntry.getEntryId(), 0L,
			_serviceContext);

		return journalArticle;
	}

	private void _assertAssetListEntryAssetEntryRels(
			JournalArticle... journalArticles)
		throws Exception {

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

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