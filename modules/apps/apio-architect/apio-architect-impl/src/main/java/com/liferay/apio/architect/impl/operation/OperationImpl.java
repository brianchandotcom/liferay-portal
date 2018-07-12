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

package com.liferay.apio.architect.impl.operation;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;

import java.util.Optional;

/**
 * @author Alejandro Hernández
 */
public class OperationImpl implements Operation {

	public OperationImpl(Form form, HTTPMethod httpMethod, String name) {
		this(form, httpMethod, name, false);
	}

	public OperationImpl(
		Form form, HTTPMethod httpMethod, String name, boolean collection) {

		_form = form;
		_httpMethod = httpMethod;
		_name = name;
		_collection = collection;
	}

	public OperationImpl(HTTPMethod httpMethod, String name) {
		this(null, httpMethod, name, false);
	}

	public OperationImpl(
		HTTPMethod httpMethod, String name, boolean collection) {

		this(null, httpMethod, name, collection);
	}

	@Override
	public Optional<Form> getFormOptional() {
		return Optional.ofNullable(_form);
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return _httpMethod;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isCollection() {
		return _collection;
	}

	private final boolean _collection;
	private final Form _form;
	private final HTTPMethod _httpMethod;
	private final String _name;

}