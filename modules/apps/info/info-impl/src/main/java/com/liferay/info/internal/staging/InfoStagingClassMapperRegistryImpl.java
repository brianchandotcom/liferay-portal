/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.staging;

import com.liferay.info.internal.util.ItemClassNameServiceReferenceMapper;
import com.liferay.info.staging.InfoStagingClassMapper;
import com.liferay.info.staging.InfoStagingClassMapperRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collection;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoStagingClassMapperRegistry.class)
public class InfoStagingClassMapperRegistryImpl
	implements InfoStagingClassMapperRegistry {

	@Override
	public String getClassName(String stagingClassName) {
		Collection<InfoStagingClassMapper<?>> infoStagingClassMappers =
			_serviceTrackerMap.values();

		for (InfoStagingClassMapper<?> infoStagingClassMapper :
				infoStagingClassMappers) {

			if (Objects.equals(
					stagingClassName,
					infoStagingClassMapper.getStagingClassName())) {

				return infoStagingClassMapper.getClassName();
			}
		}

		return stagingClassName;
	}

	@Override
	public String getStagingClassName(String className) {
		InfoStagingClassMapper<?> infoStagingClassMapper =
			_serviceTrackerMap.getService(className);

		if (infoStagingClassMapper == null) {
			return className;
		}

		return infoStagingClassMapper.getStagingClassName();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, InfoStagingClassMapper.class, null,
				new ItemClassNameServiceReferenceMapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private BundleContext _bundleContext;
	private volatile ServiceTrackerMap<String, InfoStagingClassMapper<?>>
		_serviceTrackerMap;

}