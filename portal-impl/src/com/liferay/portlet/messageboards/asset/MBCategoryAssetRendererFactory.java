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

package com.liferay.portlet.messageboards.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 * @author Jonathan Lee
 */
public class MBCategoryAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "category";

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getMBCategory(classPK);

		MBCategoryAssetRenderer mbCategoryAssetRenderer =
			new MBCategoryAssetRenderer(category);

		mbCategoryAssetRenderer.setAssetRendererType(type);

		return mbCategoryAssetRenderer;
	}

	@Override
	public String getClassName() {
		return MBCategory.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		MBCategory category = MBCategoryLocalServiceUtil.getMBCategory(classPK);

		return MBCategoryPermission.contains(
			permissionChecker, category, actionId);
	}

	@Override
	public boolean isCategorizable() {
		return false;
	}

	@Override
	public boolean isSelectable() {
		return _SELECTABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/conversation.png";
	}

	private static final boolean _SELECTABLE = false;

}