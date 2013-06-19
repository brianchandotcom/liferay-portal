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

package com.liferay.portal.action;

import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.admin.util.AdminUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class UpdateEmailAddressAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return actionMapping.findForward("portal.update_email_address");
		}

		try {
			updateEmailAddress(request);

			return actionMapping.findForward(
				ActionConstants.COMMON_REFERER_JSP);
		}
		catch (Exception e) {
			if (e instanceof DuplicateUserEmailAddressException ||
				e instanceof ReservedUserEmailAddressException ||
				e instanceof UserEmailAddressException) {

				SessionErrors.add(request, e.getClass());

				return actionMapping.findForward("portal.update_email_address");
			}
			else if (e instanceof NoSuchUserException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(request, e.getClass());

				return actionMapping.findForward("portal.error");
			}
			else {
				PortalUtil.sendError(e, request, response);

				return null;
			}
		}
	}

	protected void updateEmailAddress(HttpServletRequest request)
		throws Exception {

		long userId = PortalUtil.getUserId(request);
		String password = AdminUtil.getUpdateUserPassword(request, userId);
		String emailAddress1 = ParamUtil.getString(request, "emailAddress1");
		String emailAddress2 = ParamUtil.getString(request, "emailAddress2");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		UserServiceUtil.updateEmailAddress(
			userId, password, emailAddress1, emailAddress2, serviceContext);
	}

}