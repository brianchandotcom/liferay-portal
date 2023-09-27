/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.notifications;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Jonathan Lee
 */
public abstract class BaseUserNotificationHandler
	implements UserNotificationHandler {

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public String getSelector() {
		return _selector;
	}

	@Override
	public UserNotificationFeedEntry interpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			UserNotificationFeedEntry userNotificationFeedEntry = doInterpret(
				userNotificationEvent, serviceContext);

			if (userNotificationFeedEntry != null) {
				userNotificationFeedEntry.setOpenDialog(isOpenDialog());
				userNotificationFeedEntry.setPortletId(getPortletId());
			}
			else {
				Locale locale = serviceContext.getLocale();

				String title = LanguageUtil.get(
					locale, "notification-no-longer-applies");

				String body = StringUtil.replace(
					_BODY_TEMPLATE_DEFAULT,
					new String[] {"[$BODY$]", "[$TITLE$]"},
					new String[] {
						LanguageUtil.format(
							locale, "notification-for-x-was-deleted",
							PortalUtil.getPortletTitle(getPortletId(), locale),
							false),
						title
					});

				userNotificationFeedEntry = new UserNotificationFeedEntry(
					false, body, StringPool.BLANK, false, title);
			}

			return userNotificationFeedEntry;
		}
		catch (Exception exception) {
			_log.error("Unable to interpret notification", exception);
		}

		return null;
	}

	@Override
	public boolean isDeliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException {

		UserNotificationDefinition userNotificationDefinition =
			UserNotificationManagerUtil.fetchUserNotificationDefinition(
				_portletId, classNameId, notificationType);

		if (userNotificationDefinition == null) {
			if (deliveryType == UserNotificationDeliveryConstants.TYPE_EMAIL) {
				return true;
			}

			return false;
		}

		UserNotificationDeliveryType userNotificationDeliveryType =
			userNotificationDefinition.getUserNotificationDeliveryType(
				deliveryType);

		if (userNotificationDeliveryType == null) {
			return false;
		}

		UserNotificationDelivery userNotificationDelivery =
			UserNotificationDeliveryLocalServiceUtil.
				fetchUserNotificationDelivery(
					userId, _portletId, classNameId, notificationType,
					deliveryType);

		if (userNotificationDelivery != null) {
			return userNotificationDelivery.isDeliver();
		}

		return userNotificationDeliveryType.isDefault();
	}

	@Override
	public boolean isOpenDialog() {
		return _openDialog;
	}

	protected UserNotificationFeedEntry doInterpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		String body = getBody(userNotificationEvent, serviceContext);

		if (Validator.isNull(body)) {
			return null;
		}

		String link = getLink(userNotificationEvent, serviceContext);
		boolean applicable = isApplicable(
			userNotificationEvent, serviceContext);

		return new UserNotificationFeedEntry(
			isActionable(), body, link, applicable,
			getTitle(userNotificationEvent, serviceContext));
	}

	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getBodyTemplate() throws Exception {
		if (isActionable()) {
			return StringBundler.concat(
				"<div class=\"title\">[$TITLE$]</div><div class=\"body\"><div ",
				"class=\"button-holder\"><a class=\"btn btn-primary ",
				"user-notification-action\" ",
				"href=\"[$CONFIRM_URL$]\">[$CONFIRM$]</a><a class=\"btn ",
				"btn-secondary user-notification-action\" ",
				"href=\"[$IGNORE_URL$]\">[$IGNORE$]</a></div></div>");
		}

		return _BODY_TEMPLATE_DEFAULT;
	}

	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getTitle(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected boolean isActionable() {
		return _actionable;
	}

	protected void setActionable(boolean actionable) {
		_actionable = actionable;
	}

	protected void setOpenDialog(boolean openDialog) {
		_openDialog = openDialog;
	}

	protected void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected void setSelector(String selector) {
		_selector = selector;
	}

	private static final String _BODY_TEMPLATE_DEFAULT =
		"<div class=\"title\">[$TITLE$]</div><div class=\"body\">[$BODY$]" +
			"</div>";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUserNotificationHandler.class);

	private boolean _actionable;
	private boolean _openDialog;
	private String _portletId;
	private String _selector = StringPool.BLANK;

}