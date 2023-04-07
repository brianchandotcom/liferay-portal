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

package com.liferay.marketplace.app.manager.web.internal.util;

import com.liferay.marketplace.bundle.BundleManager;
import com.liferay.osgi.util.service.Snapshot;

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Matthew Tambara
 */
public class BundleManagerUtil {

	public static Bundle getBundle(String symbolicName, String version) {
		BundleManager bundleManager = _bundleManagerSnapshot.get();

		return bundleManager.getBundle(symbolicName, version);
	}

	public static List<Bundle> getBundles() {
		BundleManager bundleManager = _bundleManagerSnapshot.get();

		return bundleManager.getBundles();
	}

	private static final Snapshot<BundleManager> _bundleManagerSnapshot =
		new Snapshot<>(BundleManagerUtil.class, BundleManager.class);

}