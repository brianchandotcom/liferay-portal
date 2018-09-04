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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
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

		SharingEntry sharingEntry = _getSharingEntry(userNotificationEvent);

		Locale locale = serviceContext.getLocale();

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "x-has-shared-x-with-you-for-x",
			_getUserName(sharingEntry.getFromUserId(), resourceBundle),
			_getSharedObjectName(sharingEntry, locale),
			_getActionName(sharingEntry, resourceBundle));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		SharingEntry sharingEntry = _getSharingEntry(userNotificationEvent);

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

		if (_hasAction(sharingEntry, SharingEntryActionKey.UPDATE)) {
			return ResourceBundleUtil.getString(resourceBundle, "editing");
		}
		else if (_hasAction(
					 sharingEntry, SharingEntryActionKey.ADD_DISCUSSION)) {

			return ResourceBundleUtil.getString(resourceBundle, "commenting");
		}
		else if (_hasAction(sharingEntry, SharingEntryActionKey.VIEW)) {
			return ResourceBundleUtil.getString(resourceBundle, "viewing");
		}
		else {
			return ResourceBundleUtil.getString(resourceBundle, "nothing");
		}
	}

	private String _getSharedObjectName(
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

	private SharingEntry _getSharingEntry(
			UserNotificationEvent userNotificationEvent)
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long sharingEntryId = jsonObject.getLong("classPK");

		return _sharingEntryLocalService.fetchSharingEntry(sharingEntryId);
	}

	private String _getUserName(long userId, ResourceBundle resourceBundle) {
		try {
			if (userId <= 0) {
				return ResourceBundleUtil.getString(resourceBundle, "someone");
			}

			User user = _userLocalService.getUserById(userId);

			return HtmlUtil.escape(user.getFullName());
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	private boolean _hasAction(
		SharingEntry sharingEntry,
		SharingEntryActionKey sharingEntryActionKey) {

		long bitwiseVaue = sharingEntryActionKey.getBitwiseVaue();

		if ((bitwiseVaue & sharingEntry.getActionIds()) != 0) {
			return true;
		}

		return false;
	}

	@Reference(target = "(bundle.symbolic.name=com.liferay.sharing.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}