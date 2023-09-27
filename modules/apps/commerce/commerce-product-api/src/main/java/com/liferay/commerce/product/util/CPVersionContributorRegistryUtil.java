/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util;

import com.liferay.osgi.util.ServiceTrackerFactory;

import java.util.List;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Ethan Bustad
 */
public class CPVersionContributorRegistryUtil {

	public static CPVersionContributor getCPVersionContributor(String key) {
		CPVersionContributorRegistry cpVersionContributorRegistry =
			_serviceTracker.getService();

		return cpVersionContributorRegistry.getCPVersionContributor(key);
	}

	public static List<CPVersionContributor> getCPVersionContributors() {
		CPVersionContributorRegistry cpVersionContributorRegistry =
			_serviceTracker.getService();

		return cpVersionContributorRegistry.getCPVersionContributors();
	}

	private static final ServiceTracker<?, CPVersionContributorRegistry>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(CPVersionContributorRegistryUtil.class),
			CPVersionContributorRegistry.class);

}