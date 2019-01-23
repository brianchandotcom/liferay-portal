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

package com.liferay.portal.search.script;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public class ScriptField {

	public ScriptField(String field, Script script) {
		_field = field;
		_script = script;
	}

	public String getField() {
		return _field;
	}

	public Script getScript() {
		return _script;
	}

	public boolean isIgnoreFailure() {
		return _ignoreFailure;
	}

	public void setIgnoreFailure(boolean ignoreFailure) {
		_ignoreFailure = ignoreFailure;
	}

	private final String _field;
	private boolean _ignoreFailure = true;
	private final Script _script;

}