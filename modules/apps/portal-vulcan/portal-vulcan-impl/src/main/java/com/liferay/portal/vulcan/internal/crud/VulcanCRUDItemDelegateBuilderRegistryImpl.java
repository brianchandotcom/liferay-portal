/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.crud;

import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ScopedServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegate;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateBuilder;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateBuilderRegistry;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjectorBuilderFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = VulcanCRUDItemDelegateBuilderRegistry.class)
public class VulcanCRUDItemDelegateBuilderRegistryImpl
	implements VulcanCRUDItemDelegateBuilderRegistry {

	@Override
	public VulcanCRUDItemDelegateBuilder builder(
		Company company, String entityClassName) {

		VulcanCRUDItemDelegate vulcanCRUDItemDelegate =
			_serviceTrackerMap.getService(
				company.getCompanyId(), entityClassName);

		if (vulcanCRUDItemDelegate == null) {
			return null;
		}

		return new VulcanCRUDItemDelegateBuilderImpl(
			company, _contextDataInjectorBuilderFactory.builder(),
			vulcanCRUDItemDelegate);
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

	@Reference
	private ContextDataInjectorBuilderFactory
		_contextDataInjectorBuilderFactory;

	private ScopedServiceTrackerMap<VulcanCRUDItemDelegate<?>>
		_serviceTrackerMap;

}