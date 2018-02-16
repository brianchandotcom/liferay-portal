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

package com.liferay.portal.search.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = RelatedEntryIndexerRegistry.class)
public class RelatedEntryIndexerRegistryImpl
	implements RelatedEntryIndexerRegistry {

	@Override
	public List<RelatedEntryIndexer> getRelatedEntryIndexers() {
		Collection<List<RelatedEntryIndexer>> relatedEntryIndexersLists =
			serviceTrackerMap.values();

		List<RelatedEntryIndexer> relatedEntryIndexers = new ArrayList<>();

		for (List<RelatedEntryIndexer> relatedEntryIndexersList :
				relatedEntryIndexersLists) {

			relatedEntryIndexers.addAll(relatedEntryIndexersList);
		}

		return relatedEntryIndexers;
	}

	@Override
	public List<RelatedEntryIndexer> getRelatedEntryIndexers(Class clazz) {
		return serviceTrackerMap.getService(clazz.getName());
	}

	@Override
	public List<RelatedEntryIndexer> getRelatedEntryIndexers(String className) {
		return serviceTrackerMap.getService(className);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, RelatedEntryIndexer.class,
			"related.entry.indexer.class.name");
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		serviceTrackerMap.close();
	}

	protected ServiceTrackerMap<String, List<RelatedEntryIndexer>>
		serviceTrackerMap;

}