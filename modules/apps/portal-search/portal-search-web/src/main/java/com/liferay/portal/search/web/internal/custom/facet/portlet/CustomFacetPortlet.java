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

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext;
import com.liferay.portal.search.web.internal.custom.facet.display.context.builder.CustomFacetDisplayContextBuilder;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-custom-facet",
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
		"javax.portlet.display-name=Custom Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/custom/facet/view.jsp",
		"javax.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class CustomFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		CustomFacetDisplayContext customFacetDisplayContext =
			_createCustomFacetDisplayContext(
				portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, customFacetDisplayContext);

		if (customFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private CustomFacetDisplayContext _buildDisplayContext(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest)
		throws ConfigurationException {

		CustomFacetDisplayContextBuilder customFacetDisplayContextBuilder =
			new CustomFacetDisplayContextBuilder(
				_getHttpServletRequest(renderRequest));

		CustomFacetPortletPreferences customFacetPortletPreferences =
			new CustomFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		Facet facet = _getFacet(
			portletSharedSearchResponse, customFacetPortletPreferences,
			renderRequest);

		String parameterName = _getParameterName(customFacetPortletPreferences);

		Optional<List<String>> parameterValuesOptional =
			_getParameterValuesOptional(
				parameterName, portletSharedSearchResponse, renderRequest);

		return customFacetDisplayContextBuilder.setCustomDisplayCaption(
			customFacetPortletPreferences.getCustomHeadingOptional()
		).setFacet(
			facet
		).setFieldToAggregate(
			customFacetPortletPreferences.getAggregationFieldString()
		).setFrequenciesVisible(
			customFacetPortletPreferences.isFrequenciesVisible()
		).setFrequencyThreshold(
			customFacetPortletPreferences.getFrequencyThreshold()
		).setMaxTerms(
			customFacetPortletPreferences.getMaxTerms()
		).setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse)
		).setParameterName(
			parameterName
		).setParameterValues(
			parameterValuesOptional
		).build();
	}

	private CustomFacetDisplayContext _createCustomFacetDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		try {
			return _buildDisplayContext(
				portletSharedSearchResponse, renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private Facet _getFacet(
		PortletSharedSearchResponse portletSharedSearchResponse,
		CustomFacetPortletPreferences customFacetPortletPreferences,
		RenderRequest renderRequest) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getFederatedSearchResponse(
				customFacetPortletPreferences.getFederatedSearchKeyOptional());

		return searchResponse.withFacetContextGet(
			facetContext -> facetContext.getFacet(
				_getPortletId(renderRequest)));
	}

	private HttpServletRequest _getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	private String _getParameterName(
		CustomFacetPortletPreferences customFacetPortletPreferences) {

		Optional<String> optional = Stream.of(
			customFacetPortletPreferences.getParameterNameOptional(),
			customFacetPortletPreferences.getAggregationFieldOptional()
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).findFirst();

		return optional.orElse("customfield");
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

	private String _getPortletId(RenderRequest renderRequest) {
		return _portal.getPortletId(renderRequest);
	}

	@Reference
	private Portal _portal;

}