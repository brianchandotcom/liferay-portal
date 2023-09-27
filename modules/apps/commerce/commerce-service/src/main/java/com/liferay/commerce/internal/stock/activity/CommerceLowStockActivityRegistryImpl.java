/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.stock.activity;

import com.liferay.commerce.internal.stock.activity.comparator.CommerceLowStockActivityServiceWrapperPriorityComparator;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

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
@Component(service = CommerceLowStockActivityRegistry.class)
public class CommerceLowStockActivityRegistryImpl
	implements CommerceLowStockActivityRegistry {

	@Override
	public List<CommerceLowStockActivity> getCommerceLowStockActivities() {
		List<CommerceLowStockActivity> commerceLowStockActivities =
			new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceLowStockActivity>>
					commerceLowStockActivityServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceLowStockActivityServiceWrappers,
			_commerceLowStockActivityServiceWrapperPriorityComparator);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceLowStockActivity>
					commerceLowStockActivityServiceWrapper :
						commerceLowStockActivityServiceWrappers) {

			commerceLowStockActivities.add(
				commerceLowStockActivityServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceLowStockActivities);
	}

	@Override
	public CommerceLowStockActivity getCommerceLowStockActivity(
		CPDefinitionInventory cpDefinitionInventory) {

		if (cpDefinitionInventory == null) {
			return null;
		}

		return getCommerceLowStockActivity(
			cpDefinitionInventory.getLowStockActivity());
	}

	@Override
	public CommerceLowStockActivity getCommerceLowStockActivity(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceTrackerCustomizerFactory.ServiceWrapper<CommerceLowStockActivity>
			commerceLowStockActivityServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceLowStockActivityServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce low stock activity registered with key " +
						key);
			}

			return null;
		}

		return commerceLowStockActivityServiceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceLowStockActivity.class,
			"commerce.low.stock.activity.key",
			ServiceTrackerCustomizerFactory.
				<CommerceLowStockActivity>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceLowStockActivityRegistryImpl.class);

	private static final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<CommerceLowStockActivity>>
				_commerceLowStockActivityServiceWrapperPriorityComparator =
					new CommerceLowStockActivityServiceWrapperPriorityComparator();

	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <CommerceLowStockActivity>> _serviceTrackerMap;

}