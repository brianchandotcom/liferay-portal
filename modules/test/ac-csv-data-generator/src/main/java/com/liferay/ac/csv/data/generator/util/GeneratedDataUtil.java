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

package com.liferay.ac.csv.data.generator.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.HashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = GeneratedDataUtil.class)
public class GeneratedDataUtil {

	public boolean containsOrganizationKey(String name) {
		return _addedOrganizationMap.containsKey(name);
	}

	public boolean containsRoleKey(String name) {
		return _addedRoleMap.containsKey(name);
	}

	public boolean containsUserGroupKey(String name) {
		return _addedUserGroupMap.containsKey(name);
	}

	public boolean containsUserKey(String emailAddress) {
		return _addedUserMap.containsKey(emailAddress);
	}

	public void deleteAll() {
		_addedUserMap.entrySet(
		).parallelStream(
		).forEach(
			e -> {
				try {
					_userLocalService.deleteUser(e.getValue());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
		);

		_addedOrganizationMap.entrySet(
		).parallelStream(
		).forEach(
			e -> {
				try {
					_organizationLocalService.deleteOrganization(e.getValue());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
		);

		_addedRoleMap.entrySet(
		).parallelStream(
		).forEach(
			e -> {
				try {
					_roleLocalService.deleteRole(e.getValue());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
		);

		try {
			_userGroupLocalService.deleteUserGroups(_companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getDefaultUserId() {
		return _defaultUserId;
	}

	public long getOrganization(String name) {
		return _addedOrganizationMap.get(name);
	}

	public long getRole(String name) {
		return _addedRoleMap.get(name);
	}

	public long getUserGroup(String name) {
		return _addedUserGroupMap.get(name);
	}

	public void putOrganization(String name, long primaryKey) {
		_addedOrganizationMap.put(name, primaryKey);
	}

	public void putRole(String name, long primaryKey) {
		_addedRoleMap.put(name, primaryKey);
	}

	public void putUser(String emailAddress, long primaryKey) {
		_addedUserMap.put(emailAddress, primaryKey);
	}

	public void putUserGroup(String name, long primaryKey) {
		_addedUserGroupMap.put(name, primaryKey);
	}

	public void setCompanyId(long companyId) throws PortalException {
		_companyId = companyId;

		_defaultUserId = _userLocalService.getDefaultUserId(_companyId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeneratedDataUtil.class);

	private volatile HashMap<String, Long> _addedOrganizationMap =
		new HashMap<>();
	private volatile HashMap<String, Long> _addedRoleMap = new HashMap<>();
	private volatile HashMap<String, Long> _addedUserGroupMap = new HashMap<>();
	private volatile HashMap<String, Long> _addedUserMap = new HashMap<>();
	private long _companyId;
	private long _defaultUserId;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}