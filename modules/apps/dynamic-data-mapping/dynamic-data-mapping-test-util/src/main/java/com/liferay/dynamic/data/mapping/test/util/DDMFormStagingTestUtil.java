/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Victor Ware
 */
public class DDMFormStagingTestUtil {

	public static void enableLocalStaging(
			Group group, boolean enableFormsStaging)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		addStagingAttribute(
			serviceContext,
			StagingUtil.getStagedPortletId(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM),
			enableFormsStaging);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL, false);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);
	}

	public static void enableRemoteStaging(
			Group remoteLiveGroup, Group remoteStagingGroup)
		throws Exception {

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET",
					"F0E1D2C3B4A5968778695A4B3C2D1E0F");
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET_HEX", true)) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setScopeGroupId(remoteStagingGroup.getGroupId());

			addStagingAttribute(
				serviceContext,
				StagingUtil.getStagedPortletId(
					DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM),
				true);
			addStagingAttribute(
				serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, false);
			addStagingAttribute(
				serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL,
				false);

			UserTestUtil.setUser(TestPropsValues.getUser());

			StagingLocalServiceUtil.enableRemoteStaging(
				TestPropsValues.getUserId(), remoteStagingGroup, false, false,
				"localhost", PortalUtil.getPortalServerPort(false),
				PortalUtil.getPathContext(), false,
				remoteLiveGroup.getGroupId(), serviceContext);

			GroupUtil.clearCache();
		}
	}

	protected static void addStagingAttribute(
		ServiceContext serviceContext, String key, Object value) {

		serviceContext.setAttribute(
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
			String.valueOf(value));
	}

}