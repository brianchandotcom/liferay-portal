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