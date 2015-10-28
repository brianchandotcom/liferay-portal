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

package com.liferay.portal.configurator.extender;

import java.io.IOException;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;

/**
 * @author Carlos Sierra Andrés
 */
public interface BundleStorage {

	public Enumeration<URL> findEntries(
		String root, String pattern, boolean recurse);

	public long getBundleId();

	public URL getEntry(String name);

	public Enumeration<String> getEntryPaths(String name);

	public Dictionary<String, String> getHeaders();

	public String getLocation();

	public URL getResource(String name);

	public Enumeration<URL> getResources(String name) throws IOException;

	public String getSymbolicName();

}