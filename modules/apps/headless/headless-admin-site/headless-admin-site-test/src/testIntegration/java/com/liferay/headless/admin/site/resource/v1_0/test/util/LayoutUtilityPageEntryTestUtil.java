/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutUtilityPageEntryTestUtil {

	public static LayoutUtilityPageEntry getLayoutUtilityPageEntry(
			ServiceContext serviceContext)
		throws Exception {

		return LayoutUtilityPageEntryLocalServiceUtil.addLayoutUtilityPageEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			0, 0, false, RandomTestUtil.randomString(),
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR, 0,
			serviceContext);
	}

	public static Layout getLayoutUtilityPageEntryLayout(
			ServiceContext serviceContext)
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			getLayoutUtilityPageEntry(serviceContext);

		return LayoutLocalServiceUtil.getLayout(
			layoutUtilityPageEntry.getPlid());
	}

}