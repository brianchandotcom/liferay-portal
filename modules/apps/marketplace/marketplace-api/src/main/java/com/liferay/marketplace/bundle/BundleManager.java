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

package com.liferay.marketplace.bundle;

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Joan Kim
 * @author Ryan Park
 */
public interface BundleManager {

	public Bundle getBundle(String symbolicName, String versionString);

	public List<Bundle> getBundles();

	public List<Bundle> getInstalledBundles();

}