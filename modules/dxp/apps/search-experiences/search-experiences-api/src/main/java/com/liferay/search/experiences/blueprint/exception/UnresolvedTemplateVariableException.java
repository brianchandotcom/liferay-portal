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