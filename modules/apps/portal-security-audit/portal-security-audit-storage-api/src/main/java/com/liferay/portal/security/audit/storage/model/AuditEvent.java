/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AuditEvent service. Represents a row in the &quot;Audit_AuditEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.security.audit.storage.model.impl.AuditEventImpl"
)
@ProviderType
public interface AuditEvent extends AuditEventModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.security.audit.storage.model.impl.AuditEventImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AuditEvent, Long> AUDIT_EVENT_ID_ACCESSOR =
		new Accessor<AuditEvent, Long>() {

			@Override
			public Long get(AuditEvent auditEvent) {
				return auditEvent.getAuditEventId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AuditEvent> getTypeClass() {
				return AuditEvent.class;
			}

		};

}