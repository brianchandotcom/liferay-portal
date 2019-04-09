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

package com.liferay.portal.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

import java.util.Date;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class PortletPreferencesModelListener
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
		if (portletPreferences == null) {
			return;
		}

		try {
			long companyId = 0;

			Layout layout = LayoutUtil.fetchByPrimaryKey(
				portletPreferences.getPlid());

			if ((layout != null) && !layout.isPrivateLayout()) {
				companyId = layout.getCompanyId();
			}
			else {
				LayoutRevision layoutRevision =
					LayoutRevisionUtil.fetchByPrimaryKey(
						portletPreferences.getPlid());

				if ((layoutRevision != null) &&
					!layoutRevision.isPrivateLayout()) {

					companyId = layoutRevision.getCompanyId();
				}
			}

			if (companyId > 0) {
				CacheUtil.clearCache(companyId);
			}
		}
		catch (Exception e) {
			CacheUtil.clearCache();
		}
	}

	protected void updateLayout(PortletPreferences portletPreferences) {
		try {
			if ((portletPreferences.getOwnerType() ==
					PortletKeys.PREFS_OWNER_TYPE_GROUP) &&
				(portletPreferences.getOwnerId() > 0)) {

				Group group = GroupLocalServiceUtil.fetchGroup(
					portletPreferences.getOwnerId());

				if (group == null) {
					return;
				}

				String className = group.getClassName();

				if (!className.equals(LayoutSetPrototype.class.getName())) {
					return;
				}

				LayoutSetPrototype layoutSetPrototype =
					LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
						group.getClassPK());

				if (layoutSetPrototype == null) {
					return;
				}

				layoutSetPrototype.setModifiedDate(new Date());

				LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
					layoutSetPrototype);
			}
			else if ((portletPreferences.getOwnerType() ==
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT) &&
					 (portletPreferences.getPlid() > 0)) {

				Layout layout = LayoutLocalServiceUtil.fetchLayout(
					portletPreferences.getPlid());

				if (layout == null) {
					return;
				}

				layout.setModifiedDate(new Date());

				LayoutLocalServiceUtil.updateLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), layout.getTypeSettings());
			}
		}
		catch (Exception e) {
			_log.error("Unable to update the layout's modified date", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesModelListener.class);

}