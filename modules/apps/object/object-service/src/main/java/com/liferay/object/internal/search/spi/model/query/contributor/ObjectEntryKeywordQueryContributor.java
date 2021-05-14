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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
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
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		if (Validator.isNotNull(keywords)) {
			try {
				booleanQuery.add(
					new TermQueryImpl(Field.ENTRY_CLASS_PK, keywords),
					BooleanClauseOccur.SHOULD);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			if (!objectField.isIndexed()) {
				continue;
			}

			String name = objectField.getName();

			String fieldKeywords = _getKeywords(keywords, searchContext, name);

			if (Validator.isNull(fieldKeywords)) {
				continue;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Add search term ", fieldKeywords, " for object field ",
						name));
			}

			String type = objectField.getType();

			try {
				BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();

				if (objectField.isIndexedAsKeyword()) {
					String lowerCaseKeywords = StringUtil.toLowerCase(
						fieldKeywords);

					nestedBooleanQuery.add(
						new WildcardQueryImpl(
							"nestedFieldArray.value_keyword",
							lowerCaseKeywords + StringPool.STAR),
						BooleanClauseOccur.MUST);

					nestedBooleanQuery.add(
						new TermQueryImpl(
							"nestedFieldArray.value_keyword",
							lowerCaseKeywords),
						BooleanClauseOccur.SHOULD);
				}
				else if (type.equals("BigDecimal")) {
					_addRangeQuery(
						fieldKeywords, nestedBooleanQuery,
						"nestedFieldArray.value_double");
				}
				else if (type.equals("Blob")) {
					if (_log.isDebugEnabled()) {
						_log.debug("Blob field " + name + " is not searchable");
					}
				}
				else if (type.equals("Boolean")) {
					if (StringUtil.equalsIgnoreCase(fieldKeywords, "true") ||
						StringUtil.equalsIgnoreCase(fieldKeywords, "false")) {

						nestedBooleanQuery.add(
							new TermQueryImpl(
								"nestedFieldArray.value_boolean",
								StringUtil.toLowerCase(fieldKeywords)),
							BooleanClauseOccur.MUST);
					}
				}
				else if (type.equals("Date")) {
					_addDateRangeQuery(
						fieldKeywords, nestedBooleanQuery,
						"nestedFieldArray.value_date");
				}
				else if (type.equals("Double")) {
					_addRangeQuery(
						fieldKeywords, nestedBooleanQuery,
						"nestedFieldArray.value_double");
				}
				else if (type.equals("Integer")) {
					_addRangeQuery(
						fieldKeywords, nestedBooleanQuery,
						"nestedFieldArray.value_integer");
				}
				else if (type.equals("Long")) {
					_addRangeQuery(
						fieldKeywords, nestedBooleanQuery,
						"nestedFieldArray.value_long");
				}
				else if (type.equals("String")) {
					String locale = objectField.getLocale();

					if (Validator.isBlank(locale)) {
						nestedBooleanQuery.add(
							new MatchQuery(
								"nestedFieldArray.value_text", fieldKeywords),
							BooleanClauseOccur.MUST);
					}
					else if (locale.equals(_getLocaleString(searchContext))) {
						nestedBooleanQuery.add(
							new MatchQuery(
								"nestedFieldArray.value_" + locale,
								fieldKeywords),
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

	private void _addDateRangeQuery(
			String keywords, BooleanQuery booleanQuery, String field)
		throws ParseException {

		if (Validator.isBlank(keywords)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(keywords);

		String lowerTerm = range[0];
		String upperTerm = range[1];

		if ((lowerTerm == null) || (upperTerm == null)) {
			return;
		}

		Matcher lowerTermMatcher = _pattern.matcher(lowerTerm);
		Matcher upperTermMatcher = _pattern.matcher(upperTerm);

		if (!lowerTermMatcher.matches() || !upperTermMatcher.matches()) {
			return;
		}

		booleanQuery.add(
			new TermRangeQueryImpl(field, lowerTerm, upperTerm, true, true),
			BooleanClauseOccur.MUST);
	}

	private void _addRangeQuery(
			String keywords, BooleanQuery booleanQuery, String field)
		throws ParseException {

		if (Validator.isBlank(keywords)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(keywords);

		String lowerTerm = range[0];
		String upperTerm = range[1];

		if ((lowerTerm != null) && (upperTerm != null)) {
			booleanQuery.add(
				new TermRangeQueryImpl(field, lowerTerm, upperTerm, true, true),
				BooleanClauseOccur.MUST);
		}
	}

	private String _getKeywords(
		String keywords, SearchContext searchContext, String field) {

		if (Validator.isNotNull(keywords)) {
			return keywords;
		}

		String value = StringPool.BLANK;

		Serializable serializable = searchContext.getAttribute(field);

		if (serializable != null) {
			Class<?> clazz = serializable.getClass();

			if (clazz.isArray()) {
				value = StringUtil.merge((Object[])serializable);
			}
			else {
				value = GetterUtil.getString(serializable);
			}
		}

		if (!Validator.isBlank(value) &&
			(searchContext.getFacet(field) != null)) {

			return null;
		}

		if (Validator.isBlank(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isBlank(value)) {
			return null;
		}

		return value;
	}

	private String _getLocaleString(SearchContext searchContext) {
		Locale locale = searchContext.getLocale();

		return locale.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryKeywordQueryContributor.class);

	private static final Pattern _pattern = Pattern.compile("\\d{14}");

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}