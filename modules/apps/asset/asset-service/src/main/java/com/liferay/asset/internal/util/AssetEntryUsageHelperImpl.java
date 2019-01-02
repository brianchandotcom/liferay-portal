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

package com.liferay.asset.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetEntryUsageChecker;
import com.liferay.asset.util.AssetEntryUsageHelper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = AssetEntryUsageHelper.class)
public class AssetEntryUsageHelperImpl implements AssetEntryUsageHelper {

	@Override
	public void checkAssetEntryUsages(AssetEntry assetEntry)
		throws PortalException {

		AssetEntryUsageChecker assetEntryUsageChecker =
			_serviceTrackerMap.getService(assetEntry.getClassName());

		if (assetEntryUsageChecker == null) {
			return;
		}

		assetEntryUsageChecker.checkAssetEntryUsages(assetEntry);
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AssetEntryUsageChecker.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, AssetEntryUsageChecker>
		_serviceTrackerMap;

}