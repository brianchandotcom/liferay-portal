/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation;

import com.liferay.portal.search.aggregation.FieldAggregation;
import com.liferay.portal.search.script.Script;

/**
 * @author Michael C. Han
 */
public abstract class BaseFieldAggregation
	extends BaseAggregation implements FieldAggregation {

	public BaseFieldAggregation(String name, String field) {
		super(name);

		_field = field;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Object getMissing() {
		return _missing;
	}

	@Override
	public Script getScript() {
		return _script;
	}

	@Override
	public void setField(String field) {
		_field = field;
	}

	@Override
	public void setMissing(Object missing) {
		_missing = missing;
	}

	@Override
	public void setScript(Script script) {
		_script = script;
	}

	private String _field;
	private Object _missing;
	private Script _script;

}