/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Julio Camarero
 */
public class LayoutBranchLocalServiceImpl
	extends LayoutBranchLocalServiceBaseImpl {

	public LayoutBranch addLayoutBranch(
			long layoutSetBranchId, long plid, String name, String description,
			boolean master, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(serviceContext.getUserId());
		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		long layoutBranchId = counterLocalService.increment();

		LayoutBranch layoutBranch = layoutBranchPersistence.create(
			layoutBranchId);

		layoutBranch.setGroupId(layoutSetBranch.getGroupId());
		layoutBranch.setCompanyId(user.getCompanyId());
		layoutBranch.setUserId(user.getUserId());
		layoutBranch.setUserName(user.getFullName());
		layoutBranch.setLayoutSetBranchId(layoutSetBranchId);
		layoutBranch.setPlid(plid);
		layoutBranch.setName(name);
		layoutBranch.setDescription(description);
		layoutBranch.setMaster(master);

		layoutBranchPersistence.update(layoutBranch, false);

		return layoutBranch;
	}

	public LayoutBranch addLayoutBranch(
			long layoutRevisionId, String name, String description,
			boolean master, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutRevision layoutRevision =
			layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		LayoutBranch layoutBranch = addLayoutBranch(
			layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(),
			name, description, master, serviceContext);

		layoutRevisionService.addLayoutRevision(
			layoutBranch.getUserId(), layoutRevision.getLayoutSetBranchId(),
			layoutBranch.getLayoutBranchId(),
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			layoutRevision.getPlid(), layoutRevision.isPrivateLayout(),
			layoutRevision.getName(), layoutRevision.getTitle(),
			layoutRevision.getDescription(), layoutRevision.getKeywords(),
			layoutRevision.getRobots(), layoutRevision.getTypeSettings(),
			layoutRevision.getIconImage(), layoutRevision.getIconImageId(),
			layoutRevision.getThemeId(), layoutRevision.getColorSchemeId(),
			layoutRevision.getWapThemeId(),
			layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layoutBranch;
	}

	@Override
	public void deleteLayoutBranch(long layoutBranchId)
		throws PortalException, SystemException {

		LayoutBranch layoutBranch =
			layoutBranchPersistence.findByPrimaryKey(layoutBranchId);

		layoutRevisionLocalService.deleteLayoutRevisions(
			layoutBranch.getLayoutSetBranchId(), layoutBranchId,
			layoutBranch.getPlid());

		layoutBranchLocalService.deleteLayoutBranch(layoutBranch);
	}

	public void deleteLayoutSetBranchLayoutBranches(long layoutSetBranchId)
		throws PortalException, SystemException {

		List<LayoutBranch> layoutBranches =
			layoutBranchPersistence.findByLayoutSetBranchId(layoutSetBranchId);

		for (LayoutBranch layoutBranch : layoutBranches) {
			deleteLayoutBranch(layoutBranch.getLayoutBranchId());
		}
	}

	@Override
	public LayoutBranch getLayoutBranch(long layoutBranchId)
		throws PortalException, SystemException {

		return layoutBranchPersistence.findByPrimaryKey(layoutBranchId);
	}

	public List<LayoutBranch> getLayoutBranches(
			long layoutSetBranchId, long plid, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return layoutBranchPersistence.findByL_P(
			layoutSetBranchId, plid, start, end, orderByComparator);
	}

	public List<LayoutBranch> getLayoutSetBranchLayoutBranches(
			long layoutSetBranchId)
		throws SystemException {

		return layoutBranchPersistence.findByLayoutSetBranchId(
			layoutSetBranchId);
	}

	public LayoutBranch getMasterLayoutBranch(long layoutSetBranchId, long plid)
		throws PortalException, SystemException {

		return layoutBranchPersistence.findByL_P_M(
			layoutSetBranchId, plid, true);
	}

	public LayoutBranch updateLayoutBranch(
			long layoutBranchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranch layoutBranch =
			layoutBranchPersistence.findByPrimaryKey(layoutBranchId);

		layoutBranch.setName(name);
		layoutBranch.setDescription(description);

		layoutBranchPersistence.update(layoutBranch, false);

		return layoutBranch;
	}

}