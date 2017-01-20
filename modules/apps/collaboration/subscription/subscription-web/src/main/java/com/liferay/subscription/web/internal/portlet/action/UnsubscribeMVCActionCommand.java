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

package com.liferay.subscription.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.NoSuchSubscriptionException;
import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.subscription.web.constants.SubscriptionPortletKeys;
import com.liferay.subscription.web.internal.util.SubscriptionUtil;
import com.liferay.subscription.web.internal.util.UnsubscribeUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SubscriptionPortletKeys.UNSUBSCRIBE,
		"mvc.command.name=/subscription/unsubscribe"
	},
	service = MVCActionCommand.class
)
public class UnsubscribeMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String key = ParamUtil.getString(actionRequest, "key");
		long userId = ParamUtil.getLong(actionRequest, "userId");

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, SubscriptionPortletKeys.UNSUBSCRIBE,
			PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		portletURL.setParameter("mvcPath", "/unsubscribe/unsubscribed.jsp");
		portletURL.setParameter("key", key);
		portletURL.setParameter("userId", String.valueOf(userId));

		try {
			UnsubscribeUtil.checkUser(userId, actionRequest);

			Subscription subscription = _unsubscribe(key, userId);

			portletURL.setParameter(
				"subscriptionTitle",
				SubscriptionUtil.getTitle(
					actionRequest.getLocale(), subscription));

			actionResponse.sendRedirect(portletURL.toString());
		}
		catch (NoSuchSubscriptionException nsse) {
			_log.error(nsse);

			actionResponse.sendRedirect(portletURL.toString());
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass(), pe);

			actionResponse.setRenderParameter(
				"mvcPath", "/unsubscribe/error.jsp");
		}
	}

	private Subscription _unsubscribe(String key, long userId)
		throws PortalException {

		Ticket ticket = UnsubscribeUtil.getTicket(_ticketLocalService, key);

		long subscriptionId = ticket.getClassPK();

		if (ticket.isExpired()) {
			_ticketLocalService.deleteTicket(ticket);

			throw new NoSuchTicketException("{ticketKey=" + key + "}");
		}

		Subscription subscription = _subscriptionLocalService.getSubscription(
			subscriptionId);

		UnsubscribeUtil.checkUser(userId, subscription);

		_subscriptionLocalService.deleteSubscription(subscription);

		return subscription;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnsubscribeMVCActionCommand.class);

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

}