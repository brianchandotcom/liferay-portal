/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel;

import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Sergio Gonzalez
 * @author Roberto Díaz
 */
public enum RatingsType {

	LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
	THUMBS("thumbs");

	public static boolean isValid(String value) {
		if (Validator.isNull(value)) {
			return false;
		}

		if (value.equals(LIKE.getValue()) ||
			value.equals(STACKED_STARS.getValue()) ||
			value.equals(STARS.getValue()) || value.equals(THUMBS.getValue())) {

			return true;
		}

		return false;
	}

	public static RatingsType parse(String value) {
		if (Objects.equals(LIKE.getValue(), value)) {
			return LIKE;
		}
		else if (Objects.equals(STACKED_STARS.getValue(), value)) {
			return STACKED_STARS;
		}
		else if (Objects.equals(STARS.getValue(), value)) {
			return STARS;
		}
		else if (Objects.equals(THUMBS.getValue(), value)) {
			return THUMBS;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private RatingsType(String value) {
		_value = value;
	}

	private final String _value;

}