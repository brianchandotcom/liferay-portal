/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.admin.web.internal.util;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceAdminModuleRegistry.class)
public class CommerceAdminModuleRegistry {

	public NavigableMap<String, CommerceAdminModule> getCommerceAdminModules() {
		NavigableMap<String, CommerceAdminModule> commerceAdminModules =
			new TreeMap<>();

		try {
			commerceAdminModules = getCommerceAdminModules(-1, -1);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return commerceAdminModules;
	}

	public NavigableMap<String, CommerceAdminModule> getCommerceAdminModules(
			long companyId, long groupId)
		throws PortalException {

		NavigableMap<String, CommerceAdminModule> commerceAdminModules =
			new TreeMap<>();

		for (String key : _commerceAdminModuleServiceTrackerMap.keySet()) {
			CommerceAdminModule commerceAdminModule =
				_commerceAdminModuleServiceTrackerMap.getService(key);

			if ((companyId < 0) || (groupId < 0) ||
				commerceAdminModule.isVisible(groupId)) {

				commerceAdminModules.put(key, commerceAdminModule);
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAdminModuleRegistry.class);

	private ServiceTrackerMap<String, CommerceAdminModule>
		_commerceAdminModuleServiceTrackerMap;

}