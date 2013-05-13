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
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutRevisionServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;

/**
 * @author Raymond Augé
 * @author Julio Camarero
 */
public class LayoutRevisionServiceImpl extends LayoutRevisionServiceBaseImpl {

	@Override
	public LayoutRevision addLayoutRevision(
			long userId, long layoutSetBranchId, long layoutBranchId,
			long parentLayoutRevisionId, boolean head, long plid,
			long portletPreferencesPlid, boolean privateLayout, String name,
			String title, String description, String keywords, String robots,
			String typeSettings, boolean iconImage, long iconImageId,
			String themeId, String colorSchemeId, String wapThemeId,
			String wapColorSchemeId, String css, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		GroupPermissionUtil.check(
			getPermissionChecker(), layoutSetBranch.getGroupId(),
			ActionKeys.ADD_LAYOUT_BRANCH);

		return layoutRevisionLocalService.addLayoutRevision(
			userId, layoutSetBranchId, layoutBranchId, parentLayoutRevisionId,
			head, plid, portletPreferencesPlid, privateLayout, name, title,
			description, keywords, robots, typeSettings, iconImage, iconImageId,
			themeId, colorSchemeId, wapThemeId, wapColorSchemeId, css,
			serviceContext);
	}

}