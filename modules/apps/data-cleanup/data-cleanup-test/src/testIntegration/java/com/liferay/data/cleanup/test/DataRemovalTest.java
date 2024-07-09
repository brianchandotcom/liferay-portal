/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class DataRemovalTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRemoveWidgetLayoutTypeSettings() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		String key = RandomTestUtil.randomString();

		layout = _layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			UnicodePropertiesBuilder.put(
				key, "true"
			).put(
				LayoutConstants.CUSTOMIZABLE_LAYOUT, "true"
			).put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
			).put(
				"column-1-customizable", "false"
			).put(
				"column-2-customizable", "false"
			).build(
			).toString());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"removeWidgetLayoutTypeSettings", true
					).build())) {

			Layout curLayout = _layoutLocalService.getLayout(layout.getPlid());

			UnicodeProperties typeSettingsUnicodeProperties =
				curLayout.getTypeSettingsProperties();

			Assert.assertTrue(
				GetterUtil.getBoolean(typeSettingsUnicodeProperties.get(key)));
			Assert.assertNull(
				typeSettingsUnicodeProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));
			Assert.assertNull(
				typeSettingsUnicodeProperties.get(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));

			for (String curKey : typeSettingsUnicodeProperties.keySet()) {
				Assert.assertFalse(curKey.startsWith("column-"));
			}
		}
	}

	@Test
	public void testUpgradeExpiredJournalArticle() throws Exception {
		JournalArticle expiredJournalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalArticle expiredJournalArticle2 = JournalTestUtil.updateArticle(
			expiredJournalArticle1);

		JournalArticle unexpiredJournalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalTestUtil.expireArticle(
			expiredJournalArticle2.getGroupId(), expiredJournalArticle2);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"removeExpiredJournalArticles", true
					).build())) {

			FinderCacheUtil.clearLocalCache();

			Assert.assertNull(
				_journalArticleLocalService.fetchArticle(
					expiredJournalArticle1.getId()));
			Assert.assertNull(
				_journalArticleLocalService.fetchArticle(
					expiredJournalArticle2.getId()));
			Assert.assertNotNull(
				_journalArticleLocalService.fetchArticle(
					unexpiredJournalArticle.getId()));
		}
	}

	private static final String _CONFIGURATION_PID =
		"com.liferay.data.cleanup.internal.configuration." +
			"DataRemovalConfiguration";

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

}