/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.exception;

/**
 * @author André de Oliveira
 */
public class InvalidParameterException extends RuntimeException {

	public static InvalidParameterException with(String name) {
		InvalidParameterException invalidParameterException =
			new InvalidParameterException("Invalid parameter name: " + name);

		invalidParameterException._name = name;

		return invalidParameterException;
	}

	public String getName() {
		return _name;
	}

	private InvalidParameterException(String message) {
		super(message);
	}

	private String _name;

}