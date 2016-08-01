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

package com.liferay.contacts.web.internal.notifications;

import com.liferay.contacts.web.internal.constants.ContactsPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.model.SocialRequestConstants;
import com.liferay.social.kernel.service.SocialRequestLocalService;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + ContactsPortletKeys.CONTACTS_CENTER},
	service = UserNotificationHandler.class
)
public class ContactsCenterUserNotificationHandler
	extends BaseUserNotificationHandler {

	public ContactsCenterUserNotificationHandler() {
		setActionable(true);
		setPortletId(ContactsPortletKeys.CONTACTS_CENTER);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long socialRequestId = jsonObject.getLong("classPK");

		SocialRequest socialRequest =
			_socialRequestLocalService.fetchSocialRequest(socialRequestId);

		if (socialRequest == null) {
			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		String creatorUserName = getUserNameLink(
			socialRequest.getUserId(), serviceContext);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				LocaleUtil.toLanguageId(serviceContext.getLocale()));

		String title = StringPool.BLANK;

		if (socialRequest.getType() ==
				SocialRelationConstants.TYPE_BI_CONNECTION) {

			title = ResourceBundleUtil.getString(
				resourceBundle, serviceContext.getLocale(),
				"request-social-networking-summary-add-connection",
				new Object[] {creatorUserName});
		}
		else {
			title = ResourceBundleUtil.getString(
				resourceBundle, serviceContext.getLocale(),
				"x-sends-you-a-social-relationship-request",
				new Object[] {creatorUserName});
		}

		if ((socialRequest.getStatus() !=
				SocialRequestConstants.STATUS_PENDING) ||
			(socialRequest.getModifiedDate() >
				userNotificationEvent.getTimestamp())) {

			return StringUtil.replace(
				_BODY, new String[] {"[$BODY$]", "[$TITLE$]"},
				new String[] {StringPool.BLANK, title});
		}

		LiferayPortletResponse liferayPortletResponse =
			serviceContext.getLiferayPortletResponse();

		PortletURL confirmURL = liferayPortletResponse.createActionURL(
			ContactsPortletKeys.CONTACTS_CENTER);

		confirmURL.setParameter(
			ActionRequest.ACTION_NAME, "updateSocialRequest");
		confirmURL.setParameter("redirect", serviceContext.getLayoutFullURL());
		confirmURL.setParameter(
			"socialRequestId", String.valueOf(socialRequestId));
		confirmURL.setParameter(
			"status", String.valueOf(SocialRequestConstants.STATUS_CONFIRM));
		confirmURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		confirmURL.setWindowState(WindowState.NORMAL);

		PortletURL ignoreURL = liferayPortletResponse.createActionURL(
			ContactsPortletKeys.CONTACTS_CENTER);

		ignoreURL.setParameter(
			ActionRequest.ACTION_NAME, "updateSocialRequest");
		ignoreURL.setParameter("redirect", serviceContext.getLayoutFullURL());
		ignoreURL.setParameter(
			"socialRequestId", String.valueOf(socialRequestId));
		ignoreURL.setParameter(
			"status", String.valueOf(SocialRequestConstants.STATUS_IGNORE));
		ignoreURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		ignoreURL.setWindowState(WindowState.NORMAL);

		return StringUtil.replace(
			getBodyTemplate(),
			new String[] {
				"[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
				"[$IGNORE_URL$]", "[$TITLE$]"
			},
			new String[] {
				serviceContext.translate("confirm"), confirmURL.toString(),
				serviceContext.translate("ignore"), ignoreURL.toString(), title
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getUserNameLink(
		long userId, ServiceContext serviceContext) {

		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = _userLocalService.getUserById(userId);

			String userName = user.getFullName();

			String userDisplayURL = user.getDisplayURL(
				serviceContext.getThemeDisplay());

			return "<a href=\"" + userDisplayURL + "\">" +
				HtmlUtil.escape(userName) + "</a>";
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.contacts.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader, LanguageResources.RESOURCE_BUNDLE_LOADER);
	}

	private static final String _BODY =
		"<div class=\"title\">[$TITLE$]</div><div class=\"body\">[$BODY$]" +
			"</div>";

	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SocialRequestLocalService _socialRequestLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}