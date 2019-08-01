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

package com.liferay.portal.search.elasticsearch6.internal.script;

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