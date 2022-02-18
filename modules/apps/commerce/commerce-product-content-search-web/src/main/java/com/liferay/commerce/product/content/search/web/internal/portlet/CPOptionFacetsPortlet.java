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

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPOptionFacetsPortletInstanceConfiguration;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.builder.CPOptionsSearchFacetDisplayContextBuilder;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false,
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
		"javax.portlet.display-name=Option Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/option_facets/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_OPTION_FACETS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class CPOptionFacetsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		CPOptionsSearchFacetDisplayContext cpOptionsSearchFacetDisplayContext =
			_buildCPOptionsSearchFacetDisplayContext(
				portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			cpOptionsSearchFacetDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	protected String getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	private CPOptionsSearchFacetDisplayContext
		_buildCPOptionsSearchFacetDisplayContext(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CPOptionFacetsPortletInstanceConfiguration
			cpOptionFacetsPortletInstanceConfiguration =
				_getCpOptionFacetsPortletInstanceConfiguration(
					themeDisplay.getPortletDisplay());

		String displayStyle =
			cpOptionFacetsPortletInstanceConfiguration.displayStyle();
		int frequencyThreshold =
			cpOptionFacetsPortletInstanceConfiguration.getFrequencyThreshold();
		int maxTerms = cpOptionFacetsPortletInstanceConfiguration.getMaxTerms();
		boolean showFrequencies =
			cpOptionFacetsPortletInstanceConfiguration.showFrequencies();

		Optional<PortletPreferences> portletPreferencesOptional =
			portletSharedSearchResponse.getPortletPreferences(renderRequest);

		if (portletPreferencesOptional.isPresent()) {
			PortletPreferences portletPreferences =
				portletPreferencesOptional.get();

			frequencyThreshold = GetterUtil.getInteger(
				portletPreferences.getValue("frequencyThreshold", null),
				frequencyThreshold);
			maxTerms = GetterUtil.getInteger(
				portletPreferences.getValue("maxTerms", null), maxTerms);
			showFrequencies = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"frequenciesVisible", StringPool.BLANK),
				true);
			displayStyle = portletPreferences.getValue(
				"cpOptionFacetDisplayStyle", displayStyle);
		}

		CPOptionsSearchFacetDisplayContextBuilder
			cpOptionsSearchFacetDisplayBuilder =
				new CPOptionsSearchFacetDisplayContextBuilder(renderRequest);

		cpOptionsSearchFacetDisplayBuilder.cpOptionLocalService(
			_cpOptionLocalService);
		cpOptionsSearchFacetDisplayBuilder.displayStyle(displayStyle);
		cpOptionsSearchFacetDisplayBuilder.facet(
			portletSharedSearchResponse.getFacet(CPField.OPTION_NAMES));
		cpOptionsSearchFacetDisplayBuilder.frequenciesVisible(showFrequencies);
		cpOptionsSearchFacetDisplayBuilder.frequencyThreshold(
			frequencyThreshold);
		cpOptionsSearchFacetDisplayBuilder.maxTerms(maxTerms);
		cpOptionsSearchFacetDisplayBuilder.paginationStartParameterName(
			getPaginationStartParameterName(portletSharedSearchResponse));
		cpOptionsSearchFacetDisplayBuilder.portal(_portal);
		cpOptionsSearchFacetDisplayBuilder.portletSharedSearchRequest(
			_portletSharedSearchRequest);

		return cpOptionsSearchFacetDisplayBuilder.build();
	}

	private CPOptionFacetsPortletInstanceConfiguration
		_getCpOptionFacetsPortletInstanceConfiguration(
			PortletDisplay portletDisplay) {

		try {
			return portletDisplay.getPortletInstanceConfiguration(
				CPOptionFacetsPortletInstanceConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}