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

package com.liferay.portal.search.web.internal.category.facet.portlet;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetBuilder;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetConfiguration;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetConfigurationImpl;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetFactory;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetCategoriesSearchFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetCategoryPermissionCheckerImpl;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.Arrays;
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
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-category-facet",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Category Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/category/facet/view.jsp",
		"javax.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CategoryFacetPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		CategoryFacetPortletPreferences categoryFacetPortletPreferences =
			new CategoryFacetPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		Facet facet = buildFacet(
			categoryFacetPortletPreferences, portletSharedSearchSettings);

		portletSharedSearchSettings.addFacet(facet);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		AssetCategoriesSearchFacetDisplayContext
			assetCategoriesSearchFacetDisplayContext = buildDisplayContext(
				portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			assetCategoriesSearchFacetDisplayContext);

		if (assetCategoriesSearchFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	protected AssetCategoriesSearchFacetDisplayContext buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Facet facet = portletSharedSearchResponse.getFacet(getFieldName());

		CategoryFacetPortletPreferences categoryFacetPortletPreferences =
			new CategoryFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		AssetCategoriesFacetConfiguration assetCategoriesFacetConfiguration =
			new AssetCategoriesFacetConfigurationImpl(
				facet.getFacetConfiguration());

		AssetCategoriesSearchFacetDisplayBuilder
			assetCategoriesSearchFacetDisplayBuilder =
				new AssetCategoriesSearchFacetDisplayBuilder();

		assetCategoriesSearchFacetDisplayBuilder.setAssetCategoryLocalService(
			assetCategoryLocalService);
		assetCategoriesSearchFacetDisplayBuilder.setDisplayStyle(
			categoryFacetPortletPreferences.getDisplayStyle());
		assetCategoriesSearchFacetDisplayBuilder.setFacet(facet);
		assetCategoriesSearchFacetDisplayBuilder.setFrequenciesVisible(
			categoryFacetPortletPreferences.isFrequenciesVisible());
		assetCategoriesSearchFacetDisplayBuilder.setFrequencyThreshold(
			assetCategoriesFacetConfiguration.getFrequencyThreshold());
		assetCategoriesSearchFacetDisplayBuilder.setMaxTerms(
			assetCategoriesFacetConfiguration.getMaxTerms());

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		assetCategoriesSearchFacetDisplayBuilder.setLocale(
			themeDisplay.getLocale());
		assetCategoriesSearchFacetDisplayBuilder.
			setAssetCategoryPermissionChecker(
				new AssetCategoryPermissionCheckerImpl(
					themeDisplay.getPermissionChecker()));

		String parameterName =
			categoryFacetPortletPreferences.getParameterName();

		assetCategoriesSearchFacetDisplayBuilder.setParameterName(
			parameterName);

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest);

		parameterValuesOptional.ifPresent(
			assetCategoriesSearchFacetDisplayBuilder::setParameterValues);

		return assetCategoriesSearchFacetDisplayBuilder.build();
	}

	protected Facet buildFacet(
		CategoryFacetPortletPreferences categoryFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		AssetCategoriesFacetBuilder assetCategoriesFacetBuilder =
			new AssetCategoriesFacetBuilder(assetCategoriesFacetFactory);

		assetCategoriesFacetBuilder.setFrequencyThreshold(
			categoryFacetPortletPreferences.getFrequencyThreshold());
		assetCategoriesFacetBuilder.setMaxTerms(
			categoryFacetPortletPreferences.getMaxTerms());
		assetCategoriesFacetBuilder.setSearchContext(
			portletSharedSearchSettings.getSearchContext());

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchSettings.getParameterValues(
				categoryFacetPortletPreferences.getParameterName());

		Optional<long[]> categoryIdsOptional = parameterValuesOptional.map(
			parameterValues -> ListUtil.toLongArray(
				Arrays.asList(parameterValues), GetterUtil::getLong));

		categoryIdsOptional.ifPresent(
			assetCategoriesFacetBuilder::setSelectedCategoryIds);

		return assetCategoriesFacetBuilder.build();
	}

	protected String getFieldName() {
		Facet facet = assetCategoriesFacetFactory.newInstance(null);

		return facet.getFieldName();
	}

	protected AssetCategoriesFacetFactory assetCategoriesFacetFactory =
		new AssetCategoriesFacetFactory();

	@Reference
	protected AssetCategoryLocalService assetCategoryLocalService;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}