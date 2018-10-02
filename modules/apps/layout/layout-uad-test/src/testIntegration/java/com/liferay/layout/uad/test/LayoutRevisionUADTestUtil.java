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

package com.liferay.layout.uad.test;

import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutRevisionConstants;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionUADTestUtil {

	public static LayoutRevision addLayoutRevision(
			LayoutRevisionLocalService layoutRevisionLocalService,
			LayoutSetBranchLocalService layoutSetBranchLocalService,
			long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchLocalService.addLayoutSetBranch(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				false, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false,
				LayoutSetBranchConstants.ALL_BRANCHES, serviceContext);

		return layoutRevisionLocalService.addLayoutRevision(
			userId, layoutSetBranch.getLayoutSetBranchId(), 0,
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			serviceContext.getPlid(), LayoutConstants.DEFAULT_PLID, false,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null, false, 0,
			layoutSetBranch.getThemeId(), layoutSetBranch.getColorSchemeId(),
			layoutSetBranch.getCss(), serviceContext);
	}

	public static LayoutRevision addLayoutRevisionWithStatusByUserId(
			LayoutRevisionLocalService layoutRevisionLocalService,
			LayoutSetBranchLocalService layoutSetBranchLocalService,
			UserLocalService userLocalService, long userId, long statusByUserId)
		throws Exception {

		LayoutRevision layoutRevision = addLayoutRevision(
			layoutRevisionLocalService, layoutSetBranchLocalService, userId);

		User statusUser = userLocalService.getUser(statusByUserId);

		layoutRevision.setStatusByUserId(statusUser.getUserId());
		layoutRevision.setStatusByUserName(statusUser.getFullName());

		return layoutRevisionLocalService.updateLayoutRevision(layoutRevision);
	}

	public static void cleanUpDependencies(
			LayoutSetBranchLocalService layoutSetBranchLocalService,
			List<LayoutRevision> layoutRevisions)
		throws Exception {

		for (LayoutRevision layoutRevision : layoutRevisions) {
			layoutSetBranchLocalService.deleteLayoutSetBranch(
				layoutRevision.getLayoutSetBranchId());
		}
	}

}