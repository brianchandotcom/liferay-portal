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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.headless.web.experience.dto.v1_0.ContentStructure;
import com.liferay.headless.web.experience.internal.dto.v1_0.CreatorUtil;
import com.liferay.headless.web.experience.internal.odata.entity.v1_0.ContentStructureEntityModel;
import com.liferay.headless.web.experience.resource.v1_0.ContentStructureResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterSearcher;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-structure.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentStructureResource.class
)
public class ContentStructureResourceImpl
	extends BaseContentStructureResourceImpl implements EntityModelResource {

	@Override
	public Page<ContentStructure> getContentSpaceContentStructuresPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Hits hits = _getHits(contentSpaceId, filter, pagination, sorts);
		
		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (Document doc : hits.getDocs()) {
			DDMStructure ddmStructure = _ddmStructureService.getStructure(
				GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK)));

			ddmStructures.add(ddmStructure);
		}

		return Page.of(
			transform(ddmStructures, this::_toContentStructure), pagination,
			ddmStructures.size());
	}

	@Override
	public ContentStructure getContentStructure(Long contentStructureId)
		throws Exception {

		return _toContentStructure(
			_ddmStructureService.getStructure(contentStructureId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new ContentStructureEntityModel();
	}

	private SearchContext _createSearchContext(
		Long groupId, long classNameId, Pagination pagination,
		PermissionChecker permissionChecker, Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.CLASS_NAME_ID, classNameId);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setCompanyId(company.getCompanyId());
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setSorts(sorts);
		searchContext.setStart(pagination.getStartPosition());
		searchContext.setUserId(permissionChecker.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(Field.ENTRY_CLASS_PK);

		return searchContext;
	}

	private Hits _getHits(
			Long groupId, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		ClassName className = _classNameService.fetchClassName(
			JournalArticle.class.getName());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		SearchContext searchContext = _createSearchContext(
			groupId, className.getClassNameId(), pagination, permissionChecker,
			sorts);

		Query query = _getQuery(filter, searchContext);

		SearchResultPermissionFilter searchResultPermissionFilter =
			_searchResultPermissionFilterFactory.create(
				new SearchResultPermissionFilterSearcher() {

					public Hits search(SearchContext searchContext)
						throws SearchException {

						return IndexSearcherHelperUtil.search(
							searchContext, query);
					}

				},
				permissionChecker);

		return searchResultPermissionFilter.search(searchContext);
	}

	private Query _getQuery(Filter filter, SearchContext searchContext)
		throws Exception {

		Indexer<DDMStructure> indexer = _indexerRegistry.nullSafeGetIndexer(
			DDMStructure.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		if (filter != null) {
			BooleanFilter booleanFilter = booleanQuery.getPreBooleanFilter();

			booleanFilter.add(filter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	private ContentStructure _toContentStructure(DDMStructure ddmStructure)
		throws Exception {

		return new ContentStructure() {
			{
				setAvailableLanguages(
					LocaleUtil.toW3cLanguageIds(
						ddmStructure.getAvailableLanguageIds()));
				setContentSpace(ddmStructure.getGroupId());
				setCreator(
					CreatorUtil.toCreator(
						_userService.getUserById(ddmStructure.getUserId())));
				setDateCreated(ddmStructure.getCreateDate());
				setDateModified(ddmStructure.getModifiedDate());
				setDescription(
					ddmStructure.getDescription(
						acceptLanguage.getPreferredLocale()));
				setId(ddmStructure.getStructureId());
				setName(
					ddmStructure.getName(acceptLanguage.getPreferredLocale()));
			}
		};
	}

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserService _userService;

}