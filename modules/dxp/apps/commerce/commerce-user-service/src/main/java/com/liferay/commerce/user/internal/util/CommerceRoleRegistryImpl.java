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

package com.liferay.commerce.user.internal.util;

import com.liferay.commerce.user.util.CommerceRole;
import com.liferay.commerce.user.util.CommerceRoleRegistry;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceRoleRegistry.class)
public class CommerceRoleRegistryImpl implements CommerceRoleRegistry {

	@Override
	public List<Role> getRoles(long companyId) {
		List<Role> roles = new ArrayList<>();

		for (CommerceRole commerceRole : _serviceTrackerList) {
			Role role = _roleLocalService.fetchRole(
				companyId, commerceRole.getRoleName());

			if (role != null) {
				roles.add(role);
			}
		}

		return roles;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, CommerceRole.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	@Reference
	private RoleLocalService _roleLocalService;

	private ServiceTrackerList<CommerceRole, CommerceRole> _serviceTrackerList;

}