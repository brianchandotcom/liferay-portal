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

package com.liferay.portlet.siteteams;

import com.liferay.portal.DuplicateTeamException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.TeamNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.TeamServiceUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTeamsPortlet extends MVCPortlet {

	public void deleteTeam(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		TeamServiceUtil.deleteTeam(teamId);
	}

	public void editTeam(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (teamId <= 0) {

			// Add team

			TeamServiceUtil.addTeam(groupId, name, description);
		}
		else {

			// Update team

			TeamServiceUtil.updateTeam(teamId, name, description);
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicateTeamException ||
			cause instanceof NoSuchTeamException ||
			cause instanceof PrincipalException ||
			cause instanceof TeamNameException ||
			super.isSessionErrorException(cause)) {

			return true;
		}

		return false;
	}

}