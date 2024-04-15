/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.asset;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.defaultpermissions.asset.PortalDefaultPermissionsAsset;
import com.liferay.portal.defaultpermissions.asset.PortalDefaultPermissionsAssetRegistry;
import com.liferay.portal.defaultpermissions.web.internal.asset.comparator.PortalDefaultPermissionsAssetOrderComparator;
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
@Component(service = PortalDefaultPermissionsAssetRegistry.class)
public class PortalDefaultPermissionsAssetRegistryImpl
	implements PortalDefaultPermissionsAssetRegistry {

	@Override
	public PortalDefaultPermissionsAsset getPortalDefaultPermissionsAsset(
		String key) {

		ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsAsset>
				portalDefaultPermissionsAssetServiceWrapper =
					_serviceTrackerMap.getService(key);

		if (portalDefaultPermissionsAssetServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No portal default permissions asset registered with key " +
						key);
			}

			return null;
		}

		return portalDefaultPermissionsAssetServiceWrapper.getService();
	}

	@Override
	public List<PortalDefaultPermissionsAsset>
		getPortalDefaultPermissionsAssets() {

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<PortalDefaultPermissionsAsset>>
					portalDefaultPermissionsAssetServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			portalDefaultPermissionsAssetServiceWrappers,
			_portalDefaultPermissionsAssetOrderComparator);

		return Collections.unmodifiableList(
			TransformUtil.transform(
				portalDefaultPermissionsAssetServiceWrappers,
				ServiceTrackerCustomizerFactory.ServiceWrapper::getService));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortalDefaultPermissionsAsset.class,
			"portal.default.permissions.asset.key",
			ServiceTrackerCustomizerFactory.
				<PortalDefaultPermissionsAsset>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalDefaultPermissionsAssetRegistryImpl.class);

	private final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<PortalDefaultPermissionsAsset>>
				_portalDefaultPermissionsAssetOrderComparator =
					new PortalDefaultPermissionsAssetOrderComparator();
	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <PortalDefaultPermissionsAsset>> _serviceTrackerMap;

}