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
 * The extended model interface for the NotificationQueueEntryAttachment service. Represents a row in the &quot;NQueueEntryAttachment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryAttachmentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.notification.model.impl.NotificationQueueEntryAttachmentImpl"
)
@ProviderType
public interface NotificationQueueEntryAttachment
	extends NotificationQueueEntryAttachmentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.notification.model.impl.NotificationQueueEntryAttachmentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<NotificationQueueEntryAttachment, Long>
		NOTIFICATION_QUEUE_ENTRY_ATTACHMENT_ID_ACCESSOR =
			new Accessor<NotificationQueueEntryAttachment, Long>() {

				@Override
				public Long get(
					NotificationQueueEntryAttachment
						notificationQueueEntryAttachment) {

					return notificationQueueEntryAttachment.
						getNotificationQueueEntryAttachmentId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<NotificationQueueEntryAttachment> getTypeClass() {
					return NotificationQueueEntryAttachment.class;
				}

			};

}