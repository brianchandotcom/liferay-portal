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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long sharingEntryId = jsonObject.getLong("classPK");

		SharingEntry sharingEntry = _sharingEntryLocalService.fetchSharingEntry(
			sharingEntryId);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				serviceContext.getLocale());

		return ResourceBundleUtil.getString(
			resourceBundle, "x-has-shared-x-with-you-for-x",
			_getUserNameLink(sharingEntry.getFromUserId(), serviceContext),
			_getSharedObjectLink(sharingEntry, serviceContext),
			_getActionDescriptiveName(
				sharingEntry, serviceContext.getLocale()));
	}

	private String _getActionDescriptiveName(
		SharingEntry sharingEntry, Locale locale) {

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

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

	private String _getSharedObjectLink(
			SharingEntry sharingEntry, ServiceContext serviceContext)
		throws Exception {

		AssetRenderer assetRenderer = getAssetRenderer(
			sharingEntry.getClassName(), sharingEntry.getClassPK());

		if (assetRenderer != null) {
			return StringBundler.concat(
				"<a href=\"",
				assetRenderer.getURLViewInContext(
					serviceContext.getLiferayPortletRequest(),
					serviceContext.getLiferayPortletResponse(), null),
				"\">", assetRenderer.getTitle(serviceContext.getLocale()),
				"</a>");
		}
		else {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					serviceContext.getLocale());

			return ResourceBundleUtil.getString(resourceBundle, "something");
		}
	}

	private String _getUserNameLink(
		long userId, ServiceContext serviceContext) {

		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = _userLocalService.getUserById(userId);

			String userName = user.getFullName();

			String userDisplayURL = user.getDisplayURL(
				serviceContext.getThemeDisplay());

			return StringBundler.concat(
				"<a href=\"", userDisplayURL, "\">", HtmlUtil.escape(userName),
				"</a>");
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