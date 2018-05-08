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

import org.osgi.framework.Bundle;

/**
 * Processes an OSGi bundle to find NPM packages and modules inside it and make
 * them available for the {@link NPMRegistry} to track them.
 *
 * <p>
 * There can be several {@link JSBundleProcessor}s deployed inside one portal if
 * necessary. This allows the deployment of OSGi bundles with different
 * structures containing the NPM packages.
 * </p>
 *
 * <p>
 * By default, the portal is bundled with a default {@link JSBundleProcessor}
 * implemented by {@link
 * com.liferay.frontend.js.loader.modules.extender.internal.npm.flat.FlatNPMBundleProcessor},
 * which looks for NPM packages inside the
 * <code>META-INF/resources/node_modules</code> folder.
 * </p>
 *
 * @author Iván Zaera
 */
public interface JSBundleProcessor {

	/**
	 * Returns a JS Bundle filled with the description of the NPM packages or
	 * modules found in the given bundle. If the given bundle does not contain
	 * an NPM package or module, this method returns <code>null</code>.
	 *
	 * <p>
	 * This method is invoked by the {@link NPMRegistry} whenever a new OSGi
	 * bundle is deployed to the portal.
	 * </p>
	 *
	 * @param  bundle the handle to the OSGi bundle being deployed
	 * @return the valid bundle descriptor or <code>null</code> if no NPM
	 *         packages or modules were found
	 */
	public JSBundle process(Bundle bundle);

}