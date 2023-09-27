/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.parameter.contributor;

/**
 * @author Petteri Karttunen
 */
public class SXPParameterContributorDefinition {

	public SXPParameterContributorDefinition(
		Class<?> clazz, String languageKey, String name) {

		_languageKey = languageKey;

		_className = clazz.getName();

		_templateVariable = "${" + name + "}";
	}

	public String getClassName() {
		return _className;
	}

	public String getLanguageKey() {
		return _languageKey;
	}

	public String getTemplateVariable() {
		return _templateVariable;
	}

	private final String _className;
	private final String _languageKey;
	private final String _templateVariable;

}