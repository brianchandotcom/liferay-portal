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

package com.liferay.portal.kernel.url;

import java.net.URL;

import java.util.Set;

/**
 * @author Raymond Augé
 */
public interface URLContainer {

	/**
	 * Returns the resource with the given name. A resource is data that can be
	 * accessed in a way that is independent of the location or storage.
	 *
	 * <p>
	 * The name is a slash (<code>/</code>) separated path that identifies the
	 * resource.
	 * </p>
	 *
	 * @param  name the resource name
	 * @return the URL used for reading the resource, or <code>null</code> if
	 *         the resource is not found or if the invoker does not have
	 *         adequate privileges to get the resource
	 */
	public URL getResource(String name);

	/**
	 * Returns the directory-like listing of all the paths to resources within
	 * the container whose longest sub-path matches the given path. Resources
	 * that the invoker does not have access to are not included. If no
	 * resources are found, an empty set is returned.
	 *
	 * <p>
	 * Paths indicating sub-directory paths end with a slash (<code>/</code>). A
	 * path can be passed to the {@link #getResource(String)} method to return a
	 * resource URL.
	 * </p>
	 *
	 * @param  path the resource path
	 * @return the paths representing individual resources in the container
	 */
	public Set<String> getResources(String path);

}