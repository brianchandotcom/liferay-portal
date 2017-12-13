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

package com.liferay.apio.architect.wiring.osgi.manager;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.Routes;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods to retrieve information provided by the different {@link
 * com.liferay.apio.architect.resource.CollectionResource} instances. This
 * information includes field functions, types, identifier functions, and more.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    com.liferay.apio.architect.resource.CollectionResource
 */
@ProviderType
public interface CollectionResourceManager {

	/**
	 * Returns the API documentation.
	 *
	 * @return the API documentation
	 */
	public Documentation getDocumentation();

	/**
	 * Returns the resource name's model class.
	 *
	 * @param  name the resource name
	 * @return the resource name's model class
	 */
	public <T> Optional<Class<T>> getModelClassOptional(String name);

	/**
	 * Returns the name of a collection resource that matches the specified
	 * class name.
	 *
	 * @param  className the collection resource's class name
	 * @return the collection resource's name
	 */
	public Optional<String> getNameOptional(String className);

	/**
	 * Returns the representor of the collection resource's model class, if that
	 * representor exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  modelClass the collection resource's model class
	 * @return the model class's representor, if present; {@code
	 *         Optional#empty()} otherwise
	 */
	public <T, U extends Identifier> Optional<Representor<T, U>>
		getRepresentorOptional(Class<T> modelClass);

	/**
	 * Returns the root collection resource's list of names.
	 *
	 * @return the root collection resource's list of names
	 */
	public List<String> getRootCollectionResourceNames();

	/**
	 * Returns the model class's routes for the collection resource's name.
	 *
	 * @param  name the collection resource's name
	 * @return the model class's routes
	 */
	public <T> Optional<Routes<T>> getRoutesOptional(String name);

}