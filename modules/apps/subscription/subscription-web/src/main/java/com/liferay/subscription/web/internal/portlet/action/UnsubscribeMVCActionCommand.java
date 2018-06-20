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

import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.subscription.exception.NoSuchSubscriptionException;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.subscription.web.internal.constants.SubscriptionConstants;
import com.liferay.subscription.web.internal.constants.SubscriptionPortletKeys;

import java.util.Locale;
import java.util.ResourceBundle;

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
			_checkUser(userId, actionRequest);

			Subscription subscription = _unsubscribe(key, userId);

			portletURL.setParameter(
				"subscriptionTitle",
				_getTitle(actionRequest.getLocale(), subscription));

			actionResponse.sendRedirect(portletURL.toString());
		}
		catch (NoSuchSubscriptionException nsse) {
			_log.error(nsse, nsse);

			actionResponse.sendRedirect(portletURL.toString());
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass(), pe);

			actionResponse.setRenderParameter(
				"mvcPath", "/unsubscribe/error.jsp");
		}
	}

	private void _checkUser(long userId, ActionRequest actionRequest)
		throws PortalException {

		User user = _portal.getUser(actionRequest);

		if ((user != null) && (userId != user.getUserId())) {
			throw new PrincipalException();
		}
	}

	private void _checkUser(long userId, Subscription subscription)
		throws PrincipalException {

		if ((subscription != null) && (subscription.getUserId() != userId)) {
			throw new PrincipalException();
		}
	}

	private Ticket _getTicket(String key) throws PortalException {
		Ticket ticket = _ticketLocalService.getTicket(key);

		if (ticket.getType() != SubscriptionConstants.TICKET_TYPE) {
			throw new NoSuchTicketException("Invalid type " + ticket.getType());
		}

		String className = ticket.getClassName();

		if (!className.equals(Subscription.class.getName())) {
			throw new NoSuchTicketException("Invalid className " + className);
		}

		return ticket;
	}

	private String _getTitle(Locale locale, Subscription subscription)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(subscription.getClassPK());

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.format(
			resourceBundle, "blog-at-x", group.getDescriptiveName(locale));
	}

	private Subscription _unsubscribe(String key, long userId)
		throws PortalException {

		Ticket ticket = _getTicket(key);

		long subscriptionId = ticket.getClassPK();

		if (ticket.isExpired()) {
			_ticketLocalService.deleteTicket(ticket);

			throw new NoSuchTicketException("{ticketKey=" + key + "}");
		}

		Subscription subscription = _subscriptionLocalService.getSubscription(
			subscriptionId);

		_checkUser(userId, subscription);

		_subscriptionLocalService.deleteSubscription(subscription);

		return subscription;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnsubscribeMVCActionCommand.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

}