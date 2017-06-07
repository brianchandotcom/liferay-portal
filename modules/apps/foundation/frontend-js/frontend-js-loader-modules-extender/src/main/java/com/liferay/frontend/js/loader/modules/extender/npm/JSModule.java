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

import java.util.Collection;

/**
 * <p>
 * A description of an NPM module inside a {@link JSBundle}.
 * </p>
 * <p>
 * {@link JSModule}s always belong to a {@link JSPackage}, which at the same
 * time, belongs to a {@link JSBundle}.
 * </p>
 * @author Iván Zaera
 */
public interface JSModule extends JSResolvableBundleAsset {

	/**
	 * Get the list of dependencies declared by this NPM module.
	 * @return a list of module names
	 */
	public Collection<String> getDependencies();

	/**
	 * Get the list of packages that contain all the dependencies of this
	 * module.
	 * @see JSModule#getDependencies()
	 * @return a list of NPM package names
	 */
	public Collection<String> getDependencyPackageNames();

	/**
	 * Get the NPM package where this module belongs.
	 */
	public JSPackage getJSPackage();

}