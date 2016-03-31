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

package com.liferay.social.privatemessaging.upgrade.v1_0_1;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Sherry Yang
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	public UpgradeResourcePermission(
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService,
		RepositoryLocalService repositoryLocalService,
		ResourceLocalService resourceLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
		_repositoryLocalService = repositoryLocalService;
		_resourceLocalService = resourceLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			Group group = _groupLocalService.getCompanyGroup(
				company.getCompanyId());

			_upgradeDLFolderResourcePermission(company, group);
			_upgradeDLResourcePermission(company, group);
		}
	}

	private void _upgradeDLFolderResourcePermission(
			Company company, Group group)
		throws PortalException {

		Repository repository = _repositoryLocalService.fetchRepository(
			group.getGroupId(), MBPortletKeys.MESSAGE_BOARDS);

		if (repository == null) {
			return;
		}

		long folderId = repository.getDlFolderId();

		Role role = _roleLocalService.getRole(
			company.getCompanyId(), RoleConstants.GUEST);

		if (_resourcePermissionLocalService.hasResourcePermission(
				company.getCompanyId(), DLFolder.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(folderId),
				role.getRoleId(), ActionKeys.VIEW)) {

			return;
		}

		_resourceLocalService.addResources(
			company.getCompanyId(), group.getGroupId(), 0,
			DLFolder.class.getName(), folderId, false, true, true);
	}

	private void _upgradeDLResourcePermission(Company company, Group group)
		throws PortalException {

		int count = _resourcePermissionLocalService.getResourcePermissionsCount(
			company.getCompanyId(), _DL_RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(group.getGroupId()));

		if (count > 0) {
			return;
		}

		_resourceLocalService.addResources(
			company.getCompanyId(), group.getGroupId(), 0, _DL_RESOURCE_NAME,
			group.getGroupId(), false, true, true);
	}

	private static final String _DL_RESOURCE_NAME =
		"com.liferay.document.library";

	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;
	private final RepositoryLocalService _repositoryLocalService;
	private final ResourceLocalService _resourceLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}