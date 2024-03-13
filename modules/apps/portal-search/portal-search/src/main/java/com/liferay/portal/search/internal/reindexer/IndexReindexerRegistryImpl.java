/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.reindexer;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.spi.reindexer.IndexReindexerRegistry;

import java.util.Collection;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gustavo Lima
 */
@Component(service = IndexReindexerRegistry.class)
public class IndexReindexerRegistryImpl implements IndexReindexerRegistry {

	@Override
	public IndexReindexer getIndexReindexer(String className) {
		return _serviceTrackerMap.getService(className);
	}

	@Override
	public Set<String> getIndexReindexerClassNames() {
		return _serviceTrackerMap.keySet();
	}

	@Override
	public Collection<IndexReindexer> getIndexReindexers() {
		return _serviceTrackerMap.values();
	}

	private static final ServiceTrackerMap<String, IndexReindexer>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(IndexReindexerRegistry.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, IndexReindexer.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(indexReindexer, emitter) -> {
					Class<? extends IndexReindexer> clazz =
						indexReindexer.getClass();

					emitter.emit(clazz.getName());
				}));
	}

}