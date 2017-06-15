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
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 */
public class VulcanDeveloperError extends Error {

	public static class MustHaveMessageMapper extends VulcanDeveloperError {

		public MustHaveMessageMapper(String mediaType, Class<?> modelClass) {
			super(
				"Media type " + mediaType + " and model class " +
					modelClass.getName() + " does not have a message mapper");
		}

	}

	public static class MustHaveProvider extends VulcanDeveloperError {

		public MustHaveProvider(Class<?> modelClass) {
			super(
				"Model class " + modelClass.getName() +
					" does not have a provider");
		}

	}

	public static class MustHaveValidGenericType extends VulcanDeveloperError {

		public MustHaveValidGenericType(Class clazz) {
			super(
				"Class " + clazz.getName() + " must have a valid generic type");
		}

	}

	public static class UnresolvableURI extends VulcanDeveloperError {

		public UnresolvableURI(Class<?> modelClass) {
			super(
				"Unable to resolve URI for model class " +
					modelClass.getName());
		}

	}

	private VulcanDeveloperError(String message) {
		super(message);
	}

}