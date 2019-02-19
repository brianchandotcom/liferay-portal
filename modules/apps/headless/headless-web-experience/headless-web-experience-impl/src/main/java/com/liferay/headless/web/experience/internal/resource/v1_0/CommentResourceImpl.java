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
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.CommentEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.CommentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.message.boards.exception.DiscussionMaxCommentsException;
import com.liferay.message.boards.exception.MessageSubjectException;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

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
	scope = ServiceScope.PROTOTYPE, service = CommentResource.class
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
	public Comment getComment(Long commentId) throws Exception {
		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.fetchComment(commentId);

		_checkViewPermission(comment);

		return CommentUtil.toComment(comment, _portal);
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
	public Page<Comment> getStructuredContentCommentsPage(
			Long structuredContentId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		Discussion discussion = _commentManager.getDiscussion(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			JournalArticle.class.getName(), structuredContentId, null);

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return _getComments(
			rootDiscussionComment.getCommentId(), filter, pagination, sorts);
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

	private Page<Comment> _getComments(
			Long parentCommentId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws SearchException {

		List<com.liferay.portal.kernel.comment.Comment> comments =
			new ArrayList<>();

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(MBMessage.class),
			pagination,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"parentMessageId", String.valueOf(parentCommentId)),
					BooleanClauseOccur.MUST);
			},
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.CLASS_PK);
			},
			searchContext -> {
				searchContext.setAttribute("discussion", Boolean.TRUE);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
			},
			_searchResultPermissionFilterFactory, sorts);

		for (Document document : hits.getDocs()) {
			com.liferay.portal.kernel.comment.Comment comment =
				_commentManager.fetchComment(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

			comments.add(comment);
		}

		return Page.of(
			transform(
				comments, comment -> CommentUtil.toComment(comment, _portal)),
			pagination, comments.size());
	}

	private static final EntityModel _entityModel = new CommentEntityModel();

	@Reference
	private CommentManager _commentManager;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}