/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions.spi.asah.site.activity;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.rest.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.spi.suggestions.SuggestionsContributor;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.spi.constants.AsahSuggestionsConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.internal.configuration.SiteActivityAsahConfiguration",
	property = "search.suggestions.contributor.name=topSearchSiteActivity",
	service = SuggestionsContributor.class
)
public class TopSearchSiteActivityAsahSuggestionsContributor
	extends BaseSiteActivityAsahSuggestionsContributor
	implements SuggestionsContributor {

	@Override
	public SuggestionsContributorResults getSuggestionsContributorResults(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContext searchContext,
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		return getSuggestionsContributorResults(
			AsahSuggestionsConstants.PAGES,
			AsahSuggestionsConstants.SEARCH_KEYWORDS, searchContext,
			"counts,desc,lastModifiedDate,desc,keywords,asc",
			suggestionsContributorConfiguration);
	}

}