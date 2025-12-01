/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify.util;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Luis Ortiz
 */
public class PostUpgradeDataCleanupProcessUtil {

	public static boolean isEveryLiferayBundleResolved() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			String bundleSymbolicName = bundle.getSymbolicName();

			if (!bundleSymbolicName.startsWith("com.liferay.")) {
				continue;
			}

			if (bundle.getState() == Bundle.INSTALLED) {
				return false;
			}
		}

		return true;
	}

}