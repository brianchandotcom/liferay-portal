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

package com.liferay.portal.search.test;

import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class TestRelatedEntryIndexerRegistry
	implements RelatedEntryIndexerRegistry {

	public TestRelatedEntryIndexerRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			RelatedEntryIndexer.class,
			new RelatedEntryIndexerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		_serviceTracker = null;
	}

	@Override
	public List<RelatedEntryIndexer> getRelatedEntryIndexers() {
		List<RelatedEntryIndexer> relatedEntryIndexers = new ArrayList<>();

		for (List<RelatedEntryIndexer> currentRelatedEntryIndexers :
				_relatedEntryIndexers.values()) {

			relatedEntryIndexers.addAll(currentRelatedEntryIndexers);
		}

		return relatedEntryIndexers;
	}

	@Override
	public List<RelatedEntryIndexer> getRelatedEntryIndexers(Class clazz) {
		return getRelatedEntryIndexers(clazz.getName());
	}

	@Override
	public List<RelatedEntryIndexer> getRelatedEntryIndexers(String className) {
		List<RelatedEntryIndexer> relatedEntryIndexers =
			_relatedEntryIndexers.get(className);

		if (relatedEntryIndexers == null) {
			relatedEntryIndexers = Collections.emptyList();
		}

		return relatedEntryIndexers;
	}

	private final Map<String, List<RelatedEntryIndexer>> _relatedEntryIndexers =
		new ConcurrentHashMap<>();
	private ServiceTracker<RelatedEntryIndexer, RelatedEntryIndexer>
		_serviceTracker;

	private class RelatedEntryIndexerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RelatedEntryIndexer, RelatedEntryIndexer> {

		@Override
		public RelatedEntryIndexer addingService(
			ServiceReference<RelatedEntryIndexer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			RelatedEntryIndexer relatedEntryIndexer = registry.getService(
				serviceReference);

			String relatedEntryIndexerClassName =
				(String)serviceReference.getProperty(
					"related.entry.indexer.class.name");

			if (Validator.isNull(relatedEntryIndexerClassName)) {
				throw new IllegalStateException(
					"Service must contain a related.entry.indexer.class.name");
			}

			List<RelatedEntryIndexer> relatedEntryIndexers =
				_relatedEntryIndexers.get(relatedEntryIndexerClassName);

			if (relatedEntryIndexers == null) {
				relatedEntryIndexers = new ArrayList<>();

				_relatedEntryIndexers.put(
					relatedEntryIndexerClassName, relatedEntryIndexers);
			}

			relatedEntryIndexers.add(relatedEntryIndexer);

			return relatedEntryIndexer;
		}

		@Override
		public void modifiedService(
			ServiceReference<RelatedEntryIndexer> serviceReference,
			RelatedEntryIndexer relatedEntryIndexer) {
		}

		@Override
		public void removedService(
			ServiceReference<RelatedEntryIndexer> serviceReference,
			RelatedEntryIndexer relatedEntryIndexer) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String relatedEntryIndexerClassName =
				(String)serviceReference.getProperty(
					"related.entry.indexer.class.name");

			List<RelatedEntryIndexer> relatedEntryIndexers =
				_relatedEntryIndexers.get(relatedEntryIndexerClassName);

			relatedEntryIndexers.remove(relatedEntryIndexer);
		}

	}

}