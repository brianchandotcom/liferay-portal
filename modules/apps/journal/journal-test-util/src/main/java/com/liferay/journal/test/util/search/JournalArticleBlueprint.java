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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleBlueprint {

	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	public String getContentString() {
		return _journalArticleContent.getContentString();
	}

	public Map<Locale, String> getDescriptionMap() {
		if (_journalArticleDescription != null) {
			return _journalArticleDescription.getValues();
		}

		return null;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Map<Locale, String> getTitleMap() {
		return _journalArticleTitle.getValues();
	}

	public long getUserId() {
		if (_userId > 0) {
			return _userId;
		}

		try {
			return TestPropsValues.getUserId();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	public int getWorkflowAction() {
		if (_draft) {
			return WorkflowConstants.ACTION_SAVE_DRAFT;
		}

		return WorkflowConstants.ACTION_PUBLISH;
	}

	public boolean isWorkflowEnabled() {
		return _workflowEnabled;
	}

	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setDraft(boolean draft) {
		_draft = draft;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setJournalArticleContent(
		JournalArticleContent journalArticleContent) {

		_journalArticleContent = journalArticleContent;
	}

	public void setJournalArticleDescription(
		JournalArticleDescription journalArticleDescription) {

		_journalArticleDescription = journalArticleDescription;
	}

	public void setJournalArticleTitle(
		JournalArticleTitle journalArticleTitle) {

		_journalArticleTitle = journalArticleTitle;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setWorkflowEnabled(boolean workflowEnabled) {
		_workflowEnabled = workflowEnabled;
	}

	private long[] _assetCategoryIds;
	private boolean _draft;
	private long _groupId;
	private JournalArticleContent _journalArticleContent;
	private JournalArticleDescription _journalArticleDescription;
	private JournalArticleTitle _journalArticleTitle;
	private long _userId;
	private boolean _workflowEnabled;

}