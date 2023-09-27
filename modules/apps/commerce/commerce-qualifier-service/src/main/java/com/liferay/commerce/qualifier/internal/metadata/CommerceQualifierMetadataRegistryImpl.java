/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.internal.metadata;

import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadataRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Riccardo Alberti
 */
@Component(service = CommerceQualifierMetadataRegistry.class)
public class CommerceQualifierMetadataRegistryImpl
	implements CommerceQualifierMetadataRegistry {

	@Override
	public CommerceQualifierMetadata getCommerceQualifierMetadata(
		String commerceQualifierMetadataKey) {

		return _serviceTrackerMap.getService(commerceQualifierMetadataKey);
	}

	@Override
	public CommerceQualifierMetadata getCommerceQualifierMetadataByClassName(
		String className) {

		for (CommerceQualifierMetadata commerceQualifierMetadata :
				_serviceTrackerMap.values()) {

			if (Objects.equals(
					className, commerceQualifierMetadata.getModelClassName())) {

				return commerceQualifierMetadata;
			}
		}

		return null;
	}

	@Override
	public List<CommerceQualifierMetadata> getCommerceQualifierMetadatas() {
		List<CommerceQualifierMetadata> commerceQualifierMetadatas =
			new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			CommerceQualifierMetadata commerceQualifierMetadata =
				_serviceTrackerMap.getService(key);

			commerceQualifierMetadatas.add(commerceQualifierMetadata);
		}

		return Collections.unmodifiableList(commerceQualifierMetadatas);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceQualifierMetadata.class, null,
			(serviceReference, emitter) -> {
				CommerceQualifierMetadata commerceQualifierMetadata =
					bundleContext.getService(serviceReference);

				try {
					if (commerceQualifierMetadata.getKey() != null) {
						emitter.emit(commerceQualifierMetadata.getKey());
					}
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceQualifierMetadata>
		_serviceTrackerMap;

}