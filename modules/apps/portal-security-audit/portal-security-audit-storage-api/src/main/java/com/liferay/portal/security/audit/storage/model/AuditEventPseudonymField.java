/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AuditEventPseudonymField service. Represents a row in the &quot;Audit_AuditEventPseudonymField&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymFieldModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldImpl"
)
@ProviderType
public interface AuditEventPseudonymField
	extends AuditEventPseudonymFieldModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventPseudonymFieldImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AuditEventPseudonymField, Long>
		AUDIT_EVENT_PSEUDONYM_FIELD_ID_ACCESSOR =
			new Accessor<AuditEventPseudonymField, Long>() {

				@Override
				public Long get(
					AuditEventPseudonymField auditEventPseudonymField) {

					return auditEventPseudonymField.
						getAuditEventPseudonymFieldId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AuditEventPseudonymField> getTypeClass() {
					return AuditEventPseudonymField.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:1778393578