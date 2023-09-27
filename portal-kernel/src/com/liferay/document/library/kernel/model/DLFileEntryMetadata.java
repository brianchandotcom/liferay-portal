/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLFileEntryMetadata service. Represents a row in the &quot;DLFileEntryMetadata&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl"
)
@ProviderType
public interface DLFileEntryMetadata
	extends DLFileEntryMetadataModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileEntryMetadata, Long>
		FILE_ENTRY_METADATA_ID_ACCESSOR =
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

	public DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

}