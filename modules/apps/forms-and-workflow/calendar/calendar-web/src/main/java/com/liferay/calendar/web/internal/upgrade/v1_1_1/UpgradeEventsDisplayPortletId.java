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

package com.liferay.calendar.web.internal.upgrade.v1_1_1;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marcellus Tavares
 */
public class UpgradeEventsDisplayPortletId
	extends com.liferay.portal.kernel.upgrade.BaseUpgradePortletId {

	public UpgradeEventsDisplayPortletId(
		PortletPreferencesLocalService portletPreferencesLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	protected void deleteResourcePermissions(
			final String oldRootPortletId, final String newRootPortletId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property nameProperty = PropertyFactoryUtil.forName("name");

					dynamicQuery.add(
						nameProperty.eq(new String(oldRootPortletId)));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<ResourcePermission>() {

				@Override
				public void performAction(ResourcePermission resourcePermission)
					throws PortalException {

					long total = getResourcePermissionsCount(
						resourcePermission.getCompanyId(), newRootPortletId,
						resourcePermission.getScope(),
						resourcePermission.getRoleId());

					if (total > 0) {
						_resourcePermissionLocalService.
							deleteResourcePermission(resourcePermission);
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"1_WAR_eventsdisplayportlet", CalendarPortletKeys.CALENDAR
			}
		};
	}

	protected long getResourcePermissionsCount(
			final long companyId, final String name, final int scope,
			final long roleId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property companyIdProperty = PropertyFactoryUtil.forName(
						"companyId");

					dynamicQuery.add(companyIdProperty.eq(companyId));

					Property nameProperty = PropertyFactoryUtil.forName("name");

					dynamicQuery.add(nameProperty.eq(name));

					Property scopeProperty = PropertyFactoryUtil.forName(
						"scope");

					dynamicQuery.add(scopeProperty.eq(scope));

					Property roleIdProperty = PropertyFactoryUtil.forName(
						"roleId");

					dynamicQuery.add(roleIdProperty.eq(roleId));
				}

			});

		return actionableDynamicQuery.performCount();
	}

	@Override
	protected void updateInstanceablePortletPreferences(
			final String oldRootPortletId, final String newRootPortletId)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_portletPreferencesLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Junction junction = RestrictionsFactoryUtil.disjunction();

					Property property = PropertyFactoryUtil.forName(
						"portletId");

					junction.add(property.eq(oldRootPortletId));
					junction.add(
						property.like(oldRootPortletId + "_INSTANCE_%"));
					junction.add(
						property.like(oldRootPortletId + "_USER_%_INSTANCE_%"));

					dynamicQuery.add(junction);
				}

			});
		actionableDynamicQuery.setParallel(true);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<PortletPreferences>() {

				@Override
				public void performAction(PortletPreferences portletPreference)
					throws PortalException {

					updatePortletPreferences(
						portletPreference, oldRootPortletId, newRootPortletId);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			updateResourcePermission(oldRootPortletId, newRootPortletId, true);

			updateInstanceablePortletPreferences(
				oldRootPortletId, newRootPortletId);

			updateLayouts(oldRootPortletId, newRootPortletId, false);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	protected void updatePortletPreferences(
		PortletPreferences portletPreferences, String oldRootPortletId,
		String newRootPortletId) {

		String newPortletId = StringUtil.replace(
			portletPreferences.getPortletId(), oldRootPortletId,
			newRootPortletId);

		portletPreferences.setPortletId(newPortletId);

		StringBundler sb = new StringBundler(10);

		sb.append("<portlet-preferences>");
		sb.append(String.format(_preferenceFormat, "defaultView", "agenda"));
		sb.append(
			String.format(
				_preferenceFormat, "displaySchedulerHeader", "false"));
		sb.append(
			String.format(_preferenceFormat, "displaySchedulerOnly", "true"));
		sb.append(String.format(_preferenceFormat, "eventsPerPage", "10"));
		sb.append(String.format(_preferenceFormat, "maxDaysDisplayed", "1"));
		sb.append(String.format(_preferenceFormat, "showAgendaView", "true"));
		sb.append(String.format(_preferenceFormat, "showDayView", "false"));
		sb.append(String.format(_preferenceFormat, "showMonthView", "false"));
		sb.append(String.format(_preferenceFormat, "showWeekView", "false"));
		sb.append(String.format(_preferenceFormat, "showUserEvents", "false"));
		sb.append("</portlet-preferences>");

		portletPreferences.setPreferences(sb.toString());

		_portletPreferencesLocalService.updatePortletPreferences(
			portletPreferences);
	}

	@Override
	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		deleteResourcePermissions(oldRootPortletId, newRootPortletId);

		super.updateResourcePermission(
			oldRootPortletId, newRootPortletId, updateName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeEventsDisplayPortletId.class);

	private static final String _preferenceFormat =
		"<preference><name>%s</name><value>%s</value></preference>";

	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}