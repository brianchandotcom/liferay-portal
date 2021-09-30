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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.RecentLayoutBranch;
import com.liferay.portal.kernel.service.persistence.LayoutBranchPersistence;
import com.liferay.portal.service.base.RecentLayoutBranchLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
public class RecentLayoutBranchLocalServiceImpl
	extends RecentLayoutBranchLocalServiceBaseImpl {

	@Override
	public RecentLayoutBranch addRecentLayoutBranch(
			long userId, long layoutBranchId, long layoutSetBranchId, long plid)
		throws PortalException {

		LayoutBranch layoutBranch = _layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);

		RecentLayoutBranch recentLayoutBranch =
			recentLayoutBranchPersistence.create(
				counterLocalService.increment());

		recentLayoutBranch.setGroupId(layoutBranch.getGroupId());
		recentLayoutBranch.setCompanyId(layoutBranch.getCompanyId());
		recentLayoutBranch.setUserId(userId);
		recentLayoutBranch.setLayoutBranchId(layoutBranchId);
		recentLayoutBranch.setLayoutSetBranchId(layoutSetBranchId);
		recentLayoutBranch.setPlid(plid);

		return recentLayoutBranchPersistence.update(recentLayoutBranch);
	}

	@Override
	public void deleteRecentLayoutBranches(long layoutBranchId) {
		recentLayoutBranchPersistence.removeByLayoutBranchId(layoutBranchId);
	}

	@Override
	public void deleteUserRecentLayoutBranches(long userId) {
		recentLayoutBranchPersistence.removeByUserId(userId);
	}

	@Override
	public RecentLayoutBranch fetchRecentLayoutBranch(
		long userId, long layoutSetBranchId, long plid) {

		return recentLayoutBranchPersistence.fetchByU_L_P(
			userId, layoutSetBranchId, plid);
	}

	@BeanReference(type = LayoutBranchPersistence.class)
	private LayoutBranchPersistence _layoutBranchPersistence;

}