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

package com.liferay.portal.security.audit.storage.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AuditEvent service. Represents a row in the &quot;Audit_AuditEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventModel
 * @see com.liferay.portal.security.audit.storage.model.impl.AuditEventImpl
 * @see com.liferay.portal.security.audit.storage.model.impl.AuditEventModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.security.audit.storage.model.impl.AuditEventImpl")
@ProviderType
public interface AuditEvent extends AuditEventModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.security.audit.storage.model.impl.AuditEventImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AuditEvent, Long> AUDIT_EVENT_ID_ACCESSOR = new Accessor<AuditEvent, Long>() {
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