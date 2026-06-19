/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.MatchAllQuery;
import com.liferay.portal.kernel.search.MatchQuery;
import com.liferay.portal.kernel.search.NestedQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Format;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Joshua Cords
 */
public class AssetListFiltersUtil {

	public static BooleanClause[] getFiltersBooleanClauses(
		long companyId, JSONArray filtersJSONArray, Locale locale) {

		if (JSONUtil.isEmpty(filtersJSONArray)) {
			return new BooleanClause[0];
		}

		BooleanQuery booleanQuery = new BooleanQuery();

		for (int i = 0; i < filtersJSONArray.length(); i++) {
			BooleanClause<Query> booleanClause = _toClause(
				companyId, filtersJSONArray.getJSONObject(i), locale);

			if (booleanClause == null) {
				continue;
			}

			booleanQuery.add(
				booleanClause.getClause(),
				booleanClause.getBooleanClauseOccur());
		}

		if (!booleanQuery.hasClauses()) {
			return new BooleanClause[0];
		}

		return new BooleanClause[] {
			new BooleanClause<>(booleanQuery, BooleanClauseOccur.MUST)
		};
	}

	private static String _aliasMetadataName(String name) {
		return _metadataCommonFieldNames.getOrDefault(name, name);
	}

	private static String _emptyToNull(String value) {
		if (Validator.isNull(value)) {
			return null;
		}

		return value;
	}

	private static ObjectDefinition _fetchObjectDefinition(
		long classNameId, long companyId) {

		if (classNameId <= 0) {
			return null;
		}

		return ObjectDefinitionLocalServiceUtil.
			fetchObjectDefinitionByClassName(
				companyId, PortalUtil.getClassName(classNameId));
	}

