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

import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.KnowledgeBaseArticleDTOConverter;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.KnowledgeBaseArticleEntityModel;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
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
			contentSpaceId, _portal.getClassNameId(KBFolder.class.getName()),
			0L, knowledgeBaseArticle);
	}

	@Override
	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KBArticle kbArticle = _kbArticleService.getLatestKBArticle(
			knowledgeBaseArticleId, WorkflowConstants.STATUS_APPROVED);

		return _getKnowledgeBaseArticle(
			kbArticle.getGroupId(),
			_portal.getClassNameId(KBArticle.class.getName()),
			knowledgeBaseArticleId, knowledgeBaseArticle);
	}

	@Override
	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			Long knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(knowledgeBaseFolderId);

		return _getKnowledgeBaseArticle(
			kbFolder.getGroupId(),
			_portal.getClassNameId(KBFolder.class.getName()),
			knowledgeBaseFolderId, knowledgeBaseArticle);
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
			Long contentSpaceId, long parentResourceClassNameId,
			Long parentResourcePrimaryKey,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return _toKBArticle(
			_kbArticleService.addKBArticle(
				KBPortletKeys.KNOWLEDGE_BASE_DISPLAY, parentResourceClassNameId,
				parentResourcePrimaryKey, knowledgeBaseArticle.getTitle(),
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
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
			sorts);
	}

	private KnowledgeBaseArticle _toKBArticle(KBArticle kbArticle)
		throws Exception {

		if (kbArticle == null) {
			return null;
		}

		return _toKBArticle(kbArticle.getResourcePrimKey());
	}

	private KnowledgeBaseArticle _toKBArticle(
			long knowledgeBaseArticleResourcePrimKey)
		throws Exception {

		return _knowledgeBaseArticleDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				null, knowledgeBaseArticleResourcePrimKey));
	}

	private static final EntityModel _entityModel =
		new KnowledgeBaseArticleEntityModel();

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

	@Reference
	private KnowledgeBaseArticleDTOConverter _knowledgeBaseArticleDTOConverter;

	@Reference
	private Portal _portal;

}