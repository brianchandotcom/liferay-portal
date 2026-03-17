/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the VersionedEntry service. Represents a row in the &quot;VersionedEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.compat710.model.impl.VersionedEntryImpl"
)
@ProviderType
public interface VersionedEntry extends PersistedModel, VersionedEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.VersionedEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<VersionedEntry, Long>
		VERSIONED_ENTRY_ID_ACCESSOR = new Accessor<VersionedEntry, Long>() {

			@Override
			public Long get(VersionedEntry versionedEntry) {
				return versionedEntry.getVersionedEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<VersionedEntry> getTypeClass() {
				return VersionedEntry.class;
			}

		};

}
// SB-Hash:263135143