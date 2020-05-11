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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import javax.mvc.binding.ValidationError;

import javax.validation.ConstraintViolation;

/**
 * @author  Neil Griffin
 */
public class ValidationErrorImpl
	extends ParamErrorImpl implements ValidationError {

	public ValidationErrorImpl(
		ConstraintViolation<?> constraintViolation, String message,
		String paramName) {

		super(message, paramName);

		_constraintViolation = constraintViolation;
	}

	@Override
	public ConstraintViolation<?> getViolation() {
		return _constraintViolation;
	}

	private final ConstraintViolation<?> _constraintViolation;

}