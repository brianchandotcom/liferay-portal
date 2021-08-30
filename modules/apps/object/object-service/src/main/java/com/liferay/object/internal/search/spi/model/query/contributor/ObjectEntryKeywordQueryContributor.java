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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	public ObjectEntryKeywordQueryContributor(
		ObjectFieldLocalService objectFieldLocalService) {

		_objectFieldLocalService = objectFieldLocalService;
	}

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
			try {
				_contribute(
					keywords, booleanQuery, keywordQueryContributorHelper,
					objectField);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	private void _addRangeQuery(
			String keywords, BooleanQuery booleanQuery, String fieldName,
			String type)
		throws ParseException {

		if (Validator.isBlank(keywords)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(keywords);

		String lowerTerm = range[0];
		String upperTerm = range[1];

		if (!_isValidRange(lowerTerm, type, upperTerm)) {
			return;
		}

		booleanQuery.add(
			new TermRangeQueryImpl(fieldName, lowerTerm, upperTerm, true, true),
			BooleanClauseOccur.MUST);
	}

	private void _contribute(
			String keywords, BooleanQuery booleanQuery,
			KeywordQueryContributorHelper keywordQueryContributorHelper,
			ObjectField objectField)
		throws ParseException {

		if (!objectField.isIndexed()) {
			return;
		}

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		String fieldKeywords = _getKeywords(
			objectField.getName(), keywords, searchContext);

		if (Validator.isNull(fieldKeywords)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Add search term ", fieldKeywords, " for object field ",
					objectField.getName()));
		}

		BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();

		if (objectField.isIndexedAsKeyword()) {
			String lowerCaseKeywords = StringUtil.toLowerCase(fieldKeywords);

			nestedBooleanQuery.add(
				new WildcardQueryImpl(
					"nestedFieldArray.value_keyword",
					lowerCaseKeywords + StringPool.STAR),
				BooleanClauseOccur.MUST);
			nestedBooleanQuery.add(
				new TermQueryImpl(
					"nestedFieldArray.value_keyword", lowerCaseKeywords),
				BooleanClauseOccur.SHOULD);
		}
		else if (Objects.equals(objectField.getType(), "BigDecimal")) {
			_addRangeQuery(
				fieldKeywords, nestedBooleanQuery,
				"nestedFieldArray.value_double", objectField.getType());
		}
		else if (Objects.equals(objectField.getType(), "Blob")) {
			_log.error("Blob type is not indexable");
		}
		else if (Objects.equals(objectField.getType(), "Boolean")) {
			if (StringUtil.equalsIgnoreCase(fieldKeywords, "false") ||
				StringUtil.equalsIgnoreCase(fieldKeywords, "true")) {

				nestedBooleanQuery.add(
					new TermQueryImpl(
						"nestedFieldArray.value_boolean",
						StringUtil.toLowerCase(fieldKeywords)),
					BooleanClauseOccur.MUST);
			}
			else if (StringUtil.equalsIgnoreCase(fieldKeywords, "no") ||
					 StringUtil.equalsIgnoreCase(fieldKeywords, "yes")) {

				nestedBooleanQuery.add(
					new TermQueryImpl(
						"nestedFieldArray.value_keyword",
						StringUtil.toLowerCase(fieldKeywords)),
					BooleanClauseOccur.MUST);
			}
		}
		else if (Objects.equals(objectField.getType(), "Date")) {
			_addRangeQuery(
				fieldKeywords, nestedBooleanQuery,
				"nestedFieldArray.value_date", objectField.getType());
		}
		else if (Objects.equals(objectField.getType(), "Double")) {
			_addRangeQuery(
				fieldKeywords, nestedBooleanQuery,
				"nestedFieldArray.value_double", objectField.getType());
		}
		else if (Objects.equals(objectField.getType(), "Integer")) {
			_addRangeQuery(
				fieldKeywords, nestedBooleanQuery,
				"nestedFieldArray.value_integer", objectField.getType());
		}
		else if (Objects.equals(objectField.getType(), "Long")) {
			_addRangeQuery(
				fieldKeywords, nestedBooleanQuery,
				"nestedFieldArray.value_long", objectField.getType());
		}
		else if (Objects.equals(objectField.getType(), "String")) {
			if (Validator.isBlank(objectField.getIndexedLanguageId())) {
				nestedBooleanQuery.add(
					new MatchQuery(
						"nestedFieldArray.value_text", fieldKeywords),
					BooleanClauseOccur.MUST);
			}
			else if (Objects.equals(
						objectField.getIndexedLanguageId(),
						LocaleUtil.toLanguageId(searchContext.getLocale()))) {

				nestedBooleanQuery.add(
					new MatchQuery(
						"nestedFieldArray.value_" +
							objectField.getIndexedLanguageId(),
						fieldKeywords),
					BooleanClauseOccur.MUST);
			}
		}

		if (nestedBooleanQuery.hasClauses()) {
			BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.SHOULD;

			if (searchContext.isAndSearch()) {
				booleanClauseOccur = BooleanClauseOccur.MUST;
			}

			booleanQuery.add(
				new NestedQuery("nestedFieldArray", nestedBooleanQuery),
				booleanClauseOccur);
			nestedBooleanQuery.add(
				new TermQueryImpl(
					"nestedFieldArray.fieldName", objectField.getName()),
				BooleanClauseOccur.MUST);
		}
	}

	private String _getKeywords(
		String fieldName, String keywords, SearchContext searchContext) {

		if (Validator.isNotNull(keywords)) {
			return keywords;
		}

		String value = StringPool.BLANK;

		Serializable serializable = searchContext.getAttribute(fieldName);

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
			(searchContext.getFacet(fieldName) != null)) {

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

	private boolean _isValidRange(
		String lowerTerm, String type, String upperTerm) {

		if ((lowerTerm == null) || (upperTerm == null)) {
			return false;
		}

		try {
			if (Objects.equals(type, "BigDecimal") ||
				Objects.equals(type, "Double")) {

				Double.valueOf(lowerTerm);
				Double.valueOf(upperTerm);
			}
			else if (Objects.equals(type, "Date")) {
				Matcher lowerTermMatcher = _pattern.matcher(lowerTerm);
				Matcher upperTermMatcher = _pattern.matcher(upperTerm);

				if (!lowerTermMatcher.matches() ||
					!upperTermMatcher.matches()) {

					return false;
				}
			}
			else if (Objects.equals(type, "Integer")) {
				Integer.valueOf(lowerTerm);
				Integer.valueOf(upperTerm);
			}
			else if (Objects.equals(type, "Long")) {
				Long.valueOf(lowerTerm);
				Long.valueOf(upperTerm);
			}
			else {
				return false;
			}
		}
		catch (Exception exception) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryKeywordQueryContributor.class);

	private static final Pattern _pattern = Pattern.compile("\\d{14}");

	private final ObjectFieldLocalService _objectFieldLocalService;

}