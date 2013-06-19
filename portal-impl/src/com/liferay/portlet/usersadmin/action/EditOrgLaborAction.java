/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.action;

import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchOrgLaborException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.OrgLaborServiceUtil;
import com.liferay.portal.struts.PortletAction;

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
public class EditOrgLaborAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateOrgLabor(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteOrgLabor(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrgLaborException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.users_admin.error");
			}
			else if (e instanceof NoSuchListTypeException) {
				SessionErrors.add(actionRequest, e.getClass());
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
			ActionUtil.getOrgLabor(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrgLaborException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.users_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.users_admin.edit_org_labor"));
	}

	protected void deleteOrgLabor(ActionRequest actionRequest)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(actionRequest, "orgLaborId");

		OrgLaborServiceUtil.deleteOrgLabor(orgLaborId);
	}

	protected void updateOrgLabor(ActionRequest actionRequest)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(actionRequest, "orgLaborId");

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");
		int typeId = ParamUtil.getInteger(actionRequest, "typeId");

		int sunOpen = ParamUtil.getInteger(actionRequest, "sunOpen");
		int sunClose = ParamUtil.getInteger(actionRequest, "sunClose");

		int monOpen = ParamUtil.getInteger(actionRequest, "monOpen");
		int monClose = ParamUtil.getInteger(actionRequest, "monClose");

		int tueOpen = ParamUtil.getInteger(actionRequest, "tueOpen");
		int tueClose = ParamUtil.getInteger(actionRequest, "tueClose");

		int wedOpen = ParamUtil.getInteger(actionRequest, "wedOpen");
		int wedClose = ParamUtil.getInteger(actionRequest, "wedClose");

		int thuOpen = ParamUtil.getInteger(actionRequest, "thuOpen");
		int thuClose = ParamUtil.getInteger(actionRequest, "thuClose");

		int friOpen = ParamUtil.getInteger(actionRequest, "friOpen");
		int friClose = ParamUtil.getInteger(actionRequest, "friClose");

		int satOpen = ParamUtil.getInteger(actionRequest, "satOpen");
		int satClose = ParamUtil.getInteger(actionRequest, "satClose");

		if (orgLaborId <= 0) {

			// Add organization labor

			OrgLaborServiceUtil.addOrgLabor(
				organizationId, typeId, sunOpen, sunClose, monOpen, monClose,
				tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
				friOpen, friClose, satOpen, satClose);
		}
		else {

			// Update organization labor

			OrgLaborServiceUtil.updateOrgLabor(
				orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose,
				tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
				friOpen, friClose, satOpen, satClose);
		}
	}

}