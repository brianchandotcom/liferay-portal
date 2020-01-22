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

package com.liferay.depot.service.impl;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.base.DepotEntryGroupRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.permission.GroupPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=depot",
		"json.web.service.context.path=DepotEntryGroupRel"
	},
	service = AopService.class
)
public class DepotEntryGroupRelServiceImpl
	extends DepotEntryGroupRelServiceBaseImpl {

	@Override
	public DepotEntryGroupRel addDepotEntryGroupRel(
			long depotEntryId, long toGroupId)
		throws PortalException {

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryId, ActionKeys.UPDATE);

		return depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntryId, toGroupId);
	}

	@Override
	public DepotEntryGroupRel deleteDepotEntryGroupRel(
			long depotEntryGroupRelId)
		throws PortalException {

		DepotEntryGroupRel depotEntryGroupRel =
			depotEntryGroupRelLocalService.getDepotEntryGroupRel(
				depotEntryGroupRelId);

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryGroupRel.getDepotEntryId(),
			ActionKeys.UPDATE);

		return depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	@Override
	public List<DepotEntryGroupRel> getDepotEntryGroupRels(
			long groupId, int start, int end)
		throws PortalException {

		_groupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return depotEntryGroupRelLocalService.getDepotEntryGroupRels(
			groupId, start, end);
	}

	@Override
	public int getDepotEntryGroupRelsCount(long groupId)
		throws PortalException {

		_groupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW);

		return depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount(
			groupId);
	}

	@Override
	public DepotEntryGroupRel updateSearchable(
			long depotEntryGroupRelId, boolean searchable)
		throws PortalException {

		DepotEntryGroupRel depotEntryGroupRel =
			depotEntryGroupRelLocalService.getDepotEntryGroupRel(
				depotEntryGroupRelId);

		_depotEntryModelResourcePermission.check(
			getPermissionChecker(), depotEntryGroupRel.getDepotEntryId(),
			ActionKeys.UPDATE);

		return depotEntryGroupRelLocalService.updateSearchable(
			depotEntryGroupRelId, searchable);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.depot.model.DepotEntry)"
	)
	private volatile ModelResourcePermission<DepotEntry>
		_depotEntryModelResourcePermission;

	@Reference
	private GroupPermission _groupPermission;

}