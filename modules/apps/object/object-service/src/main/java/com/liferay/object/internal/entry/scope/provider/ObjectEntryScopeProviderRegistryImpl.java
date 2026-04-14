/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.entry.scope.provider;

import com.liferay.object.entry.scope.provider.ObjectEntryScopeProvider;
import com.liferay.object.entry.scope.provider.ObjectEntryScopeProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Carolina Barbosa
 */
@Component(service = ObjectEntryScopeProviderRegistry.class)
public class ObjectEntryScopeProviderRegistryImpl
	implements ObjectEntryScopeProviderRegistry {

	@Override
	public ObjectEntryScopeProvider getObjectEntryScopeProvider(
		String objectDefinitionExternalReferenceCode) {

		return _serviceTrackerMap.getService(
			objectDefinitionExternalReferenceCode);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectEntryScopeProvider.class,
			"object.definition.external.reference.code");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectEntryScopeProvider>
		_serviceTrackerMap;

}