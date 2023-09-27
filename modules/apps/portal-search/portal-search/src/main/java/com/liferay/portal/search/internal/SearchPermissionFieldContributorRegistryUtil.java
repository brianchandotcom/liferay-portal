/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFieldContributor;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author jiaxu wei
 */
public class SearchPermissionFieldContributorRegistryUtil {

	public static ServiceTrackerList<SearchPermissionFieldContributor>
		getSearchPermissionFieldContributors() {

		return _serviceTrackerList;
	}

	private static final ServiceTrackerList<SearchPermissionFieldContributor>
		_serviceTrackerList;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SearchPermissionFieldContributorRegistryUtil.class);

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundle.getBundleContext(), SearchPermissionFieldContributor.class);
	}

}