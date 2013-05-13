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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.LayoutSetServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetServiceImpl extends LayoutSetServiceBaseImpl {

	/**
	 * Updates the state of the layout set prototype link.
	 *
	 * <p>
	 * <strong>Important:</strong> Setting
	 * <code>layoutSetPrototypeLinkEnabled</code> to <code>true</code> and
	 * <code>layoutSetPrototypeUuid</code> to <code>null</code> when the layout
	 * set prototype's current uuid is <code>null</code> will result in an
	 * <code>IllegalStateException</code>.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  privateLayout whether the layout set is private to the group
	 * @param  layoutSetPrototypeLinkEnabled whether the layout set prototype is
	 *         link enabled
	 * @param  layoutSetPrototypeUuid the uuid of the layout set prototype to
	 *         link with
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		if (layoutSet.isLayoutSetPrototypeLinkEnabled() &&
			!layoutSetPrototypeLinkEnabled) {

			PortalPermissionUtil.check(
				getPermissionChecker(), ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
		}

		layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);
	}

	@Override
	public void updateLogo(
			long groupId, boolean privateLayout, boolean logo, byte[] bytes)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		layoutSetLocalService.updateLogo(groupId, privateLayout, logo, bytes);
	}

	@Override
	public void updateLogo(
			long groupId, boolean privateLayout, boolean logo, File file)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		layoutSetLocalService.updateLogo(groupId, privateLayout, logo, file);
	}

	@Override
	public void updateLogo(
			long groupId, boolean privateLayout, boolean logo,
			InputStream inputStream)
		throws PortalException, SystemException {

		updateLogo(groupId, privateLayout, logo, inputStream, true);
	}

	@Override
	public void updateLogo(
			long groupId, boolean privateLayout, boolean logo,
			InputStream inputStream, boolean cleanUpStream)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		layoutSetLocalService.updateLogo(
			groupId, privateLayout, logo, inputStream, cleanUpStream);
	}

	@Override
	public LayoutSet updateLookAndFeel(
			long groupId, boolean privateLayout, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		pluginSettingLocalService.checkPermission(
			getUserId(), themeId, Plugin.TYPE_THEME);

		return layoutSetLocalService.updateLookAndFeel(
			groupId, privateLayout, themeId, colorSchemeId, css, wapTheme);
	}

	@Override
	public LayoutSet updateSettings(
			long groupId, boolean privateLayout, String settings)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return layoutSetLocalService.updateSettings(
			groupId, privateLayout, settings);
	}

	@Override
	public LayoutSet updateVirtualHost(
			long groupId, boolean privateLayout, String virtualHost)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return layoutSetLocalService.updateVirtualHost(
			groupId, privateLayout, virtualHost);
	}

}