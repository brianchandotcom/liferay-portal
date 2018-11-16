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

package com.liferay.knowledge.base.web.internal.application.dao.search;

import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.web.internal.display.context.KBSuggestionListDisplayContext;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Sergio González
 */
public class KBCommentResultRowSplitter implements ResultRowSplitter {

	public KBCommentResultRowSplitter(
		KBSuggestionListDisplayContext kbSuggestionListDisplayContext,
		ResourceBundle resourceBundle) {

		this(kbSuggestionListDisplayContext, resourceBundle, "desc");
	}

	public KBCommentResultRowSplitter(
		KBSuggestionListDisplayContext kbSuggestionListDisplayContext,
		ResourceBundle resourceBundle, String orderByType) {

		_kbSuggestionListDisplayContext = kbSuggestionListDisplayContext;
		_resourceBundle = resourceBundle;
		_orderByType = orderByType;
	}

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> newResultRows = new ArrayList<>();
		List<ResultRow> inProgressResultRows = new ArrayList<>();
		List<ResultRow> completedResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			KBComment kbComment = (KBComment)resultRow.getObject();

			if (kbComment.getStatus() == KBCommentConstants.STATUS_NEW) {
				newResultRows.add(resultRow);
			}
			else if (kbComment.getStatus() ==
						KBCommentConstants.STATUS_IN_PROGRESS) {

				inProgressResultRows.add(resultRow);
			}
			else {
				completedResultRows.add(resultRow);
			}
		}

		if (!newResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					getNewKBCommentsLabel(), newResultRows));
		}

		if (!inProgressResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					getInProgressKBCommentsLabel(), inProgressResultRows));
		}

		if (!completedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					getCompletedKBCommentsLabel(), completedResultRows));
		}

		if (_orderByType.equals("asc")) {
			Collections.reverse(resultRowSplitterEntries);
		}

		return resultRowSplitterEntries;
	}

	protected String getCompletedKBCommentsLabel() {
		int completedKBCommentsCount = 0;

		try {
			completedKBCommentsCount =
				_kbSuggestionListDisplayContext.getCompletedKBCommentsCount();
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to obtain completed knowledge base comments count " +
					"for group " + _kbSuggestionListDisplayContext.getGroupId(),
				pe);
		}

		return String.format(
			"%s (%s)", LanguageUtil.get(_resourceBundle, "resolved"),
			completedKBCommentsCount);
	}

	protected String getInProgressKBCommentsLabel() {
		int inProgressKBCommentsCount = 0;

		try {
			inProgressKBCommentsCount =
				_kbSuggestionListDisplayContext.getInProgressKBCommentsCount();
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to obtain in progress knowledge base comments count " +
					"for  group " +
						_kbSuggestionListDisplayContext.getGroupId(),
				pe);
		}

		return String.format(
			"%s (%s)", LanguageUtil.get(_resourceBundle, "in-progress"),
			inProgressKBCommentsCount);
	}

	protected String getNewKBCommentsLabel() {
		int newKBCommentsCount = 0;

		try {
			newKBCommentsCount =
				_kbSuggestionListDisplayContext.getNewKBCommentsCount();
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to obtain new knowledge base comments count for " +
					"group " + _kbSuggestionListDisplayContext.getGroupId(),
				pe);
		}

		return String.format(
			"%s (%s)", LanguageUtil.get(_resourceBundle, "new"),
			newKBCommentsCount);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBCommentResultRowSplitter.class);

	private final KBSuggestionListDisplayContext
		_kbSuggestionListDisplayContext;
	private final String _orderByType;
	private final ResourceBundle _resourceBundle;

}