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
 * The extended model interface for the CommerceNotificationAttachment service. Represents a row in the &quot;CNotificationAttachment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationAttachmentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl"
)
@ProviderType
public interface CommerceNotificationAttachment
	extends CommerceNotificationAttachmentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceNotificationAttachment, Long>
		COMMERCE_NOTIFICATION_ATTACHMENT_ID_ACCESSOR =
			new Accessor<CommerceNotificationAttachment, Long>() {

				@Override
				public Long get(
					CommerceNotificationAttachment
						commerceNotificationAttachment) {

					return commerceNotificationAttachment.
						getCommerceNotificationAttachmentId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceNotificationAttachment> getTypeClass() {
					return CommerceNotificationAttachment.class;
				}

			};

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

}