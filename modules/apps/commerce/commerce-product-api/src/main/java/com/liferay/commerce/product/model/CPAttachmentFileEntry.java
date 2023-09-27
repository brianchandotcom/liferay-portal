/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPAttachmentFileEntry service. Represents a row in the &quot;CPAttachmentFileEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPAttachmentFileEntryImpl"
)
@ProviderType
public interface CPAttachmentFileEntry
	extends CPAttachmentFileEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPAttachmentFileEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPAttachmentFileEntry, Long>
		CP_ATTACHMENT_FILE_ENTRY_ID_ACCESSOR =
			new Accessor<CPAttachmentFileEntry, Long>() {

				@Override
				public Long get(CPAttachmentFileEntry cpAttachmentFileEntry) {
					return cpAttachmentFileEntry.getCPAttachmentFileEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPAttachmentFileEntry> getTypeClass() {
					return CPAttachmentFileEntry.class;
				}

			};

	public com.liferay.portal.kernel.repository.model.FileEntry fetchFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

}