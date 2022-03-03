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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class ObjectEntryLocalServiceSearchObjectEntriesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testBigDecimal() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"PrecisionDecimal", "BigDecimal", true, false, null, "Alpha",
				"alpha", false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new BigDecimal("45")
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new BigDecimal("54")
			).build());

		_assertKeywords("[44 TO 46]", 1);
		_assertKeywords("[44.9999 TO 45.1111]", 1);
		_assertKeywords("4", 0);
		_assertKeywords("45", 1);
		_assertKeywords("45.0000", 1);
		_assertKeywords("45.0001", 0);
		_assertKeywords("bravo 4 charlie", 0);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("bravo 45.0 charlie", 1);
		_assertKeywords("search from [ 44 TO 46 ]", 1);
	}

	@Test
	public void testBigDecimalKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"PrecisionDecimal", "BigDecimal", true, true, null, "Alpha",
				"alpha", false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new BigDecimal("45")
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new BigDecimal("54")
			).build());

		_assertKeywords("[44 TO 46]", 0);
		_assertKeywords("[44.9999 TO 45.1111]", 0);
		_assertKeywords("4", 1);
		_assertKeywords("45", 1);
		_assertKeywords("45.0000", 1);
		_assertKeywords("45.0001", 0);
		_assertKeywords("bravo 4 charlie", 1);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("bravo 45.0 charlie", 1);
		_assertKeywords("search from [ 44 TO 46 ]", 0);
	}

	@Test
	public void testBoolean() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Boolean", "Boolean", true, false, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", true
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", false
			).build());

		_assertKeywords("0", 0);
		_assertKeywords("1", 0);
		_assertKeywords("false", 1);
		_assertKeywords("no", 1);
		_assertKeywords("the false statement is not", 1);
		_assertKeywords("the false statement is not true", 2);
		_assertKeywords("Tr", 0);
		_assertKeywords("TRUE", 1);
		_assertKeywords("True", 1);
		_assertKeywords("true", 1);
		_assertKeywords("yes", 1);
	}

	@Test
	public void testBooleanKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Boolean", "Boolean", true, true, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", true
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", false
			).build());

		_assertKeywords("0", 0);
		_assertKeywords("1", 0);
		_assertKeywords("false", 1);
		_assertKeywords("no", 0);
		_assertKeywords("the false statement is not", 1);
		_assertKeywords("the false statement is not true", 2);
		_assertKeywords("Tr", 1);
		_assertKeywords("TRUE", 1);
		_assertKeywords("True", 1);
		_assertKeywords("true", 1);
		_assertKeywords("yes", 0);
	}

	@Test
	public void testDate() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Date", "Date", true, false, null, "Alpha", "alpha", false));

		long date = 1632335654272L;

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new Date(date)
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new Date(date + (Time.HOUR * 1))
			).build());

		_assertKeywords("[ 2020 TO 2022 ]", 0);
		_assertKeywords("[ 2021 TO 2021 ]", 0);
		_assertKeywords("[2020 TO 2022]", 0);
		_assertKeywords("[2021 TO 2021]", 0);
		_assertKeywords("[20210922183413 TO 20210922183415]", 1);
		_assertKeywords("[20210922183413 TO 20210923183415]", 2);
		_assertKeywords("09", 0);
		_assertKeywords("1632335654272", 0);
		_assertKeywords("18", 0);
		_assertKeywords("2021", 0);
		_assertKeywords("2021-09", 0);
		_assertKeywords("2021-09-22", 0);
		_assertKeywords("2021-09-22 18:34:14.272", 0);
		_assertKeywords("20210922183414", 1);
	}

	@Test
	public void testDateKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Date", "Date", true, true, null, "Alpha", "alpha", false));

		long date = 1632335654272L;

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new Date(date)
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", new Date(date + (Time.DAY * 1))
			).build());

		_assertKeywords("[ 2020 TO 2022 ]", 0);
		_assertKeywords("[ 2021 TO 2021 ]", 0);
		_assertKeywords("[2020 TO 2022]", 0);
		_assertKeywords("[2021 TO 2021]", 0);
		_assertKeywords("[20210922183413 TO 20210922183415]", 0);
		_assertKeywords("[20210922183413 TO 20210923183415]", 0);
		_assertKeywords("09", 0);
		_assertKeywords("1632335654272", 0);
		_assertKeywords("18", 0);
		_assertKeywords("2021", 2);
		_assertKeywords("2021-09", 2);
		_assertKeywords("2021-09-22", 1);
		_assertKeywords("2021-09-22 18:34:14.272", 1);
		_assertKeywords("20210922183414", 0);
	}

	@Test
	public void testDouble() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Decimal", "Double", true, false, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 45D
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 54D
			).build());

		_assertKeywords("[44 TO 46]", 1);
		_assertKeywords("[44.9999 TO 45.1111]", 1);
		_assertKeywords("4", 0);
		_assertKeywords("45", 1);
		_assertKeywords("45.0", 1);
		_assertKeywords("45.0000", 1);
		_assertKeywords("45.0001", 0);
		_assertKeywords("bravo 4 charlie", 0);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("bravo 45.0 charlie", 1);
		_assertKeywords("search from [ 44 TO 46 ]", 1);
	}

	@Test
	public void testDoubleKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Decimal", "Double", true, true, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 45D
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 54D
			).build());

		_assertKeywords("[44 TO 46]", 0);
		_assertKeywords("[44.9999 TO 45.1111]", 0);
		_assertKeywords("4", 1);
		_assertKeywords("45", 1);
		_assertKeywords("45.0", 1);
		_assertKeywords("45.0000", 0);
		_assertKeywords("45.0001", 0);
		_assertKeywords("bravo 4 charlie", 1);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("bravo 45.0 charlie", 1);
		_assertKeywords("search from [ 44 TO 46 ]", 0);
	}

	@Test
	public void testInteger() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Integer", "Integer", true, false, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 45
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 54
			).build());

		_assertKeywords("[44 TO 46]", 1);
		_assertKeywords("[44.9 TO 45.1]", 0);
		_assertKeywords("4", 0);
		_assertKeywords("45", 1);
		_assertKeywords("45.0", 0);
		_assertKeywords("bravo 4 charlie", 0);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("bravo 45.0 charlie", 0);
		_assertKeywords("search from [ 44 TO 46 ]", 1);
	}

	@Test
	public void testIntegerKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Integer", "Integer", true, true, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 45
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 54
			).build());

		_assertKeywords("[44 TO 46]", 0);
		_assertKeywords("[44.9 TO 45.1]", 0);
		_assertKeywords("4", 1);
		_assertKeywords("45", 1);
		_assertKeywords("45.0", 0);
		_assertKeywords("bravo 4 charlie", 1);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("bravo 45.0 charlie", 0);
		_assertKeywords("search from [ 44 TO 46 ]", 0);
	}

	@Test
	public void testLong() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"LongInteger", "Long", true, false, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 45L
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 54L
			).build());

		_assertKeywords("[44 TO 46]", 1);
		_assertKeywords("4", 0);
		_assertKeywords("45", 1);
		_assertKeywords("bravo 4 charlie", 0);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("search from [ 44 TO 46 ]", 1);
	}

	@Test
	public void testLongKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"LongInteger", "Long", true, true, null, "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 45L
			).build());
		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", 54L
			).build());

		_assertKeywords("[44 TO 46]", 0);
		_assertKeywords("4", 1);
		_assertKeywords("45", 1);
		_assertKeywords("bravo 4 charlie", 1);
		_assertKeywords("bravo 45 charlie", 1);
		_assertKeywords("search from [ 44 TO 46 ]", 0);
	}

	@Ignore
	@Test
	public void testSearchByTitleValue() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Text", "String", false, false, null, "Alpha", "alpha", false));

		ObjectEntry objectEntry = _addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", RandomTestUtil.randomString()
			).put(
				"beta", RandomTestUtil.randomString()
			).build());

		String titleValue = objectEntry.getTitleValue();

		Assert.assertTrue(Validator.isNotNull(titleValue));

		int index = RandomTestUtil.randomInt(0, titleValue.length());

		_assertKeywords(titleValue.substring(0, index), 1);
	}

	@Test
	public void testString() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Text", "String", true, false, null, "Alpha", "alpha", false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", "The quick brown fox trusted the lazy dog"
			).build());

		_assertKeywords("fox", 1);
		_assertKeywords("LAZY dog", 1);
		_assertKeywords("lazy dog", 1);
		_assertKeywords("trust", 0);
		_assertKeywords("trusted", 1);
	}

	@Test
	public void testStringAnalyzed() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Text", "String", true, false, "en_US", "Alpha", "alpha",
				false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", "The english brown fox trusted the lazy dog"
			).build());

		_assertKeywords("fox", 1);
		_assertKeywords("LAZY dog", 1);
		_assertKeywords("lazy dog", 1);
		_assertKeywords("trust", 1);
		_assertKeywords("trusted", 1);
	}

	@Test
	public void testStringKeyword() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Text", "String", true, true, null, "Alpha", "alpha", false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", "test45"
			).build());

		_assertKeywords("45", 0);
		_assertKeywords("te", 1);
		_assertKeywords("test", 1);
		_assertKeywords("test4", 1);
		_assertKeywords("TEST45", 1);
		_assertKeywords("Test45", 1);
		_assertKeywords("test45", 1);
		_assertKeywords("test456", 0);
	}

	@Test
	public void testStringNotIndexed() throws Exception {
		_addObjectDefinition(
			ObjectFieldUtil.createObjectField(
				"Text", "String", false, false, null, "Alpha", "alpha", false));

		_addObjectEntry(
			HashMapBuilder.<String, Serializable>put(
				"alpha", "The unsearchable brown fox jumps over the lazy dog"
			).build());

		_assertKeywords("fox", 0);
		_assertKeywords("The", 0);
		_assertKeywords("The unsearchable", 0);
		_assertKeywords("unsearchable", 0);
	}

	private void _addObjectDefinition(ObjectField objectField)
		throws Exception {

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(objectField));

		_objectDefinition.setTitleObjectFieldId(_getTitleObjectFieldId());

		_objectDefinitionLocalService.updateObjectDefinition(_objectDefinition);

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());
	}

	private ObjectEntry _addObjectEntry(Map<String, Serializable> values)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), values,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertKeywords(String keywords, int count) throws Exception {
		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), keywords, 0, 20);

		Assert.assertEquals(count, baseModelSearchResult.getLength());
	}

	private long _getTitleObjectFieldId() throws Exception {
		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(), "Text", "String", false,
			false, null, LocalizedMapUtil.getLocalizedMap("Beta"), "beta",
			false, Collections.emptyList());

		return objectField.getObjectFieldId();
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

}