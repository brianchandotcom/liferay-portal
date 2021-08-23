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
@Component(immediate = true, service = ObjectScopeProviderRegistry.class)
public class ObjectScopeProviderRegistryImpl
	implements ObjectScopeProviderRegistry {

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

	public List<ObjectScopeProvider> getObjectScopeProviders() {
		List<ObjectScopeProvider> objectScopeProviders =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		if (objectScopeProviders == null) {
			return Collections.emptyList();
		}

		return objectScopeProviders;
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
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