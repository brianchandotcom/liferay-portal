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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class StagingImplTest {

	@Test
	public void testLocalStagingJournal() throws Exception {
		enableLocalStaging(true, false);
	}

	@Test
	public void testLocalStagingPolls() throws Exception {
		enableLocalStaging(false, true);
	}

	protected JournalArticle addArticle(
			long groupId, String name, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		StringBundler sb = new StringBundler(3 + 6 * _locales.length);

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US,es_ES,de_DE\" default-locale=\"en_US\">");

		for (Locale locale : _locales) {
			sb.append("<static-content language-id=\"");
			sb.append(LocaleUtil.toLanguageId(locale));
			sb.append("\"><![CDATA[<p>");
			sb.append(content);
			sb.append(LocaleUtil.toLanguageId(locale));
			sb.append("</p>]]></static-content>");
		}

		sb.append("</root>");

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, 0, 0,
			StringPool.BLANK, true, 1, titleMap, descriptionMap, sb.toString(),
			"general", null, null, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true,
			0, 0, 0, 0, 0, true, false, false, null, null, null, null,
			serviceContext);
	}

	protected PollsChoice addChoice(String name, String description) {
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		PollsChoice choice = PollsChoiceUtil.create(0);

		choice.setName(name);
		choice.setDescriptionMap(descriptionMap);

		return choice;
	}

	protected PollsQuestion addQuestion(
			long groupId, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		List<PollsChoice> choices = new ArrayList<PollsChoice>();

		choices.add(addChoice("optionA", "descriptionA"));
		choices.add(addChoice("optionB", "descriptionB"));

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();
		serviceContext.setScopeGroupId(groupId);

		return PollsQuestionLocalServiceUtil.addQuestion(
			TestPropsValues.getUserId(), titleMap, descriptionMap, 0, 0, 0, 0,
			0, true, choices, serviceContext);
	}

	protected void enableLocalStaging(
			boolean stageJournal, boolean stagePolls)
		throws Exception {

		Group group = ServiceTestUtil.addGroup(ServiceTestUtil.randomString());

		ServiceTestUtil.addLayout(group.getGroupId(), "Page1");
		ServiceTestUtil.addLayout(group.getGroupId(), "Page2");

		int initialPagesCount = LayoutLocalServiceUtil.getLayoutsCount(
			group, false);

		// Create Content

		JournalArticle article = addArticle(
			group.getGroupId(), "Title", "content");

		PollsQuestion question = addQuestion(
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

		// Enable Staging

		StagingUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, group, false, false,
			serviceContext);

		Group stagingGroup = group.getStagingGroup();

		Assert.assertNotNull(stagingGroup);

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(stagingGroup, false),
			initialPagesCount);

		// Update Content in Staging

		JournalArticle stagingArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				stagingGroup.getGroupId(), article.getUrlTitle());

		stagingArticle = updateArticle(
			stagingArticle, "Title2", stagingArticle.getContent());

		PollsQuestion stagingQuestion =
			PollsQuestionLocalServiceUtil.getPollsQuestionByUuidAndGroupId(
				question.getUuid(), stagingGroup.getGroupId());

		stagingQuestion = updateQuestion(
			stagingQuestion, "Question2", "Description2");

		// Publish to Live

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			group.getGroupId(), false, parameters, null, null);

		// Retrieve content from Live after publishing

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());
		question = PollsQuestionLocalServiceUtil.getQuestion(
			question.getQuestionId());

		if (stagePolls) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					question.getTitle(locale),
					stagingQuestion.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertFalse(
					question.getTitle(locale).equals(
						stagingQuestion.getTitle(locale)));
			}
		}

		if (stageJournal) {
			for (Locale locale : _locales) {
				Assert.assertEquals(
					article.getTitle(locale), stagingArticle.getTitle(locale));
			}
		}
		else {
			for (Locale locale : _locales) {
				Assert.assertFalse(
					article.getTitle(locale).equals(
						stagingArticle.getTitle(locale)));
			}
		}
	}

	protected JournalArticle updateArticle(
			JournalArticle article, String name, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		return JournalArticleLocalServiceUtil.updateArticle(
			article.getUserId(), article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), titleMap,
			article.getDescriptionMap(), content, article.getLayoutUuid(),
			ServiceTestUtil.getServiceContext());
	}

	protected PollsQuestion updateQuestion(
			PollsQuestion question, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));

			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		return PollsQuestionLocalServiceUtil.updateQuestion(
			question.getUserId(), question.getQuestionId(), titleMap,
			descriptionMap, 0, 0, 0, 0, 0, true, question.getChoices(),
			ServiceTestUtil.getServiceContext());
	}

	private Locale[] _locales =  new Locale[] { 
		new Locale("en", "US"), new Locale("es", "ES"), 
		new Locale("de", "DE") };

}