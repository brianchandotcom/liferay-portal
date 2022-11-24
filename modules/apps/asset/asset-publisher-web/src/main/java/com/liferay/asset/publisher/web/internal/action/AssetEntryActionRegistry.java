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

package com.liferay.asset.publisher.web.internal.action;

import com.liferay.asset.kernel.action.AssetEntryAction;
import com.liferay.osgi.service.tracker.collections.ServiceTrackerMapBuilder;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = AssetEntryActionRegistry.class)
public class AssetEntryActionRegistry {

	public List<AssetEntryAction<?>> getAssetEntryActions(String className) {
		List<AssetEntryAction<?>> assetEntryActions =
			_serviceTrackerMap.getService(className);

		if (assetEntryActions != null) {
			return assetEntryActions;
		}

		return Collections.emptyList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			ServiceTrackerMapBuilder.SelectorFactory.newSelector(
				bundleContext,
				(Class<AssetEntryAction<?>>)(Class<?>)AssetEntryAction.class
			).map(
				"model.class.name"
			).collectMultiValue(
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"asset.entry.action.order"))
			).build();
	}

	private ServiceTrackerMap<String, List<AssetEntryAction<?>>>
		_serviceTrackerMap;

}