/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.PortletPreferencesFinderImpl;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Date;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class PortletPreferencesListener
	extends BaseModelListener<PortletPreferences> {

	@Override
	public void onAfterRemove(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);
	}

	@Override
	public void onAfterUpdate(PortletPreferences portletPreferences) {
		clearCache(portletPreferences);

		updateLayout(portletPreferences);
	}

	protected void clearCache(PortletPreferences portletPreferences) {
		try {
			long companyId = 0;
			long groupId = 0;
			boolean privateLayout = false;

			Layout layout = LayoutUtil.fetchByPrimaryKey(
				portletPreferences.getPlid());

			if (layout != null) {
				companyId = layout.getCompanyId();
				groupId = layout.getGroupId();
				privateLayout = layout.isPrivateLayout();
			}
			else {
				LayoutRevision layoutRevision =
					LayoutRevisionUtil.fetchByPrimaryKey(
						portletPreferences.getPlid());

				if (layoutRevision != null) {
					companyId = layoutRevision.getCompanyId();
					groupId = layoutRevision.getGroupId();
					privateLayout = layoutRevision.isPrivateLayout();
				}
			}

			if (!privateLayout) {
				CacheUtil.clearCache();
			}

			if ((companyId > 0) && (groupId > 0)) {
				Object[] finderArgs = new Object[] {
					companyId, groupId, portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(),
					portletPreferences.getPortletId(), privateLayout
				};

				FinderCacheUtil.removeResult(
					PortletPreferencesFinderImpl
						.FINDER_PATH_FIND_PLIDS_BY_C_G_O_O_P_P,
					finderArgs);

				FinderCacheUtil.removeResult(
					PortletPreferencesFinderImpl
						.FINDER_PATH_FIND_PORTLET_PREFERENCES_BY_C_G_O_O_P_P,
					finderArgs);
			}
		}
		catch (Exception e) {
			_log.error(
				"Clearing cache for " + portletPreferences + " has failed.", e);
		}
	}

	protected void updateLayout(PortletPreferences portletPreferences) {
		try {
			if ((portletPreferences.getOwnerType() ==
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT) &&
				(portletPreferences.getPlid() > 0)) {

				Layout layout = LayoutLocalServiceUtil.fetchLayout(
					portletPreferences.getPlid());

				if (layout == null) {
					return;
				}

				layout.setModifiedDate(new Date());

				LayoutLocalServiceUtil.updateLayout(layout);
			}
		}
		catch (Exception e) {
			_log.error("Unable to update the layout's modified date", e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletPreferencesListener.class);

}