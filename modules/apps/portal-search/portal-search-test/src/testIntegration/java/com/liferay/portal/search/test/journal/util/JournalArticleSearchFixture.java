/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.test.journal.util;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleSearchFixture {

	public JournalArticle addArticle(
			JournalArticleBlueprint journalArticleBlueprint)
		throws Exception {

		long userId = journalArticleBlueprint.getUserId();
		long groupId = journalArticleBlueprint.getGroupId();
		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		Map<Locale, String> titleMap = journalArticleBlueprint.getTitleMap();
		Map<Locale, String> descriptionMap = null;
		String contentString = journalArticleBlueprint.getContentString();
		String ddmStructureKey = "BASIC-WEB-CONTENT";
		String ddmTemplateKey = "BASIC-WEB-CONTENT";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		if (journalArticleBlueprint.isWorkflowEnabled()) {
			serviceContext.setWorkflowAction(
				journalArticleBlueprint.getWorkflowAction());
		}

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.addArticle(
				userId, groupId, folderId, titleMap, descriptionMap,
				contentString, ddmStructureKey, ddmTemplateKey, serviceContext);

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	public List<JournalArticle> getJournalArticles() {
		return _journalArticles;
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public JournalArticle updateArticle(JournalArticle journalArticle)
		throws Exception {

		journalArticle = JournalArticleLocalServiceUtil.updateArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), journalArticle.getTitleMap(),
			journalArticle.getDescriptionMap(), journalArticle.getContent(),
			journalArticle.getLayoutUuid(),
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	private final List<JournalArticle> _journalArticles = new ArrayList<>();

}