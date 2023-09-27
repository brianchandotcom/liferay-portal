/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * A {@link JSPackage} that allows addition of new {@link JSModule}s.
 *
 * <p>
 * Note that adding a {@link JSModule} to a {@link ModifiableJSPackage} doesn't
 * update the {@link NPMRegistry} by itself, thus the new module won't be
 * visible until the registry updates its caches.
 * </p>
 *
 * <p>
 * If you want the module to be visible immediately, use the
 * {@link NPMRegistry#registerJSModule(JSPackage, String, Collection, String, String)}
 * method instead that invokes {@link ModifiableJSPackage#addJSModule(JSModule)} under
 * the hood and triggers a registry cache update.
 * </p>
 *
 * @author Iván Zaera
 * @review
 * @see NPMRegistry#registerJSModule(JSPackage, String, Collection, String, String)
 */
public interface ModifiableJSPackage extends JSPackage {

	public void addJSModule(JSModule jsModule);

	public void removeJSModule(JSModule jsModule);

	public void replaceJSModule(JSModule jsModule);

}