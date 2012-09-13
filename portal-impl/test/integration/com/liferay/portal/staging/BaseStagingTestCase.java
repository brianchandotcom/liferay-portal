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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PrefsPropsUtil;
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

import javax.portlet.PortletPreferences;

/**
 *
 * @author Julio Camarero
 */
public class BaseStagingTestCase {
	protected JournalArticle addJournalArticle(
			long groupId, String name, String content, String defaultLanguageId)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		StringBundler sb = new StringBundler(5 + 8 * _locales.length);

		sb.append("<?xml version=\"1.0\"?><root available-locales=\"");

		for (Locale locale : _locales) {
			sb.append(LocaleUtil.toLanguageId(locale));

			sb.append(StringPool.COMMA);
		}

		sb.append("\" default-locale=\"");
		sb.append(defaultLanguageId);
		sb.append("\">");

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

	protected PollsChoice addPollsChoice(String name, String description) {
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		PollsChoice pollsChoice = PollsChoiceUtil.create(0);

		pollsChoice.setName(name);
		pollsChoice.setDescriptionMap(descriptionMap);

		return pollsChoice;
	}

	protected PollsQuestion addPollsQuestion(
			long groupId, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		List<PollsChoice> pollsChoices = new ArrayList<PollsChoice>();

		pollsChoices.add(addPollsChoice("optionA", "descriptionA"));
		pollsChoices.add(addPollsChoice("optionB", "descriptionB"));

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return PollsQuestionLocalServiceUtil.addQuestion(
			TestPropsValues.getUserId(), titleMap, descriptionMap, 0, 0, 0, 0,
			0, true, pollsChoices, serviceContext);
	}

	protected JournalArticle updateJournalArticle(
			JournalArticle journalArticle, String name, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		return JournalArticleLocalServiceUtil.updateArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), titleMap,
			journalArticle.getDescriptionMap(), content,
			journalArticle.getLayoutUuid(),
			ServiceTestUtil.getServiceContext());
	}

	protected void updateLocales(String defaultLanguageId, String languageIds)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			company.getCompanyId());

		preferences.setValue(PropsKeys.LOCALES, languageIds);

		preferences.store();

		LanguageUtil.resetAvailableLocales(company.getCompanyId());

		CompanyLocalServiceUtil.updateDisplay(
			company.getCompanyId(), defaultLanguageId,
			company.getTimeZone().getID());

		LocaleThreadLocal.setDefaultLocale(
			LocaleUtil.fromLanguageId(defaultLanguageId));
	}

	protected PollsQuestion updatePollsQuestion(
			PollsQuestion pollsQuestion, String title, String description)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
			descriptionMap.put(
				locale, description.concat(LocaleUtil.toLanguageId(locale)));
		}

		return PollsQuestionLocalServiceUtil.updateQuestion(
			pollsQuestion.getUserId(), pollsQuestion.getQuestionId(), titleMap,
			descriptionMap, 0, 0, 0, 0, 0, true, pollsQuestion.getChoices(),
			ServiceTestUtil.getServiceContext());
	}

	protected static Locale[] _locales = {
		new Locale("en", "US"), new Locale("es", "ES"),
		new Locale("de", "DE")
	};

}