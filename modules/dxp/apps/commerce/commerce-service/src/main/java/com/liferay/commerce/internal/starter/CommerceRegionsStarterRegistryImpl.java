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
@Component(immediate = true)
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