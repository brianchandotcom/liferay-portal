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