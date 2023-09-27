/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.sync.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLSyncEvent service. Represents a row in the &quot;DLSyncEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncEventModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.document.library.sync.model.impl.DLSyncEventImpl"
)
@ProviderType
public interface DLSyncEvent extends DLSyncEventModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.document.library.sync.model.impl.DLSyncEventImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLSyncEvent, Long> SYNC_EVENT_ID_ACCESSOR =
		new Accessor<DLSyncEvent, Long>() {

			@Override
			public Long get(DLSyncEvent dlSyncEvent) {
				return dlSyncEvent.getSyncEventId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLSyncEvent> getTypeClass() {
				return DLSyncEvent.class;
			}

		};

}