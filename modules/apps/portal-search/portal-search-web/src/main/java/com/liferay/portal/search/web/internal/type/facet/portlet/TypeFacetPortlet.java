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

package com.liferay.portal.search.web.internal.type.facet.portlet;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.builder.AssetEntriesSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-type-facet",
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
		"javax.portlet.display-name=Type Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/type/facet/view.jsp",
		"javax.portlet.name=" + TypeFacetPortletKeys.TYPE_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class TypeFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		AssetEntriesSearchFacetDisplayContext
			assetEntriesSearchFacetDisplayContext = _buildDisplayContext(
				portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			assetEntriesSearchFacetDisplayContext);

		if (assetEntriesSearchFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	@Reference
	protected SearchableAssetClassNamesProvider
		searchableAssetClassNamesProvider;

	private AssetEntriesSearchFacetDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Facet facet = portletSharedSearchResponse.getFacet(
			_getAggregationName(renderRequest));

		AssetEntriesFacetConfiguration assetEntriesFacetConfiguration =
			new AssetEntriesFacetConfigurationImpl(
				facet.getFacetConfiguration());

		TypeFacetPortletPreferences typeFacetPortletPreferences =
			new TypeFacetPortletPreferencesImpl(
				objectDefinitionLocalService,
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest),
				searchableAssetClassNamesProvider);

		AssetEntriesSearchFacetDisplayContextBuilder
			assetEntriesSearchFacetDisplayContextBuilder =
				_createAssetEntriesSearchFacetDisplayContextBuilder(
					renderRequest);

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		assetEntriesSearchFacetDisplayContextBuilder.setClassNames(
			_getAssetTypesClassNames(
				typeFacetPortletPreferences, themeDisplay));

		assetEntriesSearchFacetDisplayContextBuilder.setFacet(facet);
		assetEntriesSearchFacetDisplayContextBuilder.setFrequencyThreshold(
			assetEntriesFacetConfiguration.getFrequencyThreshold());
		assetEntriesSearchFacetDisplayContextBuilder.setFrequenciesVisible(
			typeFacetPortletPreferences.isFrequenciesVisible());
		assetEntriesSearchFacetDisplayContextBuilder.setLocale(
			themeDisplay.getLocale());
		assetEntriesSearchFacetDisplayContextBuilder.
			setPaginationStartParameterName(
				_getPaginationStartParameterName(portletSharedSearchResponse));

		String parameterName = typeFacetPortletPreferences.getParameterName();

		assetEntriesSearchFacetDisplayContextBuilder.setParameterName(
			parameterName);

		assetEntriesSearchFacetDisplayContextBuilder.setTypeNames(
			_getAssetTypesTypeNames(typeFacetPortletPreferences, themeDisplay));

		SearchOptionalUtil.copy(
			() -> _getParameterValuesOptional(
				parameterName, portletSharedSearchResponse, renderRequest),
			assetEntriesSearchFacetDisplayContextBuilder::setParameterValues);

		return assetEntriesSearchFacetDisplayContextBuilder.build();
	}

	private AssetEntriesSearchFacetDisplayContextBuilder
		_createAssetEntriesSearchFacetDisplayContextBuilder(
			RenderRequest renderRequest) {

		try {
			return new AssetEntriesSearchFacetDisplayContextBuilder(
				renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private String _getAggregationName(RenderRequest renderRequest) {
		return portal.getPortletId(renderRequest);
	}

	private String[] _getAssetTypesClassNames(
		TypeFacetPortletPreferences typeFacetPortletPreferences,
		ThemeDisplay themeDisplay) {

		return typeFacetPortletPreferences.getCurrentAssetTypesArray(
			themeDisplay.getCompanyId());
	}

	private Map<String, String> _getAssetTypesTypeNames(
		TypeFacetPortletPreferences typeFacetPortletPreferences,
		ThemeDisplay themeDisplay) {

		Map<String, String> assetTypesTypeNames = new HashMap<>();

		String[] classNames = _getAssetTypesClassNames(
			typeFacetPortletPreferences, themeDisplay);

		for (String className : classNames) {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			String typeName = className;

			if (assetRendererFactory != null) {
				typeName = assetRendererFactory.getTypeName(
					themeDisplay.getLocale());
			}
			else if (className.startsWith(
						ObjectDefinition.class.getName() + "#")) {

				String[] parts = StringUtil.split(className, "#");

				ObjectDefinition objectDefinition =
					objectDefinitionLocalService.fetchObjectDefinition(
						Long.valueOf(parts[1]));

				typeName = objectDefinition.getLabel(themeDisplay.getLocale());
			}

			assetTypesTypeNames.put(className, typeName);
		}

		return assetTypesTypeNames;
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