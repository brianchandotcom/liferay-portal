/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.starter;

import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.commerce.starter.CommerceRegionsStarterRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(service = CommerceRegionsStarterRegistry.class)
public class CommerceRegionsStarterRegistryImpl
	implements CommerceRegionsStarterRegistry {

	@Override
	public CommerceRegionsStarter getCommerceRegionsStarter(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<CommerceRegionsStarter> getCommerceRegionsStarters() {
		List<CommerceRegionsStarter> commerceRegionsStarters =
			new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceRegionsStarters.add(_serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableList(commerceRegionsStarters);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceRegionsStarter.class,
			"commerce.region.starter.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceRegionsStarter>
		_serviceTrackerMap;

}