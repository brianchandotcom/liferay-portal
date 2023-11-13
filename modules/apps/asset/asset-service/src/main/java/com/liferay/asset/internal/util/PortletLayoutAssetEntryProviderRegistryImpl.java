/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.util;

import com.liferay.asset.provider.PortletLayoutAssetEntryProvider;
import com.liferay.asset.provider.PortletLayoutAssetEntryProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 */
@Component(service = PortletLayoutAssetEntryProviderRegistry.class)
public class PortletLayoutAssetEntryProviderRegistryImpl
	implements PortletLayoutAssetEntryProviderRegistry {

	@Override
	public PortletLayoutAssetEntryProvider getPortletLayoutAssetEntryProvider(
		String className) {

		return _serviceTrackerMap.getService(className);
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletLayoutAssetEntryProvider.class,
			"javax.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap<String, PortletLayoutAssetEntryProvider>
		_serviceTrackerMap;

}