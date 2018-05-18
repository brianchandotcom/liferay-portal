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
@Component(immediate = true)
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