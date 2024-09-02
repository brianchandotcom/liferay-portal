/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.test.util.BaseFriendlyURLFormatUpgradeProcessTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Victor Alves
 */
@RunWith(Arquillian.class)
public class JournalArticleFriendlyURLFormatUpgradeProcessTest
	extends BaseFriendlyURLFormatUpgradeProcessTestCase<JournalArticle> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetFriendlyURLWithoutTrailingSlash() throws Exception {
		_addJournalArticle("test/");

		_runUpgrade();

		_journalArticle = _journalArticleLocalService.fetchJournalArticle(
			modelId);

		Assert.assertEquals("test", _journalArticle.getUrlTitle());
	}

	@Test
	public void testGetFriendlyURLWithUniqueURL() throws Exception {
		_addJournalArticle("test");
		_addJournalArticle("test/");

		_runUpgrade();

		_journalArticle = _journalArticleLocalService.fetchJournalArticle(
			modelId);

		Assert.assertEquals("test-1", _journalArticle.getUrlTitle());
	}

	private void _addJournalArticle(String urlTitle) {
		createFriendlyURLEntryLocalization(urlTitle, JournalArticle.class);

		_journalArticle = _journalArticleLocalService.createJournalArticle(
			modelId);

		_journalArticle.setDefaultLanguageId(languageId);

		_journalArticle.setGroupId(group.getGroupId());

		_journalArticle.setUrlTitle(urlTitle);

		_journalArticle.setResourcePrimKey(modelId);

		_journalArticleLocalService.addJournalArticle(_journalArticle);

		_journalArticleResourceLocalService.addJournalArticleResource(
			_journalArticleResourceLocalService.createJournalArticleResource(
				modelId));
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.WARN)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v6_1_3." +
			"JournalArticleFriendlyURLFormatUpgradeProcess";

	@Inject
	private static JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}