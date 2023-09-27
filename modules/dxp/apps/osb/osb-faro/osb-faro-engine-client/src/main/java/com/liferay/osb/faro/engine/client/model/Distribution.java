/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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