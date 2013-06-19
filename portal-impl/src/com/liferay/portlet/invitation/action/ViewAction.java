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

package com.liferay.portlet.invitation.action;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.invitation.util.InvitationUtil;

import java.util.HashSet;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Charles May
 */
public class ViewAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		Set<String> invalidEmailAddresses = new HashSet<String>();
		Set<String> validEmailAddresses = new HashSet<String>();

		int emailMessageMaxRecipients =
			InvitationUtil.getEmailMessageMaxRecipients();

		for (int i = 0; i < emailMessageMaxRecipients; i++) {
			String emailAddress = ParamUtil.getString(
				actionRequest, "emailAddress" + i);

			if (Validator.isEmailAddress(emailAddress)) {
				validEmailAddresses.add(emailAddress);
			}
			else if (Validator.isNotNull(emailAddress)) {
				invalidEmailAddresses.add("emailAddress" + i);
			}
		}

		if (validEmailAddresses.isEmpty() && invalidEmailAddresses.isEmpty()) {
			invalidEmailAddresses.add("emailAddress0");
		}

		if (!invalidEmailAddresses.isEmpty()) {
			SessionErrors.add(
				actionRequest, "emailAddresses", invalidEmailAddresses);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		String fromAddress = user.getEmailAddress();
		String fromName = user.getFullName();

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		Layout layout = themeDisplay.getLayout();

		String portalURL = PortalUtil.getPortalURL(actionRequest);

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, PortletKeys.INVITATION);

		String subject = InvitationUtil.getEmailMessageSubject(
			portletPreferences);
		String body = InvitationUtil.getEmailMessageBody(portletPreferences);

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$PAGE_URL$]",
				"[$PORTAL_URL$]"
			},
			new String[] {
				fromAddress, fromName, layoutFullURL, portalURL
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$PAGE_URL$]",
				"[$PORTAL_URL$]"
			},
			new String[] {
				fromAddress, fromName, layoutFullURL, portalURL
			});

		for (String emailAddress : validEmailAddresses) {
			InternetAddress to = new InternetAddress(emailAddress);

			MailMessage message = new MailMessage(
				from, to, subject, body, true);

			MailServiceUtil.sendEmail(message);
		}

		SessionMessages.add(actionRequest, "invitationSent");

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.invitation.view"));
	}

}