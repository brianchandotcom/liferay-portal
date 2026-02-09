/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.script;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class Scripts {

	public static final Scripts INSTANCE = new Scripts();

	public ScriptBuilder builder() {
		return new ScriptBuilder();
	}

	public ScriptFieldBuilder fieldBuilder() {
		return new ScriptFieldBuilder();
	}

	public Script inline(String language, String code) {
		return builder(
		).language(
			language
		).idOrCode(
			code
		).scriptType(
			ScriptType.INLINE
		).build();
	}

	public Script script(String idOrCode) {
		return builder(
		).idOrCode(
			idOrCode
		).build();
	}

	public ScriptField scriptField(String field, Script script) {
		return fieldBuilder(
		).field(
			field
		).script(
			script
		).build();
	}

	public Script stored(String scriptId) {
		return builder(
		).idOrCode(
			scriptId
		).scriptType(
			ScriptType.STORED
		).build();
	}

	private Scripts() {
	}

}