/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@RunWith(Arquillian.class)
public class JournalArticleScheduledTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testScheduleApprovedArticleToTheFuture() throws Exception {
		testScheduleArticle(true, _WHEN_FUTURE);
	}

	@Test
	public void testScheduleApprovedArticleToThePast() throws Exception {
		testScheduleArticle(true, _WHEN_PAST);
	}

	@Test
	public void testScheduleDraftArticleToTheFuture() throws Exception {
		testScheduleArticle(false, _WHEN_FUTURE);
	}

	@Test
	public void testScheduleDraftArticleToThePast() throws Exception {
		testScheduleArticle(false, _WHEN_PAST);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected JournalArticle addArticle(
			long groupId, Date displayDate, int when, boolean approved)
		throws Exception {

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, JournalArticle.class.getName(), ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			groupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		Calendar displayDateCalendar = getCalendar(displayDate, when);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		if (approved) {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}
		else {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), groupId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, titleMap, content, ddmStructure.getStructureId(),
			ddmTemplate.getTemplateKey(), null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR_OF_DAY),
			displayDateCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0,
			0, 0, 0, true, true, false, 0, 0, null, null, null, null,
			serviceContext);
	}

	protected Calendar getCalendar(Date date, int when) {
		Calendar calendar = new GregorianCalendar();

		calendar.setTime(new Date(date.getTime() + (Time.MINUTE * when * 5)));

		return calendar;
	}

	protected void testScheduleArticle(boolean approved, int when)
		throws Exception {

		int initialSearchArticlesCount = JournalTestUtil.getSearchArticlesCount(
			_group.getCompanyId(), _group.getGroupId());

		JournalArticle article = addArticle(
			_group.getGroupId(), new Date(), when, approved);

		_journalArticleLocalService.checkArticles(_group.getCompanyId());

		article = _journalArticleLocalService.getArticle(article.getId());

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (when == _WHEN_FUTURE) {
			Assert.assertFalse(article.isApproved());
			Assert.assertFalse(assetEntry.isVisible());

			if (approved) {
				Assert.assertTrue(article.isScheduled());
			}
			else {
				Assert.assertTrue(article.isDraft());
			}
		}
		else {
			Assert.assertFalse(article.isScheduled());
			Assert.assertEquals(approved, article.isApproved());
			Assert.assertEquals(approved, assetEntry.isVisible());

			Hits hits = JournalTestUtil.getSearchArticles(
				_group.getCompanyId(), _group.getGroupId());

			if (approved) {
				Assert.assertEquals(
					hits.toString(), initialSearchArticlesCount + 1,
					hits.getLength());
			}
			else {
				Assert.assertEquals(
					hits.toString(), initialSearchArticlesCount,
					hits.getLength());
			}
		}
	}

	private static final int _WHEN_FUTURE = 1;

	private static final int _WHEN_PAST = -1;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}