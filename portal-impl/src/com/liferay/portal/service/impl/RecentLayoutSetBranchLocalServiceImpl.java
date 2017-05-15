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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.RecentLayoutSetBranch;
import com.liferay.portal.service.base.RecentLayoutSetBranchLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
public class RecentLayoutSetBranchLocalServiceImpl
	extends RecentLayoutSetBranchLocalServiceBaseImpl {

	@Override
	public RecentLayoutSetBranch addRecentLayoutSetBranch(
			long userId, long layoutSetBranchId, long layoutSetId)
		throws PortalException {

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		RecentLayoutSetBranch recentLayoutSetBranch =
			recentLayoutSetBranchPersistence.create(
				counterLocalService.increment());

		recentLayoutSetBranch.setGroupId(layoutSetBranch.getGroupId());
		recentLayoutSetBranch.setCompanyId(layoutSetBranch.getCompanyId());
		recentLayoutSetBranch.setUserId(userId);
		recentLayoutSetBranch.setLayoutSetBranchId(layoutSetBranchId);
		recentLayoutSetBranch.setLayoutSetId(layoutSetId);

		return recentLayoutSetBranchPersistence.update(recentLayoutSetBranch);
	}

	@Override
	public void deleteRecentLayoutSetBranches(long layoutSetBranchId) {
		recentLayoutSetBranchPersistence.removeByLayoutSetBranchId(
			layoutSetBranchId);
	}

	@Override
	public void deleteUserRecentLayoutSetBranches(long userId) {
		recentLayoutSetBranchPersistence.removeByUserId(userId);
	}

	@Override
	public RecentLayoutSetBranch fetchRecentLayoutSetBranch(
		long userId, long layoutSetId) {

		return recentLayoutSetBranchPersistence.fetchByU_L(userId, layoutSetId);
	}

}