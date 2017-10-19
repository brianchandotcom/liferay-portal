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

package com.liferay.portal.search.web.internal.category.facet.portlet.shared.search;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;
import com.liferay.portal.search.web.internal.category.facet.builder.AssetCategoriesFacetBuilder;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferences;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Arrays;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET
)
public class CategoryFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

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

	protected Facet buildFacet(
		CategoryFacetPortletPreferences categoryFacetPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		AssetCategoriesFacetBuilder assetCategoriesFacetBuilder =
			new AssetCategoriesFacetBuilder(categoryFacetFactory);

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

	@Reference
	protected CategoryFacetFactory categoryFacetFactory;

}