/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer;

import java.io.File;

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Shuyang Zhou
 */
public interface LPKGVerifier {

	/**
	 * Verifies that the file is deployable as an LPKG bundle.
	 *
	 * @param  lpkgFile the file to verify
	 * @return the older bundles with the same symbolic name and lower versions.
	 *         The older bundles must be uninstalled before the new LPKG file
	 *         can be installed. When no older bundles are found, this method
	 *         returns an empty list.
	 * @throws LPKGVerifyException if the LPKG file does not contain a
	 *         <code>liferay-marketplace.properties</code> file, if the
	 *         <code>liferay-marketplace.properties</code> file does not have a
	 *         valid title and version, or if a bundle already exists with the
	 *         same symbolic name and version
	 */
	public List<Bundle> verify(File lpkgFile) throws LPKGVerifyException;

}