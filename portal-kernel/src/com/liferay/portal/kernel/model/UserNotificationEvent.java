/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the UserNotificationEvent service. Represents a row in the &quot;UserNotificationEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.model.impl.UserNotificationEventImpl"
)
@ProviderType
public interface UserNotificationEvent
	extends PersistedModel, UserNotificationEventModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.UserNotificationEventImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserNotificationEvent, Long>
		USER_NOTIFICATION_EVENT_ID_ACCESSOR =
			new Accessor<UserNotificationEvent, Long>() {

				@Override
				public Long get(UserNotificationEvent userNotificationEvent) {
					return userNotificationEvent.getUserNotificationEventId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<UserNotificationEvent> getTypeClass() {
					return UserNotificationEvent.class;
				}

			};

}