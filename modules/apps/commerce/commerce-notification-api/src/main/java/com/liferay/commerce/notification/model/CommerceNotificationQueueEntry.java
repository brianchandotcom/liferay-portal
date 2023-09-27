/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceNotificationQueueEntry service. Represents a row in the &quot;CommerceNotificationQueueEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryImpl"
)
@ProviderType
public interface CommerceNotificationQueueEntry
	extends CommerceNotificationQueueEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceNotificationQueueEntry, Long>
		COMMERCE_NOTIFICATION_QUEUE_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceNotificationQueueEntry, Long>() {

				@Override
				public Long get(
					CommerceNotificationQueueEntry
						commerceNotificationQueueEntry) {

					return commerceNotificationQueueEntry.
						getCommerceNotificationQueueEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceNotificationQueueEntry> getTypeClass() {
					return CommerceNotificationQueueEntry.class;
				}

			};

}