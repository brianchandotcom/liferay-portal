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

package com.liferay.portlet.dynamicdatamapping.storage.query;

/**
 * @author Michael C. Han
 */
public enum ComparisonOperator {

	EQUALS("="), EXCLUDES(" EXCLUDES "), GREATER_THAN(">"),
	GREATER_THAN_OR_EQUAL_TO(">="), IN(" IN "), INCLUDES(" INCLUDES "),
	JOIN(" = "), LESS_THAN("<"), LESS_THAN_OR_EQUAL_TO("<="), LIKE(" LIKE "),
	NOT_EQUALS("!="), NOT_IN(" NOT IN ");

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ComparisonOperator(String value) {
		_value = value;
	}

	private final String _value;

}