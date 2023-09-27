/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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