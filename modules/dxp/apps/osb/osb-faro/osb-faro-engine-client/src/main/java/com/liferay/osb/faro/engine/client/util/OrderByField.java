/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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