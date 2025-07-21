/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.impl;

import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.base.NotificationRecipientSettingLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationRecipientSetting",
	service = AopService.class
)
public class NotificationRecipientSettingLocalServiceImpl
	extends NotificationRecipientSettingLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationRecipientSetting addNotificationRecipientSetting(
			long userId, long notificationRecipientId, String name,
			Object value)
		throws PortalException {

		NotificationRecipientSetting notificationRecipientSetting =
			notificationRecipientSettingPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		notificationRecipientSetting.setCompanyId(user.getCompanyId());
		notificationRecipientSetting.setUserId(user.getUserId());
		notificationRecipientSetting.setUserName(user.getFullName());

		notificationRecipientSetting.setNotificationRecipientId(
			notificationRecipientId);
		notificationRecipientSetting.setName(name);

		if (value instanceof String) {
			notificationRecipientSetting.setValue(String.valueOf(value));
		}
		else {
			notificationRecipientSetting.setValueMap(
				(Map<Locale, String>)value);
		}

		return notificationRecipientSettingPersistence.update(
			notificationRecipientSetting);
	}

	@Override
	public NotificationRecipientSetting fetchNotificationRecipientSetting(
		long notificationRecipientId, String name) {

		return notificationRecipientSettingPersistence.fetchByNRI_N(
			notificationRecipientId, name);
	}

	@Override
	public List<NotificationRecipientSetting> getNotificationRecipientSettings(
		long notificationRecipientId) {

		return notificationRecipientSettingPersistence.
			findByNotificationRecipientId(notificationRecipientId);
	}

	@Reference
	private UserLocalService _userLocalService;

}