/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.MultiMatchQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrder",
	service = KeywordQueryContributor.class
)
public class CommerceOrderKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "accountName", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "addressName", false);
		_queryHelper.addSearchTerm(booleanQuery, searchContext, "city", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "countryIsoCode", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "countryName", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "externalReferenceCode", false);
		_queryHelper.addSearchTerm(booleanQuery, searchContext, "name", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "orderCreatorEmailAddress", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "orderItemNames", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "purchaseOrderNumber", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "regionName", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "shippingAddressExternalReference",
			false);
		_queryHelper.addSearchTerm(booleanQuery, searchContext, "sku", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "street1", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "street2", false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, "street3", false);
		_queryHelper.addSearchTerm(booleanQuery, searchContext, "zip", false);

		if (Validator.isNotNull(keywords)) {
			keywords = StringUtil.toLowerCase(keywords);

			booleanQuery.add(
				_getTrailingWildcardQuery(Field.ENTRY_CLASS_PK, keywords),
				BooleanClauseOccur.SHOULD);

			BooleanQuery searchBooleanQuery = new BooleanQuery();

			searchBooleanQuery.add(
				new TermQuery("accountName.1_10_ngram", keywords),
				BooleanClauseOccur.SHOULD);

			MultiMatchQuery multiMatchQuery = new MultiMatchQuery(keywords);

			multiMatchQuery.addFields("accountName", "accountName.reverse");
			multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

			searchBooleanQuery.add(multiMatchQuery, BooleanClauseOccur.SHOULD);

			if (searchContext.isAndSearch()) {
				booleanQuery.add(searchBooleanQuery, BooleanClauseOccur.MUST);
			}
			else {
				booleanQuery.add(searchBooleanQuery, BooleanClauseOccur.SHOULD);
			}
		}
	}

	private WildcardQuery _getTrailingWildcardQuery(
		String field, String value) {

		return new WildcardQuery(field, value + StringPool.STAR);
	}

	@Reference
	private QueryHelper _queryHelper;

}