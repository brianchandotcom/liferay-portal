/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public interface LPKGDeployer {

	/**
	 * Deploys the LPKG file. This method returns the list of bundles in the
	 * LPKG file, which includes the LPKG bundle and all its app bundles (if any
	 * exist). For example, if the LPKG file included four app bundles, then
	 * five bundles are returned (i.e. one LPKG bundle and four app bundles).
	 *
	 * @param  bundleContext the context used to install the bundle into the
	 *         OSGi container
	 * @param  lpkgFile the LPKG file to deploy
	 * @return the LPKG file bundle and its included app bundles
	 * @throws IOException if an IO failure during installation occurred
	 */
	public List<Bundle> deploy(BundleContext bundleContext, File lpkgFile)
		throws IOException;

	/**
	 * Returns the deployed LPKG bundles together with their app bundles.
	 *
	 * @return the map of bundles with the LPKG bundle as the key and the LPKG's
	 *         app bundle list as the value
	 */
	public Map<Bundle, List<Bundle>> getDeployedLPKGBundles();

	public InputStream toBundle(File lpkgFile) throws IOException;

}