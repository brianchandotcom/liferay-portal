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

package com.liferay.search.experiences.internal.blueprint.query;

import java.util.Arrays;

/**
 * @author André de Oliveira
 */
public class UnresolvedTemplateVariableException extends RuntimeException {

	public static UnresolvedTemplateVariableException with(
		String... variables) {

		UnresolvedTemplateVariableException
			unresolvedTemplateVariableException =
				new UnresolvedTemplateVariableException(
					"Unresolved template variables: " +
						Arrays.asList(variables));

		unresolvedTemplateVariableException._variables = variables;

		return unresolvedTemplateVariableException;
	}

	public String[] getVariables() {
		return _variables;
	}

	/**
	 * @param string
	 */
	private UnresolvedTemplateVariableException(String message) {
		super(message);
	}

	private String[] _variables;

}