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

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Shuyang Zhou
 */
public interface LPKGVerifier {

	/**
	 * Verify whether the give file is deployable as LPKG bundle.
	 *
	 * @param lpkgFile The file to be verified.
	 * @return Older bundles with same symbolic name and lower versions. They
	 *         must be uninstalled first, before this lpkg file can be
	 *         installed. When no older bundles can be found, an empty list will
	 *         be returned.
	 * @throws LPKGVerifyException In case of:
	 *         <li>lpkgFile does not contain liferay-marketplace.properties.
	 *         <li>liferay-marketplace.properties does not have valid title and
	 *         version.
	 *         <li>A bundle exists with the same symbolic name and same/higher
	 *         version.
	 */
	public List<Bundle> verify(File lpkgFile) throws LPKGVerifyException;

}