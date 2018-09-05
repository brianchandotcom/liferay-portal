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

package com.liferay.portal.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.TicketLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.ActionConstants;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mika Koivisto
 */
public class VerifyEmailAddressAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(request, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return actionMapping.findForward("portal.verify_email_address");
		}

		if (themeDisplay.isSignedIn() && cmd.equals(Constants.SEND)) {
			sendEmailAddressVerification(request, response, themeDisplay);

			return actionMapping.findForward("portal.verify_email_address");
		}

		try {
			verifyEmailAddress(request, response, themeDisplay);

			if (!themeDisplay.isSignedIn()) {
				PortletURL portletURL = PortletURLFactoryUtil.create(
					request, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

				response.sendRedirect(portletURL.toString());

				return null;
			}

			return actionMapping.findForward(
				ActionConstants.COMMON_REFERER_JSP);
		}
		catch (Exception e) {
			if (e instanceof PortalException || e instanceof SystemException) {
				SessionErrors.add(request, e.getClass());

				return actionMapping.findForward("portal.verify_email_address");
			}

			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected void sendEmailAddressVerification(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay)
		throws Exception {

		User user = themeDisplay.getUser();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		List<Ticket> tickets = TicketLocalServiceUtil.getTickets(
			themeDisplay.getCompanyId(), User.class.getName(), user.getUserId(),
			TicketConstants.TYPE_EMAIL_ADDRESS);

		if (ListUtil.isEmpty(tickets)) {
			UserLocalServiceUtil.sendEmailAddressVerification(
				user, user.getEmailAddress(), serviceContext);
		}
		else {
			Ticket ticket = tickets.get(0);

			UserLocalServiceUtil.sendEmailAddressVerification(
				user, ticket.getExtraInfo(), serviceContext);
		}
	}

	protected void verifyEmailAddress(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay)
		throws Exception {

		AuthTokenUtil.checkCSRFToken(
			request, VerifyEmailAddressAction.class.getName());

		String ticketKey = ParamUtil.getString(request, "ticketKey");

		UserLocalServiceUtil.verifyEmailAddress(ticketKey);
	}

}