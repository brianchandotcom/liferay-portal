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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = LayoutRevisionUADTestHelper.class)
public class LayoutRevisionUADTestHelper {

	/**
	 * Implement addLayoutRevision() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid LayoutRevisions with a specified user ID in order to execute correctly. Implement addLayoutRevision() such that it creates a valid LayoutRevision with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public LayoutRevision addLayoutRevision(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.addLayoutSetBranch(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				false, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false,
				LayoutSetBranchConstants.ALL_BRANCHES, serviceContext);

		return _layoutRevisionLocalService.addLayoutRevision(
			userId, layoutSetBranch.getLayoutSetBranchId(), 0,
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			serviceContext.getPlid(), LayoutConstants.DEFAULT_PLID, false,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null, null, false, 0,
			layoutSetBranch.getThemeId(), layoutSetBranch.getColorSchemeId(),
			layoutSetBranch.getCss(), serviceContext);
	}

	/**
	 * Implement addLayoutRevisionWithStatusByUserId() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid LayoutRevisions with specified user ID and status by user ID in order to execute correctly. Implement addLayoutRevisionWithStatusByUserId() such that it creates a valid LayoutRevision with the specified user ID and status by user ID values and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public LayoutRevision addLayoutRevisionWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		LayoutRevision layoutRevision = addLayoutRevision(userId);

		User statusUser = _userLocalService.getUser(statusByUserId);

		layoutRevision.setStatusByUserId(statusUser.getUserId());
		layoutRevision.setStatusByUserName(statusUser.getFullName());

		return _layoutRevisionLocalService.updateLayoutRevision(layoutRevision);
	}

	/**
	 * Implement cleanUpDependencies(List<LayoutRevision> layoutRevisions) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid LayoutRevisions with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<LayoutRevision> layoutRevisions) such that any additional objects created during the construction of layoutRevisions are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(List<LayoutRevision> layoutRevisions)
		throws Exception {

		for (LayoutRevision layoutRevision : layoutRevisions) {
			_layoutSetBranchLocalService.deleteLayoutSetBranch(
				layoutRevision.getLayoutSetBranchId());
		}
	}

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private UserLocalService _userLocalService;

}