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

import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = QueryPreFilterContributorsHolder.class)
public class QueryPreFilterContributorsHolderImpl
	implements QueryPreFilterContributorsHolder {

	@Override
	public void forEach(Consumer<QueryPreFilterContributor> consumer) {
		_serviceTrackerList.forEach(consumer);
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

	private ServiceTrackerList
		<QueryPreFilterContributor, QueryPreFilterContributor>
			_serviceTrackerList;

}