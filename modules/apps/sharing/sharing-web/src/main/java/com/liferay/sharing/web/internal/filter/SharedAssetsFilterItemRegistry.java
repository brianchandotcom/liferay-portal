/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.filter;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = SharedAssetsFilterItemRegistry.class)
public class SharedAssetsFilterItemRegistry {

	public SharedAssetsFilterItem getSharedAssetsFilterItem(String className) {
		for (SharedAssetsFilterItem sharedAssetsFilterItem :
				_serviceTrackerList) {

			if (StringUtil.equals(
					className, sharedAssetsFilterItem.getClassName())) {

				return sharedAssetsFilterItem;
			}
		}

		return null;
	}

	public List<SharedAssetsFilterItem> getSharedAssetsFilterItems() {
		return _serviceTrackerList.toList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SharedAssetsFilterItem.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"navigation.item.order")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<SharedAssetsFilterItem> _serviceTrackerList;

}