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

package com.liferay.segments.asah.connector.internal.client.util;

/**
 * @author Shinn Lok
 * @author David Arques
 */
public class OrderByField {

	public static OrderByField asc(String fieldName) {
		return new OrderByField(fieldName, "asc", true);
	}

	public static OrderByField asc(String fieldName, boolean system) {
		return new OrderByField(fieldName, "asc", system);
	}

	public static OrderByField desc(String fieldName) {
		return new OrderByField(fieldName, "desc", true);
	}

	public static OrderByField desc(String fieldName, boolean system) {
		return new OrderByField(fieldName, "desc", system);
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getOrderBy() {
		return _orderBy;
	}

	public boolean isSystem() {
		return _system;
	}

	private OrderByField(String fieldName, String orderBy, boolean system) {
		_fieldName = fieldName;
		_orderBy = orderBy;
		_system = system;
	}

	private final String _fieldName;
	private final String _orderBy;
	private final boolean _system;

}