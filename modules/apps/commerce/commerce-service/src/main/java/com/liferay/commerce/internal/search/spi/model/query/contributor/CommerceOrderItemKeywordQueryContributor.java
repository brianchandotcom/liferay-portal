/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = KeywordQueryContributor.class
)
public class CommerceOrderItemKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.NAME, true);
		_queryHelper.addSearchTerm(booleanQuery, searchContext, "sku", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				_expandoQueryContributor.contribute(
					expandoAttributes, booleanQuery,
					new String[] {CommerceOrderItem.class.getName()},
					searchContext);
			}
		}

		if (Validator.isNotNull(keywords)) {
			keywords = StringUtil.toLowerCase(keywords);

			BooleanQuery searchQuery = new BooleanQuery();

			booleanQuery.add(
				new TermQuery("sku.1_10_ngram", keywords),
				BooleanClauseOccur.SHOULD);

			MultiMatchQuery multiMatchQuery = new MultiMatchQuery(
				searchContext.getKeywords());

			multiMatchQuery.addField("sku");
			multiMatchQuery.addField("sku.reverse");
			multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

			booleanQuery.add(multiMatchQuery, BooleanClauseOccur.SHOULD);

			if (searchContext.isAndSearch()) {
				searchQuery.add(booleanQuery, BooleanClauseOccur.MUST);
			}
			else {
				searchQuery.add(booleanQuery, BooleanClauseOccur.SHOULD);
			}
		}
	}

	@Reference
	private ExpandoQueryContributor _expandoQueryContributor;

	@Reference
	private QueryHelper _queryHelper;

}