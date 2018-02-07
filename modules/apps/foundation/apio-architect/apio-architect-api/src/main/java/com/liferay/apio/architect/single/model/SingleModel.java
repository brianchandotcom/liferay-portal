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

package com.liferay.apio.architect.single.model;

/**
 * Provides a wrapper for a model.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 */
public class SingleModel<T> {

	public SingleModel(T model, String resourceName) {
		_model = model;
		_resourceName = resourceName;
	}

	/**
	 * Returns the model.
	 *
	 * @return the model
	 */
	public T getModel() {
		return _model;
	}

	/**
	 * Returns the resource's name.
	 *
	 * @return the resource name
	 * @review
	 */
	public String getResourceName() {
		return _resourceName;
	}

	private final T _model;
	private final String _resourceName;

}