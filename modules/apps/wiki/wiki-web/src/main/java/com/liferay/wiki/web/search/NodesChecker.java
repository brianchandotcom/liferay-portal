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

package com.liferay.wiki.web.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class NodesChecker extends EmptyOnClickRowChecker {

	public NodesChecker(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletResponse);

		_liferayPortletResponse = liferayPortletResponse;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_permissionChecker = themeDisplay.getPermissionChecker();
	}

	@Override
	public String getAllRowsCheckBox() {
		return null;
	}

	@Override
	public String getAllRowsCheckBox(HttpServletRequest request) {
		return null;
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		long nodeId = GetterUtil.getLong(primaryKey);

		WikiNode node = null;

		try {
			node = WikiNodeServiceUtil.getNode(nodeId);
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}

		String name = WikiNode.class.getSimpleName();
		boolean showInput = false;

		try {
			if (WikiNodePermissionChecker.contains(
					_permissionChecker, node, ActionKeys.DELETE)) {

				showInput = true;
			}
		}
		catch (Exception e) {
		}

		if (!showInput) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(9);

		sb.append("['");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append(RowChecker.ROW_IDS);
		sb.append(WikiNode.class.getSimpleName());
		sb.append("']");

		String checkBoxRowIds = sb.toString();

		return getRowCheckBox(
			request, checked, disabled,
			_liferayPortletResponse.getNamespace() + RowChecker.ROW_IDS +
				name + "",
			primaryKey, checkBoxRowIds, "'#" + getAllRowIds() + "'",
			StringPool.BLANK);
	}

	private final LiferayPortletResponse _liferayPortletResponse;
	private final PermissionChecker _permissionChecker;

}