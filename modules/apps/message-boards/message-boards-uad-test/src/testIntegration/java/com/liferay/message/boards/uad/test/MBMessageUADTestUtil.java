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

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageUADTestUtil {

	public static MBMessage addMBMessage(
			MBCategoryLocalService mbCategoryLocalService,
			MBMessageLocalService mbMessageLocalService, long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		MBCategory mbCategory = mbCategoryLocalService.addCategory(
			userId, 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return addMBMessage(
			mbMessageLocalService, userId, mbCategory.getCategoryId());
	}

	public static MBMessage addMBMessage(
			MBMessageLocalService mbMessageLocalService, long userId,
			long mbCategoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return mbMessageLocalService.addMessage(
			userId, RandomTestUtil.randomString(), TestPropsValues.getGroupId(),
			mbCategoryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	public static MBMessage addMBMessage(
			MBMessageLocalService mbMessageLocalService, long userId,
			long mbCategoryId, long mbThreadId)
		throws Exception {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return mbMessageLocalService.addMessage(
			userId, RandomTestUtil.randomString(), TestPropsValues.getGroupId(),
			mbCategoryId, mbThreadId, 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), MBMessageConstants.DEFAULT_FORMAT,
			inputStreamOVPs, false, 0.0, false, serviceContext);
	}

	public static MBMessage addMBMessageWithStatusByUserId(
			MBCategoryLocalService mbCategoryLocalService,
			MBMessageLocalService mbMessageLocalService, long userId,
			long statusByUserId)
		throws Exception {

		MBMessage mbMessage = addMBMessage(
			mbCategoryLocalService, mbMessageLocalService, userId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");

		return mbMessageLocalService.updateStatus(
			statusByUserId, mbMessage.getMessageId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);
	}

	public static void cleanUpDependencies(
			MBCategoryLocalService mbCategoryLocalService,
			List<MBMessage> mbMessages)
		throws Exception {

		for (MBMessage mbMessage : mbMessages) {
			long mbCategoryId = mbMessage.getCategoryId();

			if (mbCategoryId !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				mbCategoryLocalService.deleteCategory(mbCategoryId);
			}
		}
	}

}