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

package com.liferay.asset.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pavel Savinov
 */
public class AssetHelperUtil {

	public static List<AssetEntry> search(
		long companyId, AssetEntryQuery assetEntryQuery) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setClassTypeIds(assetEntryQuery.getClassTypeIds());
		searchContext.setEnd(assetEntryQuery.getEnd());
		searchContext.setStart(assetEntryQuery.getStart());

		AssetHelper assetHelper = _serviceTracker.getService();

		try {
			Hits hits = assetHelper.search(
				searchContext, assetEntryQuery, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			return assetHelper.getAssetEntries(hits);
		}
		catch (Exception e) {
			_log.error("Unable to get asset entriers", e);
		}

		return Collections.emptyList();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetHelperUtil.class);

	private static final ServiceTracker<AssetHelper, AssetHelper>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetHelper.class);

		ServiceTracker<AssetHelper, AssetHelper> serviceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), AssetHelper.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}