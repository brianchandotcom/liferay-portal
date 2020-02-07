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

package com.liferay.depot.internal.instance.lifecycle;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class DepotRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	private static final String _DEPOT_ADMINISTRATOR = "Depot Administrator";

	private static final String _DEPOT_MEMBER = "Depot Member";

	private static final String _DEPOT_OWNER = "Depot Owner";

	private static final String[] _DEPOT_ROLES = {
		_DEPOT_ADMINISTRATOR, _DEPOT_MEMBER, _DEPOT_OWNER
	};

	@Override
	public void portalInstanceRegistered(Company company)
		throws PortalException {

		for (String name : _DEPOT_ROLES) {
			_checkSystemRole(
				company.getCompanyId(), name,
				Collections.singletonMap(
					LocaleUtil.getDefault(),
					StringBundler.concat(
						"system.depot.role.",
						StringUtil.replace(
							name, CharPool.SPACE, CharPool.PERIOD),
						".description")),
				RoleConstants.TYPE_DEPOT);
		}
	}

	private void _checkSystemRole(
			long companyId, String name, Map<Locale, String> descriptionMap,
			int type)
		throws PortalException {

		try {
			Role role = _roleLocalService.getRole(companyId, name);

			if (!Objects.equals(descriptionMap, role.getDescriptionMap())) {
				role.setDescriptionMap(descriptionMap);

				_roleLocalService.updateRole(role);
			}
		}
		catch (NoSuchRoleException noSuchRoleException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchRoleException, noSuchRoleException);
			}

			User user = _userLocalService.getDefaultUser(companyId);

			PermissionThreadLocal.setAddResource(false);

			try {
				_roleLocalService.addRole(
					user.getUserId(), null, 0, name, null, descriptionMap, type,
					null, null);
			}
			finally {
				PermissionThreadLocal.setAddResource(true);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotRolesPortalInstanceLifecycleListener.class);

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}