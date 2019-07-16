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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.service.base.PortletPreferencesServiceBaseImpl;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Jorge Ferrer
 * @author Raymond Augé
 */
public class PortletPreferencesServiceImpl
	extends PortletPreferencesServiceBaseImpl {

	@Override
	public void deleteArchivedPreferences(long portletItemId)
		throws PortalException {

		PortletItem portletItem = portletItemLocalService.getPortletItem(
			portletItemId);

		GroupPermissionUtil.check(
			getPermissionChecker(), portletItem.getGroupId(),
			ActionKeys.MANAGE_ARCHIVED_SETUPS);

		long ownerId = portletItemId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		long plid = 0;

		portletPreferencesLocalService.deletePortletPreferences(
			ownerId, ownerType, plid, portletItem.getPortletId());

		portletItemLocalService.deletePortletItem(portletItemId);
	}

	@Override
	public void restoreArchivedPreferences(
			long groupId, Layout layout, String portletId, long portletItemId,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws PortalException {

		restoreArchivedPreferences(
			groupId, layout, portletId,
			portletItemLocalService.getPortletItem(portletItemId),
			jxPortletPreferences);
	}

	@Override
	public void restoreArchivedPreferences(
			long groupId, Layout layout, String portletId,
			PortletItem portletItem,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), groupId, layout, portletId,
			ActionKeys.CONFIGURATION);

		long ownerId = portletItem.getPortletItemId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		long plid = 0;

		javax.portlet.PortletPreferences archivedJxPortletPreferences =
			portletPreferencesLocalService.getPreferences(
				portletItem.getCompanyId(), ownerId, ownerType, plid,
				PortletIdCodec.decodePortletName(portletId));

		copyPreferences(archivedJxPortletPreferences, jxPortletPreferences);
	}

	@Override
	public void restoreArchivedPreferences(
			long groupId, String name, Layout layout, String portletId,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws PortalException {

		PortletItem portletItem = portletItemLocalService.getPortletItem(
			groupId, name, portletId, PortletPreferences.class.getName());

		restoreArchivedPreferences(
			groupId, layout, portletId, portletItem, jxPortletPreferences);
	}

	@Override
	public void updateArchivePreferences(
			long userId, long groupId, String name, String portletId,
			javax.portlet.PortletPreferences jxPortletPreferences)
		throws PortalException {

		PortletPermissionUtil.check(
			getPermissionChecker(), groupId, 0, portletId,
			ActionKeys.CONFIGURATION);

		PortletItem portletItem = portletItemLocalService.updatePortletItem(
			userId, groupId, name, portletId,
			PortletPreferences.class.getName());

		long ownerId = portletItem.getPortletItemId();

		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		long plid = 0;

		javax.portlet.PortletPreferences archivedJxPortletPreferences =
			portletPreferencesLocalService.getPreferences(
				portletItem.getCompanyId(), ownerId, ownerType, plid,
				portletId);

		copyPreferences(jxPortletPreferences, archivedJxPortletPreferences);
	}

	protected void copyPreferences(
		javax.portlet.PortletPreferences sourceJxPortletPreferences,
		javax.portlet.PortletPreferences targetJxPortletPreferences) {

		try {
			Map<String, String[]> targetJxPortletPreferencesMap =
				targetJxPortletPreferences.getMap();

			for (String key : targetJxPortletPreferencesMap.keySet()) {
				try {
					targetJxPortletPreferences.reset(key);
				}
				catch (ReadOnlyException roe) {
				}
			}

			Map<String, String[]> sourceJxPortletPreferencesMap =
				sourceJxPortletPreferences.getMap();

			for (String key : sourceJxPortletPreferencesMap.keySet()) {
				try {
					targetJxPortletPreferences.setValues(
						key,
						sourceJxPortletPreferences.getValues(
							key, new String[0]));
				}
				catch (ReadOnlyException roe) {
				}
			}

			targetJxPortletPreferences.store();
		}
		catch (IOException ioe) {
			_log.error("Unable to copy jxPortletPreferences", ioe);
		}
		catch (ValidatorException ve) {
			throw new SystemException(ve);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferencesServiceImpl.class);

}