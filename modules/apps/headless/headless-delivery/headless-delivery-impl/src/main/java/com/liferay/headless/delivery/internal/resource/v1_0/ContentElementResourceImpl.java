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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.headless.delivery.dto.v1_0.ContentElement;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.ContentElementEntityModel;
import com.liferay.headless.delivery.resource.v1_0.ContentElementResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.FacetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portlet.asset.util.AssetSearcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-element.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentElementResource.class
)
public class ContentElementResourceImpl extends BaseContentElementResourceImpl {

	@Override
	public Page<ContentElement> getAssetLibraryContentElementsPage(
			Long assetLibraryId, String search, Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return getSiteContentElementsPage(
			assetLibraryId, search, aggregation, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return new ContentElementEntityModel(_dtoConverterRegistry);
	}

	@Override
	public Page<ContentElement> getSiteContentElementsPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		SearchContext searchContext = _getAssetSearchContext(
			siteId, search, aggregation, filter, pagination, sorts);

		Map<String, Facet> facets = searchContext.getFacets();

		AssetSearcher assetSearcher =
			(AssetSearcher)AssetSearcher.getInstance();

		assetSearcher.setAssetEntryQuery(new AssetEntryQuery());

		List<AssetEntry> assetEntries = _assetHelper.getAssetEntries(
			assetSearcher.search(searchContext));

		return Page.of(
			new HashMap<>(),
			TransformUtil.transform(facets.values(), FacetUtil::toFacet),
			transform(assetEntries, this::_toContentElement), pagination,
			_assetHelper.searchCount(searchContext, new AssetEntryQuery()));
	}

	private SearchContext _getAssetSearchContext(
		Long groupId, String search, Aggregation aggregation, Filter filter,
		Pagination pagination, Sort[] sorts) {

		SearchUtil.SearchContext searchContext = new SearchUtil.SearchContext();

		searchContext.addVulcanAggregation(aggregation);

		BooleanQuery booleanQuery = new BooleanQueryImpl() {
			{
				BooleanFilter booleanFilter = new BooleanFilter();

				if (filter != null) {
					booleanFilter.add(filter, BooleanClauseOccur.MUST);
				}

				booleanFilter.addRequiredTerm(
					Field.STATUS, WorkflowConstants.STATUS_APPROVED);

				booleanFilter.add(
					new BooleanFilter() {
						{
							add(
								new BooleanFilter() {
									{
										addRequiredTerm(
											Field.ENTRY_CLASS_NAME,
											JournalArticle.class.getName());
										addRequiredTerm("head", true);
									}
								},
								BooleanClauseOccur.SHOULD);
							add(
								new BooleanFilter() {
									{
										addTerm(
											Field.ENTRY_CLASS_NAME,
											JournalArticle.class.getName(),
											BooleanClauseOccur.MUST_NOT);
									}
								},
								BooleanClauseOccur.SHOULD);
						}
					},
					BooleanClauseOccur.MUST);

				setPreBooleanFilter(booleanFilter);
			}
		};

		searchContext.setBooleanClauses(
			new BooleanClause[] {
				BooleanClauseFactoryUtil.create(
					booleanQuery, BooleanClauseOccur.MUST.getName())
			});

		searchContext.setCompanyId(contextCompany.getCompanyId());

		if (pagination != null) {
			searchContext.setEnd(pagination.getEndPosition());
		}

		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(search);
		searchContext.setLocale(contextAcceptLanguage.getPreferredLocale());

		if (sorts == null) {
			sorts = new Sort[] {
				new Sort(Field.ENTRY_CLASS_PK, Sort.LONG_TYPE, false)
			};
		}

		searchContext.setSorts(sorts);

		if (pagination != null) {
			searchContext.setStart(pagination.getStartPosition());
		}

		searchContext.setUserId(contextUser.getUserId());

		return searchContext;
	}

	private ContentElement _toContentElement(AssetEntry assetEntry) {
		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			assetEntry.getClassName());

		return new ContentElement() {
			{
				id = assetEntry.getClassPK();
				title = assetEntry.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					assetEntry.getTitleMap());

				setContent(
					() -> {
						if (dtoConverter == null) {
							return null;
						}

						return dtoConverter.toDTO(
							new DefaultDTOConverterContext(
								contextAcceptLanguage.isAcceptAllLanguages(),
								new HashMap<>(), _dtoConverterRegistry,
								contextHttpServletRequest,
								assetEntry.getClassPK(),
								contextAcceptLanguage.getPreferredLocale(),
								contextUriInfo, contextUser));
					});
				setContentType(
					() -> {
						if (dtoConverter == null) {
							return assetEntry.getClassName();
						}

						return dtoConverter.getContentType();
					});
			}
		};
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}