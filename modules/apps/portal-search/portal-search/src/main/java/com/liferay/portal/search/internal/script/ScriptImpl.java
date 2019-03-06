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

package com.liferay.portal.search.internal.script;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class ScriptImpl implements Script {

	@Override
	public String getIdOrCode() {
		return _idOrCode;
	}

	@Override
	public String getLanguage() {
		return _language;
	}

	@Override
	public Map<String, String> getOptions() {
		return Collections.unmodifiableMap(_options);
	}

	@Override
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(_parameters);
	}

	@Override
	public ScriptType getScriptType() {
		return _scriptType;
	}

	public static final class ScriptBuilderImpl implements ScriptBuilder {

		@Override
		public Script build() {
			return new ScriptImpl(_scriptImpl);
		}

		@Override
		public ScriptBuilder idOrCode(String idOrCode) {
			_scriptImpl._idOrCode = idOrCode;

			return this;
		}

		@Override
		public ScriptBuilder language(String language) {
			_scriptImpl._language = language;

			return this;
		}

		@Override
		public ScriptBuilder options(Map<String, String> optionsMap) {
			_scriptImpl._options.clear();

			_scriptImpl._options.putAll(optionsMap);

			return this;
		}

		@Override
		public ScriptBuilder parameters(Map<String, Object> parametersMap) {
			_scriptImpl._parameters.clear();

			_scriptImpl._parameters.putAll(parametersMap);

			return this;
		}

		@Override
		public ScriptBuilder putOption(String optionName, String optionValue) {
			_scriptImpl._options.put(optionName, optionValue);

			return this;
		}

		@Override
		public ScriptBuilder putParameter(
			String parameterName, Object parameterValue) {

			_scriptImpl._parameters.put(parameterName, parameterValue);

			return this;
		}

		@Override
		public ScriptBuilder scriptType(ScriptType scriptType) {
			_scriptImpl._scriptType = scriptType;

			return this;
		}

		private final ScriptImpl _scriptImpl = new ScriptImpl();

	}

	protected ScriptImpl() {
	}

	protected ScriptImpl(ScriptImpl scriptImpl) {
		_idOrCode = scriptImpl._idOrCode;
		_language = scriptImpl._language;
		_scriptType = scriptImpl._scriptType;

		_options.putAll(scriptImpl._options);
		_parameters.putAll(scriptImpl._parameters);
	}

	private String _idOrCode;
	private String _language;
	private final Map<String, String> _options = new LinkedHashMap<>();
	private final Map<String, Object> _parameters = new LinkedHashMap<>();
	private ScriptType _scriptType;

}