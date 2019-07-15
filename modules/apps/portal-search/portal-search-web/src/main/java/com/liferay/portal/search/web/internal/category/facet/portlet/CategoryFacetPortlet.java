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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetConfiguration;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetConfigurationImpl;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetCategoriesSearchFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetCategoryPermissionCheckerImpl;
import com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

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
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/category/facet/view.jsp",
		"javax.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class CategoryFacetPortlet extends MVCPortlet {

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

		Facet facet = portletSharedSearchResponse.getFacet(
			getAggregationName(renderRequest));

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

		Group group = themeDisplay.getScopeGroup();

		Group stagingGroup = group.getStagingGroup();

		if (stagingGroup != null) {
			assetCategoriesSearchFacetDisplayBuilder.setExcludedGroupId(
				stagingGroup.getGroupId());
		}

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

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest),
			assetCategoriesSearchFacetDisplayBuilder::setParameterValues);

		return assetCategoriesSearchFacetDisplayBuilder.build();
	}

	protected String getAggregationName(RenderRequest renderRequest) {
		return portal.getPortletId(renderRequest);
	}

	@Reference
	protected AssetCategoryLocalService assetCategoryLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

}