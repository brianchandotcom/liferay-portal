/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.query.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ExpandoQueryContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
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
	property = "indexer.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = KeywordQueryContributor.class
)
public class CPDefinitionKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, CPField.ASSET_CATEGORY_NAMES, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, CPField.EXTERNAL_REFERENCE_CODE,
			false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, CPField.SHORT_DESCRIPTION, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, CPField.SKUS, false);
		_queryHelper.addSearchTerm(
			booleanQuery, searchContext, CPField.SPECIFICATION_VALUES_NAMES,
			false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, CPField.SPECIFICATION_VALUES_NAMES,
			false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.CONTENT, false);
		_queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.DESCRIPTION, false);
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
					new String[] {CPDefinition.class.getName()}, searchContext);
			}
		}

		if (Validator.isNotNull(keywords)) {
			try {
				keywords = StringUtil.toLowerCase(keywords);

				booleanQuery.add(
					_getTrailingWildcardQuery(
						CPField.ASSET_CATEGORY_NAMES, keywords),
					BooleanClauseOccur.SHOULD);

				BooleanQuery searchQuery = new BooleanQueryImpl();

				searchQuery.add(
					new TermQueryImpl(CPField.SKUS + ".1_10_ngram", keywords),
					BooleanClauseOccur.SHOULD);

				MultiMatchQuery multiMatchQuery = new MultiMatchQuery(keywords);

				multiMatchQuery.addFields(
					CPField.SKUS, CPField.SKUS + ".reverse");
				multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

				searchQuery.add(multiMatchQuery, BooleanClauseOccur.SHOULD);

				if (searchContext.isAndSearch()) {
					booleanQuery.add(searchQuery, BooleanClauseOccur.MUST);
				}
				else {
					booleanQuery.add(searchQuery, BooleanClauseOccur.SHOULD);
				}
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	private WildcardQuery _getTrailingWildcardQuery(
		String field, String value) {

		return new WildcardQueryImpl(field, value + StringPool.STAR);
	}

	@Reference
	private ExpandoQueryContributor _expandoQueryContributor;

	@Reference
	private QueryHelper _queryHelper;

}