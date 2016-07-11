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

package com.liferay.expando.web.internal.search;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.permission.ExpandoColumnPermissionUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class CustomFieldChecker extends EmptyOnClickRowChecker {

	public CustomFieldChecker(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(renderResponse);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyId = themeDisplay.getCompanyId();

		_modelResource = ParamUtil.getString(renderRequest, "modelResource");
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		ExpandoColumn expandoColumn =
			ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
				_companyId, _modelResource, primaryKey);

		return super.getRowCheckBox(
			request, checked, disabled,
			String.valueOf(expandoColumn.getColumnId()));
	}

	@Override
	public boolean isDisabled(Object obj) {
		ExpandoColumn expandoColumn =
			ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
				_companyId, _modelResource, (String)obj);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!ExpandoColumnPermissionUtil.contains(
				permissionChecker, expandoColumn, ActionKeys.DELETE)) {

			return true;
		}

		return super.isDisabled(obj);
	}

	private final long _companyId;
	private final String _modelResource;

}