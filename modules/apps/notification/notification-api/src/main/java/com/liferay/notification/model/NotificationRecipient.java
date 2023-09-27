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
 * The extended model interface for the NotificationRecipient service. Represents a row in the &quot;NotificationRecipient&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipientModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.notification.model.impl.NotificationRecipientImpl"
)
@ProviderType
public interface NotificationRecipient
	extends NotificationRecipientModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.notification.model.impl.NotificationRecipientImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<NotificationRecipient, Long>
		NOTIFICATION_RECIPIENT_ID_ACCESSOR =
			new Accessor<NotificationRecipient, Long>() {

				@Override
				public Long get(NotificationRecipient notificationRecipient) {
					return notificationRecipient.getNotificationRecipientId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<NotificationRecipient> getTypeClass() {
					return NotificationRecipient.class;
				}

			};

	public java.util.List<NotificationRecipientSetting>
		getNotificationRecipientSettings();

}