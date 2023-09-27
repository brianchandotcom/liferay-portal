/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceTaxEngineRegistry.class)
public class CommerceTaxEngineRegistryImpl
	implements CommerceTaxEngineRegistry {

	@Override
	public CommerceTaxEngine getCommerceTaxEngine(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, CommerceTaxEngine> getCommerceTaxEngines() {
		Map<String, CommerceTaxEngine> commerceTaxEngines = new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			commerceTaxEngines.put(key, _serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableMap(commerceTaxEngines);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceTaxEngine.class, "commerce.tax.engine.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceTaxEngine> _serviceTrackerMap;

}