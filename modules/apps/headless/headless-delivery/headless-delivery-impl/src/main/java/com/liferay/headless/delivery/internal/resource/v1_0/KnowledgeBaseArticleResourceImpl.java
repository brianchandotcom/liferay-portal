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
import com.liferay.headless.collaboration.internal.dto.v1_0.util.TaxonomyCategoryUtil;
import com.liferay.headless.collaboration.internal.odata.entity.v1_0.KnowledgeBaseArticleEntityModel;
import com.liferay.headless.collaboration.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

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
	extends BaseKnowledgeBaseArticleResourceImpl
	implements EntityModelResource {

	@Override
	public void deleteKnowledgeBaseArticle(Long knowledgeBaseArticleId)
		throws Exception {

		_kbArticleService.deleteKBArticle(knowledgeBaseArticleId);
	}

	@Override
	public Page<KnowledgeBaseArticle> getContentSpaceKnowledgeBaseArticlesPage(
			Long contentSpaceId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getKnowledgeBaseArticlesPage(
			booleanQuery -> {
				if (!GetterUtil.getBoolean(flatten)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(Field.FOLDER_ID, "0"),
						BooleanClauseOccur.MUST);
					booleanFilter.add(
						new TermFilter("parentMessageId", "0"),
						BooleanClauseOccur.MUST);
				}
			},
			contentSpaceId, search, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
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
				Long knowledgeBaseArticleId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		return _getKnowledgeBaseArticlesPage(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"parentMessageId",
						String.valueOf(kbArticle.getResourcePrimKey())),
					BooleanClauseOccur.MUST);
			},
			kbArticle.getGroupId(), search, filter, pagination, sorts);
	}

	@Override
	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				Long knowledgeBaseFolderId, Boolean flatten, String search,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(knowledgeBaseFolderId);

		return _getKnowledgeBaseArticlesPage(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.FOLDER_ID,
						String.valueOf(kbFolder.getKbFolderId())),
					BooleanClauseOccur.MUST);

				if (!GetterUtil.getBoolean(flatten)) {
					booleanFilter.add(
						new TermFilter(
							"parentMessageId",
							String.valueOf(kbFolder.getKbFolderId())),
						BooleanClauseOccur.MUST);
				}
			},
			kbFolder.getGroupId(), search, filter, pagination, sorts);
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
		throws Exception {

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

	private Page<KnowledgeBaseArticle> _getKnowledgeBaseArticlesPage(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Long contentSpaceId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQueryUnsafeConsumer, filter, KBArticle.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});

				if (search == null) {
					searchContext.setKeywords("");
				}
			},
			document -> _toKBArticle(
				_kbArticleService.getLatestKBArticle(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
					WorkflowConstants.STATUS_APPROVED)),
			sorts);
	}

	private KnowledgeBaseArticle _toKBArticle(KBArticle kbArticle)
		throws Exception {

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
				encodingFormat = "text/html";
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
					TaxonomyCategoryUtil::toTaxonomyCategory,
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

	private static final EntityModel _entityModel =
		new KnowledgeBaseArticleEntityModel();

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