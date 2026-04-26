/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceOrderAttachment service. Represents a row in the &quot;CommerceOrderAttachment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderAttachmentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceOrderAttachmentImpl"
)
@ProviderType
public interface CommerceOrderAttachment
	extends CommerceOrderAttachmentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceOrderAttachmentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceOrderAttachment, Long>
		COMMERCE_ORDER_ATTACHMENT_ID_ACCESSOR =
			new Accessor<CommerceOrderAttachment, Long>() {

				@Override
				public Long get(
					CommerceOrderAttachment commerceOrderAttachment) {

					return commerceOrderAttachment.
						getCommerceOrderAttachmentId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceOrderAttachment> getTypeClass() {
					return CommerceOrderAttachment.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:1262765568