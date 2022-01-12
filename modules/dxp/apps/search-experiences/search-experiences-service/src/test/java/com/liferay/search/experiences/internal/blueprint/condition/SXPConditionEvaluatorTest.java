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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.FloatSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.rest.dto.v1_0.Condition;
import com.liferay.search.experiences.rest.dto.v1_0.Exists;

import java.time.ZonedDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
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
							"exists", JSONUtil.put("parameterName", "integer")),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "integer"
							).put(
								"value", 1
							))))));
		Assert.assertTrue(
			_evaluate(
				JSONUtil.put(
					"allConditions",
					JSONUtil.putAll(
						JSONUtil.put(
							"exists", JSONUtil.put("parameterName", "integer")),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "integer"
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
								"parameterName", "integer"
							).put(
								"value", 1
							)),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "integer"
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
								"parameterName", "integer"
							).put(
								"value", 1
							)),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "integer"
							).put(
								"value", 2
							))))));
	}

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "integer_array", _consumeValue(1))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "long_array", _consumeValue(1L))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "string", _consumeValue("ne"))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "string", _consumeValues("two", "one"))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"contains", "string_array", _consumeValue("one"))));
	}

	@Test
	public void testDateEquals() throws Exception {
		TimeZone timeZone = TimeZone.getDefault();

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2021, 12, 25, 6, 33, 59, 768, timeZone.toZoneId());

		_setSXPParameters(
			new DateSXPParameter(
				"date", true, Date.from(zonedDateTime.toInstant())));

		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "date", _consumeValue("20211225"),
					_consumeFormat("yyyyMMdd"))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "date", _consumeValue("063359"),
					_consumeFormat("HHmmss"))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "date", _consumeValue("20211231"),
						_consumeFormat("yyyyMMdd")))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "date", _consumeValue("110011"),
						_consumeFormat("HHmmss")))));
	}

	@Test
	public void testDateRange() throws Exception {
		TimeZone timeZone = TimeZone.getDefault();

		ZonedDateTime zonedDateTime = ZonedDateTime.of(
			2021, 12, 25, 6, 33, 59, 768, timeZone.toZoneId());

		_setSXPParameters(
			new DateSXPParameter(
				"date", true, Date.from(zonedDateTime.toInstant())));

		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gt", "2021-12-24", "lt", "2021-12-26",
					_consumeFormat("yyyy-MM-dd"))));
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gte", "2021-12-25", "lte", "2021-12-26",
					_consumeFormat("yyyy-MM-dd"))));
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gte", "2021-12-24", "lte", "2021-12-25",
					_consumeFormat("yyyy-MM-dd"))));
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gt", "06.01.02", "lt", "07.11.12",
					_consumeFormat("HH.mm.ss"))));
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gte", "06.33.59", "lte", "07.11.12",
					_consumeFormat("HH.mm.ss"))));
		Assert.assertTrue(
			_evaluate(
				_getRangeJSONObject(
					"date", "gte", "06.01.02", "lte", "06.33.59",
					_consumeFormat("HH.mm.ss"))));
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
					"equals", "boolean", _consumeValue(true))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "double", _consumeValue(1.0D))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "float", _consumeValue(1.0F))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "integer", _consumeValue(1))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("equals", "long", _consumeValue(1L))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"equals", "string", _consumeValue("one"))));
	}

	@Test
	public void testExists() throws Exception {
		Exists exists = new Exists();

		_condition.setExists(exists);

		String parameterName = RandomTestUtil.randomString();

		exists.setParameterName(parameterName);

		Assert.assertFalse(_evaluate());

		_setSXPParameters(new StringSXPParameter(parameterName, true, null));

		Assert.assertTrue(_evaluate());
	}

	@Test
	public void testGreaterThan() throws Exception {
		Assert.assertTrue(_evaluate(_getRangeJSONObject("double", "gt", 0.0D)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("float", "gt", 0.0F)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("integer", "gt", 0)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("long", "gt", 0L)));
	}

	@Test
	public void testGreaterThanEquals() throws Exception {
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
					"in", "double", _consumeValues(1.0D, 2.0D))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"in", "float", _consumeValues(1.0F, 2.0F))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"in", "integer", _consumeValues(1, 2))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject("in", "long", _consumeValues(1L, 2L))));
		Assert.assertTrue(
			_evaluate(
				_getConditionJSONObject(
					"in", "string", _consumeValues("one", "two"))));
	}

	@Test
	public void testLessThan() throws Exception {
		Assert.assertTrue(_evaluate(_getRangeJSONObject("double", "lt", 2.0D)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("float", "lt", 2.0F)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("integer", "lt", 2)));
		Assert.assertTrue(_evaluate(_getRangeJSONObject("long", "lt", 2L)));
	}

	@Test
	public void testLessThanEquals() throws Exception {
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
										"parameterName", "integer"
									).put(
										"value", 1
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 1.0D
									).put(
										"parameterName", "double"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 2.0F
									).put(
										"parameterName", "float"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "long"
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
										"parameterName", "integer"
									).put(
										"value", 1
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 0.0D
									).put(
										"parameterName", "double"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 1.0F
									).put(
										"parameterName", "float"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "long"
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
										"parameterName", "integer"
									).put(
										"value", 0
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 2.0D
									).put(
										"parameterName", "double"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 0.0F
									).put(
										"parameterName", "float"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "long"
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
										"parameterName", "integer"
									).put(
										"value", 0
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"gt", 1.0D
									).put(
										"parameterName", "double"
									)),
								JSONUtil.put(
									"range",
									JSONUtil.put(
										"lte", 1.0F
									).put(
										"parameterName", "float"
									)))),
						JSONUtil.put(
							"equals",
							JSONUtil.put(
								"parameterName", "long"
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
						"contains", "integer_array", _consumeValue(4)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "long_array", _consumeValue(4L)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "string", _consumeValue("no")))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "string",
						_consumeValues("two", "three")))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"contains", "string_array", _consumeValue("four")))));
	}

	@Test
	public void testNotEquals() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "boolean", _consumeValue(false)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "double", _consumeValue(2.0D)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "float", _consumeValue(2.0F)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "integer", _consumeValue(2)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "long", _consumeValue(2L)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"equals", "string", _consumeValue("two")))));
	}

	@Test
	public void testNotIn() throws Exception {
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "double", _consumeValues(2.0D, 3.0D)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "float", _consumeValues(2.0F, 3.0F)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "integer", _consumeValues(2, 3)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "long", _consumeValues(2L, 3L)))));
		Assert.assertTrue(
			_evaluate(
				_getNotJSONObject(
					_getConditionJSONObject(
						"in", "string", _consumeValues("two", "three")))));
	}

	@Test
	public void testNotRange() throws Exception {
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
			_evaluate(_getRangeJSONObject("double", "gte", 0.0D, "lt", 2.0D)));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("float", "gte", 0.0F, "lt", 2.0F)));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("integer", "gte", 0, "lt", 2)));
		Assert.assertTrue(
			_evaluate(_getRangeJSONObject("long", "gte", 0L, "lt", 2L)));
	}

	private Consumer<JSONObject> _consume(String key, Object value) {
		return jsonObject -> jsonObject.put(key, value);
	}

	private Consumer<JSONObject> _consumeFormat(String format) {
		return _consume("format", format);
	}

	private Consumer<JSONObject> _consumeValue(Object value) {
		return _consume("value", value);
	}

	private Consumer<JSONObject> _consumeValues(Object... values) {
		return _consume("value", JSONUtil.putAll(values));
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

		JSONObject jsonObject = JSONUtil.put("parameterName", parameterName);

		for (Consumer<JSONObject> consumer : consumers) {
			consumer.accept(jsonObject);
		}

		return JSONUtil.put(key, jsonObject);
	}

	private JSONObject _getNotJSONObject(JSONObject jsonObject) {
		return JSONUtil.put("not", jsonObject);
	}

	private JSONObject _getRangeJSONObject(
		String parameterName, String key, Object value,
		Consumer<JSONObject>... consumers) {

		return _getConditionJSONObject(
			"range", parameterName,
			ArrayUtil.append(consumers, _consume(key, value)));
	}

	private JSONObject _getRangeJSONObject(
		String parameterName, String key1, Object value1, String key2,
		Object value2, Consumer<JSONObject>... consumers) {

		return _getConditionJSONObject(
			"range", parameterName,
			ArrayUtil.append(
				consumers,
				new Consumer[] {
					_consume(key1, value1), _consume(key2, value2)
				}));
	}

	private void _setSXPParameters(SXPParameter... sxpParameters) {
		_sxpParameterData = new SXPParameterData(null, _toMap(sxpParameters));
	}

	private Map<String, SXPParameter> _toMap(SXPParameter... sxpParameters) {
		Map<String, SXPParameter> map = new HashMap<>();

		for (SXPParameter sxpParameter : sxpParameters) {
			map.put(sxpParameter.getName(), sxpParameter);
		}

		return map;
	}

	private Condition _condition = new Condition();
	private SXPParameterData _sxpParameterData = new SXPParameterData(
		"test",
		_toMap(
			new BooleanSXPParameter("boolean", true, true),
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
			new StringSXPParameter("string", true, "one")));

}