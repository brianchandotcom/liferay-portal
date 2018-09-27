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

package com.liferay.sharing.notifications.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.web.interpreter.SharingEntryInterpreterProvider;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = SharingNotificationUtil.class)
public class SharingNotificationUtil {

	public String getEntryURL(
			SharingEntry sharingEntry,
			LiferayPortletRequest liferayPortletRequest)
		throws Exception {

		if (liferayPortletRequest != null) {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				liferayPortletRequest, SharingEntry.class.getName(),
				PortletProvider.Action.PREVIEW);

			portletURL.setParameter(
				"sharingEntryId",
				String.valueOf(sharingEntry.getSharingEntryId()));

			return portletURL.toString();
		}

		return null;
	}

	public String getNotificationMessage(
			SharingEntry sharingEntry, Locale locale)
		throws PortalException {

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "x-has-shared-x-with-you-for-x",
			_getUserName(sharingEntry, resourceBundle),
			getSharingEntryObjectTitle(sharingEntry, locale),
			_getActionName(sharingEntry, resourceBundle));
	}

	public String getSharingEntryObjectTitle(
		SharingEntry sharingEntry, Locale locale) {

		SharingEntryInterpreter<Object> sharingEntryInterpreter =
			_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
				sharingEntry);

		if (sharingEntryInterpreter != null) {
			return sharingEntryInterpreter.getTitle(sharingEntry);
		}
		else {
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(locale);

			return ResourceBundleUtil.getString(resourceBundle, "something");
		}
	}

	private String _getActionName(
		SharingEntry sharingEntry, ResourceBundle resourceBundle) {

		if (_sharingEntryLocalService.hasSharingPermission(
				sharingEntry, SharingEntryAction.UPDATE)) {

			return ResourceBundleUtil.getString(resourceBundle, "editing");
		}
		else if (_sharingEntryLocalService.hasSharingPermission(
					sharingEntry, SharingEntryAction.ADD_DISCUSSION)) {

			return ResourceBundleUtil.getString(resourceBundle, "commenting");
		}
		else if (_sharingEntryLocalService.hasSharingPermission(
					sharingEntry, SharingEntryAction.VIEW)) {

			return ResourceBundleUtil.getString(resourceBundle, "viewing");
		}

		return ResourceBundleUtil.getString(resourceBundle, "nothing");
	}

	private String _getUserName(
		SharingEntry sharingEntry, ResourceBundle resourceBundle) {

		User user = _userLocalService.fetchUserById(
			sharingEntry.getFromUserId());

		if (user != null) {
			return HtmlUtil.escape(user.getFullName());
		}

		return ResourceBundleUtil.getString(resourceBundle, "someone");
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.sharing.notifications)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}