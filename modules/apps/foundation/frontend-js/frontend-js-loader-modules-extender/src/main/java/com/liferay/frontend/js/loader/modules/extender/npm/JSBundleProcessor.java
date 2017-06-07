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
 * <p>
 * A service that can process an OSGi bundle to find NPM packages and modules
 * inside it and make them available for the {@link com.liferay.frontend.js.loader.modules.extender.internal.npm.NPMRegistry}
 * to track them.
 * </p>
 * <p>
 * There can be several {@link JSBundleProcessor}s deployed inside one portal if
 * necessary. This would allow deployment of OSGi bundles with different
 * structure for containing the NPM packages.
 * </p>
 * <p>
 * By default, the portal is bundled with a default {@link JSBundleProcessor}
 * implemented by {@link com.liferay.frontend.js.loader.modules.extender.internal.npm.flat.FlatNPMBundleProcessor}
 * which looks for NPM packages inside <i>META-INF/resources/node_modules</i>
 * folder.
 * </p>
 * @author Iván Zaera
 */
public interface JSBundleProcessor {

	/**
	 * <p>
	 * This method is invoked by the {@link com.liferay.frontend.js.loader.modules.extender.internal.npm.NPMRegistry}
	 * whenever a new OSGi bundle is deployed to the portal.
	 * </p>
	 * <p>
	 * The bundle processor is given the {@link Bundle} object and it must
	 * inspect its contents in search for NPM packages and modules.
	 * </p>
	 * <p>
	 * If any NPM package is found, this method must return a {@link JSBundle}
	 * object filled with the description of the NPM packages or modules that
	 * have been found.
	 * </p>
	 * <p>
	 * In any other case (if no package or module is found) the method must
	 * return null.
	 * </p>
	 * @param bundle the handle to the OSGi bundle being deployed.
	 * @return a valid bundle descriptor or null if no NPM packages were found
	 */
	public JSBundle process(Bundle bundle);

}