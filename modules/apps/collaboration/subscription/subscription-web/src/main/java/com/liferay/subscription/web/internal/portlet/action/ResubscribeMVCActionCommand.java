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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
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
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SubscriptionPortletKeys.UNSUBSCRIBE,
		"mvc.command.name=/subscription/resubscribe"
	},
	service = MVCActionCommand.class
)
public class ResubscribeMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String key = ParamUtil.getString(actionRequest, "key");
			long userId = ParamUtil.getLong(actionRequest, "userId");

			UnsubscribeUtil.checkUser(userId, actionRequest);

			Ticket ticket = UnsubscribeUtil.getTicket(_ticketLocalService, key);

			Subscription subscription =
				_subscriptionLocalService.fetchSubscription(
					ticket.getClassPK());

			if (subscription == null) {
				subscription = UnsubscribeUtil.getFromSession(actionRequest);
			}

			if (subscription == null) {
				throw new NoSuchSubscriptionException();
			}

			UnsubscribeUtil.checkUser(userId, subscription);

			PortletURL portletURL = PortletURLFactoryUtil.create(
				actionRequest, SubscriptionPortletKeys.UNSUBSCRIBE,
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.MAXIMIZED);

			portletURL.setParameter("mvcPath", "/unsubscribe/resubscribed.jsp");
			portletURL.setParameter("key", key);
			portletURL.setParameter("userId", String.valueOf(userId));
			portletURL.setParameter(
				"subscriptionTitle",
				SubscriptionUtil.getTitle(
					actionRequest.getLocale(), subscription));

			actionResponse.sendRedirect(portletURL.toString());
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass(), pe);

			actionResponse.setRenderParameter(
				"mvcPath", "/unsubscribe/error.jsp");
		}
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}