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

package com.liferay.roles.admin.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.roles.admin.demo.data.creator.RoleDemoDataCreator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseRoleDemoDataCreator implements RoleDemoDataCreator {

	public void addPermissions(Role role, String xml, int scope, String primKey)
		throws PortalException {

		try {
			Document document = SAXReaderUtil.read(xml);

			Element rootElement = document.getRootElement();

			List<Element> resources = rootElement.elements("resource");

			for (Element resource : resources) {
				String resourceName = resource.elementText("resource-name");

				List<Element> actionIds = resource.elements("action-id");

				for (Element actionId : actionIds) {
					addResourcePermission(
						role, resourceName, scope, primKey, actionId.getText());
				}
			}
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	public void addResourcePermission(
			Role role, String resourceName, int scope, String primKey,
			String actionId)
		throws PortalException {

		resourcePermissionLocalService.addResourcePermission(
			role.getCompanyId(), resourceName, scope, primKey, role.getRoleId(),
			actionId);
	}

	public Role createRole(long companyId, String roleName, int roleType)
		throws PortalException {

		Company company = companyLocalService.fetchCompany(companyId);

		User user = company.getDefaultUser();

		Role role = roleLocalService.addRole(
			user.getUserId(), null, 0, roleName, null, null, roleType, null,
			null);

		_roleIds.add(role.getRoleId());

		return role;
	}

	@Override
	public void delete() throws PortalException {
		try {
			for (long roleId : _roleIds) {
				_roleIds.remove(roleId);

				roleLocalService.deleteRole(roleId);
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
	}

	public void removeResourcePermission(
			Role role, String resourceName, int scope, String primKey,
			String actionId)
		throws PortalException {

		resourcePermissionLocalService.removeResourcePermission(
			role.getCompanyId(), resourceName, scope, primKey, role.getRoleId(),
			actionId);
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Reference
	protected RoleLocalService roleLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRoleDemoDataCreator.class);

	private final List<Long> _roleIds = new ArrayList<>();

}