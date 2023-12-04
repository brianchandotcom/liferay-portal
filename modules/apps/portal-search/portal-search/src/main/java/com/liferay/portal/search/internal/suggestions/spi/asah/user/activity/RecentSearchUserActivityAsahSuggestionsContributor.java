/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah.user.activity;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.spi.suggestions.SuggestionsContributor;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.spi.constants.AsahSuggestionsConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gustavo Lima
 */
@Component(
	configurationPid = "com.liferay.portal.search.internal.configuration.UserActivityAsahConfiguration",
	property = "search.suggestions.contributor.name=recentSearchesUserActivity",
	service = SuggestionsContributor.class
)
public class RecentSearchUserActivityAsahSuggestionsContributor
	extends BaseUserActivityAsahSuggestionsContributor
	implements SuggestionsContributor {

	@Override
	public SuggestionsContributorResults getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		return getSuggestionsContributorResults(
			AsahSuggestionsConstants.INDIVIDUALS,
			AsahSuggestionsConstants.SEARCH_KEYWORDS, searchContext,
			"counts,displayLanguageId,keywords,lastModifiedDate,createDate" +
				",groupId",
			suggestionsContributorConfiguration);
	}

}