	private static ObjectField _fetchObjectField(
		long classNameId, long companyId, String name) {

		ObjectDefinition objectDefinition = _fetchObjectDefinition(
			classNameId, companyId);

		if (objectDefinition == null) {
			return null;
		}

		return ObjectFieldLocalServiceUtil.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), name);
	}

	private static String _getCommonFieldName(
		Locale locale, String propertyName) {

		if (!_commonFieldTypes.containsKey(propertyName)) {
			return null;
		}

		if (_localizedCommonFieldNames.contains(propertyName)) {
			return Field.getLocalizedName(locale, "localized_" + propertyName);
		}

		return propertyName;
	}

	private static String _getCommonFieldType(String propertyName) {
		return _commonFieldTypes.get(propertyName);
	}

	private static String _getSubfield(Locale locale, ObjectField objectField) {
		if (objectField.isIndexedAsKeyword()) {
			return "nestedFieldArray.value_keyword";
		}

		String dbType = objectField.getDBType();

		if (ObjectFieldConstants.DB_TYPE_BIG_DECIMAL.equals(dbType) ||
			ObjectFieldConstants.DB_TYPE_DOUBLE.equals(dbType)) {

			return "nestedFieldArray.value_double";
		}

		if (ObjectFieldConstants.DB_TYPE_BOOLEAN.equals(dbType)) {
			return "nestedFieldArray.value_boolean";
		}

		if (ObjectFieldConstants.DB_TYPE_DATE.equals(dbType) ||
			ObjectFieldConstants.DB_TYPE_DATE_TIME.equals(dbType)) {

			return "nestedFieldArray.value_date";
		}

		if (ObjectFieldConstants.DB_TYPE_INTEGER.equals(dbType)) {
			return "nestedFieldArray.value_integer";
		}

		if (ObjectFieldConstants.DB_TYPE_LONG.equals(dbType)) {
			return "nestedFieldArray.value_long";
		}

		if (objectField.isLocalized()) {
			return Field.getLocalizedName(locale, "nestedFieldArray.value");
		}

		String indexedLanguageId = objectField.getIndexedLanguageId();

		if (Validator.isNotNull(indexedLanguageId)) {
			return "nestedFieldArray.value_" + indexedLanguageId;
		}

		return "nestedFieldArray.value_text";
	}

	private static boolean _isCommonFieldRow(JSONObject jsonObject) {
		if ((jsonObject.getLong("classNameId") <= 0) &&
			(jsonObject.getLong("classTypeId") <= 0)) {

			return true;
		}

		return false;
	}

	private static boolean _isNegatedOperator(String operatorName) {
		if (operatorName.equals("not-contains") ||
			operatorName.equals("not-eq")) {

			return true;
		}

		return false;
	}

	private static String _normalizeDateValue(
		boolean dateTime, boolean endOfBound, String value) {

		if (Validator.isNull(value)) {
			return null;
		}

		String relativeDateValue = _resolveRelativeDateValue(value);

		if (relativeDateValue != null) {
			value = relativeDateValue;
		}

		String digits = value.replaceAll("[^0-9]", "");

		if (dateTime) {
			String padded = digits + "000000000000";

			String paddedDigits = padded.substring(0, 12);

			return paddedDigits + (endOfBound ? "59" : "00");
		}

		String padded = digits + "00000000";

		String paddedDigits = padded.substring(0, 8);

		return paddedDigits + (endOfBound ? "235959" : "000000");
	}

	private static String _resolveRelativeDateValue(String value) {
		if (!_relativeDateValues.contains(value)) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		if (value.equals("last-year") || value.equals("past-year")) {
			calendar.add(Calendar.YEAR, -1);
		}
		else if (value.equals("next-month")) {
			calendar.add(Calendar.MONTH, 1);
		}
		else if (value.equals("past-24-hours") || value.equals("past-day")) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		else if (value.equals("past-month")) {
			calendar.add(Calendar.MONTH, -1);
		}
		else if (value.equals("past-week")) {
			calendar.add(Calendar.DAY_OF_MONTH, -7);
		}

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		return format.format(calendar.getTime());
	}

	private static BooleanClause<Query> _toClause(
		long companyId, JSONObject jsonObject, Locale locale) {

		if (jsonObject == null) {
			return null;
		}

		if (_isCommonFieldRow(jsonObject)) {
			return _toCommonFieldClause(
				jsonObject, locale, jsonObject.getString("propertyName"));
		}

		ObjectField objectField = _fetchObjectField(
			jsonObject.getLong("classNameId"), companyId,
			jsonObject.getString("propertyName"));

		if (objectField == null) {
			return null;
		}

		if (objectField.isMetadata()) {
			return _toCommonFieldClause(
				jsonObject, locale, _aliasMetadataName(objectField.getName()));
		}

		NestedQuery nestedQuery = _toNestedQuery(
			jsonObject, locale, objectField);

		if (nestedQuery == null) {
			return null;
		}

		return new BooleanClause<>(nestedQuery, BooleanClauseOccur.MUST);
	}

	private static BooleanClause<Query> _toCommonFieldClause(
		JSONObject jsonObject, Locale locale, String propertyName) {

		if (Validator.isNull(propertyName)) {
			return null;
		}

		String field = _getCommonFieldName(locale, propertyName);
		String type = _getCommonFieldType(propertyName);

		if ((field == null) || (type == null)) {
			return null;
		}

		String operatorName = GetterUtil.getString(
			jsonObject.getString("operatorName"), "contains");

		Query valueQuery = _toCommonFieldValueQuery(
			field, jsonObject,
			_localizedCommonFieldNames.contains(propertyName), operatorName,
			type);

		if (valueQuery == null) {
			return null;
		}

		if (_isNegatedOperator(operatorName)) {
			BooleanQuery booleanQuery = new BooleanQuery();

			booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);
			booleanQuery.add(valueQuery, BooleanClauseOccur.MUST_NOT);

			return new BooleanClause<>(booleanQuery, BooleanClauseOccur.MUST);
		}

		return new BooleanClause<>(valueQuery, BooleanClauseOccur.MUST);
	}

	private static Query _toCommonFieldRangeQuery(
		String field, JSONObject jsonObject, String operatorName, String type) {

		boolean dateType = type.equals("date");

		if (operatorName.equals("between")) {
			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			if ((valueJSONArray == null) || (valueJSONArray.length() < 2)) {
				return null;
			}

			String lowerTerm = _emptyToNull(valueJSONArray.getString(0));
			String upperTerm = _emptyToNull(valueJSONArray.getString(1));

			if (dateType) {
				lowerTerm = _normalizeDateValue(false, false, lowerTerm);
				upperTerm = _normalizeDateValue(false, true, upperTerm);
			}

			return new TermRangeQuery(field, lowerTerm, upperTerm, true, true);
		}

		String value = jsonObject.getString("value");

		if (Validator.isNull(value)) {
			return null;
		}

		if (operatorName.equals("ge")) {
			String lowerTerm =
				dateType ? _normalizeDateValue(false, false, value) : value;

			return new TermRangeQuery(field, lowerTerm, null, true, false);
		}

		if (operatorName.equals("gt")) {
			String lowerTerm =
				dateType ? _normalizeDateValue(false, true, value) : value;

			return new TermRangeQuery(field, lowerTerm, null, false, false);
		}

		if (operatorName.equals("le")) {
			String upperTerm =
				dateType ? _normalizeDateValue(false, true, value) : value;

			return new TermRangeQuery(field, null, upperTerm, false, true);
		}

		if (operatorName.equals("lt")) {
			String upperTerm =
				dateType ? _normalizeDateValue(false, false, value) : value;

			return new TermRangeQuery(field, null, upperTerm, false, false);
		}

		return null;
	}

	private static Query _toCommonFieldValueQuery(
		String field, JSONObject jsonObject, boolean localized,
		String operatorName, String type) {

		if (operatorName.equals("between") || operatorName.equals("ge") ||
			operatorName.equals("gt") || operatorName.equals("le") ||
			operatorName.equals("lt")) {

			return _toCommonFieldRangeQuery(
				field, jsonObject, operatorName, type);
		}

		String value = jsonObject.getString("value");

		if (Validator.isNull(value)) {
			return null;
		}

		if (type.equals("date") &&
			(operatorName.equals("eq") || operatorName.equals("not-eq"))) {

			return new TermRangeQuery(
				field, _normalizeDateValue(false, false, value),
				_normalizeDateValue(false, true, value), true, true);
		}

		if (type.equals("decimal") || type.equals("integer")) {
			return new TermQuery(field, value);
		}

		if (localized) {
			return new MatchQuery(field, value);
		}

		if (operatorName.equals("contains") ||
			operatorName.equals("not-contains")) {

			return new WildcardQuery(
				field, StringPool.STAR + value + StringPool.STAR);
		}

		return new TermQuery(field, value);
	}

	private static NestedQuery _toNestedQuery(
		JSONObject jsonObject, Locale locale, ObjectField objectField) {

		String propertyName = jsonObject.getString("propertyName");
		String value = jsonObject.getString("value");

		if (Validator.isNull(propertyName) || Validator.isNull(value)) {
			return null;
		}

		String operatorName = GetterUtil.getString(
			jsonObject.getString("operatorName"), "contains");

		String subfield = _getSubfield(locale, objectField);

		Query valueQuery = _toValueQuery(
			jsonObject, objectField, operatorName, subfield, value);

		if (valueQuery == null) {
			return null;
		}

		BooleanQuery booleanQuery = new BooleanQuery();

		booleanQuery.add(
			new TermQuery("nestedFieldArray.fieldName", propertyName),
			BooleanClauseOccur.MUST);
		booleanQuery.add(
			new TermQuery(
				"nestedFieldArray.valueFieldName",
				subfield.substring(subfield.indexOf(CharPool.PERIOD) + 1)),
			BooleanClauseOccur.MUST);
		booleanQuery.add(
			valueQuery,
			_isNegatedOperator(operatorName) ? BooleanClauseOccur.MUST_NOT :
				BooleanClauseOccur.MUST);

		return new NestedQuery("nestedFieldArray", booleanQuery);
	}

	private static Query _toPicklistQuery(
		JSONObject filterJSONObject, String subfield) {

		JSONArray valueJSONArray = filterJSONObject.getJSONArray("value");

		if ((valueJSONArray == null) || (valueJSONArray.length() == 0)) {
			return null;
		}

		BooleanQuery booleanQuery = new BooleanQuery();

		String quantifier = filterJSONObject.getString("quantifier");

		BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.SHOULD;

		if (Objects.equals(quantifier, "all")) {
			booleanClauseOccur = BooleanClauseOccur.MUST;
		}

		for (int i = 0; i < valueJSONArray.length(); i++) {
			JSONObject itemJSONObject = valueJSONArray.getJSONObject(i);

			String value = StringUtil.toLowerCase(
				itemJSONObject.getString("value"));

			booleanQuery.add(
				new TermQuery(subfield, value), booleanClauseOccur);
		}

		return booleanQuery;
	}

	private static Query _toRangeQuery(
		JSONObject filterJSONObject, ObjectField objectField,
		String operatorName, String subfield) {

		boolean dateSubfield = subfield.endsWith(".value_date");

		boolean dateTime = false;

		if (dateSubfield &&
			ObjectFieldConstants.DB_TYPE_DATE_TIME.equals(
				objectField.getDBType())) {

			dateTime = true;
		}

		if (operatorName.equals("between")) {
			JSONArray valueJSONArray = filterJSONObject.getJSONArray("value");

			if ((valueJSONArray == null) || (valueJSONArray.length() < 2)) {
				return null;
			}

			String lowerTerm = _emptyToNull(valueJSONArray.getString(0));
			String upperTerm = _emptyToNull(valueJSONArray.getString(1));

			if (dateSubfield) {
				lowerTerm = _normalizeDateValue(dateTime, false, lowerTerm);
				upperTerm = _normalizeDateValue(dateTime, true, upperTerm);
			}

			return new TermRangeQuery(
				subfield, lowerTerm, upperTerm, true, true);
		}

		String value = filterJSONObject.getString("value");

		if (Validator.isNull(value)) {
			return null;
		}

		if (operatorName.equals("ge")) {
			String lowerTerm =
				dateSubfield ? _normalizeDateValue(dateTime, false, value) :
					value;

			return new TermRangeQuery(subfield, lowerTerm, null, true, false);
		}

		if (operatorName.equals("gt")) {
			String lowerTerm =
				dateSubfield ? _normalizeDateValue(dateTime, true, value) :
					value;

			return new TermRangeQuery(subfield, lowerTerm, null, false, false);
		}

		if (operatorName.equals("le")) {
			String upperTerm =
				dateSubfield ? _normalizeDateValue(dateTime, true, value) :
					value;

			return new TermRangeQuery(subfield, null, upperTerm, false, true);
		}

		if (operatorName.equals("lt")) {
			String upperTerm =
				dateSubfield ? _normalizeDateValue(dateTime, false, value) :
					value;

			return new TermRangeQuery(subfield, null, upperTerm, false, false);
		}

		return null;
	}

	private static Query _toValueQuery(
		JSONObject filterJSONObject, ObjectField objectField,
		String operatorName, String subfield, String value) {

		if (operatorName.equals("contains") ||
			operatorName.equals("not-contains")) {

			if (objectField.getListTypeDefinitionId() != 0) {
				return _toPicklistQuery(filterJSONObject, subfield);
			}

			if (subfield.endsWith(".value_keyword")) {
				return new WildcardQuery(
					subfield,
					StringPool.STAR + StringUtil.toLowerCase(value) +
						StringPool.STAR);
			}
		}

		if (operatorName.equals("between") || operatorName.equals("ge") ||
			operatorName.equals("gt") || operatorName.equals("le") ||
			operatorName.equals("lt")) {

			return _toRangeQuery(
				filterJSONObject, objectField, operatorName, subfield);
		}

		if (subfield.endsWith(".value_date") &&
			(operatorName.equals("eq") || operatorName.equals("not-eq"))) {

			boolean dateTime = ObjectFieldConstants.DB_TYPE_DATE_TIME.equals(
				objectField.getDBType());

			return new TermRangeQuery(
				subfield, _normalizeDateValue(dateTime, false, value),
				_normalizeDateValue(dateTime, true, value), true, true);
		}

		if (subfield.endsWith(".value_keyword")) {
			return new TermQuery(subfield, StringUtil.toLowerCase(value));
		}

		if (subfield.endsWith(".value_boolean") ||
			subfield.endsWith(".value_double") ||
			subfield.endsWith(".value_integer") ||
			subfield.endsWith(".value_long") || operatorName.equals("eq") ||
			operatorName.equals("not-eq")) {

			return new TermQuery(subfield, value);
		}

		return new MatchQuery(subfield, value);
	}

	private static final Map<String, String> _commonFieldTypes =
		HashMapBuilder.put(
			Field.CREATE_DATE, "date"
		).put(
			Field.DISPLAY_DATE, "date"
		).put(
			Field.EXPIRATION_DATE, "date"
		).put(
			Field.MODIFIED_DATE, "date"
		).put(
			Field.PRIORITY, "decimal"
		).put(
			Field.PUBLISH_DATE, "date"
		).put(
			Field.REVIEW_DATE, "date"
		).put(
			Field.STATUS, "integer"
		).put(
			Field.TITLE, "text"
		).put(
			Field.USER_NAME, "text"
		).put(
			"externalReferenceCode", "text"
		).put(
			"viewCount", "integer"
		).build();
	private static final Set<String> _localizedCommonFieldNames =
		SetUtil.fromArray(Field.TITLE);
	private static final Map<String, String> _metadataCommonFieldNames =
		HashMapBuilder.put(
			"creator", Field.USER_NAME
		).put(
			"modifiedDate", Field.MODIFIED_DATE
		).build();
	private static final List<String> _relativeDateValues = Arrays.asList(
		"last-year", "next-month", "now", "past-24-hours", "past-day",
		"past-month", "past-week", "past-year");

}