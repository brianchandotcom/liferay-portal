/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
	public ObjectEntryManager getObjectEntryManager(
		long companyId, String storageType) {

		ObjectEntryManager objectEntryManager = _serviceTrackerMap.getService(
			storageType);

		if (objectEntryManager == null) {
			objectEntryManager = _serviceTrackerMap.getService(
				_getCompanyScopedKey(storageType, companyId));
		}

		if (objectEntryManager == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"No object entry manager found with company ID ",
						companyId, " and storage type ", storageType));
			}
		}

		return objectEntryManager;
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
			(serviceReference, emitter) -> {
				try {
					ObjectEntryManager objectEntryManager =
						bundleContext.getService(serviceReference);

					String key = objectEntryManager.getStorageType();

					if (objectEntryManager instanceof CompanyScoped) {
						CompanyScoped objectEntryManagerCompanyScoped =
							(CompanyScoped)objectEntryManager;

						key = _getCompanyScopedKey(
							key,
							objectEntryManagerCompanyScoped.
								getAllowedCompanyId());
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Registering object entry manager with key ",
								key, " and class ",
								objectEntryManager.getClass()));
					}

					emitter.emit(key);
				}
				catch (Exception exception) {
					_log.error("Unable to get object entry manager", exception);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getCompanyScopedKey(String key, long company) {
		return StringBundler.concat(key, StringPool.POUND, company);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryManagerRegistryImpl.class);

	private ServiceTrackerMap<String, ObjectEntryManager> _serviceTrackerMap;

}