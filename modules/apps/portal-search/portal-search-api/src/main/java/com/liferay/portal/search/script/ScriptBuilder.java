/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.script;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class ScriptBuilder {

	public Script build() {
		return new Script(
			_idOrCode, _language, _scriptType, _options, _parameters);
	}

	public ScriptBuilder idOrCode(String idOrCode) {
		_idOrCode = idOrCode;

		return this;
	}

	public ScriptBuilder language(String language) {
		_language = language;

		return this;
	}

	public ScriptBuilder options(Map<String, String> optionsMap) {
		_options.clear();

		_options.putAll(optionsMap);

		return this;
	}

	public ScriptBuilder parameters(Map<String, Object> parametersMap) {
		_parameters.clear();

		_parameters.putAll(parametersMap);

		return this;
	}

	public ScriptBuilder putOption(String optionName, String optionValue) {
		_options.put(optionName, optionValue);

		return this;
	}

	public ScriptBuilder putParameter(
		String parameterName, Object parameterValue) {

		_parameters.put(parameterName, parameterValue);

		return this;
	}

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