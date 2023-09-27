/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the NotificationRecipientSetting service. Represents a row in the &quot;NotificationRecipientSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientSettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.notification.model.impl.NotificationRecipientSettingImpl"
)
@ProviderType
public interface NotificationRecipientSetting
	extends NotificationRecipientSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.notification.model.impl.NotificationRecipientSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<NotificationRecipientSetting, Long>
		NOTIFICATION_RECIPIENT_SETTING_ID_ACCESSOR =
			new Accessor<NotificationRecipientSetting, Long>() {

				@Override
				public Long get(
					NotificationRecipientSetting notificationRecipientSetting) {

					return notificationRecipientSetting.
						getNotificationRecipientSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<NotificationRecipientSetting> getTypeClass() {
					return NotificationRecipientSetting.class;
				}

			};

}