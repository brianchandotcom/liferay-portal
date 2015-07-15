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
	 * Finds the resource with the given name. A resource is some data that can
	 * be accessed in a way that is independent of the location or storage.
	 *
	 * <p> The name of a resource is a '<tt>/</tt>'-separated path name that
	 * identifies the resource.
	 *
	 * @param   name The resource name
	 * @return  A <tt>URL</tt> object for reading the resource, or <tt>null</tt>
	 *          if the resource could not be found or the invoker doesn't have
	 *          adequate  privileges to get the resource.
	 */
	public URL getResource(String name);

	/**
	 * Returns a directory-like listing of all the paths to resources within the
	 * container whose longest sub-path matches the supplied path argument.
	 * <p>
	 * Paths indicating sub-directory paths end with a '<tt>/</tt>'.
	 * <p>
	 * A path can be passed to the {{@link #getResource(String)} method to
	 * return a resource URL.
	 *
	 * @param   name The resource name
	 * @return  A set of {@link String <tt>String</tt>} objects representing
	 *          individual resources in the container. If no resources could be
	 *          found, the set will be empty. Resources that the invoker doesn't
	 *          have access to will not be included.
	 */
	public Set<String> getResources(String path);

}