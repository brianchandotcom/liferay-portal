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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DLFileEntryMetadata service. Represents a row in the &quot;DLFileEntryMetadata&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl
 * @generated
 */
@ProviderType
public interface DLFileEntryMetadata extends DLFileEntryMetadataModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileEntryMetadata, Long> FILE_ENTRY_METADATA_ID_ACCESSOR =
		new Accessor<DLFileEntryMetadata, Long>() {
			@Override
			public Long get(DLFileEntryMetadata dlFileEntryMetadata) {
				return dlFileEntryMetadata.getFileEntryMetadataId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLFileEntryMetadata> getTypeClass() {
				return DLFileEntryMetadata.class;
			}
		};

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException;
}