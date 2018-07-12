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

package com.liferay.apio.architect.impl.single.model;

import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;

/**
 * Provides a wrapper for a model.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
public class SingleModelImpl<T> implements SingleModel<T> {

	public SingleModelImpl(
		T model, String resourceName, List<Operation> operations) {

		_model = model;
		_resourceName = resourceName;
		_operations = operations;
	}

	@Override
	public T getModel() {
		return _model;
	}

	@Override
	public List<Operation> getOperations() {
		return _operations;
	}

	@Override
	public String getResourceName() {
		return _resourceName;
	}

	private final T _model;
	private final List<Operation> _operations;
	private final String _resourceName;

}