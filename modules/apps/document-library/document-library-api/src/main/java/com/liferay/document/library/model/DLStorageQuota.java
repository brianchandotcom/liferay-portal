/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLStorageQuota service. Represents a row in the &quot;DLStorageQuota&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLStorageQuotaModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.document.library.model.impl.DLStorageQuotaImpl"
)
@ProviderType
public interface DLStorageQuota extends DLStorageQuotaModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.document.library.model.impl.DLStorageQuotaImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLStorageQuota, Long>
		DL_STORAGE_QUOTA_ID_ACCESSOR = new Accessor<DLStorageQuota, Long>() {

			@Override
			public Long get(DLStorageQuota dlStorageQuota) {
				return dlStorageQuota.getDlStorageQuotaId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLStorageQuota> getTypeClass() {
				return DLStorageQuota.class;
			}

		};

}