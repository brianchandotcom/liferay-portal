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

package com.liferay.sharing.web.internal.notifications;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SharingPortletKeys.SHARING,
	service = UserNotificationHandler.class
)
public class SharingUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public SharingUserNotificationHandler() {
		setPortletId(SharingPortletKeys.SHARING);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		Locale locale = serviceContext.getLocale();

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		JSONObject userNotificationEventPayloadJSONObject =
			JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());

		SharingEntry sharingEntry = _getSharingEntry(
			userNotificationEventPayloadJSONObject);

		return ResourceBundleUtil.getString(
			resourceBundle, "x-has-shared-x-with-you-for-x",
			_getUserName(
				sharingEntry.getFromUserId(),
				userNotificationEventPayloadJSONObject, resourceBundle),
			_getSharingEntryAssetTitle(sharingEntry, locale),
			_getActionName(sharingEntry, resourceBundle));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject userNotificationEventPayloadJSONObject =
			JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());

		SharingEntry sharingEntry = _getSharingEntry(
			userNotificationEventPayloadJSONObject);

		AssetRenderer assetRenderer = getAssetRenderer(
			sharingEntry.getClassName(), sharingEntry.getClassPK());

		if (assetRenderer != null) {
			return assetRenderer.getURLViewInContext(
				serviceContext.getLiferayPortletRequest(),
				serviceContext.getLiferayPortletResponse(), null);
		}

		return super.getLink(userNotificationEvent, serviceContext);
	}

	private String _getActionName(
		SharingEntry sharingEntry, ResourceBundle resourceBundle) {

		if (_sharingEntryLocalService.hasSharingPermission(
				sharingEntry, SharingEntryActionKey.UPDATE)) {

			return ResourceBundleUtil.getString(resourceBundle, "editing");
		}
		else if (_sharingEntryLocalService.hasSharingPermission(
					 sharingEntry, SharingEntryActionKey.ADD_DISCUSSION)) {

			return ResourceBundleUtil.getString(resourceBundle, "commenting");
		}
		else if (_sharingEntryLocalService.hasSharingPermission(
					 sharingEntry, SharingEntryActionKey.VIEW)) {

			return ResourceBundleUtil.getString(resourceBundle, "viewing");
		}
		else {
			return ResourceBundleUtil.getString(resourceBundle, "nothing");
		}
	}

	private SharingEntry _getSharingEntry(
			JSONObject userNotificationEventPayloadJSONObject)
		throws PortalException {

		long sharingEntryId = userNotificationEventPayloadJSONObject.getLong(
			"classPK");

		return _sharingEntryLocalService.getSharingEntry(sharingEntryId);
	}

	private String _getSharingEntryAssetTitle(
		SharingEntry sharingEntry, Locale locale) {

		AssetRenderer assetRenderer = getAssetRenderer(
			sharingEntry.getClassName(), sharingEntry.getClassPK());

		if (assetRenderer != null) {
			return assetRenderer.getTitle(locale);
		}
		else {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(locale);

			return ResourceBundleUtil.getString(resourceBundle, "something");
		}
	}

	private String _getUserName(
		long userId, JSONObject userNotificationEventPayloadJSONObject,
		ResourceBundle resourceBundle) {

		User user = _userLocalService.fetchUserById(userId);

		if (user != null) {
			return HtmlUtil.escape(user.getFullName());
		}

		String fromUserFullName =
			userNotificationEventPayloadJSONObject.getString(
				"fromUserFullName");

		if (Validator.isNotNull(fromUserFullName)) {
			return fromUserFullName;
		}

		return ResourceBundleUtil.getString(resourceBundle, "someone");
	}

	@Reference(target = "(bundle.symbolic.name=com.liferay.sharing.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}