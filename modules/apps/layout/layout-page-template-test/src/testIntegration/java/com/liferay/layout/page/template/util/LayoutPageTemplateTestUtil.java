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

package com.liferay.layout.page.template.util;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Kyle Miho
 */
public class LayoutPageTemplateTestUtil {

	public static LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return LayoutPageTemplateCollectionLocalServiceUtil.
			addLayoutPageTemplateCollection(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
	}

	public static LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			layoutPageTemplateCollectionId, RandomTestUtil.randomString());
	}

	public static LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId, String name)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionLocalServiceUtil.
				getLayoutPageTemplateCollection(layoutPageTemplateCollectionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				layoutPageTemplateCollection.getGroupId(),
				TestPropsValues.getUserId());

		return LayoutPageTemplateEntryLocalServiceUtil.
			addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(),
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollectionId, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
				WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

}