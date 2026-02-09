/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.script;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class Script {

	public String getIdOrCode() {
		return _idOrCode;
	}

	public String getLanguage() {
		return _language;
	}

	public Map<String, String> getOptions() {
		return Collections.unmodifiableMap(_options);
	}

	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(_parameters);
	}

	public ScriptType getScriptType() {
		return _scriptType;
	}

	public static final class ScriptBuilderImpl implements ScriptBuilder {

		@Override
		public Script build() {
			return new Script(
				_idOrCode, _language, _scriptType, _options, _parameters);
		}

		@Override
		public ScriptBuilder idOrCode(String idOrCode) {
			_idOrCode = idOrCode;

			return this;
		}

		@Override
		public ScriptBuilder language(String language) {
			_language = language;

			return this;
		}

		@Override
		public ScriptBuilder options(Map<String, String> optionsMap) {
			_options.clear();

			_options.putAll(optionsMap);

			return this;
		}

		@Override
		public ScriptBuilder parameters(Map<String, Object> parametersMap) {
			_parameters.clear();

			_parameters.putAll(parametersMap);

			return this;
		}

		@Override
		public ScriptBuilder putOption(String optionName, String optionValue) {
			_options.put(optionName, optionValue);

			return this;
		}

		@Override
		public ScriptBuilder putParameter(
			String parameterName, Object parameterValue) {

			_parameters.put(parameterName, parameterValue);

			return this;
		}

		@Override
		public ScriptBuilder scriptType(ScriptType scriptType) {
			_scriptType = scriptType;

			return this;
		}

		private String _idOrCode;
		private String _language;
		private final Map<String, String> _options = new LinkedHashMap<>();
		private final Map<String, Object> _parameters = new LinkedHashMap<>();
		private ScriptType _scriptType;

	}

	protected Script(
		String idOrCode, String language, ScriptType scriptType,
		Map<String, String> options, Map<String, Object> parameters) {

		_idOrCode = idOrCode;
		_language = language;
		_scriptType = scriptType;
		_options = options;
		_parameters = parameters;
	}

	private final String _idOrCode;
	private final String _language;
	private final Map<String, String> _options;
	private final Map<String, Object> _parameters;
	private final ScriptType _scriptType;

}