/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.health.status;

import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.internal.health.status.comparator.CommerceChannelHealthStatusServiceWrapperDisplayOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
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
@Component(service = CommerceChannelHealthStatusRegistry.class)
public class CommerceChannelHealthStatusRegistryImpl
	implements CommerceChannelHealthStatusRegistry {

	@Override
	public CommerceChannelHealthStatus getCommerceChannelHealthStatus(
		String key) {

		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceChannelHealthStatus>
			commerceChannelHealthStatusServiceWrapper =
				_serviceTrackerMap.getService(key);

		if (commerceChannelHealthStatusServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce health status registered with key " + key);
			}

			return null;
		}

		return commerceChannelHealthStatusServiceWrapper.getService();
	}

	@Override
	public List<CommerceChannelHealthStatus>
		getCommerceChannelHealthStatuses() {

		List<CommerceChannelHealthStatus> commerceChannelHealthStatuses =
			new ArrayList<>();

		List<ServiceWrapper<CommerceChannelHealthStatus>>
			commerceChannelHealthStatusServiceWrappers =
				ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			commerceChannelHealthStatusServiceWrappers,
			_commerceChannelHealthStatusServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceChannelHealthStatus>
				commerceChannelHealthStatusServiceWrapper :
					commerceChannelHealthStatusServiceWrappers) {

			commerceChannelHealthStatuses.add(
				commerceChannelHealthStatusServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceChannelHealthStatuses);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceChannelHealthStatus.class,
			"commerce.channel.health.status.key",
			ServiceTrackerCustomizerFactory.
				<CommerceChannelHealthStatus>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelHealthStatusRegistryImpl.class);

	private static final Comparator<ServiceWrapper<CommerceChannelHealthStatus>>
		_commerceChannelHealthStatusServiceWrapperDisplayOrderComparator =
			new CommerceChannelHealthStatusServiceWrapperDisplayOrderComparator();

	private ServiceTrackerMap
		<String, ServiceWrapper<CommerceChannelHealthStatus>>
			_serviceTrackerMap;

}