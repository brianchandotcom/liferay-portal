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

package com.liferay.staging.processes.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Mate Thurzo
 */
public class PublishTemplatesConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PublishTemplatesConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "publish-templates";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, StagingProcessesPortletKeys.STAGING_PROCESSES,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/publish_templates/view.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		Group scopeGroup = themeDisplay.getScopeGroup();

		if ((scopeGroup != null) &&
			(scopeGroup.hasLocalOrRemoteStagingGroup() ||
			 (!scopeGroup.isStaged() && !scopeGroup.isStagingGroup()))) {

			return false;
		}

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
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

}