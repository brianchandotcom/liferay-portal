/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.scope;

import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = ObjectScopeProviderRegistry.class)
public class ObjectScopeProviderRegistryImpl
	implements ObjectScopeProviderRegistry {

	@Override
	public ObjectScopeProvider getObjectScopeProvider(
		String objectScopeProviderKey) {

		ObjectScopeProvider objectScopeProvider = _serviceTrackerMap.getService(
			objectScopeProviderKey);

		if (objectScopeProvider == null) {
			throw new IllegalArgumentException(
				"No object scope provider found with key " +
					objectScopeProviderKey);
		}

		return objectScopeProvider;
	}

	@Override
	public List<ObjectScopeProvider> getObjectScopeProviders() {
		List<ObjectScopeProvider> objectScopeProviders =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		if (objectScopeProviders == null) {
			return Collections.emptyList();
		}

		return objectScopeProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectScopeProvider.class,
			"object.scope.provider.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectScopeProvider> _serviceTrackerMap;

}