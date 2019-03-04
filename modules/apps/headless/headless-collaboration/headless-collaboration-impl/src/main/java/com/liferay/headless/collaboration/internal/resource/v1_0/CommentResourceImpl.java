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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.headless.collaboration.dto.v1_0.Comment;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CommentUtil;
import com.liferay.headless.collaboration.internal.odata.entity.v1_0.CommentEntityModel;
import com.liferay.headless.collaboration.resource.v1_0.CommentResource;
import com.liferay.message.boards.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.function.Function;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/comment.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {CommentResource.class, EntityModelResource.class}
)
public class CommentResourceImpl
	extends BaseCommentResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteComment(Long commentId) throws Exception {
		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkDeletePermission(commentId);

		_commentManager.deleteComment(commentId);

		return true;
	}

	@Override
	public Page<Comment> getBlogPostingCommentsPage(
			Long blogPostingId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		Discussion discussion = _commentManager.getDiscussion(
			blogsEntry.getUserId(), blogsEntry.getGroupId(),
			BlogsEntry.class.getName(), blogPostingId,
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return _getComments(
			rootDiscussionComment.getCommentId(), filter, pagination, sorts);
	}

	@Override
	public Comment getComment(Long commentId) throws Exception {
		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.fetchComment(commentId);

		_checkViewPermission(comment);

		return CommentUtil.toComment(comment, _commentManager, _portal);
	}

	@Override
	public Page<Comment> getCommentCommentsPage(
			Long commentId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getComments(commentId, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Comment postBlogPostingComment(Long blogPostingId, Comment comment)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _postComment(
			blogsEntry.getGroupId(), blogPostingId,
			() -> _commentManager.addComment(
				_getUserId(), blogsEntry.getGroupId(),
				blogsEntry.getModelClassName(), blogPostingId, StringPool.BLANK,
				StringPool.BLANK,
				StringBundler.concat("<p>", comment.getText(), "</p>"),
				_createServiceContextFunction()));
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
				_getUserId(), BlogsEntry.class.getName(),
				parentComment.getClassPK(), StringPool.BLANK, parentCommentId,
				StringPool.BLANK,
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
				_commentManager.fetchComment(commentId), _commentManager,
				_portal);
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
			BlogsEntry.class.getName(), comment.getClassPK());
	}

	private Function<String, ServiceContext> _createServiceContextFunction() {
		return className -> {
			return new ServiceContext() {
				{
					setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
				}
			};
		};
	}

	private Page<Comment> _getComments(
			Long parentCommentId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"parentMessageId", String.valueOf(parentCommentId)),
					BooleanClauseOccur.MUST);
			},
			filter, MBMessage.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("discussion", Boolean.TRUE);
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			document -> CommentUtil.toComment(
				_commentManager.fetchComment(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
				_commentManager, _portal),
			sorts);
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
			contextCompany.getCompanyId(), groupId, BlogsEntry.class.getName(),
			classPK);

		try {
			long commentId = addCommentUnsafeSupplier.get();

			return CommentUtil.toComment(
				_commentManager.fetchComment(commentId), _commentManager,
				_portal);
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

	private static final EntityModel _entityModel = new CommentEntityModel();

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private Portal _portal;

}