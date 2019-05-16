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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBCommentServiceUtil;
import com.liferay.knowledge.base.web.internal.KBUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
public class KBSuggestionListDisplayContext {

	public KBSuggestionListDisplayContext(
		HttpServletRequest httpServletRequest, String templatePath,
		KBArticle kbArticle) {

		_httpServletRequest = httpServletRequest;
		_templatePath = templatePath;
		_kbArticle = kbArticle;

		_groupId = kbArticle.getGroupId();
		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");
	}

	public KBSuggestionListDisplayContext(
		HttpServletRequest httpServletRequest, String templatePath,
		long groupId) {

		_httpServletRequest = httpServletRequest;
		_templatePath = templatePath;
		_groupId = groupId;

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");
	}

	public int getCompletedKBCommentsCount() throws PortalException {
		return getKBCommentsCount(KBCommentConstants.STATUS_COMPLETED);
	}

	public String getEmptyResultsMessage() {
		if (_navigation.equals("new")) {
			return "there-are-no-new-suggestions";
		}
		else if (_navigation.equals("in-progress")) {
			return "there-are-no-suggestions-in-progress";
		}
		else if (_navigation.equals("resolved")) {
			return "there-are-no-resolved-suggestions";
		}

		return "there-are-no-suggestions";
	}

	public long getGroupId() {
		return _groupId;
	}

	public int getInProgressKBCommentsCount() throws PortalException {
		return getKBCommentsCount(KBCommentConstants.STATUS_IN_PROGRESS);
	}

	public int getKBCommentsCount(int status) throws PortalException {
		if (_kbArticle == null) {
			return KBCommentServiceUtil.getKBCommentsCount(_groupId, status);
		}

		return KBCommentServiceUtil.getKBCommentsCount(
			_groupId, KBArticleConstants.getClassName(),
			_kbArticle.getResourcePrimKey(), status);
	}

	public int getNewKBCommentsCount() throws PortalException {
		return getKBCommentsCount(KBCommentConstants.STATUS_NEW);
	}

	public boolean isShowKBArticleTitle() {
		if (_kbArticle == null) {
			return true;
		}

		return false;
	}

	public void populateResultsAndTotal(
			SearchContainer<KBComment> searchContainer)
		throws PortalException {

		int status = _getStatus();

		if (_kbArticle == null) {
			if (status == KBCommentConstants.STATUS_ANY) {
				searchContainer.setTotal(
					KBCommentServiceUtil.getKBCommentsCount(_groupId));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, searchContainer.getStart(),
						searchContainer.getEnd(),
						KBUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
			else {
				searchContainer.setTotal(getKBCommentsCount(status));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, status, searchContainer.getStart(),
						searchContainer.getEnd(),
						KBUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
		}
		else {
			if (status == KBCommentConstants.STATUS_ANY) {
				searchContainer.setTotal(
					KBCommentServiceUtil.getKBCommentsCount(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getResourcePrimKey()));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getResourcePrimKey(),
						searchContainer.getStart(), searchContainer.getEnd(),
						KBUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
			else {
				searchContainer.setTotal(getKBCommentsCount(status));

				searchContainer.setResults(
					KBCommentServiceUtil.getKBComments(
						_groupId, KBArticleConstants.getClassName(),
						_kbArticle.getResourcePrimKey(), status,
						searchContainer.getStart(), searchContainer.getEnd(),
						KBUtil.getKBCommentOrderByComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())));
			}
		}
	}

	private int _getStatus() {
		if (_navigation.equals("new")) {
			return KBCommentConstants.STATUS_NEW;
		}
		else if (_navigation.equals("in-progress")) {
			return KBCommentConstants.STATUS_IN_PROGRESS;
		}
		else if (_navigation.equals("resolved")) {
			return KBCommentConstants.STATUS_COMPLETED;
		}

		return KBCommentConstants.STATUS_ANY;
	}

	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private KBArticle _kbArticle;
	private final String _navigation;
	private final String _templatePath;

}