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

package com.liferay.change.tracking.definition;

import com.liferay.portal.kernel.model.BaseModel;

import java.io.Serializable;

import java.util.List;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Máté Thurzó
 */
@ProviderType
public interface CTDefinition<T extends BaseModel, U extends BaseModel> {

	/**
	 * Returns the content type human readable text for this definition. It
	 * is a group name of the entities handled in this definition. For
	 * example, <code>JournalArticle</code> and <code>JournalResource</code>
	 * have the content type <code>Web Content</code>.
	 *
	 * @return the content type handled in this definition
	 */
	public String getContentType();

	/**
	 * Returns the content type's language key. The content type and application
	 * could be written in differing languages; the language key would provide
	 * the language agnostic display of the content type.
	 *
	 * @return the content type's language key
	 */
	public String getContentTypeLanguageKey();

	/**
	 * Returns the definition's function that retrieves resource entities
	 * from the company.
	 *
	 * @return the function that retrieves resource entities from the company
	 */
	public Function<Long, List<T>> getResourceEntitiesByCompanyIdFunction();

	/**
	 * Returns the definition's function that retrieves the resource entity
	 * by its primary key.
	 *
	 * @return the function that retrieves the resource entity by its primary
	 *         key
	 */
	public Function<Long, T> getResourceEntityByResourceEntityIdFunction();

	/**
	 * Returns the resource entity's class from the definition.
	 *
	 * @return the resource entity's class from the definition
	 */
	public Class<T> getResourceEntityClass();

	/**
	 * Returns the definition's function that retrieves the resource entity's
	 * primary key from the resource entity.
	 *
	 * @return the function that retrieves the resource entity's primary key
	 *         from the resource entity
	 */
	public Function<T, Serializable>
		getResourceEntityIdFromResourceEntityFunction();

	/**
	 * Returns the definition's function that retrieves the resource entity's
	 * primary key from the version entity.
	 *
	 * @return the function that retrieves the resource entity's primary key
	 *         from the version entity
	 */
	public Function<U, Serializable>
		getResourceEntityIdFromVersionEntityFunction();

	/**
	 * Returns the definition's function that retrieves the version entities
	 * for a given resource entity.
	 *
	 * @return the function that retrieves the version entities for a given
	 *         resource entity
	 */
	public Function<T, List<U>> getVersionEntitiesFromResourceEntityFunction();

	/**
	 * Returns the allowed workflow statuses for the definition's version
	 * entity.
	 *
	 * @return the allowed workflow statuses for the version entity
	 */
	public Integer[] getVersionEntityAllowedStatuses();

	/**
	 * Returns the definition's function that retrieves the version entity by
	 * it's primary key.
	 *
	 * @return the function that retrieves the version entity by it's primary
	 *         key
	 */
	public Function<Long, U> getVersionEntityByVersionEntityIdFunction();

	/**
	 * Returns the version entity's class from the definition.
	 *
	 * @return the version entity's class
	 */
	public Class<U> getVersionEntityClass();

	/**
	 * Returns the definition's function that retrieves the version entity's
	 * primary key from the resource entity.
	 *
	 * @return the function that retrieves the version entity's primary key from
	 *         the resource entity
	 */
	public Function<T, Serializable>
		getVersionEntityIdFromResourceEntityFunction();

	/**
	 * Returns the definition's function that retrieves the version entity's
	 * primary key from the version entity.
	 *
	 * @return the function that retrieves the version entity's primary key from
	 *         the version entity
	 */
	public Function<U, Serializable>
		getVersionEntityIdFromVersionEntityFunction();

	/**
	 * Returns the functions that each retrieve related entities of a version
	 * entity.
	 *
	 * @return the functions that each retrieve related entities of a version
	 *         entity
	 */
	public List<Function<U, List<? extends BaseModel>>>
		getVersionEntityRelatedEntitiesFunctions();

	public Function<U, String> getVersionEntitySiteNameFunction();

	/**
	 * Returns the definition's function that retrieves the version entity's
	 * workflow status from the version entity.
	 *
	 * @return the function that retrieves the version entity's workflow status
	 *         from the version entity
	 */
	public Function<U, Integer> getVersionEntityStatusFunction();

	public Function<U, String> getVersionEntityTitleFunction();

	public Function<U, Serializable> getVersionEntityVersionFunction();

}