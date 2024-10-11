/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.type.facet.portlet.shared.search;

import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.facet.type.TypeFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferences;
import com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(
	property = "javax.portlet.name=" + TypeFacetPortletKeys.TYPE_FACET,
	service = PortletSharedSearchContributor.class
)
public class TypeFacetPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		TypeFacetPortletPreferences typeFacetPortletPreferences =
			new TypeFacetPortletPreferencesImpl(
				objectDefinitionLocalService,
				portletSharedSearchSettings.getPortletPreferences(),
				searchableAssetClassNamesProvider);

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getSearchRequestBuilder();

		typeFacetSearchContributor.contribute(
			searchRequestBuilder,
			typeFacetBuilder -> typeFacetBuilder.aggregationName(
				portletSharedSearchSettings.getPortletId()
			).frequencyThreshold(
				typeFacetPortletPreferences.getFrequencyThreshold()
			).selectedEntryClassNames(
				portletSharedSearchSettings.getParameterValues(
					typeFacetPortletPreferences.getParameterName())
			));

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		searchRequestBuilder.withSearchContext(
			searchContext -> {
				Set<String> assetEntryClassNamesSet = new HashSet<>(
					Arrays.asList(searchContext.getEntryClassNames()));

				Collections.addAll(
					assetEntryClassNamesSet,
					typeFacetPortletPreferences.getCurrentAssetTypesArray(
						themeDisplay.getCompanyId()));

				searchContext.setEntryClassNames(
					assetEntryClassNamesSet.toArray(new String[0]));
			});
	}

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected SearchableAssetClassNamesProvider
		searchableAssetClassNamesProvider;

	@Reference
	protected TypeFacetSearchContributor typeFacetSearchContributor;

}