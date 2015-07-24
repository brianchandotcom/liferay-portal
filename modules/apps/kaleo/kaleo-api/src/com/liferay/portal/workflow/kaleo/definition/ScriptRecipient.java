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

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Val Nagy
 */
public class ScriptRecipient extends Recipient {

	public ScriptRecipient(
		String script, String scriptLanguage, String scriptContexts) {

		super(RecipientType.SCRIPT);

		_script = script;
		_scriptLanguage = ScriptLanguage.parse(scriptLanguage);
		_scriptContexts = scriptContexts;
	}

	public String getScript() {
		return _script;
	}

	public String getScriptContexts() {
		return _scriptContexts;
	}

	public ScriptLanguage getScriptLanguage() {
		return _scriptLanguage;
	}

	public void setScript(String script) {
		_script = script;
	}

	public void setScriptLanguage(ScriptLanguage scriptLanguage) {
		_scriptLanguage = scriptLanguage;
	}

	private String _script;
	private final String _scriptContexts;
	private ScriptLanguage _scriptLanguage;

}