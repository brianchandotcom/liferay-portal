/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.staging;

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Julio Camarero
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	TransactionalExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class StagingImplTest extends BaseStagingTestCase {

	@Test
	public void testLocalStagingJournal() throws Exception {
		enableLocalStaging(true, false);
	}

	@Test
	public void testLocalStagingPolls() throws Exception {
		enableLocalStaging(false, true);
	}

	protected void enableLocalStaging(boolean stageJournal, boolean stagePolls)
		throws Exception {

		Group group = ServiceTestUtil.addGroup();

		ServiceTestUtil.addLayout(group.getGroupId(), "Page1");
		ServiceTestUtil.addLayout(group.getGroupId(), "Page2");

		int initialPagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			group, false);

		// Create content

		JournalArticle journalArticle = addJournalArticle(
			group.getGroupId(), "Title", "content", "en_US");

		PollsQuestion pollsQuestion = addPollsQuestion(
			group.getGroupId(), "Question", "Description");

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(group.getGroupId());

		Map<String, String[]> parameters = StagingUtil.getStagingParameters();

		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {String.valueOf(false)});

		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" + PortletKeys.JOURNAL,
			new String[] {String.valueOf(stageJournal)});

		parameters.put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" + PortletKeys.POLLS,
			new String[] {String.valueOf(stagePolls)});

		for (String parameterName : parameters.keySet()) {
			serviceContext.setAttribute(
				parameterName, parameters.get(parameterName)[0]);
		}

		serviceContext.setAttribute(
			StagingConstants.STAGED_PORTLET + PortletKeys.JOURNAL,
			stageJournal);

		serviceContext.setAttribute(
			StagingConstants.STAGED_PORTLET + PortletKeys.POLLS, stagePolls);

		// Enable staging

		StagingUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, group, false, false,
			serviceContext);

		Group stagingGroup = group.getStagingGroup();

		Assert.assertNotNull(stagingGroup);

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(stagingGroup, false),
			initialPagesCount);

		// Update content in staging

		JournalArticle stagingJournalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				stagingGroup.getGroupId(), journalArticle.getUrlTitle());

		stagingJournalArticle = updateJournalArticle(
			stagingJournalArticle, "Title2",
			stagingJournalArticle.getContent());

		PollsQuestion stagingQuestion =
			PollsQuestionLocalServiceUtil.getPollsQuestionByUuidAndGroupId(
				pollsQuestion.getUuid(), stagingGroup.getGroupId());

		stagingQuestion = updatePollsQuestion(
			stagingQuestion, "Question2", "Description2");

		// Publish to live

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			group.getGroupId(), false, parameters, null, null);

		// Retrieve content from live after publishing

		journalArticle = JournalArticleLocalServiceUtil.getArticle(
			group.getGroupId(), journalArticle.getArticleId());
		pollsQuestion = PollsQuestionLocalServiceUtil.getQuestion(
			pollsQuestion.getQuestionId());

		if (stagePolls) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					pollsQuestion.getTitle(locale),
					stagingQuestion.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertFalse(
					pollsQuestion.getTitle(locale).equals(
						stagingQuestion.getTitle(locale)));
			}
		}

		if (stageJournal) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					journalArticle.getTitle(locale),
					stagingJournalArticle.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertFalse(
					journalArticle.getTitle(locale).equals(
						stagingJournalArticle.getTitle(locale)));
			}
		}
	}

}