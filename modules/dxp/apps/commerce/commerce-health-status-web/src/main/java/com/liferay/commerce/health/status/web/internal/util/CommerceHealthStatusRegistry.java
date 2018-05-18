/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.health.status.web.internal.util;

import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.health.status.web.internal.util.comparator.CommerceHealthStatusServiceWrapperDisplayOrderComparator;
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
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceHealthStatusRegistry.class)
public class CommerceHealthStatusRegistry {

	public CommerceHealthStatus getCommerceHealthStatus(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<CommerceHealthStatus>
			commerceHealthStatusServiceWrapper =
				_commerceHealthStatusRegistryMap.getService(key);

		if (commerceHealthStatusServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce health status registered with key " + key);
			}

			return null;
		}

		return commerceHealthStatusServiceWrapper.getService();
	}

	public List<CommerceHealthStatus> getCommerceHealthStatuses() {
		List<CommerceHealthStatus> commerceHealthStatuses = new ArrayList<>();

		List<ServiceWrapper<CommerceHealthStatus>>
			commerceHealthStatusServiceWrappers = ListUtil.fromCollection(
				_commerceHealthStatusRegistryMap.values());

		Collections.sort(
			commerceHealthStatusServiceWrappers,
			_commerceHealthStatusServiceWrapperDisplayOrderComparator);

		for (ServiceWrapper<CommerceHealthStatus>
				commerceHealthStatusServiceWrapper :
					commerceHealthStatusServiceWrappers) {

			commerceHealthStatuses.add(
				commerceHealthStatusServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commerceHealthStatuses);
	}

	public Set<String> getCommerceHealthStatusKeys() {
		return _commerceHealthStatusRegistryMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceHealthStatusRegistryMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceHealthStatus.class,
				"commerce.health.status.key",
				ServiceTrackerCustomizerFactory.
					<CommerceHealthStatus>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_commerceHealthStatusRegistryMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceHealthStatusRegistry.class);

	private static final Comparator<ServiceWrapper<CommerceHealthStatus>>
		_commerceHealthStatusServiceWrapperDisplayOrderComparator =
			new CommerceHealthStatusServiceWrapperDisplayOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CommerceHealthStatus>>
		_commerceHealthStatusRegistryMap;

}