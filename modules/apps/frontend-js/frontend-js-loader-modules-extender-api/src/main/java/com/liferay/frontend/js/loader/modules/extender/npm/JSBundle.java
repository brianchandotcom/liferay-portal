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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents an OSGi bundle containing NPM packages and modules.
 *
 * @author Iván Zaera
 */
@ProviderType
public interface JSBundle extends JSBundleObject {

	/**
	 * Returns the NPM packages provided by the OSGi bundle.
	 *
	 * @return the NPM packages
	 */
	public Collection<JSPackage> getJSPackages();

	/**
	 * Returns the {@link URL} of an OSGi bundle's resource.
	 *
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 * @return the URL of an OSGi bundle's resource
	 */
	@Deprecated
	public URL getResourceURL(String location);

	/**
	 * Returns the bundle's OSGi version.
	 *
	 * @return the bundle's OSGi version
	 */
	public String getVersion();

}