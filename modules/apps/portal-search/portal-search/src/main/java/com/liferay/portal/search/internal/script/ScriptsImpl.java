/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.script;

import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.script.ScriptFieldBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = Scripts.class)
public class ScriptsImpl implements Scripts {

	@Override
	public ScriptBuilder builder() {
		return new ScriptImpl.ScriptBuilderImpl();
	}

	@Override
	public ScriptFieldBuilder fieldBuilder() {
		return new ScriptFieldImpl.ScriptFieldBuilderImpl();
	}

	@Override
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

	@Override
	public Script script(String idOrCode) {
		return builder(
		).idOrCode(
			idOrCode
		).build();
	}

	@Override
	public ScriptField scriptField(String field, Script script) {
		return fieldBuilder(
		).field(
			field
		).script(
			script
		).build();
	}

	@Override
	public Script stored(String scriptId) {
		return builder(
		).idOrCode(
			scriptId
		).scriptType(
			ScriptType.STORED
		).build();
	}

}