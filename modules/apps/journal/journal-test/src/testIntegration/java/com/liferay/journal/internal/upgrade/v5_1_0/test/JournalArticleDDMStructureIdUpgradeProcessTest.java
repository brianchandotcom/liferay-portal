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

package com.liferay.journal.internal.upgrade.v5_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class JournalArticleDDMStructureIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcess() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_companyGroupDDMStructure = DDMStructureTestUtil.addStructure(
			companyGroup.getGroupId(), JournalArticle.class.getName());

		_companyGroupJournalArticle = JournalTestUtil.addArticleWithXMLContent(
			companyGroup.getGroupId(), 0,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			DDMStructureTestUtil.getSampleStructuredContent(),
			_companyGroupDDMStructure.getStructureKey(), StringPool.BLANK);

		_group1 = GroupTestUtil.addGroup();

		DDMStructure group1DDMStructure = DDMStructureTestUtil.addStructure(
			_group1.getGroupId(), JournalArticle.class.getName());

		JournalArticle group1JournalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group1.getGroupId(), 0,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(),
				group1DDMStructure.getStructureKey(), StringPool.BLANK);

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group1);

		Group layoutGroup = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), layout.getGroupId(), layout);

		JournalArticle group1LayoutGroupJournalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				layoutGroup.getGroupId(), 0,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(),
				group1DDMStructure.getStructureKey(), StringPool.BLANK);

		_group2 = GroupTestUtil.addGroup();

		DDMStructure group2DDMStructure = DDMStructureTestUtil.addStructure(
			_group2.getGroupId(), JournalArticle.class.getName());

		JournalArticle group2JournalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group2.getGroupId(), 0,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(),
				group2DDMStructure.getStructureKey(), StringPool.BLANK);

		JournalArticle group2CompanyGroupDDMStructureJournalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group2.getGroupId(), 0,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(),
				_companyGroupDDMStructure.getStructureKey(), StringPool.BLANK);

		_unsetDDMStructureId(
			_companyGroupJournalArticle, group1JournalArticle,
			group1LayoutGroupJournalArticle, group2JournalArticle,
			group2CompanyGroupDDMStructureJournalArticle);

		_runUpgrade();

		_assertDDMStructureId(
			_companyGroupJournalArticle, group1JournalArticle,
			group1LayoutGroupJournalArticle, group2JournalArticle,
			group2CompanyGroupDDMStructureJournalArticle);
	}

	private void _assertDDMStructureId(JournalArticle... journalArticles)
		throws Exception {

		for (JournalArticle journalArticle : journalArticles) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(), journalArticle.getId());

			if (assetEntry == null) {
				assetEntry = _assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
			}

			Assert.assertNotNull(assetEntry);

			JournalArticle updatedJournalArticle =
				_journalArticleLocalService.getJournalArticle(
					journalArticle.getId());

			Assert.assertEquals(
				assetEntry.getClassTypeId(),
				updatedJournalArticle.getDDMStructureId());
		}
	}

	private UpgradeProcess _getUpgradeProcess() {
		UpgradeProcess[] upgradeProcesses = new UpgradeProcess[1];

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					if (Objects.equals(clazz.getName(), _CLASS_NAME)) {
						upgradeProcesses[0] = (UpgradeProcess)upgradeStep;

						break;
					}
				}
			});

		return upgradeProcesses[0];
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	private void _unsetDDMStructureId(JournalArticle... journalArticles) {
		for (JournalArticle journalArticle : journalArticles) {
			journalArticle.setDDMStructureId(0);

			JournalArticle updatedJournalArticle =
				_journalArticleLocalService.updateJournalArticle(
					journalArticle);

			Assert.assertEquals(0, updatedJournalArticle.getDDMStructureId());
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v5_1_0." +
			"JournalArticleDDMStructureIdUpgradeProcess";

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

	@DeleteAfterTestRun
	private JournalArticle _companyGroupJournalArticle;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}