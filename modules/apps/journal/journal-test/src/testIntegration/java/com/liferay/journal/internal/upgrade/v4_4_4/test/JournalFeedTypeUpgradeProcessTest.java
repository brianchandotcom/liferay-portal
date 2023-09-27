/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v4_4_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
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
public class JournalFeedTypeUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			journalArticleClassNameId);
		DDMTemplate rendererDDMTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			journalArticleClassNameId);

		_journalFeed = JournalTestUtil.addFeed(
			_group.getGroupId(), _layout.getPlid(),
			RandomTestUtil.randomString(), ddmStructure.getStructureId(),
			ddmTemplate.getTemplateKey(), rendererDDMTemplate.getTemplateKey());

		_journalFeedClassNameId = _classNameLocalService.getClassNameId(
			JournalFeed.class.getName());
	}

	@Test
	public void testCreateAssetEntryAssetEntryDoesNotExists() throws Exception {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_journalFeedClassNameId, _journalFeed.getId());

		Assert.assertNotNull(assetEntry);

		_assetEntryLocalService.deleteAssetEntry(assetEntry.getEntryId());

		assetEntry = _assetEntryLocalService.fetchEntry(
			_journalFeedClassNameId, _journalFeed.getId());

		Assert.assertNull(assetEntry);

		_runUpgrade();

		assetEntry = _assetEntryLocalService.fetchEntry(
			_journalFeedClassNameId, _journalFeed.getId());

		Assert.assertNotNull(assetEntry);
	}

	@Test
	public void testCreateAssetEntryAssetEntryExists() throws Exception {
		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			_journalFeedClassNameId, _journalFeed.getId());

		Assert.assertNotNull(assetEntry1);

		List<AssetEntry> assetEntries1 =
			_assetEntryLocalService.getGroupEntries(_group.getGroupId());

		Assert.assertFalse(assetEntries1.toString(), assetEntries1.isEmpty());

		_runUpgrade();

		List<AssetEntry> assetEntries2 =
			_assetEntryLocalService.getGroupEntries(_group.getGroupId());

		Assert.assertEquals(
			assetEntries2.toString(), assetEntries1.size(),
			assetEntries2.size());

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			_journalFeedClassNameId, _journalFeed.getId());

		Assert.assertNotNull(assetEntry2);

		Assert.assertEquals(assetEntry1.getEntryId(), assetEntry2.getEntryId());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v4_4_4." +
			"JournalFeedTypeUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private JournalFeed _journalFeed;

	private long _journalFeedClassNameId;

	@DeleteAfterTestRun
	private Layout _layout;

}