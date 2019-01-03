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

package com.liferay.change.tracking.configuration;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.function.Function;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface CTConfiguration<T, U> {

	/**
	 * Returns the function from the configuration that retrieves the resource
	 * entity by it's primary key
	 *
	 * @return The function from the configuration that retrieves the resource
	 *         entity by it's primary key
	 */
	public Function<Long, T> getResourceEntityByResourceEntityIdFunction();

	/**
	 * Returns the resource entity's class from the configuration
	 *
	 * @return The resource entity's class from the configuration
	 */
	public Class<T> getResourceEntityClass();

	/**
	 * Returns the function from the configuration that retrieves the resource
	 * entity's primary key from the resource entity
	 *
	 * @return The function from the configuration that retrieves the resource
	 *         entity's primary key from the resource entity
	 */
	public Function<T, Serializable>
		getResourceEntityIdFromResourceEntityFunction();

	/**
	 * Returns the function from the configuration that retrieves the resource
	 * entity's primary key from the version entity
	 *
	 * @return The function from the configuration that retrieves the resource
	 *         entity's primary key from the version entity
	 */
	public Function<U, Serializable>
		getResourceEntityIdFromVersionEntityFunction();

	/**
	 * Returns an array of the allowed workflow statuses for the version entity
	 * from the configuration
	 *
	 * @return An array of the allowed workflow statuses for the version entity
	 *         from the configuration
	 */
	public Integer[] getVersionEntityAllowedStatuses();

	/**
	 * Returns the function from the configuration that retrieves the version
	 * entity by it's primary key
	 *
	 * @return The function from the configuration that retrieves the version
	 *         entity by it's primary key
	 */
	public Function<Long, U> getVersionEntityByVersionEntityIdFunction();

	/**
	 * Returns the version entity's class from the configuration
	 *
	 * @return The version entity's class from the configuration
	 */
	public Class<U> getVersionEntityClass();

	/**
	 * Returns the function from the configuration that retrieves the version
	 * entity's primary key from the resource entity
	 *
	 * @return The function from the configuration that retrieves the version
	 *         entity's primary key from the resource entity
	 */
	public Function<T, Serializable>
		getVersionEntityIdFromResourceEntityFunction();

	/**
	 * Returns the function from the configuration that retrieves the version
	 * entity's primary key from the version entity
	 *
	 * @return The function from the configuration that retrieves the version
	 *         entity's primary key from the version entity
	 */
	public Function<U, Serializable>
		getVersionEntityIdFromVersionEntityFunction();

	/**
	 * Returns the function from the configuration that retrieves the version
	 * entity's workflow status from the version entity
	 *
	 * @return The function from the configuration that retrieves the version
	 *         entity's workflow status from the version entity
	 */
	public Function<U, Integer> getVersionEntityStatusFunction();

}