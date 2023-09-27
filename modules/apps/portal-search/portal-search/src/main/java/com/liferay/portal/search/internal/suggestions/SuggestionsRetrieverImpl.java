/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
@Component(service = SuggestionsRetriever.class)
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

		try {
			return suggestionsContributor.getSuggestionsContributorResults(
				liferayPortletRequest, liferayPortletResponse, searchContext,
				suggestionsContributorConfiguration);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SuggestionsRetrieverImpl.class);

	private ServiceTrackerMap<String, SuggestionsContributor>
		_suggestionsContributorServiceTrackerMap;

}