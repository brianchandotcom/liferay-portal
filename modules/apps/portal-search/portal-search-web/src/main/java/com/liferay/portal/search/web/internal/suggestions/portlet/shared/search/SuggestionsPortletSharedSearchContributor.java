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

package com.liferay.portal.search.web.internal.suggestions.portlet.shared.search;

import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.search.web.internal.suggestions.constants.SuggestionsPortletKeys;
import com.liferay.portal.search.web.internal.suggestions.portlet.SuggestionsPortletPreferences;
import com.liferay.portal.search.web.internal.suggestions.portlet.SuggestionsPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SuggestionsPortletKeys.SUGGESTIONS,
	service = PortletSharedSearchContributor.class
)
public class SuggestionsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SuggestionsPortletPreferences suggestionsPortletPreferences =
			new SuggestionsPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		setUpQueryIndexing(
			suggestionsPortletPreferences, portletSharedSearchSettings);
		setUpRelatedSuggestions(
			suggestionsPortletPreferences, portletSharedSearchSettings);
		setUpSpellCheckSuggestion(
			suggestionsPortletPreferences, portletSharedSearchSettings);
	}

	protected void setUpQueryIndexing(
		SuggestionsPortletPreferences suggestionsPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setQueryIndexingEnabled(
			suggestionsPortletPreferences.isQueryIndexingEnabled());
		queryConfig.setQueryIndexingThreshold(
			suggestionsPortletPreferences.getQueryIndexingThreshold());
	}

	protected void setUpRelatedSuggestions(
		SuggestionsPortletPreferences suggestionsPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setQuerySuggestionEnabled(
			suggestionsPortletPreferences.isRelatedQueriesSuggestionsEnabled());
		queryConfig.setQuerySuggestionScoresThreshold(
			suggestionsPortletPreferences.
				getRelatedQueriesSuggestionsDisplayThreshold());
		queryConfig.setQuerySuggestionsMax(
			suggestionsPortletPreferences.getRelatedQueriesSuggestionsMax());
	}

	protected void setUpSpellCheckSuggestion(
		SuggestionsPortletPreferences suggestionsPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setCollatedSpellCheckResultEnabled(
			suggestionsPortletPreferences.isSpellCheckSuggestionEnabled());

		queryConfig.setCollatedSpellCheckResultScoresThreshold(
			suggestionsPortletPreferences.
				getSpellCheckSuggestionDisplayThreshold());
	}

}