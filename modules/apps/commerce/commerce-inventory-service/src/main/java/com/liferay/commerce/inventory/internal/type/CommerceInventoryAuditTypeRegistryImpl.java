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
@Component(immediate = true, service = CommerceInventoryAuditTypeRegistry.class)
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