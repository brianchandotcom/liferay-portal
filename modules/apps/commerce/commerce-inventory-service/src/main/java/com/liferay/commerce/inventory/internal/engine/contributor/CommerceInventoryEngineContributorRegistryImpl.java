/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.engine.contributor;

import com.liferay.commerce.inventory.engine.contributor.CommerceInventoryEngineContributor;
import com.liferay.commerce.inventory.engine.contributor.CommerceInventoryEngineContributorRegistry;
import com.liferay.commerce.inventory.internal.engine.contributor.comparator.CommerceInventoryEngineContributorOrderComparator;
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
@Component(service = CommerceInventoryEngineContributorRegistry.class)
public class CommerceInventoryEngineContributorRegistryImpl
	implements CommerceInventoryEngineContributorRegistry {

	@Override
	public CommerceInventoryEngineContributor
		getCommerceInventoryEngineContributor(String key) {

		ServiceTrackerCustomizerFactory.ServiceWrapper
			<CommerceInventoryEngineContributor>
				commerceInventoryEngineContributorServiceWrapper =
					_serviceTrackerMap.getService(key);

		if (commerceInventoryEngineContributorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce inventory engine contributor registered " +
						"with key " + key);
			}

			return null;
		}

		return commerceInventoryEngineContributorServiceWrapper.getService();
	}

	@Override
	public List<CommerceInventoryEngineContributor>
		getCommerceInventoryEngineContributors() {

		List<CommerceInventoryEngineContributor>
			commerceInventoryEngineContributors = new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceInventoryEngineContributor>>
					commerceInventoryEngineContributorServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceInventoryEngineContributorServiceWrappers,
			_commerceInventoryEngineContributorServiceWrapperOrderComparator);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommerceInventoryEngineContributor>
					commerceInventoryEngineContributorServiceWrapper :
						commerceInventoryEngineContributorServiceWrappers) {

			commerceInventoryEngineContributors.add(
				commerceInventoryEngineContributorServiceWrapper.getService());
		}

		return Collections.unmodifiableList(
			commerceInventoryEngineContributors);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceInventoryEngineContributor.class,
			"commerce.inventory.engine.contributor.key",
			ServiceTrackerCustomizerFactory.
				<CommerceInventoryEngineContributor>serviceWrapper(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryEngineContributorRegistryImpl.class);

	private final Comparator
		<ServiceTrackerCustomizerFactory.ServiceWrapper
			<CommerceInventoryEngineContributor>>
				_commerceInventoryEngineContributorServiceWrapperOrderComparator =
					new CommerceInventoryEngineContributorOrderComparator();
	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <CommerceInventoryEngineContributor>> _serviceTrackerMap;

}