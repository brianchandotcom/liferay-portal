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

package com.liferay.portal.search.internal.searcher;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author André de Oliveira
 */
@Component(service = SearchRequestContributorsRegistry.class)
public class SearchRequestContributorsRegistryImpl
	implements SearchRequestContributorsRegistry {

	@Override
	public Stream<SearchRequestContributor> stream(
		Collection<String> includeIds, Collection<String> excludeIds) {

		Collection<SearchRequestContributor> collection = _include(includeIds);

		_exclude(collection, excludeIds);

		return collection.stream();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SearchRequestContributor.class,
			"search.request.contributor.id");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _exclude(
		Collection<SearchRequestContributor> collection,
		Collection<String> ids) {

		for (String id : ids) {
			List<SearchRequestContributor> searchRequestContributors =
				_serviceTrackerMap.getService(id);

			if (Objects.nonNull(searchRequestContributors)) {
				collection.removeAll(searchRequestContributors);
			}
		}
	}

	private Collection<SearchRequestContributor> _include(
		Collection<String> ids) {

		if ((ids == null) || ids.isEmpty()) {
			Collection<SearchRequestContributor> searchRequestContributors =
				new HashSet<>();

			Collection<List<SearchRequestContributor>>
				searchRequestContributorLists = _serviceTrackerMap.values();

			searchRequestContributorLists.forEach(
				searchRequestContributorList ->
					searchRequestContributors.addAll(
						searchRequestContributorList));

			return searchRequestContributors;
		}

		Collection<SearchRequestContributor> collection = new ArrayList<>();

		for (String id : ids) {
			collection.addAll(_serviceTrackerMap.getService(id));
		}

		return collection;
	}

	private ServiceTrackerMap<String, List<SearchRequestContributor>>
		_serviceTrackerMap;

}