/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.action;

import com.liferay.asset.publisher.action.AssetEntryAction;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

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
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<AssetEntryAction<?>>)(Class<?>)AssetEntryAction.class, null,
			new PropertyServiceReferenceMapper<>("model.class.name"),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"asset.entry.action.order")));
	}

	private ServiceTrackerMap<String, List<AssetEntryAction<?>>>
		_serviceTrackerMap;

}