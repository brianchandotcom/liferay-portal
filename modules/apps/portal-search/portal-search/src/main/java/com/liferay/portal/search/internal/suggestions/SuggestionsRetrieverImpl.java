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

package com.liferay.portal.search.internal.suggestions;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.spi.suggestions.SuggestionsContributor;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsRetriever;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SuggestionsRetriever.class)
public class SuggestionsRetrieverImpl implements SuggestionsRetriever {

	@Override
	public List<SuggestionsContributorResults> getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext) {

		return TransformUtil.transformToList(
			(SuggestionsContributorConfiguration[])searchContext.getAttribute(
				"search.suggestions.contributor.configurations"),
			suggestionsContributorConfiguration ->
				_getSuggestionsContributorResults(
					liferayPortletRequest, liferayPortletResponse,
					searchContext, suggestionsContributorConfiguration));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_suggestionsContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, SuggestionsContributor.class,
				"search.suggestions.contributor.name");
	}

	@Deactivate
	protected void deactivate() {
		_suggestionsContributorServiceTrackerMap.close();
	}

	private SuggestionsContributorResults _getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		SuggestionsContributor suggestionsContributor =
			_suggestionsContributorServiceTrackerMap.getService(
				suggestionsContributorConfiguration.getContributorName());

		if (suggestionsContributor == null) {
			return null;
		}

		return suggestionsContributor.getSuggestionsContributorResults(
			liferayPortletRequest, liferayPortletResponse, searchContext,
			suggestionsContributorConfiguration);
	}

	private ServiceTrackerMap<String, SuggestionsContributor>
		_suggestionsContributorServiceTrackerMap;

}