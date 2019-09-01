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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;

import java.util.List;
import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ModelPreFilterContributorsHolder.class)
public class ModelPreFilterContributorsHolderImpl
	implements ModelPreFilterContributorsHolder {

	@Override
	public void forEach(
		String entryClassName, Consumer<ModelPreFilterContributor> consumer) {

		List<ModelPreFilterContributor> list = _serviceTrackerMap.getService(
			"ALL");

		if (list != null) {
			list.forEach(consumer);
		}

		list = _serviceTrackerMap.getService(entryClassName);

		if (list != null) {
			list.forEach(consumer);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ModelPreFilterContributor.class,
			"indexer.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<ModelPreFilterContributor>>
		_serviceTrackerMap;

}