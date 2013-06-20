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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.TicketConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.pwd.PwdToolkitUtilThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.login.util.LoginUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class UpdatePasswordAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Ticket ticket = getTicket(request);

		if (!themeDisplay.isSignedIn() && (ticket == null)) {
			return actionMapping.findForward(
				ActionConstants.COMMON_REFERER_JSP);
		}

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (Validator.isNull(cmd)) {
			if (ticket != null) {
				User user = UserLocalServiceUtil.getUser(ticket.getClassPK());

				try {
					UserLocalServiceUtil.checkLockout(user);

					UserLocalServiceUtil.updatePasswordReset(
						user.getUserId(), true);
				}
				catch (UserLockoutException ule) {
					SessionErrors.add(request, ule.getClass());
				}
			}

			return actionMapping.findForward("portal.update_password");
		}

		try {
			updatePassword(request, response, themeDisplay, ticket);

			String redirect = ParamUtil.getString(request, WebKeys.REFERER);

			if (Validator.isNull(redirect)) {
				PortletURL portletURL = new PortletURLImpl(
					request, PortletKeys.LOGIN, themeDisplay.getPlid(),
					PortletRequest.RENDER_PHASE);

				redirect = portletURL.toString();
			}

			response.sendRedirect(redirect);

			return null;
		}
		catch (Exception e) {
			if (e instanceof UserPasswordException) {
				SessionErrors.add(request, e.getClass(), e);

				return actionMapping.findForward("portal.update_password");
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

	protected Ticket getTicket(HttpServletRequest request) {
		String ticketKey = ParamUtil.getString(request, "ticketKey");

		if (Validator.isNull(ticketKey)) {
			return null;
		}

		try {
			Ticket ticket = TicketLocalServiceUtil.getTicket(ticketKey);

			if (ticket.getType() != TicketConstants.TYPE_PASSWORD) {
				return null;
			}

			if (!ticket.isExpired()) {
				return ticket;
			}
			else {
				TicketLocalServiceUtil.deleteTicket(ticket);
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	protected boolean isValidatePassword(HttpServletRequest request) {
		HttpSession session = request.getSession();

		Boolean setupWizardPasswordUpdated = (Boolean)session.getAttribute(
			WebKeys.SETUP_WIZARD_PASSWORD_UPDATED);

		if ((setupWizardPasswordUpdated != null) &&
			setupWizardPasswordUpdated) {

			return false;
		}

		return true;
	}

	protected void updatePassword(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay, Ticket ticket)
		throws Exception {

		AuthTokenUtil.check(request);

		long userId = 0;

		if (ticket != null) {
			userId = ticket.getClassPK();
		}
		else {
			userId = themeDisplay.getUserId();
		}

		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		boolean passwordReset = false;

		boolean previousValidate = PwdToolkitUtilThreadLocal.isValidate();

		try {
			boolean currentValidate = isValidatePassword(request);

			PwdToolkitUtilThreadLocal.setValidate(currentValidate);

			UserLocalServiceUtil.updatePassword(
				userId, password1, password2, passwordReset);
		}
		finally {
			PwdToolkitUtilThreadLocal.setValidate(previousValidate);
		}

		if (ticket != null) {
			TicketLocalServiceUtil.deleteTicket(ticket);

			User user = UserLocalServiceUtil.getUser(userId);

			Company company = CompanyLocalServiceUtil.getCompanyById(
				user.getCompanyId());

			String login = null;

			String authType = company.getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				login = user.getEmailAddress();
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				login = user.getScreenName();
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				login = String.valueOf(userId);
			}

			LoginUtil.login(request, response, login, password1, false, null);
		}
		else if (PropsValues.SESSION_STORE_PASSWORD) {
			HttpSession session = request.getSession();

			session.setAttribute(WebKeys.USER_PASSWORD, password1);
		}
	}

}