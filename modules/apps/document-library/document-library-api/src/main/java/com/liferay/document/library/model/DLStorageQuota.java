/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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