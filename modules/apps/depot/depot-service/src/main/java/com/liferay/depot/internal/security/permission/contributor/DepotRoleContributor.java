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

package com.liferay.depot.internal.security.permission.contributor;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.contributor.RoleCollection;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = RoleContributor.class)
public class DepotRoleContributor implements RoleContributor {

	@Override
	public void contribute(RoleCollection roleCollection) {
		try {
			if (roleCollection.getGroupId() <= 0) {
				return;
			}

			Group group = _groupLocalService.getGroup(
				roleCollection.getGroupId());

			if (!group.isDepot()) {
				return;
			}

			UserBag userBag = roleCollection.getUserBag();

			if (userBag.hasUserGroup(group)) {
				_addRoleId(
					roleCollection, DepotRolesConstants.ASSET_LIBRARY_MEMBER);
			}

			List<DepotEntryGroupRel> depotEntryGroupRels =
				_depotEntryGroupRelLocalService.getDepotEntryGroupRels(
					_depotEntryLocalService.getGroupDepotEntry(
						group.getGroupId()));

			for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
				if (userBag.hasUserGroup(
						_groupLocalService.getGroup(
							depotEntryGroupRel.getToGroupId()))) {

					_addRoleId(
						roleCollection,
						DepotRolesConstants.
							ASSET_LIBRARY_CONNECTED_SITE_MEMBER);

					break;
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private void _addRoleId(RoleCollection roleCollection, String roleName)
		throws PortalException {

		Role role = _roleLocalService.getRole(
			roleCollection.getCompanyId(), roleName);

		roleCollection.addRoleId(role.getRoleId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotRoleContributor.class);

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}