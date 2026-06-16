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
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.MatchAllQuery;
import com.liferay.portal.kernel.search.MatchQuery;
import com.liferay.portal.kernel.search.NestedQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
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

import java.text.Format;

import java.util.Arrays;
import java.util.Date;
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
	public void testHandlesCommonFieldOperators() {
		_assertMatchQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "title", "Apple")),
			"localized_title_en_US", "Apple");
		_assertMatchQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("contains", "title", "App")),
			"localized_title_en_US", "App");

		_assertTermQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "userName", "Alice")),
			"userName", "Alice");
		_assertWildcardQuery(
			_runAndAssertNegatedCommonFieldRow(
				_buildCommonFieldFilter("not-contains", "userName", "Alice")),
			"userName", "*Alice*");

		_assertTermQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "viewCount", "5")),
			"viewCount", "5");
		_assertTermQuery(
			_runAndAssertNegatedCommonFieldRow(
				_buildCommonFieldFilter("not-eq", "status", "0")),
			"status", "0");

		_assertTermRangeQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("gt", "priority", "0.5")),
			"priority", "0.5", null, false, false);

		_assertTermRangeQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "modified", "2026-01-15")),
			"modified", "20260115000000", "20260115235959", true, true);
		_assertTermRangeQuery(
			_runAndAssertNegatedCommonFieldRow(
				_buildCommonFieldFilter("not-eq", "modified", "2026-01-15")),
			"modified", "20260115000000", "20260115235959", true, true);
		_assertTermRangeQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("gt", "createDate", "2026-01-15")),
			"createDate", "20260115235959", null, false, false);
		_assertTermRangeQuery(
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilterWithJSONArrayValue(
					"between", "modified",
					JSONUtil.putAll("2026-01-15", "2026-01-20"))),
			"modified", "20260115000000", "20260120235959", true, true);

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID,
				JSONUtil.putAll(_buildCommonFieldFilter("eq", "bogus", "x")),
				LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testHandlesDateAndDateTimeOperators() {
		_setUpObjectField(
			"dueDate", ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DATE);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate", _buildFilter("eq", "dueDate", "2026-01-15"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", "20260115000000", "20260115235959",
			true, true);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate", _buildFilter("gt", "dueDate", "2026-01-15"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", "20260115235959", null, false,
			false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate", _buildFilter("ge", "dueDate", "2026-01-15"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", "20260115000000", null, true, false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate", _buildFilter("lt", "dueDate", "2026-01-15"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", null, "20260115000000", false,
			false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate", _buildFilter("le", "dueDate", "2026-01-15"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", null, "20260115235959", false, true);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate",
				_buildFilterWithJSONArrayValue(
					"between", "dueDate",
					JSONUtil.putAll("2026-01-15", "2026-01-20")),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", "20260115000000", "20260120235959",
			true, true);

		_setUpObjectField(
			"reminderAt", ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
			ObjectFieldConstants.DB_TYPE_DATE_TIME);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"reminderAt",
				_buildFilter("eq", "reminderAt", "2026-01-15 10:30"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", "20260115103000", "20260115103059",
			true, true);
	}

	@Test
	public void testHandlesEqualityOperators() {
		_setUpObjectField(
			"visible", ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
			ObjectFieldConstants.DB_TYPE_BOOLEAN);

		_assertTermQuery(
			_runAndAssertNestedRow(
				"visible", _buildFilter("eq", "visible", "true"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_boolean", "true");

		_setUpObjectField(
			"viewCount", ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER);

		_assertTermQuery(
			_runAndAssertNestedRow(
				"viewCount", _buildFilter("eq", "viewCount", "5"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_integer", "5");

		_assertTermQuery(
			_runAndAssertNestedRow(
				"viewCount", _buildFilter("not-eq", "viewCount", "5"),
				BooleanClauseOccur.MUST_NOT),
			"nestedFieldArray.value_integer", "5");

		_setUpObjectField(
			"externalId", ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG);

		_assertTermQuery(
			_runAndAssertNestedRow(
				"externalId", _buildFilter("eq", "externalId", "99999"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_long", "99999");

		_setUpObjectField(
			"priority", ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE);

		_assertTermQuery(
			_runAndAssertNestedRow(
				"priority", _buildFilter("eq", "priority", "3.14"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_double", "3.14");

		_setUpObjectField(
			"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		_assertTermQuery(
			_runAndAssertNestedRow(
				"title", _buildFilter("eq", "title", "keyword"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_text", "keyword");

		_assertTermQuery(
			_runAndAssertNestedRow(
				"title", _buildFilter("not-eq", "title", "keyword"),
				BooleanClauseOccur.MUST_NOT),
			"nestedFieldArray.value_text", "keyword");

		ObjectField localizedSubtitle = _setUpObjectField(
			"subtitle", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		Mockito.when(
			localizedSubtitle.isLocalized()
		).thenReturn(
			true
		);

		_assertTermQuery(
			_runAndAssertNestedRow(
				"subtitle", _buildFilter("eq", "subtitle", "keyword"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_en_US", "keyword");
	}

	@Test
	public void testHandlesKeywordTextContainsOperators() {
		_setUpKeywordTextObjectField("learnDocumentation");

		_assertWildcardQuery(
			_runAndAssertNestedRow(
				"learnDocumentation",
				_buildFilter("contains", "learnDocumentation", "Alpha"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_keyword", "*alpha*");

		_assertWildcardQuery(
			_runAndAssertNestedRow(
				"learnDocumentation",
				_buildFilter("not-contains", "learnDocumentation", "Alpha"),
				BooleanClauseOccur.MUST_NOT),
			"nestedFieldArray.value_keyword", "*alpha*");

		_assertTermQuery(
			_runAndAssertNestedRow(
				"learnDocumentation",
				_buildFilter("eq", "learnDocumentation", "Alpha"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_keyword", "alpha");
	}

	@Test
	public void testHandlesNumericRangeOperators() {
		_setUpObjectField(
			"viewCount", ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"viewCount", _buildFilter("gt", "viewCount", "5"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_integer", "5", null, false, false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"viewCount", _buildFilter("ge", "viewCount", "5"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_integer", "5", null, true, false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"viewCount", _buildFilter("lt", "viewCount", "5"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_integer", null, "5", false, false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"viewCount", _buildFilter("le", "viewCount", "5"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_integer", null, "5", false, true);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"viewCount",
				_buildFilterWithJSONArrayValue(
					"between", "viewCount", JSONUtil.putAll("5", "10")),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_integer", "5", "10", true, true);

		_setUpObjectField(
			"priority", ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"priority", _buildFilter("gt", "priority", "3.14"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_double", "3.14", null, false, false);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"priority",
				_buildFilterWithJSONArrayValue(
					"between", "priority", JSONUtil.putAll("1.0", "5.0")),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_double", "1.0", "5.0", true, true);

		_setUpObjectField(
			"externalId", ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"externalId",
				_buildFilterWithJSONArrayValue(
					"between", "externalId", JSONUtil.putAll("100", "200")),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_long", "100", "200", true, true);
	}

	@Test
	public void testHandlesPicklistMultiValueOperators() {
		_setUpPicklistObjectField("status");

		_assertPicklistBooleanQuery(
			_runAndAssertNestedRow(
				"status",
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(
						_picklistValueJSONObject("approved"),
						_picklistValueJSONObject("draft"))
				).put(
					"quantifier", "any"
				),
				BooleanClauseOccur.MUST),
			BooleanClauseOccur.SHOULD, "approved", "draft");

		_assertPicklistBooleanQuery(
			_runAndAssertNestedRow(
				"status",
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(
						_picklistValueJSONObject("approved"),
						_picklistValueJSONObject("draft"))
				).put(
					"quantifier", "all"
				),
				BooleanClauseOccur.MUST),
			BooleanClauseOccur.MUST, "approved", "draft");

		_assertPicklistBooleanQuery(
			_runAndAssertNestedRow(
				"status",
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(_picklistValueJSONObject("approved"))),
				BooleanClauseOccur.MUST),
			BooleanClauseOccur.SHOULD, "approved");

		_assertPicklistBooleanQuery(
			_runAndAssertNestedRow(
				"status",
				_buildFilterWithJSONArrayValue(
					"not-contains", "status",
					JSONUtil.putAll(
						_picklistValueJSONObject("approved"),
						_picklistValueJSONObject("draft"))
				).put(
					"quantifier", "any"
				),
				BooleanClauseOccur.MUST_NOT),
			BooleanClauseOccur.SHOULD, "approved", "draft");

		_assertPicklistBooleanQuery(
			_runAndAssertNestedRow(
				"status",
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(_picklistValueJSONObject("Approved"))
				).put(
					"quantifier", "any"
				),
				BooleanClauseOccur.MUST),
			BooleanClauseOccur.SHOULD, "approved");

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID,
				JSONUtil.putAll(
					_buildFilterWithJSONArrayValue(
						"contains", "status", JSONFactoryUtil.createJSONArray()
					).put(
						"quantifier", "any"
					)),
				LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testHandlesRelativeDateOperators() {
		_setUpObjectField(
			"dueDate", ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DATE);

		String lastYearLowerTerm = _runRelativeDateGeLowerTerm("last-year");
		String nextMonthLowerTerm = _runRelativeDateGeLowerTerm("next-month");
		String nowLowerTerm = _runRelativeDateGeLowerTerm("now");
		String past24HoursLowerTerm = _runRelativeDateGeLowerTerm(
			"past-24-hours");
		String pastDayLowerTerm = _runRelativeDateGeLowerTerm("past-day");
		String pastMonthLowerTerm = _runRelativeDateGeLowerTerm("past-month");
		String pastWeekLowerTerm = _runRelativeDateGeLowerTerm("past-week");
		String pastYearLowerTerm = _runRelativeDateGeLowerTerm("past-year");

		for (String lowerTerm :
				new String[] {
					lastYearLowerTerm, nextMonthLowerTerm, nowLowerTerm,
					past24HoursLowerTerm, pastDayLowerTerm, pastMonthLowerTerm,
					pastWeekLowerTerm, pastYearLowerTerm
				}) {

			Assert.assertEquals(lowerTerm, 14, lowerTerm.length());
			Assert.assertTrue(lowerTerm, lowerTerm.endsWith("000000"));
		}

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMdd");

		Assert.assertEquals(format.format(new Date()) + "000000", nowLowerTerm);

		Assert.assertEquals(lastYearLowerTerm, pastYearLowerTerm);
		Assert.assertEquals(past24HoursLowerTerm, pastDayLowerTerm);

		Assert.assertTrue(pastYearLowerTerm.compareTo(pastMonthLowerTerm) < 0);
		Assert.assertTrue(pastMonthLowerTerm.compareTo(pastWeekLowerTerm) < 0);
		Assert.assertTrue(pastWeekLowerTerm.compareTo(pastDayLowerTerm) < 0);
		Assert.assertTrue(pastDayLowerTerm.compareTo(nowLowerTerm) < 0);
		Assert.assertTrue(nowLowerTerm.compareTo(nextMonthLowerTerm) < 0);

		_assertTermRangeQuery(
			_runAndAssertNestedRow(
				"dueDate", _buildFilter("le", "dueDate", "now"),
				BooleanClauseOccur.MUST),
			"nestedFieldArray.value_date", null,
			format.format(new Date()) + "235959", false, true);
	}

	@Test
	public void testHandlesTextContainsOperators() {
		_setUpObjectField(
			"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		Query containsQuery = _runAndAssertNestedRow(
			"title", _buildFilter("contains", "title", "keyword"),
			BooleanClauseOccur.MUST);

		Assert.assertTrue(
			containsQuery.toString(), containsQuery instanceof MatchQuery);

		Query notContainsQuery = _runAndAssertNestedRow(
			"title", _buildFilter("not-contains", "title", "keyword"),
			BooleanClauseOccur.MUST_NOT);

		Assert.assertTrue(
			notContainsQuery.toString(),
			notContainsQuery instanceof MatchQuery);

		Query containsWithQuantifierQuery = _runAndAssertNestedRow(
			"title",
			_buildFilter(
				"contains", "title", "keyword"
			).put(
				"quantifier", "any"
			),
			BooleanClauseOccur.MUST);

		Assert.assertTrue(
			containsWithQuantifierQuery.toString(),
			containsWithQuantifierQuery instanceof MatchQuery);
	}

	@Test
	public void testReturnsEmptyClausesForInvalidInput() {
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
	public void testRoutesMetadataObjectFieldsToCommonFieldPath() {
		_setUpMetadataObjectField(
			"modifiedDate", ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DATE);

		_assertTermRangeQuery(
			_runAndAssertCommonFieldRow(
				_buildFilter("eq", "modifiedDate", "2026-01-15")),
			"modified", "20260115000000", "20260115235959", true, true);

		_setUpMetadataObjectField(
			"creator", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		_assertTermQuery(
			_runAndAssertCommonFieldRow(_buildFilter("eq", "creator", "Alice")),
			"userName", "Alice");

		_setUpMetadataObjectField(
			"id", ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG);

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, JSONUtil.putAll(_buildFilter("eq", "id", "5")),
				LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	private static MockedStatic<ObjectDefinitionLocalServiceUtil>
		_createObjectDefinitionLocalServiceUtilMockedStatic() {

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		return Mockito.mockStatic(ObjectDefinitionLocalServiceUtil.class);
	}

	private Query _assertCommonFieldRow(BooleanClause[] booleanClauses) {
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

		BooleanClause<Query> rowBooleanClause = rowBooleanClauses.get(0);

		Assert.assertEquals(
			BooleanClauseOccur.MUST, rowBooleanClause.getBooleanClauseOccur());

		Query query = rowBooleanClause.getClause();

		Assert.assertFalse(query.toString(), query instanceof NestedQuery);

		return query;
	}

	private void _assertMatchQuery(
		Query query, String expectedField, String expectedValue) {

		Assert.assertTrue(query.toString(), query instanceof MatchQuery);

		MatchQuery matchQuery = (MatchQuery)query;

		Assert.assertEquals(expectedField, matchQuery.getField());
		Assert.assertEquals(expectedValue, matchQuery.getValue());
	}

	private Query _assertNestedRow(
		BooleanClause[] booleanClauses, int rowIndex, String propertyName,
		BooleanClauseOccur expectedValueOccur) {

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

		Assert.assertTrue(rowIndex < rowBooleanClauses.size());

		NestedQuery nestedQuery = (NestedQuery)rowBooleanClauses.get(
			rowIndex
		).getClause();

		Assert.assertEquals("nestedFieldArray", nestedQuery.getPath());

		BooleanQuery innerBooleanQuery = (BooleanQuery)nestedQuery.getQuery();

		List<BooleanClause<Query>> innerBooleanClauses =
			innerBooleanQuery.clauses();

		Assert.assertEquals(
			innerBooleanClauses.toString(), 3, innerBooleanClauses.size());

		TermQuery fieldNameTermQuery = (TermQuery)innerBooleanClauses.get(
			0
		).getClause();

		Assert.assertEquals(
			"nestedFieldArray.fieldName",
			fieldNameTermQuery.getQueryTerm(
			).getField());
		Assert.assertEquals(
			propertyName,
			fieldNameTermQuery.getQueryTerm(
			).getValue());

		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			innerBooleanClauses.get(
				0
			).getBooleanClauseOccur());

		TermQuery valueFieldNameTermQuery = (TermQuery)innerBooleanClauses.get(
			1
		).getClause();

		Assert.assertEquals(
			"nestedFieldArray.valueFieldName",
			valueFieldNameTermQuery.getQueryTerm(
			).getField());

		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			innerBooleanClauses.get(
				1
			).getBooleanClauseOccur());

		Assert.assertEquals(
			expectedValueOccur,
			innerBooleanClauses.get(
				2
			).getBooleanClauseOccur());

		return innerBooleanClauses.get(
			2
		).getClause();
	}

	private void _assertPicklistBooleanQuery(
		Query query, BooleanClauseOccur expectedInnerOccur,
		String... expectedValues) {

		Assert.assertTrue(query.toString(), query instanceof BooleanQuery);

		BooleanQuery booleanQuery = (BooleanQuery)query;

		List<BooleanClause<Query>> innerBooleanClauses = booleanQuery.clauses();

		Assert.assertEquals(
			innerBooleanClauses.toString(), expectedValues.length,
			innerBooleanClauses.size());

		for (int i = 0; i < expectedValues.length; i++) {
			Assert.assertEquals(
				expectedInnerOccur,
				innerBooleanClauses.get(
					i
				).getBooleanClauseOccur());

			_assertTermQuery(
				innerBooleanClauses.get(
					i
				).getClause(),
				"nestedFieldArray.value_keyword", expectedValues[i]);
		}
	}

	private void _assertTermQuery(
		Query query, String expectedField, String expectedValue) {

		Assert.assertTrue(query.toString(), query instanceof TermQuery);

		TermQuery termQuery = (TermQuery)query;

		Assert.assertEquals(
			expectedField,
			termQuery.getQueryTerm(
			).getField());
		Assert.assertEquals(
			expectedValue,
			termQuery.getQueryTerm(
			).getValue());
	}

	private void _assertTermRangeQuery(
		Query query, String expectedField, String expectedLower,
		String expectedUpper, boolean expectedIncludesLower,
		boolean expectedIncludesUpper) {

		Assert.assertTrue(query.toString(), query instanceof TermRangeQuery);

		TermRangeQuery termRangeQuery = (TermRangeQuery)query;

		Assert.assertEquals(expectedField, termRangeQuery.getField());
		Assert.assertEquals(expectedLower, termRangeQuery.getLowerTerm());
		Assert.assertEquals(expectedUpper, termRangeQuery.getUpperTerm());
		Assert.assertEquals(
			expectedIncludesLower, termRangeQuery.includesLower());
		Assert.assertEquals(
			expectedIncludesUpper, termRangeQuery.includesUpper());
	}

	private void _assertWildcardQuery(
		Query query, String expectedField, String expectedValue) {

		Assert.assertTrue(query.toString(), query instanceof WildcardQuery);

		WildcardQuery wildcardQuery = (WildcardQuery)query;

		Assert.assertEquals(
			expectedField,
			wildcardQuery.getQueryTerm(
			).getField());
		Assert.assertEquals(
			expectedValue,
			wildcardQuery.getQueryTerm(
			).getValue());
	}

	private JSONObject _buildCommonFieldFilter(
		String operatorName, String propertyName, String value) {

		return JSONUtil.put(
			"operatorName", operatorName
		).put(
			"propertyName", propertyName
		).put(
			"value", value
		);
	}

	private JSONObject _buildCommonFieldFilterWithJSONArrayValue(
		String operatorName, String propertyName, JSONArray valueJSONArray) {

		return JSONUtil.put(
			"operatorName", operatorName
		).put(
			"propertyName", propertyName
		).put(
			"value", valueJSONArray
		);
	}

	private JSONObject _buildFilter(
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

	private JSONObject _buildFilterWithJSONArrayValue(
		String operatorName, String propertyName, JSONArray valueJSONArray) {

		return JSONUtil.put(
			"classNameId", _CLASS_NAME_ID
		).put(
			"classTypeId", _CLASS_TYPE_ID
		).put(
			"operatorName", operatorName
		).put(
			"propertyName", propertyName
		).put(
			"value", valueJSONArray
		);
	}

	private JSONObject _picklistValueJSONObject(String value) {
		return JSONUtil.put(
			"label", value
		).put(
			"value", value
		);
	}

	private Query _runAndAssertCommonFieldRow(JSONObject filterJSONObject) {
		return _assertCommonFieldRow(
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, JSONUtil.putAll(filterJSONObject), LocaleUtil.US));
	}

	private Query _runAndAssertNegatedCommonFieldRow(
		JSONObject filterJSONObject) {

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, JSONUtil.putAll(filterJSONObject), LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 1, booleanClauses.length);

		BooleanQuery outerBooleanQuery =
			(BooleanQuery)booleanClauses[0].getClause();

		BooleanClause<Query> rowBooleanClause = outerBooleanQuery.clauses(
		).get(
			0
		);

		Assert.assertEquals(
			BooleanClauseOccur.MUST, rowBooleanClause.getBooleanClauseOccur());

		BooleanQuery negatedBooleanQuery =
			(BooleanQuery)rowBooleanClause.getClause();

		List<BooleanClause<Query>> negatedBooleanClauses =
			negatedBooleanQuery.clauses();

		Assert.assertEquals(
			negatedBooleanClauses.toString(), 2, negatedBooleanClauses.size());

		Assert.assertTrue(
			negatedBooleanClauses.get(
				0
			).getClause() instanceof MatchAllQuery);
		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			negatedBooleanClauses.get(
				0
			).getBooleanClauseOccur());

		Assert.assertEquals(
			BooleanClauseOccur.MUST_NOT,
			negatedBooleanClauses.get(
				1
			).getBooleanClauseOccur());

		return negatedBooleanClauses.get(
			1
		).getClause();
	}

	private Query _runAndAssertNestedRow(
		String propertyName, JSONObject filterJSONObject,
		BooleanClauseOccur expectedValueOccur) {

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, JSONUtil.putAll(filterJSONObject), LocaleUtil.US);

		return _assertNestedRow(
			booleanClauses, 0, propertyName, expectedValueOccur);
	}

	private String _runRelativeDateGeLowerTerm(String value) {
		Query query = _runAndAssertNestedRow(
			"dueDate", _buildFilter("ge", "dueDate", value),
			BooleanClauseOccur.MUST);

		Assert.assertTrue(query.toString(), query instanceof TermRangeQuery);

		TermRangeQuery termRangeQuery = (TermRangeQuery)query;

		return termRangeQuery.getLowerTerm();
	}

	private ObjectField _setUpKeywordTextObjectField(String name) {
		ObjectField objectField = _setUpObjectField(
			name, ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

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

	private ObjectField _setUpMetadataObjectField(
		String name, String businessType, String dbType) {

		ObjectField objectField = _setUpObjectField(name, businessType, dbType);

		Mockito.when(
			objectField.isMetadata()
		).thenReturn(
			true
		);

		return objectField;
	}

	private ObjectField _setUpObjectField(
		String name, String businessType, String dbType) {

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

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getObjectDefinitionId()
		).thenReturn(
			_CLASS_TYPE_ID
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

	private ObjectField _setUpPicklistObjectField(String name) {
		ObjectField objectField = _setUpObjectField(
			name, ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING);

		Mockito.when(
			objectField.getListTypeDefinitionId()
		).thenReturn(
			_LIST_TYPE_DEFINITION_ID
		);

		Mockito.when(
			objectField.isIndexedAsKeyword()
		).thenReturn(
			true
		);

		return objectField;
	}

	private static final long _CLASS_NAME_ID = RandomTestUtil.randomLong();

	private static final long _CLASS_TYPE_ID = RandomTestUtil.randomLong();

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _LIST_TYPE_DEFINITION_ID =
		RandomTestUtil.randomLong();

	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic =
			_createObjectDefinitionLocalServiceUtilMockedStatic();
	private static final MockedStatic<ObjectFieldLocalServiceUtil>
		_objectFieldLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectFieldLocalServiceUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

}