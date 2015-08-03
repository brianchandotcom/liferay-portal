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

package com.liferay.portlet.social.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.social.model.SocialActivitySetting;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityPermissionImpl implements SocialActivityPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _getPortletId(), groupId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		if (permissionChecker.isGroupAdmin(groupId) ||
			permissionChecker.isGroupOwner(groupId)) {

			return true;
		}

		if (permissionChecker.hasPermission(
				groupId, _getPortletId(), 0, actionId)) {

			return true;
		}

		return false;
	}

	private String _getPortletId() {
		return PortletProviderUtil.getPortletId(
			SocialActivitySetting.class.getName(), PortletProvider.Action.EDIT);
	}

}