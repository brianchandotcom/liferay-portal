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

import com.liferay.portal.kernel.model.BaseModel;

import java.io.Serializable;

import java.util.List;
import java.util.function.Function;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface CTConfiguration<T extends BaseModel, U extends BaseModel> {

	/**
	 * Returns the content type human readable text for this configuration. It
	 * is a group name of the entities handled in this configuration, such as
	 * JournalArticle, JournalResource has the content type Web Content.
	 *
	 * @return the content type handled in this configuration
	 * @review
	 */
	public String getContentType();

	/**
	 * Returns the language key for the content type. The content type might be
	 * written in a language, but the application set to a different one. The
	 * language key would provide the language agnostic display of the content
	 * type.
	 *
	 * @return the language key for the content type
	 * @review
	 */
	public String getContentTypeLanguageKey();

	/**
	 * Returns the function from the configuration that retrieves the list of
	 * resources entities from a given company.
	 *
	 * @return The function from the configuration that retrieves the list of
	 *         resources entities from a given company.
	 */
	public Function<Long, List<T>> getResourceEntitiesByCompanyIdFunction();

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
	 * Returns the function from the configuration that retrieves the list of
	 * version entities for a given resource entity.
	 *
	 * @return The function from the configuration that retrieves the list of
	 *         version entities for a given resource entity.
	 */
	public Function<T, List<U>> getVersionEntitiesFromResourceEntityFunction();

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

	public Function<U, String> getVersionEntitySiteNameFunction();

	/**
	 * Returns the function from the configuration that retrieves the version
	 * entity's workflow status from the version entity
	 *
	 * @return The function from the configuration that retrieves the version
	 *         entity's workflow status from the version entity
	 */
	public Function<U, Integer> getVersionEntityStatusFunction();

	public Function<U, String> getVersionEntityTitleFunction();

	public Function<U, Serializable> getVersionEntityVersionFunction();

}