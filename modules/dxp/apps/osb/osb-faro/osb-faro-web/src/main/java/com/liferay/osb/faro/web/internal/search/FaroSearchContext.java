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