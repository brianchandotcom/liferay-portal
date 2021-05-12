/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.internal.search.spi.model.query.contributor;

import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.TermRangeQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.object.model.ObjectEntry",
	service = KeywordQueryContributor.class
)
public class ObjectEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (_log.isDebugEnabled()) {
			_log.debug("Keywords " + keywords);
		}

		if (Validator.isNull(keywords)) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		long objectDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("objectDefinitionId"));

		if (_log.isDebugEnabled()) {
			_log.debug("Object definition ID " + objectDefinitionId);
		}

		if (objectDefinitionId == 0) {
			return;
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			boolean indexed = true; //objectField.isIndexed(); //TODO

			if (!indexed) {
				continue;
			}

			String name = objectField.getName();
			String type = objectField.getType();
			String localeString = ""; //objectField.getLocaleString(); //TODO
			boolean indexedAsKeyword = //objectField.isIndexedAsKeyword(); //TODO
				(name.equals("emailAddress") ||
				name.equals("emailAddressDomain"));

			if (_log.isDebugEnabled()) {
				_log.debug("Add search query for object field " + name);
			}

			try {
				BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();

				if (indexedAsKeyword) {
					keywords = StringUtil.toLowerCase(keywords);

					nestedBooleanQuery.add(
						new WildcardQueryImpl(
							"nestedFieldArray.value_keyword",
							keywords + StringPool.STAR),
						BooleanClauseOccur.MUST);

					nestedBooleanQuery.add(
						new TermQueryImpl(
							"nestedFieldArray.value_keyword", keywords),
						BooleanClauseOccur.SHOULD);
				}
				else if (type.equals("BigDecimal")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_double");
				}
				else if (type.equals("Blob")) {
					if (_log.isDebugEnabled()) {
						_log.debug("Blob field " + name + " is not searchable");
					}
				}
				else if (type.equals("Boolean")) {
					if (StringUtil.equalsIgnoreCase(keywords, "true") ||
						StringUtil.equalsIgnoreCase(keywords, "false")) {

						nestedBooleanQuery.add(
							new TermQueryImpl(
								"nestedFieldArray.value_boolean",
								StringUtil.toLowerCase(keywords)),
							BooleanClauseOccur.MUST);
					}
				}
				else if (type.equals("Date")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_date");
				}
				else if (type.equals("Double")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_double");
				}
				else if (type.equals("Integer")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_integer");
				}
				else if (type.equals("Long")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_long");
				}
				else if (type.equals("String")) {
					Locale locale = searchContext.getLocale();

					if (!Validator.isBlank(localeString) &&
						localeString.equals(locale.toString())) {

						nestedBooleanQuery.add(
							new MatchQuery(
								"nestedFieldArray.value_" + localeString,
								keywords),
							BooleanClauseOccur.MUST);
					}
					else {
						nestedBooleanQuery.add(
							new MatchQuery(
								"nestedFieldArray.value_text", keywords),
							BooleanClauseOccur.MUST);
					}
				}

				if (nestedBooleanQuery.hasClauses()) {
					nestedBooleanQuery.add(
						new TermQueryImpl("nestedFieldArray.fieldName", name),
						BooleanClauseOccur.MUST);

					NestedQuery nestedQuery = new NestedQuery(
						"nestedFieldArray", nestedBooleanQuery);

					booleanQuery.add(nestedQuery, BooleanClauseOccur.SHOULD);
				}
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	private void _addRangeQuery(
			String keywords, BooleanQuery booleanQuery, String field)
		throws ParseException {

		String[] range = RangeParserUtil.parserRange(keywords);

		String lowerTerm = range[0];
		String upperTerm = range[1];

		if ((lowerTerm != null) && (upperTerm != null)) {
			booleanQuery.add(
				new TermRangeQueryImpl(field, lowerTerm, upperTerm, true, true),
				BooleanClauseOccur.MUST);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryKeywordQueryContributor.class);

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private QueryHelper _queryHelper;

}