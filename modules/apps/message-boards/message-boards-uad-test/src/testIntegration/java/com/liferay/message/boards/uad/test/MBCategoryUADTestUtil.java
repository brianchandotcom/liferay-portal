/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.uad.test;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Brian Wing Shun Chan
 */
public class MBCategoryUADTestUtil {

	public static MBCategory addMBCategory(
			MBCategoryLocalService mbCategoryLocalService, long userId)
		throws Exception {

		return addMBCategory(mbCategoryLocalService, userId, 0L);
	}

	public static MBCategory addMBCategory(
			MBCategoryLocalService mbCategoryLocalService, long userId,
			long parentMBCategoryId)
		throws Exception {

		return mbCategoryLocalService.addCategory(
			userId, parentMBCategoryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	public static MBCategory addMBCategoryWithStatusByUserId(
			MBCategoryLocalService mbCategoryLocalService, long userId,
			long statusByUserId)
		throws Exception {

		MBCategory mbCategory = addMBCategory(mbCategoryLocalService, userId);

		return mbCategoryLocalService.updateStatus(
			statusByUserId, mbCategory.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

}