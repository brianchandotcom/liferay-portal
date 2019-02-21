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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CommentUtil;
import com.liferay.headless.web.experience.resource.v1_0.CommentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.message.boards.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.function.Function;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/comment.properties",
	scope = ServiceScope.PROTOTYPE, service = CommentResource.class
)
public class CommentResourceImpl extends BaseCommentResourceImpl {

	@Override
	public boolean deleteComment(Long commentId) throws Exception {
		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkDeletePermission(commentId);

		_commentManager.deleteComment(commentId);

		return true;
	}

	@Override
	public Comment getComment(Long commentId) throws Exception {
		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.fetchComment(commentId);

		_checkViewPermission(comment);

		return CommentUtil.toComment(comment, _portal);
	}

	@Override
	public Page<Comment> getCommentCommentsPage(
			Long commentId, Pagination pagination)
		throws Exception {

		_checkViewPermission(_commentManager.fetchComment(commentId));

		return Page.of(
			transform(
				_commentManager.getChildComments(
					commentId, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				comment -> CommentUtil.toComment(comment, _portal)),
			pagination,
			_commentManager.getChildCommentsCount(
				commentId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public Page<Comment> getStructuredContentCommentsPage(
			Long structuredContentId, Pagination pagination)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		int count = _commentManager.getRootCommentsCount(
			journalArticle.getModelClassName(), structuredContentId,
			WorkflowConstants.STATUS_APPROVED);

		if (count == 0) {
			return Page.of(Collections.emptyList());
		}

		_checkViewPermission(
			journalArticle.getGroupId(), journalArticle.getModelClassName(),
			structuredContentId);

		return Page.of(
			transform(
				_commentManager.getRootComments(
					journalArticle.getModelClassName(), structuredContentId,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				comment -> CommentUtil.toComment(comment, _portal)),
			pagination, count);
	}

	@Override
	public Comment postCommentComment(Long parentCommentId, Comment comment)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment parentComment =
			_commentManager.fetchComment(parentCommentId);

		if (parentComment == null) {
			throw new NotFoundException();
		}

		return _postComment(
			parentComment.getGroupId(), parentComment.getClassPK(),
			() -> _commentManager.addComment(
				_getUserId(), JournalArticle.class.getName(),
				parentComment.getClassPK(), StringPool.BLANK, parentCommentId,
				StringPool.BLANK,
				StringBundler.concat("<p>", comment.getText(), "</p>"),
				_createServiceContextFunction()));
	}

	@Override
	public Comment postStructuredContentComment(
			Long structuredContentId, Comment comment)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _postComment(
			journalArticle.getGroupId(), structuredContentId,
			() -> _commentManager.addComment(
				_getUserId(), journalArticle.getGroupId(),
				journalArticle.getModelClassName(), structuredContentId,
				StringPool.BLANK, StringPool.BLANK,
				StringBundler.concat("<p>", comment.getText(), "</p>"),
				_createServiceContextFunction()));
	}

	@Override
	public Comment putComment(Long commentId, Comment comment)
		throws Exception {

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkUpdatePermission(commentId);

		com.liferay.portal.kernel.comment.Comment existingComment =
			_commentManager.fetchComment(commentId);

		try {
			_commentManager.updateComment(
				existingComment.getUserId(), existingComment.getClassName(),
				existingComment.getClassPK(), commentId, StringPool.BLANK,
				StringBundler.concat("<p>", comment.getText(), "</p>"),
				_createServiceContextFunction());

			return CommentUtil.toComment(
				_commentManager.fetchComment(commentId), _portal);
		}
		catch (MessageSubjectException mse) {
			throw new ClientErrorException("Comment text is null", 422, mse);
		}
	}

	private void _checkViewPermission(
			com.liferay.portal.kernel.comment.Comment comment)
		throws Exception {

		if (comment == null) {
			throw new NotFoundException();
		}

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			JournalArticle.class.getName(), comment.getClassPK());
	}

	private void _checkViewPermission(
			long groupId, String className, long classPK)
		throws Exception {

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), groupId, className, classPK);
	}

	private Function<String, ServiceContext> _createServiceContextFunction() {
		return className -> {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			return serviceContext;
		};
	}

	private DiscussionPermission _getDiscussionPermission() {
		return _commentManager.getDiscussionPermission(
			PermissionThreadLocal.getPermissionChecker());
	}

	private long _getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	private Comment _postComment(
			long groupId, long classPK,
			UnsafeSupplier<Long, ? extends Exception> addCommentUnsafeSupplier)
		throws Exception {

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkAddPermission(
			contextCompany.getCompanyId(), groupId,
			JournalArticle.class.getName(), classPK);

		try {
			long commentId = addCommentUnsafeSupplier.get();

			return CommentUtil.toComment(
				_commentManager.fetchComment(commentId), _portal);
		}
		catch (DiscussionMaxCommentsException dmce) {
			throw new ClientErrorException(
				"Maximum number of comments has been reached", 422, dmce);
		}
		catch (DuplicateCommentException dce) {
			throw new ClientErrorException(
				"A comment with the same text already exists", 409, dce);
		}
		catch (MessageSubjectException mse) {
			throw new ClientErrorException("Comment text is null", 422, mse);
		}
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

}