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

package com.liferay.portal.search.internal.indexer;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Spliterator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author André de Oliveira
 */
@Component(service = QueryPreFilterContributorsRegistry.class)
public class QueryPreFilterContributorsRegistryImpl
	implements QueryPreFilterContributorsRegistry {

	@Override
	public List<QueryPreFilterContributor> filterQueryPreFilterContributor(
		Collection<String> excludes, Collection<String> includes) {

		List<QueryPreFilterContributor> queryPreFilterContributors =
			new ArrayList<>();

		Spliterator<QueryPreFilterContributor> spliterator =
			_serviceTrackerList.spliterator();

		spliterator.forEachRemaining(queryPreFilterContributors::add);

		return IncludeExcludeUtil.filter(
			queryPreFilterContributors, includes, excludes,
			object -> getClassName(object));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, QueryPreFilterContributor.class,
			"(!(indexer.class.name=*))");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	protected String getClassName(Object object) {
		Class<?> clazz = object.getClass();

		return clazz.getName();
	}

	private ServiceTrackerList<QueryPreFilterContributor> _serviceTrackerList;

}