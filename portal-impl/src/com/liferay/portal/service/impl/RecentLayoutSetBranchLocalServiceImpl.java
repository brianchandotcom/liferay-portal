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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.RecentLayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.RecentLayoutSetBranchLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
@ProviderType
public class RecentLayoutSetBranchLocalServiceImpl
	extends RecentLayoutSetBranchLocalServiceBaseImpl {

	@Override
	public RecentLayoutSetBranch addRecentLayoutSetBranch(
		long companyId, long groupId, long userId, long layoutSetBranchId,
		long layoutSetId) {

		long recentLayoutSetBranchId = counterLocalService.increment();

		RecentLayoutSetBranch recentLayoutSetBranch =
			recentLayoutSetBranchPersistence.create(recentLayoutSetBranchId);

		recentLayoutSetBranch.setGroupId(groupId);
		recentLayoutSetBranch.setCompanyId(companyId);
		recentLayoutSetBranch.setUserId(userId);
		recentLayoutSetBranch.setLayoutSetBranchId(layoutSetBranchId);
		recentLayoutSetBranch.setLayoutSetId(layoutSetId);

		return recentLayoutSetBranchPersistence.update(recentLayoutSetBranch);
	}

	@Override
	public void deleteRecentLayoutSetBranches(LayoutSetBranch layoutSetBranch) {
		recentLayoutSetBranchPersistence.removeByLayoutSetBranchId(
			layoutSetBranch.getLayoutSetBranchId());
	}

	@Override
	public void deleteRecentLayoutSetBranches(User user) {
		recentLayoutSetBranchPersistence.removeByUserId(user.getUserId());
	}

	@Override
	public RecentLayoutSetBranch fetchRecentLayoutSetBranch(
		long userId, long layoutSetId) {

		return recentLayoutSetBranchPersistence.fetchByU_L(userId, layoutSetId);
	}

}