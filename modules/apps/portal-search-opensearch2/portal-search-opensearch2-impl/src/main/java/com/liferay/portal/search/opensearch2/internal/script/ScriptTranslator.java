/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.script;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptType;

import java.util.HashMap;
import java.util.Map;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch._types.InlineScript;
import org.opensearch.client.opensearch._types.StoredScriptId;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class ScriptTranslator {

	public org.opensearch.client.opensearch._types.Script translate(
		Script script) {

		ScriptType scriptType = script.getScriptType();

		if (scriptType == null) {
			return org.opensearch.client.opensearch._types.Script.of(
				openSearchScript -> openSearchScript.inline(
					InlineScript.of(
						inlineScript -> inlineScript.source(
							script.getIdOrCode()))));
		}

		if (scriptType == ScriptType.INLINE) {
			return org.opensearch.client.opensearch._types.Script.of(
				openSearchScript -> openSearchScript.inline(
					InlineScript.of(
						inlineScript -> inlineScript.lang(
							script.getLanguage()
						).options(
							script.getOptions()
						).params(
							_translateParams(script.getParameters())
						).source(
							script.getIdOrCode()
						))));
		}

		if (scriptType == ScriptType.STORED) {
			return org.opensearch.client.opensearch._types.Script.of(
				openSearchScript -> openSearchScript.stored(
					StoredScriptId.of(
						storedScriptId -> storedScriptId.id(
							script.getIdOrCode()
						).params(
							_translateParams(script.getParameters())
						))));
		}

		throw new IllegalArgumentException("Invalid script type " + scriptType);
	}

	private Map<String, JsonData> _translateParams(Map<String, Object> params) {
		Map<String, JsonData> jsonDatas = new HashMap<>();

		MapUtil.isNotEmptyForEach(
			params, (key, value) -> jsonDatas.put(key, JsonData.of(value)));

		return jsonDatas;
	}

}