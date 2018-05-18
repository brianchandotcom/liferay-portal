/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.content.search.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPOptionFacetsDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.util.CPOptionFacetsUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.service.CPOptionService;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
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
 * @author Marco Leo
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-cp-option-facets",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Option Facets",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/option_facets/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_OPTION_FACETS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CPOptionFacetsPortlet
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
				String cpOptionKey =
					CPOptionFacetsUtil.getCPOptionKeyFromIndexFieldName(
						facet.getFieldName());

				Optional<String[]> parameterValuesOptional =
					portletSharedSearchSettings.getParameterValues(cpOptionKey);

				MultiValueFacet multiValueFacet = new MultiValueFacet(
					searchContext);

				multiValueFacet.setFieldName(facet.getFieldName());

				if (parameterValuesOptional.isPresent()) {
					multiValueFacet.setValues(parameterValuesOptional.get());

					searchContext.setAttribute(
						facet.getFieldName(),
						StringUtil.merge(parameterValuesOptional.get()));
				}

				portletSharedSearchSettings.addFacet(multiValueFacet);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
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

			CPOptionFacetsDisplayContext cpOptionFacetsDisplayContext =
				new CPOptionFacetsDisplayContext(
					_cpOptionService, renderRequest, filledFacets,
					portletSharedSearchResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpOptionFacetsDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		super.render(renderRequest, renderResponse);
	}

	protected SearchContext buildSearchContext(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setGroupIds(new long[] {themeDisplay.getScopeGroupId()});
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setLocale(themeDisplay.getLocale());

		return searchContext;
	}

	protected List<Facet> getFacets(RenderRequest renderRequest)
		throws PortalException {

		List<Facet> facets = new ArrayList<>();

		AssetCategory assetCategory = (AssetCategory)renderRequest.getAttribute(
			WebKeys.ASSET_CATEGORY);

		SearchContext searchContext = buildSearchContext(renderRequest);

		if (assetCategory != null) {
			searchContext.setAssetCategoryIds(
				new long[] {assetCategory.getCategoryId()});
		}

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName(CPDefinitionIndexer.FIELD_OPTION_NAMES);

		searchContext.addFacet(facet);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(
			CPDefinitionIndexer.FIELD_OPTION_NAMES);

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.search(searchContext);

		FacetCollector facetCollector = facet.getFacetCollector();

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			MultiValueFacet multiValueFacet = new MultiValueFacet(
				searchContext);

			multiValueFacet.setFieldName(
				CPOptionFacetsUtil.getIndexFieldName(termCollector.getTerm()));

			facets.add(multiValueFacet);
		}

		return facets;
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionFacetsPortlet.class);

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private Portal _portal;

}