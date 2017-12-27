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

package com.liferay.apio.architect.related;

import java.util.Optional;
import java.util.function.Function;

/**
 * Represents the relation between two models.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @param  <T> the model's type
 * @param  <S> the related model's type
 * @review
 */
public class RelatedModel<T, S> {

	public RelatedModel(
		String key, Class<S> modelClass,
		Function<T, Optional<S>> modelFunction) {

		_key = key;
		_modelClass = modelClass;
		_modelFunction = modelFunction;
	}

	/**
	 * Returns the relation's key.
	 *
	 * @return the relation's key
	 */
	public String getKey() {
		return _key;
	}

	/**
	 * Returns the related model's class.
	 *
	 * @return the related model's class
	 */
	public Class<S> getModelClass() {
		return _modelClass;
	}

	/**
	 * Returns the function you can use to retrieve the related model. This
	 * method needs a valid instance of the model.
	 *
	 * @return the function that calculates the related model
	 */
	public Function<T, Optional<S>> getModelFunction() {
		return _modelFunction;
	}

	private final String _key;
	private final Class<S> _modelClass;
	private final Function<T, Optional<S>> _modelFunction;

}