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
import com.liferay.headless.collaboration.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.collaboration.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.ParentKnowledgeBaseFolderUtil;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.util.List;
import java.util.Optional;

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
	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			Long knowledgeBaseArticleId)
		throws Exception {

		return _toKBArticle(
			_kbArticleService.getLatestKBArticle(
				knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				Long knowledgeBaseArticleId, Pagination pagination)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
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
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				Long knowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(knowledgeBaseFolderId);

		return Page.of(
			transform(
				_kbArticleService.getKBArticles(
					kbFolder.getGroupId(), knowledgeBaseFolderId,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toKBArticle),
			pagination,
			_kbArticleService.getKBArticlesCount(
				kbFolder.getGroupId(), knowledgeBaseFolderId,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public KnowledgeBaseArticle postContentSpaceKnowledgeBaseArticle(
			Long contentSpaceId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return _getKnowledgeBaseArticle(
			contentSpaceId, 0L,
			_classNameLocalService.fetchClassName(KBFolder.class.getName()),
			knowledgeBaseArticle);
	}

	@Override
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		return _getKnowledgeBaseArticle(
			kbArticle.getGroupId(), knowledgeBaseArticleId,
			_classNameLocalService.fetchClassName(KBArticle.class.getName()),
			knowledgeBaseArticle);
	}

	@Override
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			Long knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(knowledgeBaseFolderId);

		return _getKnowledgeBaseArticle(
			kbFolder.getGroupId(), knowledgeBaseFolderId,
			_classNameLocalService.fetchClassName(KBFolder.class.getName()),
			knowledgeBaseArticle);
	}

	@Override
	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return _toKBArticle(
			_kbArticleService.updateKBArticle(
				knowledgeBaseArticleId, knowledgeBaseArticle.getTitle(),
				knowledgeBaseArticle.getArticleBody(),
				knowledgeBaseArticle.getDescription(), null, null, null, null,
				ServiceContextUtil.createServiceContext(
					knowledgeBaseArticle.getKeywords(),
					knowledgeBaseArticle.getTaxonomyCategoryIds(),
					knowledgeBaseArticle.getContentSpaceId(),
					knowledgeBaseArticle.getViewableByAsString())));
	}

	private KnowledgeBaseArticle _getKnowledgeBaseArticle(
			Long contentSpaceId, Long resourcePrimaryKey,
			ClassName resourceClassName,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws PortalException {

		return _toKBArticle(
			_kbArticleService.addKBArticle(
				KBPortletKeys.KNOWLEDGE_BASE_DISPLAY,
				resourceClassName.getClassNameId(), resourcePrimaryKey,
				knowledgeBaseArticle.getTitle(),
				knowledgeBaseArticle.getFriendlyUrlPath(),
				knowledgeBaseArticle.getArticleBody(),
				knowledgeBaseArticle.getDescription(), null, null, null,
				ServiceContextUtil.createServiceContext(
					knowledgeBaseArticle.getKeywords(),
					knowledgeBaseArticle.getTaxonomyCategoryIds(),
					contentSpaceId,
					knowledgeBaseArticle.getViewableByAsString())));
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
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(kbArticle.getUserId()));
				dateCreated = kbArticle.getCreateDate();
				dateModified = kbArticle.getModifiedDate();
				description = kbArticle.getDescription();
				friendlyUrlPath = kbArticle.getUrlTitle();
				id = kbArticle.getResourcePrimKey();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						KBArticle.class.getName(), kbArticle.getClassPK()),
					AssetTag.NAME_ACCESSOR);
				numberOfAttachments = Optional.ofNullable(
					kbArticle.getAttachmentsFileEntries()
				).map(
					List::size
				).orElse(
					0
				);
				numberOfKnowledgeBaseArticles =
					_kbArticleService.getKBArticlesCount(
						kbArticle.getGroupId(), kbArticle.getResourcePrimKey(),
						WorkflowConstants.STATUS_APPROVED);
				parentKnowledgeBaseFolderId = kbArticle.getKbFolderId();
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						KBArticle.class.getName(), kbArticle.getClassPK()),
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
				title = kbArticle.getTitle();

				setParentKnowledgeBaseFolder(
					() -> {
						if (kbArticle.getKbFolderId() <= 0) {
							return null;
						}

						return ParentKnowledgeBaseFolderUtil.
							toParentKnowledgeBaseFolder(
								_kbFolderService.getKBFolder(
									kbArticle.getKbFolderId()));
					});
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

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