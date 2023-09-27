/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLOpenerFileEntryReference service. Represents a row in the &quot;DLOpenerFileEntryReference&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReferenceModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceImpl"
)
@ProviderType
public interface DLOpenerFileEntryReference
	extends DLOpenerFileEntryReferenceModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.document.library.opener.model.impl.DLOpenerFileEntryReferenceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLOpenerFileEntryReference, Long>
		DL_OPENER_FILE_ENTRY_REFERENCE_ID_ACCESSOR =
			new Accessor<DLOpenerFileEntryReference, Long>() {

				@Override
				public Long get(
					DLOpenerFileEntryReference dlOpenerFileEntryReference) {

					return dlOpenerFileEntryReference.
						getDlOpenerFileEntryReferenceId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DLOpenerFileEntryReference> getTypeClass() {
					return DLOpenerFileEntryReference.class;
				}

			};

}