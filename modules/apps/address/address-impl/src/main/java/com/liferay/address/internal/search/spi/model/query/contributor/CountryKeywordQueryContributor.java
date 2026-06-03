/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balazs Breier
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Country",
	service = KeywordQueryContributor.class
)
public class CountryKeywordQueryContributor implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		_queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			Field.NAME, false);
		_queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			"a2", false);
		_queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			"a3", false);
	}

	@Reference
	private QueryHelper _queryHelper;

}