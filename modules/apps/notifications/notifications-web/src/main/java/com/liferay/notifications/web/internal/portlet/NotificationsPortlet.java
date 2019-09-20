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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.IOException;

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
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Notifications",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.add-process-action-success-action=false",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/notifications/view.jsp",
		"javax.portlet.name=" + NotificationsPortletKeys.NOTIFICATIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class NotificationsPortlet extends MVCPortlet {

	public void deleteNotifications(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userNotificationEventIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		for (long userNotificationEventId : userNotificationEventIds) {
			_deleteUserNotificationEvent(
				themeDisplay.getUserId(), userNotificationEventId);
		}

		_addSuccessMessage(
			actionRequest, "notifications-were-deleted-successfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	public void deleteUserNotificationEvent(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		_deleteUserNotificationEvent(
			themeDisplay.getUserId(), userNotificationEventId);

		_addSuccessMessage(
			actionRequest, "notification-was-deleted-successfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	public void markAllNotificationsAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean actionRequired = ParamUtil.getBoolean(
			actionRequest, "actionRequired");

		_userNotificationEventLocalService.archiveUserNotificationEvents(
			themeDisplay.getUserId(),
			UserNotificationDeliveryConstants.TYPE_WEBSITE, actionRequired);

		_addSuccessMessage(
			actionRequest,
			"all-notifications-were-marked-as-read-successfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	public void markNotificationAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		updateArchived(themeDisplay.getUserId(), userNotificationEventId, true);

		_addSuccessMessage(
			actionRequest, "notification-was-marked-as-read-successfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	public void markNotificationAsUnread(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		updateArchived(
			themeDisplay.getUserId(), userNotificationEventId, false);

		_addSuccessMessage(
			actionRequest, "notification-was-marked-as-unread-successfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	public void markNotificationsAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userNotificationEventIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		for (long userNotificationEventId : userNotificationEventIds) {
			updateArchived(
				themeDisplay.getUserId(), userNotificationEventId, true);
		}

		_addSuccessMessage(
			actionRequest, "notifications-were-marked-as-read-successfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	public void markNotificationsAsUnread(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] userNotificationEventIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		for (long userNotificationEventId : userNotificationEventIds) {
			updateArchived(
				themeDisplay.getUserId(), userNotificationEventId, false);
		}

		_addSuccessMessage(
			actionRequest, "notifications-were-marked-as-unread-successfully");

		_sendRedirect(actionRequest, actionResponse);
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

			if (actionName.equals("deleteNotifications")) {
				deleteNotifications(actionRequest, actionResponse);
			}
			else if (actionName.equals("deleteUserNotificationEvent")) {
				deleteUserNotificationEvent(actionRequest, actionResponse);
			}
			else if (actionName.equals("markNotificationsAsRead")) {
				markNotificationsAsRead(actionRequest, actionResponse);
			}
			else if (actionName.equals("markNotificationAsRead")) {
				markNotificationAsRead(actionRequest, actionResponse);
			}
			else if (actionName.equals("markNotificationsAsUnread")) {
				markNotificationsAsUnread(actionRequest, actionResponse);
			}
			else if (actionName.equals("markNotificationAsUnread")) {
				markNotificationAsUnread(actionRequest, actionResponse);
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

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long subscriptionId = ParamUtil.getLong(
			actionRequest, "subscriptionId");
		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		_deleteSubscription(themeDisplay.getUserId(), subscriptionId);

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				userNotificationEventId);

		if ((userNotificationEvent != null) &&
			!userNotificationEvent.isArchived()) {

			updateArchived(
				themeDisplay.getUserId(), userNotificationEventId, true);
		}

		_addSuccessMessage(
			actionRequest,
			"you-have-unsubscribed-from-this-asset-successfully");
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

			_updateUserNotificationDelivery(
				themeDisplay.getUserId(), userNotificationDeliveryId, deliver);
		}

		_addSuccessMessage(
			actionRequest, "your-configuration-was-saved-sucessfully");

		_sendRedirect(actionRequest, actionResponse);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.notifications.web)(&(release.schema.version>=2.1.0)(!(release.schema.version>=3.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected void updateArchived(
			long userId, long userNotificationEventId, boolean archived)
		throws PortalException {

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				userNotificationEventId);

		if (userNotificationEvent == null) {
			return;
		}

		if (userNotificationEvent.getUserId() != userId) {
			throw new PrincipalException();
		}

		userNotificationEvent.setArchived(archived);

		_userNotificationEventLocalService.updateUserNotificationEvent(
			userNotificationEvent);
	}

	private void _addSuccessMessage(
		ActionRequest actionRequest, String message) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		SessionMessages.add(
			actionRequest, "requestProcessed",
			LanguageUtil.get(resourceBundle, message));
	}

	private void _deleteSubscription(long userId, long subscriptionId)
		throws PortalException {

		Subscription subscription = _subscriptionLocalService.fetchSubscription(
			subscriptionId);

		if (subscription == null) {
			return;
		}

		if (subscription.getUserId() != userId) {
			throw new PrincipalException();
		}

		_subscriptionLocalService.deleteSubscription(subscriptionId);
	}

	private void _deleteUserNotificationEvent(
			long userId, long userNotificationEventId)
		throws PortalException {

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.fetchUserNotificationEvent(
				userNotificationEventId);

		if (userNotificationEvent == null) {
			return;
		}

		if (userNotificationEvent.getUserId() != userId) {
			throw new PrincipalException();
		}

		_userNotificationEventLocalService.deleteUserNotificationEvent(
			userNotificationEvent);
	}

	private void _sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	private void _updateUserNotificationDelivery(
			long userId, long userNotificationDeliveryId, boolean deliver)
		throws PortalException {

		UserNotificationDelivery userNotificationDelivery =
			_userNotificationDeliveryLocalService.fetchUserNotificationDelivery(
				userNotificationDeliveryId);

		if (userNotificationDelivery == null) {
			return;
		}

		if (userNotificationDelivery.getUserId() != userId) {
			throw new PrincipalException();
		}

		_userNotificationDeliveryLocalService.updateUserNotificationDelivery(
			userNotificationDeliveryId, deliver);
	}

	@Reference(target = "(bundle.symbolic.name=com.liferay.notifications.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserNotificationDeliveryLocalService
		_userNotificationDeliveryLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}