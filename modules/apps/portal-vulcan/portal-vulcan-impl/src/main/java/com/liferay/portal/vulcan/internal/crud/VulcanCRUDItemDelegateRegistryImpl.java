/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.crud;

import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMapFactory;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegate;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 * @author Carlos Correa
 */
@Component(service = VulcanCRUDItemDelegateRegistry.class)
public class VulcanCRUDItemDelegateRegistryImpl
	implements VulcanCRUDItemDelegateRegistry {

	@Override
	public VulcanCRUDItemDelegate<?> getVulcanCRUDItemDelegate(
		long companyId, String entityClassName) {

		return _serviceTrackerMap.getService(companyId, entityClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ScopedServiceTrackerMapFactory.create(
			bundleContext, null, "entity.class.name",
			"(crud.item.delegate=true)", () -> null);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ScopedServiceTrackerMap<VulcanCRUDItemDelegate<?>>
		_serviceTrackerMap;

}