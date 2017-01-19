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

package com.liferay.subscription.web.util;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.subscription.util.UnsubscribeLifecycleHook;
import com.liferay.subscription.web.configuration.SubscriptionConfiguration;
import com.liferay.subscription.web.constants.SubscriptionConstants;

import java.io.IOException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetHeaders;

/**
 * @author Alejandro Tardín
 */
public class UnsubscribeLifecycleHookImpl implements UnsubscribeLifecycleHook {

	public UnsubscribeLifecycleHookImpl(
		SubscriptionConfiguration configuration,
		TicketLocalService ticketLocalService,
		UserLocalService userLocalService) {

		_configuration = configuration;
		_ticketLocalService = ticketLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public void beforeSendNotificationToPersistedSubscriber(
			SubscriptionSender subscriptionSender, Subscription subscription)
		throws PortalException {

		if (subscriptionSender.isBulk()) {
			return;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.add(
			Calendar.DATE, _configuration.unsubscriptionTicketExpirationTime());

		List<Ticket> tickets = _ticketLocalService.getTickets(
			subscription.getCompanyId(), Subscription.class.getName(),
			subscription.getSubscriptionId(),
			SubscriptionConstants.TICKET_TYPE);

		Ticket ticket;

		if (ListUtil.isEmpty(tickets)) {
			ticket = _ticketLocalService.addTicket(
				subscription.getCompanyId(), Subscription.class.getName(),
				subscription.getSubscriptionId(),
				SubscriptionConstants.TICKET_TYPE, StringPool.BLANK,
				calendar.getTime(), subscriptionSender.getServiceContext());
		}
		else {
			ticket = tickets.get(0);

			ticket = _ticketLocalService.updateTicket(
				ticket.getTicketId(), Subscription.class.getName(),
				subscription.getSubscriptionId(),
				SubscriptionConstants.TICKET_TYPE, StringPool.BLANK,
				calendar.getTime());
		}

		_cache.put(subscription.getUserId(), ticket);
	}

	@Override
	public void processMailMessage(
			SubscriptionSender subscriptionSender, MailMessage mailMessage)
		throws IOException, PortalException {

		if (subscriptionSender.isBulk()) {
			return;
		}

		Locale locale = subscriptionSender.getServiceContext().getLocale();

		User user = _userLocalService.getUserByEmailAddress(
			subscriptionSender.getCompanyId(),
			mailMessage.getTo()[0].getAddress());

		Ticket ticket = _cache.get(user.getUserId());

		if (ticket != null) {
			try {
				String unsubscribeURL = _getUnsubscribeURL(
					subscriptionSender, user, ticket);

				_addUnsubscribeHeader(mailMessage, unsubscribeURL);
				_addUnsubscribeLink(mailMessage, locale, unsubscribeURL);
			}
			finally {
				_cache.remove(user.getUserId());
			}
		}
	}

	private void _addUnsubscribeHeader(
		MailMessage mailMessage, String unsubscribeURL) {

		InternetHeaders internetHeaders = new InternetHeaders();

		internetHeaders.setHeader(
			"List-Unsubscribe", "<" + unsubscribeURL + ">");

		mailMessage.setInternetHeaders(internetHeaders);
	}

	private void _addUnsubscribeLink(
			MailMessage mailMessage, Locale locale, String unsubscribeURL)
		throws IOException {

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put("[$UNSUBSCRIBE_URL$]", unsubscribeURL);

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		MailTemplate bodyMailTemplate =
			MailTemplateFactoryUtil.createMailTemplate(
				mailMessage.getBody(), true);

		String processedBody = bodyMailTemplate.renderAsString(
			locale, mailTemplateContext);

		mailMessage.setBody(processedBody);
	}

	private String _getUnsubscribeURL(
		SubscriptionSender sender, User user, Ticket ticket) {

		StringBuilder sb = new StringBuilder();

		sb.append(sender.getContextAttribute("[$PORTAL_URL$]"));
		sb.append(PortalUtil.getPathMain());
		sb.append("/portal/unsubscribe?key=");
		sb.append(ticket.getKey());
		sb.append("&userId=");
		sb.append(user.getUserId());

		return sb.toString();
	}

	private final Map<Long, Ticket> _cache = new HashMap<>();
	private final SubscriptionConfiguration _configuration;
	private final TicketLocalService _ticketLocalService;
	private final UserLocalService _userLocalService;

}