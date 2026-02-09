/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.script;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class ScriptField {

	public String getField() {
		return _field;
	}

	public Script getScript() {
		return _script;
	}

	public boolean isIgnoreFailure() {
		return _ignoreFailure;
	}

	protected ScriptField(String field, boolean ignoreFailure, Script script) {
		_field = field;
		_ignoreFailure = ignoreFailure;
		_script = script;
	}

	private final String _field;
	private final boolean _ignoreFailure;
	private final Script _script;

}