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

package com.liferay.apio.architect.operation;

import com.liferay.apio.architect.form.Form;

import java.util.Optional;

/**
 * Represents a resource's operation.
 *
 * @author Alejandro Hernández
 */
public class Operation {

	public Operation(Form form, Method method, String name) {
		this(form, method, name, false);
	}

	public Operation(
		Form form, Method method, String name, boolean collection) {

		_form = form;

		this.method = method;
		this.name = name;
		this.collection = collection;
	}

	public Operation(Method method, String name) {
		this(null, method, name, false);
	}

	public Operation(Method method, String name, boolean collection) {
		this(null, method, name, collection);
	}

	/**
	 * Returns this operation's expected form, if present; returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @return the operation's expected form, if present; {@code
	 *         Optional#empty()} otherwise
	 */
	public Optional<Form> getFormOptional() {
		return Optional.ofNullable(_form);
	}

	public final boolean collection;

	/**
	 * The operation's method.
	 */
	public final Method method;

	/**
	 * The operation's name.
	 */
	public final String name;

	private final Form _form;

}