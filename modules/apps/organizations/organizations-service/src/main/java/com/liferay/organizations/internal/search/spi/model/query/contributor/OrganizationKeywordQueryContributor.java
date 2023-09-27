/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Fabiano Nazar
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Organization",
	service = KeywordQueryContributor.class
)
public class OrganizationKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		queryHelper.addSearchTerm(booleanQuery, searchContext, "city", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "country", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "name", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "region", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "street", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "type", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "zip", false);
	}

	@Reference
	protected QueryHelper queryHelper;

}