/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommercePaymentEntryAudit service. Represents a row in the &quot;CommercePaymentEntryAudit&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAuditModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.payment.model.impl.CommercePaymentEntryAuditImpl"
)
@ProviderType
public interface CommercePaymentEntryAudit
	extends CommercePaymentEntryAuditModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.payment.model.impl.CommercePaymentEntryAuditImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePaymentEntryAudit, Long>
		COMMERCE_PAYMENT_ENTRY_AUDIT_ID_ACCESSOR =
			new Accessor<CommercePaymentEntryAudit, Long>() {

				@Override
				public Long get(
					CommercePaymentEntryAudit commercePaymentEntryAudit) {

					return commercePaymentEntryAudit.
						getCommercePaymentEntryAuditId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePaymentEntryAudit> getTypeClass() {
					return CommercePaymentEntryAudit.class;
				}

			};

}