/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.test.util;

import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
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

import java.util.Objects;

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
				null, TestPropsValues.getUserId(), groupId,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(), StringPool.BLANK,
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				serviceContext);
	}

	public static LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			layoutPageTemplateCollectionId, RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.BASIC,
			WorkflowConstants.STATUS_DRAFT);
	}

	public static LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, int type, int status)
		throws PortalException {

		long layoutPageTemplateCollectionId =
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;

		if (Objects.equals(LayoutPageTemplateEntryTypeConstants.BASIC, type) ||
			Objects.equals(
				LayoutPageTemplateEntryTypeConstants.WIDGET_PAGE, type)) {

			LayoutPageTemplateCollection layoutPageTemplateCollection =
				addLayoutPageTemplateCollection(groupId);

			layoutPageTemplateCollectionId =
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
		}

		return LayoutPageTemplateEntryLocalServiceUtil.
			addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), groupId,
				layoutPageTemplateCollectionId, RandomTestUtil.randomString(),
				type, 0, status,
				ServiceContextTestUtil.getServiceContext(
					groupId, TestPropsValues.getUserId()));
	}

	public static LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId, String name)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			layoutPageTemplateCollectionId, name,
			LayoutPageTemplateEntryTypeConstants.BASIC,
			WorkflowConstants.STATUS_DRAFT);
	}

	public static LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long layoutPageTemplateCollectionId, String name, int type,
			int status)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionLocalServiceUtil.
				getLayoutPageTemplateCollection(layoutPageTemplateCollectionId);

		return LayoutPageTemplateEntryLocalServiceUtil.
			addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(),
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollectionId, name, type, 0, status,
				ServiceContextTestUtil.getServiceContext(
					layoutPageTemplateCollection.getGroupId(),
					TestPropsValues.getUserId()));
	}

}