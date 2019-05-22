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

package com.liferay.portal.search.web.internal.search.options.portlet.shared.search;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.search.options.constants.SearchOptionsPortletKeys;
import com.liferay.portal.search.web.internal.search.options.portlet.SearchOptionsPortletPreferences;
import com.liferay.portal.search.web.internal.search.options.portlet.SearchOptionsPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchOptionsPortletKeys.SEARCH_OPTIONS,
	service = PortletSharedSearchContributor.class
)
public class SearchOptionsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchOptionsPortletPreferences searchOptionsPortletPreferences =
			new SearchOptionsPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				searchOptionsPortletPreferences.
					getFederatedSearchKeyOptional());

		searchRequestBuilder.basicFacetSelection(
			searchOptionsPortletPreferences.isBasicFacetSelection()
		).emptySearchEnabled(
			searchOptionsPortletPreferences.isAllowEmptySearches()
		);
	}

}