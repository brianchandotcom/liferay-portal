/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseRoleDemoDataCreator implements RoleDemoDataCreator {

	public void addPermissions(
			Role role, String permissionsXML, int scope, String primKey)
		throws PortalException {

		try {
			Document document = SAXReaderUtil.read(permissionsXML);

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
		catch (Exception exception) {
			throw new PortalException(exception);
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

		User user = company.getGuestUser();

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
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
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

	private final List<Long> _roleIds = new CopyOnWriteArrayList<>();

}