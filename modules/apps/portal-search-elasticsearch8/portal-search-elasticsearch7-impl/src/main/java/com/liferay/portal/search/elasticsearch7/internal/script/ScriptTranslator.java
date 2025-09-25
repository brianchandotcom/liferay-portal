/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.script;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptType;

/**
 * @author Michael C. Han
 */
public class ScriptTranslator {

	public org.elasticsearch.script.Script translate(Script script) {
		ScriptType scriptType = script.getScriptType();

		if (scriptType == null) {
			return new org.elasticsearch.script.Script(script.getIdOrCode());
		}

		if (scriptType == ScriptType.INLINE) {
			return new org.elasticsearch.script.Script(
				org.elasticsearch.script.ScriptType.INLINE,
				script.getLanguage(), script.getIdOrCode(), script.getOptions(),
				script.getParameters());
		}

		if (scriptType == ScriptType.STORED) {
			return new org.elasticsearch.script.Script(
				org.elasticsearch.script.ScriptType.STORED, null,
				script.getIdOrCode(), null, script.getParameters());
		}

		throw new IllegalArgumentException();
	}

}