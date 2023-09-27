/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = KeywordQueryContributor.class
)
public class DDLRecordKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		queryHelper.addSearchTerm(
			booleanQuery, keywordQueryContributorHelper.getSearchContext(),
			Field.USER_NAME, false);

		for (String localizedFieldName :
				_searchLocalizationHelper.getLocalizedFieldNames(
					new String[] {"ddmContent"},
					keywordQueryContributorHelper.getSearchContext())) {

			queryHelper.addSearchTerm(
				booleanQuery, keywordQueryContributorHelper.getSearchContext(),
				localizedFieldName, false);
		}
	}

	@Reference
	protected QueryHelper queryHelper;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}