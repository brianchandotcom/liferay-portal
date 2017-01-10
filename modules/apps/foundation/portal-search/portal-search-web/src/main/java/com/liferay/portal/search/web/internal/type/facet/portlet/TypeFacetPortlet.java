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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacetFactory;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetEntriesSearchFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetWebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/type/facet/view.jsp",
		"javax.portlet.name=" + TypeFacetPortletKeys.TYPE_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class TypeFacetPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		TypeFacetPortletPreferences typeFacetPortletPreferences =
			new TypeFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		Facet facet = buildFacet(
			typeFacetPortletPreferences, portletSharedSearchSettings);

		portletSharedSearchSettings.addFacet(facet);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		AssetEntriesSearchFacetDisplayContext
			assetEntriesSearchFacetDisplayContext = buildDisplayContext(
				portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			TypeFacetWebKeys.DISPLAY_CONTEXT,
			assetEntriesSearchFacetDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	protected AssetEntriesSearchFacetDisplayContext buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Facet facet = portletSharedSearchResponse.getFacet(getFieldName());

		AssetEntriesFacetConfiguration assetEntriesFacetConfiguration =
			new AssetEntriesFacetConfigurationImpl(
				facet.getFacetConfiguration());

		TypeFacetPortletPreferences typeFacetPortletPreferences =
			new TypeFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		AssetEntriesSearchFacetDisplayBuilder
			assetEntriesSearchFacetDisplayBuilder =
				new AssetEntriesSearchFacetDisplayBuilder();

		assetEntriesSearchFacetDisplayBuilder.setClassNames(
			getAssetTypesClassNames(
				assetEntriesFacetConfiguration, typeFacetPortletPreferences));
		assetEntriesSearchFacetDisplayBuilder.setFacet(facet);
		assetEntriesSearchFacetDisplayBuilder.setFrequencyThreshold(
			assetEntriesFacetConfiguration.getFrequencyThreshold());
		assetEntriesSearchFacetDisplayBuilder.setFrequenciesVisible(
			typeFacetPortletPreferences.isFrequenciesVisible());
		assetEntriesSearchFacetDisplayBuilder.setLocale(
			getLocale(portletSharedSearchResponse, renderRequest));

		String parameterName = typeFacetPortletPreferences.getParameterName();

		assetEntriesSearchFacetDisplayBuilder.setParameterName(parameterName);

		Optional<List<String>> typesOptional = getParameterValues(
			parameterName, portletSharedSearchResponse, renderRequest);

		typesOptional.ifPresent(
			assetEntriesSearchFacetDisplayBuilder::setParameterValues);

		return assetEntriesSearchFacetDisplayBuilder.build();
	}

	protected Facet buildFacet(
		TypeFacetPortletPreferences typeFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		AssetEntriesFacetBuilder assetEntriesFacetBuilder =
			new AssetEntriesFacetBuilder(assetEntriesFacetFactory);

		assetEntriesFacetBuilder.setCompanyId(
			getCompanyId(portletSharedSearchSettings));
		assetEntriesFacetBuilder.setFrequencyThreshold(
			typeFacetPortletPreferences.getFrequencyThreshold());
		assetEntriesFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchSettings.getParameterValues(
				typeFacetPortletPreferences.getParameterName());

		parameterValuesOptional.ifPresent(
			assetEntriesFacetBuilder::setSelectedTypes);

		return assetEntriesFacetBuilder.build();
	}

	protected String[] getAssetTypesClassNames(
		AssetEntriesFacetConfiguration assetEntriesFacetConfiguration,
		TypeFacetPortletPreferences typeFacetPortletPreferences) {

		Optional<String[]> assetTypesArray =
			typeFacetPortletPreferences.getAssetTypesArray();

		return assetTypesArray.orElse(
			assetEntriesFacetConfiguration.getClassNames());
	}

	protected long getCompanyId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getCompanyId();
	}

	protected String getFieldName() {
		Facet facet = assetEntriesFacetFactory.newInstance(new SearchContext());

		return facet.getFieldName();
	}

	protected Locale getLocale(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		return themeDisplay.getLocale();
	}

	protected Optional<List<String>> getParameterValues(
		String parameterName,
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest);

		return parameterValuesOptional.map(Arrays::asList);
	}

	@Reference
	protected AssetEntriesFacetFactory assetEntriesFacetFactory;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}