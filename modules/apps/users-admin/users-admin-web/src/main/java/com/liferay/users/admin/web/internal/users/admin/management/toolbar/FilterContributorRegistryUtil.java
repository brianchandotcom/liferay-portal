/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.users.admin.management.toolbar;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.users.admin.management.toolbar.FilterContributor;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Drew Brokke
 */
public class FilterContributorRegistryUtil {

	public static FilterContributor[] getFilterContributors(String id) {
		List<FilterContributor> filterContributors =
			_serviceTrackerMap.getService(id);

		if (filterContributors == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No filter contributors found for ID " + id);
			}

			return new FilterContributor[0];
		}

		return filterContributors.toArray(new FilterContributor[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FilterContributorRegistryUtil.class);

	private static final ServiceTrackerMap<String, List<FilterContributor>>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FilterContributorRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FilterContributor.class, "filter.contributor.key");
	}

}