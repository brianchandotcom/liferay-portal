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

package com.liferay.content.dashboard.web.internal.search.request;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemSearchClassMapperTracker.class)
public class ContentDashboardItemSearchClassMapperTracker {

	public String getClassName(String searchClassName) {
		Collection<ContentDashboardItemSearchClassMapper<?>>
			contentDashboardItemSearchClassMappers =
				_serviceTrackerMap.values();

		Stream<ContentDashboardItemSearchClassMapper<?>> stream =
			contentDashboardItemSearchClassMappers.stream();

		return stream.filter(
			contentDashboardItemSearchClassMapper -> Objects.equals(
				searchClassName,
				contentDashboardItemSearchClassMapper.getSearchClassName())
		).map(
			contentDashboardItemSearchClassMapper ->
				GenericUtil.getGenericClass(
					contentDashboardItemSearchClassMapper)
		).map(
			Class::getName
		).findFirst(
		).orElse(
			searchClassName
		);
	}

	public String getSearchClassName(String className) {
		return Optional.ofNullable(
			_serviceTrackerMap.getService(className)
		).map(
			ContentDashboardItemSearchClassMapper::getSearchClassName
		).orElse(
			className
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ContentDashboardItemSearchClassMapper.class,
				null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(contentDashboardItemSearchClassMapper, emitter) ->
						emitter.emit(
							GenericUtil.getGenericClassName(
								contentDashboardItemSearchClassMapper))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap
		<String, ContentDashboardItemSearchClassMapper<?>> _serviceTrackerMap;

}