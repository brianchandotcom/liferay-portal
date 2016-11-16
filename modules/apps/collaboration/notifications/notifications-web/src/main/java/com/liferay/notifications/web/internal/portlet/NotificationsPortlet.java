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

package com.liferay.notifications.web.internal.portlet;

import com.liferay.notifications.web.internal.constants.NotificationsPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=notifications-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Notifications",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.add-process-action-success-action=false",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/notifications/view.jsp",
		"javax.portlet.name=" + NotificationsPortletKeys.NOTIFICATIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class NotificationsPortlet extends MVCPortlet {

	public void deleteUserNotificationEvent(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		try {
			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEventId);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void markAllAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] userNotificationEventIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		for (long userNotificationEventId : userNotificationEventIds) {
			updateArchived(userNotificationEventId);
		}
	}

	public void markAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		updateArchived(userNotificationEventId);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		try {
			String actionName = ParamUtil.getString(
				actionRequest, ActionRequest.ACTION_NAME);

			if (actionName.equals("deleteUserNotificationEvent")) {
				deleteUserNotificationEvent(actionRequest, actionResponse);
			}
			else if (actionName.equals("markAllAsRead")) {
				markAllAsRead(actionRequest, actionResponse);
			}
			else if (actionName.equals("markAsRead")) {
				markAsRead(actionRequest, actionResponse);
			}
			else if (actionName.equals("unsubscribe")) {
				unsubscribe(actionRequest, actionResponse);
			}
			else if (actionName.equals("updateUserNotificationDelivery")) {
				updateUserNotificationDelivery(actionRequest, actionResponse);
			}
			else {
				super.processAction(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void unsubscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long subscriptionId = ParamUtil.getLong(
			actionRequest, "subscriptionId");
		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		_subscriptionLocalService.deleteSubscription(subscriptionId);

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				userNotificationEventId);

		if (userNotificationEvent != null) {
			if (!userNotificationEvent.isArchived()) {
				updateArchived(userNotificationEventId);
			}
		}
	}

	public void updateUserNotificationDelivery(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userNotificationDeliveryIds = ParamUtil.getLongValues(
			actionRequest, "userNotificationDeliveryIds");

		for (long userNotificationDeliveryId : userNotificationDeliveryIds) {
			boolean deliver = ParamUtil.getBoolean(
				actionRequest, String.valueOf(userNotificationDeliveryId));

			_userNotificationDeliveryLocalService.
				updateUserNotificationDelivery(
					userNotificationDeliveryId, deliver);
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				LanguageUtil.getLanguageId(themeDisplay.getLocale()));

		SessionMessages.add(
			actionRequest, "requestProcessed",
			LanguageUtil.get(
				resourceBundle, "your-configuration-was-saved-sucessfully"));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.notifications.web)(release.schema.version=2.1.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserNotificationDeliveryLocalService(
		UserNotificationDeliveryLocalService
			userNotificationDeliveryLocalService) {

		_userNotificationDeliveryLocalService =
			userNotificationDeliveryLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserNotificationEventLocalService(
		UserNotificationEventLocalService userNotificationEventLocalService) {

		_userNotificationEventLocalService = userNotificationEventLocalService;
	}

	protected void updateArchived(long userNotificationEventId)
		throws Exception {

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				userNotificationEventId);

		if (userNotificationEvent == null) {
			return;
		}

		userNotificationEvent.setArchived(true);

		_userNotificationEventLocalService.updateUserNotificationEvent(
			userNotificationEvent);
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.notifications.web)",
		unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	private SubscriptionLocalService _subscriptionLocalService;
	private UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService;
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}