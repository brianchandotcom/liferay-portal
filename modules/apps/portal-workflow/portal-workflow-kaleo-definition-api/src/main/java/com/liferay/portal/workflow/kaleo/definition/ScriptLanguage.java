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

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public interface ScriptLanguage {

	public static final ScriptLanguage BEANSHELL = new ScriptingLanguageImpl(
		"beanshell");

	public static final ScriptLanguage DRL = new ScriptingLanguageImpl("drl");

	public static final ScriptLanguage GROOVY = new ScriptingLanguageImpl(
		"groovy");

	public static final ScriptLanguage JAVA = new ScriptingLanguageImpl("java");

	public static final ScriptLanguage JAVASCRIPT = new ScriptingLanguageImpl(
		"javascript");

	public static final ScriptLanguage PYTHON = new ScriptingLanguageImpl(
		"python");

	public static final ScriptLanguage RUBY = new ScriptingLanguageImpl("ruby");

	public static final Pattern functionPattern = Pattern.compile(
		"^function#[a-z][a-zA-Z0-9]*(-[a-zA-Z0-9]+)*$");

	public static ScriptLanguage parse(String value)
		throws KaleoDefinitionValidationException {

		if (Objects.equals(BEANSHELL.getValue(), value)) {
			return BEANSHELL;
		}
		else if (Objects.equals(DRL.getValue(), value)) {
			return DRL;
		}
		else if (Objects.equals(GROOVY.getValue(), value)) {
			return GROOVY;
		}
		else if (Objects.equals(JAVA.getValue(), value)) {
			return JAVA;
		}
		else if (Objects.equals(JAVASCRIPT.getValue(), value)) {
			return JAVASCRIPT;
		}
		else if (Objects.equals(PYTHON.getValue(), value)) {
			return PYTHON;
		}
		else if (Objects.equals(RUBY.getValue(), value)) {
			return RUBY;
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