/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.search;

import com.liferay.osb.faro.engine.client.util.OrderByField;

import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FaroSearchContext {

	public int getCur() {
		return _cur;
	}

	public int getDelta() {
		return _delta;
	}

	public List<OrderByField> getOrderByFields() {
		return _orderByFields;
	}

	public Object getParameter(String key) {
		return _parameters.get(key);
	}

	public Map<String, Object> getParameters() {
		return _parameters;
	}

	public String getQuery() {
		return _query;
	}

	public int getType() {
		return _type;
	}

	private int _cur;
	private int _delta;
	private List<OrderByField> _orderByFields;
	private Map<String, Object> _parameters;
	private String _query;
	private int _type;

}