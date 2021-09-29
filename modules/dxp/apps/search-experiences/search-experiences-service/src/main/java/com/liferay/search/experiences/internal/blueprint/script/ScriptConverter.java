/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.script;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;

/**
 * @author Petteri Karttunen
 */
public class ScriptConverter {

	public ScriptConverter(Scripts scripts) {
		_scripts = scripts;
	}

	public Scripts getScripts() {
		return _scripts;
	}

	public Script toScript(Object object) {
		if (object instanceof JSONObject) {
			return _toScript((JSONObject)object);
		}
		else if (object instanceof String) {
			return _toScript((String)object);
		}

		throw new IllegalArgumentException();
	}

	private void _processScriptOptions(
		JSONObject jsonObject, ScriptBuilder scriptBuilder) {

		if (jsonObject != null) {
			for (String key : jsonObject.keySet()) {
				scriptBuilder.putOption(key, jsonObject.getString(key));
			}
		}
	}

	private void _processScriptParams(
		JSONObject jsonObject, ScriptBuilder scriptBuilder) {

		if (jsonObject != null) {
			for (String key : jsonObject.keySet()) {
				scriptBuilder.putParameter(key, jsonObject.get(key));
			}
		}
	}

	private Script _toScript(JSONObject jsonObject) {
		ScriptBuilder scriptBuilder = _scripts.builder();

		if (jsonObject.has("id")) {
			scriptBuilder.idOrCode(
				jsonObject.getString("id")
			).scriptType(
				ScriptType.STORED
			);
		}
		else if (jsonObject.has("source")) {
			scriptBuilder.idOrCode(
				jsonObject.getString("source")
			).scriptType(
				ScriptType.INLINE
			);
		}

		if (jsonObject.has("lang")) {
			scriptBuilder.language(jsonObject.getString("lang"));
		}

		_processScriptOptions(
			jsonObject.getJSONObject("options"), scriptBuilder);
		_processScriptParams(jsonObject.getJSONObject("params"), scriptBuilder);

		return scriptBuilder.build();
	}

	private Script _toScript(String string) {
		return _scripts.builder(
		).idOrCode(
			string
		).language(
			"painless"
		).scriptType(
			ScriptType.INLINE
		).build();
	}

	private final Scripts _scripts;

}