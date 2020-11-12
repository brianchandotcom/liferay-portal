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

package com.liferay.portal.kernel.upgrade;

import java.util.Set;

/**
 * @author Samuel Ziemer
 */
public interface ReleaseManager {

	public String check();

	public String check(boolean listAllUpgrades);

	public String execute(String bundleSymbolicName);

	public String execute(String bundleSymbolicName, String toVersionString);

	public String executeAll();

	public String getSchemaVersionString(String bundleSymbolicName);

	public Set<String> getUpgradableBundleSymbolicNames();

	public boolean isUpgradable(String bundleSymbolicName);

	public String list();

	public String list(String bundleSymbolicName);

}