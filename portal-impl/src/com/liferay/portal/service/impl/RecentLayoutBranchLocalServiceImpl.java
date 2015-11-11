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

import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.RecentLayoutBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.RecentLayoutBranchLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
@ProviderType
public class RecentLayoutBranchLocalServiceImpl
	extends RecentLayoutBranchLocalServiceBaseImpl {

	@Override
	public RecentLayoutBranch addRecentLayoutBranch(
		long companyId, long groupId, long userId, long layoutBranchId,
		long layoutSetBranchId, long plid) {

		long recentLayoutBranchId = counterLocalService.increment();

		RecentLayoutBranch recentLayoutBranch =
			recentLayoutBranchPersistence.create(recentLayoutBranchId);

		recentLayoutBranch.setGroupId(groupId);
		recentLayoutBranch.setCompanyId(companyId);
		recentLayoutBranch.setUserId(userId);
		recentLayoutBranch.setLayoutBranchId(layoutBranchId);
		recentLayoutBranch.setLayoutSetBranchId(layoutSetBranchId);
		recentLayoutBranch.setPlid(plid);

		return recentLayoutBranchPersistence.update(recentLayoutBranch);
	}

	@Override
	public void deleteRecentLayoutBranches(LayoutBranch layoutBranch) {
		recentLayoutBranchPersistence.removeByLayoutBranchId(
			layoutBranch.getLayoutBranchId());
	}

	@Override
	public void deleteRecentLayoutBranches(User user) {
		recentLayoutBranchPersistence.removeByUserId(user.getUserId());
	}

	@Override
	public RecentLayoutBranch fetchRecentLayoutBranch(
		long userId, long layoutSetBranchId, long plid) {

		return recentLayoutBranchPersistence.fetchByU_L_P(
			userId, layoutSetBranchId, plid);
	}

}