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

package com.liferay.lpkg.deployer;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public interface LPKGDeployer {

	/**
	 * Deploy the givn lpkg file.
	 *
	 * @param bundleContext Used to install bundle into OSGi container.
	 * @param lpkgFile The lpkg file to be deployed.
	 * @return A list of bundles with at least 1 bundle. The 1st bundle is the
	 *         lpkg bundle, the rests are app bundles from the lpkg package(if
	 *         there is any.)
	 * @throws IOException IO failures during installation.
	 * @throws LPKGVerifyException The given lpkg file fails verification.
	 */
	public List<Bundle> deploy(BundleContext bundleContext, File lpkgFile)
		throws IOException;

	/**
	 * Return all deployed lpkg bundles together with their app bundles.
	 * @return A map of bundles, key is lpkg bundle, value is app bundle list.
	 */
	public Map<Bundle, List<Bundle>> getDeployedLPKGBundles();

}