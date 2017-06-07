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

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.net.URL;

import java.util.Collection;

/**
 * A JSBundle describes an OSGi bundle containing some NPM packages and modules.
 * @author Iván Zaera
 */
public interface JSBundle extends JSBundleObject {

	/**
	 * Retrieve the list of NPM packages provided by the OSGi bundle.
	 */
	public Collection<JSPackage> getJSPackages();

	/**
	 * Retrieve the {@link URL} of a resource living inside the OSGi bundle.
	 */
	public URL getResourceURL(String location);

	/**
	 * Get the OSGi version of the bundle.
	 */
	public String getVersion();

}