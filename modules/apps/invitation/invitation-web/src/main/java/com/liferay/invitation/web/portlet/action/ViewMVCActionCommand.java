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

package com.liferay.invitation.web.portlet.action;

import com.liferay.invitation.web.constants.InvitationPortletKeys;
import com.liferay.invitation.web.util.InvitationUtil;
import com.liferay.mail.service.MailService;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Charles May
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + InvitationPortletKeys.INVITATION,
		"mvc.command.name=view"
	},
	service = MVCActionCommand.class
)
public class ViewMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Set<String> invalidEmailAddresses = new HashSet<>();
		Set<String> validEmailAddresses = new HashSet<>();

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		int emailMessageMaxRecipients =
			InvitationUtil.getEmailMessageMaxRecipients(portletPreferences);

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

		Map<Locale, String> localizedSubjectMap =
			InvitationUtil.getEmailMessageSubjectMap(portletPreferences);
		Map<Locale, String> localizedBodyMap =
			InvitationUtil.getEmailMessageBodyMap(portletPreferences);

		Locale locale = user.getLocale();
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String subject = localizedSubjectMap.get(locale);

		if (Validator.isNull(subject)) {
			subject = localizedSubjectMap.get(defaultLocale);
		}

		String body = localizedBodyMap.get(locale);

		if (Validator.isNull(body)) {
			body = localizedBodyMap.get(defaultLocale);
		}

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$PAGE_URL$]",
				"[$PORTAL_URL$]"
			},
			new String[] {fromAddress, fromName, layoutFullURL, portalURL});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]", "[$FROM_NAME$]", "[$PAGE_URL$]",
				"[$PORTAL_URL$]"
			},
			new String[] {fromAddress, fromName, layoutFullURL, portalURL});

		for (String emailAddress : validEmailAddresses) {
			InternetAddress to = new InternetAddress(emailAddress);

			MailMessage message = new MailMessage(
				from, to, subject, body, true);

			Company company = themeDisplay.getCompany();

			message.setMessageId(
				PortalUtil.getMailId(
					company.getMx(), InvitationUtil.MESSAGE_POP_PORTLET_PREFIX,
					PortalUUIDUtil.generate()));

			_mailService.sendEmail(message);
		}

		SessionMessages.add(actionRequest, "invitationSent");

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			actionResponse.setRenderParameter("mvcPath", redirect);
		}

		actionResponse.setRenderParameter("mvcPath", "/view.jsp");
	}

	@Reference(unbind = "-")
	protected void setMailService(MailService mailService) {
		_mailService = mailService;
	}

	private volatile MailService _mailService;

}