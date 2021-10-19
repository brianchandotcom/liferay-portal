/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.blueprint.parameter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;

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
	public boolean evaluateContains(Object value, Object[] values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateEquals(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateEquals(Object object, String format) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateIn(Object[] values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateRange(Object gt, Object gte, Object lt, Object lte) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean evaluateRange(
		Object gt, Object gte, Object lt, Object lte, String format) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String evaluateToString(Map<String, String> options) {
		return GetterUtil.getString(getValue());
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