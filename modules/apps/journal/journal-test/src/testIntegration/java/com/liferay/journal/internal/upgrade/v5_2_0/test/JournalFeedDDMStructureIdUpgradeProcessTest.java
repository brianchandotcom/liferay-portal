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

package com.liferay.journal.internal.upgrade.v5_2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
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
public class JournalFeedDDMStructureIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		_group1Layout = LayoutTestUtil.addTypePortletLayout(_group1);

		_group1DDMStructure = DDMStructureTestUtil.addStructure(
			_group1.getGroupId(), JournalArticle.class.getName());

		_group1DDMStructureJournalFeed = _addJournalFeed(
			_group1DDMStructure, _group1, _group1Layout);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_companyGroupDDMStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), JournalArticle.class.getName());

		_companyGroupDDMStructureJournalFeed = _addJournalFeed(
			_companyGroupDDMStructure, _group1, _group1Layout);

		_group2 = GroupTestUtil.addGroup();

		_group2Layout = LayoutTestUtil.addTypePortletLayout(_group2);

		_group2DDMStructure = DDMStructureTestUtil.addStructure(
			_group2.getGroupId(), JournalArticle.class.getName());

		_group2DDMStructureJournalFeed = _addJournalFeed(
			_group2DDMStructure, _group2, _group2Layout);
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		_setDDMStructureKey(
			false, _group1DDMStructureJournalFeed,
			_group2DDMStructureJournalFeed,
			_companyGroupDDMStructureJournalFeed);

		_unsetDDMStructureId(
			_group1DDMStructureJournalFeed, _group2DDMStructureJournalFeed,
			_companyGroupDDMStructureJournalFeed);

		List<LogEntry> logEntries = _runUpgrade();

		Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

		_assertDDMStructureId(
			_group1DDMStructure.getStructureId(),
			_group1DDMStructureJournalFeed);
		_assertDDMStructureId(
			_group2DDMStructure.getStructureId(),
			_group2DDMStructureJournalFeed);
		_assertDDMStructureId(
			_companyGroupDDMStructure.getStructureId(),
			_companyGroupDDMStructureJournalFeed);
	}

	@Test
	public void testUpgradeProcessDDMStructureNotFound() throws Exception {
		_setDDMStructureKey(
			true, _group1DDMStructureJournalFeed,
			_group2DDMStructureJournalFeed,
			_companyGroupDDMStructureJournalFeed);

		_unsetDDMStructureId(
			_group1DDMStructureJournalFeed, _group2DDMStructureJournalFeed,
			_companyGroupDDMStructureJournalFeed);

		List<LogEntry> logEntries = _runUpgrade();

		Assert.assertEquals(logEntries.toString(), 6, logEntries.size());

		_assertDDMStructureId(
			0, _group1DDMStructureJournalFeed, _group2DDMStructureJournalFeed,
			_companyGroupDDMStructureJournalFeed);
	}

	private JournalFeed _addJournalFeed(
			DDMStructure ddmStructure, Group group, Layout layout)
		throws Exception {

		long journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			journalArticleClassNameId);
		DDMTemplate rendererDDMTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			journalArticleClassNameId);

		return JournalTestUtil.addFeed(
			group.getGroupId(), layout.getPlid(), RandomTestUtil.randomString(),
			ddmStructure.getStructureId(), ddmTemplate.getTemplateKey(),
			rendererDDMTemplate.getTemplateKey());
	}

	private void _assertDDMStructureId(
			long expectedDDMStructureId, JournalFeed... journalFeeds)
		throws Exception {

		for (JournalFeed journalFeed : journalFeeds) {
			JournalFeed updatedJournalFeed =
				_journalFeedLocalService.getJournalFeed(journalFeed.getId());

			Assert.assertEquals(
				expectedDDMStructureId, updatedJournalFeed.getDDMStructureId());
		}
	}

	private List<LogEntry> _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.WARN)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();

			return logCapture.getLogEntries();
		}
	}

	private void _setDDMStructureKey(
			boolean randomDDMStructureKey, JournalFeed... journalFeeds)
		throws Exception {

		for (JournalFeed journalFeed : journalFeeds) {
			String ddmStructureKey = RandomTestUtil.randomString();

			if (!randomDDMStructureKey) {
				DDMStructure ddmStructure =
					_ddmStructureLocalService.getDDMStructure(
						journalFeed.getDDMStructureId());

				ddmStructureKey = ddmStructure.getStructureKey();
			}

			journalFeed = _journalFeedLocalService.getJournalFeed(
				journalFeed.getId());

			journalFeed.setDDMStructureKey(ddmStructureKey);

			_journalFeedLocalService.updateJournalFeed(journalFeed);
		}
	}

	private void _unsetDDMStructureId(JournalFeed... journalFeeds)
		throws Exception {

		for (JournalFeed journalFeed : journalFeeds) {
			journalFeed = _journalFeedLocalService.getJournalFeed(
				journalFeed.getId());

			journalFeed.setDDMStructureId(0);

			JournalFeed updatedJournalFeed =
				_journalFeedLocalService.updateJournalFeed(journalFeed);

			Assert.assertEquals(0, updatedJournalFeed.getDDMStructureId());
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v5_2_0." +
			"JournalFeedDDMStructureIdUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private DDMStructure _companyGroupDDMStructure;

	private JournalFeed _companyGroupDDMStructureJournalFeed;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	private DDMStructure _group1DDMStructure;
	private JournalFeed _group1DDMStructureJournalFeed;
	private Layout _group1Layout;

	@DeleteAfterTestRun
	private Group _group2;

	private DDMStructure _group2DDMStructure;
	private JournalFeed _group2DDMStructureJournalFeed;
	private Layout _group2Layout;

	@Inject
	private JournalFeedLocalService _journalFeedLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}