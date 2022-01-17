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