/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDVirtualSettingFileEntry service. Represents a row in the &quot;CPDVirtualSettingFileEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryImpl"
)
@ProviderType
public interface CPDVirtualSettingFileEntry
	extends CPDVirtualSettingFileEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDVirtualSettingFileEntry, Long>
		CP_DEFINITION_VIRTUAL_SETTING_FILE_ENTRY_ID_ACCESSOR =
			new Accessor<CPDVirtualSettingFileEntry, Long>() {

				@Override
				public Long get(
					CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry) {

					return cpdVirtualSettingFileEntry.
						getCPDefinitionVirtualSettingFileEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDVirtualSettingFileEntry> getTypeClass() {
					return CPDVirtualSettingFileEntry.class;
				}

			};

	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting()
		throws com.liferay.portal.kernel.exception.PortalException;

}