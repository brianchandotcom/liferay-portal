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

package com.liferay.dynamic.data.lists.form.web.portlet.configuration.icon;

import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class ExportDDLRecordSetPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ExportDDLRecordSetPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
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
		long recordSetId = ParamUtil.getLong(portletRequest, "recordSetId");

		if (recordSetId == 0) {
			return false;
		}

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return false;
		}

		try {
			return DDLRecordSetPermission.contains(
				themeDisplay.getPermissionChecker(), recordSetId,
				ActionKeys.VIEW);
		}
		catch (PortalException pe) {
			return false;
		}
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}