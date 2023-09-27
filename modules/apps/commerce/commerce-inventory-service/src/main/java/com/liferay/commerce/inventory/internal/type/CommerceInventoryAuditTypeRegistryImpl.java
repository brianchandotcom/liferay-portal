/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.type;

import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceInventoryAuditTypeRegistry.class)
public class CommerceInventoryAuditTypeRegistryImpl
	implements CommerceInventoryAuditTypeRegistry {

	@Override
	public CommerceInventoryAuditType getCommerceInventoryAuditType(
		String key) {

		ServiceWrapper<CommerceInventoryAuditType>
			commerceChannelTypeServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (commerceChannelTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceInventoryAuditType registered with key " + key);
			}

			return null;
		}

		return commerceChannelTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceInventoryAuditType> getCommerceInventoryAuditTypes() {
		List<CommerceInventoryAuditType> commerceInventoryAuditTypes =
			new ArrayList<>();

		List<ServiceWrapper<CommerceInventoryAuditType>>
			commerceInventoryAuditTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		for (ServiceWrapper<CommerceInventoryAuditType>
				commerceInventoryAuditTypeServiceWrapper :
					commerceInventoryAuditTypeServiceWrappers) {

			commerceInventoryAuditTypes.add(
				commerceInventoryAuditTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceInventoryAuditTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceInventoryAuditType.class,
			"commerce.inventory.audit.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceInventoryAuditType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryAuditTypeRegistryImpl.class);

	private ServiceTrackerMap
		<String, ServiceWrapper<CommerceInventoryAuditType>> _serviceTrackerMap;

}