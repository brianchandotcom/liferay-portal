/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order.status;

import com.liferay.commerce.internal.order.comparator.CommerceOrderStatusPriorityComparator;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.status.CommerceOrderStatus;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
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
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alec Sloan
 */
@Component(service = CommerceOrderStatusRegistry.class)
public class CommerceOrderStatusRegistryImpl
	implements CommerceOrderStatusRegistry {

	@Override
	public CommerceOrderStatus getCommerceOrderStatus(int key) {
		ServiceWrapper<CommerceOrderStatus> commerceOrderStatusServiceWrapper =
			_serviceTrackerMap.getService(String.valueOf(key));

		if (commerceOrderStatusServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No CommerceOrderStatus registered with key " + key);
			}

			return null;
		}

		return commerceOrderStatusServiceWrapper.getService();
	}

	@Override
	public List<CommerceOrderStatus> getCommerceOrderStatuses() {
		return getCommerceOrderStatuses(null);
	}

	@Override
	public List<CommerceOrderStatus> getCommerceOrderStatuses(
		CommerceOrder commerceOrder) {

		List<CommerceOrderStatus> commerceOrderStatuses = new ArrayList<>();

		List<ServiceWrapper<CommerceOrderStatus>>
			commerceOrderStatusServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceOrderStatusServiceWrappers,
			_commerceOrderStatusServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceOrderStatus>
				commerceOrderStatusServiceWrapper :
					commerceOrderStatusServiceWrappers) {

			CommerceOrderStatus commerceOrderStatus =
				commerceOrderStatusServiceWrapper.getService();

			try {
				if ((commerceOrder == null) ||
					commerceOrderStatus.isEnabled(commerceOrder)) {

					commerceOrderStatuses.add(
						commerceOrderStatusServiceWrapper.getService());
				}
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		return Collections.unmodifiableList(commerceOrderStatuses);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceOrderStatus.class,
			"commerce.order.status.key",
			ServiceTrackerCustomizerFactory.<CommerceOrderStatus>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderStatusRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceOrderStatus>>
		_commerceOrderStatusServiceWrapperOrderComparator =
			new CommerceOrderStatusPriorityComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CommerceOrderStatus>>
		_serviceTrackerMap;

}