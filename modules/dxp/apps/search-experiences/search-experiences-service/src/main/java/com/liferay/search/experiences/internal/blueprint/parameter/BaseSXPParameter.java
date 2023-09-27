/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.petra.string.StringBundler;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;

import java.util.Map;
import java.util.Objects;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseSXPParameter implements SXPParameter {

	public BaseSXPParameter(String name, boolean templateVariable) {
		this.name = name;
		this.templateVariable = templateVariable;
	}

	@Override
	public boolean equals(Object object) {
		SXPParameter sxpParameter = (SXPParameter)object;

		return Objects.equals(name, sxpParameter.getName());
	}

	@Override
	public boolean evaluateContains(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateEquals(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateEquals(String format, Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateIn(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateRange(Object gt, Object gte, Object lt, Object lte) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateRange(
		String format, Object gt, Object gte, Object lt, Object lte) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String evaluateToString(Map<String, String> options) {
		return String.valueOf(getValue());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTemplateVariable() {
		if (isTemplateVariable()) {
			return "${" + name + "}";
		}

		return null;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean isTemplateVariable() {
		return templateVariable;
	}

	@Override
	public String toString() {
		Class<?> clazz = getClass();

		return StringBundler.concat(
			"{className=", clazz.getSimpleName(), ", name=", name,
			", templateVariable=", templateVariable, ", value=",
			evaluateToString(null), "}");
	}

	protected final String name;
	protected final boolean templateVariable;

}