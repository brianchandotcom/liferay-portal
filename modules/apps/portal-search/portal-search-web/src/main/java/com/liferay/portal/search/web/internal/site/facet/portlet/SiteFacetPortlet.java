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

package com.liferay.portal.search.web.internal.site.facet.portlet;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.builder.ScopeSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-site-facet",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Site Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/site/facet/view.jsp",
		"javax.portlet.name=" + SiteFacetPortletKeys.SITE_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class SiteFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		ScopeSearchFacetDisplayContext siteFacetPortletDisplayContext =
			_buildDisplayContext(portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, siteFacetPortletDisplayContext);

		if (siteFacetPortletDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private ScopeSearchFacetDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Facet facet = portletSharedSearchResponse.getFacet(
			_getAggregationName(renderRequest));

		ScopeFacetConfiguration siteFacetConfiguration =
			new ScopeFacetConfigurationImpl(facet.getFacetConfiguration());

		SiteFacetPortletPreferences siteFacetPortletPreferences =
			new SiteFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		ScopeSearchFacetDisplayContextBuilder
			scopeSearchFacetDisplayContextBuilder =
				_createScopeSearchFacetDisplayContextBuilder(renderRequest);

		scopeSearchFacetDisplayContextBuilder.setFacet(facet);

		SearchOptionalUtil.copy(
			() -> _getFilteredGroupIdsOptional(portletSharedSearchResponse),
			scopeSearchFacetDisplayContextBuilder::setFilteredGroupIds);

		scopeSearchFacetDisplayContextBuilder.setFrequencyThreshold(
			siteFacetConfiguration.getFrequencyThreshold());
		scopeSearchFacetDisplayContextBuilder.setFrequenciesVisible(
			siteFacetPortletPreferences.isFrequenciesVisible());
		scopeSearchFacetDisplayContextBuilder.setGroupLocalService(
			groupLocalService);
		scopeSearchFacetDisplayContextBuilder.setLanguage(language);
		scopeSearchFacetDisplayContextBuilder.setLocale(
			_getLocale(portletSharedSearchResponse, renderRequest));
		scopeSearchFacetDisplayContextBuilder.setMaxTerms(
			siteFacetConfiguration.getMaxTerms());
		scopeSearchFacetDisplayContextBuilder.setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse));

		String parameterName = siteFacetPortletPreferences.getParameterName();

		scopeSearchFacetDisplayContextBuilder.setParameterName(parameterName);

		SearchOptionalUtil.copy(
			() -> _getParameterValuesOptional(
				parameterName, portletSharedSearchResponse, renderRequest),
			scopeSearchFacetDisplayContextBuilder::setParameterValues);

		scopeSearchFacetDisplayContextBuilder.setRequest(
			_getHttpServletRequest(renderRequest));

		return scopeSearchFacetDisplayContextBuilder.build();
	}

	private ScopeSearchFacetDisplayContextBuilder
		_createScopeSearchFacetDisplayContextBuilder(
			RenderRequest renderRequest) {

		try {
			return new ScopeSearchFacetDisplayContextBuilder(renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private String _getAggregationName(RenderRequest renderRequest) {
		return portal.getPortletId(renderRequest);
	}

	private Optional<long[]> _getFilteredGroupIdsOptional(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchSettings searchSettings =
			portletSharedSearchResponse.getSearchSettings();

		SearchContext searchContext = searchSettings.getSearchContext();

		return Optional.ofNullable(searchContext.getGroupIds());
	}

	private HttpServletRequest _getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	private Locale _getLocale(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		return themeDisplay.getLocale();
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	private Optional<List<String>> _getParameterValuesOptional(
		String parameterName,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Optional<String[]> optional =
			portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest);

		return optional.map(Arrays::asList);
	}

}