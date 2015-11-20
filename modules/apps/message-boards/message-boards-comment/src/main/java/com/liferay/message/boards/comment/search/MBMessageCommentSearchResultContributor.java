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

package com.liferay.message.boards.comment.search;

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchResultContributor.class)
public class MBMessageCommentSearchResultContributor
	implements SearchResultContributor {

	@Override
	public void addRelatedModel(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		MBMessage mbMessage = _mbMessageLocalService.getMessage(entryClassPK);

		Comment comment = _commentManager.fetchComment(
			mbMessage.getMessageId());

		Summary summary = new Summary(null, mbMessage.getBody());

		summary.setEscape(false);

		searchResult.addComment(comment, summary);
	}

	@Override
	public String getEntryClassName() {
		return MBMessage.class.getName();
	}

	@Reference(unbind = "-")
	public void setCommentManager(CommentManager commentManager) {
		_commentManager = commentManager;
	}

	@Reference(unbind = "-")
	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private volatile CommentManager _commentManager;
	private volatile MBMessageLocalService _mbMessageLocalService;

}