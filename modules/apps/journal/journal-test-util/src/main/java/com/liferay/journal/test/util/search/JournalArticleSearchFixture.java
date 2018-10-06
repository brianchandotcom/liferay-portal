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

package com.liferay.journal.test.util.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
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

	public JournalArticleSearchFixture(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	public JournalArticle addArticle(
		JournalArticleBlueprint journalArticleBlueprint) {

		long userId = journalArticleBlueprint.getUserId();
		long groupId = journalArticleBlueprint.getGroupId();
		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		Map<Locale, String> titleMap = journalArticleBlueprint.getTitleMap();
		Map<Locale, String> descriptionMap = null;
		String contentString = journalArticleBlueprint.getContentString();
		String ddmStructureKey = "BASIC-WEB-CONTENT";
		String ddmTemplateKey = "BASIC-WEB-CONTENT";

		ServiceContext serviceContext = getServiceContext(groupId, userId);

		serviceContext.setAssetCategoryIds(
			journalArticleBlueprint.getAssetCategoryIds());

		if (journalArticleBlueprint.isWorkflowEnabled()) {
			serviceContext.setWorkflowAction(
				journalArticleBlueprint.getWorkflowAction());
		}

		JournalArticle journalArticle = addArticle(
			userId, groupId, folderId, titleMap, descriptionMap, contentString,
			ddmStructureKey, ddmTemplateKey, serviceContext);

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

		journalArticle = _journalArticleLocalService.updateArticle(
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

	protected JournalArticle addArticle(
		long userId, long groupId, long folderId, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap, String contentString,
		String ddmStructureKey, String ddmTemplateKey,
		ServiceContext serviceContext) {

		try {
			return _journalArticleLocalService.addArticle(
				userId, groupId, folderId, titleMap, descriptionMap,
				contentString, ddmStructureKey, ddmTemplateKey, serviceContext);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected ServiceContext getServiceContext(long groupId, long userId) {
		try {
			return ServiceContextTestUtil.getServiceContext(groupId, userId);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private final JournalArticleLocalService _journalArticleLocalService;
	private final List<JournalArticle> _journalArticles = new ArrayList<>();

}