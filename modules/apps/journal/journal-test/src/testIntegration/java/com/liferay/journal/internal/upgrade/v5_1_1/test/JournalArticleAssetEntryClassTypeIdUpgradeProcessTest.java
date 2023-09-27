/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v5_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class JournalArticleAssetEntryClassTypeIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcess() throws Exception {
		_group = GroupTestUtil.addGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		JournalArticle approvedJournalArticle1 = _addApprovedJournalArticle(
			ddmStructure, ddmStructure.getStructureId());

		JournalArticle draftJournalArticle1 = _addDraftVersionJournalArticle(
			approvedJournalArticle1, ddmStructure.getStructureId());

		long wrongClassTypeId = RandomTestUtil.randomLong();

		JournalArticle approvedJournalArticle2 = _addApprovedJournalArticle(
			ddmStructure, wrongClassTypeId);

		JournalArticle draftJournalArticle2 = _addDraftVersionJournalArticle(
			approvedJournalArticle2, wrongClassTypeId);

		List<LogEntry> logEntries = _getRunUpgradeLogEntries(
			LoggerTestUtil.WARN);

		_assertWrongClassTypeIdLogEntries(
			logEntries, ddmStructure.getStructureId(), wrongClassTypeId,
			approvedJournalArticle2.getResourcePrimKey(),
			draftJournalArticle2.getId());

		_assertClassTypeId(
			approvedJournalArticle1, approvedJournalArticle2,
			draftJournalArticle1, draftJournalArticle2);
	}

	@Test
	public void testUpgradeProcessNoChanges() throws Exception {
		_group = GroupTestUtil.addGroup();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		JournalArticle approvedJournalArticle = _addApprovedJournalArticle(
			ddmStructure, ddmStructure.getStructureId());

		JournalArticle draftJournalArticle = _addDraftVersionJournalArticle(
			approvedJournalArticle, ddmStructure.getStructureId());

		List<LogEntry> logEntries = _getRunUpgradeLogEntries(
			LoggerTestUtil.DEBUG);

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(LoggerTestUtil.DEBUG, logEntry.getPriority());

		Assert.assertEquals(
			"No asset entries with the wrong class type ID were found",
			logEntry.getMessage());

		_assertClassTypeId(approvedJournalArticle, draftJournalArticle);
	}

	private JournalArticle _addApprovedJournalArticle(
			DDMStructure ddmStructure, long assetEntryClassTypeId)
		throws Exception {

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(), 0,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(),
				ddmStructure.getStructureKey(), StringPool.BLANK);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, journalArticle.getStatus());

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		Assert.assertNotNull(assetEntry);

		Assert.assertTrue(assetEntry.isVisible());

		Assert.assertEquals(
			journalArticle.getDDMStructureId(), assetEntry.getClassTypeId());

		if (assetEntryClassTypeId != assetEntry.getClassTypeId()) {
			assetEntry.setClassTypeId(assetEntryClassTypeId);

			assetEntry = _assetEntryLocalService.updateAssetEntry(assetEntry);

			Assert.assertEquals(
				assetEntryClassTypeId, assetEntry.getClassTypeId());
		}

		return journalArticle;
	}

	private JournalArticle _addDraftVersionJournalArticle(
			JournalArticle approvedJournalArticle, long assetEntryClassTypeId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		JournalArticle draftJournalArticle = JournalTestUtil.updateArticle(
			approvedJournalArticle, RandomTestUtil.randomString(),
			approvedJournalArticle.getContent(), false, false, serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, draftJournalArticle.getStatus());

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), draftJournalArticle.getId());

		Assert.assertNotNull(assetEntry);

		Assert.assertFalse(assetEntry.isVisible());

		Assert.assertEquals(
			approvedJournalArticle.getDDMStructureId(),
			assetEntry.getClassTypeId());

		if (assetEntryClassTypeId != assetEntry.getClassTypeId()) {
			assetEntry.setClassTypeId(assetEntryClassTypeId);

			assetEntry = _assetEntryLocalService.updateAssetEntry(assetEntry);

			Assert.assertEquals(
				assetEntryClassTypeId, assetEntry.getClassTypeId());
		}

		return draftJournalArticle;
	}

	private void _assertClassTypeId(JournalArticle... journalArticles) {
		for (JournalArticle journalArticle : journalArticles) {
			AssetEntry assetEntry;

			if (journalArticle.isDraft() &&
				(journalArticle.getVersion() !=
					JournalArticleConstants.VERSION_DEFAULT)) {

				assetEntry = _assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(), journalArticle.getId());
			}
			else {
				assetEntry = _assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
			}

			Assert.assertNotNull(assetEntry);

			Assert.assertEquals(
				journalArticle.isApproved(), assetEntry.isVisible());

			Assert.assertEquals(
				journalArticle.getDDMStructureId(),
				assetEntry.getClassTypeId());
		}
	}

	private void _assertWrongClassTypeIdLogEntries(
		List<LogEntry> logEntries, long expectedClassTypeId,
		long wrongClassTypeId, long... classPKs) {

		Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

		LogEntry logEntry1 = logEntries.get(0);

		Assert.assertEquals(LoggerTestUtil.WARN, logEntry1.getPriority());

		Assert.assertEquals(
			"Asset entries with the wrong class type ID " + wrongClassTypeId +
				" were found",
			logEntry1.getMessage());

		LogEntry logEntry2 = logEntries.get(1);

		Assert.assertEquals(LoggerTestUtil.WARN, logEntry2.getPriority());

		String message = logEntry2.getMessage();

		Assert.assertTrue(
			message,
			message.startsWith(
				expectedClassTypeId +
					" has been set as class type ID for the entry IDs"));

		List<Long> expectedEntryIds = new ArrayList<>();

		for (long classPK : classPKs) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(), classPK);

			Assert.assertNotNull(assetEntry);

			expectedEntryIds.add(assetEntry.getEntryId());
		}

		List<String> entryIds = StringUtil.split(
			StringUtils.substringBetween(
				message, StringPool.OPEN_BRACKET, StringPool.CLOSE_BRACKET));

		Assert.assertEquals(
			entryIds.toString(), expectedEntryIds.size(), entryIds.size());

		for (String entryId : entryIds) {
			Assert.assertTrue(
				expectedEntryIds.toString(),
				expectedEntryIds.contains(GetterUtil.getLong(entryId)));
		}
	}

	private List<LogEntry> _getRunUpgradeLogEntries(String logPriority)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, logPriority)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();

			return logCapture.getLogEntries();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v5_1_1." +
			"JournalArticleAssetEntryClassTypeIdUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

}