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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.headless.common.spi.odata.entity.CommentEntityModel;
import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CommentUtil;
import com.liferay.headless.delivery.resource.v1_0.CommentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.knowledge.base.exception.NoSuchCommentException;
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
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Map;
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
	scope = ServiceScope.PROTOTYPE, service = CommentResource.class
)
public class CommentResourceImpl extends BaseCommentResourceImpl {

	@Override
	public void deleteComment(Long commentId) throws Exception {
		_deleteComment(commentId);
	}

	@Override
	public void
			deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String blogPostingExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		BlogsEntry blogsEntry = _getBlogsEntry(
			blogPostingExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		_deleteComment(comment.getCommentId());
	}

	@Override
	public void
			deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String parentCommentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, parentCommentExternalReferenceCode, siteId);

		_deleteComment(comment.getCommentId());
	}

	@Override
	public void
			deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String documentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		DLFileEntry dlFileEntry = _getDLFileEntry(
			documentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, DLFileEntry.class.getName(),
			dlFileEntry.getFileEntryId());

		_deleteComment(comment.getCommentId());
	}

	@Override
	public void
			deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String structuredContentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		JournalArticle journalArticle = _getLatestJournalArticle(
			structuredContentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		_deleteComment(comment.getCommentId());
	}

	@Override
	public Page<Comment> getBlogPostingCommentsPage(
			Long blogPostingId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		Discussion discussion = _commentManager.getDiscussion(
			blogsEntry.getUserId(), blogsEntry.getGroupId(),
			BlogsEntry.class.getName(), blogPostingId,
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return _getComments(
			HashMapBuilder.put(
				"add-discussion",
				addAction(
					ActionKeys.ADD_DISCUSSION, blogPostingId,
					"postBlogPostingComment", blogsEntry.getUserId(),
					BlogsEntry.class.getName(), blogsEntry.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, blogPostingId,
					"getBlogPostingCommentsPage", blogsEntry.getUserId(),
					BlogsEntry.class.getName(), blogsEntry.getGroupId())
			).build(),
			rootDiscussionComment.getCommentId(), search, aggregation, filter,
			pagination, sorts);
	}

	@Override
	public Comment getComment(Long commentId) throws Exception {
		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.fetchComment(commentId);

		if (comment == null) {
			throw new NoSuchModelException(
				"No comment exists with comment ID " + commentId);
		}

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());

		return CommentUtil.toComment(comment, _commentManager, _portal);
	}

	@Override
	public Page<Comment> getCommentCommentsPage(
			Long parentCommentId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getComments(
			Collections.emptyMap(), parentCommentId, search, aggregation,
			filter, pagination, sorts);
	}

	@Override
	public Page<Comment> getDocumentCommentsPage(
			Long documentId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		DLFileEntry dlFileEntry = _dlFileEntryService.getFileEntry(documentId);

		Discussion discussion = _commentManager.getDiscussion(
			dlFileEntry.getUserId(), dlFileEntry.getGroupId(),
			DLFileEntry.class.getName(), documentId,
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return _getComments(
			HashMapBuilder.put(
				"add-discussion",
				addAction(
					ActionKeys.ADD_DISCUSSION, documentId,
					"postDocumentComment", dlFileEntry.getUserId(),
					DLFileEntry.class.getName(), dlFileEntry.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, documentId, "getDocumentCommentsPage",
					dlFileEntry.getUserId(), DLFileEntry.class.getName(),
					dlFileEntry.getGroupId())
			).build(),
			rootDiscussionComment.getCommentId(), search, aggregation, filter,
			pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new CommentEntityModel();
	}

	@Override
	public Comment
			getSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String blogPostingExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		BlogsEntry blogsEntry = _getBlogsEntry(
			blogPostingExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());

		return CommentUtil.toComment(comment, _commentManager, _portal);
	}

	@Override
	public Comment
			getSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String parentCommentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, parentCommentExternalReferenceCode, siteId);

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());

		return CommentUtil.toComment(comment, _commentManager, _portal);
	}

	@Override
	public Comment
			getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String documentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		DLFileEntry dlFileEntry = _getDLFileEntry(
			documentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, DLFileEntry.class.getName(),
			dlFileEntry.getFileEntryId());

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());

		return CommentUtil.toComment(comment, _commentManager, _portal);
	}

	@Override
	public Comment
			getSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String structuredContentExternalReferenceCode,
				String externalReferenceCode)
		throws Exception {

		JournalArticle journalArticle = _getLatestJournalArticle(
			structuredContentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());

		return CommentUtil.toComment(comment, _commentManager, _portal);
	}

	@Override
	public Page<Comment> getStructuredContentCommentsPage(
			Long structuredContentId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		Discussion discussion = _commentManager.getDiscussion(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			JournalArticle.class.getName(), structuredContentId,
			_createServiceContextFunction());

		DiscussionComment rootDiscussionComment =
			discussion.getRootDiscussionComment();

		return _getComments(
			HashMapBuilder.put(
				"add-discussion",
				addAction(
					ActionKeys.ADD_DISCUSSION, structuredContentId,
					"postStructuredContentComment", journalArticle.getUserId(),
					JournalArticle.class.getName(), journalArticle.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, structuredContentId,
					"getStructuredContentCommentsPage",
					journalArticle.getUserId(), JournalArticle.class.getName(),
					journalArticle.getGroupId())
			).build(),
			rootDiscussionComment.getCommentId(), search, aggregation, filter,
			pagination, sorts);
	}

	@Override
	public Comment postBlogPostingComment(Long blogPostingId, Comment comment)
		throws Exception {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _postEntityComment(
			comment.getExternalReferenceCode(), blogsEntry.getGroupId(),
			BlogsEntry.class.getName(), blogPostingId, comment.getText());
	}

	@Override
	public Comment postCommentComment(Long parentCommentId, Comment comment)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment parentComment =
			_commentManager.fetchComment(parentCommentId);

		if (parentComment == null) {
			throw new NotFoundException();
		}

		return _postParentCommentComment(
			comment.getExternalReferenceCode(), parentComment.getGroupId(),
			parentComment.getCommentId(), parentComment.getClassName(),
			parentComment.getClassPK(), comment.getText());
	}

	@Override
	public Comment postDocumentComment(Long documentId, Comment comment)
		throws Exception {

		DLFileEntry fileEntry = _dlFileEntryService.getFileEntry(documentId);

		return _postEntityComment(
			comment.getExternalReferenceCode(), fileEntry.getGroupId(),
			DLFileEntry.class.getName(), documentId, comment.getText());
	}

	@Override
	public Comment postStructuredContentComment(
			Long structuredContentId, Comment comment)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _postEntityComment(
			comment.getExternalReferenceCode(), journalArticle.getGroupId(),
			JournalArticle.class.getName(), structuredContentId,
			comment.getText());
	}

	@Override
	public Comment putComment(Long commentId, Comment comment)
		throws Exception {

		return _updateComment(
			_commentManager.fetchComment(commentId), commentId,
			comment.getText());
	}

	@Override
	public Comment
			putSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String blogPostingExternalReferenceCode,
				String externalReferenceCode, Comment comment)
		throws Exception {

		BlogsEntry blogsEntry = _getBlogsEntry(
			blogPostingExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment existingComment =
			_fetchComment(
				externalReferenceCode, siteId, BlogsEntry.class.getName(),
				blogsEntry.getEntryId());

		if (existingComment != null) {
			return _updateComment(
				existingComment, existingComment.getCommentId(),
				comment.getText());
		}

		return _postEntityComment(
			externalReferenceCode, blogsEntry.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			comment.getText());
	}

	@Override
	public Comment
			putSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String parentCommentExternalReferenceCode,
				String externalReferenceCode, Comment comment)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment parentComment = _getComment(
			parentCommentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment existingComment =
			_fetchComment(
				externalReferenceCode, siteId, parentComment.getClassName(),
				parentComment.getClassPK());

		if ((existingComment != null) &&
			(parentComment.getCommentId() ==
				existingComment.getParentCommentId())) {

			return _updateComment(
				existingComment, existingComment.getCommentId(),
				comment.getText());
		}

		return _postParentCommentComment(
			externalReferenceCode, parentComment.getGroupId(),
			parentComment.getCommentId(), parentComment.getClassName(),
			parentComment.getClassPK(), comment.getText());
	}

	@Override
	public Comment
			putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String documentExternalReferenceCode,
				String externalReferenceCode, Comment comment)
		throws Exception {

		DLFileEntry dlFileEntry = _getDLFileEntry(
			documentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment existingComment =
			_fetchComment(
				externalReferenceCode, siteId, DLFileEntry.class.getName(),
				dlFileEntry.getFileEntryId());

		if (existingComment != null) {
			return _updateComment(
				existingComment, existingComment.getCommentId(),
				comment.getText());
		}

		return _postEntityComment(
			externalReferenceCode, dlFileEntry.getGroupId(),
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId(),
			comment.getText());
	}

	@Override
	public Comment
			putSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(
				Long siteId, String structuredContentExternalReferenceCode,
				String externalReferenceCode, Comment comment)
		throws Exception {

		JournalArticle journalArticle = _getLatestJournalArticle(
			structuredContentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment existingComment =
			_fetchComment(
				externalReferenceCode, siteId, JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey());

		if (existingComment != null) {
			return _updateComment(
				existingComment, existingComment.getCommentId(),
				comment.getText());
		}

		return _postEntityComment(
			externalReferenceCode, journalArticle.getGroupId(),
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			comment.getText());
	}

	private Function<String, ServiceContext> _createServiceContextFunction() {
		return className -> {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			return serviceContext;
		};
	}

	private void _deleteComment(Long commentId) throws Exception {
		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkDeletePermission(commentId);

		_commentManager.deleteComment(commentId);
	}

	private com.liferay.portal.kernel.comment.Comment _fetchComment(
			String externalReferenceCode, long siteId, String className,
			long classPK)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.fetchComment(siteId, externalReferenceCode);

		if ((comment != null) && _isAssociated(className, classPK, comment)) {
			return comment;
		}

		return null;
	}

	private BlogsEntry _getBlogsEntry(String externalReferenceCode, Long siteId)
		throws Exception {

		BlogsEntry blogsEntry =
			_blogsEntryService.fetchBlogsEntryByExternalReferenceCode(
				siteId, externalReferenceCode);

		if (blogsEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append("No blog posting exists with external reference code ");
			sb.append(externalReferenceCode);
			sb.append(" and site ID ");
			sb.append(siteId);

			throw new NotFoundException(sb.toString());
		}

		return blogsEntry;
	}

	private com.liferay.portal.kernel.comment.Comment _getComment(
			String externalReferenceCode, long siteId, String className,
			long classPK)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.getComment(siteId, externalReferenceCode);

		if (!_isAssociated(className, classPK, comment)) {
			StringBundler sb = new StringBundler(5);

			sb.append("A comment with external reference code ");
			sb.append(externalReferenceCode);
			sb.append(" and site ID ");
			sb.append(siteId);
			sb.append(" is associated to another entity");

			throw new NoSuchCommentException(sb.toString());
		}

		return comment;
	}

	private com.liferay.portal.kernel.comment.Comment _getComment(
			String externalReferenceCode, Long siteId)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment comment =
			_commentManager.fetchComment(siteId, externalReferenceCode);

		if (comment == null) {
			StringBundler sb = new StringBundler(4);

			sb.append("No comment exists with external reference code ");
			sb.append(externalReferenceCode);
			sb.append(" and site ID ");
			sb.append(siteId);

			throw new NotFoundException(sb.toString());
		}

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkViewPermission(
			contextCompany.getCompanyId(), comment.getGroupId(),
			comment.getClassName(), comment.getClassPK());

		return comment;
	}

	private com.liferay.portal.kernel.comment.Comment _getComment(
			String externalReferenceCode, String parentExternalReferenceCode,
			Long siteId)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment parentComment = _getComment(
			parentExternalReferenceCode, siteId);

		com.liferay.portal.kernel.comment.Comment comment = _getComment(
			externalReferenceCode, siteId, parentComment.getClassName(),
			parentComment.getClassPK());

		if (parentComment.getCommentId() != comment.getParentCommentId()) {
			StringBundler sb = new StringBundler(6);

			sb.append("No comment exists with external reference code ");
			sb.append(externalReferenceCode);
			sb.append(", site ID ");
			sb.append(parentComment.getGroupId());
			sb.append(", and parent comment with external reference code ");
			sb.append(parentExternalReferenceCode);

			throw new NotFoundException(sb.toString());
		}

		return comment;
	}

	private Page<Comment> _getComments(
			Map<String, Map<String, String>> actions, Long commentId,
			String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
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
			filter, MBMessage.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute("discussion", Boolean.TRUE);
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setVulcanCheckPermissions(false);
			},
			sorts,
			document -> CommentUtil.toComment(
				_commentManager.fetchComment(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
				_commentManager, _portal));
	}

	private DiscussionPermission _getDiscussionPermission() {
		return _commentManager.getDiscussionPermission(
			PermissionThreadLocal.getPermissionChecker());
	}

	private DLFileEntry _getDLFileEntry(
			String externalReferenceCode, Long siteId)
		throws Exception {

		DLFileEntry dlFileEntry =
			_dlFileEntryService.fetchFileEntryByExternalReferenceCode(
				siteId, externalReferenceCode);

		if (dlFileEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append("No document exists with external reference code ");
			sb.append(externalReferenceCode);
			sb.append(" and site ID ");
			sb.append(siteId);

			throw new NotFoundException(sb.toString());
		}

		return dlFileEntry;
	}

	private JournalArticle _getLatestJournalArticle(
			String externalReferenceCode, Long siteId)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleService.fetchLatestArticleByExternalReferenceCode(
				siteId, externalReferenceCode);

		if (journalArticle == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(
				"No structured content exists with external reference code ");
			sb.append(externalReferenceCode);
			sb.append(" and site ID ");
			sb.append(siteId);

			throw new NotFoundException(sb.toString());
		}

		return journalArticle;
	}

	private long _getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	private boolean _isAssociated(
		String className, long classPK,
		com.liferay.portal.kernel.comment.Comment comment) {

		if (className.equals(comment.getClassName()) &&
			(classPK == comment.getClassPK())) {

			return true;
		}

		return false;
	}

	private Comment _postComment(
			UnsafeSupplier<Long, ? extends Exception> addCommentUnsafeSupplier,
			String className, long classPK, long groupId)
		throws Exception {

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkAddPermission(
			contextCompany.getCompanyId(), groupId, className, classPK);

		try {
			long commentId = addCommentUnsafeSupplier.get();

			return CommentUtil.toComment(
				_commentManager.fetchComment(commentId), _commentManager,
				_portal);
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

	private Comment _postEntityComment(
			String externalReferenceCode, long groupId, String className,
			long classPK, String text)
		throws Exception {

		return _postComment(
			() -> _commentManager.addComment(
				externalReferenceCode, _getUserId(), groupId, className,
				classPK, StringPool.BLANK, StringPool.BLANK,
				StringBundler.concat("<p>", text, "</p>"),
				_createServiceContextFunction()),
			className, classPK, groupId);
	}

	private Comment _postParentCommentComment(
			String externalReferenceCode, long groupId, long parentCommentId,
			String className, long classPK, String text)
		throws Exception {

		return _postComment(
			() -> _commentManager.addComment(
				externalReferenceCode, _getUserId(), className, classPK,
				StringPool.BLANK, parentCommentId, StringPool.BLANK,
				StringBundler.concat("<p>", text, "</p>"),
				_createServiceContextFunction()),
			className, classPK, groupId);
	}

	private Comment _updateComment(
			com.liferay.portal.kernel.comment.Comment comment, long commentId,
			String text)
		throws Exception {

		DiscussionPermission discussionPermission = _getDiscussionPermission();

		discussionPermission.checkUpdatePermission(commentId);

		try {
			_commentManager.updateComment(
				comment.getUserId(), comment.getClassName(),
				comment.getClassPK(), comment.getCommentId(), StringPool.BLANK,
				StringBundler.concat("<p>", text, "</p>"),
				_createServiceContextFunction());

			return CommentUtil.toComment(
				_commentManager.fetchComment(comment.getCommentId()),
				_commentManager, _portal);
		}
		catch (MessageSubjectException messageSubjectException) {
			throw new ClientErrorException(
				"Comment text is null", 422, messageSubjectException);
		}
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DLFileEntryService _dlFileEntryService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

}