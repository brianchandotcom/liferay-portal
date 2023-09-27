/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.uad.test;

import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.service.LayoutBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutBranchUADTestUtil {

	public static LayoutBranch addLayoutBranch(
			LayoutBranchLocalService layoutBranchLocalService,
			LayoutSetBranchLocalService layoutSetBranchLocalService,
			long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchLocalService.addLayoutSetBranch(
				userId, TestPropsValues.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, LayoutSetBranchConstants.ALL_BRANCHES, serviceContext);

		serviceContext.setUserId(userId);

		return layoutBranchLocalService.addLayoutBranch(
			layoutSetBranch.getLayoutSetBranchId(), serviceContext.getPlid(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

	public static void cleanUpDependencies(
			LayoutSetBranchLocalService layoutSetBranchLocalService,
			List<LayoutBranch> layoutBranchs)
		throws Exception {

		for (LayoutBranch layoutBranch : layoutBranchs) {
			layoutSetBranchLocalService.deleteLayoutSetBranch(
				layoutBranch.getLayoutSetBranchId());
		}
	}

}