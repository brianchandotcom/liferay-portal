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

package com.liferay.search.experiences.internal.blueprint.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;

import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ScriptHelper.class)
public class ScriptHelper {

	public Optional<Script> getScript(Object object) {
		if (Objects.isNull(object)) {
			return Optional.empty();
		}

		if (object instanceof String) {
			return _getStringScript((String)object);
		}
		else if (object instanceof JSONObject) {
			return _getObjectScript((JSONObject)object);
		}

		return Optional.empty();
	}

	private Optional<Script> _getObjectScript(JSONObject jsonObject) {
		if (jsonObject.length() == 0) {
			return Optional.empty();
		}

		ScriptBuilder scriptBuilder = _scripts.builder();

		_setIdOrSource(scriptBuilder, jsonObject);

		_setterHelper.setStringValue(
			jsonObject, "lang", scriptBuilder::language);

		_setOptions(scriptBuilder, jsonObject);

		_setParams(scriptBuilder, jsonObject);

		return Optional.of(scriptBuilder.build());
	}

	private Optional<Script> _getStringScript(String scriptString) {
		if (Validator.isBlank(scriptString)) {
			return Optional.empty();
		}

		ScriptBuilder scriptBuilder = _scripts.builder();

		scriptBuilder.idOrCode(
			scriptString
		).scriptType(
			ScriptType.INLINE
		).language(
			"painless"
		);

		return Optional.of(scriptBuilder.build());
	}

	private void _setIdOrSource(
		ScriptBuilder scriptBuilder, JSONObject jsonObject) {

		String id = jsonObject.getString("id");
		String source = jsonObject.getString("source");

		if (!Validator.isBlank(id)) {
			scriptBuilder.idOrCode(
				id
			).scriptType(
				ScriptType.STORED
			);
		}
		else if (!Validator.isBlank(source)) {
			scriptBuilder.idOrCode(
				source
			).scriptType(
				ScriptType.INLINE
			);
		}
	}

	private void _setOptions(
		ScriptBuilder scriptBuilder, JSONObject jsonObject) {

		JSONObject optionsJSONObject = jsonObject.getJSONObject("options");

		if (optionsJSONObject == null) {
			return;
		}

		optionsJSONObject.keySet(
		).stream(
		).forEach(
			key -> scriptBuilder.putParameter(key, optionsJSONObject.get(key))
		);
	}

	private void _setParams(
		ScriptBuilder scriptBuilder, JSONObject jsonObject) {

		JSONObject paramsJSONObject = jsonObject.getJSONObject("params");

		if (paramsJSONObject == null) {
			return;
		}

		paramsJSONObject.keySet(
		).stream(
		).forEach(
			key -> scriptBuilder.putParameter(key, paramsJSONObject.get(key))
		);
	}

	@Reference
	private Scripts _scripts;

	@Reference
	private SetterHelper _setterHelper;

}