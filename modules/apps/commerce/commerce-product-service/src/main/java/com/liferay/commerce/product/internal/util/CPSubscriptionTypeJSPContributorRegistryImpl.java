/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.util.CPSubscriptionTypeJSPContributor;
import com.liferay.commerce.product.util.CPSubscriptionTypeJSPContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

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
@Component(service = CPSubscriptionTypeJSPContributorRegistry.class)
public class CPSubscriptionTypeJSPContributorRegistryImpl
	implements CPSubscriptionTypeJSPContributorRegistry {

	@Override
	public CPSubscriptionTypeJSPContributor getCPSubscriptionTypeJSPContributor(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<CPSubscriptionTypeJSPContributor>
		getCPSubscriptionTypeJSPContributors() {

		List<CPSubscriptionTypeJSPContributor>
			cpSubscriptionTypeJSPContributors = new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			cpSubscriptionTypeJSPContributors.add(
				_serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableList(cpSubscriptionTypeJSPContributors);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPSubscriptionTypeJSPContributor.class,
			"commerce.product.subscription.type.jsp.contributor.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CPSubscriptionTypeJSPContributor>
		_serviceTrackerMap;

}