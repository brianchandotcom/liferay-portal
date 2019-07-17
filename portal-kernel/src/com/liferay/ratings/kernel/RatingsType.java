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