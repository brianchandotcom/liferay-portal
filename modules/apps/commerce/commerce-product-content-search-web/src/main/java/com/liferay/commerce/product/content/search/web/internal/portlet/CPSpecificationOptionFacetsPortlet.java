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

package com.liferay.commerce.product.content.search.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionFacetsDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.search.facet.SerializableFacet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-cp-specification-option-facets",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Specification Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/specification_option_facets/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_SPECIFICATION_OPTION_FACETS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CPSpecificationOptionFacetsPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		try {
			SearchContext searchContext =
				portletSharedSearchSettings.getSearchContext();

			List<Facet> facets = getFacets(renderRequest);

			for (Facet facet : facets) {
				String cpSpecificationOptionKey =
					CPSpecificationOptionFacetsUtil.
						getCPSpecificationOptionKeyFromIndexFieldName(
							facet.getFieldName());

				Optional<String[]> parameterValuesOptional =
					portletSharedSearchSettings.getParameterValues71(
						cpSpecificationOptionKey);

				SerializableFacet serializableFacet = new SerializableFacet(
					facet.getFieldName(), searchContext);

				if (parameterValuesOptional.isPresent()) {
					serializableFacet.select(parameterValuesOptional.get());

					searchContext.setAttribute(
						facet.getFieldName(), parameterValuesOptional.get());
				}

				portletSharedSearchSettings.addFacet(serializableFacet);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		try {
			List<Facet> facets = getFacets(renderRequest);

			List<Facet> filledFacets = new ArrayList<>(facets.size());

			for (Facet facet : facets) {
				filledFacets.add(
					portletSharedSearchResponse.getFacet(facet.getFieldName()));
			}

			CPSpecificationOptionFacetsDisplayContext
				cpSpecificationOptionFacetsDisplayContext =
					new CPSpecificationOptionFacetsDisplayContext(
						_cpSpecificationOptionLocalService, renderRequest,
						filledFacets,
						getPaginationStartParameterName(
							portletSharedSearchResponse),
						portletSharedSearchResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpSpecificationOptionFacetsDisplayContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		super.render(renderRequest, renderResponse);
	}

	protected SearchContext buildSearchContext(RenderRequest renderRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setLocale(themeDisplay.getLocale());

		searchContext.setAttribute(CPField.PUBLISHED, Boolean.TRUE);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				themeDisplay.getScopeGroupId());

		if (commerceChannel != null) {
			searchContext.setAttribute(
				"commerceChannelGroupId", commerceChannel.getGroupId());

			CommerceAccount commerceAccount =
				_commerceAccountHelper.getCurrentCommerceAccount(
					commerceChannel.getGroupId(),
					_portal.getHttpServletRequest(renderRequest));

			if (commerceAccount != null) {
				long[] commerceAccountGroupIds =
					_commerceAccountHelper.getCommerceAccountGroupIds(
						commerceAccount.getCommerceAccountId());

				searchContext.setAttribute(
					"commerceAccountGroupIds", commerceAccountGroupIds);
			}
		}

		searchContext.setAttribute("secure", Boolean.TRUE);

		return searchContext;
	}

	protected List<Facet> getFacets(RenderRequest renderRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Facet> facets = new ArrayList<>();

		AssetCategory assetCategory = (AssetCategory)renderRequest.getAttribute(
			WebKeys.ASSET_CATEGORY);

		SearchContext searchContext = buildSearchContext(renderRequest);

		if (assetCategory != null) {
			searchContext.setAssetCategoryIds(
				new long[] {assetCategory.getCategoryId()});
		}

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName(CPField.SPECIFICATION_NAMES);

		searchContext.addFacet(facet);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(CPField.SPECIFICATION_NAMES);

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.search(searchContext);

		FacetCollector facetCollector = facet.getFacetCollector();

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			CPSpecificationOption cpSpecificationOption =
				_cpSpecificationOptionLocalService.getCPSpecificationOption(
					searchContext.getCompanyId(), termCollector.getTerm());

			if (cpSpecificationOption.isFacetable()) {
				MultiValueFacet multiValueFacet = new MultiValueFacet(
					searchContext);

				multiValueFacet.setFieldName(
					CPSpecificationOptionFacetsUtil.getIndexFieldName(
						termCollector.getTerm(), themeDisplay.getLanguageId()));

				facets.add(multiValueFacet);
			}
		}

		return facets;
	}

	protected String getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private static final Log _log = LogFactoryUtil.getLog(
		CPSpecificationOptionFacetsPortlet.class);

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference
	private Portal _portal;

}