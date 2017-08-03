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

import com.liferay.vulcan.filter.QueryParamFilterType;

/**
 * Represents the errors that can occur while using Vulcan. Each error is a
 * nested error subclass.
 *
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 */
public class VulcanDeveloperError extends Error {

	/**
	 * Represents the error the developer should throw when a converter is
	 * missing.
	 */
	public static class MustHaveConverter extends VulcanDeveloperError {

		public MustHaveConverter(Class<?> modelClass) {
			super(
				"Model class " + modelClass.getName() +
					" does not have a converter");
		}

	}

	/**
	 * Represents the error the developer should throw when a filter provider is
	 * missing.
	 */
	public static class MustHaveFilterProvider extends VulcanDeveloperError {

		public MustHaveFilterProvider(
			QueryParamFilterType queryParamFilterType) {

			super(
				"Filter " + queryParamFilterType.toString() +
					" does not have a provider");
		}

		public <Q extends QueryParamFilterType> MustHaveFilterProvider(
			Class<Q> modelClass) {

			super(
				"Filter " + modelClass.getName() + " does not have a provider");
		}

	}

	/**
	 * Represents the error the developer should throw when a message mapper is
	 * missing.
	 */
	public static class MustHaveMessageMapper extends VulcanDeveloperError {

		public MustHaveMessageMapper(String mediaType, Class<?> modelClass) {
			super(
				"Media type " + mediaType + " and model class " +
					modelClass.getName() + " does not have a message mapper");
		}

	}

	/**
	 * Represents the error the developer should throw when a provider is
	 * missing.
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
	 */
	public static class MustHaveValidGenericType extends VulcanDeveloperError {

		public MustHaveValidGenericType(Class clazz) {
			super(
				"Class " + clazz.getName() + " must have a valid generic type");
		}

	}

	/**
	 * Represents the error the developer should throw when a provider is
	 * missing.
	 */
	public static class MustUseFilteredCollectionPage
		extends VulcanDeveloperError {

		public MustUseFilteredCollectionPage() {
			super(
				"QueryParamFilterType classes should not be used in " +
					"RoutesBuilder#collectionPage methods, use " +
						"RoutesBuilder#filteredCollectionPage instead");
		}

	}

	/**
	 * Represents the error the developer should throw when an URI cannot be
	 * resolved.
	 */
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