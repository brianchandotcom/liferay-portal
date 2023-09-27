/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.test.util;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Kyle Miho
 */
public class DisplayPageTemplateTestUtil {

	public static LayoutPageTemplateEntry addDisplayPageTemplate(long groupId)
		throws PortalException {

		return addDisplayPageTemplate(groupId, RandomTestUtil.randomString());
	}

	public static LayoutPageTemplateEntry addDisplayPageTemplate(
			long groupId, String name)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return LayoutPageTemplateEntryLocalServiceUtil.
			addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), groupId, 0, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

}