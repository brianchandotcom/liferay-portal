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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
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
import com.liferay.search.experiences.rest.dto.v1_0.Condition;
import com.liferay.search.experiences.rest.dto.v1_0.Exists;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.Duration;
import java.time.Instant;

import java.util.Date;
import java.util.function.Consumer;

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
	public void testAllConditions() throws Exception {
		_setSXPParameters(new IntegerSXPParameter("integer", true, 2));

		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"allConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"exists",
							JSONUtil.put("parameterName", "${integer}")),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${integer}"
							).put(
								"value", 1
							))))));

		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"allConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"exists",
							JSONUtil.put("parameterName", "${integer}")),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${integer}"
							).put(
								"value", 2
							))))));
	}

	@Test
	public void testAnyConditions() throws Exception {
		_setSXPParameters(new IntegerSXPParameter("integer", true, 2));

		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"anyConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${integer}"
							).put(
								"value", 1
							)),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${integer}"
							).put(
								"value", 3
							))))));

		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"anyConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${integer}"
							).put(
								"value", 1
							)),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${integer}"
							).put(
								"value", 2
							))))));
	}

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "integer_array", _withValue(1))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "long_array", _withValue(1L))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "string_array", _withValue("one"))));
	}

	@Test
	public void testEmpty() throws Exception {
		_condition = null;

		Assert.assertTrue(_evaluate());

		_condition = new Condition();

		Assert.assertTrue(_evaluate());
	}

	@Test
	public void testEquals() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "boolean", _withValue(true))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "date", _withValue(_toDateString(0)),
					_withDateFormat())));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("equals", "double", _withValue(1.0D))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("equals", "float", _withValue(1.0F))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("equals", "integer", _withValue(1))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("equals", "long", _withValue(1L))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "string", _withValue("one"))));
	}

	@Test
	public void testExists() throws Exception {
		Exists exists = new Exists();

		_condition.setExists(exists);

		String parameterName = RandomTestUtil.randomString();

		exists.setParameterName(parameterName);

		Assert.assertFalse(_evaluate());

		_setSXPParameters(new StringSXPParameter(parameterName, false, null));

		Assert.assertFalse(_evaluate());

		_setSXPParameters(new StringSXPParameter(parameterName, true, null));

		Assert.assertFalse(_evaluate());

		exists.setParameterName(_toTemplateVariable(parameterName));

		Assert.assertTrue(_evaluate());
	}

	@Test
	public void testGreaterThan() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gt", _toDateString(-1), _withDateFormat())));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("double", "gt", 0.0D)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("float", "gt", 0.0F)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("integer", "gt", 0)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("long", "gt", 0L)));
	}

	@Test
	public void testGreaterThanEquals() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gte", _toDateString(0), _withDateFormat())));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("double", "gte", 1.0D)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("float", "gte", 1.0F)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("integer", "gte", 1)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("long", "gte", 1L)));
	}

	@Test
	public void testIn() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"in", "double", _withValues(1.0D, 2.0D))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"in", "float", _withValues(1.0F, 2.0F))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("in", "integer", _withValues(1, 2))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("in", "long", _withValues(1L, 2L))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"in", "string", _withValues("one", "two"))));
	}

	@Test
	public void testLessThan() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "lt", _toDateString(1), _withDateFormat())));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("double", "lt", 2.0D)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("float", "lt", 2.0F)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("integer", "lt", 2)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("long", "lt", 2L)));
	}

	@Test
	public void testLessThanEquals() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "lte", _toDateString(0, "yyyyMMddHHmmssS"),
					_withFormat("yyyyMMddHHmmssS"))));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("double", "lte", 1.0D)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("float", "lte", 1.0F)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("integer", "lte", 1)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("long", "lte", 1L)));
	}

	@Test
	public void testNestedAllConditions() throws Exception {
		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"allConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"allConditions",
							JSONUtil.putAll(
								JSONUtil.put(
									"equals",
									JSONUtil.put(
										"parameterName", "${integer}"
									).put(
										"value", 1
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 1.0D
									).put(
										"parameterName", "${double}"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 2.0F
									).put(
										"parameterName", "${float}"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${long}"
							).put(
								"value", 1
							))))));
		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"allConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"allConditions",
							JSONUtil.putAll(
								JSONUtil.put(
									"equals",
									JSONUtil.put(
										"parameterName", "${integer}"
									).put(
										"value", 1
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 0.0D
									).put(
										"parameterName", "${double}"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 1.0F
									).put(
										"parameterName", "${float}"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${long}"
							).put(
								"value", 1
							))))));
	}

	@Test
	public void testNestedAnyConditions() throws Exception {
		Assert.assertFalse(
			_evaluate(
				JSONUtil.put(
					"anyConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"anyConditions",
							JSONUtil.putAll(
								JSONUtil.put(
									"equals",
									JSONUtil.put(
										"parameterName", "${integer}"
									).put(
										"value", 0
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 2.0D
									).put(
										"parameterName", "${double}"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 0.0F
									).put(
										"parameterName", "${float}"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${long}"
							).put(
								"value", 0L
							))))));
		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"anyConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"anyConditions",
							JSONUtil.putAll(
								JSONUtil.put(
									"equals",
									JSONUtil.put(
										"parameterName", "${integer}"
									).put(
										"value", 0
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 1.0D
									).put(
										"parameterName", "${double}"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 1.0F
									).put(
										"parameterName", "${float}"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "${long}"
							).put(
								"value", 0
							))))));
	}

	@Test
	public void testNotContains() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "integer_array", _withValue(4)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "long_array", _withValue(4L)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "string_array", _withValue("four")))));
	}

	@Test
	public void testNotEquals() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "boolean", _withValue(false)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "date", _withValue(_toDateString(-1)),
						_withDateFormat()))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "double", _withValue(2.0D)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "float", _withValue(2.0F)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "integer", _withValue(2)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "long", _withValue(2L)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "string", _withValue("two")))));
	}

	@Test
	public void testNotIn() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "double", _withValues(2.0D, 3.0D)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "float", _withValues(2.0F, 3.0F)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "integer", _withValues(2, 3)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "long", _withValues(2L, 3L)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "string", _withValues("two", "three")))));
	}

	@Test
	public void testNotRange() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getRangeJSONObject(
						"date", "gte", _toDateString(1), "lt", _toDateString(2),
						_withDateFormat()))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getRangeJSONObject("double", "gte", 2.0D, "lt", 3.0D))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getRangeJSONObject("float", "gte", 2.0F, "lt", 3.0F))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getRangeJSONObject("integer", "gte", 2, "lt", 3))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getRangeJSONObject("long", "gte", 2L, "lt", 3L))));
	}

	@Test
	public void testRange() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gte", _toDateString(-1), "lt", _toDateString(1),
					_withDateFormat())));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("double", "gte", 0.0D, "lt", 2.0D)));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("float", "gte", 0.0F, "lt", 2.0F)));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("integer", "gte", 0, "lt", 2)));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("long", "gte", 0L, "lt", 2L)));
	}

	private boolean _evaluate() {
		SXPConditionEvaluator sxpConditionEvaluator = new SXPConditionEvaluator(
			_sxpParameterData);

		return sxpConditionEvaluator.evaluate(_condition);
	}

	private boolean _evaluate(JSONObject jsonObject) {
		_condition = Condition.unsafeToDTO(jsonObject.toString());

		return _evaluate();
	}

	private JSONObject _getConditionJSONObject(
		String key, String parameterName, Consumer<JSONObject>... consumers) {

		JSONObject jsonObject = JSONUtil.put(
			"parameterName", _toTemplateVariable(parameterName));

		for (Consumer<JSONObject> consumer : consumers) {
			consumer.accept(jsonObject);
		}

		return JSONUtil.put(key, jsonObject);
	}

	private JSONObject _getNotJSONObject(JSONObject jsonObject) {
		return JSONUtil.put("not", jsonObject);
	}

	private JSONObject _getRangeJSONObject(
		String parameterName, String operator, Object value,
		Consumer<JSONObject>... consumers) {

		return _getConditionJSONObject(
			"range", parameterName,
			ArrayUtil.append(consumers, _withAttribute(operator, value)));
	}

	private JSONObject _getRangeJSONObject(
		String parameterName, String operator1, Object value1, String operator2,
		Object value2, Consumer<JSONObject>... consumers) {

		return _getConditionJSONObject(
			"range", parameterName,
			ArrayUtil.append(
				consumers,
				new Consumer[] {
					_withAttribute(operator1, value1),
					_withAttribute(operator2, value2)
				}));
	}

	private void _setSXPParameters(SXPParameter... sxpParameters) {
		_sxpParameterData = new SXPParameterData(
			null, SetUtil.fromArray(sxpParameters));
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

	private String _toTemplateVariable(String name) {
		return StringPool.DOLLAR_AND_OPEN_CURLY_BRACE + name +
			StringPool.CLOSE_CURLY_BRACE;
	}

	private Consumer<JSONObject> _withAttribute(String operator, Object value) {
		return jsonObject -> jsonObject.put(operator, value);
	}

	private Consumer<JSONObject> _withDateFormat() {
		return _withFormat("yyyyMMdd");
	}

	private Consumer<JSONObject> _withFormat(String pattern) {
		return jsonObject -> jsonObject.put("format", pattern);
	}

	private Consumer<JSONObject> _withValue(Object value) {
		return _withAttribute("value", value);
	}

	private Consumer<JSONObject> _withValues(Object... values) {
		return _withAttribute("values", JSONUtil.putAll(values));
	}

	private static final Date _date = new Date();

	private Condition _condition = new Condition();
	private SXPParameterData _sxpParameterData = new SXPParameterData(
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