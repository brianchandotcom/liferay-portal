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

package com.liferay.osb.faro.engine.client.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

/**
 * @author Shinn Lok
 */
public class OrderByField implements Serializable {

	public OrderByField() {
	}

	public OrderByField(String fieldName, String orderBy) {
		_fieldName = fieldName;
		_orderBy = OrderBy.valueOf(StringUtil.toLowerCase(orderBy));
	}

	public OrderByField(String fieldName, String orderBy, boolean system) {
		_fieldName = fieldName;
		_orderBy = OrderBy.valueOf(StringUtil.toLowerCase(orderBy));
		_system = system;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public OrderBy getOrderBy() {
		return _orderBy;
	}

	public boolean isSystem() {
		return _system;
	}

	public enum OrderBy {

		asc, desc

	}

	private String _fieldName;
	private OrderBy _orderBy;
	private boolean _system;

}