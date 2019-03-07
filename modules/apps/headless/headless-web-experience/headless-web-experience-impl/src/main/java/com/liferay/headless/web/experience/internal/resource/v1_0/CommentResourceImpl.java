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

import com.liferay.headless.common.spi.resource.SPICommentResource;
import com.liferay.headless.web.experience.dto.v1_0.Comment;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CommentUtil;
import com.liferay.headless.web.experience.resource.v1_0.CommentResource;
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

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Activate;
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

	@Activate
	public void afterPropertiesSet() {
		_spiCommentResource = new SPICommentResource<>(
			JournalArticle.class.getName(), _commentManager,
			() -> contextCompany,
			comment -> CommentUtil.toComment(
				comment, _commentManager, _portal));
	}

	@Override
	public boolean deleteComment(Long commentId) throws Exception {
		return _spiCommentResource.deleteComment(commentId);
	}

	@Override
	public Comment getComment(Long commentId) throws Exception {
		return _spiCommentResource.getComment(commentId);
	}

	@Override
	public Page<Comment> getCommentCommentsPage(
			Long commentId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _spiCommentResource.getCommentCommentsPage(
			commentId, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _spiCommentResource.getEntityModel(multivaluedMap);
	}

	@Override
	public Page<Comment> getStructuredContentCommentsPage(
			Long structuredContentId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _spiCommentResource.getEntityCommentsPage(
			journalArticle.getGroupId(), structuredContentId, filter,
			pagination, sorts);
	}

	@Override
	public Comment postCommentComment(Long parentCommentId, Comment comment)
		throws Exception {

		return _spiCommentResource.postCommentComment(
			parentCommentId, comment.getText());
	}

	@Override
	public Comment postStructuredContentComment(
			Long structuredContentId, Comment comment)
		throws Exception {

		JournalArticle journalArticle = _journalArticleService.getLatestArticle(
			structuredContentId);

		return _spiCommentResource.postEntityComment(
			journalArticle.getGroupId(), structuredContentId,
			comment.getText());
	}

	@Override
	public Comment putComment(Long commentId, Comment comment)
		throws Exception {

		return _spiCommentResource.putComment(commentId, comment.getText());
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

	private SPICommentResource<Comment> _spiCommentResource;

}