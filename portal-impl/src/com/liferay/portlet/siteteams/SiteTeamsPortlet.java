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
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.TeamNameException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.TeamServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.sites.action.ActionUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTeamsPortlet extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateTeam(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteTeam(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.sites_admin.error");
			}
			else if (e instanceof DuplicateTeamException ||
					 e instanceof NoSuchTeamException ||
					 e instanceof TeamNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				if (cmd.equals(Constants.DELETE)) {
					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getTeam(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof NoSuchTeamException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.sites_admin.edit_team"));
	}

	protected void deleteTeam(ActionRequest actionRequest) throws Exception {
		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		TeamServiceUtil.deleteTeam(teamId);
	}

	protected void updateTeam(ActionRequest actionRequest) throws Exception {
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

}