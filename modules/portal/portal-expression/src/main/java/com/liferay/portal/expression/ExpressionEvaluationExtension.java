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

package com.liferay.portal.expression;

import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Marcellus Tavares
 */
public class ExpressionEvaluationExtension {

	public static boolean between(Double value, Integer min, Integer max) {
		if ((value >= min) && (value <= max)) {
			return true;
		}

		return false;
	}

	public static boolean between(Integer value, Integer min, Integer max) {
		if ((value >= min) && (value <= max)) {
			return true;
		}

		return false;
	}

	public static boolean between(Long value, Long min, Long max) {
		if ((value >= min) && (value <= max)) {
			return true;
		}

		return false;
	}

	public static boolean isEmailAddress(String emailAddress) {
		return Validator.isEmailAddress(emailAddress);
	}

	public static boolean isNumber(String number) {
		return Validator.isNumber(number);
	}

	public static boolean isPassword(String password) {
		return Validator.isPassword(password);
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		return Validator.isPhoneNumber(phoneNumber);
	}

	public static boolean isURL(String url) {
		return Validator.isUrl(url);
	}

	public static boolean notBetween(Double value, Integer min, Integer max) {
		return !between(value, min, max);
	}

	public static boolean notBetween(Integer value, Integer min, Integer max) {
		return !between(value, min, max);
	}

	public static boolean notBetween(Long value, Long min, Long max) {
		return !between(value, min, max);
	}

	public static double sum(Double... values) {
		return MathUtil.sum(values);
	}

	public static int sum(Integer... values) {
		return MathUtil.sum(values);
	}

	public static long sum(Long... values) {
		return MathUtil.sum(values);
	}

}