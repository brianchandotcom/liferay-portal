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

	public static final class ScriptFieldBuilderImpl
		implements ScriptFieldBuilder {

		@Override
		public ScriptField build() {
			return new ScriptField(_scriptFieldImpl);
		}

		@Override
		public ScriptFieldBuilder field(String field) {
			_scriptFieldImpl._field = field;

			return this;
		}

		@Override
		public ScriptFieldBuilder ignoreFailure(boolean ignoreFailure) {
			_scriptFieldImpl._ignoreFailure = ignoreFailure;

			return this;
		}

		@Override
		public ScriptFieldBuilder script(Script script) {
			_scriptFieldImpl._script = script;

			return this;
		}

		private final ScriptField _scriptFieldImpl = new ScriptField();

	}

	protected ScriptField() {
		_ignoreFailure = true;
	}

	protected ScriptField(ScriptField scriptField) {
		_field = scriptField._field;
		_ignoreFailure = scriptField._ignoreFailure;
		_script = scriptField._script;
	}

	private String _field;
	private boolean _ignoreFailure;
	private Script _script;

}