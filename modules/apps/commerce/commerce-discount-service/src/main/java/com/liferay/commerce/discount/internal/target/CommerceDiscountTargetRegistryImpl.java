/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.internal.target.comparator.CommerceDiscountTargetOrderComparator;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
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
@Component(service = CommerceDiscountTargetRegistry.class)
public class CommerceDiscountTargetRegistryImpl
	implements CommerceDiscountTargetRegistry {

	@Override
	public CommerceDiscountTarget getCommerceDiscountTarget(String key) {
		ServiceWrapper<CommerceDiscountTarget>
			commerceDiscountTargetServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceDiscountTargetServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommerceDiscountTarget registered with key " + key);
			}

			return null;
		}

		return commerceDiscountTargetServiceWrapper.getService();
	}

	@Override
	public List<CommerceDiscountTarget> getCommerceDiscountTargets() {
		List<CommerceDiscountTarget> commerceDiscountTargets =
			new ArrayList<>();

		List<ServiceWrapper<CommerceDiscountTarget>>
			commerceDiscountTargetServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			commerceDiscountTargetServiceWrappers,
			_commerceDiscountTargetServiceWrapperOrderComparator);

		for (ServiceWrapper<CommerceDiscountTarget>
				commerceDiscountTargetServiceWrapper :
					commerceDiscountTargetServiceWrappers) {

			commerceDiscountTargets.add(
				commerceDiscountTargetServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceDiscountTargets);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceDiscountTarget.class,
			"commerce.discount.target.key",
			ServiceTrackerCustomizerFactory.
				<CommerceDiscountTarget>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountTargetRegistryImpl.class);

	private final Comparator<ServiceWrapper<CommerceDiscountTarget>>
		_commerceDiscountTargetServiceWrapperOrderComparator =
			new CommerceDiscountTargetOrderComparator();
	private ServiceTrackerMap<String, ServiceWrapper<CommerceDiscountTarget>>
		_serviceTrackerMap;

}