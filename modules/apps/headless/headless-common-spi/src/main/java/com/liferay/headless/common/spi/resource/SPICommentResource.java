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

package com.liferay.headless.common.spi.resource;

import com.liferay.headless.common.spi.odata.entity.CommentEntityModel;
import com.liferay.message.boards.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.Company;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Brian Wing Shun Chan
 */
public class SPICommentResource<T> {

	public SPICommentResource(
		CommentManager commentManager, Company company,
		UnsafeFunction<Comment, T, Exception> transformUnsafeFunction) {

		_commentManager = commentManager;
		_company = company;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public void deleteComment(Long commentId) throws Exception {
		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkDeletePermission(commentId);

		_commentManager.deleteComment(commentId);
	}

	public T getComment(Long commentId) throws Exception {
		Comment comment = _commentManager.fetchComment(commentId);

		if (comment == null) {
			throw new NoSuchModelException(
				"No comment exists with comment ID " + commentId);
		}

		_checkViewPermission(comment);

		return _transformUnsafeFunction.apply(comment);
	}

	public Page<T> getCommentCommentsPage(
			Long commentId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getComments(
			Collections.emptyMap(), commentId, search, filter, pagination,
			sorts);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getEntityCommentsPage(Map, long, String, long, String,
	 *             Filter, Pagination, Sort[])}
	 */
	@Deprecated
	public Page<T> getEntityCommentsPage(
			long groupId, String className, long classPK, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return getEntityCommentsPage(
			Collections.emptyMap(), groupId, className, classPK, search, filter,
			pagination, sorts);
	}

	public Page<T> getEntityCommentsPage(
			Map<String, Map<String, String>> actions, long groupId,
			String className, long classPK, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Discussion discussion = _commentManager.getDiscussion(
			_getUserId(), groupId, className, classPK,
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return _getComments(
			actions, rootDiscussionComment.getCommentId(), search, filter,
			pagination, sorts);
	}

	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	public T postCommentComment(Comment parentComment, String text)
		throws Exception {

		return _postComment(
			() -> _commentManager.addComment(
				_getUserId(), parentComment.getClassName(),
				parentComment.getClassPK(), StringPool.BLANK,
				parentComment.getCommentId(), StringPool.BLANK,
				StringBundler.concat("<p>", text, "</p>"),
				_createServiceContextFunction()),
			parentComment.getGroupId(), parentComment.getClassName(),
			parentComment.getClassPK());
	}

	public T postEntityComment(
			long groupId, String className, long classPK, String text)
		throws Exception {

		return _postComment(
			() -> _commentManager.addComment(
				_getUserId(), groupId, className, classPK, StringPool.BLANK,
				StringPool.BLANK, StringBundler.concat("<p>", text, "</p>"),
				_createServiceContextFunction()),
			groupId, className, classPK);
	}

	public T putComment(Long commentId, String text) throws Exception {
		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkUpdatePermission(commentId);

		Comment existingComment = _commentManager.fetchComment(commentId);

		try {
			_commentManager.updateComment(
				existingComment.getUserId(), existingComment.getClassName(),
				existingComment.getClassPK(), commentId, StringPool.BLANK,
				StringBundler.concat("<p>", text, "</p>"),
				_createServiceContextFunction());

			return _transformUnsafeFunction.apply(
				_commentManager.fetchComment(commentId));
		}
		catch (MessageSubjectException messageSubjectException) {
			throw new ClientErrorException(
				"Comment text is null", 422, messageSubjectException);
		}
	}

	private void _checkViewPermission(Comment comment) throws Exception {
		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			_company.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());
	}

	private Function<String, ServiceContext> _createServiceContextFunction() {
		return className -> {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			return serviceContext;
		};
	}

	private Page<T> _getComments(
			Map<String, Map<String, String>> actions, Long commentId,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"parentMessageId", String.valueOf(commentId)),
					BooleanClauseOccur.MUST);
			},
			filter, MBMessage.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("discussion", Boolean.TRUE);
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(_company.getCompanyId());
			},
			sorts,
			document -> _transformUnsafeFunction.apply(
				_commentManager.fetchComment(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
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

	private T _postComment(
			UnsafeSupplier<Long, ? extends Exception> addCommentUnsafeSupplier,
			long groupId, String className, long classPK)
		throws Exception {

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkAddPermission(
			_company.getCompanyId(), groupId, className, classPK);

		try {
			long commentId = addCommentUnsafeSupplier.get();

			return _transformUnsafeFunction.apply(
				_commentManager.fetchComment(commentId));
		}
		catch (DiscussionMaxCommentsException discussionMaxCommentsException) {
			throw new ClientErrorException(
				"Maximum number of comments has been reached", 422,
				discussionMaxCommentsException);
		}
		catch (DuplicateCommentException duplicateCommentException) {
			throw new ClientErrorException(
				"A comment with the same text already exists", 409,
				duplicateCommentException);
		}
		catch (MessageSubjectException messageSubjectException) {
			throw new ClientErrorException(
				"Comment text is null", 422, messageSubjectException);
		}
	}

	private static final EntityModel _entityModel = new CommentEntityModel();

	private final CommentManager _commentManager;
	private final Company _company;
	private final UnsafeFunction<Comment, T, Exception>
		_transformUnsafeFunction;

}