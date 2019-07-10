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

package com.liferay.portal.vulcan.internal.jaxrs.validation;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Method;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;

/**
 * @author Javier Gamarra
 */
public class ValidationUtil {

	public static void validate(Object value) {
		Validator validator = ValidatorFactory.getValidator();

		Set<ConstraintViolation<Object>> constraintViolations =
			validator.validate(value);

		if (!constraintViolations.isEmpty()) {
			_throwValidationException(constraintViolations);
		}
	}

	public static void validateArguments(
		Object resource, Method method, Object[] arguments) {

		Validator validator = ValidatorFactory.getValidator();

		ExecutableValidator executableValidator = validator.forExecutables();

		Set<ConstraintViolation<Object>> constraintViolations =
			executableValidator.validateParameters(resource, method, arguments);

		if (!constraintViolations.isEmpty()) {
			_throwValidationException(constraintViolations);
		}
	}

	private static void _throwValidationException(
		Set<ConstraintViolation<Object>> constraintViolations) {

		StringBundler sb = new StringBundler(constraintViolations.size() * 4);

		for (ConstraintViolation<Object> constraintViolation :
				constraintViolations) {

			sb.append(constraintViolation.getPropertyPath());
			sb.append(StringPool.SPACE);
			sb.append(constraintViolation.getMessage());
			sb.append(StringPool.NEW_LINE);
		}

		throw new ValidationException(sb.toString());
	}

}