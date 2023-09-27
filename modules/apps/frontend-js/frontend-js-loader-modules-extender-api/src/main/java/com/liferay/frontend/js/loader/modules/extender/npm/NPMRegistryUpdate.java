/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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