/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = KeywordQueryContributor.class
)
public class DDMFormInstanceRecordKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		queryHelper.addSearchTerm(
			booleanQuery, searchContext, Field.USER_NAME, false);

		_addSearchLocalizedTerm(booleanQuery, Field.TITLE, searchContext);
		_addSearchLocalizedTerm(booleanQuery, "ddmContent", searchContext);
	}

	@Reference
	protected QueryHelper queryHelper;

	private void _addLocalizedBooleanQuery(
		BooleanQuery booleanQuery, BooleanQuery localizedBooleanQuery,
		SearchContext searchContext) {

		BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.SHOULD;

		if (searchContext.isAndSearch()) {
			booleanClauseOccur = BooleanClauseOccur.MUST;
		}

		try {
			booleanQuery.add(localizedBooleanQuery, booleanClauseOccur);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to add localized boolean query ",
						localizedBooleanQuery, " with boolean clause occur ",
						booleanClauseOccur),
					parseException);
			}
		}
	}

	private void _addLocalizedFields(
		BooleanQuery booleanQuery, String fieldName,
		SearchContext searchContext, String value) {

		for (String localizedFieldName :
				_searchLocalizationHelper.getLocalizedFieldNames(
					new String[] {fieldName}, searchContext)) {

			try {
				booleanQuery.addTerm(localizedFieldName, value, false);
			}
			catch (ParseException parseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unable to add search term to query field:",
							localizedFieldName, " value:", value, " like:",
							false),
						parseException);
				}
			}
		}
	}

	private void _addSearchLocalizedTerm(
		BooleanQuery booleanQuery, String fieldName,
		SearchContext searchContext) {

		String value = GetterUtil.getString(
			searchContext.getAttribute(fieldName), searchContext.getKeywords());

		if (Validator.isBlank(value)) {
			return;
		}

		if (Validator.isBlank(searchContext.getKeywords())) {
			BooleanQuery localizedBooleanQuery = new BooleanQueryImpl();

			_addLocalizedFields(
				localizedBooleanQuery, fieldName, searchContext, value);

			_addLocalizedBooleanQuery(
				booleanQuery, localizedBooleanQuery, searchContext);
		}
		else {
			_addLocalizedFields(booleanQuery, fieldName, searchContext, value);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordKeywordQueryContributor.class);

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}