/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.script;

import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class ScriptTranslator {

	public co.elastic.clients.elasticsearch._types.Script translate(
		Script script) {

		ScriptType scriptType = script.getScriptType();

		if (scriptType == null) {
			return co.elastic.clients.elasticsearch._types.Script.of(
				elasticsearchScript -> elasticsearchScript.source(
					script.getIdOrCode()));
		}

		if (scriptType == ScriptType.INLINE) {
			return co.elastic.clients.elasticsearch._types.Script.of(
				elasticsearchScript -> elasticsearchScript.lang(
					script.getLanguage()
				).options(
					script.getOptions()
				).params(
					_translateParams(script.getParameters())
				).source(
					script.getIdOrCode()
				));
		}

		if (scriptType == ScriptType.STORED) {
			return co.elastic.clients.elasticsearch._types.Script.of(
				elasticsearchScript -> elasticsearchScript.id(
					script.getIdOrCode()
				).params(
					_translateParams(script.getParameters())
				));
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