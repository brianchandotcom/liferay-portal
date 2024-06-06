/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ERCVersionedEntry service. Represents a row in the &quot;ERCVersionedEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.ERCVersionedEntryImpl"
)
@ProviderType
public interface ERCVersionedEntry
	extends ERCVersionedEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCVersionedEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ERCVersionedEntry, Long>
		ERC_VERSIONED_ENTRY_ID_ACCESSOR =
			new Accessor<ERCVersionedEntry, Long>() {

				@Override
				public Long get(ERCVersionedEntry ercVersionedEntry) {
					return ercVersionedEntry.getErcVersionedEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ERCVersionedEntry> getTypeClass() {
					return ERCVersionedEntry.class;
				}

			};

}