/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.query.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.model.CPInstance",
	service = KeywordQueryContributor.class
)
public class CPInstanceKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, CPField.EXTERNAL_REFERENCE_CODE,
			false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.CONTENT, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, Field.NAME, false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.NAME, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				_expandoQueryContributor.contribute(
					expandoAttributes, booleanQuery,
					new String[] {CPInstance.class.getName()}, searchContext);
			}
		}

		if (Validator.isNotNull(keywords)) {
			try {
				keywords = StringUtil.toLowerCase(keywords);

				BooleanQuery searchQuery = new BooleanQueryImpl();

				booleanQuery.add(
					new TermQueryImpl("sku.1_10_ngram", keywords),
					BooleanClauseOccur.SHOULD);

				MultiMatchQuery multiMatchQuery = new MultiMatchQuery(
					searchContext.getKeywords());

				multiMatchQuery.addField(CPField.SKU);
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
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	@Reference
	private ExpandoQueryContributor _expandoQueryContributor;

	@Reference
	private QueryHelper _queryHelper;

}