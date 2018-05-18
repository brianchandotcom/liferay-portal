/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.notification.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceNotificationAttachment service. Represents a row in the &quot;CNotificationAttachment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationAttachmentModel
 * @see com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl
 * @see com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl")
@ProviderType
public interface CommerceNotificationAttachment
	extends CommerceNotificationAttachmentModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.notification.model.impl.CommerceNotificationAttachmentImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceNotificationAttachment, Long> COMMERCE_NOTIFICATION_ATTACHMENT_ID_ACCESSOR =
		new Accessor<CommerceNotificationAttachment, Long>() {
			@Override
			public Long get(
				CommerceNotificationAttachment commerceNotificationAttachment) {
				return commerceNotificationAttachment.getCommerceNotificationAttachmentId();
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