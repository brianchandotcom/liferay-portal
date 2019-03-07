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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.collaboration.dto.v1_0.Categories;
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.ParentFolderUtil;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

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
	public Page<KnowledgeBaseArticle> getContentSpaceKnowledgeBaseArticlesPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_kbArticleService.getKBArticles(
					contentSpaceId, 0, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toKBArticle),
			pagination,
			_kbArticleService.getKBArticlesCount(
				contentSpaceId, 0, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public Page<KnowledgeBaseArticle> getFolderKnowledgeBaseArticlesPage(
			Long folderId, Pagination pagination)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(folderId);

		return Page.of(
			transform(
				_kbArticleService.getKBArticles(
					kbFolder.getGroupId(), folderId,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toKBArticle),
			pagination,
			_kbArticleService.getKBArticlesCount(
				kbFolder.getGroupId(), folderId,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			Long knowledgeBaseArticleId)
		throws Exception {

		return _toKBArticle(
			_kbArticleService.fetchLatestKBArticle(
				knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				Long knowledgeBaseArticleId, Pagination pagination)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.fetchLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		return Page.of(
			transform(
				_kbArticleService.getKBArticles(
					kbArticle.getGroupId(), kbArticle.getResourcePrimKey(),
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toKBArticle),
			pagination,
			_kbArticleService.getKBArticlesCount(
				kbArticle.getGroupId(), kbArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public KnowledgeBaseArticle postContentSpaceKnowledgeBaseArticle(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		return super.postContentSpaceKnowledgeBaseArticle(
			contentSpaceId, multipartBody);
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

	private KnowledgeBaseArticle _toKBArticle(KBArticle kbArticle)
		throws PortalException {

		if (kbArticle == null) {
			return null;
		}

		return new KnowledgeBaseArticle() {
			{
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						KBArticle.class.getName(),
						kbArticle.getResourcePrimKey()));
				articleBody = kbArticle.getContent();
				categories = transformToArray(
					_assetCategoryLocalService.getCategories(
						KBArticle.class.getName(), kbArticle.getClassPK()),
					assetCategory -> new Categories() {
						{
							categoryId = assetCategory.getCategoryId();
							categoryName = assetCategory.getName();
						}
					},
					Categories.class);
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(kbArticle.getUserId()));
				dateCreated = kbArticle.getCreateDate();
				dateModified = kbArticle.getModifiedDate();
				friendlyUrlPath = kbArticle.getUrlTitle();
				id = kbArticle.getKbArticleId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						KBArticle.class.getName(), kbArticle.getClassPK()),
					AssetTag.NAME_ACCESSOR);

				if (kbArticle.getKbFolderId() != 0) {
					parentFolder = ParentFolderUtil.toParentFolder(
						_kbFolderService.getKBFolder(
							kbArticle.getKbFolderId()));
				}

				parentFolderId = kbArticle.getKbFolderId();
				title = kbArticle.getTitle();
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}