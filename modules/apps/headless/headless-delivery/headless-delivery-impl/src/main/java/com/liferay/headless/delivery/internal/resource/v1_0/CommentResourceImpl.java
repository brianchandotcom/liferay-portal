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
import com.liferay.headless.common.spi.resource.SPICommentResource;
import com.liferay.headless.delivery.dto.v1_0.Comment;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CommentUtil;
import com.liferay.headless.delivery.resource.v1_0.CommentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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
	public void deleteComment(Long commentId) throws Exception {
		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			null);

		spiCommentResource.deleteComment(commentId);
	}

	@Override
	public Page<Comment> getBlogPostingCommentsPage(
			Long blogPostingId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			BlogsEntry.class.getName());

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return spiCommentResource.getEntityCommentsPage(
			blogsEntry.getGroupId(), blogPostingId, search, filter, pagination,
			sorts);
	}

	@Override
	public Comment getComment(Long commentId) throws Exception {
		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			null);

		return spiCommentResource.getComment(commentId);
	}

	@Override
	public Page<Comment> getCommentCommentsPage(
			Long commentId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			null);

		return spiCommentResource.getCommentCommentsPage(
			commentId, search, filter, pagination, sorts);
	}

	@Override
	public Page<Comment> getDocumentCommentsPage(
			Long documentId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			DLFileEntry.class.getName());

		DLFileEntry dlFileEntry = _dlFileEntryService.getFileEntry(documentId);

		return spiCommentResource.getEntityCommentsPage(
			dlFileEntry.getGroupId(), documentId, search, filter, pagination,
			sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			null);

		return spiCommentResource.getEntityModel(multivaluedMap);
	}

	@Override
	public Page<Comment> getStructuredContentCommentsPage(
			Long structuredContentId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			JournalArticle.class.getName());

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return spiCommentResource.getEntityCommentsPage(
			journalArticle.getGroupId(), structuredContentId, search, filter,
			pagination, sorts);
	}

	@Override
	public Comment postBlogPostingComment(Long blogPostingId, Comment comment)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			BlogsEntry.class.getName());

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return spiCommentResource.postEntityComment(
			blogsEntry.getGroupId(), blogPostingId, comment.getText());
	}

	@Override
	public Comment postCommentComment(Long commentId, Comment comment)
		throws Exception {

		com.liferay.portal.kernel.comment.Comment parentComment =
			_commentManager.fetchComment(commentId);

		if (parentComment == null) {
			throw new NotFoundException();
		}

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			parentComment.getClassName());

		return spiCommentResource.postCommentComment(
			parentComment, comment.getText());
	}

	@Override
	public Comment postDocumentComment(Long documentId, Comment comment)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			DLFileEntry.class.getName());

		DLFileEntry fileEntry = _dlFileEntryService.getFileEntry(documentId);

		return spiCommentResource.postEntityComment(
			fileEntry.getGroupId(), documentId, comment.getText());
	}

	@Override
	public Comment postStructuredContentComment(
			Long structuredContentId, Comment comment)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			JournalArticle.class.getName());

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return spiCommentResource.postEntityComment(
			journalArticle.getGroupId(), structuredContentId,
			comment.getText());
	}

	@Override
	public Comment putComment(Long commentId, Comment comment)
		throws Exception {

		SPICommentResource<Comment> spiCommentResource = _getSPICommentResource(
			null);

		return spiCommentResource.putComment(commentId, comment.getText());
	}

	private SPICommentResource<Comment> _getSPICommentResource(
		String className) {

		return new SPICommentResource<>(
			className, _commentManager, contextCompany,
			comment -> CommentUtil.toComment(
				comment, _commentManager, _portal));
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