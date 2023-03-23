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