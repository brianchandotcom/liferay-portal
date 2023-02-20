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

package com.liferay.portal.search.admin.web.internal.reindexer;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;

import java.util.Collection;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jiaxu Wei
 */
@Component(service = IndexReindexerRegistry.class)
public class IndexReindexerRegistry {

	public IndexReindexer getIndexReindexer(String className) {
		return _serviceTrackerMap.getService(className);
	}

	public Set<String> getIndexReindexerClassNames() {
		return _serviceTrackerMap.keySet();
	}

	public Collection<IndexReindexer> getIndexReindexers() {
		return _serviceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
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

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, IndexReindexer> _serviceTrackerMap;

}