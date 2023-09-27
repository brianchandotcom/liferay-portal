/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.exception;

import java.util.Arrays;

/**
 * @author André de Oliveira
 */
public class UnresolvedTemplateVariableException extends RuntimeException {

	public static UnresolvedTemplateVariableException with(
		String... templateVariables) {

		UnresolvedTemplateVariableException
			unresolvedTemplateVariableException =
				new UnresolvedTemplateVariableException(
					"Unresolved template variables: " +
						Arrays.asList(templateVariables));

		unresolvedTemplateVariableException._templateVariables =
			templateVariables;

		return unresolvedTemplateVariableException;
	}

	public String[] getTemplateVariables() {
		return _templateVariables;
	}

	private UnresolvedTemplateVariableException(String message) {
		super(message);
	}

	private String[] _templateVariables;

}