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

package com.liferay.portal.module.framework.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class ModuleFrameworkTestUtil {

	public static <T> Collection<Long> getBundleIds(
			Class<T> clazz, String filter)
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Collection<Long> bundleIds = new ArrayList<>();

		Collection<ServiceReference<T>> serviceReferences =
			registry.getServiceReferences(clazz, filter);

		for (ServiceReference<T> serviceReference : serviceReferences) {
			Long bundleId = (Long)serviceReference.getProperty(
				"service.bundleid");

			bundleIds.add(bundleId);
		}

		return bundleIds;
	}

	public static void startBundles(Iterable<Long> bundleIds) {
		for (long bundleId : bundleIds) {
			try {
				ModuleFrameworkUtilAdapter.startBundle(bundleId);
			}
			catch (Exception e) {
				_log.error("Unable to start bundle " + bundleId, e);
			}
		}
	}

	public static void stopBundles(Iterable<Long> bundleIds)
		throws PortalException {

		for (long bundleId : bundleIds) {
			ModuleFrameworkUtilAdapter.stopBundle(bundleId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleFrameworkTestUtil.class);

}