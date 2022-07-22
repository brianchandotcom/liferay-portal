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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFactoryTracker.class)
public class ContentDashboardItemFactoryTracker {

	public Collection<Long> getClassIds() {
		Collection<String> classNames = getClassNames();

		Stream<String> stream = classNames.stream();

		return stream.map(
			_classNameLocalService::getClassNameId
		).collect(
			Collectors.toSet()
		);
	}

	public Collection<String> getClassNames() {
		return Collections.unmodifiableCollection(_serviceTrackerMap.keySet());
	}

	public ContentDashboardItemFactory<?> getContentDashboardItemFactory(
		String className) {

		return _serviceTrackerMap.getService(className);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ContentDashboardItemFactory.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(contentDashboardItem, emitter) -> emitter.emit(
						GenericUtil.getGenericClassName(
							contentDashboardItem))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile ServiceTrackerMap<String, ContentDashboardItemFactory<?>>
		_serviceTrackerMap;

}