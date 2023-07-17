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

package com.liferay.object.validation.rule;

/**
 * @author Carolina Barbosa
 */
public class ObjectValidationRuleResult {

	public ObjectValidationRuleResult(String errorMessage) {
		this(errorMessage, null);
	}

	public ObjectValidationRuleResult(
		String errorMessage, String objectFieldName) {

		_errorMessage = errorMessage;
		_objectFieldName = objectFieldName;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getObjectFieldName() {
		return _objectFieldName;
	}

	private String _errorMessage;
	private String _objectFieldName;

}