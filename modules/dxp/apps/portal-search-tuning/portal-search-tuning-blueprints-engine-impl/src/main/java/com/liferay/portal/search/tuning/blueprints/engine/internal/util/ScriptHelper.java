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

package com.liferay.portal.search.tuning.blueprints.engine.internal.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.ScriptConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ScriptHelper.class)
public class ScriptHelper {

	public Optional<Script> getScript(
		JSONObject scriptJSONObject, Messages messages) {

		if (scriptJSONObject.length() == 0) {
			return Optional.empty();
		}

		ScriptBuilder scriptBuilder = _scripts.builder();

		_setIdOrSource(scriptBuilder, scriptJSONObject, messages);

		_setLang(scriptBuilder, scriptJSONObject);

		_setOptions(scriptBuilder, scriptJSONObject);

		_setParams(scriptBuilder, scriptJSONObject);

		return Optional.of(scriptBuilder.build());
	}

	private Optional<Script> _returnIdOrSourceMissing(
		JSONObject scriptJSONObject, Messages messages) {

		messages.addMessage(
			new Message.Builder().className(
				getClass().getName()
			).localizationKey(
				"core.error.aggregation-script-id-or-source-missing"
			).msg(
				"Aggregation script id or source has to be set"
			).rootObject(
				scriptJSONObject
			).severity(
				Severity.ERROR
			).build());

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Aggregation script id or source has to be set [ " +
					scriptJSONObject + "]");
		}

		return Optional.empty();
	}

	private void _setIdOrSource(
		ScriptBuilder scriptBuilder, JSONObject scriptJSONObject,
		Messages messages) {

		if (!scriptJSONObject.isNull(ScriptConfigurationKeys.ID.getJsonKey())) {
			scriptBuilder.idOrCode(
				scriptJSONObject.getString(
					ScriptConfigurationKeys.ID.getJsonKey())
			).scriptType(
				ScriptType.STORED
			);
		}
		else if (!scriptJSONObject.isNull(
					ScriptConfigurationKeys.SOURCE.getJsonKey())) {

			scriptBuilder.idOrCode(
				scriptJSONObject.getString(
					ScriptConfigurationKeys.SOURCE.getJsonKey())
			).scriptType(
				ScriptType.INLINE
			);
		}
		else {
			_returnIdOrSourceMissing(scriptJSONObject, messages);
		}
	}

	private void _setLang(
		ScriptBuilder scriptBuilder, JSONObject scriptJSONObject) {

		if (!scriptJSONObject.has(ScriptConfigurationKeys.LANG.getJsonKey())) {
			return;
		}

		scriptBuilder.language(
			scriptJSONObject.getString(
				ScriptConfigurationKeys.LANG.getJsonKey()));
	}

	private void _setOptions(
		ScriptBuilder scriptBuilder, JSONObject scriptJSONObject) {

		if (!scriptJSONObject.has(
				ScriptConfigurationKeys.OPTIONS.getJsonKey())) {

			return;
		}

		JSONObject optionsJSONObject = scriptJSONObject.getJSONObject(
			ScriptConfigurationKeys.OPTIONS.getJsonKey());

		optionsJSONObject.keySet(
		).stream(
		).forEach(
			key -> scriptBuilder.putParameter(key, optionsJSONObject.get(key))
		);
	}

	private void _setParams(
		ScriptBuilder scriptBuilder, JSONObject scriptJSONObject) {

		if (!scriptJSONObject.has(
				ScriptConfigurationKeys.PARAMS.getJsonKey())) {

			return;
		}

		JSONObject paramsJSONObject = scriptJSONObject.getJSONObject(
			ScriptConfigurationKeys.PARAMS.getJsonKey());

		paramsJSONObject.keySet(
		).stream(
		).forEach(
			key -> scriptBuilder.putParameter(key, paramsJSONObject.get(key))
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(ScriptHelper.class);

	@Reference
	private Scripts _scripts;

}