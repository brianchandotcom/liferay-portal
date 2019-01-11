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

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum ScriptLanguage {

	BEANSHELL("beanshell"), DRL("drl"), GROOVY("groovy"), JAVA("java"),
	JAVASCRIPT("javascript"), PYTHON("python"), RUBY("ruby");

	public static ScriptLanguage parse(String value) {
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

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ScriptLanguage(String value) {
		_value = value;
	}

	private final String _value;

}