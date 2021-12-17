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

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.search.experiences.rest.dto.v1_0.Condition;
import com.liferay.search.experiences.rest.dto.v1_0.Contains;
import com.liferay.search.experiences.rest.dto.v1_0.Equals;
import com.liferay.search.experiences.rest.dto.v1_0.In;
import com.liferay.search.experiences.rest.dto.v1_0.Range;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Petteri Karttunen
 */
public class ConditionUtil {

	public static Condition unpack(Condition condition) {
		_unpack(condition);

		return condition;
	}

	private static void _unpack(Condition condition) {
		if (condition == null) {
			return;
		}

		Condition[] allConditions = condition.getAllConditions();

		if (allConditions != null) {
			Arrays.setAll(
				allConditions, i -> UnpackUtil.unpack(allConditions[i]));
		}

		Condition[] anyConditions = condition.getAnyConditions();

		if (anyConditions != null) {
			Arrays.setAll(
				anyConditions, i -> UnpackUtil.unpack(anyConditions[i]));
		}

		_unpackContains(condition.getContains());

		_unpackEquals(condition.getEquals());

		_unpackIn(condition.getIn());

		_unpackRange(condition.getRange());

		_unpack(condition.getNot());
	}

	private static void _unpackContains(Contains contains) {
		if (contains == null) {
			return;
		}

		_unpackValue(contains::getValue, contains::setValue);
		_unpackValues(contains::getValues, contains::setValues);
	}

	private static void _unpackEquals(Equals equals) {
		if (equals == null) {
			return;
		}

		_unpackValue(equals::getValue, equals::setValue);
	}

	private static void _unpackIn(In in) {
		if (in == null) {
			return;
		}

		_unpackValues(in::getValues, in::setValues);
	}

	private static void _unpackRange(Range range) {
		if (range == null) {
			return;
		}

		_unpackValue(range::getValue, range::setValue);
		_unpackValues(range::getValues, range::setValues);
	}

	private static void _unpackValue(
		Supplier<Object> supplier, Consumer<Object> consumer) {

		Object value = supplier.get();

		if (value != null) {
			consumer.accept(UnpackUtil.unpack(value));
		}
	}

	private static void _unpackValues(
		Supplier<Object[]> supplier, Consumer<Object[]> consumer) {

		Object[] values = supplier.get();

		if (values != null) {
			Arrays.setAll(values, i -> UnpackUtil.unpack(values[i]));
			consumer.accept(values);
		}
	}

}
