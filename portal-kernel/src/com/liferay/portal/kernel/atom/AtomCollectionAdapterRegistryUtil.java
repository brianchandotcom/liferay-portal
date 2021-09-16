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

package com.liferay.portal.kernel.atom;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterRegistryUtil {

	public static AtomCollectionAdapter<?> getAtomCollectionAdapter(
		String collectionName) {

		return _atomCollectionAdapters.getService(collectionName);
	}

	public static List<AtomCollectionAdapter<?>> getAtomCollectionAdapters() {
		return new ArrayList<>(_atomCollectionAdapters.values());
	}

	private AtomCollectionAdapterRegistryUtil() {
	}

	private static final ServiceTrackerMap<String, AtomCollectionAdapter<?>>
		_atomCollectionAdapters = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(),
			(Class<AtomCollectionAdapter<?>>)
				(Class<?>)AtomCollectionAdapter.class,
			null,
			(serviceReference, emitter) -> {
				BundleContext bundleContext =
					SystemBundleUtil.getBundleContext();

				AtomCollectionAdapter<?> atomCollectionAdapter =
					bundleContext.getService(serviceReference);

				emitter.emit(atomCollectionAdapter.getCollectionName());

				bundleContext.ungetService(serviceReference);
			});

}