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

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectView;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.QueryConfig;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	public ObjectEntryKeywordQueryContributor(
		ObjectFieldLocalService objectFieldLocalService,
		ObjectViewLocalService objectViewLocalService) {

		_objectFieldLocalService = objectFieldLocalService;
		_objectViewLocalService = objectViewLocalService;
	}

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		if (Validator.isBlank(keywords)) {
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
			String className = keywordQueryContributorHelper.getClassName();

			if (className.startsWith(
					"com.liferay.object.model.ObjectDefinition#")) {

				String[] parts = StringUtil.split(className, "#");

				objectDefinitionId = Long.valueOf(parts[1]);
			}
			else {
				return;
			}
		}

		AtomicBoolean addObjectEntryTitle = new AtomicBoolean(true);
		List<ObjectField> objectFields = null;

		if (GetterUtil.getBoolean(
				searchContext.getAttribute("searchByObjectView"))) {

			ObjectView defaultObjectView =
				_objectViewLocalService.fetchDefaultObjectView(
					objectDefinitionId);

			if (defaultObjectView != null) {
				addObjectEntryTitle.set(false);

				List<ObjectViewColumn> objectViewColumns =
					defaultObjectView.getObjectViewColumns();

				Stream<ObjectViewColumn> stream = objectViewColumns.stream();

				objectFields = stream.peek(
					objectViewColumn -> {
						if (Objects.equals(
								objectViewColumn.getObjectFieldName(), "id")) {

							addObjectEntryTitle.set(true);
						}
					}
				).map(
					objectViewColumn ->
						_objectFieldLocalService.fetchObjectField(
							defaultObjectView.getObjectDefinitionId(),
							objectViewColumn.getObjectFieldName())
				).collect(
					Collectors.toList()
				);
			}
		}

		if (objectFields == null) {
			objectFields = _objectFieldLocalService.getObjectFields(
				objectDefinitionId);
		}

		for (String token : _tokenizeKeywords(keywords)) {
			if (addObjectEntryTitle.get() && !Validator.isBlank(token)) {
				try {
					booleanQuery.add(
						new TermQueryImpl(Field.ENTRY_CLASS_PK, token),
						BooleanClauseOccur.SHOULD);

					String titleField = "objectEntryTitle";

					booleanQuery.add(
						new WildcardQueryImpl(
							titleField, token + StringPool.STAR),
						BooleanClauseOccur.SHOULD);

					QueryConfig queryConfig = searchContext.getQueryConfig();

					queryConfig.addHighlightFieldNames(
						Field.ENTRY_CLASS_PK, titleField);
				}
				catch (ParseException parseException) {
					throw new SystemException(parseException);
				}
			}

			for (ObjectField objectField : objectFields) {
				try {
					_contribute(
						objectField, token, booleanQuery, searchContext);
				}
				catch (ParseException parseException) {
					throw new SystemException(parseException);
				}
			}
		}
	}

	private void _addNumericClause(
			String fieldName, BooleanQuery nestedBooleanQuery,
			ObjectField objectField, String token)
		throws ParseException {

		boolean addedRangeQuery = _addRangeQuery(
			nestedBooleanQuery, fieldName, token, objectField.getDBType());

		if (!addedRangeQuery && _isValidInput(token, objectField.getDBType())) {
			nestedBooleanQuery.add(
				new TermQueryImpl(fieldName, token), BooleanClauseOccur.MUST);
		}
	}

	private boolean _addRangeQuery(
			BooleanQuery booleanQuery, String fieldName, String token,
			String type)
		throws ParseException {

		if (Validator.isBlank(token)) {
			return false;
		}

		String[] range = RangeParserUtil.parserRange(token);

		String lowerTerm = range[0];
		String upperTerm = range[1];

		if (!_isValidRange(lowerTerm, type, upperTerm)) {
			return false;
		}

		booleanQuery.add(
			new TermRangeQueryImpl(fieldName, lowerTerm, upperTerm, true, true),
			BooleanClauseOccur.MUST);

		return true;
	}

	private void _contribute(
			ObjectField objectField, String token, BooleanQuery booleanQuery,
			SearchContext searchContext)
		throws ParseException {

		if ((objectField == null) || !objectField.isIndexed()) {
			return;
		}

		token = _getToken(objectField.getName(), searchContext, token);

		if (Validator.isNull(token)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Add search term ", token, " for object field ",
					objectField.getName()));
		}

		BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();
		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (objectField.isIndexedAsKeyword()) {
			String fieldName = "nestedFieldArray.value_keyword";
			String lowerCaseToken = StringUtil.toLowerCase(token);

			nestedBooleanQuery.add(
				new WildcardQueryImpl(
					fieldName, lowerCaseToken + StringPool.STAR),
				BooleanClauseOccur.MUST);
			nestedBooleanQuery.add(
				new TermQueryImpl(fieldName, lowerCaseToken),
				BooleanClauseOccur.SHOULD);

			queryConfig.addHighlightFieldNames(fieldName);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				 Objects.equals(
					 objectField.getDBType(),
					 ObjectFieldConstants.DB_TYPE_CLOB) ||
				 Objects.equals(
					 objectField.getDBType(),
					 ObjectFieldConstants.DB_TYPE_STRING)) {

			String fieldName = "nestedFieldArray.value_text";

			if (Objects.equals(
					objectField.getIndexedLanguageId(),
					searchContext.getLanguageId())) {

				fieldName =
					"nestedFieldArray.value_" +
						objectField.getIndexedLanguageId();
			}

			nestedBooleanQuery.add(
				new MatchQuery(fieldName, token), BooleanClauseOccur.MUST);

			queryConfig.addHighlightFieldNames(fieldName);
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL)) {

			_addNumericClause(
				"nestedFieldArray.value_double", nestedBooleanQuery,
				objectField, token);
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BLOB)) {

			_log.error("Blob type is not indexable");
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BOOLEAN)) {

			String fieldName = null;

			if (StringUtil.equalsIgnoreCase(token, "false") ||
				StringUtil.equalsIgnoreCase(token, "true")) {

				fieldName = "nestedFieldArray.value_boolean";
			}
			else if (StringUtil.equalsIgnoreCase(token, "no") ||
					 StringUtil.equalsIgnoreCase(token, "yes")) {

				fieldName = "nestedFieldArray.value_keyword";
			}

			if (fieldName != null) {
				nestedBooleanQuery.add(
					new TermQueryImpl(fieldName, StringUtil.toLowerCase(token)),
					BooleanClauseOccur.MUST);

				queryConfig.addHighlightFieldNames(fieldName);
			}
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_DATE)) {

			_addNumericClause(
				"nestedFieldArray.value_date", nestedBooleanQuery, objectField,
				token);
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_DOUBLE)) {

			_addNumericClause(
				"nestedFieldArray.value_double", nestedBooleanQuery,
				objectField, token);
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_INTEGER)) {

			_addNumericClause(
				"nestedFieldArray.value_integer", nestedBooleanQuery,
				objectField, token);
		}
		else if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_LONG)) {

			_addNumericClause(
				"nestedFieldArray.value_long", nestedBooleanQuery, objectField,
				token);
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

	private String _getToken(
		String fieldName, SearchContext searchContext, String token) {

		if (Validator.isNotNull(token)) {
			return token;
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

	private boolean _isValidInput(String token, String type) {
		if (token == null) {
			return false;
		}

		try {
			if (Objects.equals(type, "BigDecimal") ||
				Objects.equals(type, "Double")) {

				Double.valueOf(token);
			}
			else if (Objects.equals(type, "Date")) {
				Matcher matcher = _pattern.matcher(token);

				if (!matcher.matches()) {
					return false;
				}
			}
			else if (Objects.equals(type, "Integer")) {
				Integer.valueOf(token);
			}
			else if (Objects.equals(type, "Long")) {
				Long.valueOf(token);
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

	private boolean _isValidRange(
		String lowerTerm, String type, String upperTerm) {

		if (_isValidInput(lowerTerm, type) && _isValidInput(upperTerm, type)) {
			return true;
		}

		return false;
	}

	private List<String> _tokenizeKeywords(String keywords) {
		KeywordTokenizer keywordTokenizer = new KeywordTokenizer();

		return keywordTokenizer.tokenize(keywords);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryKeywordQueryContributor.class);

	private static final Pattern _pattern = Pattern.compile("\\d{14}");

	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectViewLocalService _objectViewLocalService;

	private class KeywordTokenizer {

		public List<String> tokenize(String keywords) {
			keywords = _normalizeWhitespace(keywords);

			List<String> tokens = new ArrayList<>();

			int[] startAndEnd = getStartAndEnd(keywords);

			tokenize(keywords, tokens, startAndEnd[0], startAndEnd[1]);

			return tokens;
		}

		protected int[] getStartAndEnd(String keywords) {
			int quoteStart = keywords.indexOf(CharPool.QUOTE);
			int rangeStart = keywords.indexOf(CharPool.OPEN_BRACKET);

			if (quoteStart == QueryUtil.ALL_POS) {
				return new int[] {
					rangeStart,
					keywords.indexOf(CharPool.CLOSE_BRACKET, rangeStart + 1)
				};
			}
			else if (rangeStart == QueryUtil.ALL_POS) {
				return new int[] {
					quoteStart, keywords.indexOf(CharPool.QUOTE, quoteStart + 1)
				};
			}
			else if (quoteStart < rangeStart) {
				return new int[] {
					quoteStart, keywords.indexOf(CharPool.QUOTE, quoteStart + 1)
				};
			}
			else {
				return new int[] {
					rangeStart,
					keywords.indexOf(CharPool.CLOSE_BRACKET, rangeStart + 1)
				};
			}
		}

		protected String[] split(String keywords) {
			if (Objects.equals(keywords, StringPool.NULL)) {
				return new String[] {keywords};
			}

			return StringUtil.split(keywords, CharPool.SPACE);
		}

		protected void tokenize(
			String keywords, List<String> tokens, int start, int end) {

			if ((start == QueryUtil.ALL_POS) || (end == QueryUtil.ALL_POS)) {
				keywords = keywords.trim();

				if (!keywords.isEmpty()) {
					tokenizeBySpace(keywords, tokens);
				}

				return;
			}

			String token = keywords.substring(0, start);

			token = token.trim();

			if (!token.isEmpty()) {
				tokenizeBySpace(token, tokens);
			}

			token = keywords.substring(start, end + 1);

			token = token.trim();

			if (!token.isEmpty()) {
				if (StringUtil.startsWith(token, CharPool.QUOTE)) {
					token = StringUtil.unquote(token);
				}

				tokens.add(token);
			}

			if ((end + 1) > keywords.length()) {
				return;
			}

			keywords = keywords.substring(end + 1);

			keywords = keywords.trim();

			if (keywords.isEmpty()) {
				return;
			}

			int[] startAndEnd = getStartAndEnd(keywords);

			tokenize(keywords, tokens, startAndEnd[0], startAndEnd[1]);
		}

		protected void tokenizeBySpace(String keywords, List<String> tokens) {
			String[] keywordTokens = split(keywords);

			for (String keywordToken : keywordTokens) {
				String token = keywordToken.trim();

				if (!token.isEmpty()) {
					tokens.add(token);
				}
			}
		}

		private String _normalizeWhitespace(String keywords) {
			return StringUtil.replace(
				keywords, _IDEOGRAPHIC_SPACE, CharPool.SPACE);
		}

		private static final char _IDEOGRAPHIC_SPACE = '\u3000';

	}

}