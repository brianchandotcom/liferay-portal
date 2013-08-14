/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JournalArticleScheduledTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testScheduledApprovedArticle() throws Exception {
		testScheduledArticle(true);
	}

	@Test
	public void testScheduledDraftArticle() throws Exception {
		testScheduledArticle(false);
	}

	protected JournalArticle addJournalArticle(
			long groupId, boolean approved, Date displayDate)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.getDefault(), ServiceTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(
			LocaleUtil.getDefault(), ServiceTestUtil.randomString());

		Calendar displayDateCalendar = new GregorianCalendar();

		displayDateCalendar.setTime(
			new Date(displayDate.getTime() + Time.MINUTE * 5));

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		if (approved) {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}
		else {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap,
			JournalTestUtil.createLocalizedContent(
				ServiceTestUtil.randomString(), LocaleUtil.getDefault()),
			"general", null, null, null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR_OF_DAY),
			displayDateCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0,
			0, 0, 0, true, true, false, null, null, null, null, serviceContext);
	}

	protected void testScheduledArticle(boolean approved) throws Exception {
		Group group = GroupTestUtil.addGroup();

		int initialSearchArticlesCount = JournalTestUtil.getSearchArticlesCount(
			group.getCompanyId(), group.getGroupId());

		Date now = new Date();

		JournalArticle article = addJournalArticle(
			group.getGroupId(), approved, now);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (approved) {
			Assert.assertFalse(article.isApproved());
			Assert.assertTrue(article.isScheduled());
			Assert.assertFalse(assetEntry.isVisible());
		}
		else {
			Assert.assertTrue(article.isDraft());
			Assert.assertFalse(article.isScheduled());
			Assert.assertFalse(assetEntry.isVisible());
		}

		Assert.assertEquals(
			initialSearchArticlesCount,
			JournalTestUtil.getSearchArticlesCount(
				group.getCompanyId(), group.getGroupId()));

		// Modify the article Date surpassing the service to simulate the time
		// has passed

		Calendar displayDateCalendar = new GregorianCalendar();

		displayDateCalendar.setTime(new Date(now.getTime() - Time.MINUTE * 5));

		article.setDisplayDate(displayDateCalendar.getTime());

		article = JournalArticleLocalServiceUtil.updateJournalArticle(article);

		// Launch the scheduled task

		JournalArticleLocalServiceUtil.checkArticles();

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		assetEntry = AssetEntryLocalServiceUtil.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (approved) {
			Assert.assertTrue(article.isApproved());
			Assert.assertFalse(article.isScheduled());
			Assert.assertTrue(assetEntry.isVisible());

			Assert.assertEquals(
				initialSearchArticlesCount + 1,
				JournalTestUtil.getSearchArticlesCount(
					group.getCompanyId(), group.getGroupId()));
		}
		else {
			Assert.assertTrue(article.isDraft());
			Assert.assertFalse(article.isScheduled());
			Assert.assertFalse(assetEntry.isVisible());

			Assert.assertEquals(
				initialSearchArticlesCount,
				JournalTestUtil.getSearchArticlesCount(
					group.getCompanyId(), group.getGroupId()));
		}

		GroupLocalServiceUtil.deleteGroup(group);
	}

}