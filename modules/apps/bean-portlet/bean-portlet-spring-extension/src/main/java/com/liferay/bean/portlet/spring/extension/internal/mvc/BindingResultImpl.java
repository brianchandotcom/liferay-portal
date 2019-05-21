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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.ManagedBean;

import javax.mvc.binding.BindingError;
import javax.mvc.binding.ParamError;
import javax.mvc.binding.ValidationError;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author  Neil Griffin
 */
@ManagedBean
@Scope(proxyMode = ScopedProxyMode.INTERFACES, value = "portletRedirect")
public class BindingResultImpl implements MutableBindingResult, Serializable {

	@Override
	public void addBindingError(BindingError bindingError) {
		_bindingErrors.add(bindingError);
	}

	@Override
	public void addValidationError(ValidationError validationError) {
		_validationErrors.add(validationError);
	}

	@Override
	public Set<ParamError> getAllErrors() {
		_consulted = true;

		Set<ParamError> allErrors = new LinkedHashSet<>();

		allErrors.addAll(_bindingErrors);
		allErrors.addAll(_validationErrors);

		return allErrors;
	}

	@Override
	public List<String> getAllMessages() {
		_consulted = true;

		List<String> allMessages = new ArrayList<>();

		for (BindingError bindingError : _bindingErrors) {
			allMessages.add(bindingError.getMessage());
		}

		for (ValidationError validationError : _validationErrors) {
			allMessages.add(validationError.getMessage());
		}

		return allMessages;
	}

	@Override
	public Set<ParamError> getErrors(String paramName) {
		_consulted = true;

		Set<ParamError> errors = new LinkedHashSet<>();

		for (BindingError bindingError : _bindingErrors) {
			if (Objects.equals(bindingError.getParamName(), paramName)) {
				errors.add(bindingError);
			}
		}

		for (ValidationError validationError : _validationErrors) {
			if (Objects.equals(validationError.getParamName(), paramName)) {
				errors.add(validationError);
			}
		}

		return errors;
	}

	@Override
	public boolean isConsulted() {
		return _consulted;
	}

	@Override
	public boolean isFailed() {
		_consulted = true;

		if (!_bindingErrors.isEmpty() || !_validationErrors.isEmpty()) {
			return true;
		}

		return false;
	}

	private static final long serialVersionUID = 2217732469752342741L;

	private final Set<BindingError> _bindingErrors = new LinkedHashSet<>();
	private boolean _consulted;
	private final Set<ValidationError> _validationErrors =
		new LinkedHashSet<>();

}