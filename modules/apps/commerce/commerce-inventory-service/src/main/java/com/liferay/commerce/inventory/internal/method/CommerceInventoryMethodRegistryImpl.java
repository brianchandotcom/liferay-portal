/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.method;

import com.liferay.commerce.inventory.internal.method.comparator.CommerceInventoryMethodOrderComparator;
import com.liferay.commerce.inventory.method.CommerceInventoryMethod;
import com.liferay.commerce.inventory.method.CommerceInventoryMethodRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
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
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceInventoryMethodRegistry.class)
public class CommerceInventoryMethodRegistryImpl
	implements CommerceInventoryMethodRegistry {

	@Override
	public CommerceInventoryMethod getCommerceInventoryMethod(String key) {
		ServiceTrackerCustomizerFactory.ServiceWrapper<CommerceInventoryMethod>
			commerceInventoryMethodServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceInventoryMethodServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce inventory method registered with key " + key);
			}

			return null;
		}

		return commerceInventoryMethodServiceWrapper.getService();
	}

	@Override
	public List<CommerceInventoryMethod> getCommerceInventoryMethods() {
		List<CommerceInventoryMethod> commerceInventoryMethods =
			new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceInventoryMethod>>
					commerceInventoryMethodServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceInventoryMethodServiceWrappers,
			_commerceInventoryMethodServiceWrapperOrderComparator);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceInventoryMethod>
					commerceInventoryMethodServiceWrapper :
						commerceInventoryMethodServiceWrappers) {

			commerceInventoryMethods.add(
				commerceInventoryMethodServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceInventoryMethods);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceInventoryMethod.class,
			"commerce.inventory.method.key",
			ServiceTrackerCustomizerFactory.
				<CommerceInventoryMethod>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryMethodRegistryImpl.class);

	private final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<CommerceInventoryMethod>>
				_commerceInventoryMethodServiceWrapperOrderComparator =
					new CommerceInventoryMethodOrderComparator();
	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <CommerceInventoryMethod>> _serviceTrackerMap;

}