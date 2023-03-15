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

package com.liferay.osb.faro.engine.client.model;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class Distribution {

	public long getCount() {
		return _count;
	}

	public List<Object> getValues() {
		return _values;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setValues(List<Object> values) {
		_values = values;
	}

	private long _count;
	private List<Object> _values;

}