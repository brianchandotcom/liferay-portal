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

package com.liferay.search.experiences.internal.attributes.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.attributes.SXPAttributes;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPAttributeValueHelper.class)
public class SXPAttributeValueHelper {

	public Optional<Boolean> getBooleanOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return valueOptional.map(GetterUtil::getBoolean);
	}

	public Optional<Double> getDoubleOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return valueOptional.map(GetterUtil::getDouble);
	}

	public Optional<Float> getFloatOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return valueOptional.map(GetterUtil::getFloat);
	}

	public Optional<Integer[]> getIntegerArrayOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		Object value = valueOptional.get();

		if (value instanceof Integer[]) {
			Integer[] arr = (Integer[])value;

			if (arr.length > 0) {
				return Optional.of(arr);
			}
		}

		if (value instanceof int[]) {
			int[] arr = (int[])value;

			if (arr.length > 0) {
				IntStream intStream = Arrays.stream(arr);

				Integer[] boxedArray = intStream.boxed(
				).toArray(
					Integer[]::new
				);

				return Optional.of(boxedArray);
			}
		}

		return Optional.empty();
	}

	public Optional<Integer> getIntegerOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return valueOptional.map(GetterUtil::getInteger);
	}

	public Optional<Long[]> getLongArrayOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		Object value = valueOptional.get();

		if (value instanceof Long[]) {
			Long[] arr = (Long[])value;

			if (arr.length > 0) {
				return Optional.of(arr);
			}
		}

		if (value instanceof long[]) {
			long[] arr = (long[])value;

			if (arr.length > 0) {
				LongStream longStream = Arrays.stream(arr);

				Long[] boxedArray = longStream.boxed(
				).toArray(
					Long[]::new
				);

				return Optional.of(boxedArray);
			}
		}

		return Optional.empty();
	}

	public Optional<Long> getLongOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return valueOptional.map(GetterUtil::getLong);
	}

	public Optional<String[]> getStringArrayOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> valueOptional = sxpAttributes.getAttributeOptional(
			key);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		Object value = valueOptional.get();

		if (value instanceof String[]) {
			String[] arr = (String[])value;

			if (arr.length > 0) {
				return Optional.of(arr);
			}
		}

		return Optional.empty();
	}

	public Optional<String> getStringOptional(
		SXPAttributes sxpAttributes, String key) {

		Optional<Object> optional = sxpAttributes.getAttributeOptional(key);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		String s = StringUtil.trim(String.valueOf(optional.get()));

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

}