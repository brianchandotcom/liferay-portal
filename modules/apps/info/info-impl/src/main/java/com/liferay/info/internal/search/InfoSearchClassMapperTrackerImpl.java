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

package com.liferay.info.internal.search;

import com.liferay.info.search.InfoSearchClassMapper;
import com.liferay.info.search.InfoSearchClassMapperTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.Collection;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = InfoSearchClassMapperTracker.class)
public class InfoSearchClassMapperTrackerImpl
	implements InfoSearchClassMapperTracker {

	@Override
	public String getClassName(String searchClassName) {
		Collection<InfoSearchClassMapper<?>> infoSearchClassMappers =
			_serviceTrackerMap.values();

		for (InfoSearchClassMapper<?> infoSearchClassMapper :
				infoSearchClassMappers) {

			if (Objects.equals(
					searchClassName,
					infoSearchClassMapper.getSearchClassName())) {

				Class<?> genericClass = GenericUtil.getGenericClass(
					infoSearchClassMapper);

				return genericClass.getName();
			}
		}

		return searchClassName;
	}

	@Override
	public String getSearchClassName(String className) {
		InfoSearchClassMapper<?> infoSearchClassMapper =
			_serviceTrackerMap.getService(className);

		if (infoSearchClassMapper == null) {
			return className;
		}

		return infoSearchClassMapper.getSearchClassName();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoSearchClassMapper.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(infoSearchClassMapper, emitter) -> emitter.emit(
						GenericUtil.getGenericClassName(
							infoSearchClassMapper))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap<String, InfoSearchClassMapper<?>>
		_serviceTrackerMap;

}