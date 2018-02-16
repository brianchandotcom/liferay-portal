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