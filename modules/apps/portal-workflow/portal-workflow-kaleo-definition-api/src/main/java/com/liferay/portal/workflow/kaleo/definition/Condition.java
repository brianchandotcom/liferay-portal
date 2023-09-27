/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

/**
 * @author Michael C. Han
 */
public class Condition extends Node {

	public Condition(
			String name, String description, String script,
			String scriptLanguage, String scriptRequiredContexts)
		throws KaleoDefinitionValidationException {

		super(NodeType.CONDITION, name, description);

		_description = description;
		_script = script;
		_scriptLanguage = ScriptLanguage.parse(scriptLanguage);
		_scriptRequiredContexts = scriptRequiredContexts;
	}

	@Override
	public String getDescription() {
		return _description;
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

	private final String _description;
	private final String _script;
	private final ScriptLanguage _scriptLanguage;
	private final String _scriptRequiredContexts;

}