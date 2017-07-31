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

package com.liferay.vulcan.uri;

/**
 * Writers may use an instance of this interface to customize the URIs of a
 * resource that follows the collection pattern.
 *
 * <p>
 * For example, an instance of this interface can be used to add a prefix before
 * every URI.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface CollectionResourceURITransformer {

	/**
	 * Returns the transformed URI of a collection item endpoint.
	 *
	 * @param  uri the collection item URI.
	 * @param  modelClass the model class.
	 * @param  model the model instance.
	 * @return the transformed URI.
	 */
	public <T> String transformCollectionItemSingleResourceURI(
		String uri, Class<T> modelClass, T model);

	/**
	 * Returns the transformed URI of a page endpoint.
	 *
	 * @param  uri the page URI.
	 * @param  modelClass the model class.
	 * @return the transformed URI.
	 */
	public <T> String transformPageURI(String uri, Class<T> modelClass);

}