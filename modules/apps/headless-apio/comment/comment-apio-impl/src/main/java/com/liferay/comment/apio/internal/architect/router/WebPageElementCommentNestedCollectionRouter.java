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

package com.liferay.comment.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.comment.apio.internal.architect.router.base.BaseCommentNestedCollectionRouter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.web.page.element.apio.architect.identifier.WebPageElementIdentifier;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Comment">Comment</a> resources contained inside a <a
 * href="http://schema.org/WebPageElement">WebPageElement</a> through a web API.
 * The resources are mapped from the internal model {@link Comment} and {@code
 * JournalArticle}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class WebPageElementCommentNestedCollectionRouter extends
	BaseCommentNestedCollectionRouter<WebPageElementIdentifier>
	implements NestedCollectionRouter
		<Comment, Long, CommentIdentifier, Long, WebPageElementIdentifier> {

	@Override
	protected PageItems<Comment> getPageItems(
			Pagination pagination, long journalArticleId,
			PermissionChecker permissionChecker)
		throws PortalException {

		GroupedModel groupedModel = getGroupedModel(journalArticleId);

		long resourcePrimKey =
			((JournalArticle)groupedModel).getResourcePrimKey();

		int count = _commentManager.getRootCommentsCount(
			groupedModel.getModelClassName(), resourcePrimKey,
			WorkflowConstants.STATUS_APPROVED);

		if (count == 0) {
			return new PageItems<>(Collections.emptyList(), 0);
		}

		checkViewPermission(
			permissionChecker, groupedModel.getGroupId(),
			groupedModel.getModelClassName(), journalArticleId);

		List<Comment> comments = _commentManager.getRootComments(
			groupedModel.getModelClassName(), resourcePrimKey,
			WorkflowConstants.STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition());

		return new PageItems<>(comments, count);
	}

	@Override
	protected CommentManager getCommentManager() {
		return _commentManager;
	}

	@Override
	protected GroupedModel getGroupedModel(long journalArticleId)
		throws PortalException {

		return _journalArticleService.getArticle(journalArticleId);
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private JournalArticleService _journalArticleService;

}