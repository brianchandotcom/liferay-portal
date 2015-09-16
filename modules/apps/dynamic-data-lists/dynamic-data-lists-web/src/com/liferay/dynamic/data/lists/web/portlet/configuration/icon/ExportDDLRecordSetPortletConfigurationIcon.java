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

package com.liferay.dynamic.data.lists.web.portlet.configuration.icon;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.PortletDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class ExportDDLRecordSetPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ExportDDLRecordSetPortletConfigurationIcon(
		HttpServletRequest request) {

		super(request);
	}

	@Override
	public String getImage() {
		return "../aui/icon-arrow-down";
	}

	@Override
	public String getMessage() {
		return "export";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String namespace = portletDisplay.getNamespace();

		long recordSetId = ParamUtil.getLong(
			request, namespace + "recordSetId");

		if (recordSetId == 0) {
			return false;
		}

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		String rootPortletId = portletDisplay.getRootPortletId();

		if (rootPortletId.equals(DDLPortletKeys.DYNAMIC_DATA_LISTS)) {
			return DDLPermission.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(),
					DDLActionKeys.ADD_RECORD_SET);
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}