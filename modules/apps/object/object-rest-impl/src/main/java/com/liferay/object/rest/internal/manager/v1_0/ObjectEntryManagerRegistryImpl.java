/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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