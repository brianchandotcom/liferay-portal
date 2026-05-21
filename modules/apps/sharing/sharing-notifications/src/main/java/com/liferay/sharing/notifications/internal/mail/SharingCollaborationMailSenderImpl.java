/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.notifications.internal.mail;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.EscapableObject;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.configuration.SharingEntryCollaborationEmailConfiguration;
import com.liferay.sharing.mail.SharingCollaborationMailSender;
import com.liferay.sharing.model.SharingEntry;

import jakarta.mail.internet.InternetAddress;

import jakarta.portlet.PortletMode;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.WindowState;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = SharingCollaborationMailSender.class)
public class SharingCollaborationMailSenderImpl
	implements SharingCollaborationMailSender {

	@Override
	public void sendEmail(
			ServiceContext serviceContext, SharingEntry sharingEntry)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				sharingEntry.getCompanyId(), "LPD-52006")) {

			return;
		}

		Ticket ticket = _ticketLocalService.fetchTicket(
			sharingEntry.getToTicketId());

		if ((ticket == null) ||
			(ticket.getType() != TicketConstants.TYPE_INVITE_COLLABORATOR)) {

			return;
		}

		String emailAddress = ticket.getExtraInfo();

		_validateEmailAddress(emailAddress);

		if (serviceContext.getRequest() == null) {
			throw new PortalException(
				"Unable to send sharing collaboration email for ticket ID " +
					ticket.getTicketId() +
						" because the service context has no HTTP request");
		}

		_sendEmail(emailAddress, serviceContext, sharingEntry, ticket);
	}

	private String _getAssetType(
		String className, long companyId, Locale locale) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				companyId, className);

		if (objectDefinition != null) {
			return objectDefinition.getLabel(locale);
		}

		return ResourceActionsUtil.getModelResource(locale, className);
	}

	private String _getLogoURL(Group group, ThemeDisplay themeDisplay)
		throws Exception {

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		Company company = themeDisplay.getCompany();

		if (company.isSiteLogo()) {
			return _portal.getPortalURL(themeDisplay) +
				group.getLogoURL(themeDisplay, false);
		}

		return _portal.getPortalURL(themeDisplay) +
			themeDisplay.getCompanyLogo();
	}

	private void _sendEmail(
			String emailAddress, ServiceContext serviceContext,
			SharingEntry sharingEntry, Ticket ticket)
		throws Exception {

		long companyId = sharingEntry.getCompanyId();

		Group group = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		String url = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				serviceContext.getRequest(),
				AccountPortletKeys.ACCOUNT_USERS_REGISTRATION,
				_layoutLocalService.fetchDefaultLayout(
					group.getGroupId(), false),
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/account_admin/create_account_user"
		).setParameter(
			"ticketKey", ticket.getKey()
		).setPortletMode(
			PortletMode.VIEW
		).setWindowState(
			WindowState.MAXIMIZED
		).buildString();

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put("[$ACCEPT_COLLABORATION_URL$]", url);

		User user = _userLocalService.getUser(sharingEntry.getUserId());

		mailTemplateContextBuilder.put(
			"[$ASSET_TYPE$]",
			new EscapableObject<>(
				_getAssetType(
					sharingEntry.getClassName(), companyId, user.getLocale())));

		Company company = _companyLocalService.getCompany(companyId);

		mailTemplateContextBuilder.put(
			"[$COMPANY_NAME$]", new EscapableObject<>(company.getName()));

		SharingEntryCollaborationEmailConfiguration
			sharingEntryCollaborationEmailConfiguration =
				_configurationProvider.getCompanyConfiguration(
					SharingEntryCollaborationEmailConfiguration.class,
					companyId);

		String fromAddress =
			sharingEntryCollaborationEmailConfiguration.
				invitationToCollaborateEmailSenderEmailAddress();

		if (Validator.isNull(fromAddress)) {
			fromAddress = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		}

		String fromName =
			sharingEntryCollaborationEmailConfiguration.
				invitationToCollaborateEmailSenderName();

		if (Validator.isNull(fromName)) {
			fromName = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		}

		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", fromAddress);
		mailTemplateContextBuilder.put(
			"[$FROM_NAME$]", new EscapableObject<>(fromName));

		mailTemplateContextBuilder.put(
			"[$LOGO_URL$]",
			_getLogoURL(group, serviceContext.getThemeDisplay()));

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		LocalizedValuesMap subjectLocalizedValuesMap =
			sharingEntryCollaborationEmailConfiguration.
				invitationToCollaborateEmailSubject();

		MailTemplate subjectMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(
				subjectLocalizedValuesMap.get(user.getLocale()), false);

		LocalizedValuesMap bodyLocalizedValuesMap =
			sharingEntryCollaborationEmailConfiguration.
				invitationToCollaborateEmailBody();

		MailTemplate bodyMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(
				bodyLocalizedValuesMap.get(user.getLocale()), true);

		MailMessage mailMessage = new MailMessage(
			new InternetAddress(fromAddress, fromName),
			new InternetAddress(emailAddress),
			subjectMailTemplate.renderAsString(
				user.getLocale(), mailTemplateContext),
			bodyMailTemplate.renderAsString(
				user.getLocale(), mailTemplateContext),
			true);

		_mailService.sendEmail(mailMessage);
	}

	private void _validateEmailAddress(String emailAddress) throws Exception {
		if (Validator.isNull(emailAddress)) {
			throw new EmailAddressException(
				"Ticket of type \"invite collaborator\" must have an email " +
					"address");
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new EmailAddressException(
				"Invalid email address: \"" + emailAddress + "\"");
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private MailService _mailService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}