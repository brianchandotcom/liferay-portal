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

package com.liferay.message.boards.uad.test;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return mbCategoryLocalService.addCategory(
			userId, parentMBCategoryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
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