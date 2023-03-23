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
 * An object to update the {@link NPMRegistry} modules in an efficient way.
 *
 * The motivation for this interface is the need to be able to update several
 * {@link JSModule}s without triggering a cache update, which is a quite costly
 * operation.
 *
 * Note that {@link NPMRegistryUpdate}s are not thread safe.
 *
 * Also note that the update is not performed atomically, it just groups
 * operations so that just one cache update is triggered for all of them, but
 * even if {@link NPMRegistryUpdate#finish()} is not called, the updates take
 * place and will eventually be seen in the {@link NPMRegistry} as soon as it
 * triggers a cache refresh.
 *
 * @author Iván Zaera Avellón
 * @review
 */
public interface NPMRegistryUpdate {

	/**
	 * Call this method when all modules have been updated so that the
	 * {@link NPMRegistry} can refresh its caches.
	 */
	public void finish();

	public JSModule registerJSModule(
		JSPackage jsPackage, String moduleName, Collection<String> dependencies,
		String js, String map);

	public void unregisterJSModule(JSModule jsModule);

	public void updateJSModule(
		JSModule jsModule, Collection<String> dependencies, String js,
		String map);

}