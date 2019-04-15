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

package com.liferay.portal.search.web.internal.search.insights.portlet.shared.search;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.internal.search.insights.constants.SearchInsightsPortletKeys;
import com.liferay.portal.search.web.internal.search.insights.portlet.SearchInsightsPortletPreferences;
import com.liferay.portal.search.web.internal.search.insights.portlet.SearchInsightsPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchInsightsPortletKeys.SEARCH_INSIGHTS,
	service = PortletSharedSearchContributor.class
)
public class SearchInsightsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchInsightsPortletPreferences searchInsightsPortletPreferences =
			new SearchInsightsPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				searchInsightsPortletPreferences.
					getFederatedSearchKeyOptional());

		searchRequestBuilder.explain(
			searchInsightsPortletPreferences.isExplain()
		).includeResponseString(
			true
		);
	}

}