/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = PortletDataHandlerRegistry.class)
public class PortletDataHandlerRegistryImpl
	implements PortletDataHandlerRegistry {

	@Override
	public PortletDataHandler getPortletDataHandler(
		long companyId, String portletId) {

		PortletDataHandler portletDataHandler = _serviceTrackerMap.getService(
			companyId, portletId);

		if (portletDataHandler != null) {
			return portletDataHandler;
		}

		return _serviceTrackerMap.getService(companyId, "ALL");
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ScopedServiceTrackerMapFactory.create(
			bundleContext, PortletDataHandler.class, "jakarta.portlet.name", "",
			() -> null);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ScopedServiceTrackerMap<PortletDataHandler> _serviceTrackerMap;

}