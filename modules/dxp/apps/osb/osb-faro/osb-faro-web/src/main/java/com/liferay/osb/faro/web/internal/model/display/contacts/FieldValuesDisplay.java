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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class FieldValuesDisplay {

	public FieldValuesDisplay() {
	}

	public FieldValuesDisplay(String name, List<String> values) {
		_name = name;
		_values = values;
	}

	public String getName() {
		return _name;
	}

	public List<String> getValues() {
		return _values;
	}

	private String _name;
	private List<String> _values;

}