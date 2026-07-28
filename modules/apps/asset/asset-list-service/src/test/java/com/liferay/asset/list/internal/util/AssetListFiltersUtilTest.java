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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.MatchQuery;
import com.liferay.portal.kernel.search.NestedQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Felipe Lorenz
 */
public class AssetListFiltersUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ReflectionTestUtil.setFieldValue(
			FastDateFormatFactoryUtil.class, "_fastDateFormatFactory",
			new FastDateFormatFactoryImpl());
	}

	@AfterClass
	public static void tearDownClass() {
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_objectFieldLocalServiceUtilMockedStatic.close();
		_portalUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_objectDefinitionLocalServiceUtilMockedStatic.reset();
		_objectFieldLocalServiceUtilMockedStatic.reset();
		_portalUtilMockedStatic.reset();

		_setUpLocalizationUtil();
	}

	@Test
	public void testFilterQueriesWithEqualityOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
			ObjectFieldConstants.DB_TYPE_BOOLEAN, "visible");

		_assertTermQuery(
			"nestedFieldArray.value_boolean", "true",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "visible", "true"), "visible"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE, "priority");

		String priority = String.valueOf(RandomTestUtil.randomDouble());

		_assertTermQuery(
			"nestedFieldArray.value_double", priority,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "priority", priority),
				"priority"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER, "viewCount");

		String viewCount = String.valueOf(RandomTestUtil.randomInt());

		_assertTermQuery(
			"nestedFieldArray.value_integer", viewCount,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "viewCount", viewCount),
				"viewCount"));
		_assertTermQuery(
			"nestedFieldArray.value_integer", viewCount,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilterJSONObject("not-eq", "viewCount", viewCount),
				"viewCount"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG, "externalId");

		String externalId = String.valueOf(RandomTestUtil.randomLong());

		_assertTermQuery(
			"nestedFieldArray.value_long", externalId,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "externalId", externalId),
				"externalId"));

		ObjectField objectField = _setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "subtitle");

		Mockito.when(
			objectField.isLocalized()
		).thenReturn(
			true
		);

		String subtitle = RandomTestUtil.randomString();

		_assertTermQuery(
			"nestedFieldArray.value_en_US", subtitle,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "subtitle", subtitle),
				"subtitle"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "title");

		String title = RandomTestUtil.randomString();

		_assertTermQuery(
			"nestedFieldArray.value_text", title,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "title", title), "title"));
		_assertTermQuery(
			"nestedFieldArray.value_text", title,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilterJSONObject("not-eq", "title", title), "title"));
	}

	@Test
	public void testFilterQueriesWithInvalidInput() {
		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, null, LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);

		booleanClauses = AssetListFiltersUtil.getFiltersBooleanClauses(
			_COMPANY_ID, JSONFactoryUtil.createJSONArray(), LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testFilterQueriesWithKeywordTextContainsOperators() {
		_setUpKeywordTextObjectField("learnDocumentation");

		_assertTermQuery(
			"nestedFieldArray.value_keyword", "alpha",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject("eq", "learnDocumentation", "Alpha"),
				"learnDocumentation"));
		_assertWildcardQuery(
			"nestedFieldArray.value_keyword", "*alpha*",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterJSONObject(
					"contains", "learnDocumentation", "Alpha"),
				"learnDocumentation"));
		_assertWildcardQuery(
			"nestedFieldArray.value_keyword", "*alpha*",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilterJSONObject(
					"not-contains", "learnDocumentation", "Alpha"),
				"learnDocumentation"));
	}

	@Test
	public void testFilterQueriesWithTextContainsOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "title");

		String keyword = RandomTestUtil.randomString();

		Query containsQuery = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST,
			_buildFilterJSONObject("contains", "title", keyword), "title");

		Assert.assertTrue(
			containsQuery.toString(), containsQuery instanceof MatchQuery);

		Query containsWithQuantifierQuery = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST,
			_buildFilterJSONObject(
				"contains", "title", keyword
			).put(
				"quantifier", "any"
			),
			"title");

		Assert.assertTrue(
			containsWithQuantifierQuery.toString(),
			containsWithQuantifierQuery instanceof MatchQuery);

		Query notContainsQuery = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST_NOT,
			_buildFilterJSONObject("not-contains", "title", keyword), "title");

		Assert.assertTrue(
			notContainsQuery.toString(),
			notContainsQuery instanceof MatchQuery);
	}

	private Query _assertNestedRow(
		BooleanClause[] booleanClauses, BooleanClauseOccur expectedValueOccur,
		String propertyName, int rowIndex) {

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 1, booleanClauses.length);

		BooleanClause<?> outerBooleanClause = booleanClauses[0];

		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			outerBooleanClause.getBooleanClauseOccur());

		BooleanQuery outerBooleanQuery =
			(BooleanQuery)outerBooleanClause.getClause();

		List<BooleanClause<Query>> rowBooleanClauses =
			outerBooleanQuery.clauses();

		BooleanClause<Query> rowBooleanClause = rowBooleanClauses.get(rowIndex);

		NestedQuery nestedQuery = (NestedQuery)rowBooleanClause.getClause();

		Assert.assertEquals("nestedFieldArray", nestedQuery.getPath());

		BooleanQuery innerBooleanQuery = (BooleanQuery)nestedQuery.getQuery();

		List<BooleanClause<Query>> innerBooleanClauses =
			innerBooleanQuery.clauses();

		Assert.assertEquals(
			innerBooleanClauses.toString(), 3, innerBooleanClauses.size());

		BooleanClause<Query> fieldNameBooleanClause = innerBooleanClauses.get(
			0);

		TermQuery fieldNameTermQuery =
			(TermQuery)fieldNameBooleanClause.getClause();

		QueryTerm fieldNameQueryTerm = fieldNameTermQuery.getQueryTerm();

		Assert.assertEquals(
			"nestedFieldArray.fieldName", fieldNameQueryTerm.getField());
		Assert.assertEquals(propertyName, fieldNameQueryTerm.getValue());

		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			fieldNameBooleanClause.getBooleanClauseOccur());

		BooleanClause<Query> valueFieldNameBooleanClause =
			innerBooleanClauses.get(1);

		TermQuery valueFieldNameTermQuery =
			(TermQuery)valueFieldNameBooleanClause.getClause();

		QueryTerm valueFieldNameQueryTerm =
			valueFieldNameTermQuery.getQueryTerm();

		Assert.assertEquals(
			"nestedFieldArray.valueFieldName",
			valueFieldNameQueryTerm.getField());

		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			valueFieldNameBooleanClause.getBooleanClauseOccur());

		BooleanClause<Query> valueBooleanClause = innerBooleanClauses.get(2);

		Assert.assertEquals(
			expectedValueOccur, valueBooleanClause.getBooleanClauseOccur());

		return valueBooleanClause.getClause();
	}

	private void _assertTermQuery(
		String expectedField, String expectedValue, Query query) {

		Assert.assertTrue(query.toString(), query instanceof TermQuery);

		TermQuery termQuery = (TermQuery)query;

		QueryTerm queryTerm = termQuery.getQueryTerm();

		Assert.assertEquals(expectedField, queryTerm.getField());
		Assert.assertEquals(expectedValue, queryTerm.getValue());
	}

	private void _assertWildcardQuery(
		String expectedField, String expectedValue, Query query) {

		Assert.assertTrue(query.toString(), query instanceof WildcardQuery);

		WildcardQuery wildcardQuery = (WildcardQuery)query;

		QueryTerm queryTerm = wildcardQuery.getQueryTerm();

		Assert.assertEquals(expectedField, queryTerm.getField());
		Assert.assertEquals(expectedValue, queryTerm.getValue());
	}

	private JSONObject _buildFilterJSONObject(
		String operatorName, String propertyName, String value) {

		return JSONUtil.put(
			"classNameId", _CLASS_NAME_ID
		).put(
			"classTypeId", _CLASS_TYPE_ID
		).put(
			"operatorName", operatorName
		).put(
			"propertyName", propertyName
		).put(
			"value", value
		);
	}

	private Query _runAndAssertNestedRow(
		BooleanClauseOccur expectedValueOccur, JSONObject filterJSONObject,
		String propertyName) {

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, JSONUtil.putAll(filterJSONObject), LocaleUtil.US);

		return _assertNestedRow(
			booleanClauses, expectedValueOccur, propertyName, 0);
	}

	private ObjectField _setUpKeywordTextObjectField(String name) {
		ObjectField objectField = _setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, name);

		Mockito.when(
			objectField.isIndexedAsKeyword()
		).thenReturn(
			true
		);

		return objectField;
	}

	private void _setUpLocalizationUtil() {
		LocalizationUtil localizationUtil = new LocalizationUtil();

		Localization localization = Mockito.mock(Localization.class);

		Mockito.when(
			localization.getLocalizedName(
				Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			invocation ->
				invocation.getArgument(0) + "_" + invocation.getArgument(1)
		);

		localizationUtil.setLocalization(localization);
	}

	private ObjectField _setUpObjectField(
		String businessType, String dbType, String name) {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getObjectDefinitionId()
		).thenReturn(
			_CLASS_TYPE_ID
		);

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getBusinessType()
		).thenReturn(
			businessType
		);

		Mockito.when(
			objectField.getDBType()
		).thenReturn(
			dbType
		);

		Mockito.when(
			objectField.getName()
		).thenReturn(
			name
		);

		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					fetchObjectDefinitionByClassName(
						_COMPANY_ID, "com.liferay.test.Class" + _CLASS_NAME_ID)
		).thenReturn(
			objectDefinition
		);

		_objectFieldLocalServiceUtilMockedStatic.when(
			() -> ObjectFieldLocalServiceUtil.fetchObjectField(
				_CLASS_TYPE_ID, name)
		).thenReturn(
			objectField
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getClassName(_CLASS_NAME_ID)
		).thenReturn(
			"com.liferay.test.Class" + _CLASS_NAME_ID
		);

		return objectField;
	}

	private static final long _CLASS_NAME_ID = RandomTestUtil.randomLong();

	private static final long _CLASS_TYPE_ID = RandomTestUtil.randomLong();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectDefinitionLocalServiceUtil.class);
	private static final MockedStatic<ObjectFieldLocalServiceUtil>
		_objectFieldLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectFieldLocalServiceUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

}