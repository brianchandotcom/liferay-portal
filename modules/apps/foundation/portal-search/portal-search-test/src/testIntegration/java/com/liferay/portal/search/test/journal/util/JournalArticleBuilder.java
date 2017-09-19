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
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleBuilder {

	public JournalArticle addJournalArticle() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_groupId);

		long userId = serviceContext.getUserId();

		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		Map<Locale, String> titleMap = _journalArticleTitle.getValues();
		Map<Locale, String> descriptionMap = null;
		String contentString = _journalArticleContent.getContentString();
		String ddmStructureKey = "BASIC-WEB-CONTENT";
		String ddmTemplateKey = "BASIC-WEB-CONTENT";

		if (_workflowEnabled) {
			serviceContext.setWorkflowAction(_getWorkflowAction());
		}

		return JournalArticleLocalServiceUtil.addArticle(
			userId, _groupId, folderId, titleMap, descriptionMap, contentString,
			ddmStructureKey, ddmTemplateKey, serviceContext);
	}

	public void setContent(JournalArticleContent content) {
		_journalArticleContent = content;
	}

	public void setDraft(boolean draft) {
		_draft = draft;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setTitle(JournalArticleTitle title) {
		_journalArticleTitle = title;
	}

	public void setWorkflowEnabled(boolean withWorkflow) {
		_workflowEnabled = withWorkflow;
	}

	private int _getWorkflowAction() {
		if (_draft) {
			return WorkflowConstants.ACTION_SAVE_DRAFT;
		}

		return WorkflowConstants.ACTION_PUBLISH;
	}

	private boolean _draft;
	private long _groupId;
	private JournalArticleContent _journalArticleContent;
	private JournalArticleTitle _journalArticleTitle;
	private boolean _workflowEnabled;

}