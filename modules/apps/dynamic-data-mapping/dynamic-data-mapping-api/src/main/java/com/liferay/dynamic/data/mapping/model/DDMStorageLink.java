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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DDMStorageLink service. Represents a row in the &quot;DDMStorageLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkModelImpl
 * @generated
 */
@ProviderType
public interface DDMStorageLink extends DDMStorageLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMStorageLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMStorageLink, Long> STORAGE_LINK_ID_ACCESSOR = new Accessor<DDMStorageLink, Long>() {
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

	public java.lang.String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException;
}