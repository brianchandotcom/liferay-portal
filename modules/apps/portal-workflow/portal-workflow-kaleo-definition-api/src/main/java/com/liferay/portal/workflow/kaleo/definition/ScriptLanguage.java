/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public interface ScriptLanguage {

	public static final ScriptLanguage DRL = new ScriptingLanguageImpl("drl");

	public static final ScriptLanguage GROOVY = new ScriptingLanguageImpl(
		"groovy");

	public static final ScriptLanguage JAVA = new ScriptingLanguageImpl("java");

	public static final Pattern functionPattern = Pattern.compile(
		"^function#[a-z][a-zA-Z0-9]*(-[a-zA-Z0-9]+)*$");

	public static ScriptLanguage parse(String value)
		throws KaleoDefinitionValidationException {

		if (Objects.equals(DRL.getValue(), value)) {
			return DRL;
		}
		else if (Objects.equals(GROOVY.getValue(), value)) {
			return GROOVY;
		}
		else if (Objects.equals(JAVA.getValue(), value)) {
			return JAVA;
		}

		Matcher matcher = functionPattern.matcher(value);

		if (matcher.matches()) {
			return new ScriptingLanguageImpl(value);
		}

		throw new KaleoDefinitionValidationException.InvalidScriptLanguage(
			value);
	}

	public String getValue();

	public static final class ScriptingLanguageImpl implements ScriptLanguage {

		public ScriptingLanguageImpl(String value) {
			_value = value;
		}

		@Override
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private final String _value;

	}

}