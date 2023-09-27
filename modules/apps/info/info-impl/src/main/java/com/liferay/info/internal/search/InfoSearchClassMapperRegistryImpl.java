/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.search;

import com.liferay.info.search.InfoSearchClassMapper;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
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
@Component(service = InfoSearchClassMapperRegistry.class)
public class InfoSearchClassMapperRegistryImpl
	implements InfoSearchClassMapperRegistry {

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