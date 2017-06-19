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
 * Represents an NPM module inside a {@link JSBundle}.
 * 
 * <p>
 * {@link JSModule}s belong to a {@link JSPackage} and {@link JSPackage}s belong
 * to a {@link JSBundle}.
 * </p>
 *
 * @author Iván Zaera
 */
public interface JSModule extends JSResolvableBundleAsset {

	/**
	 * Returns the module dependencies declared by the NPM module.
	 *
	 * @return the module names
	 */
	public Collection<String> getDependencies();

	/**
	 * Returns the packages that contain all the NPM module's dependencies.
	 *
	 * @return the NPM package names
	 */
	public Collection<String> getDependencyPackageNames();

	/**
	 * Returns the module's NPM package.
	 *
	 * @return the NPM package
	 */
	public JSPackage getJSPackage();

}