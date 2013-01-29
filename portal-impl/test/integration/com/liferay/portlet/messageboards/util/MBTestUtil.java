/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

/**
 * @author Eudaldo Alonso
 */
public class MBTestUtil {

	public static MBCategory addMBCategory(ServiceContext serviceContext)
		throws Exception {

		return MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	public static MBMessage addMBMessage(
			long categoryId, String keywords, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			categoryId, keywords, keywords, serviceContext);

		if (!approved) {
			message = MBMessageLocalServiceUtil.updateStatus(
				message.getStatusByUserId(), message.getMessageId(),
				WorkflowConstants.STATUS_DRAFT, serviceContext);
		}

		return message;
	}

}