/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DDMStorageLink service. Represents a row in the &quot;DDMStorageLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkImpl"
)
@ProviderType
public interface DDMStorageLink extends DDMStorageLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMStorageLink, Long>
		STORAGE_LINK_ID_ACCESSOR = new Accessor<DDMStorageLink, Long>() {

			@Override
			public Long get(DDMStorageLink ddmStorageLink) {
				return ddmStorageLink.getStorageLinkId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMStorageLink> getTypeClass() {
				return DDMStorageLink.class;
			}

		};

	public String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException;

}