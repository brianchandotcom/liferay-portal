/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.script;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.script.ScriptFieldBuilder;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class ScriptFieldImpl implements ScriptField {

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Script getScript() {
		return _script;
	}

	@Override
	public boolean isIgnoreFailure() {
		return _ignoreFailure;
	}

	public static final class ScriptFieldBuilderImpl
		implements ScriptFieldBuilder {

		@Override
		public ScriptField build() {
			return new ScriptFieldImpl(_scriptFieldImpl);
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

		private final ScriptFieldImpl _scriptFieldImpl = new ScriptFieldImpl();

	}

	protected ScriptFieldImpl() {
		_ignoreFailure = true;
	}

	protected ScriptFieldImpl(ScriptFieldImpl scriptFieldImpl) {
		_field = scriptFieldImpl._field;
		_ignoreFailure = scriptFieldImpl._ignoreFailure;
		_script = scriptFieldImpl._script;
	}

	private String _field;
	private boolean _ignoreFailure;
	private Script _script;

}