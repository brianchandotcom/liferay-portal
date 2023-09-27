/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PushNotificationsDevice service. Represents a row in the &quot;PushNotificationsDevice&quot; database table, with each column mapped to a property of this class.
 *
 * @author Bruno Farache
 * @see PushNotificationsDeviceModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.push.notifications.model.impl.PushNotificationsDeviceImpl"
)
@ProviderType
public interface PushNotificationsDevice
	extends PersistedModel, PushNotificationsDeviceModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.push.notifications.model.impl.PushNotificationsDeviceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PushNotificationsDevice, Long>
		PUSH_NOTIFICATIONS_DEVICE_ID_ACCESSOR =
			new Accessor<PushNotificationsDevice, Long>() {

				@Override
				public Long get(
					PushNotificationsDevice pushNotificationsDevice) {

					return pushNotificationsDevice.
						getPushNotificationsDeviceId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<PushNotificationsDevice> getTypeClass() {
					return PushNotificationsDevice.class;
				}

			};

}