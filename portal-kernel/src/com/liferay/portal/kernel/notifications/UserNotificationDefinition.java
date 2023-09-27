/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Jonathan Lee
 */
public class UserNotificationDefinition {

	public static final int NOTIFICATION_TYPE_ADD_ENTRY = 0;

	public static final int NOTIFICATION_TYPE_EXPIRED_ENTRY = 3;

	public static final int NOTIFICATION_TYPE_REVIEW_ENTRY = 2;

	public static final int NOTIFICATION_TYPE_UPDATE_ENTRY = 1;

	public UserNotificationDefinition(
		String portletId, long classNameId, int notificationType,
		String description) {

		_portletId = portletId;
		_classNameId = classNameId;
		_notificationType = notificationType;
		_description = description;
	}

	public void addUserNotificationDeliveryType(
		UserNotificationDeliveryType userNotificationDeliveryType) {

		_userNotificationDeliveryTypes.put(
			userNotificationDeliveryType.getType(),
			userNotificationDeliveryType);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public String getDescription(Locale locale) {
		String description = ResourceBundleUtil.getString(
			getResourceBundle(locale), _description);

		if (description != null) {
			return description;
		}

		return _description;
	}

	public int getNotificationType() {
		return _notificationType;
	}

	public String getPortletId() {
		return _portletId;
	}

	public UserNotificationDeliveryType getUserNotificationDeliveryType(
		int deliveryType) {

		return _userNotificationDeliveryTypes.get(deliveryType);
	}

	public Map<Integer, UserNotificationDeliveryType>
		getUserNotificationDeliveryTypes() {

		return _userNotificationDeliveryTypes;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		try {
			return ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(missingResourceException);
			}

			return ResourceBundleUtil.getBundle(
				"content.Language", locale,
				PortalClassLoaderUtil.getClassLoader());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserNotificationDefinition.class);

	private final long _classNameId;
	private final String _description;
	private final int _notificationType;
	private final String _portletId;
	private final Map<Integer, UserNotificationDeliveryType>
		_userNotificationDeliveryTypes = new HashMap<>();

}