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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public class Script {

	public Script(String scriptId) {
		_language = null;
		_idOrCode = scriptId;
		_scriptType = ScriptType.STORED;
		_parameters = null;
	}

	public Script(String language, String code) {
		_language = language;
		_idOrCode = code;
		_scriptType = ScriptType.INLINE;
		_parameters = new HashMap<>();
	}

	public void clearOption(String optionName) {
		_options.remove(optionName);
	}

	public void clearOptions() {
		_options.clear();
	}

	public void clearParameter(String paramName) {
		_parameters.remove(paramName);
	}

	public void clearParameters() {
		_parameters.clear();
	}

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

	public void putOption(String optionName, String optionValue) {
		_options.put(optionName, optionValue);
	}

	public void putParameter(String paramName, Object paramValue) {
		_parameters.put(paramName, paramValue);
	}

	public enum ScriptType {

		INLINE, STORED

	}

	private final String _idOrCode;
	private final String _language;
	private final Map<String, String> _options = new HashMap<>();
	private final Map<String, Object> _parameters;
	private final ScriptType _scriptType;

}