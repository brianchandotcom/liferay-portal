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

package com.liferay.message.boards.web.portlet.configuration.icon;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.trash.kernel.util.TrashUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class DeleteCategoryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeleteCategoryPortletConfigurationIcon(
		PortletRequest portletRequest, MBCategory category) {

		super(portletRequest);

		_category = category;
	}

	@Override
	public String getMessage() {
		if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
			return "move-to-the-recycle-bin";
		}

		return "delete";
	}

	@Override
	public String getURL() {
		try {
			PortletURL deleteURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
				PortletRequest.ACTION_PHASE);

			deleteURL.setParameter(
				ActionRequest.ACTION_NAME, "/message_boards/edit_category");

			String cmd = Constants.DELETE;

			if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
				cmd = Constants.MOVE_TO_TRASH;
			}

			deleteURL.setParameter(Constants.CMD, cmd);

			PortletURL parentCategoryURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
				PortletRequest.RENDER_PHASE);

			long parentCategoryId = getCategoryId(
				_category.getParentCategory());

			if (parentCategoryId ==
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				parentCategoryURL.setParameter(
					"mvcRenderCommandName", "/message_boards/view");
			}
			else {
				parentCategoryURL.setParameter(
					"mvcRenderCommandName", "/message_boards/view_category");
				parentCategoryURL.setParameter(
					"mbCategoryId", String.valueOf(parentCategoryId));
			}

			deleteURL.setParameter("redirect", parentCategoryURL.toString());

			deleteURL.setParameter(
				"mbCategoryId", String.valueOf(_category.getCategoryId()));

			return deleteURL.toString();
		}
		catch (PortalException pe) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow() {
		try {
			if (_category == null) {
				return false;
			}

			if (MBCategoryPermission.contains(
					themeDisplay.getPermissionChecker(), _category,
					ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	protected long getCategoryId(MBCategory category) {
		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (category != null) {
			categoryId = category.getCategoryId();
		}

		return categoryId;
	}

	protected boolean isTrashEnabled(long groupId) {
		try {
			if (TrashUtil.isTrashEnabled(groupId)) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	private final MBCategory _category;

}