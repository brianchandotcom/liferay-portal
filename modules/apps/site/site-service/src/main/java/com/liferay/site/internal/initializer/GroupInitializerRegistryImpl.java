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

package com.liferay.site.internal.initializer;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.initializer.GroupInitializer;
import com.liferay.site.initializer.GroupInitializerRegistry;

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
@Component(immediate = true)
public class GroupInitializerRegistryImpl implements GroupInitializerRegistry {

	public GroupInitializer getGroupInitializer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<GroupInitializer> serviceWrapper =
			_serviceTrackerMap.getService(key);

		if (serviceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No group initializer registered with key " + key);
			}

			return null;
		}

		return serviceWrapper.getService();
	}

	public List<GroupInitializer> getGroupInitializers(long companyId) {
		return getGroupInitializers(companyId, false);
	}

	public List<GroupInitializer> getGroupInitializers(
		long companyId, boolean active) {

		List<GroupInitializer> groupInitializers = new ArrayList<>();

		List<ServiceWrapper<GroupInitializer>> serviceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		for (ServiceWrapper<GroupInitializer> serviceWrapper :
				serviceWrappers) {

			GroupInitializer groupInitializer = serviceWrapper.getService();

			if (!active || (active && groupInitializer.isActive(companyId))) {
				groupInitializers.add(groupInitializer);
			}
		}

		return Collections.unmodifiableList(groupInitializers);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, GroupInitializer.class, "group.initializer.key",
			ServiceTrackerCustomizerFactory.<GroupInitializer>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupInitializerRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<GroupInitializer>>
		_serviceTrackerMap;

}