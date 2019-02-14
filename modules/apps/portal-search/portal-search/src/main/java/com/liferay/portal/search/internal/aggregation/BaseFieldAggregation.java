/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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