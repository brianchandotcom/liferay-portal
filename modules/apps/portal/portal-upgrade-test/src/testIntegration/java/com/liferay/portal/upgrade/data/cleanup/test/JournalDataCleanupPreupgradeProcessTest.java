/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.JournalDataCleanupPreupgradeProcess;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class JournalDataCleanupPreupgradeProcessTest
	extends JournalDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		_group = GroupTestUtil.addGroup();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			Collections.emptyMap());

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		JournalFeed journalFeed = JournalTestUtil.addFeed(
			_group.getGroupId(), layout.getPlid(),
			RandomTestUtil.randomString(), journalArticle.getDDMStructureId(),
			journalArticle.getDDMTemplateKey(),
			journalArticle.getDDMTemplateKey());

		runSQL(
			"update JournalArticle set articleId = '-1' where articleId = '" +
				journalArticle.getArticleId() + "'");
		runSQL(
			"update JournalFeed set feedId = '-1' where feedId = '" +
				journalFeed.getFeedId() + "'");

		try {
			upgrade();
		}
		finally {
			CacheRegistryUtil.clear();

			runSQL(
				"update JournalArticle set articleId = '" +
					journalArticle.getArticleId() + "' where articleId = '-1'");
			runSQL(
				"update JournalFeed set feedId = '" + journalFeed.getFeedId() +
					"' where feedId = '-1'");
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}