/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.resource;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.defaultpermissions.web.internal.util.comparator.PortalDefaultPermissionsModelResourceComparator;
import com.liferay.portal.kernel.defaultpermissions.resource.PortalDefaultPermissionsModelResource;
import com.liferay.portal.kernel.defaultpermissions.resource.PortalDefaultPermissionsModelResourceRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Stefano Motta
 */
@Component(service = PortalDefaultPermissionsModelResourceRegistry.class)
public class PortalDefaultPermissionsModelResourceRegistryImpl
	implements PortalDefaultPermissionsModelResourceRegistry {

	@Override
	public PortalDefaultPermissionsModelResource
		getPortalDefaultPermissionsModelResource(String key) {

		ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsModelResource>
				portalDefaultPermissionsModelResourceServiceWrapper =
					_serviceTrackerMap.getService(key);

		if (portalDefaultPermissionsModelResourceServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No portal default permissions model resource registered " +
						"with key " + key);
			}

			return null;
		}

		return portalDefaultPermissionsModelResourceServiceWrapper.getService();
	}

	@Override
	public List<PortalDefaultPermissionsModelResource>
		getPortalDefaultPermissionsModelResources() {

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<PortalDefaultPermissionsModelResource>>
					portalDefaultPermissionsModelResourceServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			portalDefaultPermissionsModelResourceServiceWrappers,
			_portalDefaultPermissionsModelResourceComparator);

		return Collections.unmodifiableList(
			TransformUtil.transform(
				portalDefaultPermissionsModelResourceServiceWrappers,
				ServiceTrackerCustomizerFactory.ServiceWrapper::getService));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortalDefaultPermissionsModelResource.class,
			"portal.default.permissions.model.resource.key",
			ServiceTrackerCustomizerFactory.
				<PortalDefaultPermissionsModelResource>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalDefaultPermissionsModelResourceRegistryImpl.class);

	private final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsModelResource>>
				_portalDefaultPermissionsModelResourceComparator =
					new PortalDefaultPermissionsModelResourceComparator();
	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <PortalDefaultPermissionsModelResource>> _serviceTrackerMap;

}