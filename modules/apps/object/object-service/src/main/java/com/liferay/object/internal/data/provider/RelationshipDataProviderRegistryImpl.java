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

package com.liferay.object.internal.data.provider;

import com.liferay.object.data.provider.RelationshipDataProvider;
import com.liferay.object.data.provider.RelationshipDataProviderRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = RelationshipDataProviderRegistry.class)
public class RelationshipDataProviderRegistryImpl
	implements RelationshipDataProviderRegistry {

	@Override
	public RelationshipDataProvider getRelationshipDataProvider(
			String className, String type)
		throws PortalException {

		RelationshipDataProvider relationshipDataProvider =
			_serviceTrackerMap.getService(_formatKey(className, type));

		if (relationshipDataProvider == null) {
			throw new IllegalArgumentException(
				"No relationship data provider found with key " +
					_formatKey(className, type));
		}

		return relationshipDataProvider;
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, RelationshipDataProvider.class, null,
			(serviceReference, emitter) -> {
				RelationshipDataProvider relationshipDataProvider =
					bundleContext.getService(serviceReference);

				emitter.emit(
					_formatKey(
						relationshipDataProvider.getClassName(),
						relationshipDataProvider.getObjectRelationshipType()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _formatKey(String className, String type) {
		return className + "_" + type;
	}

	private ServiceTrackerMap<String, RelationshipDataProvider>
		_serviceTrackerMap;

}