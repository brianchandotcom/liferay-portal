/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.test.util;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Adam Brandizzi
 */
public class CalendarStagingTestUtil {

	public static void cleanUp() {
		PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(0);
	}

	public static ServiceContext createServiceContext(User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	public static void enableLocalStaging(
			Group group, boolean enableCalendarStaging)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		addStagingAttribute(
			serviceContext,
			StagingUtil.getStagedPortletId(CalendarPortletKeys.CALENDAR_ADMIN),
			enableCalendarStaging);
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

	public static Calendar getStagingCalendar(Group group, Calendar calendar)
		throws PortalException {

		if (group.hasStagingGroup()) {
			group = group.getStagingGroup();
		}

		Assert.assertTrue(group.isStaged());

		return CalendarLocalServiceUtil.fetchCalendarByUuidAndGroupId(
			calendar.getUuid(), group.getGroupId());
	}

	public static void publishLayouts(
			Group liveGroup, boolean enableCalendarStaging)
		throws PortalException {

		Group stagingGroup = liveGroup.getStagingGroup();

		List<String> portletIds = null;

		if (enableCalendarStaging) {
			portletIds = ListUtil.fromArray(CalendarPortletKeys.CALENDAR);
		}

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.buildParameterMap(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE, true,
				false, true, false, false, true, true, true, true, false, null,
				true, false, portletIds, false, null,
				ExportImportDateUtil.RANGE_ALL, true, true,
				UserIdStrategy.CURRENT_USER_ID);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), false, parameters);
	}

	public static void publishPortlet(
			Group liveGroup, Layout targetLayout, boolean enableCalendarStaging)
		throws PortalException {

		Group stagingGroup = liveGroup.getStagingGroup();

		Layout sourceLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			targetLayout.getUuid(), stagingGroup.getGroupId(), false);

		List<String> portletIds = new ArrayList<>();

		portletIds.add(CalendarPortletKeys.CALENDAR_ADMIN);

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.buildParameterMap(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE, true,
				false, true, false, false, true, true, true, true, false, null,
				true, false, enableCalendarStaging ? portletIds : null, false,
				null, ExportImportDateUtil.RANGE_ALL, true, true,
				UserIdStrategy.CURRENT_USER_ID);

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), sourceLayout.getPlid(),
			targetLayout.getPlid(), CalendarPortletKeys.CALENDAR_ADMIN,
			parameters);
	}

	protected static void addStagingAttribute(
		Map<String, String[]> parameters, String key, Object value) {

		parameters.put(key, new String[] {String.valueOf(value)});
	}

	protected static void addStagingAttribute(
		ServiceContext serviceContext, String key, Object value) {

		serviceContext.setAttribute(
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
			String.valueOf(value));
	}

}