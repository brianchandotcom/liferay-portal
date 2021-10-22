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

package com.liferay.commerce.internal.order.importer.type;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
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
@Component(
	enabled = false, immediate = true,
	service = CommerceOrderImporterTypeRegistry.class
)
public class CommerceOrderImporterTypeRegistryImpl
	implements CommerceOrderImporterTypeRegistry {

	@Override
	public CommerceOrderImporterType getCommerceOrderImporterType(String key) {
		ServiceWrapper<CommerceOrderImporterType>
			commerceOrderImporterTypeServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceOrderImporterTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce order importer type registered with key " +
						key);
			}

			return null;
		}

		return commerceOrderImporterTypeServiceWrapper.getService();
	}

	@Override
	public List<CommerceOrderImporterType> getCommerceOrderImporterTypes(
			CommerceOrder commerceOrder)
		throws PortalException {

		List<CommerceOrderImporterType> commerceOrderImporterTypes =
			new ArrayList<>();

		List<ServiceWrapper<CommerceOrderImporterType>>
			commerceOrderImporterTypeServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		for (ServiceWrapper<CommerceOrderImporterType>
				commerceOrderImporterTypeServiceWrapper :
					commerceOrderImporterTypeServiceWrappers) {

			CommerceOrderImporterType commerceOrderImporterType =
				commerceOrderImporterTypeServiceWrapper.getService();

			if (commerceOrderImporterType.isActive(commerceOrder)) {
				commerceOrderImporterTypes.add(commerceOrderImporterType);
			}
		}

		return Collections.unmodifiableList(commerceOrderImporterTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceOrderImporterType.class,
			"commerce.order.importer.type.key",
			ServiceTrackerCustomizerFactory.
				<CommerceOrderImporterType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderImporterTypeRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<CommerceOrderImporterType>>
		_serviceTrackerMap;

}