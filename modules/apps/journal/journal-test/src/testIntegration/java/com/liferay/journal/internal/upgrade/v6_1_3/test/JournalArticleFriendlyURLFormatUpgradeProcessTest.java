/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Victor Alves
 */
@RunWith(Arquillian.class)
public class JournalArticleFriendlyURLFormatUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (long friendlyURLEntryId : _friendlyURLEntryIds) {
			_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
				friendlyURLEntryId,
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		}
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetFriendlyURLWithoutTrailingSlash() throws Exception {
		_addJournalArticle("test/");

		_runUpgrade();

		_journalArticle = _journalArticleLocalService.fetchJournalArticle(
			_journalArticleId);

		Assert.assertEquals("test", _journalArticle.getUrlTitle());
	}

	@Test
	public void testGetFriendlyURLWithUniqueURL() throws Exception {
		_addJournalArticle("test");
		_addJournalArticle("test/");

		_runUpgrade();

		_journalArticle = _journalArticleLocalService.fetchJournalArticle(
			_journalArticleId);

		Assert.assertEquals("test-1", _journalArticle.getUrlTitle());
	}

	private void _addJournalArticle(String urlTitle) {
		_journalArticleId = RandomTestUtil.randomLong();

		_friendlyURLEntryIds.add(_journalArticleId);

		_journalArticle = _journalArticleLocalService.createJournalArticle(
			_journalArticleId);

		_journalArticle.setGroupId(_group.getGroupId());

		_journalArticle.setDefaultLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));

		_journalArticle.setGroupId(_group.getGroupId());

		_journalArticle.setUrlTitle(urlTitle);

		_journalArticle.setResourcePrimKey(_journalArticleId);

		_journalArticleLocalService.addJournalArticle(_journalArticle);

		_journalArticleResourceLocalService.addJournalArticleResource(
			_journalArticleResourceLocalService.createJournalArticleResource(
				_journalArticleId));

		_createFriendlyURLEntryLocalization(urlTitle);
	}

	private void _createFriendlyURLEntryLocalization(String urlTitle) {
		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.createFriendlyURLEntry(
				_journalArticleId);

		friendlyURLEntry.setDefaultLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));

		_friendlyURLEntryLocalService.addFriendlyURLEntry(friendlyURLEntry);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalizationPersistence.create(
				RandomTestUtil.randomInt());

		friendlyURLEntryLocalization.setFriendlyURLEntryId(_journalArticleId);

		friendlyURLEntryLocalization.setLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		friendlyURLEntryLocalization.setUrlTitle(urlTitle);
		friendlyURLEntryLocalization.setGroupId(_group.getGroupId());
		friendlyURLEntryLocalization.setClassNameId(
			_classNameLocalService.getClassNameId(JournalArticle.class));
		friendlyURLEntryLocalization.setClassPK(_journalArticleId);

		_friendlyURLEntryLocalService.updateFriendlyURLLocalization(
			friendlyURLEntryLocalization);
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

	private static final List<Long> _friendlyURLEntryIds = new ArrayList<>();

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@DeleteAfterTestRun
	private static Group _group;

	@Inject
	private static JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

	private JournalArticle _journalArticle;
	private long _journalArticleId;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}