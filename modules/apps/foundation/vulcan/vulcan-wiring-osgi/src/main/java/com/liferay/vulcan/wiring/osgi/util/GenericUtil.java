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
 */
public class GenericUtil {

	/**
	 * Given a type denoted by {@code T<S>} returns S class or an exception, if
	 * the class couldn't be get.
	 *
	 * @param  clazz class of the actual instance.
	 * @param  interfaceClass class of type T.
	 * @return class of type S, or an exception, if the class couldn't be get.
	 */
	public static <T, S> Try<Class<S>> getGenericClassTry(
		Class<?> clazz, Class<T> interfaceClass) {

		Type[] genericInterfaces = clazz.getGenericInterfaces();

		Try<Class<S>> classTry = Try.fail(
			new IllegalArgumentException(
				"Class " + clazz + " does not implement any interfaces."));

		for (Type genericInterface : genericInterfaces) {
			classTry = classTry.recoverWith(
				throwable -> getGenericClassTry(
					genericInterface, interfaceClass));
		}

		return classTry.recoverWith(
			throwable -> getGenericClassTry(
				clazz.getSuperclass(), interfaceClass));
	}

	/**
	 * Given a type denoted by {@code T<S>} returns S class or an exception, if
	 * the class couldn't be get.
	 *
	 * @param  type type of the actual instance.
	 * @param  clazz class of type T.
	 * @return class of type S, or an exception, if the class couldn't be get.
	 */
	public static <T, S> Try<Class<S>> getGenericClassTry(
		Type type, Class<T> clazz) {

		Try<Type> typeTry = Try.success(type);

		return typeTry.filter(
			ParameterizedType.class::isInstance
		).map(
			ParameterizedType.class::cast
		).filter(
			parameterizedType ->
				parameterizedType.getRawType().equals(clazz)
		).map(
			ParameterizedType::getActualTypeArguments
		).filter(
			typeArguments -> typeArguments.length == 1
		).map(
			typeArguments -> typeArguments[0]
		).map(
			typeArgument -> {
				if (typeArgument instanceof ParameterizedType) {
					return ((ParameterizedType)typeArgument).getRawType();
				}
				else {
					return typeArgument;
				}
			}
		).map(
			typeArgument -> (Class<S>)typeArgument
		);
	}

}