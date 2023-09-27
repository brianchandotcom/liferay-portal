/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.list.retriever;

import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ListObjectReferenceFactoryRegistry.class)
public class ListObjectReferenceRegistryImpl
	implements ListObjectReferenceFactoryRegistry {

	@Override
	public ListObjectReferenceFactory<?> getListObjectReference(String type) {
		return _serviceTrackerMap.getService(type);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ListObjectReferenceFactory<?>>)
				(Class<?>)ListObjectReferenceFactory.class,
			null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(listObjectReferenceFactory, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(
						listObjectReferenceFactory))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ListObjectReferenceFactory<?>>
		_serviceTrackerMap;

}