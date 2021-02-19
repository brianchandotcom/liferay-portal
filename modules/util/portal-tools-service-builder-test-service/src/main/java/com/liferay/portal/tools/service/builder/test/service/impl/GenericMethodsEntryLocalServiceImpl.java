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

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.tools.service.builder.test.service.base.GenericMethodsEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/**
 * @author Brian Wing Shun Chan
 */
public class GenericMethodsEntryLocalServiceImpl
	extends GenericMethodsEntryLocalServiceBaseImpl {

	@Override
	public <E extends Exception> void typeParameterAndBoundMethod(
			BiConsumer<String, E> biConsumer)
		throws E {
	}

	@Override
	public <T> void typeParameterMethod(Consumer<T> consumer) throws Exception {
	}

	@Override
	public <T, E extends Exception> List<T> typeParametersAndBoundMethod(
		BiFunction<Long, T, E> biFunction, BiConsumer<Long, E> biConsumer) {

		return null;
	}

	@Override
	public <N extends Number, E extends Exception> List<N>
		typeParametersAndBoundsMethod(
			BiFunction<Long, N, E> biFunction, BiConsumer<Long, N> biConsumer) {

		return null;
	}

	@Override
	public
		<N extends Number & ObjIntConsumer, E extends Exception & Serializable>
			List<N> typeParametersAndMultipleBoundsMethod(
				BiFunction<Long, N, E> biFunction,
				BiConsumer<Long, N> biConsumer) {

		return null;
	}

}