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

package com.liferay.roles.admin.web.internal.role.type.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.roles.admin.web.internal.role.type.RoleType;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = RoleTypeProvider.class)
public class RoleTypeProviderImpl implements RoleTypeProvider {

	@Override
	public RoleType getRoleType(int type) {
		return _roleTypeServiceTrackerMap.getService(type);
	}

	@Override
	public List<RoleType> getRoleTypes() {
		return ListUtil.fromCollection(_roleTypeServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_roleTypeServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, RoleType.class, null,
				(serviceReference, emitter) -> {
					RoleType roleType = _bundleContext.getService(
						serviceReference);

					emitter.emit(roleType.getType());
				});
	}

	@Deactivate
	protected void deactivate() {
		_roleTypeServiceTrackerMap.close();
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<Integer, RoleType> _roleTypeServiceTrackerMap;

}