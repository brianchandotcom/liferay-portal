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
import com.liferay.portal.kernel.search.QueryTerm;
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
	public void testGetFiltersBooleanClausesWithCommonFieldOperators() {
		_assertMatchQuery(
			"localized_title_en_US", "Apple",
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "title", "Apple")));
		_assertMatchQuery(
			"localized_title_en_US", "App",
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("contains", "title", "App")));

		_assertTermQuery(
			"userName", "Alice",
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "userName", "Alice")));
		_assertWildcardQuery(
			"userName", "*Alice*",
			_runAndAssertNegatedCommonFieldRow(
				_buildCommonFieldFilter("not-contains", "userName", "Alice")));

		_assertTermQuery(
			"viewCount", "5",
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "viewCount", "5")));
		_assertTermQuery(
			"status", "0",
			_runAndAssertNegatedCommonFieldRow(
				_buildCommonFieldFilter("not-eq", "status", "0")));

		_assertTermRangeQuery(
			"priority", false, false, "0.5", null,
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("gt", "priority", "0.5")));

		_assertTermRangeQuery(
			"modified", true, true, "20260115000000", "20260115235959",
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("eq", "modified", "2026-01-15")));
		_assertTermRangeQuery(
			"modified", true, true, "20260115000000", "20260115235959",
			_runAndAssertNegatedCommonFieldRow(
				_buildCommonFieldFilter("not-eq", "modified", "2026-01-15")));
		_assertTermRangeQuery(
			"createDate", false, false, "20260115235959", null,
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilter("gt", "createDate", "2026-01-15")));
		_assertTermRangeQuery(
			"modified", true, true, "20260115000000", "20260120235959",
			_runAndAssertCommonFieldRow(
				_buildCommonFieldFilterWithJSONArrayValue(
					"between", "modified",
					JSONUtil.putAll("2026-01-15", "2026-01-20"))));

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID,
				JSONUtil.putAll(
					_buildCommonFieldFilter(
						"eq", RandomTestUtil.randomString(),
						RandomTestUtil.randomString())),
				LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testGetFiltersBooleanClausesWithDateAndDateTimeOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DATE, "dueDate");

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", true, true, "20260115000000",
			"20260115235959",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("eq", "dueDate", "2026-01-15"), "dueDate"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", false, false, "20260115235959", null,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("gt", "dueDate", "2026-01-15"), "dueDate"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", true, false, "20260115000000", null,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("ge", "dueDate", "2026-01-15"), "dueDate"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", false, false, null, "20260115000000",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("lt", "dueDate", "2026-01-15"), "dueDate"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", false, true, null, "20260115235959",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("le", "dueDate", "2026-01-15"), "dueDate"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", true, true, "20260115000000",
			"20260120235959",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"between", "dueDate",
					JSONUtil.putAll("2026-01-15", "2026-01-20")),
				"dueDate"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
			ObjectFieldConstants.DB_TYPE_DATE_TIME, "reminderAt");

		_assertTermRangeQuery(
			"nestedFieldArray.value_date", true, true, "20260115103000",
			"20260115103059",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("eq", "reminderAt", "2026-01-15 10:30"),
				"reminderAt"));
	}

	@Test
	public void testGetFiltersBooleanClausesWithEqualityOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
			ObjectFieldConstants.DB_TYPE_BOOLEAN, "visible");

		_assertTermQuery(
			"nestedFieldArray.value_boolean", "true",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("eq", "visible", "true"),
				"visible"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER, "viewCount");

		_assertTermQuery(
			"nestedFieldArray.value_integer", "5",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("eq", "viewCount", "5"),
				"viewCount"));

		_assertTermQuery(
			"nestedFieldArray.value_integer", "5",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilter("not-eq", "viewCount", "5"), "viewCount"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG, "externalId");

		_assertTermQuery(
			"nestedFieldArray.value_long", "99999",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("eq", "externalId", "99999"), "externalId"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE, "priority");

		_assertTermQuery(
			"nestedFieldArray.value_double", "3.14",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("eq", "priority", "3.14"),
				"priority"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "title");

		_assertTermQuery(
			"nestedFieldArray.value_text", "keyword",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("eq", "title", "keyword"),
				"title"));

		_assertTermQuery(
			"nestedFieldArray.value_text", "keyword",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilter("not-eq", "title", "keyword"), "title"));

		ObjectField localizedSubtitle = _setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "subtitle");

		Mockito.when(
			localizedSubtitle.isLocalized()
		).thenReturn(
			true
		);

		_assertTermQuery(
			"nestedFieldArray.value_en_US", "keyword",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("eq", "subtitle", "keyword"), "subtitle"));
	}

	@Test
	public void testGetFiltersBooleanClausesWithInvalidInput() {
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
	public void testGetFiltersBooleanClausesWithKeywordTextContainsOperators() {
		_setUpKeywordTextObjectField("learnDocumentation");

		_assertWildcardQuery(
			"nestedFieldArray.value_keyword", "*alpha*",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("contains", "learnDocumentation", "Alpha"),
				"learnDocumentation"));

		_assertWildcardQuery(
			"nestedFieldArray.value_keyword", "*alpha*",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilter("not-contains", "learnDocumentation", "Alpha"),
				"learnDocumentation"));

		_assertTermQuery(
			"nestedFieldArray.value_keyword", "alpha",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilter("eq", "learnDocumentation", "Alpha"),
				"learnDocumentation"));
	}

	@Test
	public void testGetFiltersBooleanClausesWithMetadataObjectFields() {
		_setUpMetadataObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DATE, "modifiedDate");

		_assertTermRangeQuery(
			"modified", true, true, "20260115000000", "20260115235959",
			_runAndAssertCommonFieldRow(
				_buildFilter("eq", "modifiedDate", "2026-01-15")));

		_setUpMetadataObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "creator");

		_assertTermQuery(
			"userName", "Alice",
			_runAndAssertCommonFieldRow(
				_buildFilter("eq", "creator", "Alice")));

		_setUpMetadataObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG, "id");

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				_COMPANY_ID, JSONUtil.putAll(_buildFilter("eq", "id", "5")),
				LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testGetFiltersBooleanClausesWithNumericRangeOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER, "viewCount");

		_assertTermRangeQuery(
			"nestedFieldArray.value_integer", false, false, "5", null,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("gt", "viewCount", "5"),
				"viewCount"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_integer", true, false, "5", null,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("ge", "viewCount", "5"),
				"viewCount"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_integer", false, false, null, "5",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("lt", "viewCount", "5"),
				"viewCount"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_integer", false, true, null, "5",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("le", "viewCount", "5"),
				"viewCount"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_integer", true, true, "5", "10",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"between", "viewCount", JSONUtil.putAll("5", "10")),
				"viewCount"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE, "priority");

		_assertTermRangeQuery(
			"nestedFieldArray.value_double", false, false, "3.14", null,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("gt", "priority", "3.14"),
				"priority"));

		_assertTermRangeQuery(
			"nestedFieldArray.value_double", true, true, "1.0", "5.0",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"between", "priority", JSONUtil.putAll("1.0", "5.0")),
				"priority"));

		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG, "externalId");

		_assertTermRangeQuery(
			"nestedFieldArray.value_long", true, true, "100", "200",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"between", "externalId", JSONUtil.putAll("100", "200")),
				"externalId"));
	}

	@Test
	public void testGetFiltersBooleanClausesWithPicklistMultiValueOperators() {
		_setUpPicklistObjectField("status");

		_assertPicklistBooleanQuery(
			BooleanClauseOccur.SHOULD,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(
						_picklistValueJSONObject("approved"),
						_picklistValueJSONObject("draft"))
				).put(
					"quantifier", "any"
				),
				"status"),
			"approved", "draft");

		_assertPicklistBooleanQuery(
			BooleanClauseOccur.MUST,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(
						_picklistValueJSONObject("approved"),
						_picklistValueJSONObject("draft"))
				).put(
					"quantifier", "all"
				),
				"status"),
			"approved", "draft");

		_assertPicklistBooleanQuery(
			BooleanClauseOccur.SHOULD,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(_picklistValueJSONObject("approved"))),
				"status"),
			"approved");

		_assertPicklistBooleanQuery(
			BooleanClauseOccur.SHOULD,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST_NOT,
				_buildFilterWithJSONArrayValue(
					"not-contains", "status",
					JSONUtil.putAll(
						_picklistValueJSONObject("approved"),
						_picklistValueJSONObject("draft"))
				).put(
					"quantifier", "any"
				),
				"status"),
			"approved", "draft");

		_assertPicklistBooleanQuery(
			BooleanClauseOccur.SHOULD,
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST,
				_buildFilterWithJSONArrayValue(
					"contains", "status",
					JSONUtil.putAll(_picklistValueJSONObject("Approved"))
				).put(
					"quantifier", "any"
				),
				"status"),
			"approved");

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
	public void testGetFiltersBooleanClausesWithRelativeDateOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_DATE,
			ObjectFieldConstants.DB_TYPE_DATE, "dueDate");

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
			"nestedFieldArray.value_date", false, true, null,
			format.format(new Date()) + "235959",
			_runAndAssertNestedRow(
				BooleanClauseOccur.MUST, _buildFilter("le", "dueDate", "now"),
				"dueDate"));
	}

	@Test
	public void testGetFiltersBooleanClausesWithTextContainsOperators() {
		_setUpObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, "title");

		Query containsQuery = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST,
			_buildFilter("contains", "title", "keyword"), "title");

		Assert.assertTrue(
			containsQuery.toString(), containsQuery instanceof MatchQuery);

		Query notContainsQuery = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST_NOT,
			_buildFilter("not-contains", "title", "keyword"), "title");

		Assert.assertTrue(
			notContainsQuery.toString(),
			notContainsQuery instanceof MatchQuery);

		Query containsWithQuantifierQuery = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST,
			_buildFilter(
				"contains", "title", "keyword"
			).put(
				"quantifier", "any"
			),
			"title");

		Assert.assertTrue(
			containsWithQuantifierQuery.toString(),
			containsWithQuantifierQuery instanceof MatchQuery);
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
		String expectedField, String expectedValue, Query query) {

		Assert.assertTrue(query.toString(), query instanceof MatchQuery);

		MatchQuery matchQuery = (MatchQuery)query;

		Assert.assertEquals(expectedField, matchQuery.getField());
		Assert.assertEquals(expectedValue, matchQuery.getValue());
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

		Assert.assertTrue(rowIndex < rowBooleanClauses.size());

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

	private void _assertPicklistBooleanQuery(
		BooleanClauseOccur expectedInnerOccur, Query query,
		String... expectedValues) {

		Assert.assertTrue(query.toString(), query instanceof BooleanQuery);

		BooleanQuery booleanQuery = (BooleanQuery)query;

		List<BooleanClause<Query>> innerBooleanClauses = booleanQuery.clauses();

		Assert.assertEquals(
			innerBooleanClauses.toString(), expectedValues.length,
			innerBooleanClauses.size());

		for (int i = 0; i < expectedValues.length; i++) {
			BooleanClause<Query> booleanClause = innerBooleanClauses.get(i);

			Assert.assertEquals(
				expectedInnerOccur, booleanClause.getBooleanClauseOccur());

			_assertTermQuery(
				"nestedFieldArray.value_keyword", expectedValues[i],
				booleanClause.getClause());
		}
	}

	private void _assertTermQuery(
		String expectedField, String expectedValue, Query query) {

		Assert.assertTrue(query.toString(), query instanceof TermQuery);

		TermQuery termQuery = (TermQuery)query;

		QueryTerm queryTerm = termQuery.getQueryTerm();

		Assert.assertEquals(expectedField, queryTerm.getField());
		Assert.assertEquals(expectedValue, queryTerm.getValue());
	}

	private void _assertTermRangeQuery(
		String expectedField, boolean expectedIncludesLower,
		boolean expectedIncludesUpper, String expectedLower,
		String expectedUpper, Query query) {

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
		String expectedField, String expectedValue, Query query) {

		Assert.assertTrue(query.toString(), query instanceof WildcardQuery);

		WildcardQuery wildcardQuery = (WildcardQuery)query;

		QueryTerm queryTerm = wildcardQuery.getQueryTerm();

		Assert.assertEquals(expectedField, queryTerm.getField());
		Assert.assertEquals(expectedValue, queryTerm.getValue());
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

		List<BooleanClause<Query>> rowBooleanClauses =
			outerBooleanQuery.clauses();

		BooleanClause<Query> rowBooleanClause = rowBooleanClauses.get(0);

		Assert.assertEquals(
			BooleanClauseOccur.MUST, rowBooleanClause.getBooleanClauseOccur());

		BooleanQuery negatedBooleanQuery =
			(BooleanQuery)rowBooleanClause.getClause();

		List<BooleanClause<Query>> negatedBooleanClauses =
			negatedBooleanQuery.clauses();

		Assert.assertEquals(
			negatedBooleanClauses.toString(), 2, negatedBooleanClauses.size());

		BooleanClause<Query> matchAllBooleanClause = negatedBooleanClauses.get(
			0);

		Assert.assertTrue(
			matchAllBooleanClause.getClause() instanceof MatchAllQuery);
		Assert.assertEquals(
			BooleanClauseOccur.MUST,
			matchAllBooleanClause.getBooleanClauseOccur());

		BooleanClause<Query> negatedBooleanClause = negatedBooleanClauses.get(
			1);

		Assert.assertEquals(
			BooleanClauseOccur.MUST_NOT,
			negatedBooleanClause.getBooleanClauseOccur());

		return negatedBooleanClause.getClause();
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

	private String _runRelativeDateGeLowerTerm(String value) {
		Query query = _runAndAssertNestedRow(
			BooleanClauseOccur.MUST, _buildFilter("ge", "dueDate", value),
			"dueDate");

		Assert.assertTrue(query.toString(), query instanceof TermRangeQuery);

		TermRangeQuery termRangeQuery = (TermRangeQuery)query;

		return termRangeQuery.getLowerTerm();
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

	private ObjectField _setUpMetadataObjectField(
		String businessType, String dbType, String name) {

		ObjectField objectField = _setUpObjectField(businessType, dbType, name);

		Mockito.when(
			objectField.isMetadata()
		).thenReturn(
			true
		);

		return objectField;
	}

	private ObjectField _setUpObjectField(
		String businessType, String dbType, String name) {

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
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, name);

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