/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.script;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class ScriptFieldBuilder {

	public ScriptField build() {
		return new ScriptField(_field, _ignoreFailure, _script);
	}

	public ScriptFieldBuilder field(String field) {
		_field = field;

		return this;
	}

	public ScriptFieldBuilder ignoreFailure(boolean ignoreFailure) {
		_ignoreFailure = ignoreFailure;

		return this;
	}

	public ScriptFieldBuilder script(Script script) {
		_script = script;

		return this;
	}

	private String _field;
	private boolean _ignoreFailure;
	private Script _script;

}