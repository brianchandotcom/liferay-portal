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
 * The extended model interface for the NotificationTemplateAttachment service. Represents a row in the &quot;NTemplateAttachment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateAttachmentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.notification.model.impl.NotificationTemplateAttachmentImpl"
)
@ProviderType
public interface NotificationTemplateAttachment
	extends NotificationTemplateAttachmentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.notification.model.impl.NotificationTemplateAttachmentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<NotificationTemplateAttachment, Long>
		NOTIFICATION_TEMPLATE_ATTACHMENT_ID_ACCESSOR =
			new Accessor<NotificationTemplateAttachment, Long>() {

				@Override
				public Long get(
					NotificationTemplateAttachment
						notificationTemplateAttachment) {

					return notificationTemplateAttachment.
						getNotificationTemplateAttachmentId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<NotificationTemplateAttachment> getTypeClass() {
					return NotificationTemplateAttachment.class;
				}

			};

}