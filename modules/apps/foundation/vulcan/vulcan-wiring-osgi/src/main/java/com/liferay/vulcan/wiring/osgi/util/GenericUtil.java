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

package com.liferay.vulcan.wiring.osgi.util;

import com.liferay.vulcan.result.Try;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Provides methods for skipping problems related to the Java generic system.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
public class GenericUtil {

	/**
	 * Returns the {@code Class} of the {@code TypeArgument} located in the
	 * first position of a {@code Class}.
	 *
	 * @param  clazz the class from which we want to extract the {@code
	 *         TypeArgument}.
	 * @return the {@code Class} of the {@code TypeArgument} located in the
	 *         first position of a {@code Class}.
	 * @review
	 */
	public static <S> Try<Class<S>> getFirstGenericTypeArgumentTry(
		Class<?> clazz) {

		return getGenericTypeArgumentTry(clazz, 0);
	}

	/**
	 * Returns the {@code Class} of the {@code TypeArgument} located in the
	 * first position of a {@code Type}.
	 *
	 * @param  type the type from which we want to extract the {@code
	 *         TypeArgument}.
	 * @return the {@code Class} of the {@code TypeArgument} located in the
	 *         first position of a {@code Type}.
	 * @review
	 */
	public static <S> Try<Class<S>> getFirstGenericTypeArgumentTry(Type type) {
		return getGenericTypeArgumentTry(type, 0);
	}

	/**
	 * Returns the {@code Class} of the {@code TypeArgument} located in the nth
	 * position of a {@code Class}.
	 *
	 * @param  clazz the class from which we want to extract the {@code
	 *         TypeArgument}.
	 * @param  position the {@code TypeArgument} position that we want to
	 *         obtain.
	 * @return the {@code Class} of the {@code TypeArgument} located in the nth
	 *         position of a {@code Class}.
	 * @review
	 */
	public static <S> Try<Class<S>> getGenericTypeArgumentTry(
		Class<?> clazz, int position) {

		Type[] genericInterfaces = clazz.getGenericInterfaces();

		Try<Class<S>> classTry = Try.fail(
			new IllegalArgumentException(
				"Class " + clazz + " does not implement any interfaces"));

		for (Type genericInterface : genericInterfaces) {
			classTry = classTry.recoverWith(
				throwable -> getGenericTypeArgumentTry(
					genericInterface, position));
		}

		return classTry.recoverWith(
			throwable -> getGenericTypeArgumentTry(
				clazz.getSuperclass(), position));
	}

	/**
	 * Returns the {@code Class} of the {@code TypeArgument} located in the nth
	 * position of a {@code Type}.
	 *
	 * @param  type the type from which we want to extract the {@code
	 *         TypeArgument}.
	 * @param  position the {@code TypeArgument} position that we want to
	 *         obtain.
	 * @return the {@code Class} of the {@code TypeArgument} located in the nth
	 *         position of a {@code Type}.
	 * @review
	 */
	public static <S> Try<Class<S>> getGenericTypeArgumentTry(
		Type type, int position) {

		Try<Type> typeTry = Try.success(type);

		return typeTry.filter(
			ParameterizedType.class::isInstance
		).map(
			ParameterizedType.class::cast
		).map(
			ParameterizedType::getActualTypeArguments
		).filter(
			typeArguments -> {
				if (typeArguments.length >= 1) {
					return true;
				}

				return false;
			}
		).map(
			typeArguments -> typeArguments[position]
		).map(
			typeArgument -> {
				if (typeArgument instanceof ParameterizedType) {
					return ((ParameterizedType)typeArgument).getRawType();
				}

				return typeArgument;
			}
		).map(
			typeArgument -> (Class<S>)typeArgument
		);
	}

}