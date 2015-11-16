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

package com.liferay.site.memberships.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.site.memberships.web.constants.SiteMembershipsPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ViewMembershipRequestsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ViewMembershipRequestsPortletConfigurationIcon(
		HttpServletRequest request) {

		super(request);
	}

	@Override
	public String getMessage() {
		return "view-membership-requests";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			request, SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view_membership_requests.jsp");

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
		}

		return portletURL.toString();
	}

	@Override
	public boolean isShow() {
		Group group = themeDisplay.getScopeGroup();

		if (group.getType() != GroupConstants.TYPE_SITE_RESTRICTED) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}