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

import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/knowledge-base-article.properties",
	scope = ServiceScope.PROTOTYPE, service = KnowledgeBaseArticleResource.class
)
public class KnowledgeBaseArticleResourceImpl
	extends BaseKnowledgeBaseArticleResourceImpl {

	@Override
	public boolean deleteKnowledgeBaseArticle(Long knowledgeBaseArticleId)
		throws Exception {

		_kbArticleService.deleteKBArticle(knowledgeBaseArticleId);

		return true;
	}

	@Override
	public Page<KnowledgeBaseArticle> getFolderKnowledgeBaseArticlesPage(
			Long folderId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_kbArticleService.getGroupKBArticles(
					folderId, 0, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toKBArticle),
			pagination, _kbArticleService.getGroupKBArticlesCount(folderId, 0));
	}

	@Override
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			Long knowledgeBaseArticleId)
		throws Exception {

		return _toKBArticle(
			_kbArticleService.fetchLatestKBArticle(knowledgeBaseArticleId, 0));
	}

	@Override
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				Long knowledgeBaseArticleId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_kbArticleService.getGroupKBArticles(
					knowledgeBaseArticleId, 0, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toKBArticle),
			pagination,
			_kbArticleService.getGroupKBArticlesCount(
				knowledgeBaseArticleId, 0));
	}

	@Override
	public KnowledgeBaseArticle postFolderKnowledgeBaseArticle(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		return super.postFolderKnowledgeBaseArticle(folderId, multipartBody);
	}

	@Override
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			Long knowledgeBaseArticleId, MultipartBody multipartBody)
		throws Exception {

		return super.postKnowledgeBaseArticleKnowledgeBaseArticle(
			knowledgeBaseArticleId, multipartBody);
	}

	@Override
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			Long knowledgeBaseArticleId, MultipartBody multipartBody)
		throws Exception {

		return super.putKnowledgeBaseArticle(
			knowledgeBaseArticleId, multipartBody);
	}

	private KnowledgeBaseArticle _toKBArticle(KBArticle kbArticle) {
		return new KnowledgeBaseArticle() {
			{
				//				aggregateRating =
				articleBody = kbArticle.getContent();
				//				category = kbArticle.get
				//				creator = kbArticle.getCr
				dateCreated = kbArticle.getCreateDate();
				dateModified = kbArticle.getModifiedDate();
				datePublished = kbArticle.getLastPublishDate();
				//				friendlyUrlPath = kbArticle.getUr
				title = kbArticle.getTitle();
				//				keywords = kbArticle.
				//				folder = kbArticle.getKbFolderId();
			}
		};
	}

	@Reference
	private KBArticleService _kbArticleService;

}