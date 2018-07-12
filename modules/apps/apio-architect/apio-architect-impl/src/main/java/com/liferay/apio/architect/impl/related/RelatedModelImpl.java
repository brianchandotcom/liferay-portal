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

package com.liferay.apio.architect.impl.related;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedModel;

import java.util.function.Function;

/**
 * Represents the relation between two resources.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @param  <T> the model's type
 * @param  <S> the model identifier's type (e.g., {@code Long}, {@code String},
 *         etc.)
 */
public class RelatedModelImpl<T, S> implements RelatedModel<T, S> {

	public RelatedModelImpl(
		String key, Class<? extends Identifier<S>> identifierClass,
		Function<T, S> identifierFunction) {

		_key = key;
		_identifierClass = identifierClass;
		_identifierFunction = identifierFunction;
	}

	@Override
	public Class<? extends Identifier<S>> getIdentifierClass() {
		return _identifierClass;
	}

	@Override
	public Function<T, S> getIdentifierFunction() {
		return _identifierFunction;
	}

	@Override
	public String getKey() {
		return _key;
	}

	private final Class<? extends Identifier<S>> _identifierClass;
	private final Function<T, S> _identifierFunction;
	private final String _key;

}