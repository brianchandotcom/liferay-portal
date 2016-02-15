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

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Bruno Basto
 */
public class DDLRecordSetPublishPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DDLRecordSetPublishPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "publish";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:Liferay.component('formPortlet').openPublishModal()";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		long recordSetId = getRecordSetId(portletRequest);

		if (recordSetId == 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

	protected long getRecordSetId(PortletRequest portletRequest) {
		return ParamUtil.getLong(portletRequest, "recordSetId");
	}

}