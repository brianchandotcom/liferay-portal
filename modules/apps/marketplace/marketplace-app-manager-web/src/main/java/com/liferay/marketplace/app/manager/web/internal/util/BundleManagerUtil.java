/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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