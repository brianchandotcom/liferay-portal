/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.test.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Map;

/**
 * @author Alicia Garcia
 */
public class DepotStagingTestUtil {

	public static void disableRemoteStaging(DepotEntry depotEntry)
		throws PortalException {

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET",
					"F0E1D2C3B4A5968778695A4B3C2D1E0F");
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET_HEX", true)) {

			Group group = depotEntry.getGroup();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			serviceContext.setAttribute("forceDisable", Boolean.TRUE);

			StagingLocalServiceUtil.disableStaging(null, group, serviceContext);
		}
	}

	public static void disableStaging(DepotEntry depotEntry)
		throws PortalException {

		disableStaging(depotEntry.getGroup());
	}

	public static void disableStaging(Group group) throws PortalException {
		StagingLocalServiceUtil.disableStaging(
			group,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	public static DepotEntry enableLocalStaging(DepotEntry depotEntry)
		throws Exception {

		Group stagingGroup = enableLocalStaging(depotEntry.getGroup());

		return DepotEntryLocalServiceUtil.fetchGroupDepotEntry(
			stagingGroup.getGroupId());
	}

	public static Group enableLocalStaging(Group group) throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_addStagingAttributes(serviceContext);

		serviceContext.setAttribute("staging", Boolean.TRUE);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		return group.getStagingGroup();
	}

	public static DepotEntry enableRemoteStaging(
			DepotEntry remoteLiveDepotEntry, DepotEntry remoteStagingDepotEntry)
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
			serviceContext.setScopeGroupId(
				remoteStagingDepotEntry.getGroupId());

			_addStagingAttribute(
				serviceContext,
				StagingUtil.getStagedPortletId(_DEPOT_ADMIN_PORTLET_ID), true);
			_addStagingAttribute(
				serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, false);
			_addStagingAttribute(
				serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL,
				false);

			UserTestUtil.setUser(TestPropsValues.getUser());

			StagingLocalServiceUtil.enableRemoteStaging(
				TestPropsValues.getUserId(), remoteStagingDepotEntry.getGroup(),
				false, false, "localhost",
				PortalUtil.getPortalServerPort(false),
				PortalUtil.getPathContext(), false,
				remoteLiveDepotEntry.getGroupId(), serviceContext);

			Group remoteLiveDepotEntryGroup = remoteLiveDepotEntry.getGroup();

			UnicodeProperties unicodeProperties =
				remoteLiveDepotEntryGroup.getTypeSettingsProperties();

			unicodeProperties.setProperty(
				"stagedRemotely", Boolean.TRUE.toString());

			GroupLocalServiceUtil.updateGroup(
				remoteLiveDepotEntryGroup.getGroupId(),
				unicodeProperties.toString());

			GroupUtil.clearCache();

			return DepotEntryLocalServiceUtil.fetchGroupDepotEntry(
				remoteLiveDepotEntryGroup.getGroupId());
		}
	}

	public static void publishLayouts(Group stagingGroup, Group liveGroup)
		throws PortalException {

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), false, parameters);
	}

	private static void _addStagingAttribute(
		ServiceContext serviceContext, String key, Object value) {

		serviceContext.setAttribute(
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
			String.valueOf(value));
	}

	private static void _addStagingAttributes(ServiceContext serviceContext) {
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.DATA_STRATEGY_MIRROR, true);
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			true);
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, true);
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL, true);
	}

	private static final String _DEPOT_ADMIN_PORTLET_ID =
		"com_liferay_depot_web_portlet_DepotAdminPortlet";

}