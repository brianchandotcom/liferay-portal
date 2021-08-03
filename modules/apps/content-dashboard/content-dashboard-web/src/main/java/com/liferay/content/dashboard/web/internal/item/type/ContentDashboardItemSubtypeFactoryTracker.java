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

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemSubtypeFactoryTracker.class)
public class ContentDashboardItemSubtypeFactoryTracker {

	public Collection<String> getClassNames() {
		return Collections.unmodifiableCollection(_serviceTrackerMap.keySet());
	}

	public Optional<ContentDashboardItemSubtypeFactory>
		getContentDashboardItemSubtypeFactoryOptional(String className) {

		return Optional.ofNullable(_serviceTrackerMap.getService(className));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ContentDashboardItemSubtypeFactory.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(contentDashboardItem, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(contentDashboardItem))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap
		<String, ContentDashboardItemSubtypeFactory> _serviceTrackerMap;

}