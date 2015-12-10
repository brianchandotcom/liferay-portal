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

import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.RecentLayoutRevision;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.RecentLayoutRevisionLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Preston Crary
 */
@ProviderType
public class RecentLayoutRevisionLocalServiceImpl
	extends RecentLayoutRevisionLocalServiceBaseImpl {

	@Override
	public RecentLayoutRevision addRecentLayoutRevision(
		long companyId, long groupId, long userId, long layoutRevisionId,
		long layoutSetBranchId, long plid) {

		long recentLayoutRevisionId = counterLocalService.increment();

		RecentLayoutRevision recentLayoutRevision =
			recentLayoutRevisionPersistence.create(recentLayoutRevisionId);

		recentLayoutRevision.setGroupId(groupId);
		recentLayoutRevision.setCompanyId(companyId);
		recentLayoutRevision.setUserId(userId);
		recentLayoutRevision.setLayoutRevisionId(layoutRevisionId);
		recentLayoutRevision.setLayoutSetBranchId(layoutSetBranchId);
		recentLayoutRevision.setPlid(plid);

		return recentLayoutRevisionPersistence.update(recentLayoutRevision);
	}

	@Override
	public void deleteRecentLayoutRevisions(LayoutRevision layoutRevision) {
		recentLayoutRevisionPersistence.removeByLayoutRevisionId(
			layoutRevision.getLayoutRevisionId());
	}

	@Override
	public void deleteRecentLayoutRevisions(User user) {
		recentLayoutRevisionPersistence.removeByUserId(user.getUserId());
	}

	@Override
	public RecentLayoutRevision fetchRecentLayoutRevision(
		long userId, long layoutSetBranchId, long plid) {

		return recentLayoutRevisionPersistence.fetchByU_L_P(
			userId, layoutSetBranchId, plid);
	}

}