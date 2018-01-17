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
 * @author Iván Zaera Avellón
 */
public interface NPMRegistry {

	public JSModule getJSModule(String identifier);

	public JSPackage getJSPackage(String identifier);

	public Collection<JSPackage> getJSPackages();

	public JSModule getResolvedJSModule(String identifier);

	public Collection<JSModule> getResolvedJSModules();

	public Collection<JSPackage> getResolvedJSPackages();

	public JSPackage resolveJSPackageDependency(
		JSPackageDependency jsPackageDependency);

}