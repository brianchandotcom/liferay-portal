/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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