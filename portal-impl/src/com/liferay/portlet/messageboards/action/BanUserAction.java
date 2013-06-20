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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.MBBanServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Michael Young
 */
public class BanUserAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("ban")) {
				banUser(actionRequest);
			}
			else if (cmd.equals("unban")) {
				unbanUser(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.message_boards.error");
			}
			else {
				throw e;
			}
		}
	}

	protected void banUser(ActionRequest actionRequest) throws Exception {
		long banUserId = ParamUtil.getLong(actionRequest, "banUserId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MBBan.class.getName(), actionRequest);

		MBBanServiceUtil.addBan(banUserId, serviceContext);
	}

	protected void unbanUser(ActionRequest actionRequest) throws Exception {
		long banUserId = ParamUtil.getLong(actionRequest, "banUserId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MBBan.class.getName(), actionRequest);

		MBBanServiceUtil.deleteBan(banUserId, serviceContext);
	}

}