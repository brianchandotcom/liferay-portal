/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.util;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alejandro Tardín
 */
public class SharedAssetsFilterItemRegistryUtil {

	public static SharedAssetsFilterItem getSharedAssetsFilterItem(
		String className) {

		for (SharedAssetsFilterItem sharedAssetsFilterItem :
				_serviceTrackerList) {

			if (StringUtil.equals(
					className, sharedAssetsFilterItem.getClassName())) {

				return sharedAssetsFilterItem;
			}
		}

		return null;
	}

	public static List<SharedAssetsFilterItem> getSharedAssetsFilterItems() {
		return _serviceTrackerList.toList();
	}

	private static final ServiceTrackerList<SharedAssetsFilterItem>
		_serviceTrackerList;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SharedAssetsFilterItemRegistryUtil.class);

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundle.getBundleContext(), SharedAssetsFilterItem.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"navigation.item.order")));
	}

}