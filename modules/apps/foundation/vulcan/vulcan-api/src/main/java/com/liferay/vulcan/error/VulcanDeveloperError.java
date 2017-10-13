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

package com.liferay.vulcan.error;

/**
 * Represents the errors that can occur while using Vulcan. Each error is a
 * nested error subclass.
 *
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 * @review
 */
public class VulcanDeveloperError extends Error {

	/**
	 * Represents the error the developer should throw when an exception
	 * converter is missing.
	 *
	 * @review
	 */
	public static class MustHaveExceptionConverter
		extends VulcanDeveloperError {

		public MustHaveExceptionConverter(Class<?> exceptionClass) {
			super(
				"Exception class " + exceptionClass.getName() +
					" does not have a converter");
		}

	}

	/**
	 * Represents the error the developer should throw when a message mapper is
	 * missing.
	 *
	 * @review
	 */
	public static class MustHaveMessageMapper extends VulcanDeveloperError {

		public MustHaveMessageMapper(String mediaType, Class<?> modelClass) {
			super(
				"Media type " + mediaType + " and model class " +
					modelClass.getName() + " does not have a message mapper");
		}

	}

	/**
	 * Represents the error the developer should throw when an identifier-path
	 * mapper is missing.
	 *
	 * @review
	 */
	public static class MustHavePathIdentifierMapper
		extends VulcanDeveloperError {

		public MustHavePathIdentifierMapper(Class<?> identifier) {
			super("Identifier " + identifier + " does not have a path mapper");
		}

	}

	/**
	 * Represents the error the developer should throw when a problem JSON error
	 * message mapper is missing.
	 *
	 * @review
	 */
	public static class MustHaveProblemJSONErrorMessageMapper
		extends VulcanDeveloperError {

		public MustHaveProblemJSONErrorMessageMapper() {
			super(
				"Media type application/problem+json does not have a message " +
					"mapper");
		}

	}

	/**
	 * Represents the error the developer should throw when a provider is
	 * missing.
	 *
	 * @review
	 */
	public static class MustHaveProvider extends VulcanDeveloperError {

		public MustHaveProvider(Class<?> modelClass) {
			super(
				"Model class " + modelClass.getName() +
					" does not have a provider");
		}

	}

	/**
	 * Represents the error the developer should throw when a generic container
	 * has an invalid generic type.
	 *
	 * @review
	 */
	public static class MustHaveValidGenericType extends VulcanDeveloperError {

		public MustHaveValidGenericType(Class clazz) {
			super(
				"Class " + clazz.getName() + " must have a valid generic type");
		}

	}

	/**
	 * Represents the error the developer should throw when the identifier used
	 * for a related collection is not the same as the one required by the
	 * collection.
	 *
	 * @review
	 */
	public static class MustUseSameIdentifier extends VulcanDeveloperError {

		public MustUseSameIdentifier(
			Class<?> identifierClass, Class<?> collectionIdentifierClass) {

			super(
				"Identifier " + identifierClass + " must be " +
					collectionIdentifierClass);
		}

	}

	/**
	 * Represents the error the developer should throw when an URI cannot be
	 * resolved.
	 *
	 * @review
	 */
	public static class UnresolvableURI extends VulcanDeveloperError {

		public UnresolvableURI(Class<?> modelClass) {
			super(
				"Unable to resolve URI for model class " +
					modelClass.getName());
		}

		public UnresolvableURI(String className) {
			super("Unable to resolve URI for model class " + className);
		}

	}

	private VulcanDeveloperError(String message) {
		super(message);
	}

}