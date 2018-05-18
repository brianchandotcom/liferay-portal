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

package com.liferay.commerce.admin.web.internal.util;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = CommerceAdminModuleRegistry.class)
public class CommerceAdminModuleRegistry {

	public NavigableMap<String, CommerceAdminModule> getCommerceAdminModules() {
		NavigableMap<String, CommerceAdminModule> commerceAdminModules =
			new TreeMap<>();

		for (String key : _commerceAdminModuleServiceTrackerMap.keySet()) {
			CommerceAdminModule commerceAdminModule =
				_commerceAdminModuleServiceTrackerMap.getService(key);

			commerceAdminModules.put(key, commerceAdminModule);
		}

		return Collections.unmodifiableNavigableMap(commerceAdminModules);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_commerceAdminModuleServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, CommerceAdminModule.class,
				"commerce.admin.module.key");
	}

	@Deactivate
	protected void deactivate() {
		_commerceAdminModuleServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, CommerceAdminModule>
		_commerceAdminModuleServiceTrackerMap;

}