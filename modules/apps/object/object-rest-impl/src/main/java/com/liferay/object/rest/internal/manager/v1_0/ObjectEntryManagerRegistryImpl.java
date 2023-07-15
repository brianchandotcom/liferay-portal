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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Guilherme Camacho
 */
@Component(service = ObjectEntryManagerRegistry.class)
public class ObjectEntryManagerRegistryImpl
	implements ObjectEntryManagerRegistry {

	@Override
	public ObjectEntryManager getObjectEntryManager(String storageType) {
		return _serviceTrackerMap.getService(storageType);
	}

	@Override
	public List<ObjectEntryManager> getObjectEntryManagers(long companyId) {
		return ListUtil.filter(
			ListUtil.fromCollection(_serviceTrackerMap.values()),
			objectEntryManager -> {
				boolean allowed = true;

				if (objectEntryManager instanceof CompanyScoped) {
					CompanyScoped objectEntryManagerCompanyScoped =
						(CompanyScoped)objectEntryManager;

					allowed = objectEntryManagerCompanyScoped.isAllowedCompany(
						companyId);
				}

				return allowed;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ObjectEntryManager.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext, ObjectEntryManager::getStorageType));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ObjectEntryManager> _serviceTrackerMap;

}