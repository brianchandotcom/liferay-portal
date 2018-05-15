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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = LayoutBranchUADTestHelper.class)
public class LayoutBranchUADTestHelper {

	public LayoutBranch addLayoutBranch(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.addLayoutSetBranch(
				userId, TestPropsValues.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, LayoutSetBranchConstants.ALL_BRANCHES, serviceContext);

		serviceContext.setUserId(userId);

		return _layoutBranchLocalService.addLayoutBranch(
			layoutSetBranch.getLayoutSetBranchId(), serviceContext.getPlid(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

	public void cleanUpDependencies(List<LayoutBranch> layoutBranchs)
		throws Exception {

		for (LayoutBranch layoutBranch : layoutBranchs) {
			_layoutSetBranchLocalService.deleteLayoutSetBranch(
				layoutBranch.getLayoutSetBranchId());
		}
	}

	@Reference
	private LayoutBranchLocalService _layoutBranchLocalService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

}