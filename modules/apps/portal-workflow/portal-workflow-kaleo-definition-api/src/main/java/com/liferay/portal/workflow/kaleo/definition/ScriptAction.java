/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

/**
 * @author Rafael Praxedes
 */
public class ScriptAction extends Action {

	public ScriptAction(
			String name, String description, String executionType,
			String script, String scriptLanguage, String scriptRequiredContexts,
			int priority)
		throws KaleoDefinitionValidationException {

		super(ActionType.SCRIPT, name, description, executionType, priority);

		_script = script;
		_scriptLanguage = ScriptLanguage.parse(scriptLanguage);
		_scriptRequiredContexts = scriptRequiredContexts;
	}

	public String getScript() {
		return _script;
	}

	public ScriptLanguage getScriptLanguage() {
		return _scriptLanguage;
	}

	public String getScriptRequiredContexts() {
		return _scriptRequiredContexts;
	}

	private final String _script;
	private final ScriptLanguage _scriptLanguage;
	private final String _scriptRequiredContexts;

}