/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.condition;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.FloatSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.Duration;
import java.time.Instant;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class SXPConditionEvaluatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAllOf() throws Exception {
		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"all_of",
					JSONUtil.put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${integer}"
						).put(
							"value", 1
						)
					).put(
						"greater_than",
						JSONUtil.put(
							"parameter_name", "${double}"
						).put(
							"value", 0.0D
						)
					).put(
						"less_than_equals",
						JSONUtil.put(
							"parameter_name", "${float}"
						).put(
							"value", 1.0F
						)
					))));
		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"all_of",
					JSONUtil.put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${integer}"
						).put(
							"value", 1
						)
					).put(
						"greater_than",
						JSONUtil.put(
							"parameter_name", "${double}"
						).put(
							"value", 2.0D
						)
					).put(
						"less_than_equals",
						JSONUtil.put(
							"parameter_name", "${float}"
						).put(
							"value", 1.0F
						)
					))));
	}

	@Test
	public void testAnyOf() throws Exception {

		// TODO Assert.assertFalse

		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"any_of",
					JSONUtil.put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${integer}"
						).put(
							"value", 2
						)
					).put(
						"greater_than",
						JSONUtil.put(
							"parameter_name", "${double}"
						).put(
							"value", 0.0D
						)
					).put(
						"less_than_equals",
						JSONUtil.put(
							"parameter_name", "${float}"
						).put(
							"value", 0.0F
						)
					))));
	}

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(_evaluate("contains", "integer_array", 1));
		Assert.assertTrue(_evaluate("contains", "long_array", 1L));
		Assert.assertTrue(_evaluate("contains", "string_array", "one"));
	}

	@Test
	public void testEmpty() throws Exception {
		Assert.assertTrue(
			SXPConditionEvaluator.evaluate(null, _sxpParameterData));
		Assert.assertTrue(
			SXPConditionEvaluator.evaluate(
				JSONFactoryUtil.createJSONObject(), _sxpParameterData));
	}

	@Test
	public void testEquals() throws Exception {
		Assert.assertTrue(_evaluate("equals", "boolean", true));
		Assert.assertTrue(_evaluateDate("equals", _toDateString(0)));
		Assert.assertTrue(_evaluate("equals", "double", 1.0D));
		Assert.assertTrue(_evaluate("equals", "float", 1.0F));
		Assert.assertTrue(_evaluate("equals", "integer", 1));
		Assert.assertTrue(_evaluate("equals", "long", 1L));
		Assert.assertTrue(_evaluate("equals", "string", "one"));
	}

	@Test
	public void testGreaterThan() throws Exception {
		Assert.assertTrue(_evaluateDate("greater_than", _toDateString(-1)));
		Assert.assertTrue(_evaluate("greater_than", "double", 0.0D));
		Assert.assertTrue(_evaluate("greater_than", "float", 0.0F));
		Assert.assertTrue(_evaluate("greater_than", "integer", 0));
		Assert.assertTrue(_evaluate("greater_than", "long", 0L));
	}

	@Test
	public void testGreaterThanEquals() throws Exception {
		Assert.assertTrue(
			_evaluateDate("greater_than_equals", _toDateString(0)));
		Assert.assertTrue(_evaluate("greater_than_equals", "double", 1.0D));
		Assert.assertTrue(_evaluate("greater_than_equals", "float", 1.0F));
		Assert.assertTrue(_evaluate("greater_than_equals", "integer", 1));
		Assert.assertTrue(_evaluate("greater_than_equals", "long", 1L));
	}

	@Test
	public void testIn() throws Exception {
		Assert.assertTrue(
			_evaluate("in", "double", JSONUtil.putAll(1.0D, 2.0D)));
		Assert.assertTrue(
			_evaluate("in", "float", JSONUtil.putAll(1.0F, 2.0F)));
		Assert.assertTrue(_evaluate("in", "integer", JSONUtil.putAll(1, 2)));
		Assert.assertTrue(_evaluate("in", "long", JSONUtil.putAll(1L, 2L)));
		Assert.assertTrue(
			_evaluate("in", "string", JSONUtil.putAll("one", "two")));
	}

	@Test
	public void testInRange() throws Exception {
		Assert.assertTrue(
			_evaluateDate(
				"in_range",
				JSONUtil.putAll(_toDateString(-1), _toDateString(1))));
		Assert.assertTrue(
			_evaluate("in_range", "double", JSONUtil.putAll(0.0D, 2.0D)));
		Assert.assertTrue(
			_evaluate("in_range", "float", JSONUtil.putAll(0.0F, 2.0F)));
		Assert.assertTrue(
			_evaluate("in_range", "integer", JSONUtil.putAll(0, 2)));
		Assert.assertTrue(
			_evaluate("in_range", "long", JSONUtil.putAll(0L, 2L)));
	}

	@Test
	public void testLessThan() throws Exception {
		Assert.assertTrue(_evaluateDate("less_than", _toDateString(1)));
		Assert.assertTrue(_evaluate("less_than", "double", 2.0D));
		Assert.assertTrue(_evaluate("less_than", "float", 2.0F));
		Assert.assertTrue(_evaluate("less_than", "integer", 2));
		Assert.assertTrue(_evaluate("less_than", "long", 2L));
	}

	@Test
	public void testLessThanEquals() throws Exception {
		Assert.assertTrue(
			_evaluateDate(
				"less_than_equals", "yyyyMMddHHmmssS",
				_toDateString(0, "yyyyMMddHHmmssS")));
		Assert.assertTrue(_evaluate("less_than_equals", "double", 1.0D));
		Assert.assertTrue(_evaluate("less_than_equals", "float", 1.0F));
		Assert.assertTrue(_evaluate("less_than_equals", "integer", 1));
		Assert.assertTrue(_evaluate("less_than_equals", "long", 1L));
	}

	@Test
	public void testNestedAllOf() throws Exception {
		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"all_of",
					JSONUtil.put(
						"all_of",
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameter_name", "${integer}"
							).put(
								"value", 1
							)
						).put(
							"greater_than",
							JSONUtil.put(
								"parameter_name", "${double}"
							).put(
								"value", 1.0D
							)
						).put(
							"less_than_equals",
							JSONUtil.put(
								"parameter_name", "${float}"
							).put(
								"value", 2.0F
							)
						)
					).put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${long}"
						).put(
							"value", 1
						)
					))));

		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"all_of",
					JSONUtil.put(
						"all_of",
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameter_name", "${integer}"
							).put(
								"value", 1
							)
						).put(
							"greater_than",
							JSONUtil.put(
								"parameter_name", "${double}"
							).put(
								"value", 0.0D
							)
						).put(
							"less_than_equals",
							JSONUtil.put(
								"parameter_name", "${float}"
							).put(
								"value", 1.0F
							)
						)
					).put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${long}"
						).put(
							"value", 1
						)
					))));
	}

	@Test
	public void testNestedAnyOf() throws Exception {
		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"any_of",
					JSONUtil.put(
						"any_of",
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameter_name", "${integer}"
							).put(
								"value", 0
							)
						).put(
							"greater_than",
							JSONUtil.put(
								"parameter_name", "${double}"
							).put(
								"value", 2.0D
							)
						).put(
							"less_than_equals",
							JSONUtil.put(
								"parameter_name", "${float}"
							).put(
								"value", 0.0F
							)
						)
					).put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${long}"
						).put(
							"value", 0L
						)
					))));

		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"any_of",
					JSONUtil.put(
						"any_of",
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameter_name", "${integer}"
							).put(
								"value", 0
							)
						).put(
							"greater_than",
							JSONUtil.put(
								"parameter_name", "${double}"
							).put(
								"value", 1.0D
							)
						).put(
							"less_than_equals",
							JSONUtil.put(
								"parameter_name", "${float}"
							).put(
								"value", 1.0F
							)
						)
					).put(
						"equals",
						JSONUtil.put(
							"parameter_name", "${long}"
						).put(
							"value", 0
						)
					))));
	}

	@Test
	public void testNotContains() throws Exception {
		Assert.assertTrue(_evaluate("not_contains", "integer_array", 4));
		Assert.assertTrue(_evaluate("not_contains", "long_array", 4L));
		Assert.assertTrue(_evaluate("not_contains", "string_array", "four"));
	}

	@Test
	public void testNotEquals() throws Exception {
		Assert.assertTrue(_evaluate("not_equals", "boolean", false));
		Assert.assertTrue(_evaluateDate("not_equals", _toDateString(-1)));
		Assert.assertTrue(_evaluate("not_equals", "double", 2.0D));
		Assert.assertTrue(_evaluate("not_equals", "float", 2.0F));
		Assert.assertTrue(_evaluate("not_equals", "integer", 2));
		Assert.assertTrue(_evaluate("not_equals", "long", 2L));
		Assert.assertTrue(_evaluate("not_equals", "string", "two"));
	}

	@Test
	public void testNotIn() throws Exception {
		Assert.assertTrue(
			_evaluate("not_in", "double", JSONUtil.putAll(2.0D, 3.0D)));
		Assert.assertTrue(
			_evaluate("not_in", "float", JSONUtil.putAll(2.0F, 3.0F)));
		Assert.assertTrue(
			_evaluate("not_in", "integer", JSONUtil.putAll(2, 3)));
		Assert.assertTrue(_evaluate("not_in", "long", JSONUtil.putAll(2L, 3L)));
		Assert.assertTrue(
			_evaluate("not_in", "string", JSONUtil.putAll("two", "three")));
	}

	@Test
	public void testNotInRange() throws Exception {
		Assert.assertTrue(
			_evaluateDate(
				"not_in_range",
				JSONUtil.putAll(_toDateString(1), _toDateString(2))));
		Assert.assertTrue(
			_evaluate("not_in_range", "double", JSONUtil.putAll(2.0D, 3.0D)));
		Assert.assertTrue(
			_evaluate("not_in_range", "float", JSONUtil.putAll(2.0F, 3.0F)));
		Assert.assertTrue(
			_evaluate("not_in_range", "integer", JSONUtil.putAll(2, 3)));
		Assert.assertTrue(
			_evaluate("not_in_range", "long", JSONUtil.putAll(2L, 3L)));
	}

	private boolean _evaluate(JSONObject jsonObject) {
		return SXPConditionEvaluator.evaluate(jsonObject, _sxpParameterData);
	}

	private boolean _evaluate(String key, String parameterName, Object value) {
		return _evaluate(
			JSONUtil.put(
				key,
				JSONUtil.put(
					"parameter_name", "${" + parameterName + "}"
				).put(
					"value", value
				)));
	}

	private boolean _evaluateDate(String key, Object value) {
		return _evaluateDate(key, "yyyyMMdd", value);
	}

	private boolean _evaluateDate(String key, String pattern, Object value) {
		return _evaluate(
			JSONUtil.put(
				key,
				JSONUtil.put(
					"date_format", pattern
				).put(
					"parameter_name", "${date}"
				).put(
					"value", value
				)));
	}

	private String _toDateString(int offset) {
		return _toDateString(offset, "yyyyMMdd");
	}

	private String _toDateString(long offset, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);

		if (offset == 0) {
			return dateFormat.format(_date);
		}

		Instant instant = _date.toInstant();

		return dateFormat.format(
			Date.from(instant.plus(Duration.ofDays(offset))));
	}

	private static final Date _date = new Date();

	private final SXPParameterData _sxpParameterData = new SXPParameterData(
		"test",
		SetUtil.fromArray(
			new SXPParameter[] {
				new BooleanSXPParameter("boolean", true, true),
				new DateSXPParameter("date", true, _date),
				new DoubleSXPParameter("double", true, 1.0D),
				new FloatSXPParameter("float", true, 1.0F),
				new IntegerSXPParameter("integer", true, 1),
				new IntegerArraySXPParameter(
					"integer_array", true, new Integer[] {1, 2, 3}),
				new LongArraySXPParameter(
					"long_array", true, new Long[] {1L, 2L, 3L}),
				new LongSXPParameter("long", true, 1L),
				new StringArraySXPParameter(
					"string_array", true, new String[] {"one", "two", "three"}),
				new StringSXPParameter("string", true, "one")
			}));

}