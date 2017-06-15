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

package com.liferay.vulcan.representor.builder;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface RepresentorBuilder<T> {

	public FirstStep<T> identifier(Function<T, String> identifierFunction);

	public interface FirstStep<T> {

		public <S> FirstStep<T> addEmbeddedModel(
			String key, Class<S> modelClass,
			Function<T, Optional<S>> modelFunction);

		public FirstStep<T> addField(
			String key, Function<T, Object> fieldFunction);

		public FirstStep<T> addLink(String key, String url);

		public <S> FirstStep<T> addLinkedModel(
			String key, Class<S> modelClass,
			Function<T, Optional<S>> modelFunction);

		public FirstStep<T> addType(String type);

	}

}