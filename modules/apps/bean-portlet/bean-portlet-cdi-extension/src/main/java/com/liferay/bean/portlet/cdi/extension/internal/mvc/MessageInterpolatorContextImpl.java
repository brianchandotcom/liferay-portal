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

import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * @author  Neil Griffin
 */
public class MessageInterpolatorContextImpl
	implements MessageInterpolator.Context {

	public MessageInterpolatorContextImpl(
		ConstraintViolation<?> constraintViolation) {

		_constraintViolation = constraintViolation;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return _constraintViolation.getConstraintDescriptor();
	}

	@Override
	public Object getValidatedValue() {
		return _constraintViolation.getInvalidValue();
	}

	@Override
	public <T> T unwrap(Class<T> aClass) {
		throw new UnsupportedOperationException();
	}

	private final ConstraintViolation<?> _constraintViolation;

}