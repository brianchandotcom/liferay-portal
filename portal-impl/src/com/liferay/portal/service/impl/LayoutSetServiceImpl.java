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
	 * @param  layoutSetPrototypeLinkEnabled whether the layout set prototype is
	 *         link enabled
	 * @param  layoutSetPrototypeUuid the uuid of the layout set prototype to
	 *         link with
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(groupId);

		if (layoutSet.isLayoutSetPrototypeLinkEnabled() &&
			!layoutSetPrototypeLinkEnabled) {

			PortalPermissionUtil.check(
				getPermissionChecker(), ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
		}

		layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(
			groupId, layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	@Override
	public void updateLogo(long groupId, boolean logo, byte[] bytes)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutSetLocalService.updateLogo(groupId, logo, bytes);
	}

	@Override
	public void updateLogo(long groupId, boolean logo, File file)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutSetLocalService.updateLogo(groupId, logo, file);
	}

	@Override
	public void updateLogo(long groupId, boolean logo, InputStream inputStream)
		throws PortalException {

		updateLogo(groupId, logo, inputStream, true);
	}

	@Override
	public void updateLogo(
			long groupId, boolean logo, InputStream inputStream,
			boolean cleanUpStream)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutSetLocalService.updateLogo(
			groupId, logo, inputStream, cleanUpStream);
	}

	@Override
	public LayoutSet updateLookAndFeel(
			long groupId, String themeId, String colorSchemeId, String css,
			boolean wapTheme)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		pluginSettingLocalService.checkPermission(
			getUserId(), themeId, Plugin.TYPE_THEME);

		return layoutSetLocalService.updateLookAndFeel(
			groupId, themeId, colorSchemeId, css, wapTheme);
	}

	@Override
	public LayoutSet updateSettings(long groupId, String settings)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutSetLocalService.updateSettings(groupId, settings);
	}

	@Override
	public LayoutSet updateVirtualHost(long groupId, String virtualHost)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return layoutSetLocalService.updateVirtualHost(groupId, virtualHost);
	}

}