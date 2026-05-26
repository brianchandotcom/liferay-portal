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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.MatchQuery;
import com.liferay.portal.kernel.search.NestedQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
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
	}

	@Test
	public void testBooleanEqBuildsTermQuery() {
		_stubObjectField(
			"visible", ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
			ObjectFieldConstants.DB_TYPE_BOOLEAN);

		Query valueQuery = _runAndAssertNestedRow(
			"visible", _buildFilter("eq", "visible", "true"),
			BooleanClauseOccur.MUST);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_boolean", "true");
	}

	@Test
	public void testDecimalEqBuildsTermQuery() {
		_stubObjectField(
			"priority", ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
			ObjectFieldConstants.DB_TYPE_DOUBLE);

		Query valueQuery = _runAndAssertNestedRow(
			"priority", _buildFilter("eq", "priority", "3.14"),
			BooleanClauseOccur.MUST);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_double", "3.14");
	}

	@Test
	public void testIntegerEqBuildsTermQuery() {
		_stubObjectField(
			"viewCount", ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER);

		Query valueQuery = _runAndAssertNestedRow(
			"viewCount", _buildFilter("eq", "viewCount", "5"),
			BooleanClauseOccur.MUST);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_integer", "5");
	}

	@Test
	public void testIntegerNotEqWrapsTermQueryInMustNot() {
		_stubObjectField(
			"viewCount", ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
			ObjectFieldConstants.DB_TYPE_INTEGER);

		Query valueQuery = _runAndAssertNestedRow(
			"viewCount", _buildFilter("not-eq", "viewCount", "5"),
			BooleanClauseOccur.MUST_NOT);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_integer", "5");
	}

	@Test
	public void testLocalizedTextEqUsesLocalizedSubfield() {
		ObjectField objectField = _stubObjectField(
			"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		Mockito.when(
			objectField.isLocalized()
		).thenReturn(
			true
		);

		Query valueQuery = _runAndAssertNestedRow(
			"title", _buildFilter("eq", "title", "keyword"),
			BooleanClauseOccur.MUST);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_en_US", "keyword");
	}

	@Test
	public void testLongIntegerEqBuildsTermQuery() {
		_stubObjectField(
			"externalId", ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
			ObjectFieldConstants.DB_TYPE_LONG);

		Query valueQuery = _runAndAssertNestedRow(
			"externalId", _buildFilter("eq", "externalId", "99999"),
			BooleanClauseOccur.MUST);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_long", "99999");
	}

	@Test
	public void testReturnsEmptyClausesWhenFiltersJSONArrayIsEmpty() {
		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				JSONFactoryUtil.createJSONArray(), _COMPANY_ID, LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testReturnsEmptyClausesWhenFiltersJSONArrayIsNull() {
		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				null, _COMPANY_ID, LocaleUtil.US);

		Assert.assertEquals(
			Arrays.toString(booleanClauses), 0, booleanClauses.length);
	}

	@Test
	public void testTextContainsBuildsMatchQueryInsideNestedEnvelope() {
		_stubObjectField(
			"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		Query valueQuery = _runAndAssertNestedRow(
			"title", _buildFilter("contains", "title", "keyword"),
			BooleanClauseOccur.MUST);

		Assert.assertTrue(
			valueQuery.toString(), valueQuery instanceof MatchQuery);
	}

	@Test
	public void testTextEqBuildsTermQuery() {
		_stubObjectField(
			"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		Query valueQuery = _runAndAssertNestedRow(
			"title", _buildFilter("eq", "title", "keyword"),
			BooleanClauseOccur.MUST);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_text", "keyword");
	}

	@Test
	public void testTextNotEqWrapsTermQueryInMustNot() {
		_stubObjectField(
			"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING);

		Query valueQuery = _runAndAssertNestedRow(
			"title", _buildFilter("not-eq", "title", "keyword"),
			BooleanClauseOccur.MUST_NOT);

		_assertTermQuery(valueQuery, "nestedFieldArray.value_text", "keyword");
	}

	private static MockedStatic<ObjectDefinitionLocalServiceUtil>
		_createObjectDefinitionLocalServiceUtilMockedStatic() {

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		return Mockito.mockStatic(ObjectDefinitionLocalServiceUtil.class);
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
			innerBooleanClauses.toString(), 2, innerBooleanClauses.size());

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

		Assert.assertEquals(
			expectedValueOccur,
			innerBooleanClauses.get(
				1
			).getBooleanClauseOccur());

		return innerBooleanClauses.get(
			1
		).getClause();
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

	private Query _runAndAssertNestedRow(
		String propertyName, JSONObject filterJSONObject,
		BooleanClauseOccur expectedValueOccur) {

		BooleanClause[] booleanClauses =
			AssetListFiltersUtil.getFiltersBooleanClauses(
				JSONUtil.putAll(filterJSONObject), _COMPANY_ID, LocaleUtil.US);

		return _assertNestedRow(
			booleanClauses, 0, propertyName, expectedValueOccur);
	}

	private ObjectField _stubObjectField(
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
			() -> ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				_CLASS_TYPE_ID)
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

	private static final long _CLASS_NAME_ID = 30601L;

	private static final long _CLASS_TYPE_ID = 42L;

	private static final long _COMPANY_ID = 12345L;

	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic =
			_createObjectDefinitionLocalServiceUtilMockedStatic();
	private static final MockedStatic<ObjectFieldLocalServiceUtil>
		_objectFieldLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectFieldLocalServiceUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

}