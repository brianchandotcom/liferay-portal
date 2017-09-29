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

import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleBlueprint {

	public String getContentString() {
		return journalArticleContent.getContentString();
	}

	public long getGroupId() {
		return groupId;
	}

	public Map<Locale, String> getTitleMap() {
		return journalArticleTitle.getValues();
	}

	public long getUserId() throws Exception {
		if (userId > 0) {
			return userId;
		}

		return TestPropsValues.getUserId();
	}

	public int getWorkflowAction() {
		if (draft) {
			return WorkflowConstants.ACTION_SAVE_DRAFT;
		}

		return WorkflowConstants.ACTION_PUBLISH;
	}

	public boolean isWorkflowEnabled() {
		return workflowEnabled;
	}

	protected boolean draft;
	protected long groupId;
	protected JournalArticleContent journalArticleContent;
	protected JournalArticleTitle journalArticleTitle;
	protected long userId;
	protected boolean workflowEnabled;

}